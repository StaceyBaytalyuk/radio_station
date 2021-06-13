package com.baitaliuk.radiostation.domain.parts;

import javax.persistence.*;

@Entity
@Table(name = "PaidPart")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaidPart extends Part {

    // cents per second
    @Column(name = "price")
    protected Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
