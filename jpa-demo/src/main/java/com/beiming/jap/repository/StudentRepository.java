package com.beiming.jap.repository;

import com.beiming.jap.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * StudentRepository
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    List<Student> findByName(String name);

    List<Student> findByNameLike(String name);


    @Query(value = "select s from Student s where s.name like %:name%",nativeQuery = false)
    List<Student> likeByName(String name);
}
