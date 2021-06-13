package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.broadcast.Broadcast;
import com.baitaliuk.radiostation.domain.broadcast.EmployedPresenterOfBroadcast;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface EmployedPresenterOfBroadcastRepo extends CrudRepository<EmployedPresenterOfBroadcast, Integer> {
    ArrayList<EmployedPresenterOfBroadcast> findAllByBroadcast(Broadcast broadcast);
}
