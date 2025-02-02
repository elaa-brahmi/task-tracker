package com.example.task_tracker.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE+99)//This is useful when multiple configurations exist, ensuring this one has a very high priority
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    private final ApplicationContext applicationContext;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user");//Enables a simple message broker with the /user destination prefix for broadcasting messages (like notifications).
        registry.setApplicationDestinationPrefixes("/app");// Clients must use this prefix when sending messages.
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")//Defines /ws as the WebSocket connection endpoint for clients to connect.

                .setAllowedOrigins("http://localhost:4200")
                .withSockJS();
    }

}