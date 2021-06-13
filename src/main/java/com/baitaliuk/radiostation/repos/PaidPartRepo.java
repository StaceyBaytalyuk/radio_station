package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.parts.PaidPart;
import org.springframework.data.repository.CrudRepository;

public interface PaidPartRepo extends CrudRepository<PaidPart, Integer> {
}
