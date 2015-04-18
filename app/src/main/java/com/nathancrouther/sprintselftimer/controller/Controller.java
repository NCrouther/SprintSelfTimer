package com.nathancrouther.sprintselftimer.controller;

import android.content.Context;
import android.content.Intent;

import com.nathancrouther.sprintselftimer.R;
import com.nathancrouther.sprintselftimer.model.Results;
import com.nathancrouther.sprintselftimer.view.results.ResultsActivity;

import java.util.Date;
import java.util.TimeZone;

public class Controller {
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

    private void showResultsActivity(Context context, Results results) {
        ResultsViewModel resultsViewModel = new ResultsViewModel(
                results,
                context.getString(R.string.result_valueFormat),
                context.getString(R.string.result_unknownValue));
        Intent intent = ResultsActivity.createIntent(context, resultsViewModel);
        context.startActivity(intent);
    }
}
