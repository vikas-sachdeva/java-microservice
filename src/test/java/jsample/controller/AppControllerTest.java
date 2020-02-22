package jsample.controller;

import jsample.model.Application;
import jsample.service.AppService;
import jsample.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureRestDocs
public class AppControllerTest {

    @MockBean
    private AppService service;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAppsTest1() {

        Application app1 = new Application("1", "Application-1", true);
        Application app2 = new Application("2", "Application-2", false);
        Application app3 = new Application("3", "Application-3", true);

        Mockito.when(service.getApps()).thenReturn(Flux.just(app1, app2, app3));

        webTestClient.get()
                .uri(AppConstants.URI.GET_APPS)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Application.class)
                .isEqualTo(Arrays.asList(app1, app2, app3))
                .consumeWith(WebTestClientRestDocumentation.document("getApps"));
    }

    @Test
    public void getAppTest1() {

        Application app3 = new Application("3", "Application-3", true);

        Mockito.when(service.getAppById(app3.getId())).thenReturn(Mono.just(app3));

        webTestClient.get()
                .uri(AppConstants.URI.GET_APP, app3.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Application.class)
                .isEqualTo(app3)
                .consumeWith(WebTestClientRestDocumentation.document("getApp"));
    }

    @Test
    public void addAppTest1() {
        Application app4 = new Application("4", "Application-4", true);

        Mockito.when(service.addApp(Mockito.any(Application.class))).thenReturn(Mono.just(app4));

        webTestClient.post()
                .uri(AppConstants.URI.ADD_APP)
                .bodyValue(app4)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Application.class)
                .isEqualTo(app4)
                .consumeWith(WebTestClientRestDocumentation.document("addApp"));
    }

    @Test
    public void updateAppTest1() {
        Application app5 = new Application("5", "Application-5", true);

        Mockito.when(service.updateApp(Mockito.any(Application.class), Mockito.anyString()))
                .thenReturn(Mono.just(app5));

        webTestClient.put()
                .uri(AppConstants.URI.UPDATE_APP, 5)
                .bodyValue(app5)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Application.class)
                .isEqualTo(app5)
                .consumeWith(WebTestClientRestDocumentation.document("updateApp"));
    }

    @Test
    public void deleteAppTest1() {

        Mockito.when(service.deleteApp(Mockito.anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(AppConstants.URI.DELETE_APP, 5)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody()
                .consumeWith(WebTestClientRestDocumentation.document("deleteApp"));
    }
}
