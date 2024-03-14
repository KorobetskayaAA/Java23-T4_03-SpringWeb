package ru.teamscore.java23.springweb.meetings.services;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ru.teamscore.java23.springweb.meetings.entities.Meeting;
import ru.teamscore.java23.springweb.meetings.entities.Person;
import ru.teamscore.java23.springweb.meetings.mock.MeetingsMock;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
//@SessionScope если вкл., у каждого пользователя будет свой набор созвонов
public class MeetingsService {
    private Set<Meeting> meetings = Arrays.stream(MeetingsMock.getMeetings())
            .collect(Collectors.toSet());

    public Meeting[] getMeetingsAll() {
        return meetings.stream()
                .sorted(Comparator.comparing(Meeting::getFrom))
                .toArray(Meeting[]::new);
    }

    public Meeting[] getMeetings(
            Person participant,
            LocalDateTime fromDate,
            LocalDateTime toDate
    ) {
        var stream = meetings.stream();
        if (participant != null) {
            stream = stream
                    .filter(m -> m.getParticipants().contains(participant));
        }
        if (fromDate != null) {
            stream = stream
                    .filter(m -> m.getTill().isAfter(fromDate));
        }
        if (toDate != null) {
            stream = stream
                    .filter(m -> m.getFrom().isBefore(toDate));
        }
        return stream
                .sorted(Comparator.comparing(Meeting::getFrom))
                .toArray(Meeting[]::new);
    }

    public Optional<Meeting> getMeeting(UUID id) {
        return meetings.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public Meeting addMeeting(Meeting newMeeting) {
        meetings.add(newMeeting);
        return newMeeting;
    }

    public void removeMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    public Meeting removeMeeting(UUID meetingId) {
        var meetingToRemove = getMeeting(meetingId);
        if (meetingToRemove.isEmpty()) {
            return null;
        }
        removeMeeting(meetingToRemove.get());
        return meetingToRemove.get();
    }

    private Stream<Person> getParticipantsStream() {
        return meetings.stream()
                .flatMap(m -> m.getParticipants().stream())
                .distinct();
    }

    public Person[] getParticipantsAll() {
        return getParticipantsStream()
                .sorted(Comparator.comparing(Person::getEmail))
                .toArray(Person[]::new);
    }

    public Person getParticipant(String email) {
        return getParticipantsStream()
                .filter(p -> p.getEmail().equals(email))
                .findFirst()
                .get();
    }
}
