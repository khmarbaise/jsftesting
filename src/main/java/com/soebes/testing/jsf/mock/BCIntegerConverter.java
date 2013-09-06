package com.soebes.testing.jsf.mock;

import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Dieser Konverter wandelt einen Integer um. 
 * Optionale Attribute: - maxLength
 * gibt die maximal erlaubte Länge des Integers an. - formatString = true
 * bedeutet, dass der String mit Tausendertrennzeichen ausgegeben wird. -
 * errorText gibt den Fehlertext an
 */
public class BCIntegerConverter implements Converter {
	/**
	 * Konstanten für Fehlertexte
	 */
	private static final String ERROR_UNGUELTIGE_ZEICHEN = "Ungültige Zeichen verwendet";
	private static final String ERROR_WERT_ZU_GROSS = "Wert zu groß";

	private String getDefaultFehlertext(UIComponent _component) {
		String errorText = (String) _component.getAttributes().get("errorText");
		if (errorText == null)
			errorText = "Gültigen ganzzahligen Wert erfassen.";
		return errorText;
	}

	private Object parseString(String _eingabe, UIComponent _component) {
		// Eingabe in Stringbuffer umwandeln
		StringBuffer sb = new StringBuffer(_eingabe.trim());

		// Führende Nullen, Leerzeichen und Punkte aus Eingabe entfernen
		boolean ersteZahlUngleichNull = false;
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);
			if ((c == '0' && ersteZahlUngleichNull == false) || c == ' '
					|| c == '.') {
				sb.deleteCharAt(i);
				i--;
			} else {
				if (c < '0' || c > '9')
					return ERROR_UNGUELTIGE_ZEICHEN;
				ersteZahlUngleichNull = true;
			}
		}

		// Wenn Eingabe komplett leer, 0 an erster Stelle einfügen
		while (sb.length() < 1)
			sb.insert(0, '0');

		int maxLength = sb.length();
		String max = (String) _component.getAttributes().get("maxLength");
		if (max != null) {
			try {
				maxLength = Integer.parseInt(max);
			} catch (Exception e) {
			}
		}

		if (sb.length() > maxLength)
			return ERROR_WERT_ZU_GROSS;

		return new Integer(Integer.parseInt(sb.toString()));
	}

	@Override
	public Object getAsObject(FacesContext _fc, UIComponent _component,
			String _value) {
		String eingabe = (_value == null) ? "" : _value.trim();
		if ((eingabe.length() == 0))
			return null;

		// Eingabe aus GUI prüfen
		Object obj = parseString(eingabe, _component);

		if (obj instanceof String) {
			UIInput element = (UIInput) _component;
			element.setValid(false);
			_fc.addMessage(element.getClientId(_fc), new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					getDefaultFehlertext(_component) + " (" + obj + ")", ""));
			return null;
		}

		return obj;
	}

	@Override
	public String getAsString(FacesContext _fc, UIComponent _component,
			Object _obj) {
		if (_obj == null || !(_obj instanceof Integer))
			return "";

		String st = (String) _component.getAttributes().get("formatString");
		boolean formatString = (st != null && st.equalsIgnoreCase("true"));
		if (formatString) {
			NumberFormat df = NumberFormat.getNumberInstance(Locale.GERMANY);
			df.setGroupingUsed(true);
			df.setMinimumIntegerDigits(1);
			return df.format(((Integer) _obj).longValue());
		}

		return _obj.toString();
	}
}