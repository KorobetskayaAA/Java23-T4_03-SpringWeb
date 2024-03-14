package ru.teamscore.java23.springweb.meetings.mock;

import ru.teamscore.java23.springweb.meetings.entities.Meeting;
import ru.teamscore.java23.springweb.meetings.entities.Person;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class MeetingsMock {
    private final static Person[] persons = new Person[] {
            new Person("a.fedorova@example.com", "Арина Фёдорова"),
            new Person("v.kataev@example.com", "Валентин Катаев"),
            new Person("i.mescheryakov@example.com", "Иван Мещеряков"),
            new Person("e.uvarova@example.com", "Эдита Уварова"),
            new Person("s.rascolnykov@example.com", "Семён Раскольников"),
            new Person("s.yakubvich@example.com", "Самсон Якубович"),

    };

    private final static Random rnd = new Random(1);

    public static Person getPerson() {
        return persons[rnd.nextInt(persons.length)];
    }

    private static LocalDateTime getStart() {
        return LocalDateTime.now()
                .plusMinutes(rnd.nextInt(-60*24*10, +60*24*10));
    }

    public static Meeting getMeeting(String title) {
        Meeting meeting = new Meeting();
        meeting.setFrom(getStart());
        meeting.setDuration(Duration.ofMinutes(30 * rnd.nextInt(1, 4)));
        meeting.setTitle(title);
        meeting.setCreator(getPerson());
        if (rnd.nextDouble() > 0.4) {
            meeting.setUrl("http://example.com/videocall");
        }
        if (rnd.nextDouble() > 0.6) {
            meeting.setLocation("Переговорка");
        }
        int participantsCount = rnd.nextInt(1, 6);
        for (int i = 0; i < participantsCount; i++) {
            meeting.getParticipants().add(getPerson());
        }
        return meeting;
    }
    public static Meeting[] getMeetings() {
        return new Meeting[]{
                getMeeting("Очень важная встреча"),
                getMeeting("Обсуждаем итоги недели"),
                getMeeting("Планерка"),
                getMeeting("Согласование правок"),
                getMeeting("Собеседование"),
                getMeeting("Результаты тестирования")
        };
    }
}
