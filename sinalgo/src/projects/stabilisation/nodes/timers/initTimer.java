package projects.stabilisation.nodes.timers;

import projects.stabilisation.nodes.nodeImplementations.stabNode;
import sinalgo.nodes.timers.Timer;

public class initTimer extends Timer {

	public void fire() {
		stabNode n= (stabNode) this.node;
		n.start();
	}
	

}
