package com.miteprod.miteclient.installer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class InstallerThread extends Thread {
	
	private final Installer installer;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
	
	public InstallerThread(Installer installer) {
		this.installer = installer;
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		final File mcDir = new File(OSHelper.getOS().getMcDir());
		if(!mcDir.exists()) {
			mcDir.mkdirs();
		}
		
	}
	
	@Override
	public void run() {

		final String mc = OSHelper.getOS().getMcDir();
		
		try {
			
			File versionDir = new File(mc, "versions/MiteClient/");
			FileHelper.deleteDirectory(versionDir);
			versionDir.mkdirs();
			
			String installDate = sdf.format(new Date());
			
			final JsonObject newProfile = new JsonObject();
			newProfile.addProperty("name", "MiteClient v" + Constants.getVersionNumber());
			newProfile.addProperty("type", "custom");
			newProfile.addProperty("created", installDate);
			newProfile.addProperty("lastUsed", installDate);
			newProfile.addProperty("icon", Constants.MC_LAUNCHER_ICON);
			newProfile.addProperty("lastVersionId", "MiteClient");
			
			final File launcherProfileFile = new File(mc, "/launcher_profiles.json");
			JsonObject launcherProfile = new JsonObject();
			if(launcherProfileFile.exists()) {
				launcherProfile = new JsonParser().parse(FileHelper.readFile(launcherProfileFile)).getAsJsonObject();
			} else {
				launcherProfile.add("profiles", new JsonObject());
			}
			
			launcherProfile.get("profiles").getAsJsonObject().add("MiteClient", newProfile);
			launcherProfile.addProperty("selectedProfile", "MiteClient");
			
			String jsonToWrite = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(launcherProfile);
			
			FileHelper.writeFile(jsonToWrite, launcherProfileFile);
			
			System.out.println("Installing JSON File");
			FileHelper.writeFile(Constants.getJson(), new File(versionDir, "/MiteClient.json"));
			installer.moveForward();
			
			System.out.println("Installing Jar File");
			FileHelper.writeFile(Constants.getJar(), new File(versionDir, "/MiteClient.jar"));
			installer.moveForward();
			
		}
		catch (Exception e) {
			System.err.println("Failed to download client files. Shutting down.");
			installer.die(e);
		}
		
	}

}
