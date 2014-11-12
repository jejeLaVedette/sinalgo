package projects.DFS.nodes.messages;


import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class DFSMessage extends Message {
public Node sender;
public int id;

public DFSMessage(Node s){
	sender=s;
	this.id = s.ID;
}

public Message clone() {
	return new DFSMessage(sender);
}

	
}
