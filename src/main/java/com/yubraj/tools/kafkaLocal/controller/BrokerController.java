package com.yubraj.tools.kafkaLocal.controller;

import com.yubraj.tools.kafkaLocal.services.KafkaService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.Node;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brokers")
@RequiredArgsConstructor
public class BrokerController {
  private final KafkaService kafkaService;

  @GetMapping
  public Collection<Node> getBrokers() {
    return kafkaService.getBrokers();
  }
}
