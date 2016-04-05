/**
 * 
 */
package edu.lamar.common;

import edu.lamar.common.irp.Message;
import edu.lamar.common.irp.MessageTypes;

/**
 * @author user
 *
 */
public class MessageImpl implements Message {
	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	final int carId;
	final int timeStamp;
	final MessageTypes messageTypes;
	public MessageImpl(int carId, int timeStamp, MessageTypes messageTypes) {
		this.carId = carId;
		this.timeStamp = timeStamp;
		this.messageTypes = messageTypes;
	}

	@Override
	public int getCarId() {
		return carId;
	}

	@Override
	public int getTimeStamp() {
		return timeStamp;
	}

	@Override
	public MessageTypes getMessageType() {
		return messageTypes;
	}

}
