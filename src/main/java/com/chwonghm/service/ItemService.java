package com.chwonghm.service;

import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * @throws ResourceNotFoundException if the provided ID does not match an existing inventory item
     */
    @Transactional
    public void deleteItem(long id) throws ResourceNotFoundException {
        Item toDelete = findItemIfExists(id);

        itemRepository.delete(toDelete);
    }

    /**
     * Get a list of all saved inventory items
     *
     * @return a List of all inventory items
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Edit the name of an inventory item, specified by ID.
     *
     * @param name the String to edit the item's name to
     * @param id a long representing the ID of the item to edit
     * @return the newly edited item
     * @throws ResourceNotFoundException if the provided ID does not match an existing inventory item
     */
    @Transactional
    public Item editItemName(String name, long id) throws ResourceNotFoundException {
        Item toEdit = findItemIfExists(id);

        toEdit.setName(name);

        return itemRepository.save(toEdit);
    }

    /**
     * Find an inventory item by ID, if it exists. If not, an exception is thrown
     *
     * @param id a long representing the ID of the item to find
     * @return the inventory item corresponding to the provided ID
     * @throws ResourceNotFoundException if the provided ID does not match an existing inventory item
     */
    private Item findItemIfExists(long id) throws ResourceNotFoundException {
        Item item = itemRepository.findItemById(id);

        if (item == null) {
            throw new ResourceNotFoundException(String.format("Could not find item with ID %d", id));
        }

        return item;
    }
}
