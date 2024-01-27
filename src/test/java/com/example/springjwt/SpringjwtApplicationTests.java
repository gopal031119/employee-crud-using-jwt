package com.example.springjwt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.springjwt.model.DAOUser;
import com.example.springjwt.repository.UserRepository;
import com.example.springjwt.service.UserService;

@SpringBootTest
class SpringjwtApplicationTests {
	
	@Autowired
	private UserService service;
	
	@MockBean
	private UserRepository repository;
	
	@Test
	public void listAllTest() {
		when(repository.findAll()).thenReturn(Stream.of(new DAOUser(1,"abc","abc","ROLE_ADMIN")
				,new DAOUser(2,"def","def","ROLE_ADMIN")).collect(Collectors.toList()));
		
		assertEquals(2, service.listAll().size());
	}
	
	@Test
	public void saveTest() {
		DAOUser user = new DAOUser(2,"defgh","defgh","ROLE_ADMIN");
		when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.save(user));
	}
	
	@Test
	public void getUserByIdTest() {
		Long id = (long) 7;
		Optional<DAOUser> user = Optional.of((new DAOUser(7,"defgh","defgh","ROLE_ADMIN")));
		when(repository.findById(id)).thenReturn(user);
		
		assertEquals(user, service.getUserById(id));
	}
	
	@Test
	public void deleteTest() {
		DAOUser user = new DAOUser(2,"defgh","defgh","ROLE_ADMIN");
		service.delete((long) 2);
		verify(repository , times(1)).deleteById((long) 2);
	}
	
}
