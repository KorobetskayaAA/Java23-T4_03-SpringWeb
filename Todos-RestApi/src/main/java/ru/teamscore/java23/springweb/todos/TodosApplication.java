package ru.teamscore.java23.springweb.todos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.teamscore.java23.springweb.todos.entity.Todo;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TodosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodosApplication.class, args);
    }

    @Bean("mockTodos")
    public List<Todo> mockTodos() {
        List<Todo> todos = new ArrayList<>();
        todos.add(new Todo("Подготовить лекцию по Java Script"));
        todos.add(new Todo("Починить ноутбук"));
        todos.add(new Todo("Подготовить лекцию по Spring Framework"));
        return todos;
    }

}
