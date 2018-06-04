package com.nidis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @JmsListener(destination = Application.QUEUE_NAME)
    public void receiveQueue(String message) {
        log.info("Order received from the {} \n{}", Application.QUEUE_NAME, message);
    }

}
