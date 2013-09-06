/**
 *
 */
package com.soebes.testing.jsf.mock;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

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

	@Test
	public void accessTest() {
		FacesContext fc = mock(FacesContext.class);
		String value = "Test";

		UIInput element = mock(UIInput.class);
		doNothing().when(element).setValid(false);
		
		when(element.getClientId(fc)).thenReturn("THIS");
//		List<FacesMessage> messageList = new ArrayList<FacesMessage>();
		
		// when(fc.addMessage(element.getClientId(fc),new
		// FacesMessage(FacesMessage.SEVERITY_ERROR, getDefaultFehlertext(ui) +
		// " (" + value + ")", "")));

		// when(fc.addMessage("xx", )).
		// when(fc.get)
		Object result = t.getAsObject(fc, element, value);
		assertThat(fc.getMessages()).isNotEmpty();
		assertThat(fc.getMessageList()).isNotEmpty();
		assertThat(fc.getMessageList().size()).isEqualTo(1);
		
		assertThat(result).isNotNull();
	}

	@Test
	public void shouldReturnTheInputString() {
		FacesContext fc = null;
		UIComponent ui = mock(UIComponent.class);

		@SuppressWarnings("unchecked")
		Map<String, Object> map = mock(Map.class);
		when(map.get("formatString")).thenReturn("false");
		when(map.get("maxLenght")).thenReturn(null);
		
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
