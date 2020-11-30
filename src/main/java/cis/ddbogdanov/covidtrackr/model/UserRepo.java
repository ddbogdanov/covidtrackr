package cis.ddbogdanov.covidtrackr.model;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {

}
