package se233.chapter4.model;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se233.chapter4.Launcher;
import se233.chapter4.view.Platform;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;

public class Character extends Pane {
    Logger logger = LoggerFactory.getLogger((MethodHandles.lookup().lookupClass()));
    public static final int CHARACTER_WIDTH = 32;
    public static final int CHARACTER_HEIGHT = 64;
    private Image characterImg;
    private AnimatedSprite imageView;
    private int x;
    private int y;
    int xVelocity = 0;
    int yVelocity = 0;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity = 7;
    int yMaxVelocity = 17;
    int highestJump = 100;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    LocalDate Date = LocalDate.now();

    public void moveLeft(){
        isMoveLeft = true;
        isMoveRight = false;
    }
    public void moveRight(){
        isMoveLeft = false;
        isMoveRight = true;
    }
    public void stop(){
        isMoveLeft = false;
        isMoveRight = false;
    }
    public void moveX(){
        setTranslateX(x);
        if(isMoveLeft){
            xVelocity = xVelocity >= xMaxVelocity? xMaxVelocity : xVelocity + xAcceleration;
            x = x - xVelocity; }
        if(isMoveRight){
            xVelocity = xVelocity >= xMaxVelocity? xMaxVelocity : xVelocity + xAcceleration;
            x = x + xVelocity; }
    }
    public void moveY(){
        setTranslateY(y);
        if(isFalling){
            yVelocity = yVelocity >= yMaxVelocity? yMaxVelocity : yVelocity + yAcceleration;
            y = y + yVelocity; }
        else if (isJumping){
            yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAcceleration;
            y = y - yVelocity; }
    }
    // Exercise 4.2 added Character has a different movement speed for both x and y
    public void moveDiffX(){
        this.xAcceleration = 3;
        this.xMaxVelocity = 10;
        setTranslateX(x);
        if (isMoveLeft){
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x = x - xVelocity;
        } else if (isMoveRight) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x = x + xVelocity;
        }
    }
    public void moveDiffY(){
        this.yAcceleration = 3;
        this.yMaxVelocity = 31;
        setTranslateY(y);
        if(isFalling){
            yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yVelocity + yAcceleration;
            y = y + yVelocity;
        } else if (isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAcceleration;
            y = y - yVelocity;
        }
    }
    public Character(int x,int y,int offsetX,int offsetY,KeyCode leftKey,KeyCode rightKey,KeyCode upKey,String image){
    this.x = x;
    this.y = y;
    this.setTranslateX(x);
    this.setTranslateY(y);

    if (image.equals("MarioSheet"))
    {
    this.characterImg = new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png"));
    this.imageView = new AnimatedSprite(characterImg,4,4,1,offsetX,offsetY,16,32);
    this.imageView.setFitHeight(CHARACTER_HEIGHT);
    } else if (image.equals("Babyblue")) {
        // Exercise 4.3 added character
        this.characterImg = new Image(Launcher.class.getResourceAsStream("assets/Babyblue.png"));
        this.imageView = new AnimatedSprite(characterImg,5,5,2,offsetX,offsetY,70,80);
        this.imageView.setFitHeight(84);
    }

    this.imageView.setFitWidth(CHARACTER_WIDTH);
    this.leftKey = leftKey;
    this.rightKey = rightKey;
    this.upKey = upKey;
    this.getChildren().addAll(this.imageView);
    }

    public void jump(){
        if (canJump){
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }
    public void checkReachHighest(){
        if(isJumping && yVelocity <= 0){
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }
    public void checkReachFloor(){
        if(isFalling && y >= Platform.GROUND - CHARACTER_HEIGHT){
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }
    public void checkReachGameWall(){
        if(x <= 0 ){
            x = 0;
        } else if (x+getWidth() >= Platform.WIDTH){
            x = Platform.WIDTH-(int)getWidth();
        }
    }
    // Exercise 4.5 Modify the "PatternLayout"
    public void trace(){
        logger.info("x:{} y:{} vx:{} vy:{} Day:{}",x,y,xVelocity,yVelocity, Date.getDayOfWeek());
        // logger.addAppender(new FileAppender())
    }
    public void repaint(){
        moveX();
        moveY();
    }
    public void repaintChar2(){
        moveDiffX();
        moveDiffY();
    }
    public KeyCode getLeftKey(){
        return leftKey;
    }
    public KeyCode getRightKey(){
        return rightKey;
    }
    public KeyCode getUpKey(){
        return upKey;
    }
    public AnimatedSprite getImageView(){
        return imageView;
    }

}
