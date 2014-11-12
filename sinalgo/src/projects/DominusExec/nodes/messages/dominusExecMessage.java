package projects.DominusExec.nodes.messages;


import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class dominusExecMessage extends Message {
public Node sender;
public int val;

public dominusExecMessage(Node s, int v){
	sender=s;
	this.val = v;
}

public Message clone() {
	return new dominusExecMessage(sender, val);
}

	
}
