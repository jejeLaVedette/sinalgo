package projects.DominusExec.nodes.timers;

import projects.DominusExec.nodes.messages.dominusExecMessage;
import projects.DominusExec.nodes.nodeImplementations.dominusExecNode;
import sinalgo.nodes.Node;
import sinalgo.nodes.timers.Timer;

public class waitTimer extends Timer {	
	Node D;
	public waitTimer(Node D){
		this.D=D;
	}
	public void fire() {
		dominusExecNode n= (dominusExecNode) this.node;
		n.send(new dominusExecMessage(this.node, n.val),this.D);
	}

}
