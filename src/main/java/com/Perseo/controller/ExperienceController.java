package com.Perseo.controller;


import com.Perseo.model.Experience;
import com.Perseo.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/experience")
@CrossOrigin

public class ExperienceController {
    @Autowired
    ExperienceService experienceService;

    @PostMapping(path = "create")
    public Experience createExperience(@RequestBody Experience experience) {
        return experienceService.createExperience(experience);

    }

    @GetMapping(path = "")
    public List<Experience> getAllExperience(){
        return experienceService.getAllExperience();
    }

    @GetMapping(path = "/{id}")
    public Optional<Experience> getExperienceById(@PathVariable int id){
        return experienceService.getExperienceById(id);

    }

    @PutMapping(path = "update/{id}")
    public void  updateExperience(@RequestBody Experience experience, @PathVariable int id){
        experienceService.updateExperienceById(experience, id);

    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteCourseById(@PathVariable int id) {
        boolean ok = experienceService.deleteExperienceById(id);

        if (ok) {
            return "Experience with id " + id + " was deleted";
        } else {
            return "Experience with id " + id + " not found";
        }
    }
}
