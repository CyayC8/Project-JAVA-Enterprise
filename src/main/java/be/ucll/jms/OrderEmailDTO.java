package be.ucll.jms;

import java.io.Serializable;

public class OrderEmailDTO implements Serializable {
    private Long orderId;
    private String customerName;
    private String customerEmail;
    private Integer aantalProducten;
    private Long totaalBedrag;
    private Boolean afgeleverd;

    // Constructor
    public OrderEmailDTO(Long orderId, String customerName, String customerEmail, Integer aantalProducten, Long totaalBedrag, Boolean afgeleverd) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.aantalProducten = aantalProducten;
        this.totaalBedrag = totaalBedrag;
        this.afgeleverd = afgeleverd;
    }

    //Data Transfer Object
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Integer getAantalProducten() {
        return aantalProducten;
    }

    public void setAantalProducten(Integer aantalProducten) {
        this.aantalProducten = aantalProducten;
    }

    public Long getTotaalBedrag() {
        return totaalBedrag;
    }

    public void setTotaalBedrag(Long totaalBedrag) {
        this.totaalBedrag = totaalBedrag;
    }

    public Boolean getAfgeleverd() {
        return afgeleverd;
    }

    public void setAfgeleverd(Boolean afgeleverd) {
        this.afgeleverd = afgeleverd;
    }

}