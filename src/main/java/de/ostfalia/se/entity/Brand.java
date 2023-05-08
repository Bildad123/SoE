package de.ostfalia.se.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer id;

    @Column(name ="brand_name")
    private String brandName;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private Set<Product> products = new HashSet<>();

    public Brand(Integer id, String brandName, Set<Product> products) {
        this.id = id;
        this.brandName = brandName;
        this.products = products;
    }

    public Brand() {
    }

    @Override
    public String toString() {
        return brandName;
    }

    // Getter und Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
