package com.asdt.domain;

import com.asdt.persistence.PersistentObject;

public class OrderLine extends PersistentObject {
    private Product product;
    private int quantity;

    public void setProduct(Product p) {
        product = p;
    }

    public void setQuantity(int q) {
        quantity = q;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString() {
        return "OrderLine: OID: [" + oid + "] Product: [" + product + "] Quantity: [" + quantity + "]";
    }
}
