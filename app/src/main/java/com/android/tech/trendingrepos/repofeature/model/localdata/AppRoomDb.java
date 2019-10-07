package com.android.tech.trendingrepos.repofeature.model.localdata;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.android.tech.trendingrepos.repofeature.model.localdata.dao.TrendingRepoDao;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;

@Database(entities = {TrendingRepoEntity.class}, version = 1, exportSchema = false)
@TypeConverters({TrendingRepoConvertor.class})
public abstract class AppRoomDb extends RoomDatabase {
    public abstract TrendingRepoDao repoDao();
}