// import java.io.BufferedReader;
import java.io.IOException;
// import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


/**
 * LeetCode 706. Design HashMap
 * https://leetcode.com/problems/design-hashmap/
 */


/**
 * Designs and implements a HashMap without using 
 * any built-in hash table libraries.
 * 
 * Runtime: 13 ms, faster than 73.67% of Java online submissions
 * Memory Usage: 43.2 MB, less than 74.40% of Java online submissions
 */
class MyHashMap {
  
    // **** constant(s) ****
    final int INITIAL_CAPACITY      = 4;        // 4000 used in LeetCode
    final double LOAD_FACTOR        = 0.75;
    final double SHRINK_LOAD_FACTOR = 0.4;

    // **** class members ****
    private BST[]   map;
    public int      capacity;
    public int      entries;

    // **** constructor ****
    public MyHashMap() {
        this.map     = new BST[INITIAL_CAPACITY];
        this.capacity   = INITIAL_CAPACITY;
        this.entries    = 0;
    }

    // **** map a key to a bucket number ****
    private int hashFc(int key) {
        // return key % capacity;
        return key & (capacity - 1);
    }

    // **** computes load of hashmap ****
    private double load() {
        return (double)this.entries / (double)this.capacity;
    }

    // **** key will always be non-negative ****
    public void put(int key, int value) {
 
        // **** ****
        int entCnt = this.entries;

        // **** compute the bucket number ****
        int bucket = hashFc(key);

        // **** set the key value pair in the map ****
        if (this.map[bucket] == null) {
            this.map[bucket] = new BST(key, value);
            this.entries++;
        } else {

            // **** check if this bucket contains the key ****
            BSTNode f = this.map[bucket].contains(key);

            // **** update value associated with existing key - value pair ****
            if (f != null) {
                f.value = value;
            }

            // **** insert new key - value pair ****
            else {
                this.map[bucket].insert(key, value);
                this.entries++;
            }
        }

        // **** check the load of the map and increase capacity (if needed) ****
        if (this.entries != entCnt) {
            grow();
        }
    }

    // **** returns the value to which the specified key is mapped, 
    //      or -1 if this map contains no mapping for the key ****
    public int get(int key) {
 
        // **** compute the bucket number ****
        int bucket = hashFc(key);

        // **** return the associated value ****
        if (this.map[bucket] == null) {
            return -1;
        } else {

            // **** get node associated with specified key ****
            BSTNode n = this.map[bucket].contains(key);

            // **** key:value pair not in bucket ****
            if (n == null)
                return -1;

            // **** ****
            return n.value;
        }
    }

    // **** remove the mapping of the specified value key 
    //      if this map contains a mapping for the key ****
    public void remove(int key) {
 
        // **** compute the bucket number ****
        int bucket = hashFc(key);

        // **** check if no key:value pairs in this bucket ****
        if (this.map[bucket] == null)
            return;

        // **** remove key:value pair node from bucket (if contained) ****
        this.map[bucket].delete(key);

        // **** decrement count of entries ****
        this.entries--;

        // **** check and shrink the map (if needed) ****
        shrink();
    }

    // **** check the load of the map and increase the capacity (if needed) ****
    private void grow() {

        // **** determine if we need to grow the map ****
        double load = load();
        if (load < LOAD_FACTOR)
            return;

        // **** grow the map ****
        this.map = Arrays.copyOf(this.map, this.capacity *= 2);
    }

    // **** shrinking the capacity of the map left
    //      as an exercise for the ambitious reader ****
    private void shrink() {

        // **** determine if we need to shrink the map ****
        double load = load();
        if (this.capacity >= INITIAL_CAPACITY && load < SHRINK_LOAD_FACTOR)
            return;
    }

