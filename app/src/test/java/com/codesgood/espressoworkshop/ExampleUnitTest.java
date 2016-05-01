package com.codesgood.espressoworkshop;

import com.codesgood.espressoworkshop.models.UnitTestExample;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void fullNameTest() throws Exception {
        UnitTestExample example = new UnitTestExample("Pedro", "Torres");
        assertEquals("Pedro Torres", example.getFullName());
    }
}