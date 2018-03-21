package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.math.Interpolation.circle;
import static sun.audio.AudioPlayer.player;

public class BubbleGame extends ScreenAdapter{
    private static final float WORLD_SIZE = 480;

    BubbleBuster game;
    ShapeRenderer renderer;
    ScreenViewport hudViewport;
    SpriteBatch mSpriteBatch;
    BitmapFont mBitmapFont;
    ExtendViewport viewport;
    Fall mFall;
    Spike mSpike;
    Sound mp3;

    public int score;
    int rc,bc,gc;
    public float elapsedTime = 0f;

    public BubbleGame(BubbleBuster game){
        this.game = game;
    }

    @Override
    public void show() {

        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(WORLD_SIZE, WORLD_SIZE);
        hudViewport = new ScreenViewport();
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = new BitmapFont();
        mp3 = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
        mBitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        mFall = new Fall();
        mSpike = new Spike(viewport);
        score = 0;
        rc=bc=gc=0;
           }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
        hudViewport.update(width,height,true);
        mBitmapFont.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
        mSpike.init();
    }

    @Override
    public void dispose() {

        renderer.dispose();
        mBitmapFont.dispose();
        mSpriteBatch.dispose();
        mp3.dispose();
    }

    @Override
    public void render(float delta) {

        elapsedTime += delta;
        viewport.apply();

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            mFall.update(delta, viewport);
            mSpike.update(delta);

            if (mSpike.popBlueBubble(mFall)) {
                score += 5;
                bc++;
                mp3.play();
            }
            if (mSpike.popGreenBubble(mFall)) {
                score += 10;
                gc++;
                mp3.play();
            }
            if (mSpike.popRedBubble(mFall)) {
                score += 15;
                rc++;
                mp3.play();
            }

            renderer.setProjectionMatrix(viewport.getCamera().combined);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            mFall.render(renderer);
            mSpike.render(renderer);
            renderer.end();

            hudViewport.apply();
            mSpriteBatch.setProjectionMatrix(hudViewport.getCamera().combined);
            mSpriteBatch.begin();

            mBitmapFont.draw(mSpriteBatch, "Points" + "\nRed: " + 15 + "\nGreen: " + 10 + "\nBlue: " + 5,
                    Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN);

            mBitmapFont.draw(mSpriteBatch, "Blue: " + bc + "\nGreen: " + gc + "\nRed: " + rc + "\nScore: " + score, hudViewport.getWorldWidth() - Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN,
                    0, Align.right, false);

            mSpriteBatch.end();

        if(elapsedTime > 10800)
        {
            game.setScreen(new Score(game,score));
            dispose();
        }
    }
}
