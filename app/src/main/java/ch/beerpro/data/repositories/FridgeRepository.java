package ch.beerpro.data.repositories;

import android.util.Pair;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Entity;
import ch.beerpro.domain.models.Count;
import ch.beerpro.domain.utils.FirestoreQueryLiveData;
import ch.beerpro.domain.utils.FirestoreQueryLiveDataArray;

import static androidx.lifecycle.Transformations.map;
import static androidx.lifecycle.Transformations.switchMap;
import static ch.beerpro.domain.utils.LiveDataExtensions.combineLatest;

public class FridgeRepository {


    private static LiveData<List<Count>> getCountsByUser(String userId) {
        return new FirestoreQueryLiveDataArray<>(FirebaseFirestore.getInstance().collection(Count.COLLECTION)
                .orderBy(Count.FIELD_ADDED_AT, Query.Direction.DESCENDING).whereEqualTo(Count.FIELD_USER_ID, userId),
                Count.class);
    }

    private static LiveData<Count> getUserCountListFor(Pair<String, Beer> input) {
        String userId = input.first;
        Beer beer = input.second;
        DocumentReference document = FirebaseFirestore.getInstance().collection(Count.COLLECTION)
                .document(Count.generateId(userId, beer.getId()));
        return new FirestoreQueryLiveData<>(document, Count.class);
    }

    public Task<Void> toggleUserCountlistItem(String userId, String itemId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String coundId = Count.generateId(userId, itemId);

        DocumentReference wishEntryQuery = db.collection(Count.COLLECTION).document(coundId);

        return wishEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                return wishEntryQuery.delete();
            } else if (task.isSuccessful()) {
                return wishEntryQuery.set(new Count(userId, itemId, new Date(), 1));
            } else {
                throw task.getException();
            }
        });
    }

    public LiveData<List<Pair<Count, Beer>>> getMyCountlistWithBeers(LiveData<String> currentUserId,
                                                                   LiveData<List<Beer>> allBeers) {
        return map(combineLatest(getMyCountlist(currentUserId), map(allBeers, Entity::entitiesById)), input -> {
            List<Count> wishes = input.first;
            HashMap<String, Beer> beersById = input.second;

            ArrayList<Pair<Count, Beer>> result = new ArrayList<>();
            for (Count wish : wishes) {
                Beer beer = beersById.get(wish.getBeerId());
                result.add(Pair.create(wish, beer));
            }
            return result;
        });
    }

    public LiveData<List<Count>> getMyCountlist(LiveData<String> currentUserId) {
        return switchMap(currentUserId, FridgeRepository::getCountsByUser);
    }


    public LiveData<Count> getMyCountForBeer(LiveData<String> currentUserId, LiveData<Beer> beer) {


        return switchMap(combineLatest(currentUserId, beer), FridgeRepository::getUserCountListFor);
    }

}
