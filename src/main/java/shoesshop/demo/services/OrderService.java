package shoesshop.demo.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shoesshop.demo.entities.CartItem;
import shoesshop.demo.entities.Order;
import shoesshop.demo.entities.OrderItem;
import shoesshop.demo.entities.Product;
import shoesshop.demo.jpa.OrderItemJPA;
import shoesshop.demo.jpa.OrderJPA;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderJPA orderJPA;
    @Autowired
    private OrderItemJPA orderItemJPA;

    public Order findById(long id) {
        return orderJPA.findById(id).get();
    }

    //hàm lên đơn
    public void makeOrder(String shipAddress,
                          long userId,
                          double total,
                          ArrayList<CartItem> cartItems) {

        Order order = new Order();
        order.setReceivedAddress(shipAddress);
        order.setUserId(userId);
        order.setTotal(total);
        order.setOrderDate("2021-08-02");
        orderJPA.save(order);

        for (int i = 0; i < cartItems.size(); i++) {
            OrderItem orderItem = new OrderItem();
            CartItem cartItem = cartItems.get(i);
            orderItem.setName(cartItem.getName());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrderId(order.getId());
            orderItem.setProductid(cartItem.getId());
            orderItemJPA.save(orderItem);
        }
    }

    public ListResult getOrdersListByUserId(long userId, int page) {
        OrderService.ListResult listResult = new OrderService.ListResult();
        listResult.setListOrder(orderJPA.getOrderByUserId(userId, PageRequest.of(page - 1, 10)));
        listResult.setActivePage(page);
        double totalPage = Math.ceil((double) orderJPA.count() / 10);
        listResult.setTotalPage(totalPage);
        return listResult;
    }

    @Data
    public class ListResult {
        Iterable<Order> listOrder;
        int activePage;
        double totalPage;

    }
}
