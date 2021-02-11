package fields;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DecimalDocument extends PlainDocument {

	@Override
	public void insertString(int offs, String str, AttributeSet a)
	    throws BadLocationException	{
		String allString = addChar(this.getText(0, this.getLength()), str.charAt(0), offs);
    	if (!Pattern.matches("^(-?[0-9]+\\.?[0-9]*|-?[0-9]*\\.[0-9]+)$", allString)) return;
    	try {
    		if(!"-".equals(allString))
    			new BigDecimal(allString);
    	} catch (Exception e) {
			return;
		}
    	super.insertString(offs, str, a);
	}
	
	public String addChar(String str, char ch, int position) {
	    return str.substring(0, position) + ch + str.substring(position);
	}
}
