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
        int lastPage;
        if (topicService.getAllByType(type).size() % pageSize == 0) {
            lastPage = topicService.getAllByType(type).size() / pageSize;
        } else {
            lastPage = topicService.getAllByType(type).size() / pageSize + 1;
        }
        try {
            return new Response(200,
                    new TopicPage(pageSize, pageNum, lastPage, page.getContent()));
        } catch (Exception e) {
            return new Response(400, "页面获取失败");
        }
    }

    @GetMapping(value = "/topic/list")
    public Response getTopicList(@RequestParam int pageSize,@RequestParam int pageNum) {
        Page<Topic> page = topicService.getTopicsByPage(pageSize, pageNum);
        int lastPage;
        if (topicService.getAll().size() % pageSize == 0) {
            lastPage = topicService.getAll().size() / pageSize;
        } else {
            lastPage = topicService.getAll().size() / pageSize + 1;
        }
        try {
            return new Response(200,
                    new TopicPage(pageSize, pageNum, lastPage, page.getContent()));
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
        topic.setCreateTime(post.getTime());
        topic.setUpdateTime(post.getTime());
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

    @GetMapping(value = "/topic/deletetopic")
    public Response deleteTopic(@RequestParam long userId,@RequestParam long topicId) {
        Topic topic = topicService.getTopicById(topicId);
        if (topic.getUserId() != userId) {
            return new Response(202, "您没有删除该贴的权限。");
        }
        try {
            topicService.deleteById(topicId);
            return new Response(200, "删帖成功");
        } catch (Exception e) {
            return new Response(203, e.getMessage());
        }
    }

    @GetMapping(value = "/topic/listbytag")
    public Response deleteTopic(@RequestParam int pageSize,@RequestParam int pageNum
            , @RequestParam String tag) {
        try {
            return new Response(200, topicService.getTopicByTag(pageSize, pageNum, tag));
        } catch (ArrayIndexOutOfBoundsException e) {    //我在这里已进行错误处理
            return new Response(203, e.getMessage());
        }
    }

}
