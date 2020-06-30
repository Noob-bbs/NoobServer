package com.aoyouer.noobserver.controller;

import com.aoyouer.noobserver.entitiy.Post;
import com.aoyouer.noobserver.entitiy.Topic;
import com.aoyouer.noobserver.entitiy.TopicPage;
import com.aoyouer.noobserver.service.TopicService;
import com.aoyouer.noobserver.service.UserService;
import com.aoyouer.noobserver.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping(path = "/topic")
public class TopicController {
    @Resource
    TopicService topicService;

    @Resource
    UserService userService;

    @PostMapping(value = "/list")
    public Response show(@RequestParam int pageSize,@RequestParam int pageNum
            ,@RequestParam String type) {
        Page<Topic> page = topicService.getTopicsByTypeAndPage(pageSize, pageNum, type);
        try {
            return new Response(200,
                    new TopicPage(pageSize, pageNum, page.getContent()));
        } catch (Exception e) {
            return new Response(400, "页面获取失败");
        }
    }

    @PostMapping(value = "/create")
    public Response createTopic(@RequestParam long userId, @RequestParam String title,
                                @RequestParam String content, @RequestParam Set<String> tags,
                                @RequestParam String type) {
        if (userService.getUserById(userId) == null) {
            return new Response(202, "没有检测到您的用户id，请登录");
        }
        Topic topic = new Topic(title, type, tags);
        Post post = new Post(content, System.currentTimeMillis(),
                0, userService.getUserById(userId));
        topic.addPost(post);
        topicService.saveTopic(topic);
        return new Response(200, "发帖成功");
    }
}
