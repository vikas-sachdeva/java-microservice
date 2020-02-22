package jsample.service;

import jsample.exceptions.ApplicationNotFoundException;
import jsample.model.Application;
import jsample.repo.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AppService {

    @Autowired
    private AppDao dao;

    public Flux<Application> getApps() {
        return dao.findAll();
    }

    public Mono<Application> getAppById(String id) {
        return dao.findById(id).switchIfEmpty(onApplicationNotFound(id));
    }

    public Mono<Application> addApp(Application app) {
        return dao.insert(app);
    }

    public Mono<Void> deleteApp(String id) {
        return getAppById(id)
                .flatMap(x -> dao.deleteById(id));
    }

    public Mono<Application> updateApp(Application app, String id) {
        return getAppById(id)
                .map(r -> r.setName(app.getName()).setRunning(app.getRunning()))
                .flatMap(dao::save);
    }

    private Mono<Application> onApplicationNotFound(String id) {
        return Mono.error((new ApplicationNotFoundException("Application with id " + id + " not found")));
    }
}