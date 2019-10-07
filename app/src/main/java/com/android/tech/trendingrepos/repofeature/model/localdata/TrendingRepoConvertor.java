package com.android.tech.trendingrepos.repofeature.model.localdata;

import androidx.room.TypeConverter;

import com.android.tech.trendingrepos.repofeature.model.pojo.GitHubRepo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TrendingRepoConvertor {

    @TypeConverter
    public static List<GitHubRepo.BuiltBy> fromBuiltByJson(String value) {
        if (value == null) {
            return null;
        }
        Type objType = new TypeToken<List<GitHubRepo.BuiltBy>>() {
        }.getType();
        return new Gson().fromJson(value, objType);
    }

    @TypeConverter
    public static String toJsonString(List<GitHubRepo.BuiltBy> builtByList) {
        if (builtByList == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(builtByList);
    }
}
