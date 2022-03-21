package model;

import java.util.ArrayList;
import java.util.Collections;

public class Unit {
    
    private Position position;
    protected int speed;
    protected int hp;
    protected ArrayList<Position> path;
    
    protected Unit(Position pos, int speed, int hp) {
        this.position = pos;
        this.speed = speed;
        this.hp = hp;
    }
    
    public Position getPosition() {
        return this.position;
    }
    
    public void takeDamage(int v) {
        this.hp -= v;
    }
    
    public void heal(int v) {
        this.hp += v;
    }
    
    public void step() {
    	for(int i = 0; (i < speed) && (path.size()>1); i++) {
    		path.remove(0);
    		position=path.get(0);
    	}
    }
    
    public void findPath(Position goal) {
    	path = new ArrayList<Position>();
    	ArrayList<Node> openNodes = new ArrayList<Node>();
    	ArrayList<Node> closedNodes = new ArrayList<Node>();
    	
    	Node startNode = new Node(null, 0, position);
    	startNode.f = 0;
    	startNode.h = 0;
    	Node endNode = new Node(null, 0, goal);
    	endNode.f = 0;
    	endNode.h = 0;
    	
    	openNodes.add(startNode);
    	
    	while(openNodes.size() > 0) {
    		
    		Node currentNode = openNodes.get(0);
    		int currentIndex = 0;
    		
    		for(int i = 0; i < openNodes.size(); i++) {
    			if(openNodes.get(i).f < currentNode.f) {
    				currentNode = openNodes.get(i);
    				currentIndex = i;
    			}
    		}
    		
    		
    		openNodes.remove(currentIndex);
    		closedNodes.add(currentNode);
    		
    		if(currentNode.equal(endNode)) {
    			Node tmpCurrentNode = currentNode;
    			while(tmpCurrentNode != null) {
    				path.add(tmpCurrentNode.pos);
    				tmpCurrentNode = tmpCurrentNode.parent;
    			}
    			try {
    				Collections.reverse(path);
    			}catch (Exception e){
    				
    			}
    			openNodes.clear();
    		}
    		
    		Position posN = null;
    		if(currentNode.pos.getY()>0) {
    			posN = new Position(currentNode.pos.getX(),currentNode.pos.getY()-1);
    		}
    		Position posS = null;
    		if(currentNode.pos.getY()<9) {
    			posS = new Position(currentNode.pos.getX(),currentNode.pos.getY()+1);
    		}
    		Position posW = null;
    		if(currentNode.pos.getX()>0) {
    			posW = new Position(currentNode.pos.getX()-1,currentNode.pos.getY());
    		}
    		Position posE = null;
    		if(currentNode.pos.getX()<17) {
    			posE = new Position(currentNode.pos.getX()+1,currentNode.pos.getY());
    		}
    		
    		for(int i = 0; i < closedNodes.size(); i++) {
    			Node tmpNode = new Node(null,0,posN);
    			if(tmpNode.pos!= null && closedNodes.get(i).equal(tmpNode)) {
    				posN = null;
    			}
    			tmpNode = new Node(null,0,posS);
    			if(tmpNode.pos!= null && closedNodes.get(i).equal(tmpNode)) {
    				posS = null;
    			}
    			tmpNode = new Node(null,0,posW);
    			if(tmpNode.pos!= null && closedNodes.get(i).equal(tmpNode)) {
    				posW = null;
    			}
    			tmpNode = new Node(null,0,posE);
    			if(tmpNode.pos!= null && closedNodes.get(i).equal(tmpNode)) {
    				posE = null;
    			}
    		}
    		for(int i = 0; i < openNodes.size(); i++) {
    			Node tmpNode = new Node(null,0,posN);
    			if(tmpNode.pos!= null && openNodes.get(i).equal(tmpNode)) {
    				posN = null;
    			}
    			tmpNode = new Node(null,0,posS);
    			if(tmpNode.pos!= null && openNodes.get(i).equal(tmpNode)) {
    				posS = null;
    			}
    			tmpNode = new Node(null,0,posW);
    			if(tmpNode.pos!= null && openNodes.get(i).equal(tmpNode)) {
    				posW = null;
    			}
    			tmpNode = new Node(null,0,posE);
    			if(tmpNode.pos!= null && openNodes.get(i).equal(tmpNode)) {
    				posE = null;
    			}
    		}
    		
    		if(posN != null) {
    			Node child = new Node(currentNode,currentNode.g+1,posN);
    			child.h = (child.pos.getX()-endNode.pos.getX())*(child.pos.getX()-endNode.pos.getX()) + (child.pos.getY()-endNode.pos.getY())*(child.pos.getY()-endNode.pos.getY());
    			child.f = child.g + child.h;
    			openNodes.add(child);
    		}
    		
    		if(posS != null) {
    			Node child = new Node(currentNode,currentNode.g+1,posS);
    			child.h = (child.pos.getX()-endNode.pos.getX())*(child.pos.getX()-endNode.pos.getX()) + (child.pos.getY()-endNode.pos.getY())*(child.pos.getY()-endNode.pos.getY());
    			child.f = child.g + child.h;
    			openNodes.add(child);
    		}
    		
    		if(posW != null) {
    			Node child = new Node(currentNode,currentNode.g+1,posW);
    			child.h = (child.pos.getX()-endNode.pos.getX())*(child.pos.getX()-endNode.pos.getX()) + (child.pos.getY()-endNode.pos.getY())*(child.pos.getY()-endNode.pos.getY());
    			child.f = child.g + child.h;
    			openNodes.add(child);
    		}
    		
    		if(posE != null) {
    			Node child = new Node(currentNode,currentNode.g+1,posE);
    			child.h = (child.pos.getX()-endNode.pos.getX())*(child.pos.getX()-endNode.pos.getX()) + (child.pos.getY()-endNode.pos.getY())*(child.pos.getY()-endNode.pos.getY());
    			child.f = child.g + child.h;
    			openNodes.add(child);
    		}
    		
    	}
    }
    
}
