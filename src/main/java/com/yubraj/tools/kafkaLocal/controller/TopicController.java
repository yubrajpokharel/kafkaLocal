package com.yubraj.tools.kafkaLocal.controller;

import com.yubraj.tools.kafkaLocal.services.KafkaService;import org.apache.kafka.clients.admin.AdminClient;import org.apache.kafka.clients.admin.AdminClientConfig;import org.apache.kafka.clients.admin.NewTopic;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;import java.util.Properties;import java.util.Set;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired private KafkaService kafkaService;

    @GetMapping
    public Set<String> getTopics() {
        return kafkaService.getTopics();
    }

  @PostMapping
  public ResponseEntity<String> createTopic(@RequestBody TopicRequest topicRequest) {
        String topicName = topicRequest.getName();

        try {
            // Create a new Kafka admin client with the default configuration
            Properties props = new Properties();
            props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
            AdminClient adminClient = AdminClient.create(props);

            // Create the new topic
            adminClient.createTopics(Collections.singleton(new NewTopic(topicName, 1, (short) 1))).all().get();

            return ResponseEntity.ok("Topic created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating topic: " + e.getMessage());
        }
    }

    public static class TopicRequest {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
