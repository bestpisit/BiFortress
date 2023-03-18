package com.mygdx.bifortress.mechanism.balancing;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.bifortress.animation.AnimationSprite;
import com.mygdx.bifortress.mechanism.Player;
import com.mygdx.bifortress.mechanism.balancing.cell.CanonCell;
import com.mygdx.bifortress.mechanism.balancing.control.ClockPhrase;
import com.mygdx.bifortress.mechanism.balancing.control.MovementControl;
import com.mygdx.bifortress.mechanism.balancing.enemies.*;
import com.mygdx.bifortress.mechanism.balancing.inventory.Inventory;
import com.mygdx.bifortress.mechanism.balancing.items.Fruits;
import com.mygdx.bifortress.mechanism.balancing.particles.RunParticle;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.bifortress.BiFortress.*;

public class Balancing {
    public static Player player;
    public static DelayedRemovalArray<Enemy> enemies;
    public static DelayedRemovalArray<RunParticle> particles;
    public static final Pool<RunParticle> particlesPool = new Pool<RunParticle>() {
        @Override
        protected RunParticle newObject() {
            return new RunParticle();
        }
    };
    public static DelayedRemovalArray<CanonCell> canonCells;
    public static DelayedRemovalArray<Fruits> fruits;
    ShapeRenderer shapeRenderer;
    public static BinarySearchTree bst;
    public static float gameZoom;
    private Stage stage;
    Vector3 mousePos;
    public static float camX,camY;
    public static Random rand = new Random(System.currentTimeMillis());
    public static MovementControl movementControl;
    public static ClockPhrase clockPhrase;
    public static Inventory inventory;
    public static boolean gameOver;
    public static int level,score;
    BitmapFont text;
    public Balancing(){
        player = new Player(AnimationSprite.FROG_IDLE,AnimationSprite.FROG_RUN,AnimationSprite.FROG_HIT,0,-100,64,64);
        enemies = new DelayedRemovalArray<>();
        gameZoom = 1f;
        shapeRenderer = new ShapeRenderer();
        bst = new BinarySearchTree();
        stage = new Stage(gameViewport, spriteBatch);
        if (!inputMultiplexer.getProcessors().contains(stage,true)){
            inputMultiplexer.addProcessor(stage);
        }
        particles = new DelayedRemovalArray<>();
        camX = player.xPos;
        camY = player.yPos;
        movementControl = new MovementControl(bst);
        inventory = new Inventory(bst);
        canonCells = new DelayedRemovalArray<>();
        clockPhrase = new ClockPhrase();
        //phrase
        ClockPhrase.phrases.add(new ClockPhrase.PhraseEvent(ClockPhrase.Phrase.DEFAULT,5,"STARTING"));
        ClockPhrase.phrases.add(new ClockPhrase.PhraseEvent(ClockPhrase.Phrase.MANIPULATION,120,"TREE-MANIPULATION"));
        ClockPhrase.phrases.add(new ClockPhrase.PhraseEvent(ClockPhrase.Phrase.INVASION,5,"INVASION 1"));
        ClockPhrase.getNewPhrase();
        gameOver = false;
        level = 1;
        fruits = new DelayedRemovalArray<>();
        score = 0;
        text = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    }
    public void update(){
        if(!gameOver){
            clockPhrase.update();
            movementControl.update();
            player.update();
            if(gameZoom + 0.05*isScrolled > 0.1 && gameZoom + 0.05*isScrolled < 1.5){
                gameZoom+=0.05*isScrolled;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
                ArrayList<Integer> arr = new ArrayList<>();
                arr.add(rand.nextInt(100)+1);
                bst.generateTree(arr);
                bst.reLocation();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.O)){
                enemies.add(new Bat(500,300,1));
                enemies.add(new Ghost(500,300,1));
                enemies.add(new Trunk(500,300,1));
                enemies.add(new Chameleon(500,300,1));
                enemies.add(new Bunny(500,300,1));
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
                //enemies.add(new Infernous(500,300));
                fruits.add(new Fruits(2,100,100));
            }
            //camera move
            double distance = Math.sqrt(Math.pow(player.xPos - camX,2)+Math.pow(player.yPos - camY,2));
            if(distance > 1f){
                double angle = Math.atan2(player.yPos-camY, player.xPos-camX);
                float factor = 0.25f;
                camX += factor*distance*Math.cos(angle);
                camY += factor*distance*Math.sin(angle);
            }
            else{
                camX = player.xPos;
                camY = player.yPos;
            }
        }
    }
    public void render(){
        mousePos = gameViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        mousePos.x += player.hsp;
        mousePos.y += player.vsp;
        gameViewport.getCamera().position.set(camX, camY, 0);
        ((OrthographicCamera) gameViewport.getCamera()).zoom = gameZoom;
        gameViewport.apply();
        spriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        bst.render(mousePos,shapeRenderer);
        stage.act();
        stage.draw();
        for(RunParticle particle: particles){
            particle.render();
        }
        for(CanonCell cCell: canonCells){
            cCell.render(shapeRenderer);
        }
        for(Fruits f: fruits){
            f.render(shapeRenderer);
        }
        for(Enemy enemy : enemies){
            enemy.render();
        }
        player.render(mousePos,shapeRenderer);
        //UI
        spriteBatch.setProjectionMatrix(screenViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        movementControl.render(shapeRenderer);
        mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        inventory.render(shapeRenderer,mousePos);
        clockPhrase.render(shapeRenderer,mousePos);
        spriteBatch.begin();
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(text, String.valueOf(score));
        text.getData().setScale(2,2);
        text.draw(spriteBatch,glyphLayout,screenViewport.getScreenWidth()/2- glyphLayout.width/2,screenViewport.getScreenHeight()-10);
        spriteBatch.end();
    }
    public void dispose(){
        player.dispose();
        for(Enemy enemy: enemies){
            enemy.dispose();
        }
        for(RunParticle particle: particles){
            particle.dispose();
        }
        for(Fruits f: fruits){
            f.dispose();
        }
        for(CanonCell c: canonCells){
            c.dispose();
        }
        shapeRenderer.dispose();
    }
    public static void ScreenShake(int amplifier){
        int shakeX = (int)(Math.random() * amplifier) - amplifier / 2;
        int shakeY = (int)(Math.random() * amplifier) - amplifier / 2;
        camX += shakeX;
        camY += shakeY;
    }
}
