package com.murzin.chatspring.server.config;

import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Класс для загрузки зарегистрированных пользователей.
 * Файл для хранения пользователей resources/users.properties
 */
@Controller
public class AllowedUsers {
    private Set<String> usersName = new HashSet<>();

    @PostConstruct
    void init(){
        try(FileInputStream fis = new FileInputStream("src/main/resources/users.properties")) {
            Properties property = new Properties();
            property.load(fis);

            for (Object s : property.keySet()) {
                usersName.add((String)s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getUsersName() {
        return Collections.unmodifiableSet(usersName);
    }
}
