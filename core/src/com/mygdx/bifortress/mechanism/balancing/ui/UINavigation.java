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
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.node.DefenderNode;
import com.mygdx.bifortress.mechanism.balancing.node.Node;

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
            case 4:
                xR = screenViewport.getScreenWidth()-250;
                yR = screenViewport.getScreenHeight()-100;
                str = "Change the node control by right clicking";
                pointDir = PointDir.UP;
                break;
            case 5:
                xR = screenViewport.getScreenWidth()-250;
                yR = screenViewport.getScreenHeight()-100;
                str = "The Node Control Provides Deletion, Rotation and Default";
                pointDir = PointDir.UP;
                break;
            case 6:
                Vector3 eeMap = gameViewport.getCamera().project(new Vector3((Balancing.bst.root!=null)?Balancing.bst.root.x:0, (Balancing.bst.root!=null)?Balancing.bst.root.y:0, 0));
                float nodeScreenX = eeMap.x;
                float nodeScreenY = eeMap.y;

                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                Vector3 mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR = mousePos.x;
                yR = mousePos.y;
                str = "This is Supplier Node (Green)\nit emit the power cells to supply the power to all the node in the tree";
                pointDir = PointDir.DOWN;
                break;
            case 7:
                Node node = null;
                for(Node n: Balancing.bst.nodes){
                    if(n.getClass() == DefenderNode.class){
                        node = n;
                        break;
                    }
                }
                if(node != null){
                    eeMap = gameViewport.getCamera().project(new Vector3(node.x, node.y, 0));
                }
                else{
                    eeMap = gameViewport.getCamera().project(new Vector3((Balancing.bst.root!=null)?Balancing.bst.root.x:0, (Balancing.bst.root!=null)?Balancing.bst.root.y:0, 0));
                }
                nodeScreenX = eeMap.x;
                nodeScreenY = eeMap.y;
                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR = mousePos.x;
                yR = mousePos.y;
                str = "This is Defender Node (Grey)\nit help shooting the incoming raid of enemies protecting your tree";
                pointDir = PointDir.DOWN;
                break;
            case 8:
                xR = screenViewport.getScreenWidth()-250;
                yR = 40;
                str = "This is your Node Inventory, you can simply drag the node in the inventory to the tree for Node Insertion";
                pointDir = PointDir.DOWN;
                break;
            case 9:
                xR = 0;
                yR = 0;
                str = "";
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
                else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && str != ""){
                    step++;
                }
                else{
                    shapeRenderer.setColor(Color.LIME);
                }
                shapeRenderer.rect(initX-200-10,initY-glyphLayout.height-50- point.getHeight()-30, 400+20,30);
                shapeRenderer.end();
                spriteBatch.begin();
                font.draw(spriteBatch,glyphLayout,initX-200,initY-25- point.getHeight());
                GlyphLayout gl = new GlyphLayout(font,"OK",Color.WHITE,400,1,true);
                point.setRotation(0);
                point.setFlip(false,false);
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
                else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && str != ""){
                    step++;
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
                point.setRotation(0);
                point.setBounds(initX-point.getWidth()/2,initY+point.getHeight()-Math.abs(50-progress)/2-20,point.getWidth(), point.getHeight());
                point.draw(spriteBatch);
                spriteBatch.end();
            }
            else if(pointDir == PointDir.LEFT){
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                GlyphLayout glyphLayout = new GlyphLayout(font,str, Color.WHITE,400,1,true);
                shapeRenderer.setColor(0,0,0,0.8f);
                shapeRenderer.rect(initX-400-10-point.getHeight()-30-20,initY- (glyphLayout.height+30+50)/2+30, 400+20,glyphLayout.height+50);
                Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                if(new Rectangle(initX-400-10-point.getHeight()-30-20,initY- (glyphLayout.height+30+50)/2, 400+20,glyphLayout.height+50+30).contains(mousePos.x, mousePos.y)){
                    shapeRenderer.setColor(Color.GREEN);
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                        step++;
                    }
                }
                else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && str != ""){
                    step++;
                }
                else{
                    shapeRenderer.setColor(Color.LIME);
                }
                shapeRenderer.rect(initX-400-10-point.getHeight()-30-20,initY- (glyphLayout.height+30+50)/2, 400+20,30);
                shapeRenderer.end();
                spriteBatch.begin();
                font.draw(spriteBatch,glyphLayout,initX-400-10-point.getHeight()-30-20,initY+ (glyphLayout.height+30+50)/2-25);
                GlyphLayout gl = new GlyphLayout(font,"OK",Color.WHITE,400,1,true);
                font.draw(spriteBatch,gl,initX-400-10-point.getHeight()-30-20,initY- (glyphLayout.height+30+50)/2+ gl.height+5);
                point.setRotation(270);
                point.setFlip(false,false);
                point.setBounds(initX- point.getWidth()-Math.abs(50-progress)/2-20,initY- point.getHeight()/2,point.getWidth(), point.getHeight());
                point.draw(spriteBatch);
                spriteBatch.end();
            }
            else if(pointDir == PointDir.RIGHT){
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                GlyphLayout glyphLayout = new GlyphLayout(font,str, Color.WHITE,400,1,true);
                shapeRenderer.setColor(0,0,0,0.8f);
                shapeRenderer.rect(initX+10+point.getHeight()+30,initY- (glyphLayout.height+30+50)/2+30, 400+20,glyphLayout.height+50);
                Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                if(new Rectangle(initX+10+point.getHeight()+30,initY- (glyphLayout.height+30+50)/2, 400+20,glyphLayout.height+50+30).contains(mousePos.x, mousePos.y)){
                    shapeRenderer.setColor(Color.GREEN);
                    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                        step++;
                    }
                }
                else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && str != ""){
                    step++;
                }
                else{
                    shapeRenderer.setColor(Color.LIME);
                }
                shapeRenderer.rect(initX+10+point.getHeight()+30,initY- (glyphLayout.height+30+50)/2, 400+20,30);
                shapeRenderer.end();
                spriteBatch.begin();
                font.draw(spriteBatch,glyphLayout,initX+10+point.getHeight()+30,initY+ (glyphLayout.height+30+50)/2-25);
                GlyphLayout gl = new GlyphLayout(font,"OK",Color.WHITE,400,1,true);
                font.draw(spriteBatch,gl,initX+10+point.getHeight()+30,initY- (glyphLayout.height+30+50)/2+ gl.height+5);
                point.setRotation(90);
                point.setFlip(false,false);
                point.setBounds(initX+ point.getWidth()-Math.abs(50-progress)/2-20,initY- point.getHeight()/2,point.getWidth(), point.getHeight());
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
