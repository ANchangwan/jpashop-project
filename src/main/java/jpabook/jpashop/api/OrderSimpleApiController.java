package jpabook.jpashop.api;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;

import jpabook.jpashop.repository.OrderSimpleDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.*;

/*
* xToOne(ManyToOne, OneToOne)
* Order
* Order -> Member
* Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-order")
    public List<Order> orders(){
        List<Order> all = orderRepository.findByString(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleDto> orderV2(){
        return orderRepository.findByString(new OrderSearch()).stream()
                .map(o -> new OrderSimpleDto(o))
                .collect(toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleDto> orderV3(){
       List<Order> order = orderRepository.findWithMemberDelivery();
        List<OrderSimpleDto> result = orders().stream()
                .map(o -> new OrderSimpleDto(o))
                .collect(toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        return orderRepository.findOrderDtos();
    }


}
