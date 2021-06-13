package com.baitaliuk.radiostation.controller;

import com.baitaliuk.radiostation.domain.broadcast.*;
import com.baitaliuk.radiostation.domain.parts.*;
import com.baitaliuk.radiostation.repos.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PartsController {
    private final PartRepo partRepo;
    private final PaidPartRepo paidPartRepo;
    private final SongRepo songRepo;
    private final AdvertisementRepo advertisementRepo;
    private final InterviewRepo interviewRepo;
    private final PartOfBroadcastRepo partOfBroadcastRepo;
    private final BroadcastRepo broadcastRepo;

    public PartsController(PartRepo partRepo, PaidPartRepo paidPartRepo, SongRepo songRepo,
                           AdvertisementRepo advertisementRepo, InterviewRepo interviewRepo,
                           PartOfBroadcastRepo partOfBroadcastRepo, BroadcastRepo broadcastRepo) {
        this.partRepo = partRepo;
        this.paidPartRepo = paidPartRepo;
        this.songRepo = songRepo;
        this.advertisementRepo = advertisementRepo;
        this.interviewRepo = interviewRepo;
        this.partOfBroadcastRepo = partOfBroadcastRepo;
        this.broadcastRepo = broadcastRepo;
    }

    @GetMapping("parts")
    public String parts(Map<String, Object> model) {
        Iterable<Song> songs = songRepo.findAll();
        model.put("songs", songs);

        Iterable<Advertisement> advertisements = advertisementRepo.findAll();
        model.put("advertisements", advertisements);

        Iterable<Interview> interviews = interviewRepo.findAll();
        model.put("interviews", interviews);
        return "parts";
    }

    @PostMapping("addSong")
    public String addSong(@RequestParam Integer duration, @RequestParam String name, @RequestParam String artist, Map<String, Object> model) {
        if ( !name.isEmpty() && !artist.isEmpty() && duration!=null ) {
            Song song = new Song(duration, name, artist);
            songRepo.save(song);
        } else {
            model.put("message", "Введіть усі поля");
        }
        return parts(model);
    }

    @PostMapping("addAdvertisement")
    public String addAdvertisement(@RequestParam Integer duration, @RequestParam String product, Map<String, Object> model) {
        if ( !product.isEmpty() ) {
            Advertisement advertisement = new Advertisement(duration, product);
            advertisementRepo.save(advertisement);
        } else {
            model.put("message", "Введіть усі поля");
        }
        return parts(model);
    }

    @PostMapping("addInterview")
    public String addInterview(@RequestParam Integer duration, @RequestParam String interviewee, Map<String, Object> model) {
        if ( !interviewee.isEmpty() ) {
            Interview interview = new Interview(duration, interviewee);
            interviewRepo.save(interview);
        } else {
            model.put("message", "Введіть усі поля");
        }
        return parts(model);
    }

    @PostMapping("addUnpaidPartToBroadcast")
    public String addUnpaidPartToBroadcast(@RequestParam Integer id_part, @RequestParam Integer id_broadcast, Map<String, Object> model) {
        if ( id_part!=null && id_broadcast!=null ) {
            Optional<Part> part = partRepo.findById(id_part);
            Optional<Broadcast> broadcast = broadcastRepo.findById(id_broadcast);
            if ( part.isPresent() && broadcast.isPresent() ) {
                if ( checkDurationAvailable(broadcast.get(), part.get()) ) {
                    int number = 1 + partOfBroadcastRepo.findAllByBroadcast(broadcast.get()).size();
                    partOfBroadcastRepo.save(new PartOfBroadcast(broadcast.get(), part.get(), number));
                } else {
                    model.put("message", "Не вистачає місця щоб додати частину до трансляції, занадто велика тривалість");
                }
            } else {
                model.put("message", "Неправильний id");
            }
        } else {
            model.put("message", "Введіть усі поля");
        }
        return parts(model);
    }

    @PostMapping("addPaidPartToBroadcast")
    public String addSongToBroadcast(@RequestParam Integer id_part, @RequestParam Integer id_broadcast, Map<String, Object> model) {
        if ( id_part!=null && id_broadcast!=null ) {
            Optional<PaidPart> part = paidPartRepo.findById(id_part);
            Optional<Broadcast> broadcast = broadcastRepo.findById(id_broadcast);
            if ( part.isPresent() && broadcast.isPresent() ) {
                if ( checkDurationAvailable(broadcast.get(), part.get()) ) {
                    if ( checkPercentOfPaid(broadcast.get(), part.get()) ) {
                        int number = 1 + partOfBroadcastRepo.findAllByBroadcast(broadcast.get()).size();
                        partOfBroadcastRepo.save(new PartOfBroadcast(broadcast.get(), part.get(), number));
                    } else {
                        model.put("message", "Кількість платного контенту не може перевищувати 50%");
                    }
                } else {
                    model.put("message", "Не вистачає місця щоб додати частину до трансляції, занадто велика тривалість");
                }
            } else {
                model.put("message", "Неправильний id");
            }
        } else {
            model.put("message", "Введіть усі поля");
        }
        return parts(model);
    }

    private boolean checkDurationAvailable(Broadcast broadcast, Part part) {
        int sum = 0;
        ArrayList<PartOfBroadcast> parts = partOfBroadcastRepo.findAllByBroadcast(broadcast);
        sum += parts.stream().mapToInt(p -> p.getPart().getDuration()).sum();
        int available = broadcast.getDuration() - sum;
        System.out.println("sum = "+sum+", available = "+available);
        return available >= part.getDuration();
    }

    private boolean checkPercentOfPaid(Broadcast broadcast, PaidPart partToAdd) {
        ArrayList<PartOfBroadcast> allPartsOfBroadcast = partOfBroadcastRepo.findAllByBroadcast(broadcast);
        ArrayList<Part> parts = (ArrayList<Part>) allPartsOfBroadcast.stream().filter(partOfBroadcast -> partOfBroadcast.getBroadcast().equals(broadcast)).map(PartOfBroadcast::getPart).collect(Collectors.toList());
        int sum = 0;
        Iterable<PaidPart> iterable = paidPartRepo.findAllById(parts.stream().map(Part::getId).collect(Collectors.toList()));
        if ( iterable.iterator().hasNext() ) {
            ArrayList<PaidPart> paidParts = (ArrayList<PaidPart>) paidPartRepo.findAllById(parts.stream().map(Part::getId).collect(Collectors.toList()));
            sum = paidParts.stream().mapToInt(Part::getDuration).sum();
        }
        int available = broadcast.getDuration() / 2;
        int occupied = sum + partToAdd.getDuration();
        return available >= occupied;
    }

}
