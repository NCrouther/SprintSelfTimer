package com.nathancrouther.sprintselftimer.view.logbook;

import android.view.View;
import android.widget.TextView;

import com.nathancrouther.sprintselftimer.R;
import com.nathancrouther.sprintselftimer.controller.LogbookItemViewModel;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.item_logbook_list)
public class LogbookItemViewHolder extends ItemViewHolder<LogbookItemViewModel> {

    @ViewId(R.id.logbookItemTimestamp)
    TextView tvTimestamp;

    @ViewId(R.id.logbookItemTotalTime)
    TextView tvTotalTime;

    @ViewId(R.id.logbookItemReactionTime)
    TextView tvReactionTime;

    @ViewId(R.id.logbookItemRunningTime)
    TextView tvRunningTime;

    public LogbookItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(LogbookItemViewModel logbookItemViewModel, PositionInfo positionInfo) {
        tvTimestamp.setText(logbookItemViewModel.getTimestamp());
        tvTotalTime.setText(logbookItemViewModel.getTotalTime());
        tvReactionTime.setText(logbookItemViewModel.getReactionTime());
        tvRunningTime.setText(logbookItemViewModel.getRunningTime());
    }
}
