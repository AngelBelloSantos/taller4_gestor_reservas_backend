package taller4.gestor_reservas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.User;
import taller4.gestor_reservas.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository users;
	
	public List<User> getAllUsers() {
		return users.findAll();
	}
	public Optional<User> getUserById(Long id) {
		return users.findById(id);
	}
	
	public User newUser(User user) {
		return users.save(user);
	}
	
	public User updateUser(Long id, User user) {
		if (users.existsById(id)) {
			User userUpdated = getUserById(id).get();
			userUpdated.setName(user.getName());
			userUpdated.setIdentificationNumber(user.getIdentificationNumber());
			userUpdated.setAddress(user.getAddress());
			userUpdated.setPhoneNumber(user.getPhoneNumber());
			return users.save(userUpdated);
		}
		return newUser(user);
	}
	
	public void deleteUserById(Long id) {
		users.deleteById(id);
	}
}
