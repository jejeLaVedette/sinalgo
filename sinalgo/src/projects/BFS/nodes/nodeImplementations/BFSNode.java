package projects.BFS.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import projects.DFS.nodes.messages.DFSMessage;
import projects.DFS.nodes.timers.demandeTimer;
import projects.DFS.nodes.timers.initTimer;
import projects.DFS.nodes.timers.waitTimer;
import sinalgo.configuration.WrongConfigurationException;
import sinalgo.gui.transformation.PositionTransformation;
import sinalgo.nodes.Node;
import sinalgo.nodes.edges.Edge;
import sinalgo.nodes.messages.Inbox;
import sinalgo.nodes.messages.Message;
import sinalgo.tools.Tools;

enum Status{
	demandeur,
	dedans,
	dehors
}

public class BFSNode extends Node {

	public boolean[] visite;
	public int pere;
	public Color couleur = Color.BLUE;
	
	public void preStep() {}

	// ATTENTION lorsque init est appel� les liens de communications n'existent pas
	// il faut attend une unit� de temps, avant que les connections soient r�alis�es
	// nous utilisons donc un timer

	public void init() {
		(new initTimer()).startRelative(1, this);
	}

	// Lorsque le timer pr�c�dent expire, la fonction start est appel�e
	// elle correspond ainsi � l'initialisation r�elle du processus

	public void start(){
		this.visite = new boolean[this.outgoingConnections.size() + 1];
		// Pour tous les voisins
		for(int i = 1; i <= this.outgoingConnections.size(); i++){
			this.visite[i] = false;
		}
		// Si on est initiateur
		if(this.ID==1){
			this.couleur = Color.RED;
			pere = -1;
			visite[1] = true;
			this.send(new DFSMessage(this), getVoisin(1));
		}else{
			pere = 0;
		}
	}


	public Node getVoisin(int canal){
		Iterator<Edge> edgeIter = this.outgoingConnections.iterator();
		
		for(int j = 1; j < canal; j++){
			edgeIter.next();
		}
		return edgeIter.next().endNode;
	}

	public int getCanal(Node n){
		Iterator<Edge> edgeIter = this.outgoingConnections.iterator();
		int j = 1;
		while(edgeIter.next().endNode.ID != n.ID){
			j++;
		}
		return j;
	}

	// Cette fonction g�re la r�ception de message
	// Elle est appel�e r�guli�rement m�me si aucun message n'a �t� re�u

	public void handleMessages(Inbox inbox) {
		// Test si il y a des messages
		while(inbox.hasNext())
		{
			Message m=inbox.next();
			if(m instanceof DFSMessage) // Si le processus a re�u le jeton 
			{	
				int q = getCanal(((DFSMessage) m).sender);
				System.out.println(q);;
				if(pere == 0){
					pere = q;
					this.couleur = Color.RED;
				}
				boolean verif = true;
				for(int k = 1; k <= nbVoisin(); k++){
					if(!visite[k]){
						verif = false;
					}
				}
				// Phase de vérification
				if(verif){
					this.couleur = Color.GREEN;
					System.out.println("Je décide");
					Tools.stopSimulation();
				}else{
					int suivant;
					if(existe()){
						if(!visite[q] && pere != q){
							suivant = q;
						}else{
							suivant = choisirSuivant();
						}
						visite[suivant] = true;
						send(new DFSMessage(this), getVoisin(suivant));
					}else{
						this.couleur = Color.GREEN;
						visite[pere] = true;
						send(new DFSMessage(this), getVoisin(pere));
					}
				}

			}	
		}
	}
	
	public boolean existe(){
		for(int i = 1; i <= nbVoisin(); i++){
			if(pere != i && !visite[i]){
				return true;
			}
		}
		return false;
	}

	public int choisirSuivant(){
		for(int i = 1; i <= nbVoisin(); i++){
			if(!visite[i] && i != pere){
				return i;
			}
		}
		return -1;
	}

	public int nbVoisin(){
		return this.outgoingConnections.size();
	}



	public void neighborhoodChange() {}
	public void postStep() {}
	public void checkRequirements() throws WrongConfigurationException {}


	// affichage du noeud

	public void draw(Graphics g, PositionTransformation pt, boolean highlight){
		this.setColor(couleur);
		String text = ""+this.ID;
		super.drawNodeAsDiskWithText(g, pt, highlight, text, 20, Color.black);
	}


}
