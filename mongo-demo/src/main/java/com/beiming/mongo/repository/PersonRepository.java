package com.beiming.mongo.repository;

import com.beiming.mongo.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * PersonRepository
 */
@Repository
public interface PersonRepository extends MongoRepository<Person, Long> {
    Page<Person> findAllByIdGreaterThan(Long id, Pageable pageable);

    Page<Person> findByIdGreaterThan(Long id, Pageable pageable);

}
