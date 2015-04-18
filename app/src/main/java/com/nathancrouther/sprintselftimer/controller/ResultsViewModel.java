package com.nathancrouther.sprintselftimer.controller;

import android.os.Parcel;
import android.os.Parcelable;

import com.nathancrouther.sprintselftimer.model.Results;

import java.text.DateFormat;

public class ResultsViewModel implements Parcelable {
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public ResultsViewModel createFromParcel(Parcel in) {
                    return new ResultsViewModel(in);
                }

                public ResultsViewModel[] newArray(int size) {
                    return new ResultsViewModel[size];
                }
            };

    private final String timestamp;
    private final String totalTime;
    private final String reactionTime;
    private final String runningTime;
    private final boolean displayUnknownExplanation;

    public ResultsViewModel(Results results, String valueFormat, String unknownValueText) {
        DateFormat format = DateFormat.getDateTimeInstance();
        format.setTimeZone(results.getTimeZone());
        this.timestamp = format.format(results.getTimestamp());

        this.totalTime = formatResult(valueFormat, results.getTotalMilliseconds());
        if (results.hasReactionTimeMilliseconds()) {
            this.reactionTime = formatResult(valueFormat, results.getReactionMilliseconds());
            this.runningTime = formatResult(valueFormat,
                    results.getTotalMilliseconds() - results.getReactionMilliseconds());
            this.displayUnknownExplanation = false;
        } else {
            this.reactionTime = unknownValueText;
            this.runningTime = unknownValueText;
            this.displayUnknownExplanation = true;
        }
    }

    private ResultsViewModel(Parcel in) {
        timestamp = in.readString();
        totalTime = in.readString();
        reactionTime = in.readString();
        runningTime = in.readString();
        displayUnknownExplanation = in.readInt() != 0;
    }

    private static String formatResult(String format, long milliseconds) {
        return String.format(format, milliseconds / 1000.0);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public boolean isDisplayUnknownExplanation() {
        return displayUnknownExplanation;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(timestamp);
        dest.writeString(totalTime);
        dest.writeString(reactionTime);
        dest.writeString(runningTime);
        dest.writeInt(displayUnknownExplanation ? 1 : 0);
    }
}
