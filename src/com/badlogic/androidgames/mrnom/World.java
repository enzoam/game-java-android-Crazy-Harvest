package com.badlogic.androidgames.mrnom;

import java.util.Random;

public class World {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;
    static final int SCORE_INCREMENT = 1;
    static final int SCORE_DECREMENT = -2;
    static final float TICK_INITIAL = 0.3f;
    static final float TICK_DECREMENT = 1f;

    public tractor tractor;
    public Obstaculo obstaculo;

    public boolean gameOver = false;;
    public int score = 1;
    public int changerockstate = 0;

    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    static float tick = TICK_INITIAL;

    public World() {
        tractor = new tractor();
        placeObstaculo();
    }

    private void placeObstaculo() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        int len = tractor.parts.size();
        for (int i = 0; i < len; i++) {
            tractorPart part = tractor.parts.get(i);
            fields[part.x][part.y] = true;
        }

        int obstaculoX = random.nextInt(WORLD_WIDTH);
        int obstaculoY = random.nextInt(WORLD_HEIGHT);
        while (true) {
            if (fields[obstaculoX][obstaculoY] == false)
                break;
            obstaculoX += 1;
            if (obstaculoX >= WORLD_WIDTH) {
                obstaculoX = 0;
                obstaculoY += 1;
                if (obstaculoY >= WORLD_HEIGHT) {
                    obstaculoY = 0;
                }
            }
        }
        if ((obstaculoX >= 6 && obstaculoY >= 1) && (obstaculoX <= 8 && obstaculoY <= 3)) {   
        }else{
        	obstaculo = new Obstaculo(obstaculoX, obstaculoY, random.nextInt(3));
        }
    }

    public void update(float deltaTime) {
    	
    	if (score <= 0)
    		gameOver = true;
    	
        if (gameOver)
            return;

        tickTime += deltaTime;

        while (tickTime > tick) {
            tickTime -= tick;
            tractor.advance();
            if (tractor.checkBitten()) {
            	score += SCORE_DECREMENT;
            }else
            {
            	score += SCORE_INCREMENT;
            }

            tractorPart head = tractor.parts.get(0);
            tractor.eat();
            
            changerockstate = changerockstate + 1;
            
            if (changerockstate > 10) {
            	placeObstaculo();
            	changerockstate = 0;
             }
           
            if (head.x == obstaculo.x && head.y == obstaculo.y) {
                gameOver = true;

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
            if ((head.x >= 6 && head.y >= 1) && (head.x <= 8 && head.y <= 3)) {
                gameOver = true;

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
        }
    }
}
