package jpabook.jpashop.api;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.myRepository.MyRepository;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Member;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class MemberApiConroller2 {

   private final OrderRepository orderRepository;
   private final MyRepository myRepository;;
   @PersistenceContext
   private final EntityManager em;

   @GetMapping("/api2/v1/orders")
   public List<Order> getOrder() {
       List<Order> result = orderRepository.findByString(new OrderSearch());

       for(Order order : result) {
           order.getMember().getName();
           order.getDelivery().getAddress();
       }

       return result;

   }

    @GetMapping("/api2/v2/orders")
    public List<OrderDto> getOrderV2() {
       List<Order> result = orderRepository.findByString(new OrderSearch());
        List<OrderDto> collect = result.stream()
                .map(OrderDto::new)
                .collect(toList());

        return collect;
    }



    @GetMapping("/api2/v4/orders")
    public List<OrderQueryDto> getOrderV3() {
       List<OrderQueryDto> result = findOrders();

       result.forEach(o -> {
           List<OrderItemQueryDto> orderItem = findOrderItem(o.getOrderId());
           o.setOrderItems(orderItem);
       });

       return result;


    }

    private List<OrderItemQueryDto> findOrderItem(Long id) {
       return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.id, i.name, oi.orderPrice, oi.count) from OrderItem oi" +
               " join oi.item i" +
               //왜 order지????
               " where oi.order.id = :id", OrderItemQueryDto.class)
               .setParameter("id", id)
               .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
       return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate,o.status,d.address) from Order o" +
               " join o.member m" +
               " join o.delivery d", OrderQueryDto.class)
               .getResultList();
    }

    private List<OrderItemQueryDto> findOrdrItem(Long orderId) {
       return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.id, oi.item.name, oi.orderPrice, oi.count)" +
               " from OrderItem oi" +
               " join oi.item i" +
               " where oi.order.id = :id" , OrderItemQueryDto.class)
               .setParameter("id", orderId)
               .getResultList();
    }


    @Data
    static class OrderDto {
        private Long id;
        private Address address;
        private String name;
        public OrderDto(Order o) {
           this.id = o.getId();
           this.name = o.getMember().getName();
           this.address = o.getDelivery().getAddress();

        }
    }
}
