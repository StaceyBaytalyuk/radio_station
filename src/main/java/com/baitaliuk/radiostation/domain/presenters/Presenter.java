package com.baitaliuk.radiostation.domain.presenters;

import javax.persistence.*;

@Entity
@Table(name = "Presenter")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Presenter {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Integer id;

    @Column(name = "name", length = 50)
    protected String name;

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
}
