package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.broadcast.Broadcast;
import com.baitaliuk.radiostation.domain.broadcast.PartOfBroadcast;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PartOfBroadcastRepo extends CrudRepository<PartOfBroadcast, Integer> {
    ArrayList<PartOfBroadcast> findAllByBroadcast(Broadcast broadcast);
}
