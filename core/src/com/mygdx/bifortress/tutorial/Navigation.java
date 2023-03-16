package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.animation.ObjectAnimation;

import java.util.ArrayList;

import static com.mygdx.bifortress.BiFortress.*;

public class Navigation {
    float alpha;
    public boolean opening,isDisplayFinish;
    BitmapFont font;
    String currentString,stringPrototype;
    ObjectAnimation avatar;
    AnimationSprite currentAvatar;
    ArrayList<String> strQueue;
    float strTime,stateTime;
    int count;
    public Navigation(){
        alpha = 0;
        stateTime = 0;
        opening = false;
        isDisplayFinish = true;
        font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
        currentString = "";
        stringPrototype = "";
        strTime = 0;
        count = 0;
        strQueue = new ArrayList<>();
        currentAvatar = AnimationSprite.FROG_IDLE;
        avatar = new ObjectAnimation(currentAvatar,0.25f);
    }
    public void update(){
        stateTime += Gdx.graphics.getDeltaTime();
        Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Rectangle rect = new Rectangle(0,0,screenViewport.getScreenWidth(),200);
        if(rect.contains(mousePos.x, mousePos.y)){
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                Tutorial.tutorialController.nextConversation();
            }
        }
        if(opening){
            if(alpha < 100){
                alpha += 0.5f + alpha*0.1f;
            }
            else{
                alpha = 100;
            }
            if(strTime < 0.015f){
                strTime += Gdx.graphics.getDeltaTime();
            }
            else{
                if(currentString.length() < stringPrototype.length()){
                    currentString += stringPrototype.charAt(currentString.length());
                }
                else{
                    if(currentString != stringPrototype){
                        currentString = stringPrototype;
                    }
                    isDisplayFinish = true;
                }
                strTime = 0;
            }
        }
        else{
            if(alpha > 0){
                alpha -= 1.5f + (100-alpha)*0.1f;
            }
            else{
                alpha = 0;
            }
        }
        avatar.update(stateTime);
    }
    public void changeAvatar(AnimationSprite animationSprite,float speed){
        currentAvatar = animationSprite;
        avatar.changeSprite(currentAvatar,speed);
    }
    public void render(ShapeRenderer shapeRenderer){
        if(alpha > 0){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Color color = new Color(0,0,0,alpha/160);
            shapeRenderer.setColor(color);
            shapeRenderer.rect(0,0,screenViewport.getScreenWidth(),200);

            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            spriteBatch.begin();
            Sprite sprite = new Sprite(avatar.Frame);
            sprite.setAlpha(alpha/100);
            sprite.setBounds(100- 200/2,100-160/2, 200, 200);
            sprite.draw(spriteBatch);
            font.draw(spriteBatch,currentString,200,140+ font.getLineHeight(),screenViewport.getScreenWidth()-200-25,-1,true);
            spriteBatch.end();
        }
    }
    public void display(String str){
        if(!opening){
            opening = true;
        }
        currentString = "";
        stringPrototype = str;
        isDisplayFinish = false;
    }
    public void dispose(){
        font.dispose();
    }
}
