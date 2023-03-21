package com.yubraj.tools.kafkaLocal.controller;

import com.yubraj.tools.kafkaLocal.services.KafkaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumers")
@RequiredArgsConstructor
public class ConsumerController {

  private final KafkaService kafkaService;

  @GetMapping
  public List<String> getConsumerGroups() {
    return kafkaService.getConsumerGroups();
  }
}
