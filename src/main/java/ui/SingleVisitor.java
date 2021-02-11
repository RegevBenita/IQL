package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.ranges.RangeException;

import ast.Ast.Containable;
import ast.Ast.Dialog;
import ast.Ast.Query;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import fields.*;

public class SingleVisitor extends UiVisitor {
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
			case CANCEL -> setCancelBtnLabel(constraint);
			case APPROVE -> setApproveBtnLabel(constraint);
			default ->
			throw new IllegalArgumentException(id + " ,unsoppurted constraint by Single dialog");
			}
		}		
	}

	private void setApproveBtnLabel(Constraint constraint) {
		approveBtnLabel = ((Constraint.ApproveCon)constraint).value();
	}

	private void setCancelBtnLabel(Constraint constraint) {
		cancelBtnLabel = ((Constraint.CancelCon)constraint).value();
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
		buttonsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		JButton cancelButton = new JButton(cancelBtnLabel);
		JButton approveButton = new JButton(approveBtnLabel);
		buttonsPanel.add(approveButton, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 5, 0, 0);
		buttonsPanel.add(cancelButton, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx = 0;
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				results.clear();
				results.add(getEmptyEntries(panels));
				commitData(results);
				frame.dispose();
		    }
		});
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEmptyEntries(panels));
			commitData(results);
			frame.dispose();
		});
		approveButton.addActionListener(e->{
			var saved = saveAsMap(panels);
			if(saved!=null) {
				results.add(saved);
				commitData(results);
				frame.dispose();
			}});
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.CENTER;
		frame.add(buttonsPanel, gbc);
	}

	private GridBagConstraints addFrameComponents(JFrame frame, List<JPanelContainer> panels, JTextArea jDesc) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		frame.add(jDesc, gbc);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		for(JPanel panel : panels) {
			gbc.gridy++;
			frame.add(panel, gbc);
		}
		return gbc;
	}
	
}
