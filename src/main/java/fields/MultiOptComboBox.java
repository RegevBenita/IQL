package fields;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class MultiOptComboBox extends JComboBox<String>{
	private DefaultComboBoxModel<String> model;
    
    public MultiOptComboBox() {
        model = new DefaultComboBoxModel<String>();
        setModel(model);
        setRenderer(new MultiOptRenderer());
        setEditor(new MultiOptEditor());
    }
     
    public void addItems(List<String> items) {
        for (String item : items) {
            model.addElement(item);
        }
    }
}
