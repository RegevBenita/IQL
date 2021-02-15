package ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

import org.w3c.dom.ranges.RangeException;

import ast.Ast.Containable;
import ast.Ast.Dialog;
import ast.Ast.Query;
import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import fields.*;

public class PagesVisitor extends UiVisitor {
	private int currentPage = 1;
	private int totalPages = 1;
	private int minEntries = 1;
	private int maxEntries = Integer.MAX_VALUE;
	private String approveBtnLabel = "Approve";
	private String cancelBtnLabel = "Cancel";

	@Override
	public JFrame visitQuery(Query query) {
		Dialog dialog = query.dialog();
		String title = dialog.getTitle();
		String desc = dialog.getDescription();
		JFrame jFrame = makeFrame(title);
		setDialogConstraits(dialog.getConstraints());
		List<Containable> containers = query.containers();
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
		GridBagConstraints gbc = addFrameComponents(frame, panels, jDesc);
		addFrameButtons(frame, panels, results, gbc);
		int frameHeight = frame.getPreferredSize().height;
		if(frameHeight > getDialogMaxHeight())
			throw new RangeException(RangeException.BAD_BOUNDARYPOINTS_ERR, "Dialog height is bigger than the screen height");
	}

	private void addFrameButtons(JFrame frame, List<JPanelContainer> panels, List<Map<String, String>> results,
			GridBagConstraints gbc) {
		gbc.fill = GridBagConstraints.NONE;
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		JButton cancelButton = new JButton(cancelBtnLabel);
		JButton approveButton = new JButton(approveBtnLabel);
		JButton prevButton = new JButton("<");
		JButton nextButton = new JButton(">");
		JButton addButton = new JButton("Add");
		JButton deleteButton = new JButton("Delete");
		JLabel pagesIndex = new JLabel(getPageIndex());
		JPanel pagesPanel = new JPanel(new GridBagLayout());
		deleteButton.setBackground(new Color(223, 71, 89));
		deleteButton.setForeground(Color.white);
		deleteButton.setEnabled(false);
		prevButton.setEnabled(false);
		nextButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
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
		
		nextButton.addActionListener(e -> {
			var saved = saveAsMap(panels);
			if(saved==null)
				return;
			updateDataList(results, saved, currentPage -1, false);
			currentPage++;
			if(currentPage == totalPages)
				nextButton.setEnabled(false);
			if(currentPage > 1)
				prevButton.setEnabled(true);
			pagesIndex.setText(getPageIndex());
			updateUiFields(panels, results.get(currentPage-1), false);
		});
		
		prevButton.addActionListener(e -> {
			var saved = saveAsMap(panels);
			if(saved==null)
				return;
			if(results.size() < currentPage) {
				results.add(saved);
			}
			updateDataList(results, saved, currentPage -1, false);
			currentPage--;
			
			if(currentPage == 1)
				prevButton.setEnabled(false);
			if(currentPage < totalPages)
				nextButton.setEnabled(true);
			pagesIndex.setText(getPageIndex());
			updateUiFields(panels, results.get(currentPage -1), false);
		});
		
		addButton.addActionListener(e -> {
			var saved = saveAsMap(panels);
			if(saved==null)
				return;
			updateDataList(results, saved, currentPage -1, true);
			updateUiFields(panels, new HashMap<String, String>(), true);
			totalPages++;
			if(totalPages == maxEntries)
				addButton.setEnabled(false);
			deleteButton.setEnabled(true);
			if(totalPages > 1) 
				prevButton.setEnabled(true);
			currentPage = totalPages;
			pagesIndex.setText(getPageIndex());
		});
		
		deleteButton.addActionListener(e -> {
			totalPages--;
			addButton.setEnabled(true);
			if(totalPages == 1)
				deleteButton.setEnabled(false);
			currentPage = currentPage-1>0? currentPage-1: currentPage;
			if(currentPage == 1)
				prevButton.setEnabled(false);
			if(currentPage == totalPages)
				nextButton.setEnabled(false);
			pagesIndex.setText(getPageIndex());
			if(totalPages+1 == results.size())
				results.remove(currentPage);
			updateUiFields(panels, results.get(currentPage -1), false);
		});
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEmptyEntries(panels));
			getData().complete(results);
			frame.dispose();
		});
		
		approveButton.addActionListener(e->{
			if(totalPages < minEntries) {
				JOptionPane.showMessageDialog(frame,
						"You must have a least " + minEntries + " entries",
						"Warning",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			var saved = saveAsMap(panels);
			if(saved==null) 
				return;
			updateDataList(results, saved, currentPage -1, false);
			getData().complete(results);
			frame.dispose();
		});
				
		gbc.anchor = GridBagConstraints.EAST;
		pagesPanel.add(nextButton, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		pagesPanel.add(pagesIndex, gbc);
		gbc.anchor = GridBagConstraints.WEST;
		pagesPanel.add(prevButton, gbc);
		gbc.ipadx = 0;
		gbc.insets = new Insets(0, 0, 0, 5);
		buttonsPanel.add(approveButton, gbc);
		gbc.gridx++;
		buttonsPanel.add(cancelButton, gbc);
		gbc.gridx++;
		buttonsPanel.add(deleteButton, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx++;
		buttonsPanel.add(addButton, gbc);
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		frame.add(buttonsPanel, gbc);
		gbc.gridy++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 0, 0, 0);
		frame.add(pagesPanel, gbc);
	}

	// Add the components to the frame and return the updated GridBagConstraints
	private GridBagConstraints addFrameComponents(JFrame frame, List<JPanelContainer> panels, JTextArea jDesc) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		frame.add(jDesc, gbc);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		for(JPanel panel : panels) {
			gbc.gridy++;
			frame.add(panel, gbc);
		}
		return gbc;
	}
	
	private void updateDataList(List<Map<String, String>> dataList, Map<String, String> value, int index, boolean setDefault) {
		if(dataList.size() > index)
			dataList.set(index, value);
		else
			dataList.add(value);
	}

	private String getPageIndex() {
		return "Page " + currentPage + " of " + totalPages;
	}
	
	private void updateUiFields(List<JPanelContainer> panels, Map<String, String> values, boolean setDefault) {
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				updateTabFields(panel, values, setDefault);
			else if(panel.getId() == Id.Group) {
				updateGroupFields(panel, values, setDefault);
				} else {
					if(panel instanceof JPanelWithValue panelWithValue) {
						updateComponentFields(panelWithValue, values, setDefault);
					}
				}
		}
	}
	
	private void updateTabFields(JPanelContainer panel, Map<String, String> values, boolean setDefault) {
		if(panel.getComponents().length == 0)
			return;
		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
		for(int i=0; i<tabPanel.getTabCount(); i++) {
			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
			java.awt.Component[] comps = tab.getComponents();
		
			for(java.awt.Component comp: comps) 
			{
				if(comp instanceof JPanelWithValue panelWithValue) {
					updateComponentFields(panelWithValue, values, setDefault);
				} else if (comp instanceof JPanelContainer container){
					updateGroupFields(container, values, setDefault);
				}
			}
		}
	}
	
	private void updateGroupFields(JPanelContainer panel, Map<String, String> values, boolean setDefault) {
		java.awt.Component[] comps = panel.getComponents();
		for(java.awt.Component comp: comps) {
			if(comp instanceof JPanelWithValue panelWithValue) {
				updateComponentFields(panelWithValue, values, setDefault);
			}
		}
	}
	
	private void updateComponentFields(JPanelWithValue panelWithValue, Map<String, String> values, boolean setDefault) {
			String fieldName = panelWithValue.getName();
			String value = values.get(fieldName) == null? "":values.get(fieldName);
			panelWithValue.setValueOrDefault(value, setDefault);
	}
	
	
}
