package com.mygdx.bifortress.tutorial.stage.bstgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.tutorial.TutorialMenu;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class BSTNode {
    public float x,y,initX,initY,radius;
    public int value,depth;
    public Color color;
    public BSTNode left;
    public BSTNode right;
    public boolean lineConnection;
    public BSTNode(float x,float y,float radius,int value){
        this.x = x;
        this.initX = x;
        this.y = y;
        this.initY = y;
        this.radius = radius;
        this.color = Color.BLACK;
        this.value = value;
        this.left = null;
        this.right = null;
        depth = 0;
        lineConnection = false;
    }
    public void render(ShapeRenderer shapeRenderer){
        double distance = Math.sqrt(Math.pow(initX - x,2)+Math.pow(initY - y,2));
        if(distance > 1f){
            double angle = Math.atan2(y-initY,x-initX);
            float factor = 0.1f;
            initX += factor*distance*Math.cos(angle);
            initY += factor*distance*Math.sin(angle);
        }
        else{
            initY = y;
            initX = x;
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.setColor(this.color);
        shapeRenderer.circle(initX,initY,radius);
        if(lineConnection){
            shapeRenderer.setColor(color);
            if(this.left != null){
                shapeRenderer.rectLine(left.initX, left.initY, initX,initY,5);
            }
            if(this.right != null){
                shapeRenderer.rectLine(right.initX, right.initY, initX,initY,5);
            }
        }
        shapeRenderer.end();
    }
    public void renderValue(ShapeRenderer shapeRenderer){
        spriteBatch.begin();
        GlyphLayout glyphLayout = new GlyphLayout(TutorialMenu.font,String.valueOf(this.value));
        TutorialMenu.font.draw(spriteBatch,String.valueOf(this.value),initX- glyphLayout.width/2,initY+ glyphLayout.height/2);
        spriteBatch.end();
    }
    public void renderHere(){
        spriteBatch.begin();
        GlyphLayout glyphLayout = new GlyphLayout(TutorialMenu.font,"Guess Me ("+this.value+")");
        TutorialMenu.font.draw(spriteBatch,glyphLayout,initX+64+20,initY+ glyphLayout.height/2);
        spriteBatch.end();
    }
    public boolean onMouse(Vector3 mousePos){
        double distance = Math.sqrt(Math.pow(initX - mousePos.x,2)+Math.pow(initY - mousePos.y,2));
        if(distance <= this.radius){
            return true;
        }
        return false;
    }
}
