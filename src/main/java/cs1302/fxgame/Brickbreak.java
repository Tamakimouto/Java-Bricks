package cs1302.fxgame;

import com.michaelcotterell.game.Game;
import com.michaelcotterell.game.GameTime;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
//import java.net.URL;
//import java.io.File;
//import java.nio.file.Paths;
import java.util.Random;
import javafx.scene.media.MediaException;

public class Brickbreak extends Game {

    //an enum for the game mode
    public enum gameMode {start, play, win, gameover};
    gameMode mode = gameMode.start;

    int score = 0;
    int FScore;
    int lives = 3;
    int bricksKilled = 0;
    boolean sound = false;
    boolean exLife = true;
    boolean exLife2 = true;
    boolean exLife3 = true;

    //the title image
    Image title = new Image("/Title.jpg");
    
    //the gameover image
    Image end = new Image("/end.jpg");

    AudioClip hitsound;

    // rectangle to hold the background
    private Rectangle bg = new Rectangle(0, 0, 640, 480) {{
	setFill(Color.BLACK);
    }};

    
    //The canvas which displays the S picture
    Canvas bgt = new Canvas(640, 480); {{
	    GraphicsContext tool = bgt.getGraphicsContext2D();
	    tool.drawImage(title, 0, 0);
	}};

    //The canvas which displays the E picture
    Canvas bge = new Canvas(640, 480); {{
	    GraphicsContext otherTool = bge.getGraphicsContext2D();
	    otherTool.drawImage(end, 0, 0);
	}};

    // some text to display the time
    private Rectangle strongarm = new Rectangle(320, 470, 30, 5) {{
	setFill(Color.YELLOWGREEN);
    }};

    //This is the ball
    Rectangle ball = new Rectangle(bg.getWidth()/2, bg.getHeight()/2, 5, 5) {{
	setFill(Color.PINK);
    }};

    //This is my initialization of my bricks class.
    Bricks brickset = new Bricks(this);

    //Just some txt
    private Text tempTxt = new Text() {{
	setX(bg.getWidth()/2);
	setY(10);
	setFill(Color.RED);
    }};

    /**
     * This is the story line text,
     * It is based on game time, so for testing purposes
     * don't stay in the start screen for too long
     *
     * I don't know how to manipulate the Game Time as it is
     * something Cotteral created.
     */
    private Text story = new Text() {{
	setX(bg.getWidth()/2 - 140);
	setY(bg.getHeight()/2 - 10);
	setFill(Color.RED);
	setTextAlignment(TextAlignment.CENTER);
    }};

    //the score and life count at the top of the scene
    private Text scoreKeeper = new Text() {{
	setX(bg.getWidth()/2 - 55);
	setY(20);
	setFill(Color.RED);
    }};

    //the blinking press enter text
    private Text startBlink = new Text() {{
	setText("Press Enter to Start");
	setX(bg.getWidth()/2 - 60);
	setY(bg.getHeight()/2 + 90);
	setFill(Color.CYAN);
    }};

    //the game over message
    private Text loseMess = new Text() {{
	setTextAlignment(TextAlignment.CENTER);
	setFill(Color.RED);
	setY(bg.getHeight()/2 - 30);
	setX(bg.getWidth()/2 - 140);
    }};

    //the win message
    private Text winMess = new Text() {{
	setTextAlignment(TextAlignment.CENTER);
	setFill(Color.RED);
	setY(bg.getHeight()/2 - 20);
	setX(bg.getWidth()/2 - 120);
    }};

    /**
     * Constructs a new game.
     *
     * @param stage the primary stage
     */
    public Brickbreak(Stage stage) {
	super(stage, "Brick: Tale of Bustard Bernard II", 60, 640, 480);
	//the hitsound for the ball
	try {
	    hitsound = new AudioClip(Driver.class.getResource("/hitsound.wav").toString());
	    sound = true;
	}catch (MediaException e){
	    System.out.println("hitsound could not be loaded");
    }
    } /////////////////////////////// bricks constructor///////////////////////////////////////

    public boolean inBoundsArm(double x){
	boolean bounds = true;
	if (x > bg.getWidth() - strongarm.getWidth() || x < 0)
	    bounds = false;
	return bounds;
    }///////////////////////////////////////////////In bounds method for the paddle///////////////////////

    //array for ball speed
    int[] xDirPool = {-4, -3, -2, -1 , 0, 1, 2, 3, 4};
    int xDir = 0;

