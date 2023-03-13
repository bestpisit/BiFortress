package com.mygdx.bifortress.intro;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Pool;

import static com.mygdx.bifortress.BiFortress.*;

public class IntroBinaryTree {
    public static DelayedRemovalArray<InTroNode> nodes = new DelayedRemovalArray<>();
    public InTroNode root;
    float startX,startY;
    public final static int xyRange = 64;
    private final Pool<InTroNode> nodePool = new Pool<InTroNode>() {
        @Override
        protected InTroNode newObject() {
            return new InTroNode();
        }
    };
    BitmapFont font;
    public IntroBinaryTree(){
        this.root = null;
        for(int i=1;i<=7;i++){
            InTroNode node = nodePool.obtain();
            node.init(i,GAME_WIDTH/2,GAME_HEIGHT/2);
            insert(node);
            relocation();
        }
        font = new BitmapFont(Gdx.files.internal("Font/BerlinSans/BerlinSans.fnt"));
    }
    public void insert(InTroNode node){
        InTroNode start = this.root;
        InTroNode parent = null;
        boolean left = false;
        while(start != null){
            parent = start;
            if(node.value > start.value){
                start = start.right;
                left = false;
            }
            else{
                start = start.left;
                left = true;
            }
        }
        if(this.root == null){
            this.root = node;
        }
        else {
            if(left){
                parent.left = node;
            }
            else{
                parent.right = node;
            }
            node.parent = parent;
        }
        nodes.add(node);
    }
    public void update(){
        InTroNode node;
        int len = nodes.size;
        for (int i = len; --i >= 0;) {
            node = nodes.get(i);
            node.update();
            if (node.alive == false) {
                nodes.removeIndex(i);
                nodePool.free(node);
            }
        }
    }
    public void render(){
        InTroNode node;
        int len = nodes.size;
        for (int i = len; --i >= 0;) {
            node = nodes.get(i);
            node.render();
        }
        spriteBatch.begin();
        for (int i = len; --i >= 0;) {
            node = nodes.get(i);
            font.draw(spriteBatch,String.valueOf(node.value),node.iPosition.x-5, node.iPosition.y+12);
        }
        spriteBatch.end();
    }
    public void relocation(){
        startX = 0;
        startY = 0;
        inOrder(this.root,0);
        float del_x = 0;
        if(this.root != null){
            del_x = this.root.position.x;
        }
        startX -= 32;
        for(InTroNode node: nodes){
            node.position.x -= startX/2;
            node.position.x += GAME_WIDTH/2;
            if(node.iPosition.x == -100f){
                node.iPosition.x = node.position.x;
            }
            node.position.y += GAME_HEIGHT/2+startY/2;
            if(node.iPosition.y == -100f){
                node.iPosition.y = node.position.y;
            }
        }
    }
    void inOrder(InTroNode now,int depth){
        if(now != null){
            inOrder(now.left,depth+1);
            now.depth = depth;
            now.position.x = startX;
            now.position.y = -1.5f * depth * xyRange;
            startX += xyRange;
            if(startY < depth * xyRange){
                startY = depth * xyRange;
            }
            inOrder(now.right,depth+1);
        }
    }
    public void dispose(){
        font.dispose();
        for(InTroNode node:nodes){
            node.dispose();
        }
    }
    public void rotate(InTroNode n1,InTroNode n2){
        InTroNode p = n1;
        InTroNode c = n2;
        if(n2.left == n1 || n2.right == n1){
            p = n2;
            c = n1;
        }
        if(p.left == c){
            if(c.left == null && c.right == null){
                if(p.parent == null){
                    root = c;
                    root.parent = null;
                    root.right = p;
                    p.left = null;
                    p.parent = c;
                }
                else{
                    if(p.parent.left == p){
                        p.parent.left = c;
                        c.parent = p.parent;
                        c.right = p;
                        p.left = null;
                        //p.right = null;
                        p.parent = c;
                    }
                    else if(p.parent.right == p){
                        p.parent.right = c;
                        c.parent = p.parent;
                        c.right = p;
                        p.left = null;
                        p.parent = c;
                    }
                }
            }
            else if(c.left == null){
                p.left = c.right;
                p.left.parent = p;
                c.right = p;
                if(p.parent != null){
                    c.parent = p.parent;
                    if(p.parent.left == p){
                        p.parent.left = c;
                    }
                    else if(p.parent.right == p){
                        p.parent.right = c;
                    }
                }
                else{
                    c.parent = null;
                    root = c;
                }
                p.parent = c;
            }
            else if(c.right == null){
                c.right = p;
                p.left = null;
                if(p.parent != null){
                    c.parent = p.parent;
                    if(p.parent.left == p){
                        p.parent.left = c;
                    }
                    else if(p.parent.right == p){
                        p.parent.right = c;
                    }
                }
                else{
                    c.parent = null;
                    root = c;
                }
                p.parent = c;
            }
            else{
                p.left = c.right;
                p.left.parent = p;
                c.right = p;
                if(p.parent != null){
                    c.parent = p.parent;
                    if(p.parent.left == p){
                        p.parent.left = c;
                    }
                    else if(p.parent.right == p){
                        p.parent.right = c;
                    }
                }
                else{
                    c.parent = null;
                    root = c;
                }
                p.parent = c;
            }
        }
        else if(p.right == c){
            if(c.left == null && c.right == null){
                if(p.parent == null){
                    root = c;
                    root.parent = null;
                    root.left = p;
                    p.right = null;
                    p.parent = c;
                }
                else{
                    if(p.parent.left == p){
                        p.parent.left = c;
                        c.parent = p.parent;
                        c.left = p;
                        p.right = null;
                        p.parent = c;
                    }
                    else if(p.parent.right == p){
                        p.parent.right = c;
                        c.parent = p.parent;
                        c.left = p;
                        p.right = null;
                        p.parent = c;
                    }
                }
            }
            else if(c.left == null){
                c.left = p;
                p.right = null;
                if(p.parent != null){
                    c.parent = p.parent;
                    if(p.parent.left == p){
                        p.parent.left = c;
                    }
                    else if(p.parent.right == p){
                        p.parent.right = c;
                    }
                }
                else{
                    c.parent = null;
                    root = c;
                }
                p.parent = c;
            }
            else if(c.right == null){
                p.right = c.left;
                p.right.parent = p;
                c.left = p;
                if(p.parent != null){
                    c.parent = p.parent;
                    if(p.parent.left == p){
                        p.parent.left = c;
                    }
                    else if(p.parent.right == p){
                        p.parent.right = c;
                    }
                }
                else{
                    c.parent = null;
                    root = c;
                }
                p.parent = c;
            }
            else{
                p.right = c.left;
                p.right.parent = p;
                c.left = p;
                if(p.parent != null){
                    c.parent = p.parent;
                    if(p.parent.left == p){
                        p.parent.left = c;
                    }
                    else if(p.parent.right == p){
                        p.parent.right = c;
                    }
                }
                else{
                    c.parent = null;
                    root = c;
                }
                p.parent = c;
            }
        }
        relocation();
    }
}
