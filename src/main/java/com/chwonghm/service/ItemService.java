package com.chwonghm.service;

import com.chwonghm.entity.Item;
import com.chwonghm.exception.ResourceNotFoundException;
import com.chwonghm.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.chwonghm.service.ServiceUtils.findItemIfExists;

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
     * Constructs an ItemService, injecting all requires dependencies.
     * <p>
     * Note that this constructor is automatically picked up by Spring for autowiring.
     *
     * @param itemRepository an ItemRepository instance to support this service
     */
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Create a new inventory item with the provided name.
     * <p>
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
        Item toDelete = findItemIfExists(itemRepository, id);

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

    public Item getItem(long id) throws ResourceNotFoundException {
        return findItemIfExists(itemRepository, id);
    }

    /**
     * Edit the name of an inventory item, specified by ID. If the given name is null, no change is made.
     *
     * @param name the String to edit the item's name to
     * @param id   a long representing the ID of the item to edit
     * @return the newly edited item
     * @throws ResourceNotFoundException if the provided ID does not match an existing inventory item
     */
    @Transactional
    public Item editItemName(String name, long id) throws ResourceNotFoundException {
        Item toEdit = findItemIfExists(itemRepository, id);

        if (name != null) {
            toEdit.setName(name);

            return itemRepository.save(toEdit);
        }

        return toEdit;
    }

    /**
     * Edit the count of an inventory item, specified by ID. If the given count is null, no change is made.
     *
     * @param count the Long to edit the item's count to
     * @param id   a long representing the ID of the item to edit
     * @return the newly edited item
     * @throws ResourceNotFoundException if the provided ID does not match an existing inventory item
     */
    @Transactional
    public Item editItemCount(Long count, long id) throws ResourceNotFoundException {
        Item toEdit = findItemIfExists(itemRepository, id);

        if (count != null) {
            toEdit.setCount(count);
        }

        return itemRepository.save((toEdit));
    }
}
