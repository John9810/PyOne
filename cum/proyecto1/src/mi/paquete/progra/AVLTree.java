package mi.paquete.progra;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AVLTree<T extends Comparable<T>> implements Tree<T> {
	static ManageDocument document = new ManageDocument();
	
	private Node<T> root;

	@Override
	public void insert(T data, Curso curso) {
		root = insert(root, data, curso);
	}

	@Override
	public void delete(T data) {
		root = delete(root, data);
	}

	private Node<T> insert(Node<T> node, T data, Curso curso) {

		if (node == null) {
			return new Node<T>(data, curso);
		}

		if (data.compareTo(node.getData()) < 0) {
			node.setLeftNode(insert(node.getLeftNode(), data, curso));
		} else {
			node.setRightNode(insert(node.getRightNode(), data, curso));
		}

		node.setHeight(Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1);

		return settleViolation(data, node);
	}

	private Node<T> delete(Node<T> node, T data) {

		if (node == null)
			return node;

		if (data.compareTo(node.getData()) < 0) { 
			node.setLeftNode(delete(node.getLeftNode(), data));
		} else if (data.compareTo(node.getData()) > 0) { 
			node.setRightNode(delete(node.getRightNode(), data));
		} else {

			if (node.getLeftNode() == null && node.getRightNode() == null) {
				// System.out.println("Removing a leaf node...");
				return null;
			}

			if (node.getLeftNode() == null) {
				// System.out.println("Removing the right child...");
				Node<T> tempNode = node.getRightNode();
				node = null;
				return tempNode;
			} else if (node.getRightNode() == null) {
				// System.out.println("Removing the left child...");
				Node<T> tempNode = node.getLeftNode();
				node = null;
				return tempNode;
			}

			// System.out.println("Removing item with two children...");
			Node<T> tempNode = getPredecessor(node.getLeftNode());

			node.setData(tempNode.getData());
			node.setLeftNode(delete(node.getLeftNode(), tempNode.getData()));
		}

		node.setHeight(Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1);

		return settleDeletion(node);
	}

	private Node<T> settleDeletion(Node<T> node) {

		int balance = getBalance(node);

		if (balance > 1) {

			if (getBalance(node.getLeftNode()) < 0) {
				node.setLeftNode(leftRotation(node.getLeftNode()));
			}

			return rightRotation(node);
		}

		if (balance < -1) {
			if (getBalance(node.getRightNode()) > 0) {
				node.setRightNode(rightRotation(node.getRightNode()));
			}

			return leftRotation(node);
		}

		return node;
	}

	private Node<T> getPredecessor(Node<T> node) {

		Node<T> predecessor = node;

		while (predecessor.getRightNode() != null)
			predecessor = predecessor.getRightNode();

		return predecessor;
	}

	private Node<T> settleViolation(T data, Node<T> node) {

		int balance = getBalance(node);

		// Case 1 (left - left)
		if (balance > 1 && data.compareTo((T) node.getLeftNode().getData()) < 0) {
			// System.out.println("Tree is left left heavy...");
			return rightRotation(node);
		}

		// Case 2 (right - right)
		if (balance < -1 && data.compareTo((T) node.getRightNode().getData()) > 0) {
			// System.out.println("Tree is right right heavy...");
			return leftRotation(node);
		}

		// Case 3 (left right)
		if (balance > 1 && data.compareTo((T) node.getLeftNode().getData()) > 0) {
			// System.out.println("Tree is left right heavy...");
			node.setLeftNode(leftRotation(node.getLeftNode()));
			return rightRotation(node);
		}

		// Case 4 (right - left)
		if (balance < -1 && data.compareTo((T) node.getRightNode().getData()) < 0) {
			// System.out.println("Tree is right left heavy...");
			node.setRightNode(rightRotation(node.getRightNode()));
			return leftRotation(node);
		}

		return node;
	}

	private Node<T> rightRotation(Node<T> node) {

		// System.out.println("Rotating to the right on node: " + node);

		Node<T> tempLeftNode = node.getLeftNode();
		Node<T> t = tempLeftNode.getRightNode();

		tempLeftNode.setRightNode(node);
		node.setLeftNode(t);

		node.setHeight(Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1);
		tempLeftNode.setHeight(Math.max(height(tempLeftNode.getLeftNode()), height(tempLeftNode.getRightNode())) + 1);

		return tempLeftNode;
	}

	private Node<T> leftRotation(Node<T> node) {

		// System.out.println("Rotating to the left on node:" + node);

		Node<T> tempRightNode = node.getRightNode();
		Node<T> t = tempRightNode.getLeftNode();

		tempRightNode.setLeftNode(node);
		node.setRightNode(t);

		node.setHeight(Math.max(height(node.getLeftNode()), height(node.getRightNode())) + 1);
		tempRightNode
				.setHeight(Math.max(height(tempRightNode.getLeftNode()), height(tempRightNode.getRightNode())) + 1);

		return tempRightNode;
	}

	private int height(Node<T> node) {

		if (node == null) {
			return -1;
		}

		return node.getHeight();
	}

	private int getBalance(Node<T> node) {

		if (node == null) {
			return 0;
		}

		return height(node.getLeftNode()) - height(node.getRightNode());
	}

	@Override
	public void traverse(String fileName) throws FileNotFoundException, IOException {

		if (root == null)
			return;

		inOrderTraversal(root, fileName);
	}
	
	private void inOrderTraversal(Node<T> node, String fileName) throws FileNotFoundException, IOException {

		if (node.getLeftNode() != null)
			inOrderTraversal(node.getLeftNode(), fileName);
		
		document.writeContent(fileName, node.getCurso().getConcatenatedCurso());
		// System.out.println(node.getStudent().getConcatenatedStudent());

		if (node.getRightNode() != null)
			inOrderTraversal(node.getRightNode(), fileName);
	}
}
