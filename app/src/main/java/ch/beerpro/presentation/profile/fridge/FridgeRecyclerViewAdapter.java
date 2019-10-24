package ch.beerpro.presentation.profile.fridge;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;

import androidx.recyclerview.widget.DiffUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.GlideApp;
import ch.beerpro.R;
import ch.beerpro.data.repositories.FridgeRepository;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Count;
import ch.beerpro.presentation.utils.EntityPairDiffItemCallback;

public class FridgeRecyclerViewAdapter extends ListAdapter<Pair<Count, Beer>, FridgeRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "FridgeRecyclerViewAda";

    private static final DiffUtil.ItemCallback<Pair<Count, Beer>> DIFF_CALLBACK = new EntityPairDiffItemCallback<>();

    private final OnMyFridgeInteractionListener listener;

    public FridgeRecyclerViewAdapter(OnMyFridgeInteractionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_fridge_listentry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Pair<Count, Beer> item = getItem(position);
        holder.bind(item.first, item.second, listener);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.manufacturer)
        TextView manufacturer;

        @BindView(R.id.category)
        TextView category;

        @BindView(R.id.photo)
        ImageView photo;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        @BindView(R.id.numRatings)
        TextView numRatings;

        @BindView(R.id.increaseAmount)
        Button increase;

        @BindView(R.id.currentAmount)
        TextView currentAmount;

        @BindView(R.id.decreaseAmount)
        Button decrease;

        @BindView(R.id.removeFromFridge)
        Button remove;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        void bind(Count count, Beer item, OnMyFridgeInteractionListener listener) {
            String s = String.valueOf(count.getAmount());
            FridgeRepository fridgeRepository = new FridgeRepository();
            name.setText(item.getName());
            manufacturer.setText(item.getManufacturer());
            category.setText(item.getCategory());
            name.setText(item.getName());
            GlideApp.with(itemView).load(item.getPhoto()).apply(new RequestOptions().override(240, 240).centerInside())
                    .into(photo);
            ratingBar.setNumStars(5);
            ratingBar.setRating(item.getAvgRating());
            numRatings.setText(itemView.getResources().getString(R.string.fmt_num_ratings, item.getNumRatings()));
            itemView.setOnClickListener(v -> listener.onMoreClickedListener(photo, item));
            currentAmount.setText(s);
            increase.setOnClickListener(v -> listener.onIncreaseClickedListener(item, count));
            decrease.setOnClickListener(v -> listener.onDecreaseClickedListener(item, count));
            remove.setOnClickListener(v -> listener.onRemoveFromFridgeClickedListener(item, count));
        }

    }
}
