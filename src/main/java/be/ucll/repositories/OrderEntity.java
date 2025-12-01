package be.ucll.repositories;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @ManyToOne
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products = new ArrayList<>();

    private Long totaalBedrag;

    private Integer aantalProducten;

    private Boolean afgeleverd;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getTotaalBedrag() {
        return totaalBedrag;
    }

    public void setTotaalBedrag(Long totaalBedrag) {
        this.totaalBedrag = totaalBedrag;
    }

    public Integer getAantalProducten() {
        return aantalProducten;
    }

    public void setAantalProducten(Integer aantalProducten) {
        this.aantalProducten = aantalProducten;
    }

    public Boolean getAfgeleverd() {
        return afgeleverd;
    }

    public void setAfgeleverd(Boolean afgeleverd) {
        this.afgeleverd = afgeleverd;
    }








}
