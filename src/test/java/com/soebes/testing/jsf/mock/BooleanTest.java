package com.soebes.testing.jsf.mock;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class BooleanTest
{

    @Test
    public void firstTest()
    {
        Boolean bool = Boolean.valueOf( "true" );
        assertThat( bool ).isTrue();
    }

    @Test
    public void shouldReturnTrueWithTrailingSpacedValue()
    {
        Boolean bool = Boolean.valueOf( " true " );
        assertThat( bool ).isFalse();
    }

    @Test
    public void secondTest()
    {
        Boolean bool = Boolean.valueOf( "false" );
        assertThat( bool ).isFalse();
    }

    @Test
    public void shouldReturnFalseFromANullValue()
    {
        Boolean bool = Boolean.valueOf( null );
        assertThat( bool ).isFalse();
    }

    @Test
    public void shouldReturnFalseFromAnEmptyValue()
    {
        Boolean bool = Boolean.valueOf( "" );
        assertThat( bool ).isFalse();
    }

    @Test
    public void shouldReturnTrueFromATrueValueInDifferentCase()
    {
        Boolean bool = Boolean.valueOf( "True" );
        assertThat( bool ).isTrue();
    }

}
