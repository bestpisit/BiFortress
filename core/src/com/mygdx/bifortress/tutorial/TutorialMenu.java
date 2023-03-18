package com.mygdx.bifortress.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import static com.mygdx.bifortress.BiFortress.*;

public class TutorialMenu {
    public static BitmapFont font;
    public enum MenuStage{
        Introduction("Introduction"),
        BackgroundInformation("Background Information"),
        BinaryTree("BinaryTree"),
        Practice("Practice"),
        WhyBinaryTree("Why\nBinaryTree"),
        GameBST("Game\nBinarySearchTree"),
        SummarizeBST("Summarize\nBST")
        ;
        public final String title;

        MenuStage(String title) {
            this.title = title;
        }
    }
    public static ArrayList<MenuStage> menuStages = new ArrayList<>();
    public static MenuStage currentStage = null;
    public TutorialMenu(){
        init();
        font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    }
    public void init(){
        menuStages.add(MenuStage.Introduction);
        menuStages.add(MenuStage.BackgroundInformation);
        menuStages.add(MenuStage.BinaryTree);
        menuStages.add(MenuStage.Practice);
        menuStages.add(MenuStage.WhyBinaryTree);
        menuStages.add(MenuStage.GameBST);
        menuStages.add(MenuStage.SummarizeBST);
    }
    public void update(){

    }
    public void render(ShapeRenderer shapeRenderer){
        Vector3 mousePos = screenViewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        int width = 200;
        int height = 150;
        int gap = 60;
        int row = 4;
        int col = 4;
        float xPos = screenViewport.getScreenWidth()/2-(width*col+gap*(col-1))/2;
        float yPos = screenViewport.getScreenHeight()/2-(height*row+gap*(row-1))/2;
        for(int j=3;j>=0;j--){
            for(int i=0;i<4;i++){
                if(i+(3-j)*4+1 <= menuStages.size()){
                    shapeRenderer.setColor(Color.BLACK);
                    int i1 = ((i - 1 >= -1) ? i - 1 : 0) * gap;
                    int j1 = ((j-1>=-1)?j-1:0)*gap;
                    if(new Rectangle((int)xPos+gap+i*width+ i1,(int)yPos+gap+j*height+j1,width,height).contains(mousePos.x,mousePos.y)){
                        shapeRenderer.setColor(Color.RED);
                        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                            setMenuStages(menuStages.get(i+(3-j)*4));
                        }
                    }
                    shapeRenderer.rect(xPos+gap+i*width+ i1,yPos+gap+j*height+j1,width,height);
                }
            }
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        spriteBatch.begin();
        for(int j=3;j>=0;j--){
            for(int i=0;i<4;i++){
                int index = i+(3-j)*4;
                if(index+1 <= menuStages.size()){
                    font.draw(spriteBatch, (index + 1)+"\n" +menuStages.get(index).title,xPos+gap+i*width+((i-1>=-1)?i-1:0)*gap,yPos+gap+j*height+((j-1>=-1)?j-1:0)*gap+height,width,1,true);
                }
            }
        }
        spriteBatch.end();
    }
    public void setMenuStages(MenuStage menuStage){
        TutorialController tutorialController = Tutorial.tutorialController;
        tutorialController.reset();
        switch (menuStage){
            case Introduction:
                tutorialController.addConversation("Good days student, Im Ninja Frog.\nIm here to help you to dive into this fascinating world, and discover the power of Binary Tree");
                tutorialController.addConversation("Welcome To BiFortress game");
                tutorialController.addConversation("Before we go let me introduce the story of the BiFortress");
                tutorialController.addConversation("Once, there was a ancient kingdom called Celestial Imperium, ruled by the Elm Tree, which was the center of power in the kingdom");
                tutorialController.addConversation("But one day, a destructive demon named Infernous and its minions rose up to destroy the kingdom's foundation and create their own evil empire");
                tutorialController.addConversation("Ninja Frog, the protector of the rightful kingdom, had to fight and defend against the never-ending threat that plagued the city");
                break;
            case BackgroundInformation:
                tutorialController.addConversation("The Background Information About BiFortress");
                tutorialController.addConversation("BiFortress is ....");
                break;
            case BinaryTree:
                tutorialController.addConversation("This is your BinaryTree Fortress");
                tutorialController.addConversation("BinaryTree is a data structure that represent as an upside down tree,\n" +
                        "The tree may containing the node(Circle), which represent the data inside of it.\n" +
                        "each node may contains up to 2 children nodes which is left node and right node");
                tutorialController.addConversation("The left node (1) must has the lesser value than its parent node (2)");
                tutorialController.addConversation("The right node (3) must has the greater value than its parent node (2)");
                tutorialController.addConversation("Each Binary Tree must has its own Root node (2) which is the node where the Binary Operated first");
                tutorialController.addConversation("Binary Tree has Three Main Node Manipulation which is\n-Insertion\n-Deletion\n-Rotation");
                tutorialController.addConversation("About Insertion, lets insert the value of (4)\n" +
                        "The insertion start from seeing the root node(2)");
                tutorialController.addConversation("if (4) is greater than (2) then go RIGHT, else go left");
                tutorialController.addConversation("Now we are at node (3), if (4) is greater than (3) then go RIGHT, else go left");
                tutorialController.addConversation("No more node to go then add (4) to the right of the node (3)");//<---10
                tutorialController.addConversation("Yay! we are finishing the node insertion!");
                tutorialController.addConversation("Next is Deletion, lets delete the node (1)");//<---12
                tutorialController.addConversation("Node (1) is deleted Yay!");
                tutorialController.addConversation("Next is Rotation, lets rotate the node (3) with node (2)");//<---14
                tutorialController.addConversation("Rotated! nice and the tree seems to be more balance!");//<---15
                tutorialController.addConversation("So lets do some practice exercise!");
                break;
            case Practice:
                tutorialController.addConversation("Welcome To Binary Tree Practicing");
                tutorialController.addConversation("In the right bottom, you will given a nodes which you can drag and insert it into the binary tree");
                tutorialController.addConversation("Now its your time to add all the given node in to the tree,\n" +
                        "Right click to change node control\n" +
                        "Please remember the binary tree restriction:\n" +
                        "-Left node must be lesser than the parent node\n" +
                        "-Right node must be greater than the parent node",false);
                Tutorial.tutorialBSTPractice.init();
                tutorialController.addConversation("Good job you have done inserting all the node");
                break;
            case WhyBinaryTree:
                tutorialController.addConversation("As you have done the binary tree practice, you would have a questions \n-Why we need a binary tree\n" +
                        "Why performing insertion, deletion and rotation");
                tutorialController.addConversation("Binary Tree is a powerful DataStructure that can improve the efficiency of the software\n" +
                        "by performing a faster data searching and retrieval of the data record\n" +
                        "that can be used for Database Indexing, Spell Checking, File System Organization and etc.");
                tutorialController.addConversation("That sounds to be hard to be understood");
                tutorialController.addConversation("Let me visualize the binary tree applications by introducing Binary Search Game");
                break;
            case GameBST:
                tutorialController.addConversation("Welcome to the Binary Search Game, Lets start");
                tutorialController.addConversation("I have choose one number between number 1 to 15,\n" +
                        "Your task is to guess what is my chosen number\n" +
                        "After your guessing, I will provide the answer whether its the answer or not\n" +
                        "If the answer is wrong, I will told you the hint whether the chosen number is MORE or LESSER");
                tutorialController.addConversation("Now Guess My Number!",false);
                tutorialController.addConversation("Theres a method that can help you getting at most 3 guessing!");
                tutorialController.addConversation("Let me change the structure a bit,\n" +
                        "Wow it is a Binary Tree\n" +
                        "Or we can call it a Binary Search Tree");
                tutorialController.addConversation("Now lets try guessing the number from the root node,\n" +
                        "then follow the path down to the tree",false);//<--- 6
                tutorialController.addConversation("Now you will know that Binary Search Tree can improve the efficiency of searching for specific data");
                tutorialController.addConversation("But the tree have to be !Balance! inorder to effectively searching for data");
                break;
            case SummarizeBST:
                tutorialController.addConversation("How to make the tree balance");
                tutorialController.addConversation("We use Algorithms");
                tutorialController.addConversation("What is Algorithms");
                tutorialController.addConversation("Algorithms is a procedure for solving problem or performing a computation");
                tutorialController.addConversation("For Balancing the tree you can use these following algorithms\n" +
                        "-DSW Algorithms\n" +
                        "-AVL Binary Tree Algorithms");
                break;
        }
        tutorialController.nextConversation(false);
        currentStage = menuStage;
        Tutorial.tutorialState = Tutorial.TutorialState.TUTORIAL;
    }
    public void nextStage(){
        int index = menuStages.indexOf(currentStage);
        if(index+1<menuStages.size()){
            setMenuStages(menuStages.get(index+1));
        }
    }
    public void previousStage(){
        int index = menuStages.indexOf(currentStage);
        if(index-1>=0){
            setMenuStages(menuStages.get(index-1));
        }
    }
    public void dispose(){
        font.dispose();
    }
}
