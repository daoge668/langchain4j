package com.itheima.langchain4j.Config;

import com.itheima.langchain4j.aiService.ConsultantService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    //构建向量数据库操作对象
    @Bean
    public EmbeddingStore store(){//embeddingStore的对象, 这个对象的名字不能重复,所以这里使用store
        //1.加载文档进内存，从类路径下的 "content" 目录加载所有文档 将加载的文档存储在 documents 列表中
        List<Document> documents = ClassPathDocumentLoader.loadDocuments("content");
        //2.创建一个内存中的向量存储实例 提供存储向量数据的容器
        InMemoryEmbeddingStore store = new InMemoryEmbeddingStore();

        //3.构建一个EmbeddingStoreIngestor对象,完成文本数据切割,向量化, 存储
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(store)//为构建器设置之前创建的向量存储对象 告诉处理器要将向量存储到哪里
                .build();//创建 EmbeddingStoreIngestor 实例
        ingestor.ingest(documents);//完成从原始文档到向量数据的转换和存储
        return store;//返回配置好的向量存储对象 使其成为 Spring 容器中的 Bean
    }
    //构建向量数据库检索对象
    @Bean
    //定义一个返回 ContentRetriever 的方法，方法参数中注入了之前创建的EmbeddingStore对象 创建用于检索向量数据的对象
    public ContentRetriever contentRetriever(EmbeddingStore store){
        return EmbeddingStoreContentRetriever.builder()//创建检索器的构建器
                .embeddingStore(store)//设置检索器要使用的向量存储对象，告诉检索器从哪里获取数据
                .minScore(0.5)//设置检索 最小相似度 0.5
                .maxResults(3)//设置最多返回 3 条检索结果。控制返回结果的数量
                .build();//完成构建，返回配置好的 ContentRetriever 对象
    }

}
