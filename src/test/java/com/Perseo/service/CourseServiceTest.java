package com.Perseo.service;

import com.Perseo.model.Course;
import com.Perseo.repository.ICourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest implements AutoCloseable{

    @Mock
    private ICourseRepository iCourseRepository;

    @InjectMocks
    private CourseService courseService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourse() {
        Course course = new Course();
        when(iCourseRepository.save(course)).thenReturn(course);

        Course createdCourse = courseService.createCourse(course);

        assertNotNull(createdCourse);
        verify(iCourseRepository, times(1)).save(course);
    }

    @Test
    void getAllCourse() {
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courseList = Arrays.asList(course1, course2);

        when(iCourseRepository.findAll()).thenReturn(courseList);

        List<Course> result = courseService.getAllCourse();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(iCourseRepository, times(1)).findAll();
    }

    @Test
    void getCourseById() {
        int id = 1;
        Course course = new Course();
        course.setId(id);

        when(iCourseRepository.findById(id)).thenReturn(Optional.of(course));

        Optional<Course> result = courseService.getCourseById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(iCourseRepository, times(1)).findById(id);
    }

    @Test
    void updateCourse() {
        int id = 1;
        Course updatedCourse = new Course();
        updatedCourse.setId(id);

        when(iCourseRepository.save(updatedCourse)).thenReturn(updatedCourse);

        courseService.updateCourse(updatedCourse, id);

        verify(iCourseRepository, times(1)).save(updatedCourse);
    }

    @Test
    void deleteCourse() {
        int id = 1;

        doNothing().when(iCourseRepository).deleteById(id);

        boolean result = courseService.deleteCourse(id);

        assertTrue(result);
        verify(iCourseRepository, times(1)).deleteById(id);
    }

    @Override
    public void close() throws Exception {
        mocks.close();
    }
}