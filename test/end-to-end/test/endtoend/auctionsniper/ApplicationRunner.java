package test.endtoend.auctionsniper;

import static test.endtoend.auctionsniper.FakeAuctionServer.XMPP_HOSTNAME;
import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

public class ApplicationRunner {
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	public static final String SNIPER_XMPP_ID = SNIPER_ID + "@" + XMPP_HOSTNAME + "/Auction";
	private AuctionSniperDriver driver;

	public void startBiddingIn(final FakeAuctionServer auction) {
		Thread thread = new Thread("Test Application") {
			// 1.
			public void run() {
				try {
					// 2.
					Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD,
							auction.getItemId());
				} catch (Exception e) {
					// 3.
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		// 4.
		driver = new AuctionSniperDriver(1000);
		// 5.
		driver.showsSniperStatus(MainWindow.STATUS_JOINING);
	}

	public void hasShownSniperIsBidding() {
		driver.showsSniperStatus(MainWindow.STATUS_BIDDING);
	}

	public void showsSniperHasLostAuction() {
		// 6.
		driver.showsSniperStatus(MainWindow.STATUS_LOST);
	}

	public void stop() {
		if (driver != null) {
			// 7.
			driver.dispose();
		}
	}
}
