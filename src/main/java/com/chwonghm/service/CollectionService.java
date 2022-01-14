package com.chwonghm.service;

import com.chwonghm.entity.Collection;
import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.repository.CollectionRepository;
import com.chwonghm.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.chwonghm.service.ServiceUtils.findCollectionIfExists;
import static com.chwonghm.service.ServiceUtils.findItemIfExists;

/**
 * This is a service class providing all logic for inventory item related operations. It is responsible
 * for interfacing with repository interfaces to fetch/persist Item entities.
 *
 * @author Charles Wong
 */
@Service
@Transactional(readOnly = true)
public class CollectionService {

    /**
     * Repository interface for item tables
     */
    private ItemRepository itemRepository;

    /**
     * Repository interface for collection tables
     */
    private CollectionRepository collectionRepository;

    /**
     * Constructs a UserService, injecting all requires dependencies.
     * <p>
     * Note that this constructor is automatically picked up by Spring for autowiring.
     *
     * @param itemRepository an ItemRepository instance to support this service
     * @param collectionRepository a CollectionRepository instance to support this service
     */
    public CollectionService(ItemRepository itemRepository, CollectionRepository collectionRepository) {
        this.itemRepository = itemRepository;
        this.collectionRepository = collectionRepository;
    }

    /**
     * Create and persist a new inventory collection
     *
     * @param name the String name of the new collection
     * @return the newly created collection
     */
    @Transactional
    public Collection createCollection(String name) {
        return collectionRepository.save(new Collection(name));
    }

    /**
     * Delete the collection with the given ID
     *
     * @param id a long representing the ID of the collection to delete
     * @throws ResourceNotFoundException if the provided ID does not match an existing collection
     */
    @Transactional
    public void deleteCollection(long id) throws ResourceNotFoundException {
        Collection col = findCollectionIfExists(collectionRepository, id);
        collectionRepository.delete(col);
    }

    /**
     * Get a collection, specified by ID
     *
     * @param id a long representing the ID of the collection to get
     * @return the collection corresponding to the provided ID
     * @throws ResourceNotFoundException if the provided ID does not match an existing collection
     */
    public Collection getCollection(long id) throws ResourceNotFoundException {
        return findCollectionIfExists(collectionRepository, id);
    }

    /**
     * Get a list of all saved collections
     *
     * @return a List of all saved collections
     */
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    /**
     * Add collections to an item. Both the collections and the item are specified by ID. Duplicate
     * collection IDs are allowed; in this case, the collection is only added to the item once. All IDs provided
     * must exist, otherwise no change is made to the item.
     *
     * @param collectionIds a List of longs representing the IDs of the collections to add
     * @param itemId a long representing the ID of the item to be edited
     * @return the newly edited item
     * @throws ResourceNotFoundException if the provided IDs do not match existing data entries
     */
    @Transactional
    public Item addCollectionToItem(List<Long> collectionIds, long itemId) throws ResourceNotFoundException {
        Item item = findItemIfExists(itemRepository, itemId);

        Set<Collection> toAdd = new HashSet<>();

        for (long collectionId : collectionIds) {
            Collection col = findCollectionIfExists(collectionRepository, collectionId);
            toAdd.add(col);
        }

        for (Collection col : toAdd) {
            item.addCollection(col);
        }

        // if an exception is thrown above, no changes are saved
        return itemRepository.save(item);
    }

    /**
     * Remove collections from an item. Both the collections and the item are specified by ID. Duplicate
     * collection IDs are allowed; in this case, the collection is removed from the item as expected. All IDs provided
     * must exist, otherwise no change is made to the item.
     *
     * @param collectionIds a List of longs representing the IDs of the collections to remove
     * @param itemId a long representing the ID of the item to be edited
     * @return the newly edited item
     * @throws ResourceNotFoundException if the provided IDs do not match existing data entries
     */
    @Transactional
    public Item removeCollectionFromItem(List<Long> collectionIds, long itemId) throws ResourceNotFoundException {
        Item item = findItemIfExists(itemRepository, itemId);

        Set<Collection> toRemove = new HashSet<>();

        for (long collectionId : collectionIds) {
            Collection col = findCollectionIfExists(collectionRepository, collectionId);
            toRemove.add(col);
        }

        for (Collection col : toRemove) {
            item.removeCollection(col);
        }

        // if an exception is thrown above, no changes are saved
        return itemRepository.save(item);
    }
}
