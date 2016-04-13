package edu.lamar.client;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CarGui {

	public void displayGUI() {
		final JFrame frame = new JFrame("Absolute Layout Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel j = new JPanel();
		j.setOpaque(true);
		j.setBackground(Color.WHITE);
		j.setLayout(null);

		// final JLabel label = new JLabel("This JPanel uses Absolute
		// Positioning", SwingConstants.CENTER);
		// label.setSize(300, 30);
		// label.setLocation(5, 5);

		final JLabel labelUpperEdge = new JLabel("---------------------------");
		final JLabel labelLowerEdge = new JLabel("---------------------------");
		final JLabel labelCar = new JLabel("       C");
		final JButton bridgeRelease = new JButton("BRIDGE RELEASE");
		final JButton bridgeRequest = new JButton("BRIDGE REQUEST");
		final JTextField carId = new JTextField();

		labelUpperEdge.setSize(300, 20);
		labelUpperEdge.setLocation(100, 50);

		labelLowerEdge.setSize(300, 20);
		labelLowerEdge.setLocation(100, 150);

		labelCar.setSize(50, 50);
		labelCar.setLocation(170, 90);

		bridgeRequest.setSize(150, 50);
		bridgeRequest.setLocation(100, 300);

		bridgeRelease.setSize(150, 50);
		bridgeRelease.setLocation(300, 300);
		carId.setSize(50, 50);
		carId.setLocation(200, 5);

		j.add(labelUpperEdge);
		j.add(labelLowerEdge);
		j.add(labelCar);
		j.add(bridgeRequest);
		j.add(bridgeRelease);
		j.add(carId);

		frame.setContentPane(j);
		frame.setSize(500, 500);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public static void main(String... args) {

		new CarGui().displayGUI();
	}
}
