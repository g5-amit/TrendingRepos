package com.android.tech.trendingrepos.app.utils;

import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;

import java.util.Collections;
import java.util.List;

public final class SortUtils {


    public static List<TrendingRepoEntity> sortByNewest(List<TrendingRepoEntity> list) {
        Collections.sort(list, (e1, e2) -> e1.getTimeStamp().compareTo(e2.getTimeStamp()));
        Collections.reverse(list);

        return list;
    }
}
