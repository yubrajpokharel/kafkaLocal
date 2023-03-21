package com.yubraj.tools.kafkaLocal.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
  @GetMapping("/")
  public String index(Model model) {
        return "topic";
    }

  @RequestMapping(value = "/topic/{topic}", method = GET)
  public String getMessage(@PathVariable("topic") String topic, Model model) {
    System.out.println(topic);
    model.addAttribute("topicName", topic);
    return "individualTopic";
  }
}
