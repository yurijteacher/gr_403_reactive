package ua.com.reactive.gr_403_reactive.test;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServer;
import ua.com.reactive.gr_403_reactive.entity.Client;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        Flux<Client> clientFlux = Flux.just(
                new Client(1L, "Vasya", "Pypkin", 18),
                new Client(2L, "Iva", "Pypkina", 19),
                new Client(3L, "Inna", "Pypkina", 20)
        );

//        HandlerFunction<ServerResponse> clientsHandler = new HandlerFunction<ServerResponse>() {
//            @Override
//            public Mono<ServerResponse> handle(ServerRequest request) {
//                return ServerResponse
//                        .ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(clientFlux, Client.class);
//            }

        HandlerFunction<ServerResponse> clientsHandler = request ->
                ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(clientFlux, Client.class);

        RouterFunction router = RouterFunctions
                .route(RequestPredicates.GET("/all"), clientsHandler);

        HttpHandler handler = RouterFunctions.toHttpHandler(router);

        HttpServer
                .create()
                .port(8080)
                .handle(new ReactorHttpHandlerAdapter(handler))
                .bindNow();

        Thread.currentThread().join();
    }


}
