package com.murzin.chatspring.client;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.web.socket.sockjs.client.Transport;
import java.util.concurrent.ExecutionException;


public class EventClient {
    static String login;
    private static String address;
    private static String port;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        loadClientProperty();

        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();

        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        handshakeHeaders.add("login", login);

        StompSession session = stompClient.connect(address + ":" + port + "/chat", handshakeHeaders, sessionHandler).get();
        chat(session);
    }


    /**
     * Метод отправки сообщений пользователя.
     * Для завершение отправки сообщений вводится: exit
     * Получение всей истории сообщений: /history
     * Получение историй одного пользователя: /history/username
     */
    private static void chat(StompSession session){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))){
            Message msg = new Message();
            msg.setUser(login);
            while (true) {
                String line = in.readLine();
                if (line.equals("exit"))
                    break;
                msg.setText(line);
                if(line.equals("/history")){
                    session.send("/app/history", msg);
                } else if(line.startsWith("/history/")) {
                    String[] arr = line.split("/history/");
                    session.send("/app/history/" + arr[1], msg);
                } else {
                    session.send("/app/chat", msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //загрузка конфигурации пользователя
    static void loadClientProperty(){
        try(FileInputStream fis = new FileInputStream("src/main/resources/client.properties")) {
            Properties property = new Properties();
            property.load(fis);

            login = property.getProperty("login");
            address = property.getProperty("address");
            port = property.getProperty("port");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
