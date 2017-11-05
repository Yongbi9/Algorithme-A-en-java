package projetia0;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Labyrinthe {
    private Noeud[][] graphe ;//Notre graphe sous forme de matrice grille)
    private int width; //largeur de la grille
    private int height; //hauteur de la grille
    
    private ArrayList<Noeud> listeOuverts; //liste OPEN
    private ArrayList<Noeud> listeFermees; //liste CLOSE
    private Noeud arrivee ;
    private Noeud depart;
    
    public Labyrinthe(){
        listeOuverts = new ArrayList<>();
        listeFermees = new ArrayList<>();
        remplirGraphe("test.txt");
        System.out.println("answer "+starAlgorithme());
    }
    
    private boolean starAlgorithme(){
        depart = updateNoeud(depart, distance(depart, arrivee), distance(depart, arrivee), 0, null);
        listeOuverts.add(depart);
        boolean succes = false;
        
        System.out.println();
        
        while(listeOuverts.size() > 0 && !succes){
            Noeud n = meilleurNoeud(listeOuverts);
            
            System.out.println("choix : ("+n.getX()+","+n.getY()+")");
            if(isFinal(n)){
                succes = true;
            }
            else{
                addOpenToClosed(n);
                
                ArrayList<Noeud> successeurs = ajoutAdjacences(n);
                for(int i = 0; i < successeurs.size(); i++){
                    Noeud s = successeurs.get(i);
                    
                    if(!isPresent(listeOuverts, s) && !(isPresent(listeFermees, s))){
                        double h = distance(s, arrivee);
                        double g = n.getCout_g()+distance(n, s);
                        s = updateNoeud(s, h + g, g, h, n);
                        
                        listeOuverts.add(s);
                        for(int j = 0; j < listeOuverts.size(); j++){
                            System.out.println("("+listeOuverts.get(j).getX()+","+listeOuverts.get(j).getY()+")");
                        }
                    }
                    else{
                        if(s.getCout_g() > (n.getCout_g()+distance(n, s))){
                            s = updateNoeud(s, s.getCout_f(), n.getCout_g()+distance(n, s), s.getCout_h(), n);
                            
                            if(isPresent(listeFermees, s)){
                                listeOuverts.add(s);
                                listeFermees.remove(s);
                            }
                            for(int j = 0; j < listeOuverts.size(); j++){
                                System.out.println("("+listeOuverts.get(j).getX()+","+listeOuverts.get(j).getY()+")");
                            }
                        }
                    }
                }
            }
        }
        return succes;
    }
    
    //Pour une seule sortie
    private boolean isFinal(Noeud n){
        if(n.getX() == arrivee.getX() && n.getY() == arrivee.getY()){
            return true;
        }
        return false;
    }
   
    //Reperation des noeuds et ajout si possible à la liste ouverte
    private ArrayList<Noeud> ajoutAdjacences(Noeud n){
        ArrayList<Noeud> voisins = new ArrayList<>();
        
        int x = n.getX();
        int y = n.getY();
        
        if(x-1 >= 0 && (graphe[x-1][y].getVal() == 0 || graphe[x-1][y].getVal() == 3)){
            Noeud p = new Noeud(x-1, y);
            p.setVal(graphe[x-1][y].getVal());
            voisins.add(p);
        }
        if(x+1 < height && (graphe[x+1][y].getVal() == 0 || graphe[x+1][y].getVal() == 3)){
            Noeud p = new Noeud(x+1, y);
            p.setVal(graphe[x+1][y].getVal());
            voisins.add(p);
        }
        if(y-1 >= 0 && (graphe[x][y-1].getVal() == 0 || graphe[x][y-1].getVal() == 3)){
            Noeud p = new Noeud(x, y-1);
            p.setVal(graphe[x][y-1].getVal());
            voisins.add(p);
        }
        if(y+1 < width && (graphe[x][y+1].getVal() == 0 || graphe[x][y+1].getVal() == 3)){
            Noeud p = new Noeud(x, y+1);
            p.setVal(graphe[x][y+1].getVal());
            voisins.add(p);
        }
        
        return voisins;
    }
      
    private Noeud updateNoeud(Noeud fils, double cout_f, double cout_g, double cout_h, Noeud parent){
        fils.setCout_f(cout_f);
        fils.setCout_g(cout_g);
        fils.setCout_h(cout_h);
        fils.setParent(parent);
        return fils;
    }
    
    //Remplissage du graphe depuis un fichier externe
    private void remplirGraphe(String pathName){
        try{
            File fichier = new File(pathName);
            Scanner scanner = new Scanner(fichier);
            width = Integer.parseInt(scanner.nextLine());
            height  = Integer.parseInt(scanner.nextLine());
            
            graphe = new Noeud[height][width];
            
            String line, StringGraphe = "";
            int i = 0;
            int j = 0;
            
            while(scanner.hasNext()){
                line = scanner.nextLine().trim();
                while(i < line.length()){
                    char c = line.charAt(i);
                    Noeud p = new Noeud(j, i);
                    p.setVal(traiter(c));
                    switch(c){
                        case 'X':
                            depart = p;
                            break;
                        case 'O':
                            arrivee = p;
                            break;
                    }
                    graphe[j][i] = p;
                    StringGraphe += c;
                    i++;
                }
                StringGraphe +="\n";
                i = 0;
                j++;  
            }
            System.out.println("-------------------------------------------------------------");
            System.out.println("-----------------------LABYRINTHE----------------------------");
            System.out.println("-------------------------------------------------------------\n");
            System.out.println(StringGraphe);
            
            afficherGraphe();
            
            scanner.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void afficherGraphe(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.print(graphe[i][j].getVal());
            }
            System.out.print("\n");
        }
    }
    
    //Correspondance entre le terrain et les coordonnées
    private int traiter(char c){
        switch(c){
            case '-':
                return 0; //Designe le sol
            case '+':
                return 1; //Designe le mur
            case 'X':
                return 2; //Designe le depart
            case 'O':
                return 3; //Designe l'arrivée
            default:
                return 4; //signale une erreur de remplissage dans le fichier
        }
    }
    
    //Reconstitution du chemin menant vers une solution
    private ArrayList<Noeud> retrouverChemin(){
        return null;
    }
    
    //Retirer un noeud de la liste OPEN et le mettre dans la liste CLOSED
    private void addOpenToClosed(Noeud p){
        listeOuverts.remove(p);
        listeFermees.add(p);
    }
    
    
    //Recuperation du meilleur de la liste des ouverts
    private Noeud meilleurNoeud(ArrayList<Noeud> noeuds){
        double cout_f = noeuds.get(0).getCout_f();
        int k = 0;
        for(int i = 1; i < noeuds.size(); i++){
            if(cout_f > noeuds.get(i).getCout_f()){
                k = i;
                cout_f = noeuds.get(i).getCout_f();
            }
        }
        return noeuds.get(k);
    }
    
    //calcule la distance de Manathan entre (x1,y1) et (x2,y2)
    private double distance(Noeud x, Noeud y){
        return Math.sqrt((x.getX()-y.getX())*(x.getX()-y.getX()) + (x.getY()-y.getY())*(x.getY()-y.getY()));
    }
    
    //Presence d'un élément dans une liste donnée
    private boolean isPresent(ArrayList<Noeud> noeuds, Noeud p){
        for(int i = 0; i < noeuds.size(); i++){
            Noeud n = noeuds.get(i);
            if(n.getX() == p.getX() && n.getY() == p.getY()){
                return true;
            }
        }
        return false;
    }
    
}
