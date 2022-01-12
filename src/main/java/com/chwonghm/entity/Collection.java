package com.chwonghm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

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
}
