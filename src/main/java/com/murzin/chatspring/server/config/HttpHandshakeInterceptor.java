package com.murzin.chatspring.server.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Класс для проверки регистрации пользователя.
 * Если пользователь не зарегистрирован, соединение разрывается,
 * с HTTP статусом BAD_GATEWAY
 */
@Component
public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    @Autowired
    private AllowedUsers users;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map attributes) throws Exception {
        HttpHeaders header = request.getHeaders();
        String login = header.get("login").get(0);
        attributes.put("login", login);
        for (String allowedName : users.getUsersName()) {
            if(allowedName.equals(login)) {
                return true;
            }
        }
        response.setStatusCode(HttpStatus.BAD_GATEWAY);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
    }
}
