package com.virtualclassroom.repository;

import com.virtualclassroom.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Classroom, Long> {
}
