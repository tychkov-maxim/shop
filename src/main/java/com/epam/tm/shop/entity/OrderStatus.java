package com.epam.tm.shop.entity;

/**
 * 1 - processing
 * 2 - shipping
 * 3 - completed
 **/
public class OrderStatus extends BaseEntity {


    private String name;

    public OrderStatus() {
    }

    public OrderStatus(Integer id, String name) {
        this.setId(id);
        this.name = name;
    }

    public static OrderStatus getProcessingStatus() {
        return new OrderStatus(1, "Processing");
    }

    public static OrderStatus getShippingStatus() {
        return new OrderStatus(2, "Shipping");
    }

    public static OrderStatus getCompletedStatus() {
        return new OrderStatus(3, "Completed");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
