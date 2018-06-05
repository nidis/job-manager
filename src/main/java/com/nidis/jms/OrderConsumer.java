package com.nidis.jms;

import com.nidis.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConsumer {

    @JmsListener(destination = Application.QUEUE_NAME)
    public void receiveQueue(String message) {
        log.info("Order received from the {} \n{}", Application.QUEUE_NAME, message);
    }

}
