package com.nathancrouther.sprintselftimer.model;

import java.util.Date;
import java.util.TimeZone;

public class Results {
    private final Date timestamp;
    private final TimeZone timeZone;
    private final long totalTimeMilliseconds;
    private final Long reactionTimeMilliseconds;

    public Results(
            Date timestamp,
            TimeZone timeZone,
            long totalTimeMilliseconds,
            Long reactionTimeMilliseconds) {
        this.timestamp = timestamp;
        this.timeZone = timeZone;
        this.totalTimeMilliseconds = totalTimeMilliseconds;
        this.reactionTimeMilliseconds = reactionTimeMilliseconds;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public long getTotalMilliseconds() {
        return totalTimeMilliseconds;
    }

    public boolean hasReactionTimeMilliseconds() {
        return reactionTimeMilliseconds != null;
    }

    public long getReactionMilliseconds() {
        return reactionTimeMilliseconds;
    }
}
