package com.totterman.polarbookshop.orderservice.order.event;

public record OrderDispatchedMessage(
        Long orderId
) {
}
