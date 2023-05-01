package de.ostfalia.se.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderItemPK implements Serializable {
    private Integer id;
    private Order order;

    public OrderItemPK(Integer id, Order order) {
        this.id = id;
        this.order = order;
    }

    public OrderItemPK() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPK that = (OrderItemPK) o;
        return Objects.equals(id, that.id) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order);
    }
}
