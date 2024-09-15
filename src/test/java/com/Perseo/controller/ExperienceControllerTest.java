package com.Perseo.controller;

import com.Perseo.model.Course;
import com.Perseo.model.Experience;
import com.Perseo.service.CourseService;
import com.Perseo.service.ExperienceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ExperienceControllerTest {

    @Mock
    private ExperienceService experienceService;
    private MockMvc mockMvc;
    private Experience experience1;
    private Experience experience2;
    private List<Experience> experienceList;

    @InjectMocks
    private ExperienceController experienceController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(experienceController).build();

        experience1 = new Experience();
        experience1.setId(1);
        experience1.setTitle("Coder");
        experience1.setCompanyname("Factoria F5");
        experience1.setStartdate(LocalDate.of(2024,01,01));
        experience1.setEnddate(LocalDate.of(2024,05,05));
        experience1.setDescription("Hi");


        experience2 = new Experience();
        experience2.setId(2);
        experience2.setTitle("Teacher");
        experience2.setCompanyname("Factoria F5");
        experience2.setStartdate(LocalDate.of(2024,05,05));
        experience2.setEnddate(LocalDate.of(2024,10,10));
        experience2.setDescription("Hi2");

        experienceList = new ArrayList<>();
        experienceList.add(experience1);
        experienceList.add(experience2);

    }
    @Test
    void createExperience() throws Exception{
        when(experienceService.createExperience(any(Experience.class))).thenReturn(experience1);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        String courseJson = objectMapper.writeValueAsString(experience1);

        mockMvc.perform(post("/api/experience/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Coder"))
                .andExpect(jsonPath("$.companyname").value("Factoria F5"))
                .andExpect(jsonPath("$.startdate").value("01-01-2024"))
                .andExpect(jsonPath("$.enddate").value("05-05-2024"))
                .andExpect(jsonPath("$.description").value("Hi"));
    }


    @Test
    void getAllExperience() throws Exception{
        when(experienceService.getAllExperience()).thenReturn(experienceList);
        mockMvc.perform(get("/api/experience"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getExperienceById() throws Exception{
        when(experienceService.getExperienceById(2)).thenReturn(Optional.of(experience2));

        mockMvc.perform(get("/api/experience/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }


    @Test
    void updateExperienceTest() throws Exception {
        doNothing().when(experienceService).updateExperienceById(experience2, 2);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        String experienceJson = objectMapper.writeValueAsString(experience2);

        mockMvc.perform(put("/api/experience/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(experienceJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCourseById() throws Exception{
        when(experienceService.deleteExperienceById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/experience/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Experience with id 1 was deleted" ));

        when(experienceService.deleteExperienceById(1)).thenReturn(false);

        mockMvc.perform(delete("/api/experience/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Experience with id 1 not found" ));
    }
}