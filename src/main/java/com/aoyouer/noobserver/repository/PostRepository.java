package com.aoyouer.noobserver.repository;

import com.aoyouer.noobserver.entitiy.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
    //Post getPostByUser();
    void deleteById(Long id);

    Post getPostById(long id);
}
