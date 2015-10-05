// Calculator
// Build a Panel, add JButton on the Panel, add ActionListener to the JButton 
// and implement actionPerformed in the ActionListener

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame {

	private Container container;// set container
	private GridBagLayout layout; // set grid layout
	private GridBagConstraints constraints;
	private JTextField displayField;// set display field
	private String commandsaved;// save "+, -, *, /"
	private double result;// result of calculation
	private double curNum;// the current number showed on the display field
	private double preNum;// the previous number showed on the display field
	private boolean startNum;// decide whether it is the start of the number 
	private boolean startCom;// decide whether it is the start of the operator

	public Calculator() {

		super("Calculator");
		container = getContentPane();
		layout = new GridBagLayout();
		container.setLayout(layout);
		constraints = new GridBagConstraints(); 
		startNum = true;
		startCom = true;
		result = 0;
		curNum = 0;
		preNum = 0;
	    commandsaved = "="; 
		displayField = new JTextField(30); // set the length of display field
		displayField.setHorizontalAlignment(JTextField.RIGHT); // let the number showed on the right of the display field
		displayField.setBackground(Color.white);// set the background of the display field
		displayField.setText("0");
		constraints.gridwidth = 4; // width of the display field
		constraints.gridheight = 1; // height of the display field
		constraints.fill = GridBagConstraints.BOTH; // let the display field fill all the room
		constraints.weightx = 30; // set y
		constraints.weighty = 30; // set x
		layout.setConstraints(displayField, constraints);
		container.add(displayField); // let the display field show on the windows
		ActionListener insert = new InsertAction(); // set two listeners
		ActionListener command = new getCommand();
		addButton("Back", 0, 1, 1, 1, insert); 
		addButton("CE", 1, 1, 1, 1, insert);
		addButton("AC", 2, 1, 1, 1, insert);
		addButton("7", 0, 2, 1, 1, insert);
		addButton("8", 1, 2, 1, 1, insert);
		addButton("9", 2, 2, 1, 1, insert);
		addButton("/", 3, 2, 1, 1, command);
		addButton("4", 0, 3, 1, 1, insert);
		addButton("5", 1, 3, 1, 1, insert);
		addButton("6", 2, 3, 1, 1, insert);
		addButton("*", 3, 3, 1, 1, command);
		addButton("1", 0, 4, 1, 1, insert);
		addButton("2", 1, 4, 1, 1, insert);
		addButton("3", 2, 4, 1, 1, insert);
		addButton("-", 3, 4, 1, 1, command);
		addButton("0", 0, 5, 2, 1, insert);
		addButton("+/-", 3, 1, 1, 1, insert);
		addButton(".", 2, 5, 1, 1, insert);
		addButton("+", 3, 5, 1, 1, command);
		addButton("=", 0, 6, 4, 1, command);
		setSize(300, 300); // set size of the window
		setVisible(true); // set the window visible
	}

	private void addButton(String lable, int row, int col, int width,
			int height, ActionListener listener) {
		JButton jbutton = new JButton(lable);
		constraints.gridx = row;
		constraints.gridy = col;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		jbutton.addActionListener(listener);
		layout.setConstraints(jbutton, constraints);
		container.add(jbutton);
		constraints.insets = new Insets(4, 4, 1, 1);
		jbutton.setBackground(Color.YELLOW);//set the background of the button
	}

	private class InsertAction implements ActionListener {
		public void actionPerformed(ActionEvent AE) {
			String insert = AE.getActionCommand();
			if(startNum == true) {
				startNum = false;
				displayField.setText("");
			}
			
			if (insert == "+/-") {
				displayField.setText("-" + displayField.getText());
			} else {
				if(insert == "Back") {
					String pre = displayField.getText();
					if (pre.length() > 1) {
						displayField
								.setText(pre.substring(0, pre.length() - 1));
					} else {
						displayField.setText("0");
						startNum = true;
					}
				} else if (insert == "CE") {
					startNum = true;
					displayField.setText("0");
				} else if (insert == "AC") {
					startNum = true;
					startCom = true;
					curNum = 0;
					preNum = 0;
					result = 0;
					commandsaved = "=";
					displayField.setText("0");
				} else if (insert == ".") {
					if (displayField.getText().trim().indexOf(".") != -1) {
					} else {
						displayField.setText(displayField.getText() + insert);
					}
				}else {
					displayField.setText(displayField.getText() + insert);
				}
			}
		}
	}

	private class getCommand implements ActionListener {
		public void actionPerformed(ActionEvent AE) {
			String command = AE.getActionCommand();
			if (startCom == true) {
				curNum = Double.parseDouble(displayField.getText());
				commandsaved = command;
				startCom = false;
				startNum = true;
			} else {
				preNum = curNum;
				curNum = Double.parseDouble(displayField.getText());
				calculate();
				if(command == "=") {
					displayField.setText("" + result);
				}
				commandsaved = command;
		 	    startNum = true;
			}
		}
	}
	
	public void calculate () {
		if(commandsaved == "+") {
			result = preNum + curNum;
		} else if (commandsaved == "-") {
			result = preNum - curNum;
		} else if (commandsaved == "*") {
			result = preNum * curNum;
		} else if (commandsaved == "/") {
			result = preNum / curNum;
		} 
	}

	public static void main(String[] args) {
		Calculator myCalculator = new Calculator();
		myCalculator.setResizable(false);
		myCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
