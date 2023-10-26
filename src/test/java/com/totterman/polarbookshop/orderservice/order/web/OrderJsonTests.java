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
class OrderJsonTests {

    @Autowired
    private JacksonTester<Order> orderJacksonTester;

    @Test
    void testSerialize() throws Exception {
        var order = new Order(394L, "1234567890", "Book Name",
                9.90, 1, OrderStatus.ACCEPTED,
                Instant.now(), Instant.now(), "John Doe", "John Doe", 21);
        var jsonContent = this.orderJacksonTester.write(order);

        assertThat(jsonContent).extractingJsonPathNumberValue("@.id")
                .isEqualTo(order.id().intValue());
        assertThat(jsonContent).extractingJsonPathStringValue("@.bookIsbn")
                .isEqualTo(order.bookIsbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.bookName")
                .isEqualTo(order.bookName());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.bookPrice")
                .isEqualTo(order.bookPrice());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.quantity")
                .isEqualTo(order.quantity());
        assertThat(jsonContent).extractingJsonPathStringValue("@.status")
                .isEqualTo(order.status().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.createdDate")
                .isEqualTo(order.createdDate().toString());
        assertThat(jsonContent).extractingJsonPathStringValue("@.lastModifiedDate")
                .isEqualTo(order.lastModifiedDate().toString());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.version")
                .isEqualTo(order.version());
    }

    @Test
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2023-10-10T12:52:12.123456Z");
        var content = """
                {
                "id": 394,
                "bookIsbn": "1234567890",
                "bookName": "Book Name",
                "bookPrice": 9.90,
                "quantity": 1,
                "status": "%s",
                "createdDate": "2023-10-10T12:52:12.123456Z",
                "lastModifiedDate": "2023-10-10T12:52:12.123456Z",
                "createdBy": "Jane Doe",
                "lastModifiedBy": "Jane Doe",
                "version": 21
                }
                """.formatted(OrderStatus.ACCEPTED);
        assertThat(this.orderJacksonTester.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Order(394L, "1234567890", "Book Name",
                        9.90, 1, OrderStatus.ACCEPTED,
                        instant, instant, "Jane Doe", "Jane Doe", 21));
    }

}
