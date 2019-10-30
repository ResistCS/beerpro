package ch.beerpro.domain.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Rating implements Entity {
    public static final String COLLECTION = "ratings";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_LIKES = "likes";
    public static final String FIELD_CREATION_DATE = "creationDate";

    @Exclude
    private String id;
    private String beerId;
    private String beerName;
    private String userId;
    private String userName;
    private String userPhoto;
    private String photo;
    private float rating;
    private String comment;
    private String placeId;
    private Integer clarity;
    private Integer body;
    private Integer sweet;
    private Integer bitter;

    /**
     * We use a Map instead of an Array to be able to query it.
     *
     * @see <a href="https://firebase.google.com/docs/firestore/solutions/arrays#solution_a_map_of_values"/>
     */
    private Map<String, Boolean> likes;
    private Date creationDate;

    public Rating(String id, String beerId, String beerName, String userId, String userName, String userPhoto, String photo, float rating, String comment, String placeId, Integer clarity, Integer body, Integer sweet, Integer bitter, Map<String, Boolean> likes, Date creationDate) {
        this.id = id;
        this.beerId = beerId;
        this.beerName = beerName;
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.photo = photo;
        this.rating = rating;
        this.comment = comment;
        this.placeId = placeId;
        this.clarity = clarity;
        this.body = body;
        this.sweet = sweet;
        this.bitter = bitter;
        this.likes = likes;
        this.creationDate = creationDate;
    }

    public Rating() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeerId() {
        return beerId;
    }

    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
    }

    public Integer getSweet() {
        return sweet;
    }

    public void setSweet(Integer sweet) {
        this.sweet = sweet;
    }

    public Integer getBitter() {
        return bitter;
    }

    public void setBitter(Integer bitter) {
        this.bitter = bitter;
    }

    public Map<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Boolean> likes) {
        this.likes = likes;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    private boolean canEqual(final Object other) {
        return other instanceof Rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return Float.compare(rating1.getRating(), getRating()) == 0 &&
                getClarity() == rating1.getClarity() &&
                getBody() == rating1.getBody() &&
                getSweet() == rating1.getSweet() &&
                getBitter() == rating1.getBitter() &&
                Objects.equals(getId(), rating1.getId()) &&
                Objects.equals(getBeerId(), rating1.getBeerId()) &&
                Objects.equals(getBeerName(), rating1.getBeerName()) &&
                Objects.equals(getUserId(), rating1.getUserId()) &&
                Objects.equals(getUserName(), rating1.getUserName()) &&
                Objects.equals(getUserPhoto(), rating1.getUserPhoto()) &&
                Objects.equals(getPhoto(), rating1.getPhoto()) &&
                Objects.equals(getComment(), rating1.getComment()) &&
                Objects.equals(getPlaceId(), rating1.getPlaceId()) &&
                Objects.equals(getLikes(), rating1.getLikes()) &&
                Objects.equals(getCreationDate(), rating1.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBeerId(), getBeerName(), getUserId(), getUserName(), getUserPhoto(), getPhoto(), getRating(), getComment(), getPlaceId(), getClarity(), getBody(), getSweet(), getBitter(), getLikes(), getCreationDate());
    }

    @NonNull
    public String toString() {
        return "Rating{" +
                "id='" + id + '\'' +
                ", beerId='" + beerId + '\'' +
                ", beerName='" + beerName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", photo='" + photo + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", placeId='" + placeId + '\'' +
                ", clarity=" + clarity +
                ", body=" + body +
                ", sweet=" + sweet +
                ", bitter=" + bitter +
                ", likes=" + likes +
                ", creationDate=" + creationDate +
                '}';
    }
}
