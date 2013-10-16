package com.soebes.testing.jsf.mock;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

public class IntegerTest {

    @Test
    public void firstTest() {
        String value = "1234";
        Integer result = Integer.valueOf(value);
        assertThat(result).isEqualTo(1234);
    }
    
    @Test(expectedExceptions = { NumberFormatException.class })
    public void secondTest() {
        String value = "1234a";
        Integer result = Integer.valueOf(value);
        assertThat(result).isEqualTo(1234);
    }
    
    @Test(expectedExceptions = { NumberFormatException.class })
    public void thirdTest() {
        String value = null;
        Integer result = Integer.valueOf(value);
        assertThat(result).isEqualTo(null);
    }

}
