package com.example.demo.service;

import com.example.demo.Entity.CourseEntity;
import com.example.demo.dao.CourseDao;
import com.example.demo.dto.CourseRequestDTO;
import com.example.demo.dto.CourseResponseDTO;
import com.example.demo.util.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CourseService {


    private CourseDao courseDao;

    // create course object in DB
    public CourseResponseDTO onboardNewCourse(CourseRequestDTO courseRequestDTO){
        //convert RequestDTO to Entity
        CourseEntity courseEntity = AppUtils.mapTOToEntity(courseRequestDTO);
        CourseEntity entity = courseDao.save(courseEntity);
        //convert Entity to Entity
        CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(entity);
        courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
        return courseResponseDTO;
    }

    public List<CourseResponseDTO> viewAllCourse(){

        Iterable<CourseEntity> courseEntity = courseDao.findAll();

        return StreamSupport.stream(courseEntity.spliterator(), false)
                .map(AppUtils::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public CourseResponseDTO findByCourseId(Integer courseId) {

       CourseEntity Entity = courseDao.findById(courseId)
               .orElseThrow(()->new RuntimeException(""));
        return AppUtils.mapEntityToDTO(Entity);
    }

    public void deleteCourse(int courseId){
        courseDao.deleteById(courseId);
    }

    public CourseResponseDTO updateCourse(int courseId,CourseRequestDTO courseRequestDTO) {
        CourseEntity courseEntity = courseDao.findById(courseId).orElse(null);

        courseEntity.setCourseId(courseRequestDTO.getCourseId());
        courseEntity.setName(courseRequestDTO.getName());
        courseEntity.setTrainerName(courseRequestDTO.getTrainerName());
        courseEntity.setDuration(courseRequestDTO.getDuration());
        courseEntity.setStartDate(courseRequestDTO.getStartDate());
        courseEntity.setCourseType(courseRequestDTO.getCourseType());
        courseEntity.setFees(courseRequestDTO.getFees());
        courseEntity.setCertificateAvailable(courseRequestDTO.isCertificateAvailable());

        //convert Entity to ResponseDTO

        CourseEntity updatedEntity = courseDao.save(courseEntity);

        return AppUtils.mapEntityToDTO(updatedEntity);
    }

}
