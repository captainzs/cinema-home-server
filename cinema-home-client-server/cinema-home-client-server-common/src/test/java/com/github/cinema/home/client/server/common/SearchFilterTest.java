package com.github.cinema.home.client.server.common;

import com.github.cinema.home.client.server.common.types.SearchFilter;
import com.github.cinema.home.client.server.common.types.SortBy;
import com.github.cinema.home.client.server.common.types.SortOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SearchFilterTest {
    @Test
    public void givenSearchFilter_whenToString_assertHasOverriddenToStringMethod() {
        SearchFilter filter = SearchFilter.builder()
                .subText("test")
                .mandatory3d(true)
                .sortBy(SortBy.POPULARITY)
                .sortOrder(SortOrder.DESCENDING)
                .build();
        Assert.assertEquals("SearchFilter{subText='test', genres=[], 3d=true, sortBy=POPULARITY, sortOrder=DESCENDING}", filter.toString());
    }
}
