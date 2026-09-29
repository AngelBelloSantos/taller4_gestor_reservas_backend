package taller4.gestor_reservas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.User;
import taller4.gestor_reservas.services.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService users;
	
	@GetMapping
	public List<User> getAllUsers() {
		return users.getAllUsers();
	}
	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable Long id) {
		return users.getUserById(id);
	}
	
	@PostMapping
	public User postUser(@RequestBody User user) {
		return users.newUser(user);
	}
	
	@PutMapping("/{id}")
	public User putUser(@PathVariable Long id, @RequestBody User user) {
		return users.updateUser(id, user);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		users.deleteUserById(id);
	}
}
