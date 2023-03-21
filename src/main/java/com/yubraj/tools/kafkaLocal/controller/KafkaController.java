package com.yubraj.tools.kafkaLocal.controller;

import com.yubraj.tools.kafkaLocal.services.KafkaService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

  @Autowired private KafkaService kafkaService;

    @GetMapping("/brokers")
    public Collection<Node> getBrokers() {
        return kafkaService.getBrokers();
    }

    @GetMapping("/consumers")
    public List<String> getConsumerGroups() {
        return kafkaService.getConsumerGroups();
    }
}
