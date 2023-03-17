package com.mygdx.bifortress.mechanism.balancing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.bifortress.mechanism.balancing.cell.PowerCell;
import com.mygdx.bifortress.mechanism.balancing.control.ClockPhrase;
import com.mygdx.bifortress.mechanism.balancing.node.DefenderNode;
import com.mygdx.bifortress.mechanism.balancing.node.Node;
import com.mygdx.bifortress.mechanism.balancing.node.SupplierNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static com.mygdx.bifortress.BiFortress.*;

public class BinarySearchTree {
    float startX;
    public Node root;
    public DelayedRemovalArray<Node> nodes,loneNodes;
    public DelayedRemovalArray<PowerCell> cells;
    Random rand = new Random(System.currentTimeMillis());
    public final static int xyRange = 64;
    public static Node onNode;
    public float xOrigin,yOrigin;
    public static boolean toggleSelect;
    public float TreeTick;
    BitmapFont text;
    public BinarySearchTree(){
        nodes = new DelayedRemovalArray<>();
        loneNodes = new DelayedRemovalArray<>();
        cells = new DelayedRemovalArray<>();
        this.root = null;
        insert(new SupplierNode(1,this));
        //getDatabase();
        xOrigin = 0;
        yOrigin = 0;
        toggleSelect = true;
        TreeTick = 0;
        text = new BitmapFont();
    }
    public void getDatabase(){
        Preferences prefs = Gdx.app.getPreferences("Database");
        if(prefs.contains("BinaryTree")){
            String json = prefs.getString("BinaryTree");
            JsonValue.JsonIterator obj = new JsonReader().parse(json).iterator();
            for(JsonValue record: obj){
                String type = record.getString("type");
                int value = record.getInt("value");
                switch (type){
                    case "supplier":
                        insert(new SupplierNode(value,this));
                        break;
                    case "defender":
                        insert(new DefenderNode(value,this));
                        break;
                    default:
                        insert(new Node(value,this));
                }
            }
            reLocation();
        }
        else{
            prefs.putString("BinaryTree","[]");
        }
    }
    public void insert(int val){
        Node node = new Node(val,this);
        Node start = this.root;
        Node parent = null;
        boolean left = false;
        while(start != null){
            parent = start;
            if(val > start.value){
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
    public void insert(Node node){
        Node start = this.root;
        Node parent = null;
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
    public void delete(Node node){
//        if(node == root && node.left == null && node.right == null){
//            return;
//        }
        Node left = rightMost(node.left);
        Node right = leftMost(node.right);
        if(left == null && right == null){
            if(node.parent != null){
                if(node.parent.left == node){
                    node.parent.left = null;
                }
                else{
                    node.parent.right = null;
                }
            }
            else if(node == root){
                root = null;
            }
        }
        else if(left != null){
            if(left.parent == node){
                if(node.parent != null){
                    if(node.parent.left == node){
                        node.parent.left = left;
                    }
                    else{
                        node.parent.right = left;
                    }
                    left.parent = node.parent;
                }
                else if(node == root){
                    root = left;
                    left.parent = null;
                }
                if(node.right != null){
                    node.right.parent = left;
                    left.right = node.right;
                }
            }
            else{
                if(left.left != null){
                    left.parent.right = left.left;
                    left.left.parent = left.parent;
                }
                else{
                    left.parent.right = null;
                }
                if(node.parent != null){
                    if(node.parent.left == node){
                        node.parent.left = left;
                    }
                    else{
                        node.parent.right = left;
                    }
                    left.parent = node.parent;
                }
                else if(node == root){
                    root = left;
                    left.parent = null;
                }
                if(node.right != null){
                    node.right.parent = left;
                    left.right = node.right;
                }
                if(node.left != null){
                    node.left.parent = left;
                    left.left = node.left;
                }
            }
        }
        else {
            if(right.parent == node){
                if(node.parent != null){
                    if(node.parent.left == node){
                        node.parent.left = right;
                    }
                    else{
                        node.parent.right = right;
                    }
                    right.parent = node.parent;
                }
                else if(node == root){
                    root = right;
                    right.parent = null;
                }
            }
            else{
                if(right.right != null){
                    right.parent.left = right.right;
                    right.right.parent = right.parent;
                }
                else{
                    right.parent.left = null;
                }
                if(node.parent != null){
                    if(node.parent.left == node){
                        node.parent.left = right;
                    }
                    else{
                        node.parent.right = right;
                    }
                    right.parent = node.parent;
                }
                else if(node == root){
                    root = right;
                    right.parent = null;
                }
                if(node.right != null){
                    node.right.parent = right;
                    right.right = node.right;
                }
            }
        }
        if(node.pow < 0){
            nodes.removeIndex(nodes.indexOf(node,true));
        }
        else{
            node.left = null;
            node.right = null;
            node.parent = null;
        }
        reLocation();
    }
    public void rotate(Node n1,Node n2){
        if(n1 == null || n2 == null){
            return;
        }
        Node p = n1;
        Node c = n2;
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
        reLocation();
    }
    Node rightMost(Node now){
        if(now != null){
            if(now.right == null){
                return now;
            }
            else{
                return rightMost(now.right);
            }
        }
        else{
            return null;
        }
    }
    Node leftMost(Node now){
        if(now != null){
            if(now.left == null){
                return now;
            }
            else{
                return leftMost(now.left);
            }
        }
        else{
            return null;
        }
    }
    void inOrder(Node now,int depth){
        if(now != null){
            inOrder(now.left,depth+1);
            now.depth = depth;
            now.x = startX;
            now.y = -1 * depth * xyRange;
            startX += xyRange;
            inOrder(now.right,depth+1);
        }
    }
    public void update(){
        for(Node node: loneNodes){
            node.x = Balancing.camX + (gameViewport.getCamera().viewportWidth/2+64) *Balancing.gameZoom;
            node.y = Balancing.camY - (gameViewport.getCamera().viewportHeight/2)*Balancing.gameZoom;
        }
        //tree tick
        TreeTick += Gdx.graphics.getDeltaTime();
        //check on node
        boolean hasToggle = false;
        for(int i=0;i<nodes.size;i++){
            Node node = nodes.get(i);
            if(loneNodes.contains(node,true)){
                node.lone = true;
            }
            else{
                node.lone = false;
            }
            if(onNode != node){
                node.toggle = false;
                if(onNode != null){
                    node.tToggle = false;
                }
            }
            if(node.onMouse && onNode==null){
                if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && toggleSelect){
                    onNode = node;
                    onNode.toggle = true;
                    toggleSelect = false;
                }
                else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && toggleSelect){
                    toggleSelect = false;
                }
            }
            if(node.toggle){
                hasToggle = true;
            }
        }
        if(!hasToggle){
            toggleSelect = false;
        }
        if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT)&&!Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            toggleSelect = true;
        }
        if(ClockPhrase.phrase.phrase != ClockPhrase.Phrase.MANIPULATION){
            if(onNode != null){
                onNode.toggle = false;
                onNode = null;
            }
        }
        if(onNode!=null && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            onNode.toggle = false;
            onNode = null;
        }
    }
    public void render(Vector3 mousePos,ShapeRenderer shapeRenderer){
        update();
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        for(Node node: nodes){
            node.update(shapeRenderer,mousePos);
            if(node != root && node.left == null && node.right == null && node.parent == null && !loneNodes.contains(node,true)){
                loneNodes.add(node);
            }
        }
        for(Node node: nodes){
            node.drawLineConnection(shapeRenderer);
        }
        for(PowerCell pc: cells){
            pc.render(shapeRenderer);
        }
        for(Node node: nodes){
            node.render(shapeRenderer,mousePos);
        }
        if(ClockPhrase.phrase.phrase == ClockPhrase.Phrase.MANIPULATION){
            for(int i=0;i<nodes.size;i++){
                Node node = nodes.get(i);
                node.drawToggle(shapeRenderer,mousePos);
            }
        }
    }
    int maxSubValue(Node now){
        if(now != null){
            int maxC = 0;
            if(now.left != null){
                int maxL = maxSubValue(now.left);
                if(maxC < maxL){
                    maxC = maxL;
                }
            }
            if(now.right != null){
                int maxR = maxSubValue(now.right);
                if(maxC < maxR){
                    maxC = maxR;
                }
            }
            if(now.value > maxC){
                return now.value;
            }
            return maxC;
        }
        return 0;
    }
    int minSubValue(Node now){
        if(now != null){
            int maxC = -1;
            if(now.left != null){
                int maxL = minSubValue(now.left);
                if(maxC > maxL || maxC == -1){
                    maxC = maxL;
                }
            }
            if(now.right != null){
                int maxR = minSubValue(now.right);
                if(maxC > maxR || maxC == -1){
                    maxC = maxR;
                }
            }
            if(now.value < maxC || maxC == -1){
                return now.value;
            }
            return maxC;
        }
        return -1;
    }
    public void reLocation(){
        startX = xOrigin;
        inOrder(this.root,0);
        float del_x = 0;
        if(this.root != null){
            del_x = this.root.x;
        }
        for(Node node: nodes){
            node.x -= del_x;
            if(node.initX == -100f){
                node.initX = node.x;
            }
            node.y += yOrigin;
            if(node.initY == -100f){
                node.initY = node.y;
            }
        }
        //calculate power
        float sumPower = 0;
        for(Node node:nodes){
            //bst requirement rule
            Integer max = maxSubValue(node);
            Integer min = minSubValue(node);
            node.wrongOrder = false;
            if(node.parent != null && (node.parent.wrongOrder||(node.parent.left == node && node.parent.value < max) || (node.parent.right == node && node.parent.value > min && min != -1))){
                node.power = 0;
                node.wrongOrder = true;
                continue;
            }
            int leftDepth = node.findDepth(node.left,1);
            int rightDepth = node.findDepth(node.right,1);
            node.balanceFactor = leftDepth - rightDepth;
            int reductionDepth = Math.abs(leftDepth-rightDepth);
            if(reductionDepth <= 1){
                reductionDepth = 0;
            }
            if(node != root){
                node.power = node.depth - reductionDepth;
                if(node.power < 0){
                    node.power = 0;
                }
                sumPower += node.power;
            }
            else{
                node.power = 0;
            }
            node.power += node.level;
        }
        if(root != null){
            int leftDepth = root.findDepth(root.left,1);
            int rightDepth = root.findDepth(root.right,1);
            root.balanceFactor = leftDepth - rightDepth;
            int reductionDepth = Math.abs(leftDepth-rightDepth);
            if(reductionDepth <= 1){
                reductionDepth = 0;
            }
            root.power = sumPower - reductionDepth;
            root.power += root.level;
        }
        for(PowerCell pc:cells){
            pc.path.clear();
            if(pc.dest != null){
                pc.x = pc.dest.x;
                pc.y = pc.dest.y;
                pc.hamiltonian(pc.dest,null);
            }
        }
        Preferences prefs = Gdx.app.getPreferences("Database");
        prefs.putString("BinaryTree",toJSON());
        prefs.flush();
    }
    void generateTree(ArrayList<Integer> arr){
        for(Integer i: arr){
            int rDel = rand.nextInt(2);
            if(rDel == 0){
                insert(new SupplierNode(i,this));
            }
            else{
                insert(new DefenderNode(i,this));
            }
        }
        reLocation();
    }
    public String toJSON(){
        JsonValue arr = new JsonValue(JsonValue.ValueType.array);
        Queue<Node> queue = new LinkedList<>();
        if(this.root != null){
            queue.add(this.root);
        }
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            JsonValue ind = new JsonValue(JsonValue.ValueType.object);
            if(current.getClass() == SupplierNode.class){
                ind.addChild("type",new JsonValue("supplier"));
            }
            else if(current.getClass() == DefenderNode.class){
                ind.addChild("type",new JsonValue("defender"));
            }
            else{
                ind.addChild("type",new JsonValue("default"));
            }
            ind.addChild("value",new JsonValue(current.value));
            arr.addChild(ind);

            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
        return arr.prettyPrint(JsonWriter.OutputType.json,1);
    }
}
