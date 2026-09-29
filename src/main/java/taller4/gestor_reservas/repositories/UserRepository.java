package taller4.gestor_reservas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import taller4.gestor_reservas.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
