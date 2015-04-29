package com.nathancrouther.sprintselftimer.controller;

import com.nathancrouther.sprintselftimer.model.Results;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.text.DateFormat;

@Parcel(Parcel.Serialization.BEAN)
public class LogbookItemViewModel {
    private final String timestamp;
    private final String totalTime;
    private final String reactionTime;
    private final String runningTime;

    @ParcelConstructor
    LogbookItemViewModel(String timestamp, String totalTime, String reactionTime, String runningTime) {
        this.timestamp = timestamp;
        this.totalTime = totalTime;
        this.reactionTime = reactionTime;
        this.runningTime = runningTime;
    }

    LogbookItemViewModel(Results results, String valueFormat, String unknownValueText) {
        DateFormat format = DateFormat.getDateTimeInstance();
        format.setTimeZone(results.getTimeZone());
        this.timestamp = format.format(results.getTimestamp());

        this.totalTime = formatResult(valueFormat, results.getTotalMilliseconds());
        if (results.hasReactionTimeMilliseconds()) {
            this.reactionTime = formatResult(valueFormat, results.getReactionMilliseconds());
            this.runningTime = formatResult(valueFormat,
                    results.getTotalMilliseconds() - results.getReactionMilliseconds());
        } else {
            this.reactionTime = unknownValueText;
            this.runningTime = unknownValueText;
        }
    }

    private static String formatResult(String format, long milliseconds) {
        return String.format(format, milliseconds / 1000.0);
    }
    public String getTimestamp() {
        return timestamp;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getReactionTime() {
        return reactionTime;
    }

    public String getRunningTime() {
        return runningTime;
    }
}
