package com.flappybirdclone.devtides.flappybirdclone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.flappybirdclone.devtides.flappybirdclone.sprites.Background;
import com.flappybirdclone.devtides.flappybirdclone.sprites.Bird;
import com.flappybirdclone.devtides.flappybirdclone.sprites.Obstacle;
import com.flappybirdclone.devtides.flappybirdclone.sprites.ObstacleManager;

public class GameManager extends SurfaceView implements SurfaceHolder.Callback {

    public MainThread thread;

    private Bird bird;
    private Background background;
    private DisplayMetrics dm;
    private ObstacleManager obstacleManager;

    public GameManager(Context context, AttributeSet attributeSet) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        initGame();
    }

    private void initGame() {
        bird = new Bird(getResources());
        background = new Background(getResources(), dm.heightPixels);
        obstacleManager = new ObstacleManager(getResources(), dm.heightPixels, dm.widthPixels);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        bird.update();
        obstacleManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(150, 255, 255);
        background.draw(canvas);
        bird.draw(canvas);
        obstacleManager.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        bird.onTouchEvent();
        return super.onTouchEvent(event);
    }
}
