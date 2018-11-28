package me.bactoria.celebbatch.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, String> {
    Optional<Channel> findById(String id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value="UPDATE Channel channel SET channel.title=:title, channel.content=:content, channel.image=:image, channel.subscriber=:subscriber, channel.joinDate=:joinDate, channel.views=:views, channel.updatedTime=:updatedTime WHERE channel.id=:id")
    int updateChannel(@Param("id") String id, @Param("title") String title, @Param("content") String content, @Param("image") String image, @Param("subscriber") Long subscriber, @Param("joinDate") LocalDate joinDate, @Param("views") Long views, @Param("updatedTime") LocalDateTime updatedTime);
}
