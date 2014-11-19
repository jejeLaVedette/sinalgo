package projects.BFS.nodes.timers;

import projects.BFS.nodes.nodeImplementations.BFSNode;
import projects.DFS.nodes.messages.DFSMessage;
import sinalgo.nodes.Node;
import sinalgo.nodes.timers.Timer;

public class waitTimer extends Timer {	
	Node D;
	public waitTimer(Node D){
		this.D=D;
	}
	public void fire() {
		BFSNode n= (BFSNode) this.node;
		n.send(new DFSMessage(this.node),this.D);
	}

}
