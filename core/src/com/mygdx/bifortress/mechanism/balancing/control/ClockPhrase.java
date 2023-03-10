package com.mygdx.bifortress.mechanism.balancing.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.enemies.Bat;
import com.mygdx.bifortress.mechanism.balancing.enemies.Infernous;
import com.mygdx.bifortress.mechanism.balancing.inventory.Inventory;
import com.mygdx.bifortress.mechanism.balancing.inventory.ItemNode;
import com.mygdx.bifortress.mechanism.balancing.node.DefenderNode;
import com.mygdx.bifortress.mechanism.balancing.node.SupplierNode;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class ClockPhrase {
    public static float time,maxTime;
    float xPos=40,yPos=40;
    boolean atReady;
    BitmapFont text;
    public static class PhraseEvent{
        public Phrase phrase;
        public float time;
        public String text;
        public PhraseEvent(Phrase phrase, float time,String text){
            this.phrase = phrase;
            this.time = time;
            this.text = text;
        }
    }
    public ClockPhrase(){
        time = 0;
        maxTime = 30f;
        text = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
        this.phrase = new PhraseEvent(Phrase.DEFAULT,5,"");
        phrases = new ArrayList<>();
        invasion = false;
        atReady = false;
    }
    public void update(){
        if(time < maxTime){
            time += Gdx.graphics.getDeltaTime();
        }
        else{
            getNewPhrase();
        }
        if(invasion){
            updateInvasion();
        }
    }
    public static void createInvasion(){
        if(Balancing.level % 10 == 0){
            int count = Balancing.level/10;
            for(int i=0;i<count;i++){
                Balancing.enemies.add(new Infernous(500,300));
            }
        }
        for(int i=0;i<Balancing.level;i++){
            Balancing.enemies.add(new Bat(500,300,0.5f));
        }
    }
    public void updateInvasion(){
        if(Balancing.enemies.size <= 0){
            //get free node
            Random rand = new Random();
            int r = rand.nextInt(100);
            boolean isNode = rand.nextBoolean();
            Inventory.itemNodes.add(new ItemNode((isNode)? DefenderNode.class: SupplierNode.class,r));
            Inventory.reLocation();
            Balancing.level++;
            phrases.add(new PhraseEvent(Phrase.MANIPULATION,120,"TREE-MANIPULATION"));
            phrases.add(new PhraseEvent(Phrase.INVASION,5,"INVASION "+Balancing.level));
            invasion = false;
        }
    }
    public enum Phrase{
        DEFAULT,
        MANIPULATION,
        INVASION,
        NONE
    }
    public static PhraseEvent phrase;
    public static ArrayList<PhraseEvent> phrases;
    public static boolean invasion;
    public static void getNewPhrase(){
        if(phrase.phrase == Phrase.INVASION){
            invasion = true;
            createInvasion();
        }
        if(phrases.size()>0){
            //check phrase invasion
            time = 0;
            PhraseEvent pe = phrases.remove(0);
            phrase = pe;
            maxTime = pe.time;
        }
        else{
            phrase = new PhraseEvent(Phrase.NONE,0,"");
        }
    }
    public void render(ShapeRenderer shapeRenderer, Vector3 mousePos){
        if(phrase != null && phrase.phrase != Phrase.NONE) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(xPos, yPos, 40);
            shapeRenderer.setColor(Color.valueOf("F44336"));
            shapeRenderer.arc(xPos, yPos, 32, 90, 360f * (maxTime - time) / maxTime, 64);
            shapeRenderer.end();

            spriteBatch.begin();
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(text, String.valueOf(Math.round(maxTime - time)));
            text.draw(spriteBatch, glyphLayout, xPos - glyphLayout.width / 2, yPos + glyphLayout.height / 2);
            glyphLayout.setText(text, phrase.text);
            text.draw(spriteBatch, glyphLayout, xPos + 32 + 15, yPos + glyphLayout.height / 2);
            spriteBatch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if(phrase.phrase == Phrase.MANIPULATION){
                shapeRenderer.setColor(Color.LIME);
                if(new Rectangle(8+64+10+glyphLayout.width+10,8,64*2,64).contains(mousePos.x, mousePos.y)){
                    atReady = true;
                }
                else{
                    atReady = false;
                }
                if(atReady){
                    shapeRenderer.setColor(Color.GREEN);
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                        getNewPhrase();
                    }
                }
                shapeRenderer.rect(8+64+10+glyphLayout.width+10,8,64*2,64);
            }
            shapeRenderer.end();
            spriteBatch.begin();
            if(phrase.phrase == Phrase.MANIPULATION){
                text.draw(spriteBatch,"Ready",8+64+10+glyphLayout.width+10+20,yPos + glyphLayout.height / 2);
            }
            spriteBatch.end();
        }
        if(invasion){
            int cc = Balancing.enemies.size;
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.circle(xPos, yPos, 40);
            shapeRenderer.setColor(Color.valueOf("F44336"));
            shapeRenderer.arc(xPos, yPos, 32, 90, 360f * (cc) / Balancing.level, 64);
            shapeRenderer.end();
            spriteBatch.begin();
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(text, String.valueOf(cc));
            text.draw(spriteBatch, glyphLayout, xPos - glyphLayout.width / 2, yPos + glyphLayout.height / 2);
            glyphLayout.setText(text, "ENEMIES LEFT");
            text.draw(spriteBatch, glyphLayout, xPos + 32 + 15, yPos + glyphLayout.height / 2);
            spriteBatch.end();
        }
    }
}
