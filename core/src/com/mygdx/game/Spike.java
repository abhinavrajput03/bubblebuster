package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Circle;

public class Spike {

    Vector2 position;
    ExtendViewport mViewport;

        public Spike(ExtendViewport mViewport)
    {
        this.mViewport = mViewport;
        init();
    }

    public void init()
    {
        position = new Vector2(mViewport.getWorldWidth()/2,Constants.SPIKE_HEIGHT);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= delta * Constants.SPIKE_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += delta * Constants.SPIKE_SPEED;
        }

        float accelerometerInput = -Gdx.input.getAccelerometerY() / (15f* 2f);
        position.x += -delta * accelerometerInput * Constants.SPIKE_SPEED;

        ensureInBounds();
    }

    public void ensureInBounds() {
        if (position.x - Constants.SPIKE_WIDTH < 0) {
            position.x = Constants.SPIKE_WIDTH;
        }
        if (position.x + Constants.SPIKE_WIDTH > mViewport.getWorldWidth()) {
            position.x = mViewport.getWorldWidth() - Constants.SPIKE_WIDTH;
        }
    }

    public boolean popBlueBubble(Fall fall){
        boolean pop = false;

        for(BlueBubble bb : fall.mBlueBubbles){
            if(bb.position.dst(position) < bb.radius )
            pop =true;
        }
        return pop;
    }

    public boolean popGreenBubble(Fall fall){
        boolean pop = false;

        for(GreenBubble gb : fall.mGreenBubbles){
            if(gb.position.dst(position)< gb.radius)
                pop =true;
        }
        return pop;
    }

    public boolean popRedBubble(Fall fall){
        boolean pop = false;

        for(RedBubble rb : fall.mRedBubbles){
            if(rb.position.dst(position)< rb.radius)
                pop =true;
        }
        return pop;
    }

    public void render(ShapeRenderer renderer){
        renderer.setColor(Color.GRAY);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.triangle(position.x,position.y,position.x - Constants.SPIKE_WIDTH / 2, position.y - Constants.SPIKE_HEIGHT,
                position.x + Constants.SPIKE_WIDTH / 2, position.y - Constants.SPIKE_HEIGHT);
    }
}
