package com.nathancrouther.sprintselftimer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.TimeZone;

@DatabaseTable
public class Results {
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    @DatabaseField(canBeNull = false)
    private long timestamp;
    @DatabaseField(canBeNull = false)
    private String timeZone;
    @DatabaseField(canBeNull = false)
    private long totalTimeMilliseconds;
    @DatabaseField(canBeNull = true)
    private Long reactionTimeMilliseconds;

    /**
     * Include no-arg constructor for ORMLite, but prevent others from mistakenly calling it.
     */
    @Deprecated
    public Results() {
    }

    public Results(
            Date timestamp,
            TimeZone timeZone,
            long totalTimeMilliseconds,
            Long reactionTimeMilliseconds) {
        this.timestamp = timestamp.getTime();
        this.timeZone = timeZone.getID();
        this.totalTimeMilliseconds = totalTimeMilliseconds;
        this.reactionTimeMilliseconds = reactionTimeMilliseconds;
    }

    public Date getTimestamp() {
        return new Date(timestamp);
    }

    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone(timeZone);
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
