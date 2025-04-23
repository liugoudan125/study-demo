package com.beiming.es.mapper;

import com.beiming.es.model.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Student
 */
@Repository
public interface StudentRepository extends ElasticsearchRepository<Student, Long> {
}
