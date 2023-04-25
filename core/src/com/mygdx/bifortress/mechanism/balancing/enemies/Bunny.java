package com.mygdx.bifortress.mechanism.balancing.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.node.Node;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class Bunny extends Enemy{
    ObjectAnimation idleAnimation,hitAnimation;
    boolean sHit;
    public Bunny(float x, float y,int power) {
        super(x, y);
        this.power = power;
        idleAnimation = new ObjectAnimation(AnimationSprite.BUNNY_IDLE,1f);
        hitAnimation = new ObjectAnimation(AnimationSprite.BUNNY_HIT,1f);
        width = idleAnimation.width;
        height = idleAnimation.height;
        sHit = true;
        life = 2;
        MOVE_SPEED = 7f;
    }
    public void updateSelf(){
        stateTime += Gdx.graphics.getDeltaTime()/4;
        if(isHit){

        }
        else{
            tracking(select());
        }
        if(isHit && sHit){
            stateTime = 0;
            hitAnimation.update(stateTime);
            sHit = false;
        }
        else if(isHit && hitAnimation.animate.isAnimationFinished(stateTime)){
            isHit = false;
            sHit = true;
            if(life <= 0){
                dead();
                dispose();
                Balancing.enemies.removeIndex(Balancing.enemies.indexOf(this,true));
                return;
            }
        }
        idleAnimation.update(stateTime);
        hitAnimation.update(stateTime);
        Sprite sprite;
        if(!isHit){
            sprite = new Sprite( idleAnimation.Frame );
        }
        else{
            sprite = new Sprite( hitAnimation.Frame );
        }
        sprite.flip((hsp>0),false);
        sprite.setBounds(this.xPos-width/2, this.yPos-height/2,this.width,this.height);
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
    public void dispose(){
        idleAnimation.dispose();
        hitAnimation.dispose();
    }
}
