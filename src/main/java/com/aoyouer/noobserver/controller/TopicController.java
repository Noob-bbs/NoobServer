package com.aoyouer.noobserver.controller;

import com.aoyouer.noobserver.entitiy.Post;
import com.aoyouer.noobserver.entitiy.Topic;
import com.aoyouer.noobserver.entitiy.TopicPage;
import com.aoyouer.noobserver.entitiy.User;
import com.aoyouer.noobserver.service.TopicService;
import com.aoyouer.noobserver.service.UserService;
import com.aoyouer.noobserver.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/api")
public class TopicController {
    @Resource
    TopicService topicService;

    @Resource
    UserService userService;

    @GetMapping(value = "/topic/listbytype")
    public Response getListByType(@RequestParam int pageSize,@RequestParam int pageNum
            ,@RequestParam String type) {
        Page<Topic> page = topicService.getTopicsByTypeAndPage(pageSize, pageNum, type);
        try {
            return new Response(200,
                    new TopicPage(pageSize, pageNum, page.getContent()));
        } catch (Exception e) {
            return new Response(400, "页面获取失败");
        }
    }

    @GetMapping(value = "/topic/list")
    public Response getTopicList(@RequestParam int pageSize,@RequestParam int pageNum) {
        Page<Topic> page = topicService.getTopicsByPage(pageSize, pageNum);
        try {
            return new Response(200,
                    new TopicPage(pageSize, pageNum, page.getContent()));
        } catch (Exception e) {
            return new Response(400, "页面获取失败");
        }
    }

    //得到所有标签以及每个标签之下的主题数量
    @GetMapping(value = "/gettags")
    public Response getTagsMap() {
        try {
            return new Response(200, topicService.getTagsInfo());
        } catch (Exception e) {
            return new Response(400, e.getMessage());
        }
    }

    //新建主题帖
    @PostMapping(value = "/topic/create")
    public Response createTopic(@RequestBody Topic topic) {
        if (userService.getUserById(topic.getUserId()) == null) {
            return new Response(202, "没有检测到您的用户id，请登录");
        }
        User user = userService.getUserById(topic.getUserId());
        Post post = new Post(topic.getContent(), System.currentTimeMillis(),
                0, topic.getUserId(), user.getAccount()
                , user.getNick(), user.getAvatarUrl(), topic.getId());
        topic.addPost(post);
        topicService.saveTopic(topic);
        return new Response(200, "发帖成功");
    }

    //得到主题帖数量
    @GetMapping(value = "/topic/getpostsnum")
    public Response getPostNum() {
        return new Response(200, topicService.getTopicListSize());
    }

//    //得到标签数量
//    @GetMapping(value = "/getTagNum")
//    public Response getTagNum() {
//        return new Response(200, topicService.getTopicListSize());
//    }
}
