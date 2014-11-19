package projects.DominusExec.nodes.nodeImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Random;

import projects.DominusExec.nodes.messages.dominusExecMessage;
import projects.DominusExec.nodes.timers.initTimer;
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

public class dominusExecNode extends Node {

	public int val;
	public boolean jeton=false;
	public Status etat=Status.dehors;
	public int tabVoisin[];
	
	private int etatD; //0 = dominant, 1 = domine

	public void preStep() {}

	// ATTENTION lorsque init est appel� les liens de communications n'existent pas
	// il faut attend une unit� de temps, avant que les connections soient r�alis�es
	// nous utilisons donc un timer

	public void init() {
		(new initTimer()).startRelative(1, this);
		etatD=1;
	}

	// Lorsque le timer pr�c�dent expire, la fonction start est appel�e
	// elle correspond ainsi � l'initialisation r�elle du processus

	public void start(){
		Random ran = new Random();
		val = ran.nextInt(20);
		this.broadcast(new dominusExecMessage(this, val));
	}

	// Cette fonction g�re la r�ception de message
	// Elle est appel�e r�guli�rement m�me si aucun message n'a �t� re�u

	public void handleMessages(Inbox inbox) {
		// Test si il y a des messages
		while(inbox.hasNext())
		{
			Message m=inbox.next();
			if(m instanceof dominusExecMessage) 
			{	//leave
				if(etatD==0){
					if(!plusPetit()&&petitDomine()){
						etatD=1; //domine
					}
				} else if(etatD==1){ //join
					if(plusPetit()){
						etatD=0; //dominant
					} else if(tousDomine()){
						etatD=0;
					}
				}
			}
		}
		this.val = (this.val + 1) % (2 * Tools.getNodeList().size() + 1) ;
		this.broadcast(new dominusExecMessage(this, val));
	}

	public boolean plusPetit(){
		boolean isPetit = true;
		Iterator<Edge> edgeIter = this.outgoingConnections.iterator();
		for(int i=1; i <= this.outgoingConnections.size() ;i++){
			isPetit = isPetit&&(this.ID==Math.min(this.ID, edgeIter.next().getID()));
		}
		return isPetit;
	}
	
	public boolean tousDomine(){
		boolean isDomine = true;
		Iterator<Edge> edgeIter = this.outgoingConnections.iterator();
		for(int i=1; i <= this.outgoingConnections.size() ;i++){
			isDomine = isDomine && (((dominusExecNode) (edgeIter.next().endNode)).getEtatD()==0);
		}
		return isDomine;
	}
	
	public boolean petitDomine(){
		boolean isPetitDomine = true;
		Iterator<Edge> edgeIter = this.outgoingConnections.iterator();
		int idPetit;
		idPetit=(int) edgeIter.next().getID();
		for(int i=2; i <= this.outgoingConnections.size() ;i++){
			idPetit=Math.min(idPetit, (int) edgeIter.next().getID());
		}
		for(int i=1; i <= this.outgoingConnections.size() ;i++){
			Edge voisin = edgeIter.next();
			if(idPetit==voisin.getID()){
				isPetitDomine = ((dominusExecNode)voisin.endNode).getEtatD()==0;
			}
		}		
		return isPetitDomine;
	}
	
	public int getEtatD(){
		return this.etatD;
	}

	public void neighborhoodChange() {}
	public void postStep() {}
	public void checkRequirements() throws WrongConfigurationException {}

	public Color Couleur(){
		if(this.etatD == 0){
			return Color.RED;
		}else if(this.etatD == 1){
			return Color.BLUE;
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
