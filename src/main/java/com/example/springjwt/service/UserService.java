package com.example.springjwt.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springjwt.model.DAOUser;
import com.example.springjwt.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public DAOUser save(DAOUser user) {
       return repo.save(user);
    }
     
    public List<DAOUser> listAll() {
        return (List<DAOUser>) repo.findAll();
    }
     
    public Optional<DAOUser> getUserById(Long id) {
        return repo.findById(id);
    }
     
    public void delete(Long id) {
        repo.deleteById(id);
    }
	
}
