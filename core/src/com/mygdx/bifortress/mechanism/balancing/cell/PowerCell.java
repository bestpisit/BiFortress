package com.mygdx.bifortress.mechanism.balancing.cell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.bifortress.mechanism.balancing.BinarySearchTree;
import com.mygdx.bifortress.mechanism.balancing.node.Node;

import java.util.ArrayList;

public class PowerCell {
    public float x,y;
    public Node dest,a,b;
    public ArrayList<Node> path;
    BinarySearchTree origin;
    public int speed;
    public PowerCell(float x,float y,int speed,Node bef,BinarySearchTree origin){
        this.x = x;
        this.y = y;
        this.dest = null;
        this.speed = speed;
        this.origin = origin;
        path = new ArrayList<>();
        hamiltonian(bef,null);
        if(path.size()>0){
            dest = path.get(0);
            path.remove(0);
        }
        else{
            dest = null;
        }
//        path.add(bef);
//        this.a = bef;
//        dest = choosePath(bef);
//        this.b = dest;
//        path.add(dest);
    }
    public void update(){
        if(dest != null && !origin.loneNodes.contains(dest,true)){
            float toggleDistance = (float) Math.sqrt(Math.pow(dest.x - x,2)+Math.pow(dest.y - y,2));
            if(toggleDistance > this.speed){
                double angle = Math.atan2(dest.y - y,dest.x - x);
                x += this.speed*Math.cos(angle);
                y += this.speed*Math.sin(angle);
            }
            else{
                if(dest.pow < dest.power){
                    dest.pow++;
                    origin.cells.removeIndex(origin.cells.indexOf(this,true));
                }
                else{
                    if(path.size()>0){
                        dest = path.get(0);
                        path.remove(0);
                    }
                    else{
                        origin.cells.removeIndex(origin.cells.indexOf(this,true));
                        dest = null;
                    }
                }
            }
        }
        else{
            origin.cells.removeIndex(origin.cells.indexOf(this,true));
        }
    }
    public void hamiltonian(Node now,Node bef){
        if(bef != now.left && now.left != null){
            path.add(now.left);
            hamiltonian(now.left,now);
            path.add(now);
        }
        if(bef != now.right && now.right != null){
            path.add(now.right);
            hamiltonian(now.right,now);
            path.add(now);
        }
        if(bef != now.parent && now.parent != null){
            path.add(now.parent);
            hamiltonian(now.parent,now);
            path.add(now);
        }
    }
    Node choosePath(Node now){
        if(now.left != null && !path.contains(now.left)){
            return now.left;
        }
        if(now.right != null && !path.contains(now.right)){
            return now.right;
        }
        if(now.parent != null && !path.contains(now.parent)){
            return now.parent;
        }
        if(now.left != null){
            return now.left;
        }
        if(now.right != null){
            return now.right;
        }
        if(now.parent != null){
            return now.parent;
        }
        return null;
    }
    public void render(ShapeRenderer shapeRenderer){
        update();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(x,y,5);
        shapeRenderer.end();
    }
}
