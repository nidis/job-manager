package com.nidis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Scheduled(fixedRateString = "${jms.delay}")
    public void run() throws Exception {
        StringBuilder sb = new StringBuilder();

        for (Order order : generateOrders()) {
            sb.append("Order info: \n");
            sb.append("Id: ").append(order.getId()).append("\n");
            sb.append("Customer Name: ").append(order.getCustomerName()).append("\n");
            sb.append("Products: ").append(order.getProducts()).append("\n");
            sb.append("--------------------------------\n");

            send(sb.toString());
            log.info("Order sent to the {} \n", queue.getQueueName());

            sb.setLength(0);
        }
    }

    public void send(final String message) {
        MessageCreator messageCreator = session -> session.createTextMessage(message);

        this.jmsTemplate.setPriority(9);
        this.jmsTemplate.send(this.queue, messageCreator);
    }

    private List<Order> generateOrders() {
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Order order = new Order((long) i, "John Smith" + i, Arrays.asList("product1", "product2", "product3").toString());
            orders.add(order);
        }

        return orders;
    }
}