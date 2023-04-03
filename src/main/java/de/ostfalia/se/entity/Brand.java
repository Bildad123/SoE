package de.ostfalia.se.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name ="brand_name")
    private String brandName;

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
}
