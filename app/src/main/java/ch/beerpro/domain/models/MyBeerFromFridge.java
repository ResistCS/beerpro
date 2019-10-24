package ch.beerpro.domain.models;

import java.util.Date;

public class MyBeerFromFridge implements MyBeer {
    private Count count;
    private Beer beer;

    public MyBeerFromFridge(Count count, Beer beer){
        this.beer = beer;
        this.count = count;
    }

    @Override
    public String getBeerId() {
        return count.getBeerId();
    }

    @Override
    public Date getDate() {
        return count.getAddedAt();
    }

    public Count getWish() {
        return this.count;
    }

    public Beer getBeer() {
        return this.beer;
    }

    public void setWish(Count count) {
        this.count = count;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

}
