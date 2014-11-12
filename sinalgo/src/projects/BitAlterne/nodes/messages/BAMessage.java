package projects.BitAlterne.nodes.messages;


import sinalgo.nodes.Node;
import sinalgo.nodes.messages.Message;

public class BAMessage extends Message {
public Node sender;
public int id;
public int buffer;
public String val;
public boolean tag;

public BAMessage(Node s, String val, int buffer, boolean tag){
	sender=s;
	this.tag = tag;
	this.val = val;
	this.buffer = buffer;
	this.id = s.ID;
}

public BAMessage(Node s, String val, boolean tag){
	sender=s;
	this.tag = tag;
	this.val = val;
	this.id = s.ID;
}

public Message clone() {
	return new BAMessage(sender, val, buffer, tag);
}

	
}
