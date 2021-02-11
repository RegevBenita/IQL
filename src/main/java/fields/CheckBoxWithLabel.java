package fields;

public class CheckBoxWithLabel {
	  private String label;
	  private boolean selected = false;

	  public CheckBoxWithLabel(String label) {
	    this.label = label;
	  }
	  
	  public CheckBoxWithLabel(String label, boolean selected) {
		    this(label);
		    this.selected = selected;
	  }

	  public boolean isSelected() {
	    return selected;
	  }

	  public void setSelected(boolean selected) {
	    this.selected = selected;
	  }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	  
}
