package taller4.gestor_reservas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import taller4.gestor_reservas.models.User;
import taller4.gestor_reservas.models.UserRole;
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
		User newUser = new User();
		newUser.setName(user.getName());
		newUser.setIdentificationNumber(user.getIdentificationNumber());
		newUser.setPassword(user.getPassword());
		newUser.setRole(UserRole.STANDARD_USER);
		newUser.setEmail(user.getEmail());
		newUser.setPhoneNumber(user.getPhoneNumber());
		newUser.setAddress(user.getAddress());
		return users.save(newUser);
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
	public Optional<User> updatePassword(Long id, User user) {
		if (users.existsById(id)) {
			User userUpdated = getUserById(id).get();
			userUpdated.setPassword(user.getPassword());
			users.save(userUpdated);
		}
		return Optional.empty();
	}
	public Optional<User> setUserRole(Long id, User user) {
		boolean validRole = false;
		for (UserRole role : UserRole.values()) {
			if (role.compareTo(user.getRole()) == 0) validRole = true;
		}
		if (users.existsById(id) && validRole) {
			User userUpdated = users.findById(id).get();
			userUpdated.setRole(user.getRole());
			return Optional.of(users.save(userUpdated));
		}
		return Optional.empty();
	}
	
	public void deleteUserById(Long id) {
		users.deleteById(id);
	}
}
