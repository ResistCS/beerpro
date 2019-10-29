package ch.beerpro.presentation.profile.fridge;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ch.beerpro.data.repositories.BeersRepository;
import ch.beerpro.data.repositories.CurrentUser;
import ch.beerpro.data.repositories.FridgeRepository;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Count;

public class FridgeViewModel extends ViewModel implements CurrentUser {
    private final MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private final BeersRepository beersRepository;
    private final FridgeRepository fridgeRepository;

    public FridgeViewModel() {
        beersRepository = new BeersRepository();
        fridgeRepository = new FridgeRepository();
        currentUserId.setValue(getCurrentUser().getUid());
    }

    public LiveData<List<Pair<Count, Beer>>> getFridgeWithBeers() {
        return fridgeRepository.getMyCountlistWithBeers(currentUserId, beersRepository.getAllBeers());
    }


}
