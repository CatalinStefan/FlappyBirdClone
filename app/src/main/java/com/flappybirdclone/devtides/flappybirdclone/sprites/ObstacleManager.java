package com.flappybirdclone.devtides.flappybirdclone.sprites;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.flappybirdclone.devtides.flappybirdclone.GameManagerCallback;
import com.flappybirdclone.devtides.flappybirdclone.R;

import java.util.ArrayList;
import java.util.List;

public class ObstacleManager implements ObstacleCallback{

    private int interval;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private int screenWidth, screenHeight;
    private Resources resources;
    private int progress = 0;
    private int speed;
    private GameManagerCallback callback;

    public ObstacleManager(Resources resources, int screenHeight, int screenWidth, GameManagerCallback callback) {
        this.resources = resources;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.callback = callback;
        interval = (int) resources.getDimension(R.dimen.obstacle_interval);
        speed = (int) resources.getDimension(R.dimen.obstacle_speed);
        obstacles.add(new Obstacle(resources, screenHeight, screenWidth, this));
    }

    public void update() {
        progress += speed;
        if(progress > interval) {
            progress = 0;
            obstacles.add(new Obstacle(resources, screenHeight, screenWidth, this));
        }
        List<Obstacle> duplicate = new ArrayList<>();
        duplicate.addAll(obstacles);
        for (Obstacle obstacle: duplicate) {
            obstacle.update();
        }
    }

    public void draw(Canvas canvas) {
        for (Obstacle obstacle: obstacles) {
            obstacle.draw(canvas);
        }
    }

    @Override
    public void obstacleOffScreen(Obstacle obstacle) {
        obstacles.remove(obstacle);
        callback.removeObstacle(obstacle);
    }

    @Override
    public void updatePosition(Obstacle obstacle, ArrayList<Rect> positions) {
        callback.updatePosition(obstacle, positions);
    }
}
