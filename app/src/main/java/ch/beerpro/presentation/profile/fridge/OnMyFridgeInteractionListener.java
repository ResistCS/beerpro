package ch.beerpro.presentation.profile.fridge;

import android.widget.ImageView;

import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Count;

public interface OnMyFridgeInteractionListener {
    void onMoreClickedListener(ImageView photo, Beer beer);

    void onIncreaseClickedListener(Beer item, Count count);

    void onDecreaseClickedListener(Beer item, Count count);

    void onRemoveFromFridgeClickedListener(Beer item, Count count);
}
