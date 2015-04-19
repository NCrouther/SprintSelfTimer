package com.nathancrouther.sprintselftimer.controller;

import android.os.Parcel;
import android.os.Parcelable;

import com.nathancrouther.sprintselftimer.model.Results;

import java.util.ArrayList;
import java.util.List;

public class LogbookViewModel implements Parcelable {
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public LogbookViewModel createFromParcel(Parcel in) {
                    return new LogbookViewModel(in);
                }

                public LogbookViewModel[] newArray(int size) {
                    return new LogbookViewModel[size];
                }
            };
    private final List<LogbookItemViewModel> items;

    public LogbookViewModel(
            List<Results> resultsList,
            String valueFormat,
            String unknownValueText) {
        items = new ArrayList<LogbookItemViewModel>(resultsList.size());
        for (Results results : resultsList) {
            items.add(new LogbookItemViewModel(results, valueFormat, unknownValueText));
        }
    }

    private LogbookViewModel(Parcel in) {
        items = new ArrayList<LogbookItemViewModel>();
        in.readTypedList(items, LogbookItemViewModel.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
    }

    public List<LogbookItemViewModel> getItems() {
        return items;
    }
}
