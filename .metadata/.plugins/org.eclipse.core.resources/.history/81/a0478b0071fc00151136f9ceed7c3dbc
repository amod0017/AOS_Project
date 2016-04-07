package edu.lamar.client;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CarGui {
	JFrame myFrame = new JFrame();
	private final JLabel labelUpperEdge = new JLabel("--------------------");
	private final JLabel labelLowerEdge = new JLabel("--------------------");
	private final JLabel labelCar = new JLabel("       C");
	private final JButton bridgeRelease = new JButton("BRIDGE RELEASE");
	private final JButton bridgeRequest = new JButton("BRIDGE REQUEST");
	private final JTextField carId = new JTextField();
	private final JPanel jPanel = new JPanel();

	public CarGui() {
		myFrame = new JFrame("Java SWING Examples");
		myFrame.setSize(400, 400);
		myFrame.setLayout(new GridLayout(6, 0));
		final JPanel emptyPanel = new JPanel();
		// emptyPanel.setSize(360, 10);
		// jPanel.setLayout(new BoxLayout(myFrame, 0));
		carId.setSize(5, 400);
		// emptyPanel.setSize(350, 10);
		myFrame.add(new JPanel().add(carId));
		// myFrame.add(emptyPanel);
		myFrame.add(new JPanel().add(labelUpperEdge));
		myFrame.add(emptyPanel);
		myFrame.add(new JPanel().add(labelCar));
		// jPanel.add(new JLabel(""));
		myFrame.add(new JPanel().add(labelLowerEdge));
		// jPanel.add(new JLabel(""));
		myFrame.add(new JPanel().add(bridgeRelease));
		myFrame.add(new JPanel().add(bridgeRequest));
		myFrame.add(jPanel);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		final CarGui carGui = new CarGui();
		carGui.myFrame.setVisible(true);
	}

}
