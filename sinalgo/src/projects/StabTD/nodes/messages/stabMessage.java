package projects.StabTD.nodes.messages;


import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class stabMessage extends Message {
public Node sender;
public int val;

public stabMessage(Node s, int v){
	sender=s;
	this.val = v;
}

public Message clone() {
	return new stabMessage(sender, val);
}

	
}
