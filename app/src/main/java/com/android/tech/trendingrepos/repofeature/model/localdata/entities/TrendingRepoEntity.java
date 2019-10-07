package com.android.tech.trendingrepos.repofeature.model.localdata.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.tech.trendingrepos.repofeature.model.pojo.GitHubRepo;

import java.util.List;

@Entity(tableName = "TrendingRepoEntity")
public class TrendingRepoEntity {

    @Ignore
    private static final long STALE_TIME = 2 * 60 * 60 * 1000; // Data is stale after 2 hrs

    /**
     * Considering this will be Unique for every trending repo list
     */
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "repoUrl")
    private String url;

    private String author;

    private String name;

    private String avatar;

    private String description;

    private Integer stars;

    private Integer forks;

    private Integer currentPeriodStars;

    /**
     * Since nothing is unique in BuiltBy
     * hence we can't use Foreign key here
     * saved as Json String in Room DB using TypeConvertor
     */
    private List<GitHubRepo.BuiltBy> builtBy ;

    private String language;

    private String languageColor;

    private Long timeStamp;

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Integer getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(Integer currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public List<GitHubRepo.BuiltBy> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(List<GitHubRepo.BuiltBy> builtBy) {
        this.builtBy = builtBy;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public boolean isUpToDate() {
        return System.currentTimeMillis() - timeStamp < STALE_TIME;
    }
}
