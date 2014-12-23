package com.xindong.tank;
//Tank images and sound effects collected from the Internet


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class Main extends Application {
	GameLauncher gl = new GameLauncher();
	boolean vic = false;
	boolean run = true;

	private class JFXThread implements Runnable {

		@Override
		public void run() {
			while (run) {
				vic = gl.victory;
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (vic) {
							run = false;
							showdialog();
						}
					}
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void start(Stage primaryStage) {
		new Thread(new JFXThread()).start();

		try {
			Parent root = FXMLLoader.load(getClass().getResource("tank.fxml"));
			Scene scene = new Scene(root, 1600, 768);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tank War");
			primaryStage.setFullScreen(true);
			primaryStage.show();
			Button button = (Button) root.lookup("#start");
			button.setOnAction(e -> {

				GamePanel mPanel = new GamePanel();
				final Scene scene2 = new Scene(mPanel, 1, 1);
				scene2.setFill(Color.BLACK);
				primaryStage.setScene(scene2);
				primaryStage.setTitle("JavaFX Tank Wars");
				gl.lauchFrame();
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showdialog() {
		try {
			Parent root = FXMLLoader
					.load(getClass().getResource("dialog.fxml"));

			Scene scene = new Scene(root, 500, 300);
			Stage end = new Stage();
			end.setScene(scene);
			end.setTitle("Tank War");
			end.show();
			Button button1 = (Button) root.lookup("#play");
			button1.setOnAction(e -> {
				gl.lauchFrame();
				run=true;
				new Thread(new JFXThread()).start();
			});
			Button button2 = (Button) root.lookup("#ok");
			button2.setOnAction(e -> {
				System.exit(0);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	
}
