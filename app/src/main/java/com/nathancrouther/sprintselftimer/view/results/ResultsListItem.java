package com.nathancrouther.sprintselftimer.view.results;

final class ResultsListItem {
    private final int titleResourceId;
    private final int explanationResourceId;
    private final String value;

    public ResultsListItem(int titleResourceId, int explanationResourceId, String value) {
        this.titleResourceId = titleResourceId;
        this.explanationResourceId = explanationResourceId;
        this.value = value;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public int getExplanationResourceId() {
        return explanationResourceId;
    }

    public String getValue() {
        return value;
    }
}
