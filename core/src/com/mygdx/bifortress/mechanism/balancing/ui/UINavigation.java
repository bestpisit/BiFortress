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
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.control.ClockPhrase;
import com.mygdx.bifortress.mechanism.balancing.enemies.Enemy;
import com.mygdx.bifortress.mechanism.balancing.node.DefenderNode;
import com.mygdx.bifortress.mechanism.balancing.node.FreezeNode;
import com.mygdx.bifortress.mechanism.balancing.node.Node;
import com.mygdx.bifortress.mechanism.balancing.traversal.Traversal;

import static com.mygdx.bifortress.BiFortress.*;

public class UINavigation {
    enum PointDir{
        UP,DOWN,LEFT,RIGHT
    }
    float progress,xR,yR,initX,initY;
    int step,nextStep;
    boolean reCheck = true;
    String str;
    PointDir pointDir = PointDir.UP;
    BitmapFont font = new BitmapFont(Gdx.files.internal("ui/ui.fnt"));
    Sprite point = new Sprite(new Texture(Gdx.files.internal("point.png")));
    public UINavigation(){
        progress = 0;
        step = 0;
        nextStep = 1;
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
                nextStep = 1;
                break;
            case 1:
                xR = screenViewport.getScreenWidth()/2;
                yR = screenViewport.getScreenHeight()/2-64;
                str = "Use WASD to move";
                pointDir = PointDir.UP;
                nextStep = 2;
                break;
            case 2:
                xR = screenViewport.getScreenWidth()/2;
                yR = screenViewport.getScreenHeight()-100;
                str = "Score displays progress";
                pointDir = PointDir.UP;
                nextStep = 3;
                break;
            case 3:
                xR = 250;
                yR = 50;
                str = "This is the timer that shows the current progress";
                pointDir = PointDir.DOWN;
                nextStep = 4;
                break;
            case 4:
                xR = screenViewport.getScreenWidth()-250;
                yR = screenViewport.getScreenHeight()-100;
                str = "Right-click to change node control";
                pointDir = PointDir.UP;
                nextStep = 5;
                break;
            case 5:
                xR = screenViewport.getScreenWidth()-250;
                yR = screenViewport.getScreenHeight()-100;
                str = "With the Node Control, you can delete, rotate, or reset to default";
                pointDir = PointDir.UP;
                nextStep = 51;
                break;
            case 51:
                Vector3 eeMap = gameViewport.getCamera().project(new Vector3((Balancing.bst.root!=null)?Balancing.bst.root.x:0, (Balancing.bst.root!=null)?Balancing.bst.root.y:0, 0));
                float nodeScreenX = eeMap.x;
                float nodeScreenY = eeMap.y;

                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                Vector3 mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR = mousePos.x;
                yR = mousePos.y;
                str = "Node provides protection and info. Hover over node circle for pop-up";
                pointDir = PointDir.DOWN;
                nextStep = 52;
                break;
            case 52:
                eeMap = gameViewport.getCamera().project(new Vector3((Balancing.bst.root!=null)?Balancing.bst.root.x:0, (Balancing.bst.root!=null)?Balancing.bst.root.y:0, 0));
                nodeScreenX = eeMap.x;
                nodeScreenY = eeMap.y;

                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR = mousePos.x;
                yR = mousePos.y;
                str = "Each node requires power to operate, with the maximum power depending on the node's balancing factor. The higher the balancing factor, the less power is needed. Therefore, you must balance the tree";
                pointDir = PointDir.DOWN;
                nextStep = 6;
                break;
            case 6:
                eeMap = gameViewport.getCamera().project(new Vector3((Balancing.bst.root!=null)?Balancing.bst.root.x:0, (Balancing.bst.root!=null)?Balancing.bst.root.y:0, 0));
                nodeScreenX = eeMap.x;
                nodeScreenY = eeMap.y;

                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR = mousePos.x;
                yR = mousePos.y;
                str = "This is a Supplier Node (Green).\nIt emits power cells to supply power to all the nodes in the tree";
                pointDir = PointDir.DOWN;
                nextStep = 7;
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
                str = "This is a Defender Node (Grey).\nIt helps shoot down incoming enemy raids, protecting your tree";
                pointDir = PointDir.DOWN;
                nextStep = 50;
                break;
            case 50:
                node = null;
                for(Node n: Balancing.bst.nodes){
                    if(n.getClass() == FreezeNode.class){
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
                str = "This is a Freeze Node (Cyan).\nIt shoots snowballs to freeze incoming enemies for a short period of time";
                pointDir = PointDir.DOWN;
                nextStep = 8;
                break;
            case 8:
                xR = 250;
                yR = 50;
                str = "Now, it's node manipulation. You can delete and rotate nodes as you wish by choosing the mode in the top-right corner";
                pointDir = PointDir.DOWN;
                nextStep = 9;
                break;
            case 9:
                xR = 250;
                yR = 50;
                str = "Ready? Press READY button for invasion phase";
                pointDir = PointDir.DOWN;
                nextStep = -1;
                if(ClockPhrase.phrase.phrase == ClockPhrase.Phrase.INVASION){
                    step = 10;
                }
                break;
            case 10:
                if(reCheck){
                    xR = 250;
                    yR = 50;
                    if(!Balancing.enemies.isEmpty()){
                        reCheck = false;
                    }
                }
                else{
                    if(!Balancing.enemies.isEmpty()){
                        Enemy enemy = Balancing.enemies.first();
                        eeMap = gameViewport.getCamera().project(new Vector3(enemy.xPos,enemy.yPos, 0));
                        nodeScreenX = eeMap.x;
                        nodeScreenY = eeMap.y;

                        nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                        mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                        xR = mousePos.x;
                        yR = mousePos.y;
                    }
                    else{
                        xR = 250;
                        yR = 50;
                        step = 11;
                        reCheck = true;
                    }
                }
                str = "The enemies are coming to destroy your tree";
                pointDir = PointDir.DOWN;
                nextStep = -1;
                break;
            case 11:
                if(reCheck){
                    if(Balancing.traversal.modeTraversal == Traversal.ModeTraversal.CHOOSE){
                        reCheck = false;
                    }
                }
                else{
                    if(Balancing.traversal.modeTraversal != Traversal.ModeTraversal.SHOW){
                        step = 12;
                        reCheck = true;
                    }
                }
                eeMap = gameViewport.getCamera().project(new Vector3(Balancing.bst.root.x,Balancing.bst.root.y, 0));
                nodeScreenX = eeMap.x;
                nodeScreenY = eeMap.y;

                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR = mousePos.x;
                yR = mousePos.y;
                str = "Now it's the special event which is node traversal, Please remember the traversal sequence";
                pointDir = PointDir.DOWN;
                nextStep = -1;
                break;
            case 12:
                if(reCheck){
                    if(Balancing.traversal.modeTraversal == Traversal.ModeTraversal.CHOOSE){
                        reCheck = false;
                    }
                }
                else{
                    if(Balancing.traversal.modeTraversal == Traversal.ModeTraversal.TRUE){
                        step = 13;
                        reCheck = true;
                    }
                }
                eeMap = gameViewport.getCamera().project(new Vector3(Balancing.bst.root.x,Balancing.bst.root.y, 0));
                nodeScreenX = eeMap.x;
                nodeScreenY = eeMap.y;

                nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                xR = mousePos.x;
                yR = mousePos.y;
                str = "Now it's your turn to follow the right node traversal sequence by simply clicking the node in the right order";
                pointDir = PointDir.DOWN;
                nextStep = -1;
                break;
            case 13:
                xR = screenViewport.getScreenWidth()-250;
                yR = 40;
                str = "After you complete the node traversal, you will receive one free node.\nThis is your Node Inventory, which collects the nodes you acquire.\nSelect a node, then DRAG it to the desired position for insertion";
                reCheck = true;
                pointDir = PointDir.DOWN;
                if(Balancing.bst.onNode != null){
                    step = 16;
                }
                break;
            case 16:
                Node s16node = Balancing.bst.root;
                boolean lefts16 = false;
                for(Node node2: Balancing.bst.nodes){
                    if(s16node.value < node2.value && node2 != Balancing.bst.onNode){
                        s16node = node2;
                    }
                }
                if(s16node != null){
                    eeMap = gameViewport.getCamera().project(new Vector3(s16node.initX+ (BinarySearchTree.xyRange)*((lefts16)?-1:1),s16node.initY-BinarySearchTree.xyRange, 0));
                    nodeScreenX = eeMap.x;
                    nodeScreenY = eeMap.y;

                    nodeScreenY = Gdx.graphics.getHeight() - nodeScreenY;
                    mousePos = screenViewport.getCamera().unproject(new Vector3(nodeScreenX, nodeScreenY, 0));
                    xR = mousePos.x;
                    yR = mousePos.y;
                }
                str = "Place it here";
                pointDir = PointDir.UP;
                if(Balancing.bst.onNode == null && Balancing.inventory.itemNodes.size > 0){
                    step = 15;
                }
                else if(Balancing.bst.onNode == null){
                    step = 17;
                }
                break;
            case 17:
                xR = screenViewport.getScreenWidth()/2;
                yR = 40;
                str = "Nice, you finally understand how to play.\nGood luck!";
                reCheck = true;
                pointDir = PointDir.DOWN;
                nextStep = 18;
                break;
            case 18:
                xR = screenViewport.getScreenWidth()/2;
                yR = 40;
                str = "";
                reCheck = true;
                pointDir = PointDir.DOWN;
                nextStep = -1;
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
    public void goNextStep(){
        if(nextStep != -1){
            step = nextStep;
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
                        goNextStep();
                    }
                }
                else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                    goNextStep();
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
                        goNextStep();
                    }
                }
                else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                    goNextStep();
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
                        goNextStep();
                    }
                }
                else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                    goNextStep();
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
                        goNextStep();
                    }
                }
                else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                    goNextStep();
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
