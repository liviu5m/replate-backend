package com.replate.backend.repository;

import com.replate.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllBySenderIdOrReceiverId(Long userA, Long userB);
    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.id = :userA AND m.receiver.id = :userB) OR " +
            "(m.sender.id = :userB AND m.receiver.id = :userA)")
    List<Message> findConversationBetweenUsers(@Param("userA") Long userA, @Param("userB") Long userB);

}
