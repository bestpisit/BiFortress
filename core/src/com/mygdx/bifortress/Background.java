package com.mygdx.bifortress;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

import static com.mygdx.bifortress.BiFortress.*;

public class Background {
    public TiledDrawable tiledDrawable;
    public Texture backgroundTexture;
    private float xPos,yPos;
    float alpha,speed;

    public Background(String path, float speed){
        backgroundTexture = new Texture((path));
        tiledDrawable = new TiledDrawable(new TextureRegion(backgroundTexture));
        xPos = 0;
        yPos = 0;
        alpha = 1;
        this.speed = speed;
    }
    public void render(){
        spriteBatch.begin();
        tiledDrawable.draw(spriteBatch,xPos, -1*yPos-64-32, GAME_WIDTH+64, GAME_HEIGHT+128);
        spriteBatch.end();
    }
    public void update(){
        if (yPos < 0){
            //xPos += 0.25f*speed;
            yPos += 0.25f*speed;
        }
        else{
            xPos = -64;
            yPos = -64;
        }
    }
    public void dispose(){
        backgroundTexture.dispose();
    }
}
