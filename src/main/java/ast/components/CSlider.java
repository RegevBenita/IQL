package ast.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.BoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CSlider implements Component {
	private JSlider jSlider;
	private final String name;
	private final String prompt;
	private int minVal;
	private int maxVal;
	private int defVal;
	private final List<Constraint> constraints;
	private final JWindow toolTip = new JWindow();
	private final JLabel label = new JLabel("", SwingConstants.CENTER);
	private final Dimension tooltipDim = new Dimension(30, 20);
	private int prevValue = -1;

	public CSlider(String name, String prompt, int minVal, int maxVal, int defVal, List<Constraint> constraints) {
		this.name = name;
		this.prompt = prompt;
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.defVal = defVal;
		this.constraints = constraints;
	}

	public CSlider(ComponentContext ctx) {
		this.name = extractCompName(ctx);
		this.prompt = extractCompTitle(ctx);
		setMinMaxValues(ctx);
		setDefaultVal(ctx);
		this.constraints = extractConstraints(ctx);
		setTooltip();
	}

	private void setTooltip() {
		label.setOpaque(false);
		label.setBackground(UIManager.getColor("ToolTip.background"));
		label.setBorder(UIManager.getBorder("ToolTip.border"));
		toolTip.add(label);
		toolTip.setSize(tooltipDim);
	}

	private void setMinMaxValues(ComponentContext ctx) {
		String idText = ctx.CompId().getText();
		String valueText = idText.substring(idText.indexOf("[") + 2, idText.indexOf("]")-1);
		if (valueText == null || valueText.isEmpty() || valueText.endsWith(",") || valueText.startsWith(","))
			throw new IllegalArgumentException("'" + valueText + "' is not a valid option for slider");
		String[] values = valueText.trim().split(",");
		if (values.length != 2)
			throw new IllegalArgumentException(valueText + " must contain 2 numbers seperated by comma");
		try {
			minVal = Integer.valueOf(values[0].trim());
			maxVal = Integer.valueOf(values[1].trim());
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid Slider default values");
		}
		if (minVal >= maxVal) {
			throw new NumberFormatException("Slider max value must be bigger than the min value");
		}
	}

	private void setDefaultVal(ComponentContext ctx) {
		String value = extractCompDefVal(ctx);
		if ("".equals(value))
			defVal = minVal;
		else {
			try {
				defVal = Integer.valueOf(value);
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Invalid Slider default values");
			}
		}
		if (minVal > defVal || maxVal < defVal) {
			throw new NumberFormatException("Default value must be between or equal to the min and max values");
		}
	}

	protected void updateToolTip(MouseEvent e) {
		int intValue = (int) jSlider.getValue();
		if (prevValue != intValue) {
			label.setText(String.valueOf(jSlider.getValue()));
			Point pt = e.getPoint();
			pt.y = -tooltipDim.height;
			SwingUtilities.convertPointToScreen(pt, e.getComponent());
			pt.translate(-tooltipDim.width / 2, 0);
			toolTip.setLocation(pt);
		}
		prevValue = intValue;
	}

	public JPanelWithValue make() {
		JSlider jSlider = generateJSlider();
		jSlider.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {}

			@Override
			public void mouseDragged(MouseEvent e) {
				updateToolTip(e);
			}
		});

		jSlider.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (UIManager.getBoolean("Slider.onlyLeftMouseButtonDrag") && SwingUtilities.isLeftMouseButton(e)) {
					toolTip.setVisible(true);
					updateToolTip(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				toolTip.setVisible(false);
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int i = (int) jSlider.getValue() - e.getWheelRotation();
				BoundedRangeModel m = jSlider.getModel();
				jSlider.setValue(Math.min(Math.max(i, m.getMinimum()), m.getMaximum()));
			}

		});

		JPanelWithValue panel = new JPanelWithValue(Id.Slider, this, getName(), prompt) {
			@Override
			public boolean checkForError() {
				return false;
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if (setDefault) {
					jSlider.setValue(defVal);
				} else {
					jSlider.setValue(Integer.valueOf(value));
				}
			}
		};
		panel.setValueOrDefault("", true);
		panel.setValue(String.valueOf(getDefVal()));
		panel.setLayout(new GridBagLayout());
		jSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				panel.setValue(String.valueOf(jSlider.getValue()));
			}
		});
		Map<ConstraintId, Constraint> constraints = getMapConstraint(getConstraints());
		JLabel title = generateTitle(prompt, Map.of());
		DisplayId display = (DisplayId) constraints.get(ConstraintId.DISPLAY);
		if (display == DisplayId.Non) {
			display = DisplayId.Block;
		}
		return switch (display) {
		case Block -> setSliderBlockDisplay(title, jSlider, panel);
		case Inline -> setSliderInlineDisplay(title, jSlider, panel);
		default -> throw new IllegalArgumentException("Unexpected value: " + display);
		};
	}

	private JSlider generateJSlider() {
		JSlider jSlider = new JSlider(getMinVal(), getMaxVal(), getDefVal());
		this.jSlider = jSlider;
		jSlider.setLabelTable(null);
		Map<ConstraintId, Constraint> constraints = getMapConstraint(getConstraints());
		if (constraints.containsKey(ConstraintId.MAJORTICKS))
			jSlider.setMajorTickSpacing(((Constraint.MajorTicksCon) constraints.get(ConstraintId.MAJORTICKS)).value());
		else
			jSlider.setMajorTickSpacing(getMaxVal() - getMinVal());
		if (constraints.containsKey(ConstraintId.MINORTICKS))
			jSlider.setMinorTickSpacing(((Constraint.MinorTicksCon) constraints.get(ConstraintId.MINORTICKS)).value());
		jSlider.setPaintTicks(true);
		jSlider.setPaintLabels(true);
		return jSlider;
	}

	private JPanelWithValue setSliderInlineDisplay(JLabel title, JSlider slider, JPanelWithValue panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		slider.setPreferredSize(new Dimension(326, 50));
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		JPanel sliderPanel = new JPanel(new GridBagLayout());
		sliderPanel.setPreferredSize(new Dimension(326, 50));
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		sliderPanel.add(slider, gbc);
		gbc.gridx = 1;
		panel.add(sliderPanel, gbc);
		return panel;
	}

	private JPanelWithValue setSliderBlockDisplay(JLabel title, JSlider slider, JPanelWithValue panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		slider.setPreferredSize(new Dimension(326, 50));
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(slider, gbc);
		gbc.gridx = 1;
		panel.setPreferredSize(new Dimension(326, (int) panel.getPreferredSize().getHeight()));
		return panel;
	}

	public String getName() {
		return name;
	}

	public String getPrompt() {
		return prompt;
	}

	public int getMinVal() {
		return minVal;
	}

	public int getMaxVal() {
		return maxVal;
	}

	public int getDefVal() {
		return defVal;
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	@Override
	public Id getType() {
		return Id.Slider;
	}

	@Override
	public String toString() {
		return "Slider [name=" + name + ", prompt=" + prompt + ", minVal=" + minVal + ", maxVal=" + maxVal + ", defVal="
				+ defVal + ", constraints=" + constraints + "]";
	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitSlider(this);
	}
}
