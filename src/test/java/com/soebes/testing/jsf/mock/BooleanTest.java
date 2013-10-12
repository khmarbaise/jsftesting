package com.soebes.testing.jsf.mock;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

public class BooleanTest {

    @Test
    public void firstTest() {
        String value = "true";
        Boolean bool = Boolean.valueOf(value);
        assertThat(bool).isTrue();
    }

    @Test
    public void shouldReturnTrueWithTrailingSpacedValue() {
        String value = " true ";
        Boolean bool = Boolean.valueOf(value);
        assertThat(bool).isFalse();
    }

    @Test
    public void secondTest() {
        String value = "false";
        Boolean bool = Boolean.valueOf(value);
        assertThat(bool).isFalse();
    }

    @Test
    public void shouldReturnFalseFromANullValue() {
        String value = null;
        Boolean bool = Boolean.valueOf(value);
        assertThat(bool).isFalse();
    }
    @Test
    public void shouldReturnFalseFromAnEmptyValue() {
        String value = "";
        Boolean bool = Boolean.valueOf(value);
        assertThat(bool).isFalse();
    }

    @Test
    public void shouldReturnTrueFromATrueValueInDifferentCase() {
        String value = "True";
        Boolean bool = Boolean.valueOf(value);
        assertThat(bool).isTrue();
    }

}
