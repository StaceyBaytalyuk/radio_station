package com.baitaliuk.radiostation.domain.broadcast;

import com.baitaliuk.radiostation.domain.presenters.*;

import javax.persistence.*;

@Entity
@Table(name = "EmployedPresenterOfBroadcast")
public class EmployedPresenterOfBroadcast {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "broadcast_id")
    private Broadcast broadcast = null;

    @ManyToOne
    @JoinColumn(name = "presenter_id")
    private EmployedPresenter presenter = null;

    public EmployedPresenterOfBroadcast() {}

    public EmployedPresenterOfBroadcast(EmployedPresenter presenter, Broadcast broadcast) {
        this.broadcast = broadcast;
        this.presenter = presenter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(EmployedPresenter presenter) {
        this.presenter = presenter;
    }
}
