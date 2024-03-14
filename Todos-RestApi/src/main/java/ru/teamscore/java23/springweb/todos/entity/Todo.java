package ru.teamscore.java23.springweb.todos.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Todo {
    @NonNull
    private UUID id = UUID.randomUUID();
    @NonNull
    @EqualsAndHashCode.Include
    private String text;
    private LocalDateTime created = LocalDateTime.now();
    private boolean done = false;
}
