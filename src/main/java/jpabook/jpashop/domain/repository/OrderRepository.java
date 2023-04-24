package jpabook.jpashop.domain.repository;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
//        JPAQueryFactory query = new JPAQueryFactory(em);
//        QueryFactory queryFactory = new JPAQueryFactory(em);
//        List<Order> orderList =
        return null;
    }

    public List<Order> findAll2(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m" +
                        "where o.status = :status " +
                        "and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
//                .setFirstResult(100) // 페이징
                .setMaxResults(1000) // 최대 1000건
                .getResultList();

        // querydsl 로 변환해서 사용하기기
    }
}