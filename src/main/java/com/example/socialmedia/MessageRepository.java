package com.example.socialmedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message , Long> {

    @Query("""
        SELECT m FROM Message m
        WHERE
           (m.senderEmail=:user1 AND m.receiverEmail=:user2)OR
            (m.senderEmail=:user2 AND m.receiverEmail=:user1)
            ORDER BY m.createdAt
        """)
    List<Message> getConversation(
            @Param("user1") String user1,
            @Param("user2") String user2
    );

    @Query("""
        SELECT m
        FROM Message m
        WHERE m.senderEmail = :sender
        AND m.receiverEmail = :receiver
        AND m.isRead = false
        """)
    List<Message> findUnreadMessages(String sender , String receiver);

    @Query("""
        SELECT COUNT(m)
        FROM Message m
        WHERE m.senderEmail = :sender
        AND m.receiverEmail = :receiver
        AND m.isRead = false
        """)
    Long countUnreadMessage(String sender , String receiver);

    List<Message> findBySenderEmailAndReceiverEmail(String sender, String receiver);
    List<Message> findByReceiverEmail(String receiver);
}
