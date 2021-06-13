package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.parts.Advertisement;
import org.springframework.data.repository.CrudRepository;

public interface AdvertisementRepo extends CrudRepository<Advertisement, Integer> {
}
