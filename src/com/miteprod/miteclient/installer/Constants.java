package com.miteprod.miteclient.installer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Constants {
	
	public static final String MC_LAUNCHER_ICON = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABhWlDQ1BJQ0MgcHJvZmlsZQAAKJF9kT1Iw0AYht+mSotUHewg4pChOlkUFXGUKhbBQmkrtOpgcukfNGlIUlwcBdeCgz+LVQcXZ10dXAVB8AfE0clJ0UVK/C4ptIjxjuMe3vvel7vvAKFRYarZNQGommWk4jExm1sVA68IIow+muMSM/VEejEDz/F1Dx/f76I8y7vuz9Gr5E0G+ETiOaYbFvEG8cympXPeJw6zkqQQnxOPGXRB4keuyy6/cS46LPDMsJFJzROHicViB8sdzEqGSjxNHFFUjfKFrMsK5y3OaqXGWvfkLwzltZU012kNI44lJJCECBk1lFGBhSjtGikmUnQe8/APOf4kuWRylcHIsYAqVEiOH/wPfvfWLExNukmhGND9YtsfI0BgF2jWbfv72LabJ4D/GbjS2v5qA5j9JL3e1iJHQP82cHHd1uQ94HIHGHzSJUNyJD8toVAA3s/om3LAwC3Qs+b2rXWO0wcgQ71avgEODoHRImWve7w72Nm3f2ta/fsBkJVys2CSUe0AAAAGYktHRAD/AP8A/6C9p5MAAAAJcEhZcwAALiMAAC4jAXilP3YAAAAHdElNRQfmCBQODzvfzwHXAAAAGXRFWHRDb21tZW50AENyZWF0ZWQgd2l0aCBHSU1QV4EOFwAAA1FJREFUWMPtl01oVFcUx3/3TaYaXDguiqFomaKoGISkGLTFr2kVFL/iwkURJeMHR6EFlS7EhWZlKV3o9lAlgggqfgx+RG2EBnRhXQ0jaHQRB13ooosxYsZkOnNd5D55eX3JvOjSHHg87j3vnPO/557/mTMwKZPyuYsZS6GqLUATMAN4LCL5UYblSgew3hoDSe8GCe+0s2sDZgFl4FXYri4AVf0WOAMsDKleAIf3bstet18kciS9lUFlc3no0b6LF75KlsupkF0R+EVErtcFoKodQFdg6zXwHFjkb9zetLl0uWlmKsrZ1pevWH3tqg8WYHZAnQOyIlKKBKCqaeCZW94HfhKRotPNAI69nL9gb+fKFePeaXpweHdx2pRTAZ8ngM1O3SsimbEAdAEdwFNgkYgMh50nK9UnlQZv3rgILEU8800os+3AFbc8KCLHfZ0X+O4H9z4aFdx7M9xSN/jIkdJUa63BLRHJuUwAjEphEMB6d0fnIg82NbE2Nrfsh5QHQRwAMkD24whbq13CWhvr+a/WE9dtw3hKVU0CPwJLLw4MrOiZPj2W02XvBuduV+0E+oBLIlKZEABVXQdIoHqZVqnETtYgXho4GvCXA46IyMP/1VYocMp93B0I/gbo/bexsRgXwHMvWXW89znfDhRU9eCYjUhVU0Ah0DxOA+dF5NZIYdm/gVUxMfRiTEZVE8BG4AjgM+OkiOyJAuAHKAFbReTOKKTV2hXrmfaYLLiDZ9aEsuv3GYAdInImfAX+6baEgzv5ZwKcuRdBw6zLKsDOqCLcAjSJSG+Ux139/X0n586JFT0xVO2uRqsOAI+A2DT1C/M3VbWrXg/U7QFL3761qrosrn+vTvA2l85DAEv6nvTXc7i6UAC4q6p/qOqXHw3A/TQ/AJpdYe7/fUnbHGO5OZZN87uh/OxCwb/CX4Gnqtqpqo0TmohUdSdwyi3zrjBH+kDNpoy13dYz341mic3bhMlgTElVs0An8HXARyY8C0RmQFWXA38GekHmQ3AAz5RswvseyJqazZnayKBhG7xWjCm5iu8CFjgQJaAFOBt3JHsGpIG8iLR+6tCpqmuAv9yyNTwjhlvxYhd80NHyk0VEelwmS4HWPC7in1V1w+Qfhs9G3gMlbG+MQO6wCgAAAABJRU5ErkJggg==";
	
	private static final String BASE_URL = "https://raw.githubusercontent.com/TheBlueRuby/MiteClient-Archive/main/";
	
	private static String versionNumber = null;
	
	public static String getVersionNumber() {
		if(versionNumber == null) {
			
			try {
				InputStream stream = FileHelper.getStreamFromURL(BASE_URL + "version.txt");
				InputStreamReader reader = new InputStreamReader(stream);
				BufferedReader bufferedReader = new BufferedReader(reader);
				versionNumber = bufferedReader.readLine();
				bufferedReader.close();
				reader.close();
				stream.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
				Installer.getInstance().die(e);
			}
			
		}
		
		return versionNumber;
	}
	
	public static InputStream getIcon() {
		return ClassLoader.getSystemResourceAsStream("assets/icon.png");
	}
	
	public static InputStream getJar() throws IOException {
		return FileHelper.getStreamFromURL(BASE_URL + getVersionNumber() + "/" + "MiteClient.jar");
	}
	
	public static InputStream getJson() throws IOException {
		return FileHelper.getStreamFromURL(BASE_URL + getVersionNumber() + "/" + "MiteClient.json");
	}
	
}
