package com.example.demo.controller;

import com.example.demo.dto.CourseRequestDTO;
import com.example.demo.dto.CourseResponseDTO;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {
    
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody CourseRequestDTO courseRequestDTO){

        CourseResponseDTO newCourse = courseService.onboardNewCourse(courseRequestDTO);
        return new ServiceResponse<>(HttpStatus.CREATED, newCourse);
    }

    @GetMapping
    public ServiceResponse<List<CourseResponseDTO>> findAllCourse(){

        List<CourseResponseDTO> courseResponseDTOS = courseService.viewAllCourse();

        return new ServiceResponse<>(HttpStatus.OK, courseResponseDTOS);
    }


    @GetMapping("/search/path/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourse(@PathVariable("courseId") Integer courseId){

        CourseResponseDTO byCourseId = courseService.findByCourseId(courseId);

        return new ServiceResponse<>(HttpStatus.OK,byCourseId);
    }


    @GetMapping("/search/request")
    public ServiceResponse<CourseResponseDTO> findCourseRequestingParam(@RequestParam(required = false)Integer courseId){

        CourseResponseDTO course = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK,course);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId){
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("",HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody CourseRequestDTO course){
        CourseResponseDTO updateCourse = courseService.updateCourse(courseId, course);

        return new ServiceResponse<>(HttpStatus.OK,updateCourse);

    }
}
