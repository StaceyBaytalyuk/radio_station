package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.parts.Part;
import org.springframework.data.repository.CrudRepository;

public interface PartRepo extends CrudRepository<Part, Integer> {
}
