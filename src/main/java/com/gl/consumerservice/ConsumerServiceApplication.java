package com.gl.consumerservice;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@SpringBootApplication
public class ConsumerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerServiceApplication.class, args);
    }

}

@RestController
class ConsumerController {

    @GetMapping("/allEmpAsString/{id}")
    public String consumeAllEmps(@PathVariable long id) {
        var emp = RestClient
                .create("http://localhost:8080/emps/" + id)
                .get()
                .retrieve()
                .body(String.class);

        return emp;
    }

    @GetMapping("/allEmpObject/{id}")
    public Emp allEmpObject(@PathVariable long id) {
        Emp emp = RestClient
                .create("http://localhost:8080/emps/" + id)
                .get()
                .retrieve()
                .body(Emp.class);

        return emp;
    }

    @GetMapping("/allEmps")
    public Object[] consumeAllEmps() {
        var allEmps = RestClient
                .create("http://localhost:8080/emps")
                .get()
                .retrieve()
                .body(List.class);

        return new Object[]{allEmps};
    }

    @GetMapping("/allDepts")
    public Object[] allDepts() {
        var allDepts = RestClient
                .create("http://localhost:8081/depts")
                .get()
                .retrieve()
                .body(List.class);

        return new Object[]{allDepts};
    }

    @GetMapping("/allEmpsAndDepts")
    public Object[] allEmpsAndDepts() {
        Object[] objects = new Object[2];
        try {
            var allEmps = RestClient
                    .create("http://localhost:8080/emps")
                    .get()
                    .retrieve()
                    .body(List.class);
            objects[0] = allEmps;
        } catch (Exception e) {
        }

        try {
            var allDepts = RestClient
                    .create("http://localhost:8081/depts")
                    .get()
                    .retrieve()
                    .body(List.class);
            objects[1] = allDepts;
        } catch (Exception e) {
        }

        return objects;
    }

    @GetMapping("/allEmpsAndDeptsToEntity")
    public Object[] allEmpsAndDeptsToEntity() {

        var allEmps = RestClient
                .create("http://localhost:8080/emps")
                .get()
                .retrieve()
                .toEntity(List.class);

        var allDepts = RestClient
                .create("http://localhost:8081/depts")
                .get()
                .retrieve()
                .toEntity(List.class);

        return new Object[]{allEmps, allDepts};
    }
}

@Data
class Emp {
    long id;
    String name;
}