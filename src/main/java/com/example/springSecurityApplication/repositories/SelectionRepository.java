package com.example.springSecurityApplication.repositories;

import com.example.springSecurityApplication.models.Selection;
import com.example.springSecurityApplication.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectionRepository extends JpaRepository<Selection, Integer> {

    List<Selection> findByPerson(Person person);
}
