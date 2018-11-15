package rest.dao.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Cuisine")

@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(name = "findCuisineByName", query = "FROM Cuisine WHERE name = :name"),
        @org.hibernate.annotations.NamedQuery(name = "findCuisineById", query = "FROM Cuisine WHERE id= :id"),
        @org.hibernate.annotations.NamedQuery(name = "findAllCuisine", query = "FROM Cuisine")})
public class Cuisine implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique=true)
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
