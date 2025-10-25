package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/*
* 총 주문 2개
* * userA
* JPA1 BOOK
* JPA2 BOOK
* * userB
*   * SPRING1 BOOK
*   * SPRING2 BOOK
 */


@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        @PersistenceContext
        private EntityManager em;

        public void dbInit2(){
            Member member = getMember("userB", "서울", "1", "1111");
            em.persist(member);

            Book book1 = getBook("spring1",1000, 100);
            em.persist(book1);

            Book book2 = getBook("spring2",2000, 200);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }


        public void dbInit1(){
            Member member = getMember("userA", "인천", "1", "1111");
            em.persist(member);

            Book book1 = getBook("book1",1000, 100);
            em.persist(book1);

            Book book2 = getBook("book2",2000, 200);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book getBook(String bookName, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(bookName);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }
        private Member getMember(String userName, String street, String city, String zipcode) {
            Member member = new Member();
            member.setName(userName);
            member.setAddress(new Address(street, city, zipcode));
            return member;
        }
    }
}

