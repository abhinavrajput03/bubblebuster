package com.mygdx.game;

import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import static com.mygdx.game.Constants.BLUE_SPAWNS_PER_SECOND;
import static com.mygdx.game.Fall.*;
import static javax.swing.UIManager.get;

public class Fall {


    Array<BlueBubble> mBlueBubbles;
    Array<GreenBubble> mGreenBubbles;
    Array<RedBubble> mRedBubbles;

    DelayedRemovalArray<BlueBubble> dbb;
    DelayedRemovalArray<RedBubble> drb;
    DelayedRemovalArray<GreenBubble> dgb;

    public Fall(){

        mBlueBubbles = new Array<BlueBubble>();
        mGreenBubbles = new Array<GreenBubble>();
        mRedBubbles = new Array<RedBubble>();

        dbb = new DelayedRemovalArray<BlueBubble>(false,100);
        dgb = new DelayedRemovalArray<GreenBubble>(false,100);
        drb = new DelayedRemovalArray<RedBubble>(false,100);

    }

    public void update(float delta, Viewport viewport){
        Random random = new Random();
        if (random.nextFloat() < delta * Constants.BLUE_SPAWNS_PER_SECOND){
            mBlueBubbles.add(new BlueBubble(viewport));
        }
        if (random.nextFloat() < delta * Constants.GREEN_SPAWNS_PER_SECOND){
            mGreenBubbles.add(new GreenBubble(viewport));
        }
        if (random.nextFloat() < delta * Constants.RED_SPAWNS_PER_SECOND){
            mRedBubbles.add(new RedBubble(viewport));
        }

        for (int i = 0; i < mBlueBubbles.size; i++){
            BlueBubble bb = mBlueBubbles.get(i);
            bb.update(delta);
            if (bb.isBelowScreen()){
                mBlueBubbles.removeIndex(i);
            }
        }

        for (int i = 0; i < mGreenBubbles.size; i++){
            GreenBubble gb = mGreenBubbles.get(i);
            gb.update(delta);
            if (gb.isBelowScreen()){
                mGreenBubbles.removeIndex(i);
            }
        }

        for (int i = 0; i < mRedBubbles.size; i++){
            RedBubble rb = mRedBubbles.get(i);
            rb.update(delta);
            if (rb.isBelowScreen()){
                mRedBubbles.removeIndex(i);
            }
        }

        dbb.begin();

        for (int i = 0; i < dbb.size; i++) {
            if (dbb.get(i).position.y < - 50f) {
                dbb.removeIndex(i);
            }
        }
        dbb.end();

        dgb.begin();

        for (int i = 0; i < dgb.size; i++) {
            if (dgb.get(i).position.y < - 300f) {
                dgb.removeIndex(i);
            }
        }
        dgb.end();

        drb.begin();

        for (int i = 0; i < dbb.size; i++) {
            if (drb.get(i).position.y < - 20f) {
                drb.removeIndex(i);
            }
        }
        drb.end();


    }

    public void render(ShapeRenderer renderer){
        for (BlueBubble bb : mBlueBubbles){
            bb.render(renderer);
        }

        for (GreenBubble gb : mGreenBubbles){
            gb.render(renderer);
        }

        for (RedBubble rb : mRedBubbles){
            rb.render(renderer);
        }
    }
}
