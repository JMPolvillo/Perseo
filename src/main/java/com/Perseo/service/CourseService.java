package com.Perseo.service;

import com.Perseo.model.Course;
import com.Perseo.repository.ICourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final ICourseRepository iCourseRepository;

    public CourseService(ICourseRepository iCourseRepository) {
        this.iCourseRepository = iCourseRepository;
    }

    public Course createCourse(Course course) {
        return iCourseRepository.save(course);
    }

    public List<Course> getAllCourse() {
        try {
            return iCourseRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving donations.", e);
        }
    }

    public Optional<Course> getCourseById (int donationId) {
        try {
            return iCourseRepository.findById(donationId);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving donation details.", e);
        }
    }

    public void updateCourse(Course course, int Id) {
        course.setId(Id);
        iCourseRepository.save(course);
    }

    public boolean deleteCourse(int Id) {
        try {
            iCourseRepository.deleteById(Id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
