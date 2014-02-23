package auctionsniper.ui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import test.endtoend.auctionsniper.ApplicationRunner;

public class MainWindow extends JFrame{
	
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPER_STATUS_NAME = "Sniper Status";
	public static final String STATUS_JOINING = "joining";
	public static final String STATUS_LOST = "lost";
	public static final String STATUS_BIDDING = "bidding";
	private final JLabel sniperStatus = createLabel(MainWindow.STATUS_JOINING);
	
	public MainWindow() {
		super("Auction Sniper");
		setName(MainWindow.MAIN_WINDOW_NAME);
		add(sniperStatus);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private static JLabel createLabel(String initialText) {
		JLabel result = new JLabel(initialText);
		result.setName(SNIPER_STATUS_NAME);
		result.setBorder(new LineBorder(Color.BLACK));
		return result;
	}
	
	public void showStatus(String status) {
		sniperStatus.setText(status);
	}
}
