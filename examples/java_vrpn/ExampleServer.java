package java_vrpn;

import jni.JniBuilder.Analog;
import vrpnserver.VrpnServer;

public class ExampleServer extends Thread {
	public static void main(String[] args) {
		new ExampleServer();

	}

	ExampleServer() {
		super();
		/**
		 * 
		 * We set up a vrpn server on port 3883 with one button and two 2ch
		 * analogs
		 * 
		 * For buttons, connect your client to "Button0@localhost". The name of
		 * the button server does not change.
		 * 
		 * For analogs, it's "[analogName]@localhost".
		 * 
		 */
		int port = 3883;
		int buttonCount = 1;

		String analogName = "Analog0";
		int analogChannels = 2;
		Analog analog0 = new Analog(analogName, analogChannels);

		server = new VrpnServer(port, buttonCount, analog0, new Analog(
				"Analog1", 2));
	}

	VrpnServer server;

	public void run() {
		while (true) {
			/**
			 * Set our button to "on" Set random values for the analog devices
			 */
			server.updateButton(0, true);
			server.updateAnalogVal(0, 0, (float) Math.random());
			server.updateAnalogVal(0, 1, (float) Math.random());

			server.updateAnalogVal(1, 0, (float) Math.random());
			server.updateAnalogVal(1, 1, (float) Math.random());
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
