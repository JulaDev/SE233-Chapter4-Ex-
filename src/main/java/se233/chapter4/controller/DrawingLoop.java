package se233.chapter4.controller;

import se233.chapter4.model.Character;
import se233.chapter4.view.Platform;

public class DrawingLoop implements Runnable {
    private Platform platform;
    private int frameRate;
    private float interval;
    private boolean running;
    public DrawingLoop(Platform platform){
        this.platform = platform;
        frameRate = 30;
        interval = 1000.0f / frameRate; // 1000 ms = 1 second
        running = true;
    }
    private void checkDrawCollisions(Character character){
        character.checkReachGameWall();
        character.checkReachHighest();
        character.checkReachFloor();
    }
    private void paint(Character character){
        character.repaint();
    }
    private void paint2(Character character){
        character.repaintChar2();
    }
    @Override
    public void run(){
        while (running){
            float time = System.currentTimeMillis();
            //Character1
            checkDrawCollisions(platform.getCharacter());
            paint(platform.getCharacter());
            //Character2
            checkDrawCollisions(platform.getCharacter2());
            paint(platform.getCharacter2());
            time = System.currentTimeMillis() - time;
            if (time < interval){
                try{
                    Thread.sleep((long)(interval - time));
                }catch (InterruptedException e){
                }
            }else {
                try{
                    Thread.sleep((long) (interval-(interval % time)));
                }catch (InterruptedException e){
                }
            }
        }
    }
}
