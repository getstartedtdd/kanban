package com.kanban.core;

/**
 * Created by L.x on 15-6-2.
 */
public class Role {
    private String name;
    private Integer id;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

}
