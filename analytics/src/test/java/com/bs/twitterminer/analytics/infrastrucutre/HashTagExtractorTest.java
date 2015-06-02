package com.bs.twitterminer.analytics.infrastrucutre;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class HashTagExtractorTest {

    private HashTagExtractor testObj;

    @Test
    public void testExtractCollectsAllHasTags() {
        //setup
        testObj = new HashTagExtractor("#Poker bla bla #Casino bla poker blab bla #Casino #Poker #Poker");

        //act
        Map<String, Long> result = testObj.extract();

        //assert
        assertNotNull(result);
        assertThat(result, Matchers.allOf(hasEntry(is("#Poker"), is(3L)), Matchers.hasEntry(is("#Casino"), is(2L))));
    }
}