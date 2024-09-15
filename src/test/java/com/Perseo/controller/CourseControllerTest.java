package com.Perseo.controller;

import com.Perseo.model.Course;
import com.Perseo.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;
    private MockMvc mockMvc;
    private Course course1;
    private Course course2;
    private List<Course> courseList;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();

        course1 = new Course();
        course1.setId(1);
        course1.setTitle("Introduction to Java");
        course1.setDescription("hi");
        course1.setPrice(150);


        course2 = new Course();
        course2.setId(2);
        course2.setTitle("Introduction to Python");
        course2.setDescription("hi");
        course2.setPrice(250);

        courseList = new ArrayList<>();
        courseList.add(course1);
        courseList.add(course2);

    }

    @Test
    void createCourseTest() throws Exception {
            when(courseService.createCourse(any(Course.class))).thenReturn(course1);

            ObjectMapper objectMapper = new ObjectMapper();

            String courseJson = objectMapper.writeValueAsString(course1);

            mockMvc.perform(post("/api/course/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(courseJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.title").value("Introduction to Java"))
                    .andExpect(jsonPath("$.description").value("hi"))
                    .andExpect(jsonPath("$.price").value(150));
        }


    @Test
    void getAllCourseTest() throws Exception {
        when(courseService.getAllCourse()).thenReturn(courseList);
        mockMvc.perform(get("/api/course"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getDonationByIdTest() throws Exception {
        when(courseService.getCourseById(2)).thenReturn(Optional.of(course2));

        mockMvc.perform(get("/api/course/2")
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(2));
        }


    @Test
    void updateCourseTest() throws Exception {
        doNothing().when(courseService).updateCourse(course2, 2);

        ObjectMapper objectMapper = new ObjectMapper();

        String courseJson = objectMapper.writeValueAsString(course2);

        mockMvc.perform(put("/api/course/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCourseById() throws Exception{
        when(courseService.deleteCourse(1)).thenReturn(true);

        mockMvc.perform(delete("/api/course/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Donation with id 1 was deleted" ));

        when(courseService.deleteCourse(1)).thenReturn(false);

        mockMvc.perform(delete("/api/course/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Donation with id 1 not found" ));
    }
}