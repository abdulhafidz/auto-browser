package com.hafidz.autoBrowser;

import com.hafidz.autoBrowser.eventHandler.PlayButtonHandler;
import com.hafidz.autoBrowser.eventHandler.StopButtonHandler;
import com.hafidz.autoBrowser.listener.PageLoadListener;
import com.hafidz.autoBrowser.util.ListenerUtil;
import com.hafidz.autoBrowser.util.WebUtil;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

	public static WebView browser;

	public static TextArea recordingList;
	public static TextArea playbackList;
	public static WebView testResultList;

	public static Label componentInfo = new Label();
	public static Label componentValue = new Label();
	public static Label componentXpath = new Label();

	public static Button stupidBT = new Button("Analyze Component");

	public static Button stopBT;
	public static Button playBT;

	public static boolean recording;

	public static Button recordBT;
	public static Button recordPauseBT;

	public static TabPane activityTabPanel;
	public static Tab infoTab;
	public static Tab recordingTab;
	public static Tab playbackTab;
	public static Tab testResultTab;

	static {
		componentInfo.setWrapText(false);
		componentInfo.setTextOverrun(OverrunStyle.ELLIPSIS);

		componentValue.setWrapText(false);
		componentValue.setTextOverrun(OverrunStyle.ELLIPSIS);

		componentXpath.setWrapText(false);
		componentXpath.setTextOverrun(OverrunStyle.ELLIPSIS);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();

			SplitPane splitPL = new SplitPane();
			splitPL.setDividerPositions(0.85);
			root.setCenter(splitPL);

			// // network monitoring
			// URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
			//
			// @Override
			// public URLStreamHandler createURLStreamHandler(String protocol) {
			// System.out.println("protocol = " + protocol);
			// return null;
			// }
			// });

			activityTabPanel = new TabPane();
			activityTabPanel.setPrefWidth(250);
			splitPL.getItems().add(activityTabPanel);

			// - - - - - - -
			// info panel
			// - - - - - - -
			VBox infoPL = new VBox();

			infoTab = new Tab("Info");
			infoTab.setClosable(false);
			infoTab.setContent(infoPL);
			activityTabPanel.getTabs().add(infoTab);

			// - - - - - - - - -
			// recording panel
			// - - - - - - - - -
			BorderPane recordingPL = new BorderPane();

			// add to tab panel
			recordingTab = new Tab("Recording");
			recordingTab.setClosable(false);
			recordingTab.setContent(recordingPL);
			activityTabPanel.getTabs().add(recordingTab);

			// history list
			recordingList = new TextArea();
			recordingList.setWrapText(false);
			recordingList.setEditable(false);
			recordingPL.setCenter(recordingList);

			Button clearBT = new Button("Clear");
			clearBT.setOnAction(event -> recordingList.clear());

			recordBT = new Button("Record");
			recordPauseBT = new Button("Pause");
			recordPauseBT.setDisable(true);

			recordBT.setOnAction(event -> {
				recordBT.setDisable(true);
				recordPauseBT.setDisable(false);
				recording = true;
				recordBT.setText("Recording...");
				ListenerUtil.updateListeners();
			});
			recordPauseBT.setOnAction(event -> {
				recordBT.setDisable(false);
				recordPauseBT.setDisable(true);
				recording = false;
				recordBT.setText("Record");
			});

			// bottom button panels
			FlowPane recordButtonPL = new FlowPane();
			recordButtonPL.getChildren().add(recordBT);
			recordButtonPL.getChildren().add(recordPauseBT);
			recordButtonPL.getChildren().add(clearBT);
			recordingPL.setBottom(recordButtonPL);

			// - - - - - - - - -
			// playback panel
			// - - - - - - - - -
			BorderPane playbackPL = new BorderPane();

			// add to tab panel
			playbackTab = new Tab("Playback");
			playbackTab.setClosable(false);
			playbackTab.setContent(playbackPL);
			activityTabPanel.getTabs().add(playbackTab);

			// playback list
			playbackList = new TextArea();
			playbackList.setWrapText(false);
			playbackPL.setCenter(playbackList);

			// bottom button panels
			FlowPane playbackkButtonPL = new FlowPane();
			playbackPL.setBottom(playbackkButtonPL);

			// // play button
			// playBT = new Button("Play");
			// playBT.setOnAction(new PlayButtonHandler(playbackList));
			// playbackkButtonPL.getChildren().add(playBT);

			// stop button
			stopBT = new Button("Stop");
			stopBT.setDisable(true);
			stopBT.setOnAction(new StopButtonHandler());
			playbackkButtonPL.getChildren().add(stopBT);

			// - - - - - - -
			// Test Panel
			// - - - - - - -
			BorderPane testPL = new BorderPane();

			// add to tab panel
			testResultTab = new Tab("Test Results");
			testResultTab.setDisable(true);
			testResultTab.setClosable(false);
			testResultTab.setContent(testPL);
			activityTabPanel.getTabs().add(testResultTab);

			// test results
			testResultList = new WebView();
			// testResultList.setEditable(false);
			// testResultList.setWrapText(false);
			// testResultList.getEngine().load(ResultConstant.EMPTY_RESULT_HTML);
			testPL.setCenter(testResultList);

			// play button (initialise here because we need testResultList to be
			// initialised before)
			playBT = new Button("Play");
			playBT.setOnAction(new PlayButtonHandler(playbackList, testResultList));
			playbackkButtonPL.getChildren().add(playBT);

			// bottom button panels
			FlowPane testButtonPL = new FlowPane();
			testPL.setBottom(testButtonPL);

			// // clear button
			// Button clearTestBT = new Button("Clear");
			// clearTestBT.setOnAction(event ->
			// testResultList.getEngine().loadContent(ResultConstant.EMPTY_RESULT_HTML));
			// testButtonPL.getChildren().add(clearTestBT);

			// - - - - - - - - - - - -
			// top bar [START]
			// - - - - - - - - - - - -
			VBox topBar = new VBox();
			FlowPane urlPane = new FlowPane();
			urlPane.getChildren().add(new Label(" URL : "));
			TextField urlTF = new TextField();
			urlTF.setPrefColumnCount(30);
			urlPane.getChildren().add(urlTF);

			Button goBT = new Button("GO!");
			goBT.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					WebUtil.openURL(urlTF.getText());

				}
			});
			urlPane.getChildren().add(goBT);

			// stupid button
			stupidBT.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					ListenerUtil.updateListeners();

				}
			});
			urlPane.getChildren().add(stupidBT);
			urlPane.getChildren().add(componentInfo);
			topBar.getChildren().add(urlPane);

			urlTF.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ENTER) {

						browser.requestFocus();
						WebUtil.openURL(urlTF.getText());

					}
				}

			});
			// topBar.getChildren().add(componentInfo);
			topBar.getChildren().add(componentXpath);
			topBar.getChildren().add(componentValue);

			root.setTop(topBar);
			// - - - - - - - - - - - -
			// top bar [END]
			// - - - - - - - - - - - -

			// - - - - - - - - - - - - - - -
			// web view [START]
			// - - - - - - - - - - - - - - -
			browser = new WebView();

			// determine page loaded
			browser.getEngine().getLoadWorker().stateProperty().addListener(new PageLoadListener());

			splitPL.getItems().add(0, browser);
			// - - - - - - - - - - - - - - -
			// web view [END]
			// - - - - - - - - - - - - - - -

			Scene scene = new Scene(root);
			scene.getStylesheets().add(

					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Auto Browser");
			primaryStage.show();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
