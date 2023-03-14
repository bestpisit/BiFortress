package com.mygdx.bifortress.mechanism.balancing.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;

import static com.mygdx.bifortress.BiFortress.screenViewport;
import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class MovementControl {
    public enum Movement{
        DEFAULT,ROTATE,SWAP,DELETE
    }
    public static Movement control;
    public static int itMC;
    public static Movement[] MoveStage = {Movement.DEFAULT,Movement.DELETE,Movement.ROTATE};
    BitmapFont text;
    public MovementControl(){
        itMC = 0;
        control = MoveStage[itMC];
        text = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    }
    public void update(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            if(itMC+1 >= MoveStage.length){
                itMC = 0;
            }
            else{
                itMC++;
            }
            control = MoveStage[itMC];
            if(BinarySearchTree.onNode != null){
                BinarySearchTree.onNode.toggle = false;
                BinarySearchTree.onNode = null;
            }
        }
    }
    public void render(ShapeRenderer shapeRenderer){
        spriteBatch.begin();
        String str;
        Sprite sprite = new Sprite(new Texture(("ui/default.png")));
        String txt = "Right Click To Change Node Control";
        switch(control){
            case ROTATE:
                sprite = new Sprite(new Texture(("ui/rotate.png")));
                txt = "Rotate Node";
                break;
            case DELETE:
                sprite = new Sprite(new Texture(("ui/delete.png")));
                txt = "Delete Node";
                break;
        }
        sprite.setBounds(5,screenViewport.getScreenHeight()-5-64,64,64);
        text.draw(spriteBatch,txt,5+64+5,screenViewport.getScreenHeight()-5-20);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
}
