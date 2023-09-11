package com.hngTask2.identify.data.repository;

import com.hngTask2.identify.data.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Long, Person> {
}
