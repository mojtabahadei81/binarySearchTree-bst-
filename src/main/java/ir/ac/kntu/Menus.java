package ir.ac.kntu;

public class Menus {
    public static void printStartMenu() {
        System.out.println("1) enter information of a tree.\n" +
                "2) read a BST from a file.\n" +
                "3) exit.\n");
    }

    public static void printEnteredBSTreeMenu() {
        System.out.println("1) show tree in terminal.\n" +
                "2) print element of tree in order.\n" +
                "3) add a value to this tree.\n" +
                "4) remove a value from this tree.\n" +
                "5) update value of a node.\n" +
                "6) search a value in this tree.\n" +
                "7) checking the equality of a tree and this tree.\n" +
                "8) print depth of this tree.\n" +
                "9) write this tree to a file.\n" +
                "10) exit from this tree.\n");
    }
}
