package vttp2022.assessment.csf.orderbackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;


@RestController
@RequestMapping(path="/api/order", produces=MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private OrderRepository orderRepository;

    private Logger logger = Logger.getLogger(OrderRestController.class.getName());

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postOrder(@RequestBody String payload) {
        logger.info("Payload:%s".formatted(payload));
        Order order = Order.create(payload);
        logger.info("toppings:%s".formatted(order.getToppings()));
        orderRepository.insertOrder(order);
        return null;
    }

    @GetMapping(path="{email}/all")
    public ResponseEntity<String> getOrdersByEmail(@PathVariable String email) {
      
        List<OrderSummary> orderSummaries = new LinkedList<>();
        orderSummaries = orderRepository.getOrdersByEmail(email);
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (OrderSummary summary: orderSummaries)
            arrBuilder.add(summary.toJson());

        return ResponseEntity.ok(arrBuilder.build().toString());
    }
}
