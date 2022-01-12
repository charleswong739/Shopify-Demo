package com.chwonghm.repository;

import com.chwonghm.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface defining item database interaction methods. Methods defined
 * here are automatically generated implementations by Spring
 * <p>
 * Extending JpaRepository also provides some default repository methods.
 *
 * @author Charles Wong
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * Find an inventory item by its ID
     *
     * @param id a long representing the ID of the inventory item to find
     * @return the Item object if a corresponding one is found, and null otherwise
     */
    Item findItemById(long id);
}
