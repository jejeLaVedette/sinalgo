package projects.DominusExec.nodes.timers;

import projects.StabTD.nodes.nodeImplementations.stabNode;
import sinalgo.nodes.timers.Timer;

public class initTimer extends Timer {

	public void fire() {
		stabNode n= (stabNode) this.node;
		n.start();
	}
	

}
