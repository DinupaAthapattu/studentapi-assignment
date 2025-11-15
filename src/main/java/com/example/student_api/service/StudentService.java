package com.example.student_api.service;

import com.example.student_api.entity.Student;
import com.example.student_api.exception.ResourceNotFoundException;
import com.example.student_api.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // BONUS: Pagination & Sorting
    public Page<Student> getAllStudentsPaginated(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    // BONUS: Search by name
    public Page<Student> searchByName(String name, Pageable pageable) {
        return studentRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    // BONUS: Search by course
    public Page<Student> searchByCourse(String course, Pageable pageable) {
        return studentRepository.findByCourseContainingIgnoreCase(course, pageable);
    }

    // BONUS: Search by keyword (name or course)
    public Page<Student> searchByKeyword(String keyword, Pageable pageable) {
        return studentRepository.searchByNameOrCourse(keyword, pageable);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setCourse(studentDetails.getCourse());
        student.setAge(studentDetails.getAge());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}

