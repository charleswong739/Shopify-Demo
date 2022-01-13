package com.chwonghm.controller;

import com.chwonghm.entity.Collection;
import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.service.CollectionService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
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

    @JsonView(Views.Item.class)
    @GetMapping("api/collection/all")
    public List<Collection> getAllCollections() {
        return this.collectionService.getAllCollections();
    }

    @JsonView(Views.Collection.class)
    @GetMapping("api/collection")
    public Collection getCollection(@RequestParam("id") long id) throws ResourceNotFoundException {
        return this.collectionService.getCollection(id);
    }

    @JsonView(Views.Collection.class)
    @Validated(CreateGroup.class)
    @PostMapping("api/collection")
    public Collection createCollection(@Valid @RequestBody CollectionPayload payload) {
        return this.collectionService.createCollection(payload.name);
    }

    @JsonView(Views.Item.class)
    @Validated(EditCollectionGroup.class)
    @PutMapping("api/item/collection")
    public Item addCollection(@RequestParam("id") long id, @Valid @RequestBody CollectionPayload payload) throws ResourceNotFoundException {
        return this.collectionService.addCollectionToItem(payload.collectionIds, id);
    }

    @JsonView(Views.Item.class)
    @Validated(EditCollectionGroup.class)
    @DeleteMapping("api/item/collection")
    public Item removeCollection(@RequestParam("id") long id, @Valid @RequestBody CollectionPayload payload) throws ResourceNotFoundException {
        return this.collectionService.removeCollectionFromItem(payload.collectionIds, id);
    }

    private interface CreateGroup {
    }

    private interface EditCollectionGroup {
    }

    /**
     * This class defines all possible request payload parameters for endpoints in this controller. Through
     * the use of validation groups, this then allows for input validation for individual endpoints.
     */
    private static class CollectionPayload {

        /**
         * Name of an item
         */
        @NotNull(groups = CreateGroup.class)
        private String name;

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

        public void setCollectionIds(List<Long> collectionIds) {
            this.collectionIds = collectionIds;
        }
    }
}