    //array for ball speed
    int[] yDirPool = {-4, -3, -2, -1, 1, 2, 3, 4};
    int yDir = 0;

    /**
     * This methods adds the next ball frame, essentially moving the
     * ball once the update method is called
     */
    public void moveBall(){
	ball.setX(ball.getX() + xDir);
	ball.setY(ball.getY() + yDir);
    }///////////////////////////////////////////move ball/////////////////////////////////////
    
    //New random, for the ball speed/direction
    Random slots = new Random();
    
    /**
     * This is the collision handler
     * 
     * It determines what happens when a ball touches something.
     * be it wining the game, losing the game, reversing the ball trajectory
     * etc
     */
    public void collisionHandler(){
	if (ball.getX() <= 0){ //left edge
	    try {
		if (sound)
		    hitsound.play();
	    }catch (Exception e) {
		System.out.println("No Sounds, Sorry");
	    }//catch
	    try {
		xDir = xDirPool[slots.nextInt(6) + 4];
	    }catch (Exception e){}
	}
	if (ball.getX() >= bg.getWidth() - ball.getWidth()){ //right edge
	    try {
		if (sound)
		    hitsound.play();
	    }catch (Exception e) {
		System.out.println("No Sounds, Sorry");
	    }//catch
	    try {
		xDir = xDirPool[slots.nextInt(5)];
	    }catch (Exception e) {}
	}
	if (ball.getY() - ball.getHeight() <= 0){ //top edge
	    try {
		if (sound)
		    hitsound.play();
	    }catch (Exception e) {
		System.out.println("No Sounds, Sorry");
	    }//catch
	    try {
		yDir = yDirPool[slots.nextInt(8) + 4];
	    }catch (Exception e){}
	}
	if (ball.getX() + ball.getWidth() > strongarm.getX() //paddle collision
	    && ball.getX() < strongarm.getX() + strongarm.getWidth() 
	    && ball.getY() > strongarm.getY() - strongarm.getHeight()){
	    try {
		if (sound)
		    hitsound.play();
	    }catch (Exception e) {
		System.out.println("No Sounds, Sorry");
	    }//catch
	    xDir = xDirPool[slots.nextInt(9)];
	    yDir = yDirPool[slots.nextInt(4)];
	}//if 
	if (ball.getY() - ball.getHeight() > strongarm.getY())
	    bRes();
	for (int i = 0; i < brickset.BrickArr.length; i ++){
	    if (brickset.BrickArr[i] != null){
		if (ball.intersects(brickset.BrickArr[i].getX(), 
				    brickset.BrickArr[i].getY(), 
				    brickset.BrickArr[i].getWidth(), 
				    brickset.BrickArr[i].getHeight())
		    ){
		    switch (brickset.getColorHp(brickset.BrickArr[i])){
		    case 3:
			brickset.BrickArr[i].setFill(Color.YELLOW);
			try {
			    if (sound)
				hitsound.play();
			}catch (Exception e) {
			    System.out.println("No Sounds, Sorry");
			}//catch
			break;
		    case 2:
			brickset.BrickArr[i].setFill(Color.RED);
			try {
			    if (sound)
				hitsound.play();
			}catch (Exception e) {
			    System.out.println("No Sounds, Sorry");
			}//catch
			break;
		    case 1:
			try {
			    if (sound)
				hitsound.play();
			}catch (Exception e) {
			    System.out.println("No Sounds, Sorry");
			}//catch
			try{
			    brickset.BrickArr[i] = null;
			}catch (Exception e){}
			bricksKilled ++;
			if (bricksKilled == 30 && exLife){
			    lives ++;
			    exLife = false;
			    for (int x = 0; x < xDirPool.length; x++){
				xDirPool[x] = xDirPool[x]*2;
			    }
			    for (int y = 0; y < yDirPool.length; y++){
				yDirPool[y] = yDirPool[y]*2;
			    }
			}else if (bricksKilled == 60 && exLife2){
			    lives ++;
			    exLife2 = false;
			    for (int x = 0; x < xDirPool.length; x++){
				xDirPool[x] = xDirPool[x]*2;
			    }
			    for (int y = 0; y < yDirPool.length; y++){
				yDirPool[y] = yDirPool[y]*2;
			    }
			}else if (bricksKilled == 90 && exLife3){
			    lives ++;
			    exLife3 = false;
			    for (int x = 0; x < xDirPool.length; x++){
				xDirPool[x] = xDirPool[x]*2;
			    }
			    for (int y = 0; y < yDirPool.length; y++){
				yDirPool[y] = yDirPool[y]*2;
			    }
			}else if (bricksKilled == brickset.BrickArr.length){
			    mode = gameMode.win;
			}//else if
			score ++;
			FScore = score;
			//System.out.println(FScore);
			break;
		    }//switch
		    xDir = xDirPool[slots.nextInt(9)];
		    yDir = yDirPool[slots.nextInt(4) + 4];
		}//if
	    }//null check
	}//for
	//brickset.brickLifeAlert();
    }////////////////////////Collision Handler////////////////////////////////////////
    
