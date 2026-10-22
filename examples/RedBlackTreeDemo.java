import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

/** Original teaching example: conventional bottom-up red-black insertion.
 * Integer set: duplicate keys are ignored. null represents a BLACK NIL leaf.
 * No deletion; no external dependencies. Java 11+ source-file mode supported.
 */
public class RedBlackTreeDemo {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static final class Node {
        final int key;
        boolean color = RED;
        Node left, right, parent;
        Node(int key, Node parent) { this.key = key; this.parent = parent; }
    }

    private Node root;
    private final boolean trace;
    private int rotations;

    public RedBlackTreeDemo(boolean trace) { this.trace = trace; }

    private static boolean colorOf(Node n) { return n == null ? BLACK : n.color; }

    public boolean insert(int key) {
        Node p = null;
        Node cur = root;
        while (cur != null) {
            p = cur;
            if (key < cur.key) cur = cur.left;
            else if (key > cur.key) cur = cur.right;
            else {
                log("Duplicate " + key + ": unchanged");
                return false;
            }
        }
        Node z = new Node(key, p);
        if (p == null) root = z;
        else if (key < p.key) p.left = z;
        else p.right = z;
        rotations = 0;
        log("Attach " + key + " as RED (repair not finished)");
        fixAfterInsert(z);
        validate();
        require(rotations <= 2, "More than two insertion rotations");
        log("DONE " + key + "; rotations=" + rotations);
        return true;
    }

    private void fixAfterInsert(Node z) {
        while (z != root && colorOf(z.parent) == RED) {
            Node p = z.parent;
            Node g = p.parent; // Exists: a red parent cannot be the black root.
            if (p == g.left) {
                Node u = g.right;
                if (colorOf(u) == RED) {
                    p.color = BLACK;
                    u.color = BLACK;
                    g.color = RED;
                    log("RED UNCLE: P,U -> BLACK; G -> RED; continue at " + g.key);
                    z = g;
                } else {
                    if (z == p.right) {
                        z = p;
                        rotateLeft(z);
                        log("LR inner step: rotateLeft(" + z.key + ")");
                    }
                    // Re-read relatives: the inner rotation changes their roles.
                    p = z.parent;
                    g = p.parent;
                    p.color = BLACK;
                    g.color = RED;
                    rotateRight(g);
                    log("LL outer step: P -> BLACK; G -> RED; rotateRight(" + g.key + ")");
                }
            } else {
                Node u = g.left;
                if (colorOf(u) == RED) {
                    p.color = BLACK;
                    u.color = BLACK;
                    g.color = RED;
                    log("RED UNCLE: P,U -> BLACK; G -> RED; continue at " + g.key);
                    z = g;
                } else {
                    if (z == p.left) {
                        z = p;
                        rotateRight(z);
                        log("RL inner step: rotateRight(" + z.key + ")");
                    }
                    p = z.parent;
                    g = p.parent;
                    p.color = BLACK;
                    g.color = RED;
                    rotateLeft(g);
                    log("RR outer step: P -> BLACK; G -> RED; rotateLeft(" + g.key + ")");
                }
            }
        }
        root.color = BLACK;
    }

    // Promote x.right. Its left subtree beta becomes x.right.
    private void rotateLeft(Node x) {
        Node y = x.right;
        if (y == null) throw new IllegalArgumentException("Left rotation needs a right child");
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x;
        x.parent = y;
        rotations++;
    }

    // Promote x.left. Its right subtree beta becomes x.left.
    private void rotateRight(Node x) {
        Node y = x.left;
        if (y == null) throw new IllegalArgumentException("Right rotation needs a left child");
        x.left = y.right;
        if (y.right != null) y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.right = x;
        x.parent = y;
        rotations++;
    }

    public List<Integer> inOrder() {
        List<Integer> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node n, List<Integer> out) {
        if (n == null) return;
        inOrder(n.left, out);
        out.add(n.key);
        inOrder(n.right, out);
    }

    /** Validate ordering, parent links, red rule and black-path counts.
     * Returned count includes this node if black and the terminal NIL.
     * long bounds allow Integer.MIN_VALUE and Integer.MAX_VALUE as keys.
     */
    public void validate() {
        if (root == null) return;
        require(root.parent == null, "Root has parent");
        require(root.color == BLACK, "Root is red");
        check(root, null, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private int check(Node n, Node expectedParent, long low, long high) {
        if (n == null) return 1; // One black NIL, not zero.
        require(n.parent == expectedParent, "Bad parent at " + n.key);
        require(low < n.key && n.key < high, "BST ordering at " + n.key);
        if (n.color == RED) {
            require(colorOf(n.left) == BLACK && colorOf(n.right) == BLACK,
                    "Red-red at " + n.key);
        }
        int left = check(n.left, n, low, n.key);
        int right = check(n.right, n, n.key, high);
        require(left == right, "Unequal black paths at " + n.key);
        return left + (n.color == BLACK ? 1 : 0);
    }

    private static void require(boolean ok, String message) {
        if (!ok) throw new IllegalStateException(message);
    }

    // Compact exact representation: keyColor(left,right); . is a black NIL.
    private String shape(Node n) {
        if (n == null) return ".";
        return n.key + (n.color == RED ? "R" : "B")
                + "(" + shape(n.left) + "," + shape(n.right) + ")";
    }

    private void log(String event) {
        if (trace) System.out.println(event + "\n  " + shape(root));
    }

    private static void testSequence(int[] values) {
        RedBlackTreeDemo tree = new RedBlackTreeDemo(false);
        TreeSet<Integer> reference = new TreeSet<>();
        tree.validate();
        for (int value : values) {
            boolean expected = reference.add(value);
            require(tree.insert(value) == expected, "Duplicate policy");
            tree.validate();
            require(tree.inOrder().equals(new ArrayList<>(reference)), "Lost or extra key");
        }
    }

    private static int permutations(int[] a, int pos) {
        if (pos == a.length) { testSequence(a); return 1; }
        int count = 0;
        for (int i = pos; i < a.length; i++) {
            int tmp = a[pos]; a[pos] = a[i]; a[i] = tmp;
            count += permutations(a, pos + 1);
            tmp = a[pos]; a[pos] = a[i]; a[i] = tmp;
        }
        return count;
    }

    private static void selfTest() {
        int count = permutations(new int[] {1, 2, 3, 4, 5, 6, 7}, 0);
        Random random = new Random(6442);
        for (int run = 0; run < 20; run++) {
            int[] a = new int[200];
            for (int i = 0; i < a.length; i++) a[i] = random.nextInt(401) - 200;
            testSequence(a);
        }
        testSequence(new int[] {});
        testSequence(new int[] {Integer.MIN_VALUE, 0, Integer.MAX_VALUE,
                Integer.MIN_VALUE, Integer.MAX_VALUE, 0});
        testSequence(new int[] {41, 38, 31, 12, 19, 8});
        testSequence(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        System.out.println("PASS: " + count + " permutations; 20 seeded random sequences;"
                + " empty, boundary, duplicate and worked examples.");
    }

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--self-test")) {
            selfTest();
            return;
        }
        int[] values = args.length == 0 ? new int[] {10, 20, 30}
                : Arrays.stream(args).mapToInt(Integer::parseInt).toArray();
        RedBlackTreeDemo tree = new RedBlackTreeDemo(true);
        for (int value : values) tree.insert(value);
        System.out.println("In-order: " + tree.inOrder());
    }
}
