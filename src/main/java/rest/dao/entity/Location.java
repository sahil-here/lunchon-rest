package rest.dao.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Location", indexes = { @Index(columnList = "id", name = "location_id_index_on_location")})

@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "idempotencyCheckForLocation", query = "FROM Location WHERE latitude = :latitude " +
                " and longitude = :longitude and radius=:radius and radius_unit= :radius_unit and zipcode= :zipcode and city= :city" +
                " and state = :state and country= :country"),
        @org.hibernate.annotations.NamedQuery(name = "findLocationById", query = "FROM Location WHERE id= :id")})
public class Location implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "radius")
    private Float radius;

    @Column(name = "radius_unit")
    private String radiusUnit;

    @Column(name = "zipcode")
    private Integer zipcode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public String getRadiusUnit() {
        return radiusUnit;
    }

    public void setRadiusUnit(String radiusUnit) {
        this.radiusUnit = radiusUnit;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
