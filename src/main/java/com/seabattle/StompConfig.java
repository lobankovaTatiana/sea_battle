package com.seabattle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic")
                .setTaskScheduler(heartBeatScheduler())
                .setHeartbeatValue(new long[]{25000, 25000});
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").setAllowedOriginPatterns("*")
                .withSockJS()
                .setClientLibraryUrl("https://cdn.jsdelivr.net/npm/sockjs-client@1.4.0/dist/sockjs.min.js")
                .setWebSocketEnabled(false)
                .setSessionCookieNeeded(false)
                .setDisconnectDelay(10000)
                .setHeartbeatTime(25000);
    }

    @Bean
    public TaskScheduler heartBeatScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
