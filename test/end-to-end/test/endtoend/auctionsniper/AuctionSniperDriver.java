package test.endtoend.auctionsniper;

import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import static org.hamcrest.Matchers.equalTo;


public class AuctionSniperDriver extends JFrameDriver {
	public AuctionSniperDriver(int timeoutMillis) { 
	    super(new GesturePerformer(), 
	          JFrameDriver.topLevelFrame( 
	            named(MainWindow.MAIN_WINDOW_NAME), 
	            showingOnScreen()), 
	            new AWTEventQueueProber(timeoutMillis, 100)); 
	}
	
	// looks for the relevant label in the user interface and confirms that it shows the given status
	// If the driver cannot find a feature it expects, it will throw an exception and fail the test.
	public void showsSniperStatus(String statusText) {
		new JLabelDriver(this, named(MainWindow.SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
	}
}
