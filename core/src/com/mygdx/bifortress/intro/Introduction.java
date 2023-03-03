package com.mygdx.bifortress.intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.bifortress.Background;
import com.mygdx.bifortress.BiFortress;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;

import java.awt.*;

import static com.mygdx.bifortress.BiFortress.*;

public class Introduction {
    Texture BiFortressLogo;
    float progress;
    ObjectAnimation frog_run;
    Dummy dummy;
    float fX,fY,fHsp,fVsp,alpha;
    ShapeRenderer shapeRenderer;
    Background intro_background;
    public Introduction(){
        BiFortressLogo = new Texture(Gdx.files.internal("ui/BiFortress/BiFortress Logo.png"));
        progress = 100;
        frog_run = new ObjectAnimation(AnimationSprite.FROG_RUN,0.25f);
        fX = 0;
        fY = 0;
        dummy = new Dummy(AnimationSprite.BUNNY_IDLE,GAME_WIDTH,GAME_HEIGHT,GAME_WIDTH/2,GAME_HEIGHT/2,1.0f);
        alpha = 0;
        shapeRenderer = new ShapeRenderer();
        intro_background = new Background("Background/Gray.png",1);
    }
    public void update(){
        if(alpha <= 100){
            if(progress > 1){
                progress-=progress*0.05f;
            }
            else{
                progress = 1;
            }
            frog_run.update(stateTime);
            //frog
            if(Math.abs(fX - GAME_WIDTH/2)> frog_run.width/2 || Math.abs(fY - GAME_HEIGHT/2)> frog_run.height/2){
                double rad = Math.atan2(GAME_HEIGHT/2-fY,GAME_WIDTH/2-fX);
                fX += 1.8f*Math.cos(rad);
                fY += 1.8f*Math.sin(rad);
            }
            else{
                alpha += 0.5f+alpha*0.1f;
            }
            dummy.update();
        }
        else{
            if(alpha < 200){
                alpha += 0.5f+(alpha-100)*0.1f;
            }
            else{
                alpha = 200;
            }
        }
        intro_background.update();
    }
    public void render(){
        if(alpha <= 100){
            intro_background.render();
            dummy.render();
            spriteBatch.begin();
            Sprite frog = new Sprite(frog_run.Frame);
            frog.setBounds(fX,fY, frog_run.width, frog_run.height);
            frog.draw(spriteBatch);
            Sprite sprite = new Sprite(BiFortressLogo);
            sprite.setOrigin(632*progress/2,395*progress/2);
            sprite.rotate((progress-1)*2);
            sprite.setBounds(GAME_WIDTH/2-632*progress/2,GAME_HEIGHT/2-395*progress/2,632*progress,395*progress);
            sprite.draw(spriteBatch);
            spriteBatch.end();
        }
        if(alpha > 0){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
            Color color;
            if(alpha <= 100){
                color = new Color(0, 0, 0, alpha/100f);
            }
            else{
                color = new Color(0, 0, 0, (200-alpha)/100f);
            }
            shapeRenderer.setColor(color);
            shapeRenderer.rect(0,0,GAME_WIDTH,GAME_HEIGHT);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
    public void dispose(){
        BiFortressLogo.dispose();
        frog_run.dispose();
        shapeRenderer.dispose();
    }
}
