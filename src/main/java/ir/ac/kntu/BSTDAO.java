package ir.ac.kntu;

import java.io.*;
import java.util.ArrayList;

public class BSTDAO {

    private final String filePath;

    private final File file;

    public BSTDAO(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
    }

    public ArrayList<BSTree> getAllTrees() {
        ArrayList<BSTree> savedTrees = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file); ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
            while (true) {
                try {
                    BSTree bsTree = (BSTree) input.readObject();
                    savedTrees.add(bsTree);
                } catch (EOFException e) {
                    break;
                } catch (Exception e) {
                    System.out.println("Problem with some of the records in the binarySearchTree(BST) data file");
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("No previous data for BST has been saved.");
        }
        return savedTrees;
    }

    public void saveAllTrees(ArrayList<BSTree> trees) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file); ObjectOutputStream output = new ObjectOutputStream(fileOutputStream)) {
            for (BSTree m : trees) {
                try {
                    output.writeObject(m);
                } catch (IOException e) {
                    System.out.println("An error occurred while trying to save info");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while trying to save info");
            System.out.println(e.getMessage());
        }
    }

    public void updateTree(BSTree bsTree) {
        ArrayList<BSTree> trees = getAllTrees();
        if (trees.contains(bsTree)) {
            trees.set(trees.indexOf(bsTree), bsTree);
            System.out.println("successfully updated.");
        } else {
            trees.add(bsTree);
            System.out.println("successfully added.");
        }
        saveAllTrees(trees);
    }

    public void deleteTree(BSTree tree) {
        ArrayList<BSTree> trees = getAllTrees();
        if (trees.contains(tree)) {
            trees.remove(tree);
            System.out.println("successfully removed.");
        } else {
            System.out.println("No tree have been registered with this profile.");
        }
        saveAllTrees(trees);
    }
}
