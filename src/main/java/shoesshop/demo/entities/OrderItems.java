package shoesshop.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "quantity")
    Integer quantity;


    @Column(name = "price")
    Double price;
}
