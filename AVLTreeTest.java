public class AVLTreeTest{
    private static String[] nums = {"a","b","c","d","e","f","g"};
    public static void main(String[] args) {
        AVLTree<String> tree = new AVLTree<String>();

        for(int i=0;i<nums.length;i++){
            tree.insert(nums[i]);
            System.out.printf("%s ", nums[i]);
        }

        System.out.printf("== height: %d\n", tree.height());
        // System.out.printf("== min: %d\n", tree.minimum());
        // System.out.printf("== max: %d\n", tree.maximum());

        System.out.print("\n====inorder:");
        tree.inOrder();
        System.out.println();
        tree.print();
        tree.search("b");

        tree.delete("e");
        tree.delete("c");
        // tree.delete(11);
        System.out.printf("== height: %d\n", tree.height());
        tree.inOrder();
        //System.out.println();
        //tree.print();
    }
}