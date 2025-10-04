package com.itheima.langchain4j.aiService;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,//手动装配
        chatModel = "openAiChatModel",//指定模型
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        contentRetriever = "contentRetriever"//配置向量数据库检索对象
        )

public interface ConsultantService {

    @SystemMessage(fromResource = "system.txt")
    @UserMessage("多说一点,以下是问题：{{message}}")
    public String chat(@V("message") @UserMessage String message, @MemoryId String memoryId);

    @SystemMessage(fromResource = "system.txt")
    @UserMessage("多说一点,以下是问题：{{message}}")
    public Flux<String> chatFlux(@V("message") String message,@MemoryId String memoryId);

}
