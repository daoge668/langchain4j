package com.itheima.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Langchain4jApplication {

    public static void main(String[] args) {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://160.202.237.247:3002/v1")
                .apiKey("sk-USKUo0wJMX6Vcm6g6Nr5YOv6hqE1nHtkVA7rwRqQv3Qr1VDE")
                .modelName("gemini-2.5-pro-free")
                .build();
        String result = model.chat("你好");
        System.out.println(result);
    }

}
