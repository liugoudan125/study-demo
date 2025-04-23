package com.beiming.jap.repository;

import com.beiming.jap.model.Student;
import com.beiming.jap.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * StudentRepository
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
