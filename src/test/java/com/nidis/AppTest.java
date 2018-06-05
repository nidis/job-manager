package com.nidis;

import com.nidis.jms.OrderProducer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private OrderProducer orderProducer;

    @Test
    public void sendOrderMessage() throws InterruptedException {
        this.orderProducer.send("Order message");
        Thread.sleep(1000L);

        assertThat(this.outputCapture.toString().contains("Order message")).isTrue();
    }
}
