package com.baitaliuk.radiostation.domain.parts;

import javax.persistence.*;

@Entity
@Table(name = "Interview")
public class Interview extends PaidPart {

    @Column(name = "interviewee", length = 50)
    private String interviewee;

    public Interview() {}

    public Interview(int duration, String interviewee) {
        this.duration = duration;
        this.price = 50;
        this.interviewee = interviewee;
    }

    public String getInterviewee() {
        return interviewee;
    }

    public void setInterviewee(String interviewee) {
        this.interviewee = interviewee;
    }
}
