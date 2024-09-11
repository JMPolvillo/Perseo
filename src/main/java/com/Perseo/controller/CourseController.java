package com.Perseo.controller;

import com.Perseo.model.Course;
import com.Perseo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/course")
@CrossOrigin

public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping(path = "")
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);

    }

    @GetMapping(path = "")
    public List<Course> getAllCourse(){
        return courseService.getAllCourse();
    }

    @GetMapping(path = "/{id}")
    public Optional<Course> getDonationId(@PathVariable int id){
        return courseService.getCourseById(id);

    }

    @PutMapping(path = "/{id}")
    public void  updateCourse(@RequestBody Course course, @PathVariable int id){
        courseService.updateCourse(course, id);

    }

    @DeleteMapping(path = "/{id}")
    public String deleteCourseById(@PathVariable int id) {
        boolean ok = courseService.deleteCourse(id);

        if (ok) {
            return "Donation with id " + id + " was deleted";
        } else {
            return "Donation with id " + id + " not found";
        }
    }
}
