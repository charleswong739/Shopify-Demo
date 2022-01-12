package com.chwonghm.controller;

import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * Spring REST controller defining endpoints related to grocery list management. The following
 * endpoints are defined:
 * <ul>
 *     <li>api/item/create</li>
 *     <li>api/item/delete</li>
 *     <li>api/item/edit</li>
 *     <li>api/item/get-all</li>
 * </ul>
 *
 * @author Charles Wong
 */
@RestController
@Validated
public class ItemController {

    /**
     * An item Service to delegate logic to.
     */
    private final ItemService itemService;

    /**
     * Constructs this grocery controller given an ItemService.
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
     *
     * The following validation strategies apply:
     * <ul>
     *     <li>name must be provided</li>
     * </ul>
     *
     * id is ignored, whether it is provided or not.
     *
     * @param payload the ItemPayload of the request
     * @return the newly created inventory item
     */
    @PostMapping("api/item/create")
    public Item createItem(@Validated(CreateGroup.class) @RequestBody ItemPayload payload) {
        return itemService.createItem(payload.name);
    }

    /**
     * Delete the inventory item with the provided ID
     *
     * @param id a long representing the ID of the item to delete
     * @throws ResourceNotFoundException if the provided ID does not match an existing item
     */
    @DeleteMapping("api/item/delete")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteList(@RequestParam("id") long id) throws ResourceNotFoundException {
        itemService.deleteItem(id);
    }

    /**
     * Get all stored inventory items
     *
     * @return a List of all stored inventory items
     */
    @GetMapping("api/item/get-all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    /**
     * Used to specify the validation strategies for create item
     */
    private interface CreateGroup { }

    /**
     * This class defines all possible request payload parameters for endpoints in this controller. Through
     * the use of validation groups, this then allows for input validation for individual endpoints.
     */
    private static class ItemPayload {

        /**
         * ID of an item
         */
        private Long id;

        /**
         * Name of an item
         */
        @NotNull(groups = CreateGroup.class)
        private String name;
    }
}
