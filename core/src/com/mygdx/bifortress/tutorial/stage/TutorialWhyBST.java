package com.mygdx.bifortress.tutorial.stage;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.bifortress.menu.Menu;

import static com.mygdx.bifortress.BiFortress.screenViewport;
import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class TutorialWhyBST {
    public TutorialWhyBST(){

    }
    public void update(){

    }
    public void render(){
        spriteBatch.begin();
        Sprite sprite = new Sprite(Menu.BiFortressLogo);
        sprite.setTexture(Menu.BiFortressText);
        sprite.setBounds(screenViewport.getScreenWidth()/2-287*1.5f/2,screenViewport.getScreenHeight()/2+66*1.5f/2+150,287*1.5f,66*1.5f);
        sprite.draw(spriteBatch);
        sprite.setTexture(Menu.BiFortressLogo);
        sprite.setOrigin(632,395);
        sprite.setBounds(screenViewport.getScreenWidth()/2-632/2-15,screenViewport.getScreenHeight()/2-395/2,632,395);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
    public void dispose(){

    }
}
