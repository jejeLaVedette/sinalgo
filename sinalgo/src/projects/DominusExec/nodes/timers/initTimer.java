package projects.DominusExec.nodes.timers;

import projects.DominusExec.nodes.nodeImplementations.dominusExecNode;
import sinalgo.nodes.timers.Timer;

public class initTimer extends Timer {

	public void fire() {
		dominusExecNode n= (dominusExecNode) this.node;
		n.start();
	}
	

}
