package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;

    private String memberName;

    private String itemName;

    private int price;

    private int count;

    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.memberName = order.getMember().getName();
        this.itemName = order.getOrderItems().get(0).getItem().getName();
        this.price = order.getOrderItems().get(0).getOrderPrice();
        this.count = order.getOrderItems().get(0).getCount();
        this.orderStatus = order.getStatus();
        this.orderDate = order.getOrderDate();
    }
}
