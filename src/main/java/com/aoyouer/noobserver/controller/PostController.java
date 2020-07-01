package com.aoyouer.noobserver.controller;

import com.aoyouer.noobserver.entitiy.Post;
import com.aoyouer.noobserver.entitiy.Topic;
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
        return new Response(200, topic);
    }
}
