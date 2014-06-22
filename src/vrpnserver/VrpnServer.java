package vrpnserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Timer;
import java.util.TimerTask;

import jni.JniBuilder;
import jni.JniBuilder.Analog;
import jni.JniBuilder.Button;
import jni.JniLayer;

/**
 * The remote activity for the Android VRPN controller. This class is not very
 * pretty, but is the glue which holds the server together.
 * 
 * @author UNC VRPN Team
 * 
 */
public class VrpnServer {
	/**
	 * The interval (in milliseconds) between calls to vrpn's mainloop()
	 * functions. Calling these on a timer prevents the client application from
	 * complaining that it hasn't heard from the Android server.
	 */
	public static final int IDLE_INTERVAL = 1000;

	/**
	 * The Tag used by this class for logging
	 */
	static final String TAG = "VRPN";

	/**
	 * The timer that calls mainloop to persist the connection
	 */
	private Timer idleTimer;

	/**
	 * The connection between Java and the C++ world of VRPN, using Java Native
	 * Interface, called the JNI
	 */
	private JniLayer jniLayer;

	/**
	 * The JNI creator object
	 */
	private JniBuilder builder;

	public VrpnServer(int port, int buttonCount, Analog... analogs) {
		this.builder = new JniBuilder(port);
		for (int i = 0; i < buttonCount; i++)
			this.builder.add(new Button("Button" + i));
		if (analogs != null)
			this.builder.add(analogs);
		this.jniLayer = builder.toJni();
		setupVrpn();
	}

	/**
	 * Creates a VrpnServer instance hosting the specified number of vrpn
	 * Buttons at the specified port. All buttons are available at:
	 * "Button0@localhost"
	 */

	public VrpnServer(int port, int buttonCount) {
		this(port, buttonCount, (Analog) null);
	}

	/**
	 * Creates a VrpnServer instance hosting two vrpn Buttons at the specified
	 * port. "Button0@localhost"
	 */

	public VrpnServer(int port) {
		this(port, 2);
	}

	/**
	 * Creates a VrpnServer instance hosting two vrpn Buttons.
	 * "Button0@localhost:10000"
	 */

	public VrpnServer() {
		this(10000, 2);
	}

	/**
	 * Sets the status (up or down) of a given button. True for button down,
	 * false for button up.
	 * 
	 * @param buttonId
	 *            - the button whose status to update
	 * @param state
	 *            - whether to set the button's status to pressed (true) or not
	 *            pressed (false)
	 */

	public void updateButton(int id, boolean val) {
		this.jniLayer.updateButtonStatus(id, val);
	}

	/**
	 * Sets the value for the specified channel in the specified analog server.
	 * 
	 * @param analogId
	 *            - The identifier of the analog server to update
	 * @param channel
	 *            - Which channel (or dimension) of the analog to pass the value
	 * @param val
	 *            - The new value
	 */

	public void updateAnalogVal(int analogId, int channel, float val) {
		this.jniLayer.updateAnalogVal(analogId, channel, val);
	}

	class WaitingForConnectionThread extends Thread {

		public WaitingForConnectionThread() {
			super();
		}

		public void run() {
			// this is the hack to allow reconnection to the client
			// after a disconnect. the hack polls for a open port,
			// and loops until the port is open/free'd by GC.
			boolean stop = false;
			ServerSocket server = null;
			while (!stop) {
				try {
					server = new ServerSocket(jniLayer.getPort());
					System.out.println("Connection found!");
					server.close();
					stop = true;
				} catch (IOException e) {
					System.out.println("Connecting...");
					System.out.println(e.getMessage());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						System.err.println("VRPN Connection failed!");
					}
				}
			}

			jniLayer.start();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {

			}
		}
	}

	/**
	 * Creates/recreates the JniLayer and attaches the phone's sensors. The
	 * content of this method was moved from onResume()
	 */
	public void setupVrpn() {
		// ////// This bit of code opens a new thread in case we need to wait
		// for Dalvik to free up the port //////////
		new WaitingForConnectionThread().start();
		// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// setup the idle timer
		this.idleTimer = new Timer();
		this.idleTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				sendIdle();
			}

		}, IDLE_INTERVAL, IDLE_INTERVAL);
	}

	/**
	 * Calls the vrpn connection's mainLoop to send an empty message to persist
	 * the connection.
	 */
	public void sendIdle() {
		this.jniLayer.mainLoop();
	}

	public void stop() {
		jniLayer.stop();
	}
}