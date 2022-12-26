package ai;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class Pathfinder {
	GamePanel gp;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode;
	Node goalNode;
	Node currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public Pathfinder(GamePanel gp) {
		this.gp = gp;
		instantiateNodes();
	}
	public void instantiateNodes(){
		node = new Node[gp.maxWorldCol][gp.maxWorldRow];
		int col = 0;
		int row = 0;	
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			node[col][row] = new Node(col, row);
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	public void resetNodes() {
		int col = 0;
		int row = 0;	
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
		resetNodes();
		
		// Set Start and Goal node
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;	
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//Set solid nodes
			//check tiles
			int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
			if(gp.tileM.tile[tileNum].collision == true) {
				node[col][row].solid = true;
			}
			// check interactive tiles
			for(int i = 0; i < gp.iTile[1].length; i++) {
				if(gp.iTile[gp.currentMap][i]!= null && gp.iTile[gp.currentMap][i].destructible == true) {
					int itCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
					int itRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
					node[itCol][itRow].solid = true;
				}
			}
			// Set cost
			getCost(node[col][row]);
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	private void getCost(Node node) {

		// G Cost
		int xDist = Math.abs(node.col - startNode.col); // x distance basic formula
		int yDist = Math.abs(node.row - startNode.row); // y distance basic formula
		node.gCost = xDist + yDist;
		// H Cost
		xDist = Math.abs(node.col - goalNode.col); // x distance basic formula
		yDist = Math.abs(node.row - goalNode.row); // y distance basic formula
		node.hCost = xDist + yDist;
		// F Cost
		node.fCost = node.gCost + node.hCost;
		
	}	
	public boolean search() {
		while(goalReached == false && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//open node (up)
			if(row-1 >= 0) { 
				openNode(node[col][row-1]);
				}
			//open node (left)
			if(col-1 >= 0) { 
				openNode(node[col-1][row]);
				}
			//open node (down)
			if(row+1 < gp.maxWorldRow) { 
				openNode(node[col][row+1]);
				}
			//open node (right)
			if(col+1 < gp.maxWorldCol) { 
				openNode(node[col+1][row]);
				}
			
			// Best move
			int bestNodeIndex = 0;
			int bestNodeFCost = 999; // first one automatically
			for(int i =0; i<openList.size(); i++) {
				//if this node's fCost is better
				if(openList.get(i).fCost < bestNodeFCost) {
					bestNodeIndex = i;
					bestNodeFCost = openList.get(i).fCost;
				}
				//if f cost is equal, check g cost
				else if(openList.get(i).fCost == bestNodeFCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			// if there is no node in openList, kill loop
			if(openList.size() == 0) {
				break;
			}
			currentNode = openList.get(bestNodeIndex);
			if(currentNode == goalNode) {
				goalReached = true;
				trackPath();
			}
			step++;
		}
		return goalReached;
	}
	private void openNode(Node node) {
		if(node.open == false && node.checked == false && node.solid == false) {
			//If node is not opened yet, add it to the open list
			node.open = true;
			//node.setAsOpen();
			node.parentNode = currentNode;
			openList.add(node);
		}
	}
	
	private void trackPath() {
		//backtracking from goal
		Node current = goalNode;
		
		while(current != startNode) {
			pathList.add(0,current);
			current = current.parentNode;
		}
	}
}


