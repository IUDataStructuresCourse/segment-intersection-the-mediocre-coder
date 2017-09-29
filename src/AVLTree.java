import java.util.function.BiPredicate;

/**
 * TODO: This is your second major task.
 * <p>
 * This class implements a generic height-balanced binary search tree,
 * using the AVL algorithm. Beyond the constructor, only the insert()
 * and remove() methods need to be implemented. All other methods are unchanged.
 */

public class AVLTree<K> extends BinarySearchTree<K> {
    boolean atRoot;
    Node errorNode;

    public AVLTree(BiPredicate<K, K> lessThan) {
        super(lessThan);
    }

    public Node insert(K key) {
        // Insert node normally
        if(search(key) != null){
            return null;
        }

        else{
            super.insert(key);
            Node newInsert = search(key);

            // Check balance
            if(isBalanced(root)){
                // If it is balanced then it needs no shifting.
                // Return Node and end method
                return newInsert;
            }

            // If not balanced, then shifting is needed
            else{
                balance(findErrorNode(root));

            }

            //Next perform a rotation on the node that is unbalanced
            //Figure what type of rotation is needed first
            return newInsert;
        }
    }

    public boolean violatesAVL(Node n){
        if(n == null){
            return false;
        }
        else if((Math.abs(get_height(n.left) - get_height(n.right)) < 2)){
            return false;
        }
        else{
            return true;
        }
    }

    public void rightRotation(Node n) {
        if(n.parent == null){
            atRoot = true;
        }

        Node temp = n.left;
        n.left = temp.right;
        temp.right = n;
        temp.parent = n.parent;
        n.parent = temp;

        if(!atRoot){
            temp.parent.right = temp;

        }
        if(atRoot){
            root = temp;
            root.parent = null;
        }
        if(n.left != null){
            n.left.parent = n;
        }


        n.updateHeight();
        temp.updateHeight();

        if(!atRoot){
            updateRestofTree(temp.parent);
        }
    }

    public void leftRotation(Node n){
        if(n.parent == null){
            atRoot = true;
        }

        Node temp = n.right;
        n.right = temp.left;
        temp.left = n;
        temp.parent = n.parent;
        n.parent = temp;

        if(!atRoot){
            temp.parent.left = temp;

        }
        if(atRoot){
            root = temp;
            root.parent = null;
        }
        if(n.right != null){
            n.right.parent = n;
        }


        n.updateHeight();
        temp.updateHeight();

        if(!atRoot){
            updateRestofTree(temp.parent);
        }

    }

    public void doubleRightRotation(Node n){
        Node temp = n;
        leftRotation(n.left);
        if(!isBalanced(root)) {
            rightRotation(n);
        }
    }

    public void doubleLeftRotation(Node n){
        Node temp = n;
        rightRotation(n.right);
        if(!isBalanced(root)) {
            leftRotation(n);
        }

    }

    public boolean isBalanced(Node n){
        if(n == null){
            return true;
        }
        else if(!(Math.abs(get_height(n.left) - get_height(n.right)) < 2)){
            return false;
        }
        else{
            return isBalanced(n.left) & isBalanced(n.right);
        }
    }

    public Node findErrorNode(Node n){
        if(n.isNodeBalanced()){
            return n.parent;
        }
        else{
            if(get_height(n.left) > get_height(n.right)){
                return findErrorNode(n.left);
            }
            else{
                return findErrorNode(n.right);
            }
        }
    }

    public void remove(K key) {
        // Insert node normally
        if(search(key) != null){
            super.remove(key);

            // Check balance
            if (!isBalanced(root)) {
                balance(findErrorNode(root));
            }
        }
    }

    public void balance(Node n){
        if(get_height(n.left) > get_height(n.right)){
            //check if double rotation needed
            if(get_height(n.left.right) > get_height(n.left.left)){
                doubleRightRotation(n);
            }
            else{
                rightRotation(n);
            }
        }

        // RIGHT > left
        else if(get_height(n.right) > get_height(n.left)){
            //check if double rotation needed
            if(get_height(n.right.left) > get_height(n.right.right)){
                doubleLeftRotation(n);

            }
            else{
                leftRotation(n);
            }
        }
    }

    public void updateRestofTree(Node n){
        n.updateHeight();
        if(n.parent!=null){
            updateRestofTree(n.parent);
        }
    }
}
