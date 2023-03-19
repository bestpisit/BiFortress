package com.mygdx.bifortress.option;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.mygdx.bifortress.BiFortress;
import com.mygdx.bifortress.tutorial.TutorialMenu;

import java.util.ArrayList;

import static com.mygdx.bifortress.BiFortress.*;

public class Option {
    ArrayList<Texture> textures;
    Texture currentTexture;
    public Option(){
        textures = new ArrayList<>();
        textures.add(new Texture(Gdx.files.internal("Background/Green.png")));
        textures.add(new Texture(Gdx.files.internal("Background/Blue.png")));
        textures.add(new Texture(Gdx.files.internal("Background/Brown.png")));
        textures.add(new Texture(Gdx.files.internal("Background/Gray.png")));
        textures.add(new Texture(Gdx.files.internal("Background/Pink.png")));
        textures.add(new Texture(Gdx.files.internal("Background/Purple.png")));
        textures.add(new Texture(Gdx.files.internal("Background/Yellow.png")));
        currentTexture = textures.get(0);
    }
    public void update(){
        if(main_background.backgroundTexture != currentTexture){
            main_background.backgroundTexture = currentTexture;
            main_background.tiledDrawable = new TiledDrawable(new TextureRegion(currentTexture));
        }
    }
    public void render()
    {
        Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        spriteBatch.begin();
        GlyphLayout glyphLayout = new GlyphLayout(TutorialMenu.font,"Choose Background Color");
        TutorialMenu.font.draw(spriteBatch,glyphLayout,screenViewport.getScreenWidth()/2-glyphLayout.width/2,screenViewport.getScreenHeight()-100);
        for(int j=0;j<3;j++){
            for(int i=0;i<3;i++){
                int number = i+j*3+1;
                if(number <= textures.size()){
                    Texture texture = textures.get(number-1);
                    Sprite sprite = new Sprite(texture);
                    sprite.setBounds(screenViewport.getScreenWidth()/2-128-64-30+158*i,screenViewport.getScreenHeight()-300-158*j,128,128);
                    if(new Rectangle(screenViewport.getScreenWidth()/2-128-64-30+158*i,screenViewport.getScreenHeight()-300-158*j,128,128).contains(mousePos.x, mousePos.y)){
                        sprite.setCenter(screenViewport.getScreenWidth()/2-128+32-25+158*i,screenViewport.getScreenHeight()-300+64-158*j);
                        sprite.rotate(45);
                        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                            currentTexture = texture;
                        }
                    }
                    sprite.draw(spriteBatch);
                }
            }
        }
        spriteBatch.end();
    }
    public void dispose(){

    }
}
