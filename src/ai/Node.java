
package ai;

public class Node {

	Node parentNode;
	public int col;
	public int row;
	int gCost; // the distance between current node and start node
	int hCost; // distance from current node to goal node
	int fCost; // G+H = F
	// Tile Markers
	boolean solid;
	boolean open;
	boolean checked;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
		
	}

}
