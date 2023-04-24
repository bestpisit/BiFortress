package com.mygdx.bifortress.mechanism.balancing.traversal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Queue;
import com.mygdx.bifortress.mechanism.balancing.Balancing;
import com.mygdx.bifortress.mechanism.balancing.control.ClockPhrase;
import com.mygdx.bifortress.mechanism.balancing.inventory.ItemNode;
import com.mygdx.bifortress.mechanism.balancing.node.DefenderNode;
import com.mygdx.bifortress.mechanism.balancing.node.Node;
import com.mygdx.bifortress.mechanism.balancing.node.SupplierNode;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.bifortress.BiFortress.spriteBatch;

public class Traversal {
    ArrayList<Node> nodeSequence,answerSequence,chooseSequence;
    BitmapFont font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    public static Sound beepSound = Gdx.audio.newSound(Gdx.files.internal("sounds/beep.mp3"));
    public static Sound showSound = Gdx.audio.newSound(Gdx.files.internal("sounds/show.mp3"));
    public static Sound chooseSound = Gdx.audio.newSound(Gdx.files.internal("sounds/choose.mp3"));
    public static Sound trueSound = Gdx.audio.newSound(Gdx.files.internal("sounds/true.mp3"));
    public static Sound falseSound = Gdx.audio.newSound(Gdx.files.internal("sounds/false.mp3"));
    Node currentNode=null;
    boolean soundPlay = false;
    float time=0;
    public enum TraversalType{
        BF,Inorder,Preorder,Postorder
    }
    public enum ModeTraversal{
        SHOW,FAIL,TRUE,BEEP,CHOOSE
    }
    public TraversalType traversalType;
    public ModeTraversal modeTraversal;
    public Traversal(){
        nodeSequence = new ArrayList<>();
        answerSequence = new ArrayList<>();
        chooseSequence = new ArrayList<>();
        traversalType = TraversalType.BF;
        modeTraversal = ModeTraversal.BEEP;
    }
    public void update(){
        if(ClockPhrase.phrase.phrase == ClockPhrase.Phrase.TRAVERSAL){

        }
    }
    public void generateSequence(){
        nodeSequence.clear();
        switch (traversalType){
            case BF:
                breadthFirst();
                break;
            case Inorder:
                inorder(Balancing.bst.root);
                break;
            case Preorder:
                preorder(Balancing.bst.root);
                break;
            case Postorder:
                postorder(Balancing.bst.root);
                break;
        }
    }
    public void breadthFirst(){
        Queue<Node> nodes = new Queue<>();
        nodes.addLast(Balancing.bst.root);
        while(!nodes.isEmpty()){
            Node now = nodes.removeFirst();
            nodeSequence.add(now);
            if(now.left != null){
                nodes.addLast(now.left);
            }
            if(now.right != null){
                nodes.addLast(now.right);
            }
        }
    }
    public void inorder(Node now){
        if(now != null){
            inorder(now.left);
            nodeSequence.add(now);
            inorder(now.right);
        }
    }
    public void preorder(Node now){
        if(now != null){
            nodeSequence.add(now);
            preorder(now.left);
            preorder(now.right);
        }
    }
    public void postorder(Node now){
        if(now != null){
            postorder(now.left);
            postorder(now.right);
            nodeSequence.add(now);
        }
    }
    public void render(ShapeRenderer shapeRenderer, Vector3 mousePos){
        if(ClockPhrase.phrase.phrase == ClockPhrase.Phrase.TRAVERSAL){
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            switch (modeTraversal){
                case BEEP:
                    time += Gdx.graphics.getDeltaTime();
                    if(time < 0.5f){
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.WHITE);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                        if(!soundPlay){
                            beepSound.play();
                            soundPlay = true;
                        }
                    }
                    else if(time < 1f){
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.BLACK);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                        soundPlay = false;
                    }
                    else if(time < 1.5f){
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.WHITE);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                        if(!soundPlay){
                            beepSound.play();
                            soundPlay = true;
                        }
                    }
                    else if(time < 2f){
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.BLACK);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                        soundPlay = false;
                    }
                    else if(time < 2.5f){
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.WHITE);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                        if(!soundPlay){
                            beepSound.play();
                            soundPlay = true;
                        }
                    }
                    else if(time < 3f){
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.BLACK);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                    }
                    else{
                        time = 0;
                        soundPlay = false;
                        generateSequence();
                        modeTraversal = ModeTraversal.SHOW;
                        answerSequence.clear();
                        for(Node node: nodeSequence){
                            answerSequence.add(node);
                        }
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.BLACK);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                    }
                    break;
                case SHOW:
                    if(time < 0.5f){
                        time += Gdx.graphics.getDeltaTime();
                    }
                    else{
                        if(nodeSequence.isEmpty()){
                            currentNode = null;
                            modeTraversal = ModeTraversal.CHOOSE;
                            chooseSequence.clear();
                            soundPlay = false;
                            time = 0;
                        }
                        else{
                            showSound.play();
                            currentNode = nodeSequence.remove(0);
                        }
                        time = 0;
                    }
                    for(Node node: Balancing.bst.nodes){
                        shapeRenderer.setColor(0,0,0,1);
                        if(node == currentNode){
                            shapeRenderer.setColor(Color.WHITE);
                        }
                        shapeRenderer.circle(node.initX,node.initY,37);
                    }
                    break;
                case CHOOSE:
                    for(Node node: Balancing.bst.nodes){
                        shapeRenderer.setColor(0,0,0,1);
                        if(node.onMouse && !chooseSequence.contains(node)){
                            shapeRenderer.setColor(Color.LIME);
                            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                                chooseSequence.add(node);
                                chooseSound.play();
                            }
                        }
                        else if(chooseSequence.contains(node)){
                            shapeRenderer.setColor(Color.GREEN);
                        }
                        shapeRenderer.circle(node.initX,node.initY,37);
                    }
                    //check
                    boolean pass = true;
                    for(int i=0;i<chooseSequence.size();i++){
                        if(chooseSequence.get(i) != answerSequence.get(i)){
                            chooseSequence.clear();
                            modeTraversal = ModeTraversal.FAIL;
                            soundPlay = false;
                            time = 0;
                            pass = false;
                        }
                    }
                    if(pass && chooseSequence.size() == answerSequence.size()){
                        modeTraversal = ModeTraversal.TRUE;
                        soundPlay = false;
                        chooseSequence.clear();
                        answerSequence.clear();
                        nodeSequence.clear();
                        time = 0;
                    }
                    break;
                case FAIL:
                    if(!soundPlay){
                        soundPlay = true;
                        falseSound.play();
                    }
                    if(time < 1f){
                        time += Gdx.graphics.getDeltaTime();
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.RED);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                    }
                    else{
                        time = 0;
                        generateSequence();
                        modeTraversal = ModeTraversal.SHOW;
                        soundPlay = false;
                        answerSequence.clear();
                        for(Node node: nodeSequence){
                            answerSequence.add(node);
                        }
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.BLACK);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                    }
                    break;
                case TRUE:
                    if(!soundPlay){
                        soundPlay = true;
                        trueSound.play();
                    }
                    if(time < 1f){
                        time += Gdx.graphics.getDeltaTime();
                        for(Node node: Balancing.bst.nodes){
                            shapeRenderer.setColor(Color.LIME);
                            shapeRenderer.circle(node.initX,node.initY,37);
                        }
                    }
                    else{
                        //get free node
                        Random rand = new Random();
                        int r = rand.nextInt(100);
                        boolean isNode = rand.nextBoolean();
                        Balancing.bst.inventory.itemNodes.add(new ItemNode((isNode)? DefenderNode.class: SupplierNode.class,r,Balancing.bst.inventory));
                        Balancing.bst.inventory.reLocation();
                        soundPlay = false;
                        //end get free node
                        time = 0;
                        answerSequence.clear();
                        nodeSequence.clear();
                        chooseSequence.clear();
                        ClockPhrase.getNewPhrase();
                    }
                    break;
            }
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            spriteBatch.begin();
            for(Node node: Balancing.bst.nodes){
                font.setColor(Color.WHITE);
                GlyphLayout glyphLayout = new GlyphLayout(font,String.valueOf(node.value));
                font.draw(spriteBatch,glyphLayout,node.initX-glyphLayout.width/2,node.initY+glyphLayout.height/2);
            }
            spriteBatch.end();
        }
    }
    public void dispose(){
        beepSound.dispose();
        showSound.dispose();
        chooseSound.dispose();
        trueSound.dispose();
        falseSound.dispose();
    }
}
