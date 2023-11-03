package org.example;

import entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class App {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                null, String.class);
        String cookie = responseEntity.getHeaders().getFirst("Set-Cookie");
        System.out.println(cookie);

//Сохранить пользователя с id = 3, name = James, lastName = Brown, age = на ваш выбор.
        User user = new User(3L, "James", "Brown", (byte)28);

        //Создаем headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);

        //Создаем запрос(requestEntity)
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        //отправляем Post request
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        //проверяем response
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

//изменяем пользователя
// Изменить пользователя с id = 3. Необходимо поменять name на Thomas, а lastName на Shelby.

        User userPut = new User(3L, "Thomas", "Shelby", (byte)28);
        //Создаем запрос(requestEntity)
        HttpEntity<User> entityPut = new HttpEntity<>(userPut, headers);

        //отправляем Put запрос
        ResponseEntity<String> responseEntityPut = restTemplate.exchange(url, HttpMethod.PUT,
                entityPut, String.class);

        //проверяем response
        System.out.println();
        System.out.println(responseEntityPut.getStatusCode());
        System.out.println(responseEntityPut.getBody());

//Удалить пользователя с id = 3
//Удаление пользователя - …/api/users /{id} ( DELETE )

        //Создаем запрос(requestEntity)
        HttpEntity<Object> entityDelete = new HttpEntity<>(headers);

        //отправляем Delete запрос
        ResponseEntity<String> responseEntityDelete = restTemplate.exchange(url+"/"+3, HttpMethod.DELETE,
                entityDelete, String.class);

        //проверяем response
        System.out.println();
        System.out.println(responseEntityDelete.getStatusCode());
        System.out.println(responseEntityDelete.getBody());

        //получаем ответ
        System.out.println();
        System.out.println(response.getBody()+responseEntityPut.getBody()+responseEntityDelete.getBody());
    }
}