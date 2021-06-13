package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.broadcast.Broadcast;
import com.baitaliuk.radiostation.domain.broadcast.InvitedPresenterOfBroadcast;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface InvitedPresenterOfBroadcastRepo extends CrudRepository<InvitedPresenterOfBroadcast, Integer> {
    ArrayList<InvitedPresenterOfBroadcast> findAllByBroadcast(Broadcast broadcast);
}
