package com.aoyouer.noobserver.service;

import com.aoyouer.noobserver.entitiy.Post;
import com.aoyouer.noobserver.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public Page<Post> getLastPost() {
        Pageable pageable = PageRequest.of(0 , 10, Sort.by("time").descending());
        return postRepository.findAll(pageable);
    }

    public List<Post> getPostsByUserId(long userId) {
        return postRepository.findAllByUserId(userId);
    }

    public int getLikeNumByUserId(long userId) {
        List<Post> postList = postRepository.findAllByUserId(userId);
        int likeNum = 0;
        for (Post po :
                postList) {
            likeNum = po.getLikeNum() + likeNum;
        }
        return likeNum;
    }

    public int likePost(long userId, long postId) {
        Post post = postRepository.getPostById(postId);
        for (long id :
                post.getLikeUsers()) {
            if (userId != id) { //点赞
                post.getLikeUsers().add(userId);
                post.setLikeNum(post.getLikeUsers().size());
                postRepository.save(post);
            } else {
                post.getLikeUsers().remove(userId);
                post.setLikeNum(post.getLikeUsers().size());
                postRepository.save(post);
            }
        }
        return post.getLikeNum();
    }
}