    /**
     * to reset the position of the ball
     * used after the ball falls below the paddle
     */
    public void bRes(){
	ball.setX(bg.getWidth()/2);
	ball.setY(bg.getHeight()/2);
	xDir = 0;
	yDir = -1;
	lives --; 
	if (lives == 0){
	    mode = gameMode.gameover;
	}
    }////////////////////////////////////////////////reset ball///////////////////////////////////
    
    /**
     * to reset the position of the ball and set the score to 0
     * usually used after the user presses enter
     * after and ESC to prevent score cheating
     */
    public void gRes(){
	ball.setX(bg.getWidth()/2);
	ball.setY(bg.getHeight()/2);
	xDir = 0;
	yDir = -1;
	//timer = new GameTime();
    }//////////////////////////////////////////reset game////////////////////////////////////
    
    /**
     * The game mode for the start screen (ex credit)
     * 
     * @param Game the brick breaker game
     * @param GameTime the time
     */
    public void updateS(Game game, GameTime gameTime){
	getSceneNodes().getChildren().setAll(bgt, tempTxt, startBlink);
	if (gameTime.getTotalGameTime().getSeconds() % 2 == 1){
	    startBlink.setText("Press Enter to Start");
	}else {
	    startBlink.setText("");
	}//else if
	if (game.getKeyManager().isKeyPressed(KeyCode.ENTER)) {
	    mode = gameMode.play;
	    gRes();
	    score = 0;
	    lives = 3;
	    yDir = 1;
	}//if
    }////////////////////////////////////////introduction - start screen //////////////////////////////////////////
    
    /**
     * The game mode for the main game play
     * 
     * @param Game the brick breaker game
     * @param GameTime the time
     */
    public void updateP(Game game, GameTime timer) {
	getSceneNodes().getChildren().setAll(bg, strongarm, ball, story, scoreKeeper);
	try{
	    for (int i = 0; i < brickset.BrickArr.length; i ++){
		if (brickset.BrickArr[i] != null)
		    getSceneNodes().getChildren().addAll(brickset.BrickArr[i]);
	    }//for
	}catch (Exception e) {}
	if (timer.getTotalGameTime().getTotalSeconds() <= 15){
	    story.setText("Hey there! Welcome to \n"
			  + "Brick: Tale of Bustard Bernard the 2nd \n"
			  + "This is the story of Bernard, the brave ball. \n"
			  + "And his fellow sidekick Armstrong, the paddle!");
	}else if (timer.getTotalGameTime().getTotalSeconds() <= 25){
	    story.setText("Use the LEFT and RIGHT arrow keys \n"
			  + "to tell Armstrong where to go. \n"
			  + "It's up to Armstrong to prevent Bernard from falling! \n"
			  + "Bernard can jump off of Armstrong to keep afloat");
	}else if (timer.getTotalGameTime().getTotalSeconds() <= 33){
	    story.setText("But be careful though, \n"
			  + "Bernard is quite furious right now! \n"
			  + "Upon impact, the brave ball will \n"
			  + "sometimes defy the laws of Physics \n"
			  + "having lost itself in it's rage \n"
			  + "as result, Bernard may fly in his own direction \n"
			  + "and gain or lose speed at will.");
	}else if (timer.getTotalGameTime().getTotalSeconds() <= 41){
	    story.setText("Each Brick usuually requires 3 hits \n"
			  + "to take out, however, due to Bernards rage, \n"
			  + "periodically Bernard's hits may deal an \n"
			  + "incredible amount of damage. Sometimes even \n"
			  + "taking out multiple bricks in a single hit.");
	}else if (timer.getTotalGameTime().getTotalSeconds() <= 50){
	    story.setText("Them bastard bricks had an affair \n"
			  + "with Bernards wife, this hurt Bernard deeply. \n"
			  + "Help them get revenge on the Bricks!");
	}else if (timer.getTotalGameTime().getTotalSeconds() <= 58){
	    story.setText("Defeat the brick colony by \n"
			  + "slaughtering every single one of them. \n"
			  + "If the brave ball is able to strike \n"
			  + "the back of the bricks, it will no doubt \n"
			  + "be a critical hit, permanently paralyzing \n"
			  + "and eventually killing the the bricks.");
	}else if (timer.getTotalGameTime().getTotalSeconds() <= 68){
	    story.setText("");
	}//else if
	scoreKeeper.setText("Score: " + score + "\t Life: " + lives);
	//random color
	Color puke = new Color(slots.nextDouble(), slots.nextDouble(), slots.nextDouble(), 0.5);
	ball.setFill(puke);
	moveBall();
	collisionHandler();
        if (game.getKeyManager().isKeyPressed(KeyCode.LEFT)) 
	    if (inBoundsArm(strongarm.getX() - 4)){
		//strongarm.setTranslateX(strongarm.getTranslateX() - 4);
		strongarm.setX(strongarm.getX()-4);
	    }
        if (game.getKeyManager().isKeyPressed(KeyCode.RIGHT)) 
	    if (inBoundsArm(strongarm.getX() + 4)){
		//strongarm.setTranslateX(strongarm.getTranslateX() + 4);
		strongarm.setX(strongarm.getX()+4);
	    }
        if (game.getKeyManager().isKeyPressed(KeyCode.ESCAPE)){
	    mode = gameMode.start;
	    //GameTime timer = new GameTime();
        }//if
    }////////////////////////////////////////////update play///////////////////////////////////////////////////////////

