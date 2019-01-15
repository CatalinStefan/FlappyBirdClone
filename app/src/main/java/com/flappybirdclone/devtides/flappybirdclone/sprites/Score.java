package com.flappybirdclone.devtides.flappybirdclone.sprites;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.flappybirdclone.devtides.flappybirdclone.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Score implements Sprite {

    private static final String SCORE_PREF = "Score pref";
    private Bitmap zero;
    private Bitmap one;
    private Bitmap two;
    private Bitmap three;
    private Bitmap four;
    private Bitmap five;
    private Bitmap six;
    private Bitmap seven;
    private Bitmap eight;
    private Bitmap nine;
    private Bitmap bmpScore;
    private Bitmap bmpBest;
    private HashMap<Integer, Bitmap> map = new HashMap<>();
    private int screenHeight, screenWidth;
    private int score;
    private int topScore;
    private boolean collision = false;


    public Score(Resources resources, int screenHeight, int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        bmpScore = BitmapFactory.decodeResource(resources, R.drawable.score);
        bmpBest = BitmapFactory.decodeResource(resources, R.drawable.best);
        zero = BitmapFactory.decodeResource(resources, R.drawable.zero);
        one = BitmapFactory.decodeResource(resources, R.drawable.one);
        two = BitmapFactory.decodeResource(resources, R.drawable.two);
        three = BitmapFactory.decodeResource(resources, R.drawable.three);
        four = BitmapFactory.decodeResource(resources, R.drawable.four);
        five = BitmapFactory.decodeResource(resources, R.drawable.five);
        six = BitmapFactory.decodeResource(resources, R.drawable.six);
        seven = BitmapFactory.decodeResource(resources, R.drawable.seven);
        eight = BitmapFactory.decodeResource(resources, R.drawable.eight);
        nine = BitmapFactory.decodeResource(resources, R.drawable.nine);
        map.put(0, zero);
        map.put(1, one);
        map.put(2, two);
        map.put(3, three);
        map.put(4, four);
        map.put(5, five);
        map.put(6, six);
        map.put(7, seven);
        map.put(8, eight);
        map.put(9, nine);
    }

    @Override
    public void draw(Canvas canvas) {
        if(!collision) {
            ArrayList<Bitmap> digits = convertToBitmaps(score);
            for (int i = 0; i < digits.size(); i++) {
                int x = screenWidth / 2 - digits.size() * zero.getWidth() / 2 + zero.getWidth() * i;
                canvas.drawBitmap(digits.get(i), x, screenHeight/4, null);
            }
        } else {
            ArrayList<Bitmap> currentDigits = convertToBitmaps(score);
            ArrayList<Bitmap> topDigits = convertToBitmaps(topScore);

            canvas.drawBitmap(bmpScore, screenWidth / 4 - bmpScore.getWidth() / 2, 3 * screenHeight / 4 - zero.getHeight() - bmpScore.getHeight(), null);
            for (int i = 0; i < currentDigits.size(); i++) {
                int x = screenWidth / 4 - currentDigits.size() * zero.getWidth() + zero.getWidth() * i;
                canvas.drawBitmap(currentDigits.get(i), x, 3 * screenHeight / 4, null);
            }

            canvas.drawBitmap(bmpBest, 3 * screenWidth / 4 - bmpBest.getWidth() / 2, 3 * screenHeight / 4 - zero.getHeight() - bmpBest.getHeight(), null);
            for (int i = 0; i < topDigits.size(); i++) {
                int x = 3 * screenWidth / 4 - topDigits.size() * zero.getWidth() + zero.getWidth() * i;
                canvas.drawBitmap(topDigits.get(i), x, 3 * screenHeight / 4, null);
            }
        }
    }

    private ArrayList<Bitmap> convertToBitmaps(int amount) {
        ArrayList<Bitmap> digits = new ArrayList();
        if(amount == 0) {
            digits.add(zero);
        }
        while (amount > 0) {
            int lastDigit = amount % 10;
            amount /= 10;
            digits.add(map.get(lastDigit));
        }
        ArrayList<Bitmap> finalDigits = new ArrayList<>();
        for (int i = digits.size()-1; i >= 0; i--) {
            finalDigits.add(digits.get(i));
        }
        return finalDigits;
    }

    @Override
    public void update() {

    }

    public void updateScore(int score) {
        this.score = score;
    }

    public void collision(SharedPreferences prefs) {
        collision = true;
        topScore = prefs.getInt(SCORE_PREF, 0);
        if(topScore < score) {
            prefs.edit().putInt(SCORE_PREF, score).apply();
            topScore = score;
        }
    }
}
