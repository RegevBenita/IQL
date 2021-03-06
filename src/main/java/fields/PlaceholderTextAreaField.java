package fields;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextArea;

public class PlaceholderTextAreaField extends JTextArea {
private String placeholder;


	public PlaceholderTextAreaField(int rows, int columns) {
	    super(rows, columns);
	}
	
    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.gray);
        g.drawString(placeholder, getInsets().left, 
        	pG.getFontMetrics().getMaxAscent() + getInsets().top + getInsets().bottom-2);
    }

    public String getPlaceholder() {
        return placeholder;
    }
    
    public void setPlaceholder(final String s) {
        placeholder = s;
    }
}
