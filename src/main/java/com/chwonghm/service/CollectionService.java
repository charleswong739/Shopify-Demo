package com.chwonghm.service;

import com.chwonghm.entity.Collection;
import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.repository.CollectionRepository;
import com.chwonghm.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public Item addCollectionToItem(long collectionId, long itemId) throws ResourceNotFoundException {
        Collection col = findCollectionIfExists(collectionRepository, collectionId);
        Item item = findItemIfExists(itemRepository, itemId);

        item.addCollection(col);

        return itemRepository.save(item);
    }

    @Transactional
    public Item addCollectionsToItem(List<Long> collectionIds, long itemId) throws ResourceNotFoundException {
        Item item = findItemIfExists(itemRepository, itemId);

        for (long collectionId : collectionIds) {
            Collection col = findCollectionIfExists(collectionRepository, collectionId);
            item.addCollection(col);
        }

        // if an exception is thrown above, no changes are saved
        return itemRepository.save(item);
    }

    @Transactional
    public Item removeCollectionFromItem(long collectionId, long itemId) throws ResourceNotFoundException {
        Collection col = findCollectionIfExists(collectionRepository, collectionId);
        Item item = findItemIfExists(itemRepository, itemId);

        item.removeCollection(col);

        return itemRepository.save(item);
    }

    @Transactional
    public Item clearCollectionsFromItem(long itemId) throws ResourceNotFoundException {
        Item item = findItemIfExists(itemRepository, itemId);

        for (Collection col : item.getCollections()) {
            item.removeCollection(col);
        }

        return itemRepository.save(item);
    }
}
