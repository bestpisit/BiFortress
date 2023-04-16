package com.mygdx.bifortress.mechanism.balancing.node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.control.MovementControl;
import com.mygdx.bifortress.mechanism.balancing.inventory.Inventory;
import com.mygdx.bifortress.mechanism.balancing.inventory.ItemNode;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class Node {
    public float x,y,radius,xR,yR;
    public Node left,right,parent;
    BitmapFont font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    public int value,depth;
    public float initRadius,initX,initY,toggleX,toggleY,toggleDistance;
    public boolean toggle,tToggle,actionToggle;
    public boolean onMouse,toggleMouse,lone;
    public BinarySearchTree origin;
    public float power,pow,initPow,level;
    public static final Color color = Color.BLACK;
    public int balanceFactor;
    public boolean wrongOrder,hideUI;
    public float uiprogress;
    public Color mainNodeColor = Color.BLACK;
    float atMe = 0,notAtMe = 0;
    public Node(int value,BinarySearchTree origin){
        this(value,0,0,origin);
    }
    public Node(int value, float x, float y, BinarySearchTree origin){
        this(value,x,y,32,origin);
    }
    public Node(int value,float x,float y,float radius,BinarySearchTree origin){
        this.origin = origin;
        this.x = x;
        this.y = y;
        this.xR = this.x + origin.xOrigin;
        this.yR = this.y + origin.yOrigin;
        this.radius = radius;
        this.value = value;
        initRadius = 1f;
        initX = -100f;
        initY = -100f;
        toggleX = -100f;
        toggleY = -100f;
        toggleDistance = 0f;
        toggle = false;
        tToggle = false;
        onMouse = false;
        this.parent = null;
        actionToggle = false;
        depth = 0;
        level = 1;
        power = level;
        pow = power;
        toggleMouse = false;
        lone = false;
        balanceFactor = 0;
        wrongOrder = false;
        hideUI = false;
        uiprogress = 0;
    }
    public void updateSelf(){

    }
    public void update(ShapeRenderer shapeRenderer,Vector3 mousePos){
        updateSelf();
        if(this.pow < 0 && this.getClass() != Node.class){
            dispose();
            origin.delete(this);
        }
        if(this.pow > this.power){
            this.pow = this.power;
        }
        if(origin!=null){
            this.xR = this.x + origin.xOrigin;
            this.yR = this.y + origin.yOrigin;
        }
        else{
            this.xR = this.x;
            this.yR = this.y;
        }
        onMouse = atMouse(mousePos);
        double distance = Math.sqrt(Math.pow(initX - xR,2)+Math.pow(initY - yR,2));
        if(distance > 1f){
            double angle = Math.atan2(yR-initY,xR-initX);
            float factor = 0.1f;
            if(lone){
                factor = 0.2f;
            }
            initX += factor*distance*Math.cos(angle);
            initY += factor*distance*Math.sin(angle);
        }
        else{
            initY = yR;
            initX = xR;
        }
        if(Math.abs(initRadius - radius)>1f){
            initRadius += (radius-initRadius+10*Math.signum(radius-initRadius))/radius;
        }
        else{
            initRadius = radius;
        }
        if(toggle){
            toggleX = mousePos.x;
            toggleY = mousePos.y;
            if(lone){
                initX = mousePos.x;
                initY = mousePos.y;
            }
            tToggle = true;
        }
        else{
            toggleDistance = (float) Math.sqrt(Math.pow(toggleX - initX,2)+Math.pow(toggleY - initY,2));
            if(lone){
                if(toggleDistance > this.radius){
                    double angle = Math.atan2(initY-toggleY,initX-toggleX);
                    float factor = 0.2f;
                    toggleX += factor*toggleDistance*Math.cos(angle);
                    toggleY += factor*toggleDistance*Math.sin(angle);
                }
                else{
                    toggleY = initY;
                    toggleX = initX;
                    tToggle = false;
                    origin.nodes.removeIndex(origin.nodes.indexOf(this,true));
                    origin.loneNodes.removeIndex(origin.loneNodes.indexOf(this,true));
                    if(origin.inventory != null){
                        ItemNode itemNode = new ItemNode(this.getClass(),value,origin.inventory);
                        origin.inventory.itemNodes.add(itemNode);
                        origin.inventory.reLocation();
                    }
                }
            }
            else{
                if(toggleDistance > 1f){
                    double angle = Math.atan2(initY-toggleY,initX-toggleX);
                    float factor = 0.2f;
                    toggleX += factor*toggleDistance*Math.cos(angle);
                    toggleY += factor*toggleDistance*Math.sin(angle);
                }
                else{
                    toggleY = initY;
                    toggleX = initX;
                    tToggle = false;
                }
            }
        }

        double initDeg = 360*initPow/power;
        double Deg = 360*pow/power;
        if(Math.abs(initDeg-Deg)>5f){
            initPow += (Deg-initDeg)*0.1f*power/360;
        }
        else{
            initPow = pow;
        }
        //ui progress
        if(origin.uinode != this){
            if(uiprogress > 0.5f){
                uiprogress -= 0.5f+uiprogress/10f;
            }
            else{
                uiprogress = 0f;
            }
        }
        else{
            if(uiprogress < 100){
                uiprogress += 0.5f+(100-uiprogress)/10f;
            }
            else{
                uiprogress = 100;
            }
        }
        if(onMouse){
            atMe += Gdx.graphics.getDeltaTime();
        }
        else{
            atMe = 0;
        }
    }
    public int findDepth(Node now,int depth){
        if(now != null){
            return Math.max(findDepth(now.left,depth+1),findDepth(now.right,depth+1));
        }
        else{
            return depth;
        }
    }
    public void drawLineConnection(ShapeRenderer shapeRenderer){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(this.parent != null){
            shapeRenderer.setColor(new Color(0,0,0,0.5f));
            if(wrongOrder){
                shapeRenderer.setColor(new Color(1,0,0,0.5f));
            }
            shapeRenderer.rectLine(this.initX,this.initY,this.parent.initX,this.parent.initY,10);
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    public void drawToggle(ShapeRenderer shapeRenderer, Vector3 mousePos){
        if(toggle){
            actionToggle = true;
        }
        if((toggle || toggleDistance > 1f)&&tToggle){
            if(actionToggle){
                boolean toggleFix = true;
                if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
                    toggleMouse = true;
                    toggleFix = false;
                }
                if(lone){
                    actionInsert(shapeRenderer, mousePos);
                }
                else{
                    switch (MovementControl.control){
                        case ROTATE:
                            actionRotate(shapeRenderer,mousePos);
                            break;
                        case DELETE:
                            actionDelete(shapeRenderer, mousePos);
                            break;
                        default:
                            //actionDefault(shapeRenderer, mousePos);
                    }
                }
                if(toggleFix){
                    toggleMouse = false;
                }
           }
        }
    }
    public void renderUI(ShapeRenderer shapeRenderer, Vector3 mousePos){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(mainNodeColor);
        shapeRenderer.circle(this.initX, this.initY, initRadius);
        if((onMouse || toggle)&&(origin.onNode == null||origin.onNode == this)){
            //shapeRenderer.setColor(Color.RED);
        }
        shapeRenderer.end();
    }
    public void render(ShapeRenderer shapeRenderer, Vector3 mousePos){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(57/255f,1,20/255f,1);
        shapeRenderer.circle(this.initX,this.initY,37*(uiprogress)/100);
//        shapeRenderer.rect(this.initX-125*(uiprogress)/100,this.initY+50,250*(uiprogress)/100,100);
//        if(this.parent == null){
//
//        }
//        else if(this.parent.right == this){
//            shapeRenderer.rect(this.initX,this.initY-30,250*(uiprogress)/100,60);
//        }
//        else if(this.parent.left == this){
//            shapeRenderer.rect(this.initX-250*(uiprogress)/100,this.initY-30,250*(uiprogress)/100,60);
//        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        renderUI(shapeRenderer, mousePos);
        spriteBatch.begin();
        if(onMouse && atMe > 0.1f){
            Balancing.nodeNavigation.node = this;
        }
        else if(Balancing.nodeNavigation.node == this && !onMouse){
            if(notAtMe < 0.5f){
                notAtMe += Gdx.graphics.getDeltaTime();
            }
            else{
                Balancing.nodeNavigation.node = null;
                notAtMe = 0;
            }
        }
        else{
            notAtMe = 0;
        }
        font.setColor(Color.WHITE);
        GlyphLayout glyphLayout = new GlyphLayout(font,String.valueOf(this.value));
        font.draw(spriteBatch,glyphLayout,this.initX-glyphLayout.width/2,this.initY+glyphLayout.height/2);
//        if(!hideUI){
//            font.draw(spriteBatch, ((this.pow-(int) this.pow==0)?(int)this.pow:this.pow) +"/"+ (int) this.power,this.initX,this.initY+25);
//            font.setColor(Color.GOLD);
//            font.draw(spriteBatch,String.valueOf(-1*Math.abs(balanceFactor)),this.initX,this.initY+50);
//        }
        spriteBatch.end();
    }
    boolean atMouse(Vector3 mousePos){
        double rad = Math.sqrt(Math.pow(this.xR - mousePos.x,2)+Math.pow(this.yR - mousePos.y,2));
        return (rad <= this.radius);
    }
    public void dispose(){
        font.dispose();
    }
    void actionDefault(ShapeRenderer shapeRenderer, Vector3 mousePos){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(160,160,160,0.5f));
        if(this.parent != null) {
            shapeRenderer.rectLine(this.toggleX, this.toggleY, this.parent.initX, this.parent.initY, 3);
        }
        if(this.left != null){
            shapeRenderer.rectLine(this.toggleX, this.toggleY, this.left.initX, this.left.initY, 3);
        }
        if(this.right != null){
            shapeRenderer.rectLine(this.toggleX, this.toggleY, this.right.initX, this.right.initY, 3);
        }
        shapeRenderer.circle(this.toggleX, this.toggleY, initRadius);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    void actionInsert(ShapeRenderer shapeRenderer, Vector3 mousePos){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(origin.root == null){
            shapeRenderer.setColor(160,160,0,0.5f);
            if(Math.sqrt(Math.pow(origin.xOrigin - mousePos.x,2)+Math.pow(origin.yOrigin- mousePos.y,2))<=20){
                shapeRenderer.setColor(160,160,0,1f);
                if(BinarySearchTree.toggleSelect && toggleMouse){
                    origin.root = this;
                    BinarySearchTree.toggleSelect = false;
                    actionToggle = false;
                    toggleMouse = false;
                    origin.loneNodes.removeIndex(origin.loneNodes.indexOf(this,true));
                    origin.reLocation();
                    lone = false;
                    this.initX = mousePos.x;
                    this.initY = mousePos.y;
                    toggleY = initY;
                    toggleX = initX;
                }
            }
            shapeRenderer.circle(origin.xOrigin, origin.yOrigin, 20);
        }
        else{
            for(Node node: origin.nodes){
                if(node != this){
                    if(node.left == null){
                        shapeRenderer.setColor(160,160,0,0.5f);
                        shapeRenderer.rectLine(node.initX,node.initY,node.initX-BinarySearchTree.xyRange/2,node.initY-BinarySearchTree.xyRange,5);
                        if(Math.sqrt(Math.pow(node.initX-BinarySearchTree.xyRange/2- mousePos.x,2)+Math.pow(node.initY-BinarySearchTree.xyRange- mousePos.y,2))<=20){
                            shapeRenderer.setColor(160,160,0,1f);
                            if(BinarySearchTree.toggleSelect && toggleMouse){
                                BinarySearchTree.toggleSelect = false;
                                actionToggle = false;
                                toggleMouse = false;
                                node.left = this;
                                this.parent = node;
                                origin.loneNodes.removeIndex(origin.loneNodes.indexOf(this,true));
                                origin.reLocation();
                                lone = false;
                                this.initX = mousePos.x;
                                this.initY = mousePos.y;
                                toggleY = initY;
                                toggleX = initX;
                            }
                        }
                        shapeRenderer.circle(node.initX-BinarySearchTree.xyRange/2,node.initY-BinarySearchTree.xyRange,20);
                    }
                    if(node.right == null){
                        shapeRenderer.setColor(160,160,0,0.5f);
                        shapeRenderer.rectLine(node.initX,node.initY,node.initX+BinarySearchTree.xyRange/2,node.initY-BinarySearchTree.xyRange,5);
                        if(Math.sqrt(Math.pow(node.initX+BinarySearchTree.xyRange/2- mousePos.x,2)+Math.pow(node.initY-BinarySearchTree.xyRange- mousePos.y,2))<=20){
                            shapeRenderer.setColor(160,160,0,1f);
                            if(BinarySearchTree.toggleSelect && toggleMouse){
                                BinarySearchTree.toggleSelect = false;
                                actionToggle = false;
                                toggleMouse = false;
                                node.right = this;
                                this.parent = node;
                                origin.loneNodes.removeIndex(origin.loneNodes.indexOf(this,true));
                                origin.reLocation();
                                lone = false;
                                this.initX = mousePos.x;
                                this.initY = mousePos.y;
                                toggleY = initY;
                                toggleX = initX;
                            }
                        }
                        shapeRenderer.circle(node.initX+BinarySearchTree.xyRange/2,node.initY-BinarySearchTree.xyRange,20);
                    }
                }
            }
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    void actionDelete(ShapeRenderer shapeRenderer, Vector3 mousePos){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(160,160,160,0.5f));
        double distance = Math.sqrt(Math.pow(this.initX - mousePos.x,2)+Math.pow(this.initY - mousePos.y,2));
        if(distance < 200f){

        }
        else{
            shapeRenderer.setColor(new Color(160,0,0,0.5f));
            if(BinarySearchTree.toggleSelect && toggleMouse){
                origin.delete(this);
                BinarySearchTree.toggleSelect = false;
                actionToggle = false;
                toggleMouse = false;
            }
        }
        shapeRenderer.rectLine(this.initX, this.initY, this.toggleX, this.toggleY, 3);
        shapeRenderer.circle(this.toggleX, this.toggleY, initRadius);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    void actionRotate(ShapeRenderer shapeRenderer, Vector3 mousePos){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(new Color(160,160,160,0.5f));
        shapeRenderer.circle(this.toggleX, this.toggleY, initRadius);
        shapeRenderer.setColor(Color.LIME);
        if(this.parent != null) {
            shapeRenderer.rectLine(this.initX, this.initY, this.parent.initX, this.parent.initY, 3);
        }
        if(this.left != null){
            shapeRenderer.rectLine(this.initX, this.initY, this.left.initX, this.left.initY, 3);
        }
        if(this.right != null){
            shapeRenderer.rectLine(this.initX, this.initY, this.right.initX, this.right.initY, 3);
        }
        float rad = 20;
        if(this.left != null){
            double distance = Math.sqrt(Math.pow(mousePos.x - this.left.initX,2)+Math.pow(mousePos.y - this.left.initY,2));
            if(distance <= rad){
                shapeRenderer.setColor(new Color(0,100,0,.8f));
            }
            else{
                shapeRenderer.setColor(new Color(0,100,0,.5f));
            }
            shapeRenderer.circle(this.left.initX,this.left.initY,rad);
            if(distance <= rad && BinarySearchTree.toggleSelect && toggleMouse){
                origin.rotate(this.left,this);
                BinarySearchTree.toggleSelect = false;
                actionToggle = false;
                toggleMouse = false;
            }
        }
        if(this.right != null){
            double distance = Math.sqrt(Math.pow(mousePos.x - this.right.initX,2)+Math.pow(mousePos.y - this.right.initY,2));
            if(distance <= rad){
                shapeRenderer.setColor(new Color(0,100,0,.8f));
            }
            else{
                shapeRenderer.setColor(new Color(0,100,0,.5f));
            }
            shapeRenderer.circle(this.right.initX,this.right.initY,rad);
            if(distance <= rad && BinarySearchTree.toggleSelect && toggleMouse){
                origin.rotate(this.right,this);
                BinarySearchTree.toggleSelect = false;
                actionToggle = false;
                toggleMouse = false;
            }
        }
        if(this.parent != null){
            double distance = Math.sqrt(Math.pow(mousePos.x - this.parent.initX,2)+Math.pow(mousePos.y - this.parent.initY,2));
            if(distance <= rad){
                shapeRenderer.setColor(new Color(0,100,0,.8f));
            }
            else{
                shapeRenderer.setColor(new Color(0,100,0,.5f));
            }
            shapeRenderer.circle(this.parent.initX,this.parent.initY,rad);
            if(distance <= rad && BinarySearchTree.toggleSelect && toggleMouse){
                origin.rotate(this,this.parent);
                BinarySearchTree.toggleSelect = false;
                actionToggle = false;
                toggleMouse = false;
            }
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    public static String toCString(){
        return "default";
    }
}
