package com.Perseo.repository;

import com.Perseo.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseRepository extends JpaRepository<Course, Integer> {

}

