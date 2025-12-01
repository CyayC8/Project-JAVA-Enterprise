package be.ucll.repositories;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String name;

    private String description;

    private Long price;

    @ManyToMany(mappedBy = "products")
    private List<OrderEntity> orders = new ArrayList<>();


}
