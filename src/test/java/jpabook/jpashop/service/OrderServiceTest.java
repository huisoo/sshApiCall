package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private OrderRepository orderRepository;

    @Test //주문
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();
        Book book = createBook("bookA", 3000, 10);

        int orderCount = 2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertEquals(1, getOrder.getOrderItems().size(), "주문 한 상품수가 하나여야 한다.");
        assertEquals(3000*orderCount, getOrder.getTotalPrice(), "주문가격은 수량 * 가격이다.");
        assertEquals(8, book.getStockQuantity());
    }



    @Test //주문취소
    public void 상품주문_재고수량초과() throws Exception{
        Member member = createMember();
        Book book = createBook("bookA", 3000, 10);

        int orderCount = 11;


        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
            fail("재고수량 부족 예외가 발생해야 한다.");
        });


    }


    @Test //주문취소
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("BookA", 3000, 10);
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        //when
        orderService.cancel(orderId);

        //then
        Order order = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, order.getStatus(),"주문상태가 캔슬");
        assertEquals(10, item.getStockQuantity());
    }


    private Book createBook(String name, int price, int quant) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quant);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("userA");
        member.setAddress(new Address("성남시", "양현로", "114"));
        em.persist(member);
        return member;
    }
}