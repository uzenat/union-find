import java.awt.image.BufferedImage; 
import java.util.HashSet; 
import javax.imageio.ImageIO; 
import java.io.File;

class Arbre { //Classe construisant un arbre correspondant à un pixel
    public int x, y, taille; //Coordonnee (x,x) du pixel noir, la taille correspond au nombre de de pixel relié à cet arbre
    public Arbre pere; //Reference vers le pere de l'arbre
    public Arbre(int x, int y){ this.x= x; this.y= y; this.taille = 1; this.pere = this; } //Constructeur, construit l'objet à partir des coordonnées
    public Arbre ROOT(){ return (this!=this.pere)?this.pere=this.pere.ROOT():this.pere; } //Methode qui remonte les pere de l'arbre jusqu'a la racine
    public void UNION(Arbre a){ 
		Arbre rac1 = this.ROOT(); Arbre rac2 = a.ROOT();
			if(rac1 != rac2){ 
	    	if(rac1.taille < rac2.taille){ rac1.pere = rac2; rac2.taille += rac1.taille; }
	    	else { rac2.pere = rac1; rac1.taille += rac2.taille; }
		}
    }
}

class Foret { //Classe construisant la foret des peres
    private Arbre [] pix_noir; //tableau de pixel noir, chaque arbre correspond à un pixel noir
    private int [][]id; //tableau des identifant des pixels
    private HashSet<Arbre> comp_co; //ensemble de composant connexe
    public int nb_arbre(){ return comp_co.size(); } //retourne le nombre de composante connexe
    public Foret(BufferedImage img, int R, int N){ //Constructeur de classe
	id = new int[img.getWidth()][img.getHeight()]; //initialisation du tableau d'identifiant
		comp_co = new HashSet<Arbre>(); int cpt = 0; //initialisation de l'ensemble + declaration d'un compteur 
		for(int i = 0; i<img.getWidth(); i++) //          |
	    	for(int j = 0; j<img.getHeight(); j++) //     | Parcours l'image dans le but de compter le nombre de pixel noir et initialiser la taille du tableau
			id[i][j] = (img.getRGB(i,j)==-1)?-1:cpt++; // | et stock dans la matrice id l'identifiant du pixel rencontrer correspondant à sa position dans le tableau pix noir
		pix_noir = new Arbre[cpt]; cpt = 0;
		for(int i = 0; i<img.getWidth(); i++)   //                       |
	    	for(int j = 0; j<img.getHeight(); j++) //                    | Parcours une seconde fois l'image et pour chaque pixel noir rencontré, créer un  
			if(img.getRGB(i,j)!=-1) pix_noir[cpt++] = new Arbre(i,j);//  | noeud de coordonnee i,j et le stock dans le tableau
		for(int i = 0; i<pix_noir.length; i++){   // les opération qui suivent correspondent au traitement des pixel R voisins
		    int jj = pix_noir[i].x-R<0?0:pix_noir[i].x-R; //le principe est de parcourir les pixels de x-R,y-R a x+R,y+R en omettant le cas ou est de les comparer au pixel x,y
	    	for(int j = jj; j<=pix_noir[i].x+R; j++){  
			if(j>=img.getWidth()) break; int kk = pix_noir[i].y-R<0?0:pix_noir[i].y-R;
			for(int k = kk; k<=pix_noir[i].y+R; k++){
		    	if(k>=img.getHeight()) break;
		    	if((pix_noir[i].x!=j || pix_noir[i].y!=k) 
		       	&& (id[j][k]!=-1) && ((pix_noir[i].x-j)*(pix_noir[i].x-j)+(pix_noir[i].y-k)*(pix_noir[i].y-k))<=R) // teste si deux pixel sont R voisins et si le pixel n'est pas blanc
				pix_noir[i].UNION(pix_noir[id[j][k]]); }}} //On effectue l'union des pixel R voisins a celui de corrdonee x,y
		for(int i=0; i<pix_noir.length; i++){ Arbre a = pix_noir[i].ROOT(); if(a.taille>N) comp_co.add(a); }
    }
}

public class Main { //Classe contenant le programme main
	public static void main(String[] args){
		if(args.length != 3) { System.err.println("Nombre d'argument invalide"); System.exit(0);
		} try {
			BufferedImage img = ImageIO.read(new File(args[0])); //Charge l'image
			Foret ff = new Foret(img,Integer.parseInt(args[1]), Integer.parseInt(args[2])); //Créer la foret à partir de l'image et des argument R et N
			System.out.println(ff.nb_arbre()); //Affiche le nombre d'élément qui constitue notre foret qui represente le nombre de composante connexe
		} catch(Exception e){ e.printStackTrace(); }
	}
}
