package com.example.Youtube.Repository;

import com.example.Youtube.Model.Video;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {
    Video findByPathName(String pathName);
}
