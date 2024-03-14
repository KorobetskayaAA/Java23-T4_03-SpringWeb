package ru.teamscore.java23.springweb.todos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.teamscore.java23.springweb.todos.entity.Todo;

import java.util.*;

@Service
public class TodoService {
    @Autowired
    @Qualifier("mockTodos")
    private final List<Todo> todos = new ArrayList<>();

    public List<Todo> getAll() {
        return todos.stream().toList();
    }

    public Optional<Todo> get(UUID id) {
        return todos.stream()
                .filter(td -> td.getId().equals(id))
                .findFirst();
    }


    public Optional<Todo> get(int index) {
        return index >= 0 && index < todos.size()
                ? Optional.of(todos.get(index))
                : Optional.empty();
    }

    public Todo add(Todo todo) {
        todos.add(todo);
        return todo;
    }

    public Optional<Todo> update(Todo todo) {
        var existingTodo = get(todo.getId());
        if (existingTodo.isPresent()) {
            existingTodo.get().setText(todo.getText());
            existingTodo.get().setDone(todo.isDone());
        }
        return existingTodo;
    }

    public Todo remove(UUID id) {
        var existingTodo = get(id);
        if (existingTodo.isPresent()) {
            Todo todo = existingTodo.get();
            todos.remove(todo);
            return todo;
        }
        return null;
    }

    public Todo remove(Todo todo) {
        return remove(todo.getId());
    }

    public List<Todo> reorder(List<UUID> orderedIds) {
        todos.sort(Comparator.comparingInt(todo -> Optional
                .of(orderedIds.indexOf(todo.getId()))
                .orElse(Integer.MAX_VALUE) //Если todo отсутствует в отсортированном списке, ставим в конец
        ));
        return getAll();
    }
}
