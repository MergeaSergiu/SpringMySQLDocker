package org.api.crudspringbootmysqldocker.repository;

import jakarta.transaction.Transactional;
import org.api.crudspringbootmysqldocker.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.beans.Transient;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Transactional
    @Modifying
    void deleteByName(String name);
}
