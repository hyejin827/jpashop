package jpabook.jpashop.dto.request;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class OrderRequestDto {

    private Long memberId;

    private Long itemId;

    private int orderCnt;
}
