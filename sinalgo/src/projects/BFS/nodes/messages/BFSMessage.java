package projects.BFS.nodes.messages;


import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class BFSMessage extends Message {
public Node sender;
public int id;

public BFSMessage(Node s){
	sender=s;
	this.id = s.ID;
}

public Message clone() {
	return new BFSMessage(sender);
}

	
}
