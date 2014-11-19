package projects.BFS.nodes.timers;


import projects.BFS.nodes.nodeImplementations.BFSNode;
import sinalgo.nodes.timers.Timer;

public class demandeTimer extends Timer {
	
	public void fire() {
		BFSNode n= (BFSNode) this.node;
	}
}
