package ru.teamscore.java23.springweb.todos.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.springweb.todos.entity.Todo;
import ru.teamscore.java23.springweb.todos.service.TodoService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    @GetMapping({"", "/", "/list"})
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        var todo = todoService.get(id);
        if (todo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @GetMapping("index/{index}")
    public ResponseEntity<?> get(@PathVariable String index) {
        var todo = todoService.get(Integer.parseInt(index));
        if (todo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PostMapping
    public Todo add(@RequestBody Todo todo) {
        return todoService.add(todo);
    }


    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody Todo todo) {
        var result = todoService.update(todo);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("reorder")
    public List<Todo> reorder(@RequestBody UUID[] todoIds) {
        return todoService.reorder(Arrays.stream(todoIds).toList());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        var todo = todoService.remove(id);
        if (todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }
}
