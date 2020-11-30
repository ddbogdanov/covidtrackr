package cis.ddbogdanov.covidtrackr.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {
    List<User> findByUsername(String username);
}
