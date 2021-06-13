package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.parts.Interview;
import org.springframework.data.repository.CrudRepository;

public interface InterviewRepo extends CrudRepository<Interview, Integer> {
}
