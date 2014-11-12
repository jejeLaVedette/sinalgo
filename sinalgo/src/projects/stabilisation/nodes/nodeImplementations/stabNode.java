package projects.stabilisation.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import projects.stabilisation.nodes.messages.stabMessage;
import projects.stabilisation.nodes.timers.demandeTimer;
import projects.stabilisation.nodes.timers.initTimer;
import projects.stabilisation.nodes.timers.waitTimer;
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
		this.initDemande();
		(new waitTimer(this.Droite())).startRelative(10, this);		
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
				stabNode D=this.Droite(); 

				if(this.ID == 1){
					if(((stabMessage)m).val == this.val){
						this.val = (val + 1) % (Tools.getNodeList().size());
					}
				}else{
					this.val = ((stabMessage)m).val;
				}

				(new waitTimer(D)).startRelative(10, this);				 	
			}
		}
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

	public void initDemande(){
		demandeTimer t=new demandeTimer();
		int tps=Math.max((int) ((Math.random()*10000.0)%500.0),1);
		t.startRelative(tps, this);
	}	

	public void demande(){
		this.etat=Status.demandeur;
	}

	public void neighborhoodChange() {}
	public void postStep() {}
	public void checkRequirements() throws WrongConfigurationException {}

	public Color Couleur(){
		if(this.val % 2 == 0){
			return Color.RED;
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
