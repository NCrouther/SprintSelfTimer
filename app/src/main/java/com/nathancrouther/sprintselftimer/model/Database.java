package com.nathancrouther.sprintselftimer.model;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.List;

public class Database {
    private final DatabaseHelper helper;
    private final RuntimeExceptionDao<Results, Integer> resultsDao;

    public Database(Context applicationContext, String path) {
        helper = new DatabaseHelper(applicationContext, path);
        resultsDao = helper.getRuntimeExceptionDao(Results.class);
    }

    @Override
    public void finalize() throws Throwable {
        helper.close();
        super.finalize();
    }

    public void addResults(Results results) {
        resultsDao.create(results);
    }

    public List<Results> getAllResults() {
        try {
            return resultsDao.queryBuilder().orderBy("timestamp", false).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
