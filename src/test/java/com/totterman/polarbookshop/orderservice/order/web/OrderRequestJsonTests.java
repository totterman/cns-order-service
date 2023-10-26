package com.totterman.polarbookshop.orderservice.order.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.totterman.polarbookshop.orderservice.order.domain.Order;
import com.totterman.polarbookshop.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JsonTest
class OrderRequestJsonTests {

    @Autowired
    private JacksonTester<OrderRequest> orderRequestJacksonTester;

    @Test
    void testSerialize() throws Exception {
        var orderRequest = new OrderRequest("1234567890", 1);
        var jsonContent = this.orderRequestJacksonTester.write(orderRequest);

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(orderRequest.isbn());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.quantity")
                .isEqualTo(orderRequest.quantity());
    }


        @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                    "isbn": "1234567890",
                    "quantity": 1
                }
                """;
        assertThat(this.orderRequestJacksonTester.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new OrderRequest("1234567890", 1));
    }

}
