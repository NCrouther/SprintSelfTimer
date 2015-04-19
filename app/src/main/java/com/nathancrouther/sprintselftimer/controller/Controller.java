package com.nathancrouther.sprintselftimer.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.nathancrouther.sprintselftimer.R;
import com.nathancrouther.sprintselftimer.model.Database;
import com.nathancrouther.sprintselftimer.model.Results;
import com.nathancrouther.sprintselftimer.view.results.ResultsActivity;

import java.util.Date;
import java.util.TimeZone;

public class Controller {
    public static final Controller INSTANCE = new Controller();
    private Database database;

    private Controller() {
    }

    private Database getDatabase(Context context) {
        if (database == null) {
            database = new Database(context.getApplicationContext(), "database.db");
        }
        return database;
    }

    public void onMeasurementComplete(
            Context context,
            long totalTimeInMilliseconds,
            Long reactionTimeInMilliseconds) {
        Results results = new Results(
                new Date(),
                TimeZone.getDefault(),
                totalTimeInMilliseconds,
                reactionTimeInMilliseconds);
        showResultsActivity(context, results);
    }

    public void onViewAllResults(Context context) {
        Toast.makeText(
                context,
                String.format(
                        "%d results in database",
                        getDatabase(context).getAllResults().size()),
                Toast.LENGTH_LONG).show();
    }

    private void showResultsActivity(Context context, Results results) {
        getDatabase(context).addResults(results);

        ResultsViewModel resultsViewModel = new ResultsViewModel(
                results,
                context.getString(R.string.result_valueFormat),
                context.getString(R.string.result_unknownValue));
        Intent intent = ResultsActivity.createIntent(context, resultsViewModel);
        context.startActivity(intent);
    }
}
