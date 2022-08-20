package com.miteprod.miteclient.installer;

import javax.swing.JOptionPane;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;

public class Installer extends Application {

	public static void main(String[] args) {
		System.out.println("Starting Java Application");
		Application.launch(Installer.class);
	}
	
	private static Installer instance;
	private final FeedbackHandler feedbackHandler = new FeedbackHandler();
	private final WebView webView = new WebView();
	private int currentIndex;

	@Override
	public void start(Stage stage) throws Exception {
		instance = this;
		
		final VBox layout = new VBox();
		
		layout.getChildren().add(webView);
		stage.setScene(new Scene(layout));
		stage.setTitle("MiteClient Installer");
		stage.getIcons().add(new Image(Constants.getIcon()));
		stage.setResizable(false);
		stage.setWidth(640);
		stage.setHeight(480);
		webView.setContextMenuEnabled(false);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				webView.getEngine().load(ClassLoader.getSystemResource("index.html").toExternalForm());
				webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
					
					if(newValue == Worker.State.SUCCEEDED) {
						((JSObject)webView.getEngine().executeScript("window")).setMember("feedback", feedbackHandler);
						if(webView.getEngine().getLocation().toLowerCase().contains("index.html")) {
							registerWorkers();
						}
					}
					
				});
			}
			
		});
		
	stage.show();
	
	System.out.println("Application Started!");
		
	}
	
	private void registerWorkers() {
		new InstallerThread(this).start();
		moveForward();
		new Timeline(new KeyFrame[] { new KeyFrame(Duration.minutes(2), e -> {
		
			if(this.currentIndex > 100) {
				die(new Exception("Timed Out"));
			}
			
		}, new KeyValue[0])}).play();
	}

	public void moveForward() {
		this.currentIndex += 34;
		if(currentIndex > 100) {
			new Timeline(
					new KeyFrame[] {
							new KeyFrame(
								Duration.seconds(4), 
								e -> Platform.runLater(
										() -> webView.getEngine().executeScript("javascript:finished()")
								),
								new KeyValue[0]
							)
					}
			)
			.play();
		}
		
	}

	public void die(Throwable err) {
		err.printStackTrace();
		JOptionPane.showMessageDialog(null, err.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
		stopApplication();
	}

	public void stopApplication() {
		System.exit(0);
	}

	public static Installer getInstance() {
		return instance;
	}

}
