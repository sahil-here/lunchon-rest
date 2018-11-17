package rest.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Restaurant {

    @NotEmpty(message = "field is missing")
    @JsonProperty("id")
    private String id;

    @NotEmpty(message = "field is missing")
    @JsonProperty("name")
    private String name;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("price")
    private String price;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("alias")
    private String alias;

    @JsonProperty("is_closed")
    private Boolean isClosed;

    @JsonProperty("url")
    private String url;

    @JsonProperty("coordinates")
    private Coordinates coordinates;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("location")
    private Location location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", price='" + price + '\'' +
                ", phone='" + phone + '\'' +
                ", alias='" + alias + '\'' +
                ", isClosed=" + isClosed +
                ", url='" + url + '\'' +
                ", coordinates=" + coordinates +
                ", imageUrl='" + imageUrl + '\'' +
                ", location=" + location +
                '}';
    }
}
