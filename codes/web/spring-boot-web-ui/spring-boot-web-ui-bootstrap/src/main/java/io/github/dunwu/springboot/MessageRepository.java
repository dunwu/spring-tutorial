package io.github.dunwu.springboot;

public interface MessageRepository {

    void deleteMessage(Long id);

    Iterable<Message> findAll();

    Message findMessage(Long id);

    Message save(Message message);

}
