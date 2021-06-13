package com.baitaliuk.radiostation.domain.parts;

import javax.persistence.*;

@Entity
@Table(name = "Advertisement")
public class Advertisement extends PaidPart {

    @Column(name = "product", length = 50)
    private String product;

    public Advertisement() {}

    public Advertisement(int duration, String product) {
        this.duration = duration;
        this.price = 500;
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
