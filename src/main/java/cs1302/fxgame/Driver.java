package cs1302.fxgame;

import com.michaelcotterell.game.Game;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import javafx.scene.media.MediaException;

public class Driver extends Application {

    

    //URL music = getClass().getResource("/BGM.mp3");
    //File music = new File("src/main/rsources/BGM.mp3");
    //Media bgm = new Media(music.toURI().toURL().toExternalForm());
    Media bgm;
    //Media bgm = new Media(new File("src/main/resources/BGM.mp3").toURI().toString());
    MediaPlayer player;

    

    @Override
	public void start(Stage primaryStage) throws Exception {

	try {
	    bgm = new Media(Driver.class.getResource("/BGM.mp3").toExternalForm());
	    player = new MediaPlayer(bgm);
	    Game game = new Brickbreak(primaryStage);
	    primaryStage.setTitle(game.getTitle());
	    primaryStage.setScene(game.getScene());
	    primaryStage.show();
	    player.setCycleCount(MediaPlayer.INDEFINITE);
	    player.setVolume(0.4);
	    player.play();
	    game.run();
	}catch (MediaException e){
	    Game game = new Brickbreak(primaryStage);
	    primaryStage.setTitle(game.getTitle());
	    primaryStage.setScene(game.getScene());
	    primaryStage.show();
	    game.run();
	}
    } // start

    public static void main(String[] args) {
	launch(args);
    } // main

} // Driver

