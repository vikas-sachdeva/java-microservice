package jsample;

import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import jsample.model.Application;
import jsample.service.AppService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
public class AppBaseClass {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AppService service;

    @BeforeEach
    public void setup() {
        RestAssuredWebTestClient.webTestClient(webTestClient);

        Application app1 = new Application("1", "Application-1", true);
        Application app2 = new Application("2", "Application-2", false);
        Application app3 = new Application("3", "Application-3", true);

        Mockito.when(service.getApps()).thenReturn(Flux.just(app1, app2, app3));

        Mockito.when(service.getAppById(Mockito.anyString())).thenAnswer(i -> {
            String id = i.getArgument(0);
            return Mono.just(app2.setId(id));
        });

        Mockito.when(service.addApp(Mockito.any(Application.class))).thenAnswer(i -> {

            Application app = i.getArgument(0);
            app.setId("4");
            return Mono.just(app);
        });

        Mockito.when(service.updateApp(Mockito.any(Application.class), Mockito.anyString())).thenAnswer(i -> {

            Application app = i.getArgument(0);
            String id = i.getArgument(1);
            app.setId(id);
            return Mono.just(app);
        });

        Mockito.when(service.deleteApp(Mockito.anyString())).thenReturn(Mono.empty());
    }
}