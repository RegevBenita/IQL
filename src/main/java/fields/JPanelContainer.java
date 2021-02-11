package fields;

import javax.swing.JPanel;

import ast.Id;

public abstract class JPanelContainer extends JPanel {
	private Id id;
	private boolean hasError = false;
	
	public abstract boolean checkForError();
	
	public JPanelContainer(Id id) {
		this.id = id;
	}
	
	public boolean isHasError() {
		return hasError;
	}
	
	public void setHasError(boolean haveError) {
		this.hasError = haveError;
	}
	
	public Id getId() {
		return id;
	}
}
