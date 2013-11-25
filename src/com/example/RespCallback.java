package com.example;

import java.util.ArrayList;

public interface RespCallback {
	public void callback_events(ArrayList<Event> Events);
	public void callback_ack();

}
