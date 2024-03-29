package com.mygdx.bifortress.mechanism.balancing.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.node.DefenderNode;
import com.mygdx.bifortress.mechanism.balancing.node.SupplierNode;

import static com.mygdx.bifortress.BiFortress.screenViewport;

public class Inventory {
    public DelayedRemovalArray<ItemNode> itemNodes;
    public float startX,startY;
    public BinarySearchTree origin;
    public boolean allowManipulation = false;
    public int count;
    public Inventory(BinarySearchTree origin){
        this.origin = origin;
        origin.inventory = this;
        itemNodes = new DelayedRemovalArray<>();
        //getDatabase();
        startX = (screenViewport.getCamera().viewportWidth-32);
        startY = 32;
        count = 0;
        reLocation();
    }
    public void getDatabase(){
        Preferences prefs = Gdx.app.getPreferences("Database");
        if(prefs.contains("Inventory")){
            String json = prefs.getString("Inventory");
            JsonValue.JsonIterator obj = new JsonReader().parse(json).iterator();
            for(JsonValue record: obj){
                String type = record.getString("type");
                int value = record.getInt("value");
                switch (type){
                    case "supplier":
                        itemNodes.add(new ItemNode(SupplierNode.class,value,this));
                        break;
                    case "defender":
                        itemNodes.add(new ItemNode(DefenderNode.class,value,this));
                        break;
                }
            }
        }
        else{
            prefs.putString("Inventory","[]");
        }
    }
    public void update(){
        allowManipulation = origin.allowManipulation;
        if(count != itemNodes.size){
            reLocation();
            count = itemNodes.size;
        }
    }
    public void render(ShapeRenderer shapeRenderer, Vector3 mousePos){
        update();
        for(int i=0;i<itemNodes.size;i++){
            ItemNode node = itemNodes.get(i);
            node.render(shapeRenderer,mousePos);
        }
    }
    public void reLocation(){
        startX = (screenViewport.getCamera().viewportWidth-32);
        startY = 32;
        for(ItemNode node: itemNodes){
            node.x = startX;
            node.y = startY;
            startX-=64;
        }
        Preferences prefs = Gdx.app.getPreferences("Database");
        prefs.putString("Inventory",toJSON());
        prefs.flush();
    }
    public String toJSON(){
        JsonValue arr = new JsonValue(JsonValue.ValueType.array);
        for(ItemNode node: itemNodes){
            JsonValue ind = new JsonValue(JsonValue.ValueType.object);
            if(node.type == SupplierNode.class){
                ind.addChild("type",new JsonValue("supplier"));
            }
            else if(node.type == DefenderNode.class){
                ind.addChild("type",new JsonValue("defender"));
            }
            else{
                ind.addChild("type",new JsonValue("default"));
            }
            ind.addChild("value",new JsonValue(node.value));
            arr.addChild(ind);
        }
        return arr.prettyPrint(JsonWriter.OutputType.json,1);
    }
}
