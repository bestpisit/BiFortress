package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.BiFortress;

import java.util.ArrayList;

import static com.mygdx.bifortress.BiFortress.screenViewport;
import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class TutorialController {
    public class StringNavigation{
        public String str;
        public boolean allowNext;
        public StringNavigation(String str,boolean allowNext){
            this.str = str;
            this.allowNext = allowNext;
        }
    }
    ArrayList<StringNavigation> conversationQueue,prevConversation;
    public StringNavigation currentStr;
    public int currentIndex;
    Texture upLogo,upLogoR;
    public TutorialController(){
        conversationQueue = new ArrayList<>();
        prevConversation = new ArrayList<>();
        init();
        currentStr = null;
        currentIndex = 0;
        upLogo = new Texture(Gdx.files.internal("ui/icons/Arrow Up_Lightgreen.png"));
        upLogoR = new Texture(Gdx.files.internal("ui/icons/Arrow Up_Red.png"));
        //nextConversation();
    }
    public void init(){
//        conversationQueue.add("You know, its hard for me to make my heart silent" +
//                " The echo of my secret could be never end" +
//                " And I dont want my heart to be so broken" +
//                " From all those words inside that left unspoken");
//        conversationQueue.add("Nice To Meet You");
//        conversationQueue.add("You are Inspiration");
//        conversationQueue.add("Good luck");
    }
    public void update(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            previousConversation();
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            nextConversation(false);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            goMenuStages();
        }
    }
    public void previousConversation(){
        if(currentStr == null || currentStr.allowNext){
            if(currentStr != null){
                if(prevConversation.isEmpty()){
                    conversationQueue.add(0,currentStr);
                    Tutorial.navigation.opening = false;
                    Tutorial.navigation.isDisplayFinish = true;
                    currentStr = null;
                }
                else{
                    conversationQueue.add(0,currentStr);
                    currentStr = prevConversation.remove(0);
                    Tutorial.navigation.display(currentStr.str);
                }
                currentIndex--;
            }
            else{
                if(prevConversation.isEmpty()){
                    Tutorial.tutorialMenu.previousStage();
                }
                else{
                    currentStr = prevConversation.remove(0);
                    Tutorial.navigation.display(currentStr.str);
                    currentIndex--;
                }
            }
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        spriteBatch.begin();
        GlyphLayout glyphLayout = new GlyphLayout(TutorialMenu.font,TutorialMenu.currentStage.title);
        TutorialMenu.font.draw(spriteBatch,glyphLayout,screenViewport.getScreenWidth()/2- glyphLayout.width/2,screenViewport.getScreenHeight());
        Sprite sprite = new Sprite(upLogo);
        sprite.setBounds(100,screenViewport.getScreenHeight()-80-10, 80, 80);
        if(sprite.getBoundingRectangle().contains(mousePos.x, mousePos.y)){
            sprite.setBounds(100-10,screenViewport.getScreenHeight()-80-10-10, 100, 100);
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                goMenuStages();
            }
        }
        sprite.draw(spriteBatch);
        GlyphLayout glyphLayout1 = new GlyphLayout(TutorialMenu.font,"Back To Menu");
        TutorialMenu.font.draw(spriteBatch,glyphLayout1,200,screenViewport.getScreenHeight()-35);
        spriteBatch.end();
        if(Tutorial.navigation.alpha > 0){
            if(currentStr != null && currentStr.allowNext){
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                Color color = new Color(0,0,0,Tutorial.navigation.alpha/160);
                if(new Rectangle(0,200,150,50).contains(mousePos.x, mousePos.y)){
                    color.r = 50;
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                        previousConversation();
                    }
                }
                shapeRenderer.setColor(color);
                shapeRenderer.rect(0,200,150,50);
                color = new Color(0,0,0,Tutorial.navigation.alpha/160);
                if(new Rectangle(screenViewport.getScreenWidth()-150,200,150,50).contains(mousePos.x, mousePos.y)){
                    color.g = 50;
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                        nextConversation(false);
                    }
                }
                shapeRenderer.setColor(color);
                shapeRenderer.rect(screenViewport.getScreenWidth()-150,200,150,50);
                shapeRenderer.end();
                spriteBatch.begin();
                TutorialMenu.font.draw(spriteBatch,"Previous",0,250-10,150,1,true);
                TutorialMenu.font.draw(spriteBatch,"Next",screenViewport.getScreenWidth()-150,250-10,150,1,true);
                spriteBatch.end();
            }
        }
        else{
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(screenViewport.getScreenWidth()/2-100,100,200,100);
            shapeRenderer.end();
            spriteBatch.begin();
            TutorialMenu.font.draw(spriteBatch,"Continue",screenViewport.getScreenWidth()/2-100,165,200,1,true);
            spriteBatch.end();
        }
    }
    public void dispose(){
        upLogoR.dispose();
        upLogo.dispose();
    }
    public void nextConversation(boolean force){
        if(currentStr == null || currentStr.allowNext || force){
            if(conversationQueue.size() > 0){
                StringNavigation str = conversationQueue.remove(0);
                if (currentStr != null) {
                    prevConversation.add(0, currentStr);
                }
                currentStr = str;
                Tutorial.navigation.display(currentStr.str);
                currentIndex++;
            }
            else{
                if(currentStr != null){
                    prevConversation.add(0, currentStr);
                    currentIndex++;
                    currentStr = null;
                    Tutorial.navigation.opening = false;
                    Tutorial.navigation.isDisplayFinish = true;
                }
                else{
                    Tutorial.tutorialMenu.nextStage();
                }
            }
        }
    }
    public void reset(){
        conversationQueue.clear();
        prevConversation.clear();
        currentStr = null;
        currentIndex = 0;
        Tutorial.navigation.opening = false;
        Tutorial.navigation.isDisplayFinish = true;
    }
    public void goMenuStages(){
        Tutorial.tutorialState = Tutorial.TutorialState.MENU;
        TutorialMenu.currentStage = null;
    }
    public void addConversation(String str,boolean bool){
        conversationQueue.add(new StringNavigation(str,bool));
    }
    public void addConversation(String str){
        conversationQueue.add(new StringNavigation(str,true));
    }
}
