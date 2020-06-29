package com.aoyouer.noobserver.repository;

import com.aoyouer.noobserver.entitiy.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.awt.print.Pageable;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
