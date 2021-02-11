package fields;

public class PlaceholderDecimalField extends PlaceholderTextField {
	public PlaceholderDecimalField() {
		this.setDocument(new DecimalDocument());
	}
}
