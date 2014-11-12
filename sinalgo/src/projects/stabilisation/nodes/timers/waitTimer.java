package projects.stabilisation.nodes.timers;

import projects.stabilisation.nodes.messages.stabMessage;
import projects.stabilisation.nodes.nodeImplementations.stabNode;
import sinalgo.nodes.Node;
import sinalgo.nodes.timers.Timer;

public class waitTimer extends Timer {	
	Node D;
	public waitTimer(Node D){
		this.D=D;
	}
	public void fire() {
		stabNode n= (stabNode) this.node;
		n.send(new stabMessage(this.node, n.val),this.D);
	}

}
