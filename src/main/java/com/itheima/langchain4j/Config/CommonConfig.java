package com.itheima.langchain4j.Config;

import com.itheima.langchain4j.aiService.ConsultantService;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    @Autowired
    private ChatMemoryStore redisChatMemoryStore;
    @Autowired
    private OpenAiChatModel model;
//    @Bean
//    public ConsultantService consultantService() {
//        return AiServices.builder(ConsultantService.class)
//                .chatModel(model)
//                .build();
//
//    }
    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        ChatMemoryProvider provider = new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory
                        .builder()
                        .id(memoryId)//设置唯一标识符为传入的memoryId，这样可以区分不同的对话会话
                        .maxMessages(20)//设置消息窗口的最大容量为20条，限制对话历史的长度
                        .chatMemoryStore(redisChatMemoryStore)
                        .build();//调用构建器的build()方法，完成MessageWindowChatMemory对象的创建
            }
        };
        return provider;
    }
}
