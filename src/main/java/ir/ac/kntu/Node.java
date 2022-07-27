package ir.ac.kntu;

import java.io.Serializable;

public class Node<T extends Number & Comparable<T>> implements Serializable {
    private T data;
    private Node<T> leftChild, rightChild;

    Node(T data) {
        this.data = data;
        leftChild = null;
        rightChild = null;
    }

    public void setLeftChild(Node leftNode) {
        this.leftChild = leftNode;
    }

    public void setRightChild(Node rightNode) {
        this.rightChild = rightNode;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
