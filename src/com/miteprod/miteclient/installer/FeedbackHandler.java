package com.miteprod.miteclient.installer;

public class FeedbackHandler {
	
	public void close() {
		Installer.getInstance().stopApplication();
	}

}
