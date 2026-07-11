package ospedale.gui;

import javax.swing.*;

public class Home {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->
				new ospedale.gui.LoginFrame(new ospedale.controller.Controller()).setVisible(true));
	}
}
