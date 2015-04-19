package com.nathancrouther.sprintselftimer.view.results;

import android.view.View;
import android.widget.TextView;

import com.nathancrouther.sprintselftimer.R;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.item_results_list)
final class ResultsListItemViewHolder extends ItemViewHolder<ResultsListItem> {

    @ViewId(R.id.resultItemTitle)
    TextView tvTitle;

    @ViewId(R.id.resultItemExplanation)
    TextView tvExplanation;

    @ViewId(R.id.resultItemValue)
    TextView tvValue;

    public ResultsListItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(ResultsListItem resultsListItem, PositionInfo positionInfo) {
        tvTitle.setText(resultsListItem.getTitleResourceId());
        tvExplanation.setText(resultsListItem.getExplanationResourceId());
        tvValue.setText(resultsListItem.getValue());
    }
}
