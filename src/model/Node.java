package model;

/**
 * Node for A* pathfinding
 */
public class Node {
	public Position pos;
	public int f;
	public int g;
	public int h;
	public Node parent;
	public Node(Node parenti, int gi, Position posi) {
		this.parent = parenti;
		this.g = gi;
		this.pos = posi;
	}
	/**
	 * Checks whether another node has the same position
     * @param other the node to check equality with
     */
	public boolean equal (Node other) {
			return ((this.pos.getX() == other.pos.getX())&&(this.pos.getY() == other.pos.getY()));
	}
}
