/**
 *
 */
package edu.lamar.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import edu.lamar.common.MessageImpl;
import edu.lamar.common.irp.Message;
import edu.lamar.common.irp.MessageTypes;

/**
 * @author user
 *
 */
public class CarClient3 extends AbstractClient {
	private boolean hadIrequestedTheBridge = false;
	private int myCurrentTimeStamp = 0;
	private final Queue<Integer> queue = new LinkedBlockingQueue<Integer>();
	private final int myCarId;
	private final Map<Integer, String> carAcknowledgementStatusMap = new HashMap<>();
	private final List<Integer> onBridge = new ArrayList<Integer>();
	private final int timeIrequestedTheBridge = 0;
	private final String myDirection;
	private boolean amIonBridge = false;
	private final MyFrame2 myFrame2;

	public CarClient3(MyFrame2 myFrame, int carId, String direction, String host, int port) {
		super(host, port);
		myCarId = carId;
		myDirection = direction;
		myFrame2 = myFrame;
		carAcknowledgementStatusMap.put(1, "NCK");
		carAcknowledgementStatusMap.put(2, "NCK");
		carAcknowledgementStatusMap.put(3, "NCK");
		carAcknowledgementStatusMap.put(4, "NCK");
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		try {
			final Message myMessage = (Message) msg;
			// timestamp should be increased after recieving each message.
			if (myCurrentTimeStamp >= myMessage.getTimeStamp()) {
				myCurrentTimeStamp = myCurrentTimeStamp + 1;
			} else {
				myCurrentTimeStamp = myMessage.getTimeStamp() + 1;
			}
			if (myMessage.getMessageType().equals(MessageTypes.BridgeRequest)) {
				if (myDirection.equalsIgnoreCase(myMessage.getDirection())) {
					try {
						sendToServer(new MessageImpl(myCarId, myCurrentTimeStamp, MessageTypes.Acknowledge));
					} catch (final IOException e) {
						e.printStackTrace();
					}
				} else if (hadIrequestedTheBridge || amIonBridge) {
					if (timeIrequestedTheBridge > myMessage.getTimeStamp()) {
						try {
							queue.add(myMessage.getCarId());
							sendToServer(new MessageImpl(myCarId, myCurrentTimeStamp, MessageTypes.Acknowledge));
						} catch (final IOException e) {
							e.printStackTrace();
						}
					} else if (timeIrequestedTheBridge < myMessage.getTimeStamp()) {
						// this means that the other process had requested the
						// bridge after me. I should ACK when I am done.
						queue.add(myMessage.getCarId());
					} else if (myCarId == myMessage.getCarId()) {
						// my message. I need to send ACK to me.
						try {
							sendToServer(new MessageImpl(myCarId, myCurrentTimeStamp, MessageTypes.Acknowledge));
						} catch (final IOException e) {
							e.printStackTrace();
						}
						// carAcknowledgementStatusMap.put(myCarId, "ACK");
					}
				} else {
					myCurrentTimeStamp = myMessage.getTimeStamp() + 1;
					queue.add(myMessage.getCarId());
					try {
						sendToServer(new MessageImpl(myCarId, myCurrentTimeStamp, MessageTypes.Acknowledge));
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			} else if (myMessage.getMessageType().equals(MessageTypes.BridgRelease)) {
				// remove from queue
				// should be the top car
				if (Integer.valueOf(myMessage.getCarId()).equals(Integer.valueOf(myCarId))) {
					// if I am on bridge do these things
					try {
						// send ACK to everyone in the queue. BCZ I am done.
						// for (final Integer integer : queue) {
						sendToServer(new MessageImpl(myCarId, getCurrentTimeStamp(), MessageTypes.Acknowledge));
						// }
						queue.clear();
					} catch (final IOException e) {
						e.printStackTrace();
					}
					resetAckStatus(carAcknowledgementStatusMap);
					// hadIrequestedTheBridge = false;
					amIonBridge = false;
				} else {
					if (!queue.isEmpty()) {
						queue.remove();
					}
				}
				onBridge.remove(Integer.valueOf(myMessage.getCarId()));
				myFrame2.updateOnBridgeLabel(onBridge.toString());
				System.out.println("Bridge is free now: " + onBridge);
			} else if (myMessage.getMessageType().equals(MessageTypes.Acknowledge)) {
				// Acknowledge
				// if (myCarId != 2) {
				// System.out.println("debug Me");
				// }
				if (hadIrequestedTheBridge && !onBridge.contains(myCarId)) {
					System.out.println(" My Car Id: " + myCarId + " ACK from Car Id: " + myMessage.getCarId());
					carAcknowledgementStatusMap.put(myMessage.getCarId(), "ACK");
					if (getUpdatedAckStatus(carAcknowledgementStatusMap)) {
						try {
							System.out.println("I am on bridge: " + myCarId);
							sendToServer(new MessageImpl(myCarId, myMessage.getTimeStamp() + 1, MessageTypes.OnBridge));
						} catch (final IOException e) {
							e.printStackTrace();
						}
						hadIrequestedTheBridge = false;
						amIonBridge = true;
					}
				}

			} else if (myMessage.getMessageType().equals(MessageTypes.OnBridge)) {
				onBridge.add(myMessage.getCarId());
				myFrame2.updateOnBridgeLabel(onBridge.toString());
				System.out.println("On bridge: " + onBridge);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void resetAckStatus(Map<Integer, String> carAcknowledgementStatusMap2) {
		carAcknowledgementStatusMap.put(1, "NCK");
		carAcknowledgementStatusMap.put(2, "NCK");
		carAcknowledgementStatusMap.put(3, "NCK");
		carAcknowledgementStatusMap.put(4, "NCK");
	}

	private boolean getUpdatedAckStatus(Map<Integer, String> carAcknowledgementStatusMap2) {
		for (final Entry<Integer, String> entry : carAcknowledgementStatusMap2.entrySet()) {
			if (entry.getValue().equals("NCK")) {
				return false;
			}
		}
		return true;
	}

	private int getCurrentTimeStamp() {
		myCurrentTimeStamp = myCurrentTimeStamp + 1;
		return myCurrentTimeStamp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// try {
		// final Scanner scanner = new Scanner(new
		// InputStreamReader(System.in));
		// System.out.println("Enter Car Id:");
		// final int carId = scanner.nextInt();
		// System.out.println("F for FWD and B for BCK");
		// final String direction = scanner.next();
		// final CarClient3 myClient = new CarClient3(carId, direction,
		// "localhost", 5555);
		// myClient.openConnection();
		// System.out.println("Press 1 for bridge request & 2 for bridge
		// release");
		// while (true) {
		// final int option = scanner.nextInt();
		// if (option == 2) {
		// // callBridgeReleaseRequest(direction, myClient);
		// } else if (option == 1) {
		// // callBridgeRequest(carId, direction, myClient);
		// } else {
		// break;
		// }
		// }
		// scanner.close();
		// } catch (final IOException e) {
		// e.printStackTrace();
		// }
	}

	public void callBridgeRequest(final CarClient3 myClient) {
		myClient.hadIrequestedTheBridge = true;
		try {
			myClient.sendToServer(new MessageImpl(myClient.myCarId, myClient.getCurrentTimeStamp(),
					myClient.myDirection, MessageTypes.BridgeRequest));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void callBridgeRelease(final CarClient3 myClient) {
		if (!myClient.amIonBridge) {
			System.out.println("Please request the bridge first");
		} else {
			try {
				myClient.sendToServer(new MessageImpl(myClient.myCarId, myClient.getCurrentTimeStamp(),
						myClient.myDirection, MessageTypes.BridgRelease));
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

}
