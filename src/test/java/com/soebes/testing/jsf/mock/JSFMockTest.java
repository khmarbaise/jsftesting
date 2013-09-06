/**
 *
 */
package com.soebes.testing.jsf.mock;

import static org.fest.assertions.Assertions.assertThat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import de.barmergek.pdv.web.converter.BCIntegerConverter;

/**
 * @author Karl Heinz Marbaise
 * 
 */
public class JSFMockTest {
    private BCIntegerConverter t;

    @BeforeMethod
    public void beforeMethod() {
        t = new BCIntegerConverter();
    }

    @Test
    public void accessTest() {
        FacesContext fc = null;
        UIComponent ui = null;
        String value = "Test";

         UIInput element = null;
//         when(element.setValid(true));

        // UIInput element = (UIInput) _component;
        // element.setValid(false);
         when(fc.addMessage(element.getClientId(fc),new FacesMessage(FacesMessage.SEVERITY_ERROR, getDefaultFehlertext(ui) + " (" + value + ")", ""));

        // when(fc.addMessage("xx", )).
        // when(fc.get)
        Object result = t.getAsObject(fc, ui, value);
        assertThat(result).isNotNull();
        // assertThat(true).isTrue();
    }

    @Test
    public void shouldReturnNullWithNullValue() {
        FacesContext fc = null;
        UIComponent ui = null;
        String value = null;
        Object result = t.getAsObject(fc, ui, value);
        assertThat(result).isNull();
    }

    @Test
    public void shouldReturnNullWithSpacesInValue() {
        FacesContext fc = null;
        UIComponent ui = null;
        String value = "   ";
        Object result = t.getAsObject(fc, ui, value);
        assertThat(result).isNull();
    }

    @Test
    public void shouldReturnNullWithEmptyStringInValue() {
        FacesContext fc = null;
        UIComponent ui = null;
        String value = "";
        Object result = t.getAsObject(fc, ui, value);
        assertThat(result).isNull();
    }

    @Test
    public void shouldReturnNullWithTabsInValue() {
        FacesContext fc = null;
        UIComponent ui = null;
        String value = "\t";
        Object result = t.getAsObject(fc, ui, value);
        assertThat(result).isNull();
    }
}
