package com.android.tech.trendingrepos.repofeature.model.localdata.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import java.util.List;
import io.reactivex.Single;

/**
 * Room Dao interface
 */
@Dao
public interface TrendingRepoDao {

    @Query("SELECT * FROM TrendingRepoEntity ")
    Single<List<TrendingRepoEntity>> getTrendingRepoEntityList();

    @Query("SELECT * FROM TrendingRepoEntity WHERE repoUrl = :url")
    Single<TrendingRepoEntity> getTrendingRepoById(String url);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrendingRepoEntity(TrendingRepoEntity trendingRepoEntity);

    @Query("DELETE FROM TrendingRepoEntity WHERE repoUrl = :url")
    int deleteTrendingRepoById(String url);

    /**
     * Delete all Users (items).
     */
    @Query("DELETE FROM TrendingRepoEntity")
    void deleteAllTrendingRepo();

    @Update
    int updateTrendingRepo(TrendingRepoEntity trendingRepoEntity);
}

