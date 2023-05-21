package org.application.controllers;

import org.application.dao.TeacherDao;
import org.application.models.DayOfWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teacher")
public class TeachersController {
    @Autowired
    private TeacherDao teacherDao;

    @GetMapping("/formWorkDay")
    public String fromWorkDay(Model model){
        model.addAttribute("days",teacherDao.getDays());
        model.addAttribute("classrooms",teacherDao.getClassRooms());
        return "/teachers/teacherForm";
    }

    @GetMapping
    public String formNotWorkDay(Model model){
        model.addAttribute("days",teacherDao.getDays());
        return "/teachers/formNotWorkDay";
    }

    @GetMapping("/workDay")
    public String getTeacherDayAndClassRoom(Model model,@RequestParam("day") String day, @RequestParam("class") int classroom){
        DayOfWeek dayOfWeek = DayOfWeek.getDayByName(day);
        model.addAttribute("teachers",teacherDao.getInfoTeacherWorkInDayAndClassroom(dayOfWeek, classroom));

        return "/teachers/workDay";
    }

    @GetMapping("/notWorkDay")
    public String getTeacherNotWorkInDay(@RequestParam("day") String day, Model model){
        DayOfWeek dayOfWeek = DayOfWeek.getDayByName(day);
        model.addAttribute("teachers",teacherDao.getInfoTeacherNotWorkThisDayOfWeek(dayOfWeek));

        return "/teachers/notWorkDay";
    }

}
