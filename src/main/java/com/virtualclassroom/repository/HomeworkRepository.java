package com.virtualclassroom.repository;

import com.virtualclassroom.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {

}
