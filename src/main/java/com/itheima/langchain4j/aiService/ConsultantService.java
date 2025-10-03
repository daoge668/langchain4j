package com.itheima.langchain4j.aiService;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,//手动装配
        chatModel = "openAiChatModel",//指定模型
        streamingChatModel = "openAiStreamingChatModel"
)

public interface ConsultantService {

    @SystemMessage(fromResource = "system.txt")
    @UserMessage("多说一点,以下是问题：{{message}}")
    public String chat(String message);

    @SystemMessage(fromResource = "system.txt")
    @UserMessage("多说一点,以下是问题：{{message}}")
    public Flux<String> chatFlux(@V("message") String message);

}
