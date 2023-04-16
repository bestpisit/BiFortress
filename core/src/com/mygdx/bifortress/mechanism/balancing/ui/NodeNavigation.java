package com.mygdx.bifortress.mechanism.balancing.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.mechanism.balancing.node.Node;

import static com.mygdx.bifortress.BiFortress.*;
import static com.mygdx.bifortress.mechanism.balancing.Balancing.gameZoom;

public class NodeNavigation {
    public Node node;
    float progress,xR,yR,initX,initY,xR1,yR1,initX1,initY1;
    BitmapFont font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    public NodeNavigation(){
        node = null;
        progress = 0;
        xR = yR = initY = initX = 0;
        xR1 = yR1 = initY1 = initX1 = 0;
    }
    public void render(ShapeRenderer shapeRenderer){
        //update
        if(node == null){
            if(progress > 0.5f){
                progress -= 0.5f+ progress /10f;
            }
            else{
                progress = 0f;
            }
        }
        else{
            if(progress < 100){
                progress += 0.5f+(100- progress)/10f;
            }
            else{
                progress = 100;
            }
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
        //render
        if(progress > 0){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//            shapeRenderer.setColor(0,0,0,0.5f);
//            shapeRenderer.rect(0,screenViewport.getScreenHeight()/2+500/2,200*progress/100,screenViewport.getScreenHeight()/2-500/2);
//            shapeRenderer.setColor(57/255f,1,20/255f,0.5f);
//            shapeRenderer.rectLine(-10*((100-progress)/100)+5,screenViewport.getScreenHeight()/2-500/2,-10*((100-progress)/100)+5,screenViewport.getScreenHeight()/2+500/2,10);
//            shapeRenderer.rectLine(200*progress/100,screenViewport.getScreenHeight()/2+500/2,0,screenViewport.getScreenHeight()/2+500/2,10);
//            shapeRenderer.rectLine(200*progress/100,screenViewport.getScreenHeight()/2-500/2,0,screenViewport.getScreenHeight()/2-500/2,10);
//            shapeRenderer.rectLine(200*progress/100,screenViewport.getScreenHeight()/2+500/2+5,200*progress/100,screenViewport.getScreenHeight()/2-500/2-5,10);
            if(node != null){
                Vector3 eeMap = gameViewport.getCamera().project(new Vector3(node.x+50, node.y+50, 0));
                float nodeScreenX = eeMap.x;
                float nodeScreenY = eeMap.y;

                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                Vector3 mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR = mousePos.x;
                yR = mousePos.y;
                eeMap = gameViewport.getCamera().project(new Vector3(node.x, node.y, 0));
                nodeScreenX = eeMap.x;
                nodeScreenY = eeMap.y;

                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR1 = mousePos.x;
                yR1 = mousePos.y;
            }
            GlyphLayout glyphLayout = new GlyphLayout(font,(node!=null)?node.balanceFactor+((Math.abs(node.balanceFactor)<=1)?"\n(Balance)":"\n(Unbalance)")+"\n"+Integer.valueOf((int) node.pow)+"/"+Integer.valueOf((int) node.power)+"\nPower":"\n\n\n", Color.WHITE,200,1,true);
            if(progress > 0){
                shapeRenderer.setColor(0,0,0,0.75f*progress/100);
                shapeRenderer.rect(initX,initY,200, glyphLayout.height+50);
                shapeRenderer.rectLine(initX,initY,xR1,yR1,5);
            }
            shapeRenderer.end();
            if(node != null){
                spriteBatch.begin();
                font.draw(spriteBatch,glyphLayout,initX,initY+glyphLayout.height+25);
                spriteBatch.end();
            }
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
    public void dispose(){
        font.dispose();
    }
}
