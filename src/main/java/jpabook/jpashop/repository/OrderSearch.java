package jpabook.jpashop.repository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName; //화면 이동
    private OrderStatus orderStatus; //주문 상태[Order, Cancel]


}
