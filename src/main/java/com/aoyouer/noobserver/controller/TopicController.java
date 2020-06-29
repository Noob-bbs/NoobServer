package com.aoyouer.noobserver.controller;

import com.aoyouer.noobserver.entitiy.Topic;
import com.aoyouer.noobserver.entitiy.TopicPage;
import com.aoyouer.noobserver.service.TopicService;
import com.aoyouer.noobserver.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(path = "/topic")
public class TopicController {
    @Resource
    TopicService topicService;

    @PostMapping(value = "/list")
    public Response login(@RequestParam int pageSize,@RequestParam int pageNum
            ,@RequestParam String type) {
        Page<Topic> page = topicService.getTopicsByTypeAndPage(pageSize, pageNum, type);
        try {
            return new Response(200,
                    new TopicPage(pageSize, pageNum, page.getContent()));
        } catch (Exception e) {
            return new Response(400, "页面获取失败");
        }
    }
}
