package com.nathancrouther.sprintselftimer.view.results;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.nathancrouther.sprintselftimer.R;
import com.nathancrouther.sprintselftimer.controller.ResultsViewModel;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public final class ResultsActivity extends ActionBarActivity {
    private static final String TAG_VIEW_MODEL = "ViewModel";

    @InjectView(R.id.results_timestamp) TextView tvTimestamp;
    @InjectView(R.id.results_list) ListView lvList;
    @InjectView(R.id.results_unknownExplanation) TextView tvUnknownExplanation;

    public static Intent createIntent(Context context, ResultsViewModel vm) {
        Intent intent = new Intent(context, ResultsActivity.class);
        intent.putExtra(TAG_VIEW_MODEL, vm);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.inject(this);

        ResultsViewModel vm = getIntent().getParcelableExtra(TAG_VIEW_MODEL);
        tvTimestamp.setText(vm.getTimestamp());
        ResultsListAdapter adapter = new ResultsListAdapter(
                this,
                R.layout.item_results_list,
                Arrays.asList(
                        new ResultsListItem(
                                R.string.label_totalTime,
                                R.string.explanation_totalTime,
                                vm.getTotalTime()),
                        new ResultsListItem(
                                R.string.label_reactionTime,
                                R.string.explanation_reactionTime,
                                vm.getReactionTime()),
                        new ResultsListItem(
                                R.string.label_runningTime,
                                R.string.explanation_runningTime,
                                vm.getRunningTime())
                ));
        lvList.setAdapter(adapter);
        tvUnknownExplanation.setVisibility(
                vm.isDisplayUnknownExplanation()
                        ? View.VISIBLE
                        : View.GONE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
