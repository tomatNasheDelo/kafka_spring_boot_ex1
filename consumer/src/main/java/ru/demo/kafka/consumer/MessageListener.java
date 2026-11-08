package ru.demo.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @KafkaListener(topics = "${app.kafka.topic}")
    public void listen(String message) {
        System.out.println("Получено из Kafka: " + message);
    }
}
