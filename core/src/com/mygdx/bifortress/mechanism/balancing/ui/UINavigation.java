package com.mygdx.bifortress.mechanism.balancing.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.BiFortress;

import static com.mygdx.bifortress.BiFortress.*;

public class UINavigation {
    enum PointDir{
        UP,DOWN,LEFT,RIGHT
    }
    float progress,xR,yR,initX,initY;
    int step;
    String str;
    PointDir pointDir = PointDir.UP;
    BitmapFont font = new BitmapFont(Gdx.files.internal("ui/ui.fnt"));
    Sprite point = new Sprite(new Texture(Gdx.files.internal("point.png")));
    public UINavigation(){
        progress = 0;
        step = 0;
        xR = yR = initY = initX = 0;
        str = "";
    }
    public void update(){
        if(progress<100){
            progress += 1;
        }
        else{
            progress = 0;
        }
        //update
        switch(step){
            case 0:
                xR = screenViewport.getScreenWidth()/2;
                yR = screenViewport.getScreenHeight()/2-64;
                str = "You are ninja frog\nyou are responsible for protecting the tree";
                pointDir = PointDir.UP;
                break;
            case 1:
                xR = screenViewport.getScreenWidth()/2;
                yR = screenViewport.getScreenHeight()/2-64;
                str = "Press WASD key to move around";
                pointDir = PointDir.UP;
                break;
            case 2:
                xR = screenViewport.getScreenWidth()/2;
                yR = screenViewport.getScreenHeight()-100;
                str = "This is your score that display your progress";
                pointDir = PointDir.UP;
                break;
            case 3:
                xR = 250;
                yR = 50;
                str = "This is the phrase timer to show the current progress";
                pointDir = PointDir.DOWN;
                break;
        }
        double distance = Math.sqrt(Math.pow(initX - xR,2)+Math.pow(initY - yR,2));
        if(distance > 1f){
            double angle = Math.atan2(yR-initY,xR-initX);
            float factor = 0.1f;
            initX += factor*distance*Math.cos(angle);
            initY += factor*distance*Math.sin(angle);
        }
        else{
            initY = yR;
            initX = xR;
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        update();
        if(str != ""){
            if(pointDir == PointDir.UP){
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                GlyphLayout glyphLayout = new GlyphLayout(font,str, Color.WHITE,400,1,true);
                shapeRenderer.setColor(0,0,0,0.8f);
                shapeRenderer.rect(initX-200-10,initY-glyphLayout.height-50- point.getHeight(), 400+20,glyphLayout.height+50);
                Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                if(new Rectangle(initX-200-10,initY-glyphLayout.height-50- point.getHeight()-30, 400+20,glyphLayout.height+50+30).contains(mousePos.x, mousePos.y)){
                    shapeRenderer.setColor(Color.GREEN);
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                        step++;
                    }
                }
                else{
                    shapeRenderer.setColor(Color.LIME);
                }
                shapeRenderer.rect(initX-200-10,initY-glyphLayout.height-50- point.getHeight()-30, 400+20,30);
                shapeRenderer.end();
                spriteBatch.begin();
                font.draw(spriteBatch,glyphLayout,initX-200,initY-25- point.getHeight());
                GlyphLayout gl = new GlyphLayout(font,"OK",Color.WHITE,400,1,true);
                font.draw(spriteBatch,gl,initX-200,initY-25- point.getHeight()- glyphLayout.height-30);
                point.setBounds(initX-point.getWidth()/2,initY-point.getHeight()+Math.abs(50-progress)/2,point.getWidth(), point.getHeight());
                point.draw(spriteBatch);
                spriteBatch.end();
            }
            else if(pointDir == PointDir.DOWN){
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                GlyphLayout glyphLayout = new GlyphLayout(font,str, Color.WHITE,400,1,true);
                shapeRenderer.setColor(0,0,0,0.8f);
                shapeRenderer.rect(initX-200-10,initY+50+point.getHeight()+30, 400+20,glyphLayout.height+50);
                Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                if(new Rectangle(initX-200-10,initY+50+point.getHeight(), 400+20,glyphLayout.height+50+30).contains(mousePos.x, mousePos.y)){
                    shapeRenderer.setColor(Color.GREEN);
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                        step++;
                    }
                }
                else{
                    shapeRenderer.setColor(Color.LIME);
                }
                shapeRenderer.rect(initX-200-10,initY+50+ point.getHeight(), 400+20,30);
                shapeRenderer.end();

                spriteBatch.begin();
                font.draw(spriteBatch,glyphLayout,initX-200,initY+50+point.getHeight()+30+ glyphLayout.height+50-25);
                GlyphLayout gl = new GlyphLayout(font,"OK",Color.WHITE,400,1,true);
                font.draw(spriteBatch,gl,initX-200,initY+point.getHeight()+50+30-5);
                point.setFlip(false,true);
                point.setBounds(initX-point.getWidth()/2,initY+point.getHeight()-Math.abs(50-progress)/2-20,point.getWidth(), point.getHeight());
                point.draw(spriteBatch);
                spriteBatch.end();
            }
        }
    }
    public void dispose(){
        point.getTexture().dispose();
        font.dispose();

    }
}
