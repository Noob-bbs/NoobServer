package com.aoyouer.noobserver.controller;

import com.aoyouer.noobserver.entitiy.Post;
import com.aoyouer.noobserver.entitiy.Topic;
import com.aoyouer.noobserver.entitiy.User;
import com.aoyouer.noobserver.entitiy.UserPage;
import com.aoyouer.noobserver.repository.PostRepository;
import com.aoyouer.noobserver.repository.TopicRepository;
import com.aoyouer.noobserver.service.PostService;
import com.aoyouer.noobserver.service.TopicService;
import com.aoyouer.noobserver.service.UserService;
import com.aoyouer.noobserver.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/api")
public class PostController {

    @Resource
    TopicService topicService;

    @Resource
    PostService postService;

    @Resource
    UserService userService;

    //申请主题帖的列表
    @GetMapping(value = "/post/getlist")
    public Response getPostsByTopic(@RequestParam int pageSize,@RequestParam int pageNum
            , @RequestParam long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        if (topic == null) {
            return new Response(201, "该主题帖id不存在");
        } else {
            return new Response(200, topic);
        }
    }

    @PostMapping(value = "/post/addpost")
    public Response addPost(@RequestBody Post post) {
        Topic topic = topicService.getTopicById(post.getTopicId());
        if (userService.getUserById(post.getUserId()) == null) {
            return new Response(202, "没有检测到您的用户id，请登录");
        } else if (topic == null) {
            return new Response(203, "你回复的主题帖id有问题。");
        }
        topic.addPost(post);
        topic.setUpdateTime(post.getTime());
        topicService.saveTopic(topic);
        return new Response(200, topic);
    }

    @GetMapping(value = "/post/deletepost")
    public Response deleteTopic(@RequestParam long userId,@RequestParam long postId) {
        Post post = postService.getPostById(postId);
        if (post.getUserId() != userId) {
            return new Response(202, "您没有删除该评论的权限。");
        }
        try {
            postService.deleteById(postId);
            return new Response(200, "删帖成功");
        } catch (Exception e) {
            return new Response(203, e.getMessage());
        }
    }

    @GetMapping(value = "/post/getlastpost")
    public Response getLastPost() {
        try {
            return new Response(200, postService.getLastPost().getContent());
        } catch (Exception e) {
            return new Response(400, e.getMessage());
        }
    }

    //得到用户信息
    @GetMapping(value = "/getuserinfo")
    public Response getUserInfo(@RequestParam long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new Response(202, "该用户不存在");
        }
        int postNum = postService.getPostsByUserId(userId).size();
        int topicNum = topicService.getTopicsByUserId(userId).size();
        int likeNum = postService.getLikeNumByUserId(userId);
        return new Response(200, new UserPage(topicNum, postNum, likeNum, user));
    }

    //点赞功能
    @GetMapping(value = "/post/addlike")
    public Response addLike(@RequestParam long userId, @RequestParam long postId) {
        int likeNum = postService.getPostById(postId).getLikeNum();
        int curLikeNum;
        try {
            curLikeNum = postService.likePost(userId, postId);
        } catch (Exception e) {
            return new Response(203, e.getMessage());
        }
        if (likeNum < curLikeNum) {
            return new Response(200, curLikeNum);
        } else if (likeNum > curLikeNum) {
            return new Response(201, curLikeNum);
        } else {
            return new Response(204, "点赞没有反应，数据错误");
        }
    }

//    @GetMapping(value = "/post/searchpost")
//    public Response searchPost(@RequestParam long userId)
}
