package ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import ast.Ast.Containable;
import ast.Ast.Tabable;
import ast.Id;
import ast.components.*;
import fields.*;
import iql.IQL;

public abstract class UiVisitor implements Visitor {
	private CompletableFuture<List<Map<String, String>>> data = new CompletableFuture<>();
	private final JTabbedPane tabbedPane = new JTabbedPane();
	
	
	protected abstract void constructDialog(JFrame frame, List<JPanelContainer> panels, String desc);
	
	@Override
	public CompletableFuture<List<Map<String, String>>> getData() {
		return data;
	}
	
	protected JPanelContainer getPanel(Containable container) {
		return container.accept(this);
	}
	
		
	protected Map<String, String> getEmptyEntries(List<JPanelContainer> panels) {
		Map<String, String> data = new IQL.EmptyEntriesMap();
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				checkTabErrors(data, false, panel);
			else if(panel.getId() == Id.Group) {
				checkGroupErrors(data, false, panel);
				} else {
					checkComponentErrors(data, false, panel);
				}
		}
		return setNullValues(data);
	}
	
	protected Map<String, String> setNullValues(Map<String, String> entries) {
		for (Map.Entry<String, String> entry : entries.entrySet()) {
		    entry.setValue(null);
		}
		return entries;
	}
	
	protected Map<String, String> saveAsMap(List<JPanelContainer> panels) {
		Map<String, String> data = new HashMap<>();
		boolean haveErrors = false;
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				haveErrors |= checkTabErrors(data, haveErrors, panel);
			else if(panel.getId() == Id.Group) {
				haveErrors |= checkGroupErrors(data, haveErrors, panel);
				} else {
					haveErrors |= checkComponentErrors(data, haveErrors, panel);
				}
		}
		if(haveErrors)
			return null;
		return data;
	}

	protected boolean checkComponentErrors(Map<String, String> data, boolean haveErrors,
			java.awt.Component comp) {
		if(comp instanceof JPanelWithValue) {
			JPanelWithValue panelWithValue = (JPanelWithValue)comp;
			haveErrors |=panelWithValue.checkForError();
			String value = panelWithValue.getValue() == null || panelWithValue.getValue().isBlank()? null: panelWithValue.getValue();
			data.put(panelWithValue.getName(), value);
		}
		return haveErrors;
	}

	protected boolean checkGroupErrors(Map<String, String> data, boolean haveErrors,
			JPanelContainer panel) {
		java.awt.Component[] comps = panel.getComponents();
		for(java.awt.Component comp: comps) {
			haveErrors |= checkComponentErrors(data, haveErrors, comp);
		}
		return haveErrors;
	}
	
	protected boolean checkTabErrors(Map<String, String> data, boolean haveErrors,
			JPanelContainer panel) {
		if(panel.getComponents().length == 0)
			return false;
		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
		for(int i=0; i<tabPanel.getTabCount(); i++) {
			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
			java.awt.Component[] comps = tab.getComponents();
		
			for(java.awt.Component comp: comps) 
			{
				if(comp instanceof JPanelWithValue panelWithValue) {
					haveErrors |= checkComponentErrors(data, haveErrors, panelWithValue);
				} else if (comp instanceof JPanelContainer container){
					haveErrors |= checkGroupErrors(data, haveErrors, container);
				}
			}
		}
		return haveErrors;
	}
	
	public JFrame makeFrame(String title) {
		JFrame jFrame = new JFrame();
		jFrame.setLayout(new GridBagLayout());
		JPanel panel = (JPanel)jFrame.getContentPane();
		panel.setBorder(new EmptyBorder(0, 15, 5, 15));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		jFrame.setTitle(title);
		jFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jFrame.setResizable(false);
		return jFrame;
	}
	
	@Override
	public JPanelContainer visitGroup(Group group) {
		String title = group.getTitle();
		List<Component> components = group.getComponents();
		List<JPanelWithValue> panels = new ArrayList<>();
		for(Component c:components) {
			JPanelWithValue panel = (JPanelWithValue)getPanel((Containable)c);
			panels.add(panel);
		}
		return addGroupPanels(title, panels);
	}
	
	private JPanelContainer addGroupPanels(String title, List<JPanelWithValue> panels) {
		JPanelContainer groupPanel = new JPanelContainer(Id.Group){
			@Override
			public boolean checkForError() {
				boolean haveErrors = false;
				for (JPanelWithValue panel: panels) {
					haveErrors |= panel.checkForError();
				}
				setHasError(haveErrors);
				return haveErrors;
			}
		};
		groupPanel.setBorder(new TitledBorder(new EtchedBorder(), title));
		groupPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.gridwidth = 3;
		for(JPanelWithValue panel : panels) {
			gbc.gridy++;
			groupPanel.add(panel, gbc);
		}
		return groupPanel;
	}

	@Override
	public JPanelContainer visitTab(Tab tab) {
		List<JPanelContainer> panels = new ArrayList<>();
		String title = tab.getTitle();
		List<Tabable> containers = tab.getContainers();
		for(Tabable container: containers) {
			JPanelContainer panel = getPanel((Containable)container);
			panels.add(panel);
		}
		tabbedPane.add(title, addTabPanels(title, panels));
		JPanelContainer tabPanel = new JPanelContainer(Id.TabsContainer){
			@Override
			public boolean checkForError() {
				boolean haveErrors = false;
				for (JPanelContainer panel: panels) {
					haveErrors |= haveErrors || panel.checkForError();
				}
				setHasError(haveErrors);
				return haveErrors;
				}
		};
		tabPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		tabPanel.add(tabbedPane, gbc);
		return tabPanel;
	}

	private JPanelContainer addTabPanels(String title, List<JPanelContainer> panels) {
		JPanelContainer tabPanel = new JPanelContainer(Id.Tab){
			@Override
			public boolean checkForError() {
				boolean haveErrors = false;
				for (JPanelContainer panel: panels) {
					haveErrors |= haveErrors || panel.checkForError();
				}
				setHasError(haveErrors);
				return haveErrors;
			}
		};
		tabPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.weightx = 1.0;
		
		// Align all the component in the tab to the top right corner
		for(int i=0; i<panels.size(); i++) {;
			gbc.gridy++;
			tabPanel.add(panels.get(i), gbc);
		}
		return tabPanel;
	}

	@Override
	public JPanelWithValue visitString(CString component) {
		return component.make();
	}
	
	@Override
	public JPanelWithValue visitTextArea(CTextArea component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitPassword(CPassword component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitInteger(CInteger component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitDecimal(CDecimal component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitBoolean(CBoolean component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitSlider(CSlider slider) {
		return slider.make();
	}

	@Override
	public JPanelWithValue visitSingleOpt(CSingleOpt component) {
		return component.make();
	}
	
	@Override
	public JPanelWithValue visitMultiOpt(CMultiOpt component) {
		return component.make();
	}
	
	protected int getDialogMaxHeight() {
		return (int) (Toolkit.getDefaultToolkit().getScreenSize().height*0.95);
	}
	
	protected int getDialogMaxWidth() {
		return (int) (Toolkit.getDefaultToolkit().getScreenSize().width*0.95);
	}
	
	protected JTextArea generateDesc(String title) {
		JTextArea label = new JTextArea();
		label.setBorder(new EmptyBorder(5, 0, 10, 0));
		label.setText(title);
		label.setEditable(false);
		label.setBackground(new Color(238, 238, 238));
		return label;
	}
	
}
