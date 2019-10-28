package com.murzin.chatspring.server.dbService.dao;

import com.murzin.chatspring.server.dbService.dataSet.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDAO extends CrudRepository<Message, Long> {
    Iterable<Message> findAllByUser(String user);
}
