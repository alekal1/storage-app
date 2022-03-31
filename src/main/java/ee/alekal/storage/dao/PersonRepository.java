package ee.alekal.storage.dao;

import ee.alekal.storage.model.jpa.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String username);
    Optional<Person> findByUsernameAndPassword(String username, String password);
}
