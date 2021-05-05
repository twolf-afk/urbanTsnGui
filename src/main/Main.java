package main;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

import gui.MainWindow;

public class Main {

	public static void main(String[] args) {
		
		//Core.initGuiBackend();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}
}