package jpabook.jpashop.dto.response;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;

    private String name;

    private Address address;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.address = member.getAddress();
    }
}
