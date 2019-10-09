package com.android.tech.trendingrepos.repofeature.model.pojo;


import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class GitHubRepo {

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    /**
     * Considering this will be Unique for every trending repo list
     */
    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("stars")
    @Expose
    private Integer stars;
    @SerializedName("forks")
    @Expose
    private Integer forks;
    @SerializedName("currentPeriodStars")
    @Expose
    private Integer currentPeriodStars;
    @SerializedName("builtBy")
    @Expose
    private List<BuiltBy> builtBy = null;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("languageColor")
    @Expose
    private String languageColor;

    public GitHubRepo(String author, String name, String avatar) {
        this.author = author;
        this.name = name;
        this.avatar = avatar;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<BuiltBy> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(List<BuiltBy> builtBy) {
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

    public class BuiltBy {

        @SerializedName("href")
        @Expose
        private String href;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("username")
        @Expose
        private String username;

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GitHubRepo repo = (GitHubRepo) obj;

        return Objects.equals(url, repo.url);
    }


    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @NotNull
    @Override
    public String toString() {
        return author+url;
    }

}
