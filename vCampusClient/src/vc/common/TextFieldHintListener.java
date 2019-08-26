package vc.common;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TextFieldHintListener implements FocusListener{
	private String hintText;
	private TextField textField;
	public TextFieldHintListener(TextField TextField,String hintText) {
		this.textField = TextField;
		this.hintText = hintText;
		TextField.setText(hintText);
		TextField.setForeground(Color.GRAY);
		}
	
	public void focusGained(FocusEvent e) {
		String temp = textField.getText();	
		if(temp.equals(hintText)) {
			textField.setText("");
			textField.setForeground(Color.BLACK);
			}
		}
	
	public void focusLost(FocusEvent e) {
		String temp = textField.getText();
		if(temp.equals("")) {
			textField.setForeground(Color.GRAY);
			textField.setText(hintText);
		}
	}
	
	public void setHintText(String hintText)
	{
		this.hintText = hintText;
	}
}
