package jpabook.jpashop.domain.Service;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import jpabook.jpashop.domain.repository.MemberRepository;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;



    @Test
    public void 상품주문() throws Exception{
      //given
        Member member = new Member();
        member.setName("Kim1");
        member.setAddress(new Address("서울", "도봉구", "1111"));
        em.persist(member);

        Book book = new Book();
        book.setName("서울 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격 = 가격 * 수량 이다.", 10000*orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다.", 8 ,book.getStockQuantity());

    }

    @Test
    public void 주문취소() throws Exception{
      //given

      //when

      //then

    }

    @Test(expected = NotEnoughStockException.class)
    @DisplayName("주문수량초과 TEST")
    public void 주문수량초과() throws Exception{
        //given
        Item item = new Item();
        item.setStockQuantity(3);
        //when
        OrderItem.creatOrderItem(item, 1000, 4 );

        //then
        fail("need more stock");
    }
}