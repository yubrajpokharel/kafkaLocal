package com.yubraj.tools.kafkaLocal.controller;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.apache.kafka.clients.consumer.OffsetResetStrategy.EARLIEST;import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.yubraj.tools.kafkaLocal.services.KafkaService;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;import org.apache.kafka.clients.consumer.OffsetResetStrategy;import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.ConsumerFactory;import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
@Slf4j
public class TopicController {

  private final KafkaService kafkaService;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ConsumerFactory<String, String> consumerFactory;


  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServer;

  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

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
      props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, EARLIEST);
      AdminClient adminClient = AdminClient.create(props);

      // Create the new topic
      adminClient
          .createTopics(Collections.singleton(new NewTopic(topicName, 1, (short) 1)))
          .all()
          .get();

      return ResponseEntity.ok("Topic created successfully");
    } catch (Exception e) {
      log.error("Exception while creating topic: {}", getMessage(e));
      return ResponseEntity.status(INTERNAL_SERVER_ERROR)
          .body("Error creating topic: " + e.getMessage());
    }
  }

  @PostMapping("/message/send")
  public ResponseEntity<String> sendMessage(
      @RequestParam String topic, @RequestBody String message) {
    try {
      // Send the message to the specified topic
      kafkaTemplate.send(topic, message);

      return ResponseEntity.ok("Message sent successfully");
    } catch (Exception e) {
      log.error("Error sending message to : {}, message: {}", topic, message);
      return ResponseEntity.status(INTERNAL_SERVER_ERROR)
          .body("Error sending message: " + e.getMessage());
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
