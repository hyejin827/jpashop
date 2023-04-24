package jpabook.jpashop.domain;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//n  mappdBy는 @OneToOne, @OneToMany, @ManyToMany 어노테이션에서 사용할 수 있으며
//   mappedBy가 없으면 JAP에서 양뱡향 관계라는 것을 모르고 두 엔티티의 매핑 테이블을 생성한다.
//
//n  mappedBy는 OWBER 엔티티의 필드나 속성과 대응된다.
//
//n  ManyToOne 양방향 관계에서 Many측에는 mappedBy요소를 사용할 수 없다.(MANY 쪽이 OWNER)
//
//n  OneToOne 양방향 관계에서 OWNER는 반대쪽(INVERSE SIDE)에 대한 FK를 가지는 쪽이다.
//
//n  ManyToMany 양방향 관계는 양쪽 중 아무나 OWNER가 될 수 있다.
@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // XToOne 의 경우 디폴트가 EAGER / XToMany 는 디폴트 LAZY
    // .find()로 order 1개 조회할때는 상관없지만
    // jpql일때 select * from order 가 100개면 member 를 100번 조회함
    // lazy일때는 member 조회안하는데 조회하게 하고싶으면 fetch 쓰면됨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // persist(orderItemA)
    // persist(orderItemB)
    // persist(orderItemC)
    // persist(order) -> cascade = CascadeType.ALL으로 설정하면 order만 persist 해도 됨
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // springPhysivalNamingStrategy 으로 order_date 로 생성됨
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 연관관계 매서드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

//    public static void main(String[] args) {
//        Member member = new Member();
//        Order order = new Order();

//        member.getOrders().add(order);
//        order.setMember(member);
//    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /* 생성메서드
    * 엔티티에서 비지니스로직을 가지는것 -> 도메인 모델 패턴 (객체지향 특성 활용..)
    * 서비스에서 비지니스로직을 가지는것 -> 트랜잭션 스크립트 패턴
    */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /* 비지니스로직 */
    /* 주문취소 */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /* 조회로직 */
    /* 전체주문가격조회 */
    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
        int totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
        return totalPrice;
    }
}
