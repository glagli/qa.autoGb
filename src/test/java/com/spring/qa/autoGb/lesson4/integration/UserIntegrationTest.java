package com.spring.qa.autoGb.lesson4.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.qa.autoGb.lesson2.example.dto.User;
import com.spring.qa.autoGb.lesson4.entity.UserEntity;
import com.spring.qa.autoGb.lesson4.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void saveUserTest() throws Exception {
        //pre-condition
        User user = new User();
        user.setAge(50);
        user.setFirstName("First Name");
        user.setSecondName("Second Name");

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(user)))
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //step 2
        UserEntity userEntity = userRepository.findAll().stream()
                .findFirst()
                .orElseThrow();

        //assert
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(userEntity.getFirstName()).isEqualTo(user.getFirstName());
            s.assertThat(userEntity.getSecondName()).isEqualTo(user.getSecondName());
            s.assertThat(userEntity.getAge()).isEqualTo(user.getAge());
        });
    }

    @Test
    void getUserTest() throws Exception {
        //pre-condition
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("First Name");
        userEntity.setSecondName("Second Name");
        userEntity.setAge(50);
        UserEntity entity = userRepository.saveAndFlush(userEntity);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/" + entity.getId()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .GET()
                .build();

        //step 1
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(response.statusCode()).isEqualTo(SC_OK);

        //assert
        User user = objectMapper.readValue(response.body(), User.class);
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(userEntity.getFirstName()).isEqualTo(user.getFirstName());
            s.assertThat(userEntity.getSecondName()).isEqualTo(user.getSecondName());
            s.assertThat(userEntity.getAge()).isEqualTo(user.getAge());
        });
    }

    @Test
    void updateUserTest() throws Exception {
        //pre-condition
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("First Name");
        userEntity.setSecondName("Second Name");
        userEntity.setAge(50);
        UserEntity entity = userRepository.saveAndFlush(userEntity);

        User updateUser = new User();
        updateUser.setId(entity.getId());
        updateUser.setAge(25);
        updateUser.setFirstName("Update First Name");
        updateUser.setSecondName("Update Second Name");

        HttpRequest putHttpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/"))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(updateUser)))
                .build();

        //step 1
        HttpResponse<String> putResponse = httpClient.send(putHttpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(putResponse.statusCode()).isEqualTo(SC_OK);


        HttpRequest getHttpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/" + entity.getId()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .GET()
                .build();

        //step 2
        HttpResponse<String> getResponse = httpClient.send(getHttpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after second
        // step
        Assertions.assertThat(getResponse.statusCode()).isEqualTo(SC_OK);

        //assert
        User user = objectMapper.readValue(getResponse.body(), User.class);
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(updateUser.getFirstName()).isEqualTo(user.getFirstName());
            s.assertThat(updateUser.getSecondName()).isEqualTo(user.getSecondName());
            s.assertThat(updateUser.getAge()).isEqualTo(user.getAge());
        });
    }

    @Test
    void deleteUserTest() throws Exception {
        //pre-condition
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("First Name");
        userEntity.setSecondName("Second Name");
        userEntity.setAge(50);
        UserEntity entity = userRepository.saveAndFlush(userEntity);

        HttpRequest deleteHttpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/" + entity.getId()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .DELETE()
                .build();

        //step 1
        HttpResponse<String> deleteResponse = httpClient.send(deleteHttpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after first step
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(SC_OK);


        HttpRequest getHttpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getRootUrl() + "/user-rest/" + entity.getId()))
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .GET()
                .build();

        //step 2
        HttpResponse<String> getResponse = httpClient.send(getHttpRequest, HttpResponse.BodyHandlers.ofString());

        //intermediate assert after second
        Assertions.assertThat(getResponse.statusCode()).isEqualTo(SC_INTERNAL_SERVER_ERROR);

    }
}
