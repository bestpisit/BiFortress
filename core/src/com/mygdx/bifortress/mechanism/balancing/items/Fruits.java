package com.mygdx.bifortress.mechanism.balancing.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.mechanism.balancing.Balancing;

import java.util.Random;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class Fruits {
    ObjectAnimation animation,collectedAnimation;
    public int radius,degree;
    float x,y,stateTime,speed,point;
    boolean collected;
    public static final AnimationSprite[] Fruits_sprite = {
            AnimationSprite.APPLE,
            AnimationSprite.BANANAS,
            AnimationSprite.CHERRIES,
            AnimationSprite.KIWI,
            AnimationSprite.MELON,
            AnimationSprite.ORANGE,
            AnimationSprite.PINEAPPLE,
            AnimationSprite.Strawberry
    };
    public Fruits(float point,int x,int y){
        collectedAnimation = new ObjectAnimation(AnimationSprite.FRUITCOLLECTED,0.4f);
        animation = new ObjectAnimation(Fruits_sprite[new Random().nextInt(Fruits_sprite.length)],0.5f);
        this.point = point;
        stateTime = 0;
        this.radius = 32;
        collected = false;
        this.x = x;
        this.y = y;
        degree = (int) (Math.random()*360);
        speed = (float) (Math.random()*2+4);
    }
    public void render(ShapeRenderer shapeRenderer){
        if(speed > 0.01){
            speed -= speed*0.1;
            this.x += speed*Math.cos(Math.toRadians(degree));
            this.y += speed*Math.sin(Math.toRadians(degree));
        }
        else{
            speed = 0;
        }
        animation.update(stateTime);
        collectedAnimation.update(stateTime);
        stateTime += Gdx.graphics.getDeltaTime();
        if(!collected && new Circle(x,y,radius*1.2f).contains(Balancing.player.xPos,Balancing.player.yPos)){
            collected = true;
            stateTime = 0;
            collectedAnimation.update(stateTime);
            Balancing.score += Math.round(point);
        }
        if(collected){
            if(collectedAnimation.animate.isAnimationFinished(stateTime* collectedAnimation.speed)){
                Balancing.fruits.removeIndex(Balancing.fruits.indexOf(this,true));
            }
            spriteBatch.begin();
            Sprite sprite = new Sprite(collectedAnimation.Frame);
            sprite.setBounds(x-radius,y-radius,radius*2,radius*2);
            sprite.setOrigin(radius,radius);
            sprite.draw(spriteBatch);
            spriteBatch.end();
        }
        else{
            spriteBatch.begin();
            Sprite sprite = new Sprite(animation.Frame);
            sprite.setBounds(x-radius,y-radius,radius*2,radius*2);
            sprite.draw(spriteBatch);
            spriteBatch.end();
        }
    }
    public void dispose(){
        animation.dispose();
        collectedAnimation.dispose();
    }
}
