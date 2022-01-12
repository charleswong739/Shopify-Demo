package com.chwonghm.repository;

import com.chwonghm.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface defining collection database interaction methods. Methods defined
 * here are automatically generated implementations by Spring
 * <p>
 * Extending JpaRepository also provides some default repository methods.
 *
 * @author Charles Wong
 */
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    /**
     * Find an inventory collection by its ID
     *
     * @param id a long representing the ID of the inventory collection to find
     * @return the Collection object if a corresponding one is found, and null otherwise
     */
    Collection findCollectionById(long id);
}
