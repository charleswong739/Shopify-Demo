package com.chwonghm.controller;

import com.chwonghm.entity.Collection;
import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.service.CollectionService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Spring REST controller defining endpoints related to inventory collection management. The following
 * endpoints are defined:
 * <ul>
 *     <li>api/collection</li>
 *     <li>api/collection/all</li>
 *     <li>api/item/collection</li>
 * </ul>
 *
 * @author Charles Wong
 */
@CrossOrigin
@RestController
@Validated
public class CollectionController {

    /**
     * A collection Service to delegate logic to.
     */
    private final CollectionService collectionService;

    /**
     * Constructs this collection controller given a CollectionService.
     * <p>
     * Note that by default, Spring will attempt to autowire the only
     * constructor of a class.
     *
     * @param collectionService an collectionService used to provide logic for this controller
     */
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    /**
     * Get all saved collections
     *
     * @return a List of all saved collections
     */
    @JsonView(Views.Item.class)
    @GetMapping("api/collection/all")
    public List<Collection> getAllCollections() {
        return this.collectionService.getAllCollections();
    }

    /**
     * Get a collection by ID.
     *
     * @param id a long representing the ID of the collection to fetch
     * @return the collection with the corresponding ID
     * @throws ResourceNotFoundException if the provided ID does not match an existing collection
     */
    @JsonView(Views.Collection.class)
    @GetMapping("api/collection")
    public Collection getCollection(@RequestParam("id") long id) throws ResourceNotFoundException {
        return this.collectionService.getCollection(id);
    }

    /**
     * Create a new collection, and initialize using details provided with the payload
     *
     * @param payload the CollectionPayload provided with the request
     * @return the newly created Collection
     */
    @JsonView(Views.Collection.class)
    @Validated(CreateGroup.class)
    @PostMapping("api/collection")
    public Collection createCollection(@Valid @RequestBody CollectionPayload payload) {
        return this.collectionService.createCollection(payload.name);
    }

    /**
     * Place an item in collections, by specifying an item ID, and providing a list of collection IDs in
     * the request body. All provided IDs must match existing data entries, otherwise no changes are made.
     *
     * @param id a long representing the ID of the item to place in collections
     * @param payload the CollectionPayload provided with the request
     * @return the newly edited inventory Item
     * @throws ResourceNotFoundException if any provided ID does not match existing data entries
     */
    @JsonView(Views.Item.class)
    @Validated(EditCollectionGroup.class)
    @PutMapping("api/item/collection")
    public Item addCollection(@RequestParam("id") long id, @Valid @RequestBody CollectionPayload payload) throws ResourceNotFoundException {
        return this.collectionService.addCollectionToItem(payload.collectionIds, id);
    }

    /**
     * Remove an inventory item from collections by specifying an item ID, and providing a list of collection IDs in
     * the request body. All provided IDs must match existing data entries, otherwise no changes are made.
     * Providing collection IDs that do not contain the inventory item is allowed; in this case the returned item
     * will simply continue to not be in that collection.
     *
     * @param id  a long representing the ID of the item to remove from collections
     * @param payload the CollectionPayload provided with the request
     * @return the newly edited inventory Item
     * @throws ResourceNotFoundException if any provided ID does not match existing data entries
     */
    @JsonView(Views.Item.class)
    @Validated(EditCollectionGroup.class)
    @DeleteMapping("api/item/collection")
    public Item removeCollection(@RequestParam("id") long id, @Valid @RequestBody CollectionPayload payload) throws ResourceNotFoundException {
        return this.collectionService.removeCollectionFromItem(payload.collectionIds, id);
    }

    /**
     * Used to specify the validation strategies for create collection
     */
    private interface CreateGroup {
    }

    /**
     * Used to specify the validation strategies for edit item's collection
     */
    private interface EditCollectionGroup {
    }

    /**
     * This class defines all possible request payload parameters for endpoints in this controller. Through
     * the use of validation groups, this then allows for input validation for individual endpoints.
     */
    private static class CollectionPayload {

        /**
         * Name of an collection
         */
        @NotNull(groups = CreateGroup.class)
        private String name;

        /**
         * A list of collection IDs to add/remove
         */
        @NotNull(groups = EditCollectionGroup.class)
        private List<Long> collectionIds;

        /**
         * Set the name field of the payload
         *
         * @param name the String name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Set the collectionIds field of the payload
         *
         * @param collectionIds a List of longs to set
         */
        public void setCollectionIds(List<Long> collectionIds) {
            this.collectionIds = collectionIds;
        }
    }
}
