package com.baitaliuk.radiostation.controller;

import com.baitaliuk.radiostation.domain.broadcast.*;
import com.baitaliuk.radiostation.domain.presenters.*;
import com.baitaliuk.radiostation.domain.parts.*;
import com.baitaliuk.radiostation.repos.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BroadcastController {
    private final EmployedPresenterRepo employedPresenterRepo;
    private final InvitedPresenterRepo invitedPresenterRepo;
    private final BroadcastRepo broadcastRepo;
    private final EmployedPresenterOfBroadcastRepo employedPresenterOfBroadcastRepo;
    private final InvitedPresenterOfBroadcastRepo invitedPresenterOfBroadcastRepo;
    private final PaidPartRepo paidPartRepo;
    private final SongRepo songRepo;
    private final AdvertisementRepo advertisementRepo;
    private final InterviewRepo interviewRepo;
    private final PartOfBroadcastRepo partOfBroadcastRepo;

    public BroadcastController(EmployedPresenterRepo employedPresenterRepo, InvitedPresenterRepo invitedPresenterRepo,
                               BroadcastRepo broadcastRepo, EmployedPresenterOfBroadcastRepo employedPresenterOfBroadcastRepo,
                               InvitedPresenterOfBroadcastRepo invitedPresenterOfBroadcastRepo, PaidPartRepo paidPartRepo,
                               SongRepo songRepo, AdvertisementRepo advertisementRepo, InterviewRepo interviewRepo,
                               PartOfBroadcastRepo partOfBroadcastRepo) {
        this.employedPresenterRepo = employedPresenterRepo;
        this.invitedPresenterRepo = invitedPresenterRepo;
        this.broadcastRepo = broadcastRepo;
        this.employedPresenterOfBroadcastRepo = employedPresenterOfBroadcastRepo;
        this.invitedPresenterOfBroadcastRepo = invitedPresenterOfBroadcastRepo;
        this.paidPartRepo = paidPartRepo;
        this.songRepo = songRepo;
        this.advertisementRepo = advertisementRepo;
        this.interviewRepo = interviewRepo;
        this.partOfBroadcastRepo = partOfBroadcastRepo;
    }

    @GetMapping("broadcasts")
    public String broadcasts(Map<String, Object> model) {
        Iterable<Broadcast> broadcasts = broadcastRepo.findAll();
        model.put("broadcasts", broadcasts);
        return "broadcasts";
    }

    @PostMapping("addBroadcast")
    public String addBroadcast(@RequestParam Integer duration, Map<String, Object> model) {
        if ( duration!=null ) {
            broadcastRepo.save(new Broadcast(duration));
        } else {
            model.put("message", "Неможливо додати трансляцію, введіть тривалість");
        }
        return broadcasts(model);
    }

    @GetMapping("infoBroadcast")
    public String infoBroadcast(@RequestParam Integer id, Map<String, Object> model) {
        if ( broadcastRepo.findById(id).isPresent() ) {
            Broadcast broadcast = broadcastRepo.findById(id).get();
            model.put("broadcast", broadcast);
            ArrayList<Part> parts = findAllPartsOfBroadcast(broadcast);

            showTime(broadcast, parts, model);
            showProfit(parts, model);
            showPresenters(broadcast, model);
            showParts(broadcast, parts, model);
            return "infoBroadcast";
        }
        return "broadcasts";
    }

    private void showTime(Broadcast broadcast, ArrayList<Part> parts, Map<String, Object> model) {
        int seconds = broadcast.getDuration();
        model.put("durationAll", stringFormatTime(seconds));

        seconds = calculateOccupiedSeconds(parts);
        model.put("durationOccupied", stringFormatTime(seconds));

        seconds = calculateAvailablePaidSeconds(parts, broadcast.getDuration());
        model.put("durationAvailablePaid", stringFormatTime(seconds));

        ArrayList<EmployedPresenterOfBroadcast> employedPresenters = employedPresenterOfBroadcastRepo.findAllByBroadcast(broadcast);
        Iterable<EmployedPresenter> employed = employedPresenterRepo.findAllById(employedPresenters.stream().map(p -> p.getPresenter().getId()).collect(Collectors.toList()));
        model.put("employedPresenters", employed);
    }

    private void showPresenters(Broadcast broadcast, Map<String, Object> model) {
        ArrayList<EmployedPresenterOfBroadcast> employedPresenters = employedPresenterOfBroadcastRepo.findAllByBroadcast(broadcast);
        Iterable<EmployedPresenter> employed = employedPresenterRepo.findAllById(employedPresenters.stream().map(p -> p.getPresenter().getId()).collect(Collectors.toList()));
        model.put("employedPresenters", employed);

        ArrayList<InvitedPresenterOfBroadcast> invitedPresenters = invitedPresenterOfBroadcastRepo.findAllByBroadcast(broadcast);
        Iterable<InvitedPresenter> invited = invitedPresenterRepo.findAllById(invitedPresenters.stream().map(p -> p.getPresenter().getId()).collect(Collectors.toList()));
        model.put("invitedPresenters", invited);
    }

    private void showParts(Broadcast broadcast, ArrayList<Part> parts, Map<String, Object> model) {
        Iterable<PartOfBroadcast> iterable = partOfBroadcastRepo.findAllByBroadcast(broadcast).stream().sorted(Comparator.comparing(PartOfBroadcast::getNumber)).collect(Collectors.toList());
        if ( iterable.iterator().hasNext() ) {
            ArrayList<PartOfBroadcast> allParts  = (ArrayList<PartOfBroadcast>) iterable;
            model.put("partsOfBroadcast", allParts);
            model.put("songs", findSongsByBroadcast(parts));
            model.put("advertisements", findAdvertisementsByBroadcast(parts));
            model.put("interviews", findInterviewsByBroadcast(parts));
        }
    }

    private void showProfit(ArrayList<Part> parts, Map<String, Object> model) {
        int profit = calculateProfit(parts);
        StringBuilder money = new StringBuilder();
        money.append(profit / 100).append(".").append(profit % 100);
        model.put("profit", money);
    }

    private int calculateProfit(ArrayList<Part> parts) {
        int sum = 0;
        Iterable<PaidPart> iterable = paidPartRepo.findAllById(parts.stream().map(Part::getId).collect(Collectors.toList()));
        if ( iterable.iterator().hasNext() ) {
            ArrayList<PaidPart> paidParts = (ArrayList<PaidPart>) iterable;
            sum = paidParts.stream().mapToInt(p -> p.getPrice()*p.getDuration()).sum();
        }
        return sum;
    }

    private int calculateAvailablePaidSeconds(ArrayList<Part> allParts, Integer totalDuration) {
        int sum = 0;
        Iterable<PaidPart> iterable = paidPartRepo.findAllById(allParts.stream().map(Part::getId).collect(Collectors.toList()));
        if ( iterable.iterator().hasNext() ) {
            ArrayList<PaidPart> paidParts = (ArrayList<PaidPart>) iterable;
            sum = paidParts.stream().mapToInt(Part::getDuration).sum();
        }
        int available = totalDuration / 2;
        return available - sum;
    }

    private int calculateOccupiedSeconds(ArrayList<Part> allPartsOfBroadcast) {
        int sum = 0;
        sum += allPartsOfBroadcast.stream().mapToInt(Part::getDuration).sum();
        return sum;
    }

    private StringBuilder stringFormatTime(int seconds) {
        StringBuilder time = new StringBuilder();
        time.append(seconds / 3600).append(":").append(seconds / 60).append(":").append(seconds % 60);
        return time;
    }

    // упорядочены по номеру
    private ArrayList<Part> findAllPartsOfBroadcast(Broadcast broadcast) {
        Iterable<PartOfBroadcast> iterable = partOfBroadcastRepo.findAllByBroadcast(broadcast);
        if (iterable.iterator().hasNext()) {
            ArrayList<PartOfBroadcast> allParts = (ArrayList<PartOfBroadcast>) iterable;
            return  (ArrayList<Part>) allParts.stream().filter(partOfBroadcast -> partOfBroadcast.getBroadcast().equals(broadcast)).sorted(Comparator.comparing(PartOfBroadcast::getNumber)).map(PartOfBroadcast::getPart).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    private Iterable<Song> findSongsByBroadcast(ArrayList<Part> parts) {
        return songRepo.findAllById(parts.stream().map(Part::getId).collect(Collectors.toList()));
    }

    private Iterable<Advertisement> findAdvertisementsByBroadcast(ArrayList<Part> parts) {
        return advertisementRepo.findAllById(parts.stream().map(Part::getId).collect(Collectors.toList()));
    }

    private Iterable<Interview> findInterviewsByBroadcast(ArrayList<Part> parts) {
        return interviewRepo.findAllById(parts.stream().map(Part::getId).collect(Collectors.toList()));
    }

}
