package cs1302.fxgame;

import com.michaelcotterell.game.Game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bricks extends Rectangle{
    
    boolean dead = false;
    int brickLon = 120;
    int brickAlt = 40;
    Game brik;
    
    //this is a declaration for the brick array.
    Rectangle[] BrickArr = new Rectangle[195];
    
    /**
     * This is the constructor for the bricks, it makes a brick array,
     * which is what is displayed in the main game. I spent a very long time with this
     * 
     * 
     * @param game I don't know what i was trying to do with this
     * It takes in game, but i was trying to do something with it, but
     * it didn't turn out right.
     */
    public Bricks(Game game) {
	brik = game;
	
	for (int i = 0; i < BrickArr.length; i++) {
	    
	    BrickArr[i] = new Rectangle(brickLon, brickAlt, 30, 10);
	    BrickArr[i].setFill(Color.GREEN);
	    
	    if (i == 12) {
		brickLon = 105;
		brickAlt = 52;
	    }
	    if (i == 24) {
		brickLon = 120;
		brickAlt = 64;
	    }
	    if (i == 35) {
		brickLon = 100;
		brickAlt = 76;
	    }
	    if (i == 38) {
		brickLon = 230;
		brickAlt = 76;
	    }
	    if (i == 43) {
		brickLon = 420;
		brickAlt = 76;
	    }
	    if (i == 45) {
		brickLon = 90;
		brickAlt = 88;
	    }
	    if (i == 48) {
		brickLon = 250;
		brickAlt = 88;
	    }
	    if (i == 52) {
		brickLon = 430;
		brickAlt = 88;
	    }
	    if (i == 55) {
		brickLon = 75;
		brickAlt = 100;
	    }
	    if (i == 59) {
		brickLon = 230;
		brickAlt = 100;
	    }
	    if (i == 64) {
		brickLon = 420;
		brickAlt = 100;
	    }
	    if (i == 68) {
		brickLon = 70;
		brickAlt = 112;
	    }
	    if (i == 82) {
		brickLon = 80;
		brickAlt = 124;
	    }
	    if (i == 95) {
		brickLon = 90;
		brickAlt = 136;
	    }
	    if (i == 107) {
		brickLon = 100;
		brickAlt = 148;
	    }
	    if (i == 112) {
		brickLon = 360;
		brickAlt = 148;
	    }
	    if (i == 115) {
		brickLon = 110;
		brickAlt = 160;
	    }
	    if (i == 119) {
		brickLon = 380;
		brickAlt = 160;
	    }
	    if (i == 121) {
		brickLon = 30;
		brickAlt = 172;
	    }
	    if (i == 137) {
		brickLon = 20;
		brickAlt = 184;
	    }
	    if (i == 154) {
		brickLon = 0;
		brickAlt = 196;
	    }
	    if (i == 172) {
		brickLon = -20;
		brickAlt = 208;
	    }
	    if (i == 176) {
		brickLon = 460;
		brickAlt = 208;
	    }
	    if (i == 180) {
		brickLon = -30;
		brickAlt = 220;
	    }
	    if (i == 185) {
		brickLon = 440;
		brickAlt = 220;
	    }
	    if (i == 190) {
		brickLon = 30;
		brickAlt = 74;
	    }
	    if (i == 191) {
		brickLon = 520;
		brickAlt = 64;
	    }
	    if (i == 192) {
		brickLon = 520;
		brickAlt = 52;
	    }
	    if (i == 193) {
		brickLon = 30;
		brickAlt = 62;
	    }
	    brickLon += 32;
	}//for
    }//bricks
    
    /**
     * This is a method for returning an int
     * based on what color a single brick is.
     * I use this int for behavior determination
     * for collisions.
     * 
     * @param x A rectangle is passed in from the brick array
     * @return an int from 0 to 3
     */
    public int getColorHp(Rectangle x){
	int colorNum = 0;
	if (x.getFill() == Color.GREEN)
	    colorNum = 3;
	if (x.getFill() == Color.YELLOW)
	    colorNum = 2;
	if (x.getFill() == Color.RED)
	    colorNum = 1;
	return colorNum;
    }
    
    /**
       public void brickLifeAlert(){
       for (int i = 0; i < BrickArr.length; i++){
       if (hp == 2)
       BrickArr[i].setFill(Color.YELLOW);
       if (hp == 1)
       BrickArr[i].setFill(Color.RED);
       if (hp == 0){
       BrickArr[i] = null;
       }
       
       }//for
       }//life
    */
    
}//brick
