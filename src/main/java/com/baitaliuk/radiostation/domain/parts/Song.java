package com.baitaliuk.radiostation.domain.parts;

import javax.persistence.*;

@Entity
@Table(name = "Song")
public class Song extends Part {

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "artist", length = 50)
    private String artist;

    public Song() {}

    public Song(int duration, String name, String artist) {
        this.duration = duration;
        this.name = name;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
