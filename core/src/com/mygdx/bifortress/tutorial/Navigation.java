package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import static com.mygdx.bifortress.BiFortress.*;

public class Navigation {
    float alpha;
    boolean opening;
    BitmapFont font;
    String currentString,stringPrototype;
    ArrayList<String> strQueue;
    float strTime;
    int count;
    public Navigation(){
        alpha = 0;
        opening = false;
        font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
        currentString = "";
        stringPrototype = "";
        strTime = 0;
        count = 0;
        strQueue = new ArrayList<>();
        display("You know, its hard for me to make my heart silent" +
                " The echo of my secret could be never end" +
                "And I dont want my heart to be so broken" +
                " From all those words inside that left unspoken");
    }
    public void update(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            display("Hello ByBua I'm Besty! "+ ++count);
        }
        if(opening){
            if(alpha < 100){
                alpha += 0.5f + alpha*0.1f;
            }
            else{
                alpha = 100;
            }
            if(strTime < 0.05f){
                strTime += Gdx.graphics.getDeltaTime()*Math.log(stringPrototype.length()-currentString.length());
            }
            else{
                if(currentString.length() < stringPrototype.length()){
                    currentString += stringPrototype.charAt(currentString.length());
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

            font.draw(spriteBatch,currentString,200,100+ font.getLineHeight(),screenViewport.getScreenWidth()-200-25,-1,true);
            spriteBatch.end();
        }
    }
    public void display(String str){
        if(!opening){
            opening = true;
        }
        currentString = "";
        stringPrototype = str;
    }
    public void dispose(){
        font.dispose();
    }
}
