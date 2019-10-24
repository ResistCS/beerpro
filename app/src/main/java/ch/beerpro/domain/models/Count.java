package ch.beerpro.domain.models;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Count implements Entity {
    public static final String COLLECTION = "count";
    public static final String FIELD_ID = "id";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_BEER_ID = "beerId";
    public static final String FIELD_ADDED_AT = "addedAt";
    public static final Integer AMOUNT = 0;

    @Exclude
    private String id;
    private String userId;
    private String beerId;
    private Date addedAt;
    private Integer amount;

    public Count(){}

    public Count(String userId, String beerId, Date addedAt){
        this.userId = userId;
        this.beerId = beerId;
        this.addedAt = addedAt;
        this.amount = 1;
    }

    public Count(String userId, String beerId, Date addedAt, Integer amount){
        this.userId = userId;
        this.beerId = beerId;
        this.addedAt = addedAt;
        this.amount = amount;
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getBeerId() {
        return this.beerId;
    }

    public Date getAddedAt() {
        return this.addedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBeerId(String beerId) {
        this.beerId = beerId;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public static String generateId(String userId, String beerId) {
        return String.format("%s_%s", userId, beerId);
    }
}
