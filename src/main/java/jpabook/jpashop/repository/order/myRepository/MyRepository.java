package jpabook.jpashop.repository.order.myRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MyRepository {

    @PersistenceContext
    private final EntityManager em;

    public List<OrderQueryDto> findByMyOrder() {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o" +
                                " join  o.member m " +
                                " join  o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderItem> findById(Long id) {
        return em.createQuery(" select oi from OrderItem oi" +
                " join fetch oi.item i" +
                " where i.id = :id", OrderItem.class)
                .setParameter("id", id)
                .getResultList();
    }




}
