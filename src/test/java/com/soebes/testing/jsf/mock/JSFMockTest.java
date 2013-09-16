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
	private FacesContext facesContext;
	private UIComponent uiComponent;

	@BeforeMethod
	public void beforeMethod() {
		t = new BCIntegerConverter();
		facesContext = null;
		uiComponent = null;
	}

	private UIComponent setupConfigurationMapWithFormatting(String formatting) {
		UIComponent ui = mock(UIComponent.class);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mock(Map.class);
		when(map.get("maxLength")).thenReturn(null);
		when(map.get("errorText")).thenReturn(null);
		when(map.get("formatString")).thenReturn(formatting);
		
		when(ui.getAttributes()).thenReturn(map);
		return ui;
	}
	
	private @Captor ArgumentCaptor<FacesMessage> stringCaptor = ArgumentCaptor.forClass(FacesMessage.class);
	 
	@Test
	public void shouldReturnErrorMessageWithWrongInputValue() {
		final String clientIdForFC = "THIS";

		facesContext = mock(FacesContext.class);
		when(facesContext.getAttributes()).thenReturn(new HashMap<Object, Object>());

		UIInput element = mock(UIInput.class);
		doNothing().when(element).setValid(false);
		when(element.getClientId(facesContext)).thenReturn(clientIdForFC);

		String value = "Test";
		Object result = t.getAsObject(facesContext, element, value);
		verify(facesContext).addMessage(eq(clientIdForFC), stringCaptor.capture());

		assertThat(result).isNull();
		assertThat(stringCaptor.getValue().getSummary()).isEqualTo("Gültigen ganzzahligen Wert erfassen. (Ungültige Zeichen verwendet)");
		assertThat(stringCaptor.getValue().getSeverity()).isEqualTo(FacesMessage.SEVERITY_ERROR);
		//@TODO: The detail is really set to "" instead of null. This should be checked in the real code.
		assertThat(stringCaptor.getValue().getDetail()).isEqualTo("");
	}

	@Test
	public void shouldReturnTheInputStringWithFormattingNull() {
		UIComponent ui = setupConfigurationMapWithFormatting(null);

		Integer integer = new Integer("12345");

		Object result = t.getAsString(facesContext, ui, integer);
		assertThat(result).isEqualTo("12345");
	}

	@Test
	public void shouldReturnTheInputStringWithFormattingFalse() {
		UIComponent ui = setupConfigurationMapWithFormatting("false");

		Integer integer = new Integer("12345");

		Object result = t.getAsString(facesContext, ui, integer);
		assertThat(result).isEqualTo("12345");
	}

	@Test
	public void shouldReturnTheFormattedInputStringWithFormattingTrue() {
		uiComponent = setupConfigurationMapWithFormatting("true");

		Integer integer = new Integer("12345");

		Object result = t.getAsString(facesContext, uiComponent, integer);
		assertThat(result).isEqualTo("12.345");
	}

	@Test
	public void shouldReturnTheFormattedInputStringWith100000() {
		uiComponent = setupConfigurationMapWithFormatting("true");

		Integer integer = new Integer("123456");

		Object result = t.getAsString(facesContext, uiComponent, integer);
		assertThat(result).isEqualTo("123.456");
	}

	@Test
	public void shouldReturnNullWithNullValue() {
		String value = null;
		Object result = t.getAsObject(facesContext, uiComponent, value);
		assertThat(result).isNull();
	}

	@Test
	public void shouldReturnNullWithSpacesInValue() {
		String value = "   ";
		Object result = t.getAsObject(facesContext, uiComponent, value);
		assertThat(result).isNull();
	}

	@Test
	public void shouldReturnNullWithEmptyStringInValue() {
		String value = "";
		Object result = t.getAsObject(facesContext, uiComponent, value);
		assertThat(result).isNull();
	}

	@Test
	public void shouldReturnNullWithTabsInValue() {
		String value = "\t";
		Object result = t.getAsObject(facesContext, uiComponent, value);
		assertThat(result).isNull();
	}
}
