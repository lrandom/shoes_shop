package shoesshop.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    @Column(name = "pwd")
    String password;

    @Column(name="full_name")
    String fullName;

    @Column(name="role")
    Integer role;


}