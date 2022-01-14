package com.chwonghm.entity;

import com.chwonghm.controller.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class describes a named collection of inventory items.
 */
@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class Collection {

    /**
     * The ID of this collection
     */
    @Id
    @GeneratedValue
    @Column(name = "collection_id")
    @JsonView({ Views.Collection.class, Views.Item.class })
    private long id;

    /**
     * The name of this collection
     */
    @Column(nullable = false)
    @JsonView({ Views.Collection.class, Views.Item.class })
    private String name;

    /**
     * The grocery items in this collection
     */
    @ManyToMany(mappedBy = "collections")
    @JsonView(Views.Collection.class)
    private Set<Item> items;

    /**
     * Construct an inventory collection.
     * <p>
     * Empty constructor required for Jackson deserialization
     */
    public Collection() {
    }

    /**
     * Create a named inventory collection
     *
     * @param name the String name to initialize this collection with
     */
    public Collection(String name) {
        this.name = name;
        this.items = new HashSet<>();
    }

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

    /**
     * Remove this collection from all items it contains.
     *
     * Called automatically by Hibernate before deletion of this item in order to
     * maintain database relations.
     */
    @PreRemove
    void removeCollectionFromItems() {
        for (Item item : this.items) {
            item.removeCollection(this);
        }
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
