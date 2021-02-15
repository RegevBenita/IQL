package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.ranges.RangeException;

import ast.Ast.Containable;
import ast.Ast.Dialog;
import ast.Ast.Query;
import ast.Id;
import ast.components.*;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import fields.*;
import iql.IQL;

public class TabularVisitor extends UiVisitor {
	private static final String BLOCK = "block";
	private static final String BLOCK_LIST = "blockList";
	private static final int PROMPT = 0;
	private static final int FIELD = 1;
	private int minEntries = 1;
	private int maxEntries = Integer.MAX_VALUE;
	private String approveBtnLabel = "Approve";
	private String cancelBtnLabel = "Cancel";
	private List<Containable> containers;
	private int fieldsRowsCounter = 0;

	@Override
	public JFrame visitQuery(Query query) {
		Dialog dialog = query.dialog();
		String title = dialog.getTitle();
		String desc = dialog.getDescription();
		JFrame jFrame = makeFrame(title);
		setDialogConstraits(dialog.getConstraints());
		containers = query.containers();
		List<JPanelContainer> panels = new ArrayList<>();
		for(Containable container:containers)
			panels.add(getPanel(container));
		constructDialog(jFrame, panels, desc);
		jFrame.pack();
		jFrame.setVisible(true);
		return jFrame;
	}
	
	private void setDialogConstraits(List<Constraint> constraints) {
		if(constraints == null || constraints.isEmpty())
			return;
		for(Constraint constraint: constraints) {
			ConstraintId id = constraint.getID();
			switch (id) {
			case MIN -> setMinPages(constraint);
			case MAX -> setMaxPages(constraint);
			case CANCEL -> setCancelBtnLabel(constraint);
			case APPROVE -> setApproveBtnLabel(constraint);
			default ->
			throw new IllegalArgumentException(id + " ,unsoppurted constraint by Pages dialog");
			}
		}		
	}
	
	private void setApproveBtnLabel(Constraint constraint) {
		approveBtnLabel = ((Constraint.ApproveCon)constraint).value();
	}

	private void setCancelBtnLabel(Constraint constraint) {
		cancelBtnLabel = ((Constraint.CancelCon)constraint).value();
	}

	private void setMinPages(Constraint constraint) {
		double value = ((Constraint.MinCon)constraint).value();
		if(value < 1 || value > maxEntries)
			throw new IllegalArgumentException("Dialog min constraint must be greater or equals"
					+ " to one and small or equals than max constraint.");
		minEntries = (int)value;
		if(minEntries != value)
			throw new IllegalArgumentException(value + " is not a valid value. The value must be an integer");
	}
	
	private void setMaxPages(Constraint constraint) {
		double value = ((Constraint.MaxCon)constraint).value();
		if(value < minEntries)
			throw new IllegalArgumentException("Dialog max constraint positive and greater or equal to min constraint.");
		maxEntries = (int)value;
		if(maxEntries != value)
			throw new IllegalArgumentException(value + " is not a valid value. The value must be an integer");
	}

	@Override
	protected void constructDialog(JFrame frame, List<JPanelContainer> panels, String desc) {
		List<Map<String, String>> results = new ArrayList<>();
		JTextArea jDesc = generateDesc(desc);
		JPanel fieldsContainer = new JPanel(new GridBagLayout());
		JScrollPane scroll = new JScrollPane(fieldsContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc = addFrameComponents(frame, panels, jDesc, scroll);
		addFrameButtons(frame, panels, results, gbc, fieldsContainer);
		int height = getDialogMaxHeight();
		int frameWidth = frame.getPreferredSize().width;
		if(frameWidth > getDialogMaxWidth())
			throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, "Dialog width is bigger than the screen width");
		scroll.setMinimumSize(new Dimension(frameWidth, (int) (height*0.7-150)));
		frame.setMinimumSize(new Dimension(frameWidth, (int) (height*0.7)));
	}

