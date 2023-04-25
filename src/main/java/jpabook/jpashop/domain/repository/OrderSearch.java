package jpabook.jpashop.domain.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;

}
