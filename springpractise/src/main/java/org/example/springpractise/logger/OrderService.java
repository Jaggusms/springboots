package org.example.springpractise.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(OrderService.class);

    public void createOrder() {
        LOGGER.info("Creating order");
    }
}
