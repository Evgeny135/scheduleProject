package org.application.controllers;

import org.application.dao.SubjectDao;
import org.application.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/subject")
public class SubjectsController {
    @Autowired
    private SubjectDao subjectDao;

    @GetMapping("/countSub")
    public String getDayOfWeekBySubjectCount(@RequestParam("countSub") int count, Model model){
        model.addAttribute("days",subjectDao.getDayOfWeekFromSubjectCount(count));

        return "subjects/countDays";
    }

    @GetMapping("/subForm")
    public String formClass(Model model){
        return "/subjects/formSub";
    }

    @GetMapping("/classForm")
    public String classForm(Model model){
        return "/subjects/formClass";
    }

    @GetMapping("/countClassRoom")
    public String getDayOfWeekByClassroomCount(@RequestParam("count") int count, Model model){
        model.addAttribute("days",subjectDao.getDayCountsClassroom(count));

        return "subjects/countClass";
    }


}
