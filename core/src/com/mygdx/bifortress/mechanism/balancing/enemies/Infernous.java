package com.mygdx.bifortress.mechanism.balancing.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.node.Node;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class Infernous extends Enemy{
    ObjectAnimation idleAnimation,hitAnimation,deadAnimation;
    boolean sHit;
    public Infernous(float x, float y) {
        super(x, y);
        idleAnimation = new ObjectAnimation(AnimationSprite.INFERNOUS_IDLE,1f);
        hitAnimation = new ObjectAnimation(AnimationSprite.INFERNOUS_HIT,1f);
        deadAnimation = new ObjectAnimation(AnimationSprite.INFERNOUS_DIE,1f);
        width = idleAnimation.width*2;
        height = idleAnimation.height*2;
        sHit = true;
        life = 10;
        power = 2;
        MOVE_SPEED = 1f;
    }
    public void updateSelf(){
        stateTime += Gdx.graphics.getDeltaTime()/4;
        if(!isHit){
            tracking(select());
        }
        Sprite sprite;
        if(!isHit){
            sprite = new Sprite( idleAnimation.Frame );
        }
        else if(life <= 0){
            sprite = new Sprite( deadAnimation.Frame );
        }
        else{
            sprite = new Sprite( hitAnimation.Frame );
        }
        sprite.flip((hsp<0),false);
        sprite.setBounds(this.xPos-width/2, this.yPos-height/2,this.width,this.height);
        spriteBatch.begin();
        sprite.draw(spriteBatch);
        spriteBatch.end();
        boolean fixAnimate = true;
        if(isHit && sHit){
            stateTime = 0;
            hitAnimation.update(stateTime);
            deadAnimation.update(stateTime);
            sHit = false;
        }
        else if(isHit && hitAnimation.animate.isAnimationFinished(stateTime)){
            isHit = false;
            sHit = true;
            fixAnimate = false;
            if(life <= 0){
                dead();
                dispose();
                Balancing.enemies.removeIndex(Balancing.enemies.indexOf(this,true));
                return;
            }
        }
        idleAnimation.update(stateTime);
        if(fixAnimate){
            hitAnimation.update(stateTime);
            deadAnimation.update(stateTime);
        }
    }
    public void dispose(){
        idleAnimation.dispose();
        hitAnimation.dispose();
        deadAnimation.dispose();
    }
}
