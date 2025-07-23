package com.example.Youtube.Repository;

import com.example.Youtube.Model.Channel;
import com.example.Youtube.Model.EnumFeel;
import com.example.Youtube.Model.Feeling;
import com.example.Youtube.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeelingRepository extends CrudRepository<Feeling, Long> {
    @Query("SELECT f FROM Feeling f WHERE f.channel.id = :channelId AND f.video.id = :videoId")
    Feeling findByIds(Long channelId, Long videoId);

    @Query("SELECT COUNT(f) FROM Feeling f WHERE f.video.pathName = :video AND f.feeling = 'LIKE'")
    long countLikesByVideo(@Param("video") String videoPathName);

    @Query("SELECT f.feeling FROM Feeling f WHERE f.channel = :channel AND f.video.pathName = :videoPathName")
    EnumFeel getUserFeeling(@Param("channel") Channel channel, @Param("videoPathName") String videoPathName);
}
