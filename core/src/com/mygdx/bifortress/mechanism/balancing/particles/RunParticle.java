package com.mygdx.bifortress.mechanism.balancing.particles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;
import com.mygdx.bifortress.mechanism.balancing.Balancing;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class RunParticle {
    public float x,y,rad;
    ObjectAnimation particle;
    public RunParticle(float x, float y, float rad){
        this.x = x;
        this.y = y;
        this.rad = rad;
        particle = new ObjectAnimation(AnimationSprite.DUST_PARTICLE,1);
    }
    public void render(){
        spriteBatch.begin();
        Sprite sprite = new Sprite( particle.Frame );
        sprite.setBounds(this.x-this.rad, this.y-this.rad,this.rad*2,this.rad*2);
        sprite.draw(spriteBatch);
        spriteBatch.end();
        if(this.rad - .5f <= 1f){
            Balancing.particles.removeIndex(Balancing.particles.indexOf(this,true));
            particle.dispose();
        }
        else{
            this.rad -= .05f*this.rad;
            this.x += (Balancing.rand.nextInt(2)-1)*Balancing.rand.nextFloat();
            this.y += (Balancing.rand.nextInt(2)-1)*Balancing.rand.nextFloat();
        }
    }
    public void dispose(){
        particle.dispose();
    }
}
