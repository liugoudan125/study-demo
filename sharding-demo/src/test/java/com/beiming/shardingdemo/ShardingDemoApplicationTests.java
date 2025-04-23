package com.beiming.shardingdemo;

import com.beiming.shardingdemo.domain.Student;
import com.beiming.shardingdemo.domain.Teacher;
import com.beiming.shardingdemo.mapper.StudentMapper;
import com.beiming.shardingdemo.mapper.TeacherMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@SpringBootTest(classes = ShardingDemoApplication.class)
class ShardingDemoApplicationTests {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private DataSource dataSource;

    @Test
    public void testInsert() {
        System.out.println(dataSource);
        List<Student> list = new ArrayList<>();
        for (int a = 0; a < 100; a++) {
            for (int i = 0; i < 10000; i++) {
                Student student = createStudent();
                list.add(student);
            }
            studentMapper.batchInsert(list);
        }

    }

    @Test
    public void testInsertTeacher() {
        List<Teacher> list = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            Teacher teacher = createTeacher();
            list.add(teacher);
        }
        teacherMapper.batchInsert(list);
    }

    @Test
    public void testSelect() {
//        selectOne();
//        selectByName();
//        selectByAgn();
//        selectLimit();
        selectGroup();
    }

    private void selectGroup() {
        List<Map<String, Objects>> list = studentMapper.selectGroup();
        for (Map<String, Objects> map : list) {
            System.out.println(map);
        }
    }

    @Test
    public void testUpdate() {
        updateById();
    }

    private void updateById() {
        Student student = new Student();
        student.setId(1053349342858969098L);
        student.setName("liuchenglong");
        studentMapper.updateByPrimaryKeySelective(student);
    }

    private void selectLimit() {
        List<Student> students = studentMapper.selectList();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void selectByAgn() {
        List<Student> students = studentMapper.selectByAge(25);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private void selectByName() {
        List<Student> student = studentMapper.selectByName("hXMETqjITF");
        System.out.println(student);
    }

    private void selectOne() {
        Student student = studentMapper.selectByPrimaryKey(1053350279400918290L);
        System.out.println(student);

    }

    private Student createStudent() {
        Student student = new Student();
        student.setName(RandomStringUtils.randomAlphabetic(10));
        student.setAge(new Random().nextInt(20) + 20);
        student.setRemark(RandomStringUtils.randomAlphabetic(10, 100));
        return student;
    }

    private LocalDateTime one = LocalDateTime.of(2024, 10, 1, 0, 0, 0);
    private LocalDateTime two = LocalDateTime.of(2024, 10, 2, 0, 0, 0);

    private Teacher createTeacher() {
        Teacher student = new Teacher();
        student.setName(RandomStringUtils.randomAlphabetic(10));
        student.setAge(new Random().nextInt(20) + 20);
        student.setRemark(RandomStringUtils.randomAlphabetic(10, 100));
        student.setCreateTime(new Random().nextBoolean() ? one : two);
        return student;
    }
}
