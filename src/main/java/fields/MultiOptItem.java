package fields;

public class MultiOptItem {
	private final String text;
	private boolean selected;

	public MultiOptItem(String text, boolean selected) {
		this.text = text;
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return text;
	}
}
