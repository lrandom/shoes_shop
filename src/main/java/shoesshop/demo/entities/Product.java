package shoesshop.demo.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    Double price;

    @Column(name="description")
    String description;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name="discount")
    Double discount;

    @Column(name="status")
    Integer status;

    @Column(name="picture")
    String picture;
}
