package net.sf.gogui.gogui;

import java.awt.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class IgsRoomFrame extends JDialog {

	private static final long serialVersionUID = 1L;

	public IgsRoomFrame(JFrame parent, String title, String massage) {
		super(parent, title);
		
		// set the position of the window
		Point p = new Point(400, 400);
		setLocation(p.x, p.y);

		// Create a message
		JPanel messagePane = new JPanel();
		JLabel label = new JLabel("<html>"+massage+"</html>", SwingConstants.CENTER);
		label.setFont(new Font("Serif", Font.PLAIN, 14));



		//messagePane.add(new JLabel("<html>"+massage+"</html>", SwingConstants.CENTER));
		messagePane.add(label);
		// get content pane, which is usually the
		// Container of all the dialog's components.
		getContentPane().add(messagePane);
		
				
        

       
		


		// Create a button
		JPanel buttonPane = new JPanel();
		JButton button = new JButton("Close me");
		buttonPane.add(button);
		// set action listener on the button
		button.addActionListener(new MyActionListener());
		getContentPane().add(buttonPane, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);

	}

	// override the createRootPane inherited by the JDialog, to create the rootPane.
	// create functionality to close the window when "Escape" button is pressed
	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action action = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				System.out.println("escaping..");
				setVisible(false);
				dispose();
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", action);
		return rootPane;
	}

	// an action listener to be used when an action is performed
	// (e.g. button is pressed)
	class MyActionListener implements ActionListener {

		//close and dispose of the window.
		public void actionPerformed(ActionEvent e) {
			System.out.println("disposing the window..");
			setVisible(false);
			dispose();
		}
	}

	/*public  void show() {
		MyJDialog dialog = new MyJDialog(new JFrame(), "hello JCGs", "This is a JDialog example");
		// set the size of the window
		dialog.setSize(300, 150);
	}*/
}
