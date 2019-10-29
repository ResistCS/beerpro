package ch.beerpro.presentation.profile.fridge;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.beerpro.R;
import ch.beerpro.domain.models.Beer;
import ch.beerpro.domain.models.Count;
import ch.beerpro.presentation.details.DetailsActivity;

public class FridgeActivity extends AppCompatActivity implements OnMyFridgeInteractionListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.emptyView)
    View emptyView;

    private FridgeViewModel model;
    private FridgeRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mein Kühlschrank");

        model = ViewModelProviders.of(this).get(FridgeViewModel.class);
        model.getFridgeWithBeers().observe(this, this::updateFridge);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FridgeRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void updateFridge(List<Pair<Count, Beer>> entries) {
        adapter.submitList(entries);
        if (entries.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMoreClickedListener(ImageView animationSource, Beer beer) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.ITEM_ID, beer.getId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, animationSource, "image");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onIncreaseClickedListener(Beer beer, Count count) {
        TextView view = findViewById(R.id.currentAmount);
        int i = Integer.parseInt(view.getText().toString()) + 1;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String coundId = Count.generateId(count.getUserId(), count.getBeerId());
        DocumentReference wishEntryQuery = db.collection(Count.COLLECTION).document(coundId);
        wishEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                wishEntryQuery.update("amount", i);
                view.setText(i);
                return null;
            } else {
                throw task.getException();
            }
        });
    }

    @Override
    public void onDecreaseClickedListener(Beer beer, Count count) {
        TextView view = findViewById(R.id.currentAmount);
        int i = Integer.parseInt(view.getText().toString()) - 1;
        if (i < 0) {
            i = 0;
        }
        int x = i;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String coundId = Count.generateId(count.getUserId(), count.getBeerId());
        DocumentReference wishEntryQuery = db.collection(Count.COLLECTION).document(coundId);
        wishEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                wishEntryQuery.update("amount", x);
                view.setText(x);
                return null;
            } else {
                throw task.getException();
            }
        });
    }

    @Override
    public void onRemoveFromFridgeClickedListener(Beer beer, Count count) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String coundId = Count.generateId(count.getUserId(), count.getBeerId());
        DocumentReference wishEntryQuery = db.collection(Count.COLLECTION).document(coundId);
        wishEntryQuery.get().continueWithTask(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                wishEntryQuery.delete();
                return null;
            } else {
                throw task.getException();
            }
        });
    }
}
