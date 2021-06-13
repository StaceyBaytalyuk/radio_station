package com.baitaliuk.radiostation.controller;

import com.baitaliuk.radiostation.domain.broadcast.*;
import com.baitaliuk.radiostation.domain.presenters.*;
import com.baitaliuk.radiostation.repos.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class PresenterController {

    private final EmployedPresenterRepo employedPresenterRepo;
    private final InvitedPresenterRepo invitedPresenterRepo;
    private final BroadcastRepo broadcastRepo;
    private final EmployedPresenterOfBroadcastRepo employedPresenterOfBroadcastRepo;
    private final InvitedPresenterOfBroadcastRepo invitedPresenterOfBroadcastRepo;

    public PresenterController(EmployedPresenterRepo employedPresenterRepo, InvitedPresenterRepo invitedPresenterRepo,
                               BroadcastRepo broadcastRepo, EmployedPresenterOfBroadcastRepo employedPresenterOfBroadcastRepo,
                               InvitedPresenterOfBroadcastRepo invitedPresenterOfBroadcastRepo) {
        this.employedPresenterRepo = employedPresenterRepo;
        this.invitedPresenterRepo = invitedPresenterRepo;
        this.broadcastRepo = broadcastRepo;
        this.employedPresenterOfBroadcastRepo = employedPresenterOfBroadcastRepo;
        this.invitedPresenterOfBroadcastRepo = invitedPresenterOfBroadcastRepo;
    }


    @GetMapping("presenters")
    public String presenters(Map<String, Object> model) {
        Iterable<EmployedPresenter> employedPresenters = employedPresenterRepo.findAll();
        model.put("employedPresenters", employedPresenters);

        Iterable<InvitedPresenter> invitedPresenters = invitedPresenterRepo.findAll();
        model.put("invitedPresenters", invitedPresenters);

        return "presenters";
    }

    @PostMapping("addEmployedPresenter")
    public String addEmployedPresenter(@RequestParam String name, @RequestParam Integer experience, Map<String, Object> model) {
        if ( !name.isEmpty() && experience!=null ) {
            EmployedPresenter presenter = new EmployedPresenter(name, experience);
            employedPresenterRepo.save(presenter);
        } else {
            model.put("message", "Введіть усі поля");
        }
        return presenters(model);
    }

    @PostMapping("addInvitedPresenter")
    public String addInvitedPresenter(@RequestParam String name, @RequestParam String resume, Map<String, Object> model) {
        if ( !name.isEmpty() && !resume.isEmpty() ) {
            InvitedPresenter presenter = new InvitedPresenter(name, resume);
            invitedPresenterRepo.save(presenter);
        } else {
            model.put("message", "Введіть усі поля");
        }
        return presenters(model);
    }

    @PostMapping("addPresenterToBroadcast")
    public String addPresenterToBroadcast(@RequestParam Integer id_presenter, @RequestParam Integer id_broadcast, Map<String, Object> model) {
        if ( id_broadcast!=null && id_presenter!=null ) {
            if ( broadcastRepo.findById(id_broadcast).isPresent() && employedPresenterRepo.findById(id_presenter).isPresent() ) {
                Broadcast broadcast = broadcastRepo.findById(id_broadcast).get();
                EmployedPresenter presenter = employedPresenterRepo.findById(id_presenter).get();
                employedPresenterOfBroadcastRepo.save(new EmployedPresenterOfBroadcast(presenter, broadcast));
            } else {
                model.put("message", "Неправильний id");
            }
        } else {
            model.put("message", "Введіть усі поля");
        }
        return presenters(model);
    }

    @PostMapping("invitePresenterToBroadcast")
    public String invitePresenterToBroadcast(@RequestParam Integer id_presenter, @RequestParam Integer id_broadcast, Map<String, Object> model) {
        if ( id_broadcast!=null && id_presenter!=null ) {
            if ( broadcastRepo.findById(id_broadcast).isPresent() && invitedPresenterRepo.findById(id_presenter).isPresent() ) {
                Broadcast broadcast = broadcastRepo.findById(id_broadcast).get();
                InvitedPresenter presenter = invitedPresenterRepo.findById(id_presenter).get();
                invitedPresenterOfBroadcastRepo.save(new InvitedPresenterOfBroadcast(presenter, broadcast));
            } else {
                model.put("message", "Неправильний id");
            }
        } else {
            model.put("message", "Введіть усі поля");
        }
        return presenters(model);
    }

}
