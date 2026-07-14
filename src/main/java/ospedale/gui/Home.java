package ospedale.gui;

import javax.swing.*;

/**
 * Punto di ingresso dell'applicazione grafica.
 * Avvia il Controller e la schermata di login.
 */
public class Home {

	/**
	 * Metodo principale che inizializza il sistema e mostra il login.
	 *
	 * @param args argomenti da riga di comando
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->
				new ospedale.gui.LoginFrame(new ospedale.controller.Controller()).setVisible(true));
	}
}