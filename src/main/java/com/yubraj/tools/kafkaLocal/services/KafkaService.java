package com.yubraj.tools.kafkaLocal.services;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import java.util.Collection;import java.util.List;
import java.util.Properties;
import java.util.Set;import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.kafka.clients.admin.ConsumerGroupListing;

@Service
public class KafkaService {

  @Autowired
  private KafkaAdmin kafkaAdmin;

  public Set<String> getTopics() {
    Properties props = new Properties();
    props.putAll(kafkaAdmin.getConfigurationProperties());
    try (AdminClient adminClient = AdminClient.create(props)) {
      ListTopicsResult topicsResult = adminClient.listTopics();
      return topicsResult.names().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Failed to get topics", e);
    }
  }

  public Collection<Node> getBrokers() {
    Properties props = new Properties();
    props.putAll(kafkaAdmin.getConfigurationProperties());
    try (AdminClient adminClient = AdminClient.create(props)) {
      DescribeClusterResult clusterResult = adminClient.describeCluster();
      return clusterResult.nodes().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Failed to get brokers", e);
    }
  }

  public List<String> getConsumerGroups() {
    Properties props = new Properties();
    props.putAll(kafkaAdmin.getConfigurationProperties());
    try (AdminClient adminClient = AdminClient.create(props)) {
      ListConsumerGroupsResult consumerGroupsResult = adminClient.listConsumerGroups();
      return consumerGroupsResult.all()
              .get()
              .stream()
              .map(ConsumerGroupListing::groupId)
              .collect(Collectors.toList());
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Failed to get consumer groups", e);
    }
  }
}

