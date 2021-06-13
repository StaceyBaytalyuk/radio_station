package com.baitaliuk.radiostation.domain.presenters;

import javax.persistence.*;;

@Entity
@Table(name = "EmployedPresenter")
public class EmployedPresenter extends Presenter {

    // years
    @Column(name = "experience")
    private Integer experience;

    public EmployedPresenter() {}

    public EmployedPresenter(String name, Integer experience) {
        this.name = name;
        this.experience = experience;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}