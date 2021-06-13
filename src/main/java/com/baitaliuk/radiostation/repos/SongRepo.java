package com.baitaliuk.radiostation.repos;

import com.baitaliuk.radiostation.domain.parts.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepo extends CrudRepository<Song, Integer> {
}
