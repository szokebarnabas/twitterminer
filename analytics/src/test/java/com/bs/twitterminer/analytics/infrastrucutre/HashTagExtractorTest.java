package com.bs.twitterminer.analytics.infrastrucutre;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class HashTagExtractorTest {

    private HashTagExtractor testObj = new HashTagExtractor();

    @Test
    public void testExtractCollectsAllHasTags() {
        //act
        Map<String, Long> result = testObj.extract("#Poker bla bla #Casino bla poker blab bla #Casino #Poker #Poker");

        //assert
        assertNotNull(result);
        assertThat(result, Matchers.allOf(hasEntry(is("#Poker"), is(3L)), Matchers.hasEntry(is("#Casino"), is(2L))));
    }
}