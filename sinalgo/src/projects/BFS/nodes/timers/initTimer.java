package projects.BFS.nodes.timers;

import projects.BFS.nodes.nodeImplementations.BFSNode;
import sinalgo.nodes.timers.Timer;

public class initTimer extends Timer {

	public void fire() {
		BFSNode n= (BFSNode) this.node;
		n.start();
	}
	

}
