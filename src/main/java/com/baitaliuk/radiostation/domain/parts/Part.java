package com.baitaliuk.radiostation.domain.parts;

import javax.persistence.*;

@Entity
@Table(name = "Part")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Part {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Integer id;

    // seconds
    @Column(name = "duration")
    protected Integer duration;

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
