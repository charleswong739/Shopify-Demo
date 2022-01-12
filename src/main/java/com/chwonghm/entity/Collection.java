package com.chwonghm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * This class describes a named collection of inventory items.
 */
@Entity
public class Collection {

    /**
     * The ID of this collection
     */
    @Id
    @GeneratedValue
    @Column(name = "collection_id")
    private long id;

    /**
     * The name of this collection
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The grocery items in this collection
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "collections")
    private Set<Item> items;

    /**
     * Get the ID of this collection
     *
     * @return the ID of this collection
     */
    public long getId() {
        return id;
    }

    /**
     * Set the ID of this collection
     *
     * @param id a long representing the desired ID for this collection
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the name of this collection
     *
     * @return the name of this collection
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this collection
     *
     * @param name a String to set the name of this collection to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the items in this collection
     *
     * @return the Set of items in this collection
     */
    public Set<Item> getItems() {
        return items;
    }

    /**
     * Add an item to this collection. If the provided item is already in this collection,
     * no change occurs.
     *
     * @param item the Item to place in this collection
     */
    void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Remove an item from this collection. If the provided item is not in this collection,
     * no change occurs.
     *
     * @param item the Item to remove from this collection
     */
    void removeItem(Item item) {
        this.items.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
