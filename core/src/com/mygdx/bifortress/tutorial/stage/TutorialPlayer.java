package com.mygdx.bifortress.tutorial.stage;

import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.mechanism.Player;

public class TutorialPlayer {
    public Player player;
    public TutorialPlayer(){
        init();
    }
    public void init(){
        player = new Player(AnimationSprite.FROG_IDLE,AnimationSprite.FROG_RUN,AnimationSprite.FROG_HIT,100,100,64,64);
    }
    public void render(){
        player.update();
        player.render();
    }
    public void dispose(){
        player.dispose();
    }
}
