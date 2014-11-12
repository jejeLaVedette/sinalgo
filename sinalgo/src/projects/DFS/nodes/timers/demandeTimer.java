package projects.DFS.nodes.timers;


import projects.DFS.nodes.nodeImplementations.DFSNode;
import sinalgo.nodes.timers.Timer;

public class demandeTimer extends Timer {
	
	public void fire() {
		DFSNode n= (DFSNode) this.node;
	}
}
