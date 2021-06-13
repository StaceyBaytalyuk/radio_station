package com.baitaliuk.radiostation.controller;

import com.baitaliuk.radiostation.domain.broadcast.Broadcast;
import com.baitaliuk.radiostation.domain.presenters.*;
import com.baitaliuk.radiostation.domain.parts.*;
import com.baitaliuk.radiostation.repos.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private final EmployedPresenterRepo employedPresenterRepo;
    private final InvitedPresenterRepo invitedPresenterRepo;
    private final BroadcastRepo broadcastRepo;
    private final SongRepo songRepo;
    private final AdvertisementRepo advertisementRepo;
    private final InterviewRepo interviewRepo;

    public MainController(EmployedPresenterRepo employedPresenterRepo, InvitedPresenterRepo invitedPresenterRepo,
                          BroadcastRepo broadcastRepo, SongRepo songRepo, AdvertisementRepo advertisementRepo,
                          InterviewRepo interviewRepo) {
        this.employedPresenterRepo = employedPresenterRepo;
        this.invitedPresenterRepo = invitedPresenterRepo;
        this.broadcastRepo = broadcastRepo;
        this.songRepo = songRepo;
        this.advertisementRepo = advertisementRepo;
        this.interviewRepo = interviewRepo;
    }

    @GetMapping("mainMenu")
    public String mainMenu() {
        return "mainMenu";
    }

    @GetMapping("generateAll")
    public String generateAll() {
        generatePresenters();
        generateBroadcasts();
        generateParts();
        return mainMenu();
    }

    private void generatePresenters() {
        List<EmployedPresenter> employed = new ArrayList<>();
        employed.add(new EmployedPresenter("Bill", 1));
        employed.add(new EmployedPresenter("Marry", 3));
        employed.add(new EmployedPresenter("John", 7));
        employedPresenterRepo.saveAll(employed);

        List<InvitedPresenter> invited = new ArrayList<>();
        invited.add(new InvitedPresenter("Mike", "mikeCV.doc"));
        invited.add(new InvitedPresenter("Jane", "janeCV.doc"));
        invitedPresenterRepo.saveAll(invited);
    }

    private void generateBroadcasts() {
        List<Broadcast> broadcasts = new ArrayList<>();
        int minutes = 50;
        broadcasts.add(new Broadcast(minutes*60));
        minutes = 40;
        broadcasts.add(new Broadcast(minutes*60));
        broadcastRepo.saveAll(broadcasts);
    }

    private void generateParts() {
        int minutes = 3;
        List<Song> songs = new ArrayList<>();
        songs.add(new Song(minutes*60, "song1", "artist1"));
        minutes = 8;
        songs.add(new Song(minutes*60, "song2", "artist2"));
        minutes = 5;
        songs.add(new Song(minutes*60, "song3", "artist3"));
        songRepo.saveAll(songs);

        List<Advertisement> ads = new ArrayList<>();
        minutes = 1;
        ads.add(new Advertisement(minutes*60, "ariel"));
        minutes = 2;
        ads.add(new Advertisement(minutes*60, "persil"));
        minutes = 1;
        ads.add(new Advertisement(minutes*60, "tide"));
        advertisementRepo.saveAll(ads);

        List<Interview> interviews = new ArrayList<>();
        minutes = 3;
        interviews.add(new Interview(minutes*60, "Jim"));
        minutes = 5;
        interviews.add(new Interview(minutes*60, "Hannah"));
        interviewRepo.saveAll(interviews);
    }

}
