package projects.BitAlterne.nodes.timers;

import projects.BitAlterne.nodes.nodeImplementations.BANode;
import sinalgo.nodes.Node;
import sinalgo.nodes.timers.Timer;

public class waitTimer extends Timer {	
	Node D;
	public waitTimer(Node D){
		this.D=D;
	}
	public void fire() {
		BANode n= (BANode) this.node;
		n.minuteur();
	}

}
