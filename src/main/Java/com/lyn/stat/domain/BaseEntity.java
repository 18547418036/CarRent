package com.lyn.stat.domain;

/**
 * @author iamhere
 * @description
 * @date 2020/8/18
 */
public class BaseEntity {
    private String name;
    private Double value;

    public BaseEntity() {
    }

    public BaseEntity(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
