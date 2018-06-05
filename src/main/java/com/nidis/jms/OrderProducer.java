package com.nidis.jms;

import com.nidis.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Slf4j
public class OrderProducer {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Value("${jms.highPriority}")
    private int highPriority;

    @Scheduled(fixedRateString = "${jms.delay}")
    public void run() throws Exception {
        StringBuilder sb = new StringBuilder();

        for (Order order : generateOrders()) {
            sb.append("Order info: \n");
            sb.append("Id: ").append(order.getId()).append("\n");
            sb.append("Customer Name: ").append(order.getCustomerName()).append("\n");
            sb.append("Products: ").append(order.getProducts()).append("\n");
            sb.append("--------------------------------\n");

            /***
             * Let's assume that big orders should be treated with higher priority
             * Otherwise, the message will be sent with default priority
             */
            if (order.getTotalPrice() > 500.00)
                this.jmsTemplate.setPriority(highPriority);

            send(sb.toString());
            log.info("Order sent to the {} \n", queue.getQueueName());

            sb.setLength(0);
        }
    }

    public void send(final String message) {
        MessageCreator messageCreator = session -> session.createTextMessage(message);

        this.jmsTemplate.send(this.queue, messageCreator);
    }

    /***
     * @return List of Orders
     */
    private List<Order> generateOrders() {
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Double totalPrice = ThreadLocalRandom.current().nextDouble(100, 1000);
            Order order = new Order((long) i, "John Smith" + i, Arrays.asList("product1", "product2", "product3").toString(), totalPrice);
            orders.add(order);
        }

        return orders;
    }
}