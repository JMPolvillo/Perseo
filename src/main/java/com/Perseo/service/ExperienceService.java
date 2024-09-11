package com.Perseo.service;

import com.Perseo.model.Experience;
//import com.Perseo.model.ExperienceDTO;
import com.Perseo.repository.IExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService {
    @Autowired
    IExperienceRepository iExperienceRepository;

    public Experience createExperience(Experience experience) {
        return iExperienceRepository.save(experience);
    }

    public List<Experience> getAllExperience() {
        try {
            return iExperienceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving pets.", e);
        }
    }

    public Optional<Experience> getExperienceById(int id) {
        try {
            return iExperienceRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving pet by id", e);
        }
    }

    public void updateExperienceById(Experience experience, int id) {
        experience.setId(id);
        iExperienceRepository.save(experience);
    }

    public boolean deleteExperienceById(int id) {
        try {
            iExperienceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
