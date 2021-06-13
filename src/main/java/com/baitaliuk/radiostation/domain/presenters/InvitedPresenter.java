package com.baitaliuk.radiostation.domain.presenters;

import javax.persistence.*;

@Entity
@Table(name = "InvitedPresenter")
public class InvitedPresenter extends Presenter {

    @Column(name = "resume", length = 500)
    private String resume;

    public InvitedPresenter() {}

    public InvitedPresenter(String name, String resume) {
        this.name = name;
        this.resume = resume;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}