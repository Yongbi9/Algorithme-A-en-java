package projetia0;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alain
 */
public class Noeud {
    
    private int x;
    private int y;
    private int val;
            
    private double cout_g;
    private double cout_h;
    private double cout_f;
    private Noeud parent;
    
    public Noeud(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public void setVal(int val){
        this.val = val;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getVal(){
        return this.val;
    }
    
    public double getCout_g(){
        return cout_g;
    }
    
    public double getCout_h(){
        return cout_h;
    }
    
    public double getCout_f(){
        return cout_f;
    }
    
    public Noeud getParent(){
        return parent;
    }
    
    public void setCout_g(double cout_g){
        this.cout_g = cout_g;
    }
    
    public void setCout_h(double cout_h){
        this.cout_h = cout_h;
    }
    
    public void setCout_f(double cout_f){
        this.cout_f = cout_f;
    }
    
    public void setParent(Noeud parent){
        this.parent = parent;
    }
}
