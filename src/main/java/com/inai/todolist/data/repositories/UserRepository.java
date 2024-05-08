package com.inai.todolist.data.repositories;

import com.inai.todolist.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT e FROM UserEntity e WHERE e.phone = :mobile")
    Optional<UserEntity> findByMobile(String mobile);


}
