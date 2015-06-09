package org.bs.messaging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Config.class)
public class JsonMessageSerializerTest {

    @Autowired
    private JsonMessageSerializer testObj;
    private static final String JSON_TEXT = "{\"clientId\":\"12345\",\"keywords\":[\"hello\"]}";


    @Test
    public void testGetJsonReturnsCorrectResult() {
        //setup
        StartStreamCommand cmd = new StartStreamCommand("12345", Arrays.asList("hello"));

        //act
        String result = testObj.getJson(cmd);

        //assert
        assertEquals(JSON_TEXT, result);
    }

    @Test
    public void testGetObjectReturnsCorrectResult() {
        //setup
        StartStreamCommand cmd = new StartStreamCommand("12345", Arrays.asList("hello"));

        //act
        StartStreamCommand result = testObj.getObject(JSON_TEXT, StartStreamCommand.class);

        //assert
        assertEquals(cmd, result);
    }
}