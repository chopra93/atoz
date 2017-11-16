package com.cfs.core.objects;

/**
 * Created by Chopra on 16/11/17.
 */
public class Information implements Comparable<Information>{
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Information o) {
        return this.name.compareTo(o.getName());
    }
}
