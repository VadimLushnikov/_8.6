package ru.example.conditional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    //final
    private static GenericContainer<?> myAppDev = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);
    @Container
    //final
    private static GenericContainer<?> myAppProd = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);
    @BeforeAll
    public static void setUp() {
        myAppDev.start();
        myAppProd.start();
    }
    @Test
    void contextLoads() {
        ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:" + myAppDev.getMappedPort(8080)+"/profile", String.class);
        System.out.println(forEntityDev.getBody());
        ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:" + myAppProd.getMappedPort(8081)+"/profile", String.class);
        System.out.println(forEntityProd.getBody());
    }

}
