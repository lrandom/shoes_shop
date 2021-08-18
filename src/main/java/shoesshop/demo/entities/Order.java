package shoesshop.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "total")
    Double total;

    @Column(name = "order_date")
    String orderDate;

    @Column(name = "received_address")
    String receivedAddress;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "status")
    Integer status;

    @OneToMany(mappedBy = "order" , fetch = FetchType.LAZY)
    Collection<OrderItem> orderItems;



}