    // **** for testing purpose only ****
    @Override
    public String toString() {

        // **** initialization ****
        StringBuilder sb    = new StringBuilder();
        List<BSTNode> lst   = new LinkedList<BSTNode>();

        // **** collect hashmap info ****
        sb.append("[c:" + this.capacity);
        sb.append(" e:" + this.entries);
        sb.append(" l:" + load() + "] ");

        // **** traverse the map ****
        for (int bucket = 0; bucket < map.length; bucket++) {

            // **** skip BST if null ****
            if (this.map[bucket] == null)
                continue;

            // **** collect nodes into list ****
            this.map[bucket].toList(this.map[bucket].tree, lst);
        }

        // **** comparator by key ****
        Comparator<BSTNode> compareByKey = (BSTNode n1, BSTNode n2) -> {
            if (n1.key > n2.key)
                return 1;
            else if (n1.key < n2.key)
                return -1;
            else 
                return 0;
        };

        // **** sort list ****
        Collections.sort(lst, compareByKey);

        // **** build string ****
        lst.forEach( n -> {
            sb.append(n.toString());
        });

        // **** return string ****
        return sb.toString();
    }
}


/**
 * Design a hashmap in Java.
 * Fourth and last approach.
 * Please note that the related project names are NOT in ascending order.
 */
public class DesignHashMap3 {

    /**
     * Test scaffolding
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        /*
        // !!!! experiment with the BST !!!!
        // **** open a buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** create and populate an array with input line ****
        int arr[] = Arrays.stream(br.readLine().split(",")).mapToInt(Integer::parseInt).toArray();

        // ???? ????
        System.out.println("main <<< arr: " + Arrays.toString(arr));

        // **** close the buffered reader ****
        br.close();

        // **** shuffle the array ****
        Integer[] tmp       = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        List<Integer> lst   = Arrays.asList(tmp);
        Collections.shuffle(lst);
        int[] sarr          = lst.stream().mapToInt(i -> i).toArray();

        // **** use array instead of sorted array ****
        // int[] sarr = Arrays.copyOf(arr, arr.length);

        // ???? ????
        System.out.println("main <<< sarr: " + Arrays.toString(sarr));

        // **** root for BST ****
        BST tree = new BST();

        // **** populate BST ****
        for (int key : sarr) {
            tree.insert(key, key * 2);
        }

        // **** in-order traversal of the BST ****
        System.out.print("main <<< inOrder: ");
        tree.inOrder();

        // **** display the depth of the BST ****
        System.out.println("main <<< depth: " + tree.depth());

        // **** delete all nodes from the BST ****
        for (int k = -1; k <= arr.length; k++) {

            // ???? ????
            System.out.println("main <<< k: " + k);

            // **** delete this node ****
            tree.delete(k);

            // **** in-order traversal of the BST ****
            System.out.print("main <<< inOrder: ");
            tree.inOrder();

            // **** display the depth of the BST ****
            System.out.println("main <<< depth: " + tree.depth());   
        }
                
        // !!!! experiment with the hashmap !!!!
        System.out.println();
        */


        // **** create my hash map ****
        MyHashMap hm = new MyHashMap();
     
        // **** put key:value pair ****
        hm.put(1, 1);
     
        // ???? ????
        System.out.println("main <<< hm: " + hm.toString());

        // **** put key:value pair ****
        hm.put(2, 2);
     
        // ???? ????
        System.out.println("main <<< hm: " + hm.toString());
     
        // **** get value associated specified key ****
        System.out.println("main <<< hm.get(1): " + hm.get(1));
     
        // **** get value associated specified key ****
        System.out.println("main <<< hm.get(3): " + hm.get(3));
     
        // **** put key:value pair ****
        hm.put(2, 1);
     
        // ???? ????
        System.out.println("main <<< hm: " + hm.toString());
     
        // **** get value associated specified key ****
        System.out.println("main <<< hm.get(2): " + hm.get(2));
     
        // *** remove key:value pair for specified key ****
        hm.remove(2);
     
        // ???? ????
        System.out.println("main <<< hm: " + hm.toString());
     
        // **** get value associated specified key ****
        System.out.println("main <<< hm.get(2): " + hm.get(2));

        // **** put key:value pairs ****
        for (int key = 2; key <= 16; key++) {

            hm.put(key, key * 2);

            // ???? ????
            System.out.println("main <<< hm: " + hm.toString());
        }
    }
  }