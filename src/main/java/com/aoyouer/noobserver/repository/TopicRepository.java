package com.aoyouer.noobserver.repository;

import com.aoyouer.noobserver.entitiy.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {
    Page<Topic> findAllByType(String type, Pageable pageable);

    Page<Topic> findAll(Pageable pageable);

    List<Topic> findAll();

    Topic getTopicById(long id);
}
