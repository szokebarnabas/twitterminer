package com.bs.twitterminer.analytics.domain;

import com.bs.twitterminer.analytics.domain.UserHashTags;
import com.google.common.collect.Maps;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;

public class UserHashTagsTest {

    private UserHashTags testObj = new UserHashTags();

    @Before
    public void setup() {
        Map<String,Long> hashTags = Maps.newHashMap();
        hashTags.put("#yolo", 5L);
        hashTags.put("#poker", 5L);
        hashTags.put("#tournament", 1L);
        testObj.setSummarizedHashTags(hashTags);
    }

    @Test
    public void testSumHashTags()  {
        //setup
        Map<String, Long> newHashTags = Maps.newHashMap();
        newHashTags.put("#yolo", 2L);
        newHashTags.put("#poker", 3L);

        //act
        testObj.sumHashTags(newHashTags);

        //assert
        Assert.assertThat(testObj.getSummarizedHashTags(), Matchers.allOf(hasEntry(is("#yolo"), is(7L)),
                Matchers.hasEntry(is("#tournament"), is(1L)), Matchers.hasEntry(is("#poker"), is(8L))));
    }
}