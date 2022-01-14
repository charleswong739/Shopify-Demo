package com.chwonghm.controller;

import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.service.ItemService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Spring REST controller defining endpoints related to inventory item management. The following
 * endpoints are defined:
 * <ul>
 *     <li>api/item</li>
 *     <li>api/item/all</li>
 * </ul>
 *
 * @author Charles Wong
 */
@CrossOrigin
@RestController
@Validated
public class ItemController {

    /**
     * An item Service to delegate logic to.
     */
    private final ItemService itemService;

    /**
     * Constructs this item controller given an ItemService.
     * <p>
     * Note that by default, Spring will attempt to autowire the only
     * constructor of a class.
     *
     * @param itemService an ItemService used to provide logic for this controller
     */
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Create a new inventory item with the information provided in the request payload.
     * <p>
     * The following validation strategies apply:
     * <ul>
     *     <li>name must be provided</li>
     * </ul>
     * <p>
     * All other provided fields are ignored.
     *
     * @param payload the ItemPayload of the request
     * @return the newly created inventory item
     */
    @PostMapping("api/item")
    @Validated(CreateGroup.class)
    @JsonView(Views.Item.class)
    public Item createItem(@Valid @RequestBody ItemPayload payload) {
        return itemService.createItem(payload.name);
    }

    /**
     * Delete the inventory item with the provided ID
     *
     * @param id a long representing the ID of the item to delete
     * @throws ResourceNotFoundException if the provided ID does not match an existing item
     */
    @DeleteMapping("api/item")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @JsonView(Views.Item.class)
    public void deleteList(@RequestParam("id") long id) throws ResourceNotFoundException {
        itemService.deleteItem(id);
    }

    /**
     * Get all stored inventory items
     *
     * @return a List of all stored inventory items
     */
    @GetMapping("api/item/all")
    @JsonView(Views.Item.class)
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("api/item")
    @JsonView(Views.Item.class)
    public Item getItem(@RequestParam("id") long id) throws ResourceNotFoundException {
        return itemService.getItem(id);
    }

    /**
     * Edit details of an inventory item. The ID of the item to edit must be provided in the payload, and the ID
     * field is uneditable.
     *
     * @param payload the ItemPayload of the request
     * @return the newly edited inventory item
     * @throws ResourceNotFoundException if the provided ID does not match an existing item
     */
    @PutMapping("api/item")
    @Validated(EditGroup.class)
    @JsonView(Views.Item.class)
    public Item editItem(@RequestParam("id") long id, @Valid @RequestBody ItemPayload payload) throws ResourceNotFoundException {
        itemService.editItemName(payload.name, id);
        return itemService.editItemCount(payload.count, id);
    }

    /**
     * Used to specify the validation strategies for create item
     */
    private interface CreateGroup {
    }

    /**
     * Used to specify the validation strategies for edit item
     */
    private interface EditGroup {
    }

    /**
     * This class defines all possible request payload parameters for endpoints in this controller. Through
     * the use of validation groups, this then allows for input validation for individual endpoints.
     */
    private static class ItemPayload {

        /**
         * Name of an item
         */
        @NotNull(groups = CreateGroup.class)
        private String name;

        /**
         * Name of an item
         */
        @PositiveOrZero(groups = EditGroup.class)
        private Long count;

        /**
         * Set the name field of the payload
         *
         * @param name the String name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Set the count field of the payload
         *
         * @param count the Long count to set
         */
        public void setCount(Long count) {
            this.count = count;
        }
    }
}
