package com.nathancrouther.sprintselftimer.view.logbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.nathancrouther.sprintselftimer.R;
import com.nathancrouther.sprintselftimer.controller.LogbookViewModel;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.ribot.easyadapter.EasyAdapter;

public final class LogbookActivity extends ActionBarActivity {
    private static final String TAG_VIEW_MODEL = "ViewModel";

    @InjectView(R.id.logbook_list)
    ListView lvList;

    public static Intent createIntent(Context context, LogbookViewModel vm) {
        Intent intent = new Intent(context, LogbookActivity.class);
        intent.putExtra(TAG_VIEW_MODEL, Parcels.wrap(vm));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logbook);
        ButterKnife.inject(this);

        LogbookViewModel vm = Parcels.unwrap(getIntent().getParcelableExtra(TAG_VIEW_MODEL));
        lvList.setAdapter(new EasyAdapter<>(
                this,
                LogbookItemViewHolder.class,
                vm.getItems()));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
