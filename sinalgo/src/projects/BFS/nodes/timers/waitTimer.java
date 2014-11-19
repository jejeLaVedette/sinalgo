package projects.BFS.nodes.timers;

import projects.DFS.nodes.messages.DFSMessage;
import projects.DFS.nodes.nodeImplementations.DFSNode;
import sinalgo.nodes.Node;
import sinalgo.nodes.timers.Timer;

public class waitTimer extends Timer {	
	Node D;
	public waitTimer(Node D){
		this.D=D;
	}
	public void fire() {
		DFSNode n= (DFSNode) this.node;
		n.send(new DFSMessage(this.node),this.D);
	}

}
