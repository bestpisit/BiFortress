package com.mygdx.bifortress.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.bifortress.BiFortress.manager;
public class ObjectAnimation {
    public Animation<TextureRegion> animate;
    Texture sheet;
    public float speed;
    public TextureRegion Frame;
    public float width,height;
    String value;
    public ObjectAnimation(AnimationSprite idle, float speed){
        this.value = idle.value;
        sheet = manager.get(idle.value, Texture.class); //nice
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / idle.col,
                sheet.getHeight() / idle.row);
        this.width = sheet.getWidth() / idle.col * 2;
        this.height = sheet.getHeight() / idle.row * 2;
        TextureRegion[] walkFrames = new TextureRegion[idle.col * idle.row];
        int index = 0;
        for (int i = 0; i < idle.row; i++) {
            for (int j = 0; j < idle.col; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        animate = new Animation<TextureRegion>(0.025f, walkFrames);
        this.speed = speed;
        Frame = animate.getKeyFrame(0f, true);
    }
    public void update(float stateTime){
        Frame = animate.getKeyFrame(stateTime*speed, true);
    }
    public void dispose(){
        //Frame.getTexture().dispose();
    }
}
