package cis.ddbogdanov.covidtrackr.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SnapshotRepo extends CrudRepository<Snapshot, UUID> {

}
