package com.flappybirdclone.devtides.flappybirdclone.sprites;

import android.graphics.Rect;

import java.util.ArrayList;

public interface ObstacleCallback {
    void obstacleOffScreen(Obstacle obstacle);
    void updatePosition(Obstacle obstacle, ArrayList<Rect> positions);
}
