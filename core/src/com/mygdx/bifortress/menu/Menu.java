package com.mygdx.bifortress.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import static com.mygdx.bifortress.BiFortress.*;

public class Menu {
    BitmapFont font;
    float progress;
    public Menu(){
        font = new BitmapFont(Gdx.files.internal("BerlinSans/BerlinSans.fnt"));
        progress = 0;
    }
    public void update(){
        if(progress < 100){
            progress += 0.1+progress*0.075f;
        }
        else{
            progress = 100;
        }
        if(!hasIntro)progress=0;
    }
    public void render(){
        spriteBatch.begin();
        font.draw(spriteBatch,"Play",20-200*(100-progress)/100,GAME_HEIGHT/2+80);
        font.draw(spriteBatch,"Tutorial",20-200*(100-progress)/100,GAME_HEIGHT/2-50+80);
        font.draw(spriteBatch,"Option",20-200*(100-progress)/100,GAME_HEIGHT/2-100+80);
        spriteBatch.end();
    }
    public void dispose(){
        font.dispose();
    }
}
