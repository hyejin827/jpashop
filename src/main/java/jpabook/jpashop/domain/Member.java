package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 수정하지않아야함
    // ArrayList에서 hibernate 의 타입인 persistBag? 으로 바뀜
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
