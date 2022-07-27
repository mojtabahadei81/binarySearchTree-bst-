package ir.ac.kntu;

import java.util.InputMismatchException;

public class UI {

    private final ScannerWrapper scanner = ScannerWrapper.getInstance();

    private BSTree<Integer> bsTree;

    public UI() {
        this.bsTree = new BSTree<>();
    }

    public enum UserStartOptions {
        READ_A_BST_FROM_KEYBOARD, READ_A_BST_FROM_A_FILE, EXIT
    }

    public void start() {
        Menus.printStartMenu();
        UserStartOptions[] userOptions = UserStartOptions.values();
        UserStartOptions userOption;
        while (true) {
            System.out.print("What do you want to do?\nEnter its number: ");
            try {
                int number = scanner.nextInt();
                userOption = userOptions[number - 1];
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You entered a number outside the range.");
            } catch (InputMismatchException e) {
                System.out.println("You entered wrong character. you must enter a number in range.");
            } finally {
                scanner.nextLine();
            }
        }
        switch (userOption) {
            case READ_A_BST_FROM_KEYBOARD -> {
                bsTree = BSTree.readBSTFromKeyboard();
                enterInBSTree();
            }
            case READ_A_BST_FROM_A_FILE -> {
                bsTree = BSTree.readBSTFromAFile();
                enterInBSTree();
            }
            default -> {
            }
        }
    }

    public enum EnteredBSTOption {
        SHOW_TREE_IN_TERMINAL, PRINT_ELEMENTS_OF_TREE_IN_ORDER, ADD_A_NODE, REMOVE_A_NODE, UPDATE_A_NODE,
        SEARCH_A_VALUE, COMPARE_TWO_TREE, RETURN_DEPTH_OF_TREE, WRITE_BST_TO_A_FILE, EXIT
    }

    public void enterInBSTree() {
        Menus.printEnteredBSTreeMenu();
        EnteredBSTOption[] enteredBSTOptions = EnteredBSTOption.values();
        EnteredBSTOption enteredBSTOption;
        while (true) {
            System.out.print("What do you want to do?\nEnter its number: ");
            try {
                int number = scanner.nextInt();
                enteredBSTOption = enteredBSTOptions[number - 1];
                break;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You entered a number outside the range.");
            } catch (InputMismatchException e) {
                System.out.println("You entered wrong character. you must enter a number in range.");
            } finally {
                scanner.nextLine();
            }
        }
        switch (enteredBSTOption) {
            case SHOW_TREE_IN_TERMINAL -> bsTree.show();
            case PRINT_ELEMENTS_OF_TREE_IN_ORDER -> bsTree.postOrder();
            case ADD_A_NODE -> addANode();
            case REMOVE_A_NODE -> removeANode();
            case UPDATE_A_NODE -> updateKeyOfANode();
            case SEARCH_A_VALUE -> searchAValue();
            case COMPARE_TWO_TREE -> compareTwoTree();
            case RETURN_DEPTH_OF_TREE -> printDepthOfTree();
            case WRITE_BST_TO_A_FILE -> BSTree.write(bsTree);
            default -> {
                bsTree = null;
                start();
                return;
            }
        }
        enterInBSTree();
    }

    public void addANode() {
        do {
            System.out.print("enter number: ");
            try {
                int number = scanner.nextInt();
                bsTree.add(number);
            } catch (InputMismatchException e) {
                System.out.println("You entered wrong character. you must enter a number in range.");
                scanner.nextLine();
            }
            System.out.println("do you want to add another number? (yes|no)");
        } while (scanner.next().equals("yes"));
    }

    public void removeANode() {
        do {
            System.out.print("enter number: ");
            try {
                int number = scanner.nextInt();
                bsTree.deleteKey(number);
            } catch (InputMismatchException e) {
                System.out.println("You entered wrong character. you must enter a number in range.");
                scanner.nextLine();
            }
            System.out.println("do you want to remove another number? (yes|no)");
        } while (scanner.next().equals("yes"));
    }

    public void searchAValue() {
        do {
            System.out.print("enter number: ");
            try {
                int number = scanner.nextInt();
                bsTree.search(number);
            } catch (InputMismatchException e) {
                System.out.println("You entered wrong character. you must enter a number in range.");
                scanner.nextLine();
            }
            System.out.println("do you want to search another number? (yes|no)");
        } while (scanner.next().equals("yes"));
    }

    public void compareTwoTree() {
        System.out.println("enter a BSTree to check equality.");
        BSTree<Integer> bsTree1 = readATree();
        if (bsTree1 == null) {
            return;
        }
        if (bsTree1.equals(bsTree)) {
            System.out.println("the BSTree that you entered now is equal to this BSTree.");
        } else {
            System.out.println();
        }
    }

    public BSTree<Integer> readATree() {
        System.out.println("read from:\n" + " 1)Keyboard\n 2)File");
        BSTree<Integer> bsTree = new BSTree<>();
        try {
            int number = scanner.nextInt();
            if (number == 1) {
                bsTree = BSTree.readBSTFromKeyboard();
            } else if (number == 2) {
                bsTree = BSTree.readBSTFromAFile();
            } else {
                System.out.println("you entered wrong number.");
            }
        } catch (InputMismatchException e) {
            System.out.println("You entered wrong character. you must enter a number in range.");
            return null;
        }
        return bsTree;
    }

    public void printDepthOfTree() {
        System.out.println(bsTree.heightOfTree());
    }

    public void updateKeyOfANode() {
        try {
            System.out.print("Enter the value of the node you want to change: ");
            Integer number = scanner.nextInt();
            System.out.print("Enter the replacement value: ");
            Integer number2 = scanner.nextInt();
            bsTree.deleteKey(number);
            bsTree.add(number2);
        } catch (InputMismatchException e) {
            System.out.println("You entered wrong character. you must enter a number in range.");
        }
    }
}
