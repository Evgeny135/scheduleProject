package org.application.controllers;

import org.application.dao.ScheduleDao;
import org.application.models.DayOfWeek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ScheduleController {
    @Autowired
    private ScheduleDao scheduleDao;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("info",scheduleDao.index());

        return "/schedule/index";
    }


    @PatchMapping("/update")
    public String replaceSubjectFromOneDay(Model model, @RequestParam("name")String name,
                                           @RequestParam("dayOld") String dayOld,
                                           @RequestParam("dayNew")String dayNew){

        scheduleDao.replaceSubjectsFromDayOfWeek(name, DayOfWeek.getDayByName(dayOld),DayOfWeek.getDayByName(dayNew));

        return "redirect:/";
    }

    @GetMapping("/updateForm")
    public String formReplace(Model model){
        model.addAttribute("days",scheduleDao.getDays());
        model.addAttribute("names",scheduleDao.getName());
        return "/schedule/formUpdate";
    }
}
