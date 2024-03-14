package ru.teamscore.java23.springweb.meetings.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Meeting - Собрание
 */
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Meeting {
    @EqualsAndHashCode.Include
    private UUID id = UUID.randomUUID();

    @NotEmpty(message = "Название обязательно")
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "Выберите дату и время начала собрания")
    private LocalDateTime from;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @NotNull(message = "Выберите дату и время окончания собрания")
    private LocalDateTime till;
    private String url;
    private String location;

    public Duration getDuration() {
        return Duration.between(getFrom(), getTill());
    }

    public Temporal getDurationTime() {
        return getDuration().addTo(java.time.LocalTime.MIDNIGHT);
    }

    public void setDuration(Duration duration) {
        setTill(getFrom().plus(duration));
    }

    private Person creator;
    private final List<Person> participants = new ArrayList<>();

    public void removeParticipant(String email) {
        var participant = participants.stream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst();
        if (participant.isPresent()) {
            participants.remove(participant.get());
        }
    }
}
