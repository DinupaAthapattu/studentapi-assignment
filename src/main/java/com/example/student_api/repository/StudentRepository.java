package com.example.student_api.repository;

import com.example.student_api.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Search by name (case-insensitive, partial match)
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Search by course (case-insensitive, partial match)
    Page<Student> findByCourseContainingIgnoreCase(String course, Pageable pageable);

    // Search by name or course
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.course) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Student> searchByNameOrCourse(@Param("keyword") String keyword, Pageable pageable);
}

