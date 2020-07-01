package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.entitiy.Post;
import com.aoyouer.noobserver.repository.PostRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PostService {
    @Resource
    PostRepository postRepository;

    public void deleteById(long id) {
        postRepository.deleteById(id);
    }

    public Post getPostById(long postId) {
        return postRepository.getPostById(postId);
    }
}
