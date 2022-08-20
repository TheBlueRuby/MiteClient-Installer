package com.miteprod.miteclient.installer;

import java.io.File;

public enum OSHelper {
	
	WINDOWS("AppData" + File.separator + "Roaming" + File.separator + ".minecraft"),
	MAC("Library" + File.separator + "Application Support" + File.separator + "minecraft"),
	LINUX(".minecraft");
	
	private final String mcDir;
	
	private OSHelper(String mcDir) {
		this.mcDir = File.separator + mcDir + File.separator;		
	}
	
	public String getMcDir() {
		return System.getProperty("user.home") + mcDir;
	}
	
	public static final OSHelper getOS() {
		final String currentOS = System.getProperty("os.name").toLowerCase();
		if(currentOS.startsWith("windows")) {
			return WINDOWS;
		}
		else if (currentOS.startsWith("mac")) {
			return MAC;
		}
		else {
			return LINUX;
		}
	}

}
