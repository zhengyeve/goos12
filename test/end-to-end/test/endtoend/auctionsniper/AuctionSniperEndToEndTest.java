package test.endtoend.auctionsniper;

import org.junit.After;
import org.junit.Test;

public class AuctionSniperEndToEndTest {
	private final FakeAuctionServer auction = new FakeAuctionServer(
			"item-54321");
	private final ApplicationRunner application = new ApplicationRunner();

	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		// 1. Wait for the stub auction to receive
		// the Join request before continuing with the test.
//		auction.hasReceivedJoinRequestFromSniper();
		auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		// 2.
		auction.reportPrice(1000, 98, "other bidder");
		// 3
		application.hasShownSniperIsBidding();
		// 4
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		// 5
		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}

	@After
	public void stopAuction() {
		auction.stop();
	}

	@After
	public void stopApplication() {
		application.stop();
	}
}
