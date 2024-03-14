package ru.teamscore.java23.springweb.meetings.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.teamscore.java23.springweb.meetings.entities.Meeting;
import ru.teamscore.java23.springweb.meetings.entities.Person;
import ru.teamscore.java23.springweb.meetings.services.MeetingsService;

import java.util.UUID;

@Controller
@RequestMapping(value={"", "/", "/meetings"})
@RequiredArgsConstructor
public class MeetingsController {
    // @Autowired необязателен, т.к. входит в @RequiredArgsConstructor
    private final MeetingsService meetingsService;

    @ModelAttribute("participantsAll")
    public Person[] populateParticipants() {
        return meetingsService.getParticipantsAll();
    }

    @GetMapping
    public String getMeetings(Model model) {
        var meetings = meetingsService.getMeetingsAll();
        model.addAttribute("meetings", meetings);
        return "index";
    }

    @GetMapping("form")
    public String newMeeting(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("newParticipant", new Person());
        return "form";
    }

    @GetMapping("form/{id}")
    public String editMeeting(@PathVariable UUID id, Model model) {
        model.addAttribute("action", "update");
        var meeting = meetingsService.getMeeting(id);
        if (meeting.isEmpty()) {
            new ModelAndView("errors/404", HttpStatus.NOT_FOUND);
        }
        model.addAttribute("meeting", meeting.get());
        model.addAttribute("newParticipant", new Person());
        return "form";
    }

    @RequestMapping(value="form", params={"addParticipant"})
    public String addParticipant(@ModelAttribute("meeting") Meeting meeting,
                                 @RequestParam String addParticipantName,
                                 @RequestParam String addParticipantEmail,
                                 BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "form";
        }
        meeting.getParticipants().add(new Person(addParticipantName, addParticipantEmail));
        model.addAttribute("newParticipant", new Person());
        return "form";
    }

    @RequestMapping(value="form", params={"removeParticipant"})
    public String removeParticipant(@ModelAttribute("meeting") Meeting meeting,
                                    @RequestParam String removeParticipant,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }
        meeting.removeParticipant(removeParticipant);
        return "form";
    }

    @PostMapping("form")
    public String createMeeting(@Valid Meeting meeting, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "form";
        }
        var exisitngMeeting = meetingsService.getMeeting(meeting.getId());
        if (exisitngMeeting.isPresent()) {
            meetingsService.removeMeeting(exisitngMeeting.get());
        }
        meetingsService.addMeeting(meeting);
        model.clear();
        return  "redirect:/meetings";
    }

    @RequestMapping("delete/{id}")
    public String deleteMeeting(@PathVariable UUID id) {
        meetingsService.removeMeeting(id);
        return  "redirect:/meetings";
    }
}
