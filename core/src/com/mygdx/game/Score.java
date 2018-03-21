package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Score extends ScreenAdapter{

    private static final float WORLD_SIZE = 480;
    BubbleBuster game;
    int score;
    ShapeRenderer renderer;
    ExtendViewport viewport;
    SpriteBatch mSpriteBatch;
    BitmapFont mBitmapFont;

    public Score(BubbleBuster game,int score){
        this.game = game;
        this.score = score;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(WORLD_SIZE, WORLD_SIZE);
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = new BitmapFont();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        mBitmapFont.getData().setScale(Math.min(width, height) / 150f);


    }

    @Override
    public void dispose() {
        renderer.dispose();
        mBitmapFont.dispose();;
        mSpriteBatch.dispose();;
    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.circle(viewport.getWorldWidth()/2,viewport.getWorldHeight()/2,200f);
        renderer.end();

        mSpriteBatch.begin();
        mBitmapFont.setColor(Color.WHITE);
        mBitmapFont.draw(mSpriteBatch,"Score\n  " + score,viewport.getWorldWidth()/2 + 400f,viewport.getWorldHeight()/2 + 400f);
        mSpriteBatch.end();


    }
}
