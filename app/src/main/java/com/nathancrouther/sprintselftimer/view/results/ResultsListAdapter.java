package com.nathancrouther.sprintselftimer.view.results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nathancrouther.sprintselftimer.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

final class ResultsListAdapter extends ArrayAdapter<ResultsListItem> {

    public ResultsListAdapter(Context context, int resource, List<ResultsListItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            view = li.inflate(R.layout.item_results_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ResultsListItem item = getItem(position);
        if (item != null) {
            holder.tvTitle.setText(item.getTitleResourceId());
            holder.tvExplanation.setText(item.getExplanationResourceId());
            holder.tvValue.setText(item.getValue());
        }

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.resultItemTitle) TextView tvTitle;
        @InjectView(R.id.resultItemExplanation) TextView tvExplanation;
        @InjectView(R.id.resultItemValue) TextView tvValue;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
