package com.bs.twitterminer.analytics.infrastrucutre;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class KeywordStatisticsTest {

    private KeywordStatistics testObj = new KeywordStatistics();

    @Test
    public void testExtractReturnsMultipleOccurrences() {
        //setup
        String message = "dfksfdk fjsoijf soidf jdf Poker dsfdkfs dfoi Tournament jsdf oij";

        //act
        Map<String, Long> result = testObj.extract(message, Lists.newArrayList("Poker","Tournament"));

        //assert
        assertNotNull(result);
        assertThat(result, Matchers.allOf(hasEntry(is("Poker"), is(1L)), Matchers.hasEntry(is("Tournament"), is(1L))));
    }

    @Test
    public void testExtractReturnsOneOccurrence() {
        //setup
        String message = "Poker poker poker poker poker poker";

        //act
        Map<String, Long> result = testObj.extract(message, Lists.newArrayList("Poker","Tournament"));

        //assert
        assertNotNull(result);
        assertThat(result, Matchers.allOf(hasEntry(is("Poker"), is(1L))));
    }
}