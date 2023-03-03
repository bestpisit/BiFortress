package com.mygdx.bifortress.intro;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;

import static com.mygdx.bifortress.BiFortress.*;

public class Dummy {
    ObjectAnimation objectAnimation;
    float dX,dY,x,y,w,h,spd;
    public Dummy(AnimationSprite animationSprite,int x,int y,int dX,int dY,float spd){
        objectAnimation = new ObjectAnimation(animationSprite,1f);
        this.x = x;
        this.y = y;
        this.dX = dX;
        this.dY = dY;
        this.w = objectAnimation.width;
        this.h = objectAnimation.height;
        this.spd = spd;
    }
    public void update(){
        if(Math.abs(dX - x) > w/2 || Math.abs(dY - y)> h/2){
            double rad = Math.atan2(dY-y,dX-x);
            x += 1.5f*Math.cos(rad);
            y += 1.5f*Math.sin(rad);
        }
        objectAnimation.update(stateTime);
    }
    public void render(){
        spriteBatch.begin();
        Sprite sprite = new Sprite(objectAnimation.Frame);
        sprite.setBounds(x,y,w,h);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
    public void dispose(){
        objectAnimation.dispose();
    }
}
