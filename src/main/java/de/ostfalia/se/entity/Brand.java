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
    private Long id;

    @Column(name ="brand_name")
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private Set<Product> products = new HashSet<>();

    public Brand(Long id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public Brand() {
    }

    @Override
    public String toString() {
        return "Brands{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                '}';
    }

    //Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
