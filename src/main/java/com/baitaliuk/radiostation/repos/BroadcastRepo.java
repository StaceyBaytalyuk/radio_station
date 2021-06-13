package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.broadcast.Broadcast;
import org.springframework.data.repository.CrudRepository;

public interface BroadcastRepo extends CrudRepository<Broadcast, Integer> {
}
