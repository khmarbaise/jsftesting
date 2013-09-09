/**
 *
 */
package com.soebes.testing.jsf.mock;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

	@Captor ArgumentCaptor<FacesMessage> stringCaptor = ArgumentCaptor.forClass(FacesMessage.class);
	 
	@Test
	public void shouldReturnErrorMessageWithWrongInputValue() {
		final String clientIdForFC = "THIS";

		FacesContext fc = mock(FacesContext.class);
		when(fc.getAttributes()).thenReturn(new HashMap<Object, Object>());

		UIInput element = mock(UIInput.class);
		doNothing().when(element).setValid(false);
		when(element.getClientId(fc)).thenReturn(clientIdForFC);

		String value = "Test";
		Object result = t.getAsObject(fc, element, value);
		verify(fc).addMessage(eq(clientIdForFC), stringCaptor.capture());

		assertThat(result).isNull();
		assertThat(stringCaptor.getValue().getSummary()).isEqualTo("Gültigen ganzzahligen Wert erfassen. (Ungültige Zeichen verwendet)");
		assertThat(stringCaptor.getValue().getSeverity()).isEqualTo(FacesMessage.SEVERITY_ERROR);
	}

	@Test
	public void shouldReturnTheInputString() {
		FacesContext fc = null;
		UIComponent ui = mock(UIComponent.class);

		@SuppressWarnings("unchecked")
		Map<String, Object> map = mock(Map.class);
		when(map.get("formatString")).thenReturn("false");
		when(map.get("maxLenght")).thenReturn(null);
		when(map.get("errorText")).thenReturn(null);
		
		when(ui.getAttributes()).thenReturn(map);

		Integer integer = new Integer("12345");

		Object result = t.getAsString(fc, ui, integer);
		assertThat(result).isEqualTo("12345");
	}

	@Test
	public void shouldReturnTheFormattedInputString() {
		FacesContext fc = null;
		UIComponent ui = mock(UIComponent.class);

		@SuppressWarnings("unchecked")
		Map<String, Object> map = mock(Map.class);
		when(map.get("formatString")).thenReturn("true");
		when(map.get("maxLenght")).thenReturn(null);

		when(ui.getAttributes()).thenReturn(map);

		Integer integer = new Integer("12345");

		Object result = t.getAsString(fc, ui, integer);
		assertThat(result).isEqualTo("12.345");
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
