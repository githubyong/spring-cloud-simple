package org.example.auth.dao;

import org.example.auth.model.ScsUser;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<ScsUser,Integer> {


    @Query("select * from user where username=:name")
    List<ScsUser> findByUsername(@Param("name") String name);
}
