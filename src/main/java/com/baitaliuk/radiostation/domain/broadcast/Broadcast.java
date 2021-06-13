package com.baitaliuk.radiostation.domain.broadcast;

import javax.persistence.*;

@Entity
@Table(name = "Broadcast")
public class Broadcast {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    // seconds
    @Column(name = "duration")
    private Integer duration;

    public Broadcast() {}

    public Broadcast(Integer duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
