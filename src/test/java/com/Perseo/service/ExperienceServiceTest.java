package com.Perseo.service;

import com.Perseo.model.Experience;
import com.Perseo.repository.IExperienceRepository;
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

class ExperienceServiceTest implements AutoCloseable{

    @Mock
    private IExperienceRepository iExperienceRepository;

    @InjectMocks
    private ExperienceService experienceService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void createExperience() {
        Experience experience = new Experience();
        when(iExperienceRepository.save(experience)).thenReturn(experience);

        Experience createdExperience = experienceService.createExperience(experience);

        assertNotNull(createdExperience);
        verify(iExperienceRepository, times(1)).save(experience);
    }

    @Test
    void getAllExperience() {
        Experience experience1 = new Experience();
        Experience experience2 = new Experience();
        List<Experience> experienceList = Arrays.asList(experience1, experience2);

        when(iExperienceRepository.findAll()).thenReturn(experienceList);

        List<Experience> result = experienceService.getAllExperience();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(iExperienceRepository, times(1)).findAll();
    }

    @Test
    void getExperienceById() {
        int id = 1;
        Experience experience = new Experience();
        experience.setId(id);

        when(iExperienceRepository.findById(id)).thenReturn(Optional.of(experience));

        Optional<Experience> result = experienceService.getExperienceById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(iExperienceRepository, times(1)).findById(id);
    }

    @Test
    void updateExperienceById() {
        int id = 1;
        Experience updatedExperience = new Experience();
        updatedExperience.setId(id);

        when(iExperienceRepository.save(updatedExperience)).thenReturn(updatedExperience);

        experienceService.updateExperienceById(updatedExperience, id);

        verify(iExperienceRepository, times(1)).save(updatedExperience);
    }

    @Test
    void deleteExperienceById() {
        int id = 1;

        doNothing().when(iExperienceRepository).deleteById(id);

        boolean result = experienceService.deleteExperienceById(id);

        assertTrue(result);
        verify(iExperienceRepository, times(1)).deleteById(id);
    }

    @Override
    public void close() throws Exception {
        mocks.close();
    }
}