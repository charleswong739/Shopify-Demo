package com.chwonghm.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * This class defines an inventory item, which has its own unique numeric ID, as well as some string name.
 *
 * @author Charles Wong
 */
@Entity
public class Item {

    /**
     * The unique ID of this inventory item
     */
    @Id
    @GeneratedValue
    @Column(name = "list_id")
    private long id;

    /**
     * The name of this inventory item
     */
    @Column(nullable = false)
    private String name;

    /**
     * The count of this inventory item
     */
    private long count;

    /**
     * The collections this item belongs to
     */
    @ManyToMany
    @JoinTable(
            name = "item_collections",
            joinColumns = @JoinColumn(
                    name = "item_id",
                    referencedColumnName = "list_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "collection_id",
                    referencedColumnName = "collection_id"
            )
    )
    private Set<Collection> collections;

    /**
     * Construct an inventory item.
     * <p>
     * Empty constructor required for Jackson serialization
     */
    public Item() {
        count = 0L;
    }

    /**
     * Construct a named inventory item
     *
     * @param name the String name for the item
     */
    public Item(String name) {
        this.name = name;
        this.count = 0L;
    }

    /**
     * Get the ID of this item
     *
     * @return the ID of this item
     */
    public long getId() {
        return id;
    }

    /**
     * Set the ID of this item
     *
     * @param id a long representing the desired ID for this item
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the name of this item
     *
     * @return the name of this item
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this item
     *
     * @param name a String to set the name of this item to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the count of this item
     *
     * @return the count of this item
     */
    public long getCount() {
        return count;
    }

    /**
     * Set the count of this item
     *
     * @param count a long to set the count of this item to
     */
    public void setCount(long count) {
        this.count = count;
    }
}
