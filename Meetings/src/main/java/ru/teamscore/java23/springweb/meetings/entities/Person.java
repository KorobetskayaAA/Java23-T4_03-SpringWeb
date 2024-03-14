package ru.teamscore.java23.springweb.meetings.entities;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {
    @NonNull
    @EqualsAndHashCode.Include
    @NotEmpty(message = "Email обязателен")
    @Email(message = "Укажите корректный email")
    private String email;
    private String name;

    @Override
    public String toString() {
        return String.format("%s <%s>", name, email);
    }
}
