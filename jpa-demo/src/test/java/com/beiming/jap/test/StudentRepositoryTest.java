package com.beiming.jap.test;

import com.beiming.jap.Application;
import com.beiming.jap.model.Student;
import com.beiming.jap.model.Teacher;
import com.beiming.jap.repository.StudentRepository;
import com.beiming.jap.repository.TeacherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

/**
 * StudentRepositoryTest
 */
@SpringBootTest(classes = Application.class)
public class StudentRepositoryTest {

    @Resource
    private StudentRepository studentRepository;


    @Resource
    private TeacherRepository teacherRepository;

    @Test
    public void testSave() throws ParseException {
        for (int i = 0; i < 3; i++) {
            Teacher tea = createTea();
            teacherRepository.save(tea);
        }
        List<Teacher> teachers = teacherRepository.findAll();
        for (int i = 0; i < 10; i++) {
            Student stu = createStu();
            stu.setTeacherList(teachers);
            studentRepository.save(stu);
        }
    }

    private Student createStu() {
        Student student = new Student();
        student.setName(RandomStringUtils.randomAlphabetic(10));
        return student;
    }

    private Teacher createTea() {
        Teacher teacher = new Teacher();
        teacher.setName(RandomStringUtils.randomAlphabetic(10));
        return teacher;
    }

    @Resource
    private ObjectMapper objectMapper;

    @Test
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public void testQuery() throws JsonProcessingException {
        Page<Student> page = studentRepository.findAll(PageRequest.of(0, 10).withSort(Sort.by(Sort.Order.desc("name"))));
        System.out.println(objectMapper.writeValueAsString(page));
    }


    @Test
    public void testAnno() throws JsonProcessingException {
        System.out.println(studentRepository.likeByName("a"));
    }
}
