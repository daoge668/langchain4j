package com.itheima.langchain4j.Controller;


import com.itheima.langchain4j.aiService.ConsultantService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {
    @Autowired
    private ConsultantService consultantService;

    @GetMapping("/chat")
    public String chat(@RequestParam String message){
        String result = consultantService.chat(message);
        return result;
    }
    @GetMapping(value = "/chatFlux",produces = "text/html;charset=utf-8")
    public Flux<String> chat(String memoryId, String message){
        Flux<String> result = consultantService.chatFlux(message);
        return result;
    }

}
