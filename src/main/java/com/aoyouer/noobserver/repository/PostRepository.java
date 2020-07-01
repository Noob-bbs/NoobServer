package com.aoyouer.noobserver.repository;

import com.aoyouer.noobserver.entitiy.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    //Post getPostByUser();
    void deleteById(Long id);

    Post getPostById(long id);

    Page<Post> findAll(Pageable pageable);

    List<Post> findAllByUserId(long userId);
}
