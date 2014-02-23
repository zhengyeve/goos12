package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import auctionsniper.ui.MainWindow;

public class Main implements AuctionEventListener {
	private MainWindow ui;

	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID = 3;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/"
			+ AUCTION_RESOURCE;
	public static final String JOIN_COMMAND_FORMAT = "join command format";
	public static final String BID_COMMAND_FORMAT = "bid command format";

	private Chat notToBeGCdChat;

	public Main() throws Exception {
		startUserInterface();
	}

	public static void main(String... args) throws Exception {
		Main main = new Main();

		main.joinAuction(
				connectTo(args[ARG_HOSTNAME], args[ARG_USERNAME],
						args[ARG_PASSWORD]), args[ARG_ITEM_ID]);

	}

	private void joinAuction(XMPPConnection connection, String itemId)
			throws XMPPException {
		disconnectWhenUICloses(connection);
		Chat chat = connection.getChatManager().createChat(
				auctionId(itemId, connection),
				new AuctionMessageTranslator(this));
		chat.sendMessage(JOIN_COMMAND_FORMAT);
		this.notToBeGCdChat = chat;
	}
	
	private void disconnectWhenUICloses(final XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				connection.disconnect();
			}
		});
	}

	private static XMPPConnection connectTo(String hostname, String username,
			String password) throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
		return connection;
	}

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}

	private void startUserInterface() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				ui = new MainWindow();
			}
		});
	}

	@Override
	public void auctionClosed() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ui.showStatus(MainWindow.STATUS_LOST);
			}
		});
	}

	@Override
	public void currentPrice(int price, int increment) {
		// TODO Auto-generated method stub

	}

}
