package com.baitaliuk.radiostation.domain.broadcast;

import com.baitaliuk.radiostation.domain.parts.Part;

import javax.persistence.*;

@Entity
@Table(name = "PartOfBroadcast")
public class PartOfBroadcast {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "broadcast_id")
    private Broadcast broadcast = null;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part = null;

    // first/second... in broadcast
    @Column(name = "number")
    private Integer number;

    public PartOfBroadcast() {}

    public PartOfBroadcast(Broadcast broadcast, Part part, Integer number) {
        this.broadcast = broadcast;
        this.part = part;
        this.number = number;
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

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
