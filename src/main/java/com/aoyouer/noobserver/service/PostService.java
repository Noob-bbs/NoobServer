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
import java.util.Set;

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
        Set<Long> userIdSet = post.getLikeUsers();
        if (!userIdSet.contains(userId)){
            //点赞
            userIdSet.add(userId);
            post.setLikeUsers(userIdSet);
            post.setLikeNum(post.getLikeNum() + 1);
        }
        else {
            //取消点赞
            userIdSet.remove(userId);
            post.setLikeUsers(userIdSet);
            post.setLikeNum(post.getLikeNum() - 1);
        }
        postRepository.save(post);
        return post.getLikeNum();
    }
}
