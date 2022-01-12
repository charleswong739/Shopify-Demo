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

    Item findItemById(long id);
}
