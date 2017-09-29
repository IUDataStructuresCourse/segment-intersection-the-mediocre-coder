

import static org.junit.Assert.*;

import java.util.*;
import java.util.function.BiPredicate;

import org.junit.Test;

import java.util.Random;

public class MyTests {

    @Test
    public void rebuildSmallAVL() {
        AVLTree<Integer> avl = new AVLTree<>((Integer x, Integer y) -> x < y);
        int[] a = new int[]{4, 8,0,2,6,10};
        for (Integer key : a) {

            avl.insert(key);
            assertTrue(verifyAVL(avl.root));
        }
        int n = a.length;
        avl.remove(4);
        avl.remove(8);
        //avl.remove(0);
        System.out.println(get_height(avl.search(6)));
        System.out.println(get_height(avl.search(2)));
        System.out.println(get_height(avl.search(10)));



    }

    private <K> int get_height(BinarySearchTree<K>.Node p) {
        if (p == null) return -1;
        else return p.height;
    }

    private <K> boolean verifyHeights(BinarySearchTree<K>.Node p) {
        if (p == null)
            return true;
        int h1 = get_height(p.left);
        int h2 = get_height(p.right);
        return p.height == 1 + Math.max(h1, h2) &&
                verifyHeights(p.left) && verifyHeights(p.right);
    }

    private <K> boolean verifyAVL(BinarySearchTree<K>.Node p) {
        if (p == null)
            return true;
        int h1 = get_height(p.left);
        int h2 = get_height(p.right);
        if (Math.abs(h1 - h2) > 1) {
            System.out.println("Not AVL");
            System.out.println(p);
            return false;
        } else
            return verifyAVL(p.left) && verifyAVL(p.right);
    }

    private <K> boolean verifyParentPointers(BinarySearchTree<K>.Node root) {
        if (root == null)
            return true;
        if (root.parent != null)
            return false;
        return verifyParentPointersHelper(root, root.left) &&
                verifyParentPointersHelper(root, root.right);
    }

    private <K> boolean verifyParentPointersHelper(BinarySearchTree<K>.Node p, BinarySearchTree<K>.Node q) {
        if (q == null)
            return true;
        if (q.parent != p)
            return false;
        return
                verifyParentPointersHelper(q, q.left) && verifyParentPointersHelper(q, q.right);
    }

    private <K>
    boolean verifyOrderingProperty(BinarySearchTree<K>.Node p, BiPredicate<K, K> lessThan) {
        if (p == null) return true;
        K key = p.data;
        return allLessThan(p.left, key, lessThan) && allGreaterThan(p.right, key, lessThan) &&
                verifyOrderingProperty(p.left, lessThan) && verifyOrderingProperty(p.right, lessThan);
    }

    private <K>
    boolean allGreaterThan(BinarySearchTree<K>.Node p, K x, BiPredicate<K, K> lessThan) {
        if (p == null) return true;
        return !lessThan.test(p.data, x) &&
                allGreaterThan(p.left, x, lessThan) && allGreaterThan(p.right, x, lessThan);
    }

    private <K>
    boolean allLessThan(BinarySearchTree<K>.Node p, K x, BiPredicate<K, K> lessThan) {
        if (p == null) return true;
        return lessThan.test(p.data, x) &&
                allLessThan(p.left, x, lessThan) && allLessThan(p.right, x, lessThan);
    }

}
