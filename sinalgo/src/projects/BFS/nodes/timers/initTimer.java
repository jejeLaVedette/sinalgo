package projects.BFS.nodes.timers;

import projects.DFS.nodes.nodeImplementations.DFSNode;
import sinalgo.nodes.timers.Timer;

public class initTimer extends Timer {

	public void fire() {
		DFSNode n= (DFSNode) this.node;
		n.start();
	}
	

}
