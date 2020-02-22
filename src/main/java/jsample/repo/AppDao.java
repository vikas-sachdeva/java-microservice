package jsample.repo;

import jsample.model.Application;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDao extends ReactiveMongoRepository<Application, String> {

}