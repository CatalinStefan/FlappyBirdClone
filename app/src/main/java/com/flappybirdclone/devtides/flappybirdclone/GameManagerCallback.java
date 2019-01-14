package com.flappybirdclone.devtides.flappybirdclone;

import android.graphics.Rect;

import com.flappybirdclone.devtides.flappybirdclone.sprites.Obstacle;

import java.util.ArrayList;

public interface GameManagerCallback {
    void updatePosition(Rect birdPosition);
    void updatePosition(Obstacle obstacle, ArrayList<Rect> positions);
    void removeObstacle(Obstacle obstacle);
}
