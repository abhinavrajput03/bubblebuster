package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;


public class BlueBubble {

    Vector2 position;
    Vector2 velocity;
    float radius;

    public BlueBubble(Viewport viewport){
        init(viewport);

    }

    public void init(Viewport viewport){
        position = new Vector2();

        velocity = new Vector2(0, 0);

        radius = viewport.getWorldWidth() * Constants.BLUE_RADIUS_RATIO;
        position.y = viewport.getWorldHeight() + radius;
        Random random = new Random();
        position.x = random.nextFloat() * (viewport.getWorldWidth() - 2 * radius) + radius;
    }

    public void update(float delta){

        velocity.y += delta * Constants.BLUE_GRAVITY;

        position.x += delta * velocity.x;
        position.y += delta * velocity.y;
    }

    public boolean isBelowScreen(){
        return position.y < -radius;
    }

    public void render(ShapeRenderer renderer){

        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        renderer.circle(position.x, position.y, radius);
    }
}
