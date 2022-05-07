package com.virtualclassroom.repository;

import com.virtualclassroom.model.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    @Query("SELECT DISTINCT classroom FROM Classroom classroom JOIN classroom.users user  WHERE user.userName = :userName")
    List<Classroom> findByUserName(@Param("userName") String userName);

    @Query("SELECT p FROM Classroom p WHERE p.codeClass LIKE %?1%")
    Classroom findbyKey(String keyword);

    @Query("SELECT DISTINCT classroom FROM Classroom classroom JOIN classroom.users user  WHERE user.userName = :userName")
    Page<Classroom> findByUserNamePagination(@Param("userName") String userName, Pageable pageable);
}