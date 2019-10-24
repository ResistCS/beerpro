package ch.beerpro.presentation.profile.fridge;

import android.widget.ImageView;

import ch.beerpro.domain.models.Beer;

public interface OnMyFridgeInteractionListener {
    void onFridgeClickedListener(Beer beer);
    void onMoreClickedListener(ImageView photo, Beer beer);
}
