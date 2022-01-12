package com.chwonghm.service;

import com.chwonghm.entity.Collection;
import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.repository.CollectionRepository;
import com.chwonghm.repository.ItemRepository;

public class ServiceUtils {

    /**
     * Find an inventory item by ID, if it exists. If not, an exception is thrown
     *
     * @param itemRepository the ItemRepository to search
     * @param id a long representing the ID of the item to find
     * @return the inventory item corresponding to the provided ID
     * @throws ResourceNotFoundException if the provided ID does not match an existing inventory item
     */
    static Item findItemIfExists(ItemRepository itemRepository, long id) throws ResourceNotFoundException {
        Item item = itemRepository.findItemById(id);

        if (item == null) {
            throw new ResourceNotFoundException(String.format("Could not find item with ID %d", id));
        }

        return item;
    }

    /**
     * Find an inventory collection by ID, if it exists. If not, an exception is thrown
     *
     * @param collectionRepository the CollectionRepository to search
     * @param id a long representing the ID of the collection to find
     * @return the inventory collection corresponding to the provided ID
     * @throws ResourceNotFoundException if the provided ID does not match an existing inventory collection
     */
    static Collection findCollectionIfExists(CollectionRepository collectionRepository, long id) throws ResourceNotFoundException {
        Collection collection = collectionRepository.findCollectionById(id);

        if (collection == null) {
            throw new ResourceNotFoundException(String.format("Could not find collection with ID %d", id));
        }

        return collection;
    }
}