    /**
     * The game mode for the winning message
     * 
     * @param Game the brick breaker game
     * @param GameTime the time
     */
    public void updateW(Game game, GameTime timer){
	FScore = score;
	winMess.setText("YOU WIN \n"
			+ "Oh my god, Someone actually did it. \n"
			+ "This is amazing, I did not program this game \n"
			+ "expecting someone to actually win the game. \n"
			+ "I thank you for getting this far. \n"
			+ "Have a great Christmas Break, \n"
			+ "If by some chance you are grading these projects \n"
			+ "I at least hope you got a little joy out of \n"
			+ "this game I made. It may not be the best game \n"
			+ "out of the whole class, hell it might even be the worst. \n"
			+ "but I hope you enjoyed it. \n"
			+ "Thanks for all of your hard work this semester."
			+ "\n"
			+ "YOUR SCORE: " + FScore);
	getSceneNodes().getChildren().setAll(bg, winMess);
	stop();
    }/////////////////////////////////////////////////update win///////////////////////////////////////////////////
    
    /**
     * The game mode for the losing message
     * 
     * @param Game the brick breaker game
     * @param GameTime the time
     */
    public void updateE(Game game, GameTime gameTime){
	FScore = score;
	loseMess.setText("YOU LOSE \n"
			 + "\n"
			 + "It turns out that Armstrong was \n"
			 + "Actually a BRICK the whole time! You were tricked! \n"
			 + "It was actually Armstrong and not \n"
			 + "the other bricks that Bernard's wife slept with \n"
			 + "Now you have murdered innocent bricks. \n"
			 + "Now that you have committed this sin, \n"
			 + "you must finish it, play again and kill ALL the bricks! \n"
			 + "\n"
			 + "YOUR SCORE: " + FScore);
	getSceneNodes().getChildren().setAll(bge, loseMess);
	stop();
    }//////////////////////////////////game over//////////////////////////////////////////////////////////////////
    
    /**
     * The main update method,
     * contains a switch statement for alternating between the modes.
     * 
     * @param Game the brick breaker game
     * @param GameTime the time
     */
    @Override
	public void update(Game game, GameTime gameTime) {
	switch (mode){
	case start:
	    updateS(game, gameTime);
	    break;
	case play:
	    updateP(game, gameTime);
	    break;
	case win:
	    updateW(game, gameTime);
	    break;
	case gameover:
	    updateE(game, gameTime);
	    break;
	}//switch
    } ////////////////////////////////////////////// main update ////////////////////////////////////////////////
    
    

} // brick

