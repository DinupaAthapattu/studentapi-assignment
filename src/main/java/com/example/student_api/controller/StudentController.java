package com.example.student_api.controller;

import com.example.student_api.entity.Student;
import com.example.student_api.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing students")
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Create a new student")
    @ApiResponse(responseCode = "201", description = "Student created successfully")
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all students (no pagination)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all students")
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(summary = "BONUS: Get students with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated students")
    @GetMapping("/paginated")
    public ResponseEntity<Page<Student>> getAllStudentsPaginated(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Items per page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field (name, age, email, course)") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Student> students = studentService.getAllStudentsPaginated(pageable);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(summary = "BONUS: Search students by name")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching students")
    @GetMapping("/search/name")
    public ResponseEntity<Page<Student>> searchByName(
            @Parameter(description = "Name to search") @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentService.searchByName(name, pageable);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(summary = "BONUS: Search students by course")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching students")
    @GetMapping("/search/course")
    public ResponseEntity<Page<Student>> searchByCourse(
            @Parameter(description = "Course to search") @RequestParam String course,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentService.searchByCourse(course, pageable);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(summary = "BONUS: Search students by keyword (name or course)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching students")
    @GetMapping("/search")
    public ResponseEntity<Page<Student>> searchByKeyword(
            @Parameter(description = "Keyword to search in name or course") @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<Student> students = studentService.searchByKeyword(keyword, pageable);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Operation(summary = "Get student by ID")
    @ApiResponse(responseCode = "200", description = "Student found")
    @ApiResponse(responseCode = "404", description = "Student not found")
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @Operation(summary = "Update student")
    @ApiResponse(responseCode = "200", description = "Student updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student studentDetails) {
        Student updatedStudent = studentService.updateStudent(id, studentDetails);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @Operation(summary = "Delete student")
    @ApiResponse(responseCode = "200", description = "Student deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student deleted successfully");
        response.put("id", String.valueOf(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

