package com.epam.tm.shop.entity;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NEWS_SEQ")
    @SequenceGenerator(name = "NEWS_SEQ", sequenceName = "NEWS_SEQ", allocationSize = 1)
    private Integer id;

    public BaseEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}


