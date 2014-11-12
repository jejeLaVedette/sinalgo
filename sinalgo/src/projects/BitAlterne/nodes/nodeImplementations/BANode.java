package projects.BitAlterne.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import projects.BitAlterne.nodes.messages.BAMessage;
import projects.BitAlterne.nodes.timers.initTimer;
import projects.BitAlterne.nodes.timers.waitTimer;
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

public class BANode extends Node {

	public boolean tag = false;
	int buffer;
	ArrayList<Integer> arrivee;
	ArrayList<Integer> depart;


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

		if(this.ID == 1){
			buffer = -1;
			depart = new ArrayList<Integer>();
			depart.add(5);
			depart.add(7);
			depart.add(8);
			depart.add(5);
			(new waitTimer(this)).startRelative(10, this);
		}else{
			arrivee = new ArrayList<Integer>();
		}
	}

	public void minuteur(){
		if(buffer == -1){
			if(depart.size() != 0){
				buffer = depart.get(0);
				depart.remove(0);
				tag = !tag;
				if(!tag){
					this.couleur = Color.RED;
				}else{
					this.couleur = Color.BLUE;
				}
			}
		}
		if(buffer != -1){
			send(new BAMessage(this, "DATA", buffer, tag), getVoisin());
		}
		(new waitTimer(this)).startRelative(10, this);
	}

	public Node getVoisin(){
		Iterator<Edge> edgeIter = this.outgoingConnections.iterator();
		return edgeIter.next().endNode;
	}



	// Cette fonction g�re la r�ception de message
	// Elle est appel�e r�guli�rement m�me si aucun message n'a �t� re�u

	public void handleMessages(Inbox inbox) {
		// Test si il y a des messages
		while(inbox.hasNext())
		{
			Message m=inbox.next();
			if(m instanceof BAMessage) // Si le processus a re�u le jeton 
			{	
				if(((BAMessage) m).val.equals("ACK")){
					if(tag == ((BAMessage) m).tag){
						buffer = -1;
						System.out.println("vidage du buffer");
					}
				}else if(((BAMessage) m).val.equals("DATA")){
					if(((BAMessage)m).tag != tag){
						tag = ((BAMessage)m).tag;
						if(!tag){
							this.couleur = Color.RED;
						}else{
							this.couleur = Color.BLUE;
						}
						arrivee.add(((BAMessage)m).buffer);
						if(arrivee.size() == 4){
							System.out.println(arrivee.toString());
							Tools.stopSimulation();
						}
						System.out.println("Réception de : " + ((BAMessage)m).buffer);
					}
					send(new BAMessage(this, "ACK", tag), getVoisin());
				}

			}	
		}
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
