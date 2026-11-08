package ru.demo.kafka.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public MessageController(
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${app.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("messageForm", new MessageForm());
        return "index";
    }

    @PostMapping("/messages")
    public String sendMessage(@ModelAttribute MessageForm messageForm, Model model) {
        String message = messageForm.getMessage();
        if (message == null || message.isBlank()) {
            model.addAttribute("messageForm", messageForm);
            model.addAttribute("error", "Введите непустое сообщение.");
            return "index";
        }

        kafkaTemplate.send(topic, message);
        model.addAttribute("message", message);
        model.addAttribute("topic", topic);
        return "sent";
    }
}
