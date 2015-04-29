package com.nathancrouther.sprintselftimer.controller;

import com.nathancrouther.sprintselftimer.model.Results;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class LogbookViewModel {
    private final List<LogbookItemViewModel> items;

    @ParcelConstructor
    LogbookViewModel(List<LogbookItemViewModel> items) {
        this.items = items;
    }

    public LogbookViewModel(
            List<Results> resultsList,
            String valueFormat,
            String unknownValueText) {
        items = new ArrayList<LogbookItemViewModel>(resultsList.size());
        for (Results results : resultsList) {
            items.add(new LogbookItemViewModel(results, valueFormat, unknownValueText));
        }
    }

    public List<LogbookItemViewModel> getItems() {
        return items;
    }
}
