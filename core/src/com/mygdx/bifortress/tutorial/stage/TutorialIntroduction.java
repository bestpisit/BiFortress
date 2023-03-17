package com.mygdx.bifortress.tutorial.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.menu.Menu;
import com.mygdx.bifortress.tutorial.Tutorial;
import com.mygdx.bifortress.tutorial.TutorialController;

import static com.mygdx.bifortress.BiFortress.*;

public class TutorialIntroduction {
    ObjectAnimation infernous,frog;
    float stateTime = 0;
    public TutorialIntroduction(){
        infernous = new ObjectAnimation(AnimationSprite.INFERNOUS_IDLE,0.25f);
        frog = new ObjectAnimation(AnimationSprite.FROG_RUN,0.25f);
    }
    public void update(){
        int index = Tutorial.tutorialController.currentIndex;
        stateTime += Gdx.graphics.getDeltaTime();
        if(index >= 5){
            infernous.update(stateTime);
        }
        if(index >= 6){
            frog.update(stateTime);
        }
    }
    public void render(){
        spriteBatch.begin();
        Sprite sprite = new Sprite(Menu.BiFortressLogo);
        int index = Tutorial.tutorialController.currentIndex;
        if(index >= 2){
            sprite.setTexture(Menu.BiFortressText);
            sprite.setBounds(screenViewport.getScreenWidth()/2-287*1.5f/2,screenViewport.getScreenHeight()/2+66*1.5f/2+150,287*1.5f,66*1.5f);
            sprite.draw(spriteBatch);
        }
        if(index >= 5){
            Sprite sprite1 = new Sprite(infernous.Frame);
            sprite1.flip(true,false);
            sprite1.setBounds(screenViewport.getScreenWidth()/2+300-100,screenViewport.getScreenHeight()/2+100,200,200);
            sprite1.draw(spriteBatch);
        }
        if(index >= 6){
            Sprite sprite1 = new Sprite(frog.Frame);
            sprite1.setBounds(screenViewport.getScreenWidth()/2-300-50,screenViewport.getScreenHeight()/2+50-200,100,100);
            sprite1.draw(spriteBatch);
        }
        sprite.setTexture(Menu.BiFortressLogo);
        sprite.setOrigin(632,395);
        sprite.setBounds(screenViewport.getScreenWidth()/2-632/2-15,screenViewport.getScreenHeight()/2-395/2,632,395);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
    public void dispose(){
        infernous.dispose();
    }
}
