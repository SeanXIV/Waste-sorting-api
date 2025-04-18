package com.enviro.assessment.grad001.andrewseanego.repository;

import com.enviro.assessment.grad001.andrewseanego.entity.ERole;
import com.enviro.assessment.grad001.andrewseanego.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
