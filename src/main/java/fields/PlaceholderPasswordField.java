package fields;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPasswordField;

public class PlaceholderPasswordField extends JPasswordField {
	private String placeholder;
	
    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || !String.valueOf(getPassword()).isEmpty()) {
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
