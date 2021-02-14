package regel.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private String house;

    @Column(name = "flat")
    private String flat;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "modified")
    private LocalDateTime modified;

    @OneToMany(mappedBy = "registeredAddress")
    List<Customer> customersWithThisAsRegisteredAddress;

    @OneToMany(mappedBy = "actualAddress")
    List<Customer> customersWithThisAsActualAddress;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public List<Customer> getCustomersWithThisAsRegisteredAddress() {
        return customersWithThisAsRegisteredAddress;
    }

    public void setCustomersWithThisAsRegisteredAddress(List<Customer> customersWithThisAsRegisteredAddress) {
        this.customersWithThisAsRegisteredAddress = customersWithThisAsRegisteredAddress;
    }

    public List<Customer> getCustomersWithThisAsActualAddress() {
        return customersWithThisAsActualAddress;
    }

    public void setCustomersWithThisAsActualAddress(List<Customer> customersWithThisAsActualAddress) {
        this.customersWithThisAsActualAddress = customersWithThisAsActualAddress;
    }
}
