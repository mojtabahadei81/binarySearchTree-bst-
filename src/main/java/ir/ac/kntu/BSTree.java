package ir.ac.kntu;

import java.io.*;
import java.util.*;

public class BSTree<T extends Number & Comparable<T>> implements Serializable {

    private final ArrayList<T> allElements;

    private Node<T> root;

    public BSTree() {
        this.root = null;
        allElements = new ArrayList<>();
    }

    public void add(T data) {
        if (!allElements.contains(data)) {
            this.root = this.add(this.root, data);
            allElements.add(data);
            System.out.println("this element successfully added.");
            show();
        } else {
            System.out.println("this value inserted before.");
        }
    }

    private Node<T> add(Node<T> root, T data) {
        if (root == null) {
            root = new Node<>(data);
        } else if (root.getData().compareTo(data) >= 1) {
            root.setLeftChild(add(root.getLeftChild(), data));
        } else {
            root.setRightChild(add(root.getRightChild(), data));
        }
        return root;
    }

    public void show() {
        LinkedList<Node> treeLevel = new LinkedList<Node>();
        treeLevel.add(root);
        LinkedList<Node> temp = new LinkedList<Node>();
        int counter = 0;
        int height = heightOfTree(root) - 1;
        double numberOfElements = (Math.pow(2, (height + 1)) - 1);
        while (counter <= height) {
            Node removed = treeLevel.removeFirst();
            if (temp.isEmpty()) {
                printSpace(numberOfElements / Math.pow(2, counter + 1), removed);
            } else {
                printSpace(numberOfElements / Math.pow(2, counter), removed);
            }
            if (removed == null) {
                temp.add(null);
                temp.add(null);
            } else {
                temp.add(removed.getLeftChild());
                temp.add(removed.getRightChild());
            }
            if (treeLevel.isEmpty()) {
                System.out.println("");
                System.out.println("");
                treeLevel = temp;
                temp = new LinkedList<>();
                counter++;
            }
        }
    }

    public void search(T number) {
        Node<T> key = new Node<>(number);
        if (search(root, key) == null) {
            System.out.println("This value(" + key.getData() + ") does not exist in this binary search tree(BST).");
        } else {
            System.out.println("this value(" + key.getData() + ") exists in this binary search tree");
            printPath(root, key.getData());
        }
    }

    private Node<T> search(Node<T> root, Node<T> key) {
        if (root == null || root.getData().equals(key.getData())) {
            return root;
        }
        if (root.getData().compareTo(key.getData()) < 0) {
            return search(root.getRightChild(), key);
        }
        return search(root.getLeftChild(), key);
    }

    public boolean hasPath(Node<T> root, ArrayList<T> arr, T x) {
        if (root==null) {
            return false;
        }
        arr.add(root.getData());
        if (root.getData() == x) {
            return true;
        }
        if (hasPath(root.getLeftChild(), arr, x) || hasPath(root.getRightChild(), arr, x)) {
            return true;
        }
        arr.remove(arr.size()-1);
        return false;
    }

    public void printPath(Node root, T x) {
        ArrayList<T> arr = new ArrayList<>();
        if (hasPath(root, arr, x)) {
            for (int i = 0; i < arr.size() - 1; i++) {
                System.out.print(arr.get(i) + "->");
            }
            System.out.println(arr.get(arr.size() - 1));
        } else {
            System.out.print("No Path");
        }
    }

    public void deleteKey(T key) {
        if (search(root, new Node<>(key)) != null) {
            root = deleteRec(root, key);
            allElements.remove(key);
            System.out.println(key + " Removed successfully.");
            show();
        }
    }

    public Node<T> deleteRec(Node<T> root, T key) {
        if (root == null) {
            return root;
        }
        if (key.compareTo(root.getData()) < 0) {
            root.setLeftChild(deleteRec(root.getLeftChild(), key));
        } else if (key.compareTo(root.getData()) > 0) {
            root.setRightChild(deleteRec(root.getRightChild(), key));
        } else {
            if (root.getLeftChild() == null) {
                return root.getRightChild();
            } else if (root.getRightChild() == null) {
                return root.getLeftChild();
            }
            root.setData((T) minValue(root.getRightChild()));
            root.setRightChild(deleteRec(root.getRightChild(), root.getData()));
        }
        return root;
    }

    private T minValue(Node<T> root) {
        T minv = root.getData();
        while (root.getLeftChild() != null) {
            minv = (T) root.getLeftChild().getData();
            root = root.getLeftChild();
        }
        return minv;
    }

    public void postOrder() {
        postOrder(root);
        System.out.println("\n");
    }

    private void postOrder(Node root) {
        if (root != null) {
            postOrder(root.getLeftChild());
            postOrder(root.getRightChild());
            System.out.print(root.getData() + "  ");
        }
    }

    public static <T extends Number & Comparable<T>> void write(BSTree<T> bsTree) {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        System.out.println("please enter FilePath that you want write to it.");
        String filePath = scanner.next();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("this file not found.");
            return;
        }
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(bsTree);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Serialized data is saved in " + filePath);
    }

    public static BSTree<Integer> readBSTFromKeyboard() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        BSTree<Integer> bsTree = new BSTree<>();
        System.out.print("please enter root of tree: ");
        Integer root = scanner.nextInt();
        bsTree.add(root);
        while (true) {
            System.out.println("do you want to add a node? (yes|no)");
            if (scanner.next().equals("yes")) {
                System.out.print("please enter a integer number: ");
                try {
                    bsTree.add(scanner.nextInt());
                } catch (InputMismatchException e) {
                    System.out.println("you entered wrong character.");
                }
            } else {
                break;
            }
        }
        return bsTree;
    }

    public static BSTree<Integer> readBSTFromAFile() {
        ScannerWrapper scanner = ScannerWrapper.getInstance();
        BSTree<Integer> bsTree = new BSTree<>();
        System.out.print("please enter FilePath of tree: ");
        String fileAddress = scanner.next();
        File file;
        file = new File(fileAddress);
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));){
            bsTree = (BSTree<Integer>) input.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bsTree;
    }

    private static void printSpace(double n, Node removed) {
        for (; n > 0; n--) {
            System.out.print("\t");
        }
        if (removed == null) {
            System.out.print(" ");
        } else {
            System.out.print(removed.getData());
        }
    }

    public int heightOfTree() {
        return heightOfTree(root);
    }
    public static int heightOfTree(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(heightOfTree(root.getLeftChild()), heightOfTree(root.getRightChild()));
    }

    public static boolean isIdentical(Node<?> root1, Node<?> root2) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 != null && root2 == null) {
            return false;
        } else if (root1 == null && root2 != null) {
            return false;
        } else {
            return root1.getData().equals(root2.getData()) && isIdentical(root1.getLeftChild(), root2.getLeftChild()) && isIdentical(root1.getRightChild(), root2.getRightChild());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BSTree)) {
            return false;
        }
        BSTree<?> bsTree = (BSTree<?>) o;
        return isIdentical(bsTree.root, this.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }
}