package com.example.springjwt.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springjwt.model.DAOUser;


@Repository
public interface UserRepository extends JpaRepository<DAOUser, Long> {
	
	
	DAOUser findByUsername(String username);

}