	private GridBagConstraints addFrameComponents(JFrame frame, List<JPanelContainer> panels, JTextArea jDesc,
			JScrollPane scroll) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 10, 0, 0);
		frame.add(jDesc, gbc);
		List<JLabel> titles = getComponentsTitles(panels);
		JPanel titlePanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 7, 0, 0);
		for(JLabel title : titles) {
			title.setPreferredSize(new Dimension(138, 22));
			gbc.gridx++;
			titlePanel.add(title, gbc);	
		}
		titlePanel.setMinimumSize(new Dimension(1000, 22));
		gbc.gridx = 0;
		titlePanel.setBorder(new EmptyBorder(0,14,0,10));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		frame.add(titlePanel, gbc);
		
		gbc.weighty = 1.0;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.PAGE_START;
		
		scroll.setMaximumSize(new Dimension(100, 150));
		frame.add(scroll, gbc);
		return gbc;
	}

	private void addFrameButtons(JFrame frame, List<JPanelContainer> panels, List<Map<String, String>> results,
			GridBagConstraints gbc, JPanel fieldsContainer) {
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		buttonsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		JButton cancelButton = new JButton(cancelBtnLabel);
		JButton approveButton = new JButton(approveBtnLabel);
		JButton addButton = new JButton("Add");
		addNewFields(frame, fieldsContainer, addButton);
		disableButton(fieldsContainer);
	
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				if(getData().isDone())
					return;
				results.clear();
				results.add(getEmptyEntries(panels));
				getData().complete(results);
				frame.dispose();
		    }
		});

		addButton.addActionListener(e -> {
			List<Map<String, String>> res = new ArrayList<>();
			var haveError = updateData(fieldsContainer.getComponents(), res);
			if(haveError) 
				return;
			addNewFields(frame, fieldsContainer, addButton);
			if(fieldsRowsCounter == maxEntries)
				addButton.setEnabled(false);
		});
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEmptyEntries(panels));
			getData().complete(results);
			frame.dispose();
		});
		
		approveButton.addActionListener(e->{
			if(fieldsRowsCounter < minEntries) {
				JOptionPane.showMessageDialog(frame,
						"You must have a least " + minEntries + " entries",
						"Warning",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			List<Map<String, String>> res = new ArrayList<>();
			var haveError = updateData(fieldsContainer.getComponents(), res);
			if(!haveError) {
				getData().complete(res);
				frame.dispose();
			}});
		
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		buttonsPanel.add(approveButton, gbc);
		gbc.insets = new Insets(5, 5, 5, 0);
		gbc.gridx = 1;
		buttonsPanel.add(cancelButton, gbc);
		gbc.gridx = 2;
		buttonsPanel.add(addButton, gbc);
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 10;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 0; 
		gbc.anchor = GridBagConstraints.PAGE_END;
		frame.add(buttonsPanel, gbc);
	}

	private boolean updateData(Component[] components, List<Map<String, String>> results) {
		if(components == null)
			return true;
		boolean haveErrors = false;
		for(int i=0; i<components.length; i++) {
			Map<String, String> map = new HashMap<>();
			if(components[i] instanceof JPanel jPanel) {
				for(int j=0; j<jPanel.getComponentCount(); j++) {
					haveErrors |= checkComponentErrors(map, haveErrors, jPanel.getComponent(j));
				}
				results.add(map);
			}			
		}
		return haveErrors;		
	}

	private void addNewFields(JFrame frame, JPanel fieldsContainer, JButton addButton) {
		enableButton(fieldsContainer);
		GridBagConstraints gbc = new GridBagConstraints();
		List<JPanelContainer> panels = new ArrayList<>();
		JPanel row = new JPanel(new GridBagLayout());
		for(Containable container:containers)
			panels.add(getPanel(container));
		setMultiLayout(panels);
		gbc.insets = new Insets(5, 0, 5, 0);
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		ImageIcon trashImg = new ImageIcon(this.getClass().getResource("/images/trash.png"));
		Image trashDim = trashImg.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon deleteIcon = new ImageIcon(trashDim);
		JButton deleteButton = new JButton();
		deleteButton.setIcon(deleteIcon);
		deleteButton.setPreferredSize(new Dimension(22, 22));

		row.add(deleteButton, gbc);
		for(JPanel panel : panels) {
			gbc.gridx++;
			gbc.insets = new Insets(5, 5, 0, 0);
			row.add(panel, gbc);	
		}
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.PAGE_START;
		fieldsContainer.add(row, gbc);

		deleteButton.addActionListener(e -> {
			fieldsContainer.remove(row);
			disableButton(fieldsContainer);
			fieldsRowsCounter--;
			addButton.setEnabled(true);
			frame.validate();
		});
		fieldsRowsCounter++;
		frame.validate();
	}
	
	private void disableButton(JPanel fieldsContainer) {
		if(fieldsContainer.getComponentCount() == 1) {
			JButton button = (JButton)(((JPanel)fieldsContainer.getComponent(0)).getComponent(0));
			button.setEnabled(false);
		}
	}
	
	private void enableButton(JPanel fieldsContainer) {
		if(fieldsRowsCounter > 0) {
			JButton button = (JButton)(((JPanel)fieldsContainer.getComponent(0)).getComponent(0));
			button.setEnabled(true);
		}
	}
	
	@Override
	protected Map<String, String> getEmptyEntries(List<JPanelContainer> panels) {
		Map<String, String> data = new IQL.EmptyEntriesMap();
		for(JPanelContainer panel: panels) {
			checkComponentErrors(data, false, panel);
		}
		return setNullValues(data);
	}
	
	private List<JLabel> getComponentsTitles(List<JPanelContainer> panels) {
		List<JLabel> titles = new ArrayList<>();
		for(JPanelContainer panel: panels)
		if(panel instanceof JPanelWithValue panelWithValue) {
			ast.components.Component component = panelWithValue.getComponent();
			String prompt = component.getPrompt();
			List<Constraint> constraints = component.getConstraints();
			JLabel title = component.generateTitle(prompt, component.getMapConstraint(constraints));
			titles.add(title);
		}
		return titles;
	}
	
	private void setMultiLayout(List<JPanelContainer> panels) {
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				setMultiTabLayout(panel);
			else if(panel instanceof JPanelWithValue panelWithValue) {
				setMultiComponentLayout(panelWithValue);
			}
		}
	}
	
	private void setMultiTabLayout(JPanelContainer panel) {
		if(panel.getComponents().length == 0)
			return;
		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
		for(int i=0; i<tabPanel.getTabCount(); i++) {
			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
			java.awt.Component[] comps = tab.getComponents();
			for(java.awt.Component comp: comps) {
				if(comp instanceof JPanelWithValue panelWithValue) {
					setMultiComponentLayout(panelWithValue);
				}
			}
		}
	}
	
	private void setMultiComponentLayout(JPanelWithValue panelWithValue) {
		panelWithValue.getErrorLabel().setVisible(false);
		panelWithValue.getComponent(PROMPT).setVisible(false);
		if(panelWithValue.getComponent(FIELD) instanceof PlaceholderPasswordField)
			panelWithValue.getComponent(FIELD).setPreferredSize(new Dimension(128, 22));
		else
			panelWithValue.getComponent(FIELD).setPreferredSize(new Dimension(140, 22));
	}

	@Override
	public JPanelContainer visitGroup(Group group) {
		throw new IllegalArgumentException("Tabular dialog do not support groups");
	}
	
	@Override
	public JPanelContainer visitTab(Tab tab) {
		throw new IllegalArgumentException("Tabular dialog do not support tabs");
	}

	private List<Constraint> setTabularDisplay(Id id, List<Constraint> constraints, String display) {
		constraints = constraints.stream()
			.filter(con -> con.getID() != ConstraintId.DISPLAY)
			.collect(Collectors.toList());
		Constraint constraintId = ConstraintId.from(id, display);
		constraints.add(constraintId);
		return constraints;
	}

	@Override
	public JPanelWithValue visitString(CString component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setTabularDisplay(id, constraints, BLOCK);
		CString cString = new CString(component.getName(), component.getPrompt(), component.getDefVal() , newConstraints);
		return cString.make();
	}
	
	@Override
	public JPanelWithValue visitTextArea(CTextArea component) {
		throw new IllegalArgumentException("TextArea is not supported in Tabular dialog");
	}

	@Override
	public JPanelWithValue visitPassword(CPassword component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setTabularDisplay(id, constraints, BLOCK);
		CPassword cPassword = new CPassword(component.getName(), component.getPrompt(), component.getDefVal() , newConstraints);
		return cPassword.make();
	}

	@Override
	public JPanelWithValue visitInteger(CInteger component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setTabularDisplay(id, constraints, BLOCK);
		CInteger cInteger = new CInteger(component.getName(), component.getPrompt(), component.getDefVal() , newConstraints);
		return cInteger.make();
	}

	@Override
	public JPanelWithValue visitDecimal(CDecimal component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setTabularDisplay(id, constraints, BLOCK);
		CDecimal cDecimal = new CDecimal(component.getName(), component.getPrompt(), component.getDefVal() , newConstraints);
		return cDecimal.make();
	}

	@Override
	public JPanelWithValue visitBoolean(CBoolean component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setTabularDisplay(id, constraints, BLOCK_LIST);
		CBoolean cBoolean = new CBoolean(component.getName(), component.getPrompt(), component.getDefVal() , newConstraints);
		return cBoolean.make();
	}

	@Override
	public JPanelWithValue visitSlider(CSlider slider) {
		throw new IllegalArgumentException("Slider is not supported in Tabular dialog");
	}

	@Override
	public JPanelWithValue visitSingleOpt(CSingleOpt component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setTabularDisplay(id, constraints, BLOCK_LIST);
		CSingleOpt cSingle = new CSingleOpt(component.getName(), component.getPrompt(), component.getOptions(), component.getDefValue() , newConstraints);
		return cSingle.make();
	}
	
	@Override
	public JPanelWithValue visitMultiOpt(CMultiOpt component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setTabularDisplay(id, constraints, BLOCK_LIST);
		CMultiOpt cMulti = new CMultiOpt(component.getName(), component.getPrompt(), component.getOptions(), component.getDefValues() , newConstraints);
		return cMulti.make();
	}

}
