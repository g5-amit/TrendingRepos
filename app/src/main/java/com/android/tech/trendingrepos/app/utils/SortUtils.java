package com.android.tech.trendingrepos.app.utils;

import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;

import java.util.Collections;
import java.util.List;

public final class SortUtils {


    public static List<TrendingRepoEntity> sortByStars(List<TrendingRepoEntity> list) {
        Collections.sort(list, (e1, e2) -> e1.getStars().compareTo(e2.getStars()));
        Collections.reverse(list);

        return list;
    }
}
