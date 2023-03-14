package com.mygdx.bifortress.intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.bifortress.Background;


import static com.mygdx.bifortress.BiFortress.*;

public class Introduction {
    Texture BiFortressLogo;
    public float progress,alpha,time;
    int counter;
    ShapeRenderer shapeRenderer;
    Background intro_background;
    IntroBinaryTree introBinaryTree;
    Sound sound;
    public Introduction(){
        BiFortressLogo = new Texture(("ui/BiFortress/BiFortress Logo.png"));
        progress = 100;
        alpha = -100;
        shapeRenderer = new ShapeRenderer();
        intro_background = new Background("Background/Gray.png",1);
        introBinaryTree = new IntroBinaryTree();
        time = 0;
        counter = 1;
        sound = Gdx.audio.newSound(Gdx.files.internal("Intro/IntroMusic.mp3"));
        sound.play(1.0f); // play new sound and keep handle for further manipulation
    }
    public void update(){
        if(counter == 6){
            if(alpha <= 100){
                if(progress > 1){
                    progress-=progress*0.05f;
                }
                else{
                    progress = 1;
                    if(alpha < 0){
                        alpha += 0.5f+Math.abs(Math.abs(alpha)-100)*0.1f;
                    }
                    else{
                        alpha += 0.5f+alpha*0.1f;
                    }
                }
            }
            else{
                if(alpha < 300){
                    if(alpha < 200){
                        alpha += 0.5f+(alpha-100)*0.1f;
                    }
                    else{
                        alpha += 0.5f+(alpha-200)*0.1f;
                    }
                }
                else{
                    alpha = 300;
                    sound.stop();
                }
            }
        }
        intro_background.update();
        introBinaryTree.update();
        time += Gdx.graphics.getDeltaTime();
        if(counter == 1 && time > 0.25f){
            introBinaryTree.rotate(introBinaryTree.nodes.get(0),introBinaryTree.nodes.get(1));
            counter = 2;
        }
        else if(counter == 2 && time > 0.9f){
            introBinaryTree.rotate(introBinaryTree.nodes.get(2),introBinaryTree.nodes.get(3));
            counter = 3;
        }
        else if(counter == 3 && time > 1.6f){
            introBinaryTree.rotate(introBinaryTree.nodes.get(4),introBinaryTree.nodes.get(5));
            counter = 4;
        }
        else if(counter == 4 && time > 2.1f){
            introBinaryTree.rotate(introBinaryTree.nodes.get(1),introBinaryTree.nodes.get(3));
            counter = 5;
        }
        else if(counter == 5 && time > 2.1f){
            counter = 6;
        }
    }
    public void render(){
        if(alpha <= 100){
            intro_background.render();
            if(counter==6){
                spriteBatch.begin();
                Sprite sprite = new Sprite(BiFortressLogo);
                sprite.setOrigin(632*progress/2,395*progress/2);
                sprite.rotate((progress-1)*2);
                sprite.setBounds(GAME_WIDTH/2-632*progress/2-15,GAME_HEIGHT/2-395*progress/2,632*progress,395*progress);
                sprite.draw(spriteBatch);
                spriteBatch.end();
            }
            introBinaryTree.render();
        }
        if(alpha > 0){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
            Color color;
            if(alpha <= 100){
                hasIntro = true;
                color = new Color(0, 0, 0, alpha/100f);
            }
            else{
                if(alpha <= 200){
                    color = new Color(0, 0, 0, 1);
                }
                else{
                    color = new Color(0, 0, 0, (300-alpha)/100f);
                }
            }
            shapeRenderer.setColor(color);
            shapeRenderer.rect(0,0,GAME_WIDTH,GAME_HEIGHT);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
    public void dispose(){
        BiFortressLogo.dispose();
        shapeRenderer.dispose();
        introBinaryTree.dispose();
    }
}
