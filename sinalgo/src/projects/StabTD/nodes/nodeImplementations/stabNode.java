package projects.StabTD.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Random;

import projects.StabTD.nodes.messages.stabMessage;
import projects.StabTD.nodes.timers.initTimer;
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

public class stabNode extends Node {

	public int val;
	public boolean jeton=false;
	public Status etat=Status.dehors;
	public int tabVoisin[];

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
		Random ran = new Random();
		val = ran.nextInt(20);
		this.broadcast(new stabMessage(this, val));
	}

	// Cette fonction g�re la r�ception de message
	// Elle est appel�e r�guli�rement m�me si aucun message n'a �t� re�u

	public void handleMessages(Inbox inbox) {
		// Test si il y a des messages
		while(inbox.hasNext())
		{
			Message m=inbox.next();
			if(m instanceof stabMessage) 
			{	
				this.val = (Math.min(this.val, ((stabMessage) m).val)); 	
			}
		}
		this.val = (this.val + 1) % (2 * Tools.getNodeList().size() + 1) ;
		this.broadcast(new stabMessage(this, val));
	}



	public stabNode Droite(){
		for(Edge e : this.outgoingConnections)
			if(e.endNode.ID==this.ID%Tools.getNodeList().size()+1)
				return (stabNode) e.endNode;
		return null;
	}	

	public stabNode Gauche(){
		for(Edge e : this.outgoingConnections)
			if(e.endNode.ID!=this.ID%Tools.getNodeList().size()+1)
				return (stabNode) e.endNode;
		return null;
	}	


	public void demande(){
		this.etat=Status.demandeur;
	}

	public void neighborhoodChange() {}
	public void postStep() {}
	public void checkRequirements() throws WrongConfigurationException {}

	public Color Couleur(){
		if(this.val % 6 == 0){
			return Color.RED;
		}else if(this.val % 6 == 1){
			return Color.BLUE;
		}else if(this.val % 6== 2){
			return Color.YELLOW;
		}else if(this.val % 6 == 3){
			return Color.BLACK;
		}else if(this.val % 6 == 4){
			return Color.GRAY;
		}else if(this.val % 6 == 5){
			return Color.PINK;
		}
		return Color.GREEN;
	}

	// affichage du noeud

	public void draw(Graphics g, PositionTransformation pt, boolean highlight){
		this.setColor(this.Couleur());
		String text = ""+this.val;
		super.drawNodeAsDiskWithText(g, pt, highlight, text, 20, Color.black);
	}


}
