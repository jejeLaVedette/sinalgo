package projects.BitAlterne.nodes.timers;

import projects.BitAlterne.nodes.nodeImplementations.BANode;
import sinalgo.nodes.timers.Timer;

public class initTimer extends Timer {

	public void fire() {
		BANode n= (BANode) this.node;
		n.start();
	}
	

}
