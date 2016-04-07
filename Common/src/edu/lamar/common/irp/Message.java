package edu.lamar.common.irp;

import java.io.Serializable;

public interface Message extends Serializable{

	int getCarId();
	int getTimeStamp();
	MessageTypes getMessageType();
	String getDirection();
}
