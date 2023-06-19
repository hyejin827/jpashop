package jpabook.jpashop.domain.repository;

import javax.persistence.EntityManager;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import jpabook.jpashop.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepository {
    private final EntityManager em;

    private final QMember qMember = QMember.member;
    private final QOrder qOrder = QOrder.order;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .selectFrom(qOrder)
                .join(qOrder.member, qMember)
//                                .on(qOrder.member.id.eq(qMember.id))
                .where(emptyName(orderSearch.getMemberName())
                        ,emptyStatus(orderSearch.getOrderStatus()))
                .fetch();
    }

    private BooleanExpression emptyName(String memberName) {
        if (StringUtils.isEmpty(memberName)) {
            return null;
        }
        return qMember.name.eq(memberName);
    }

    private BooleanExpression emptyStatus(OrderStatus orderStatus) {
        if (ObjectUtils.isEmpty(orderStatus)) {
            return null;
        }
        return qOrder.status.eq(orderStatus);
    }

    public List<Order> findAll2(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m " +
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