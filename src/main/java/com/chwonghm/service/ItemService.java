package com.chwonghm.service;

import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a service class providing all logic for inventory item related operations. It is responsible
 * for interfacing with repository interfaces to fetch/persist Item entities.
 *
 * @author Charles Wong
 */
@Service
@Transactional(readOnly = true)
public class ItemService {

    /**
     * Repository interface for item tables
     */
    private ItemRepository itemRepository;

    /**
     * Constructs a UserService, injecting all requires dependencies.
     * <p>
     * Note that this constructor is automatically picked up by Spring for autowiring.
     *
     * @param itemRepository  an ItemRepository instance to support this service
     */
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Create a new inventory item with the provided name.
     *
     * No checks are performed for duplicate item names. Created items are assigned a new unique ID.
     *
     * @param name the String name to create the inventory item with
     * @return the newly created inventory item
     */
    @Transactional
    public Item createItem(String name) {
        return itemRepository.save(new Item(name));
    }

    /**
     * Delete the inventory with the provided ID, if it exists
     *
     * @param id a long representing the ID of the inventory item to delete
     * @throws Exception if the provided ID does not match an existing inventory item
     */
    @Transactional
    public void deleteItem(long id) throws Exception{
        Item toDelete = itemRepository.findItemById(id);

        if (toDelete == null) {
            throw new ResourceNotFoundException(String.format("Could not find item with ID %d", id));
        }

        itemRepository.delete(toDelete);
    }
}
