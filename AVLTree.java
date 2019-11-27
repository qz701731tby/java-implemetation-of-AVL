public class AVLTree<T extends Comparable<T>>{
    private AVLTreeNode<T> mRoot;    //rootnode

    class AVLTreeNode<T>{
        T val;                
        int height;         
        AVLTreeNode<T> left;    
        AVLTreeNode<T> right;    

        public AVLTreeNode(T val, AVLTreeNode<T> left, AVLTreeNode<T> right) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }
    
    public int max(int a, int b){
        return a>b?a:b;
    }

    private int height(AVLTreeNode<T> tree) {
        if (tree != null)
            return tree.height;

        return 0;
    }

    public int height() {
        return height(mRoot);
    }

    private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1;

        k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = max( height(k2.left), height(k2.right)) + 1;
        k1.height = max( height(k1.left), k2.height) + 1;

        return k1;
    }

    private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> k1) {
        AVLTreeNode<T> k2;

        k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = max(height(k1.left), height(k1.right))+1;
        k2.height = max(height(k2.right), k1.height)+1;

        return k2;
    }

    private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> k3) {
        k3.left = rightRightRotation(k3.left);
        return leftLeftRotation(k3);
    }

    private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> k1) {
        k1.right = leftLeftRotation(k1.right);
        return rightRightRotation(k1);
    }

    private AVLTreeNode<T> insert(AVLTreeNode<T> root, T val){
        if(root==null){
            root = new AVLTreeNode<T>(val, null, null);
        }
        else{
            int cmp=val.compareTo(root.val);

            if(cmp<0){
                root.left = insert(root.left, val);
                if(height(root.left)-height(root.right)==2){
                    if(val.compareTo(root.left.val)<0){
                        root = leftLeftRotation(root);
                    }else{
                        root = leftRightRotation(root);
                    }
                }
            }else if(cmp>0){
                root.right = insert(root.right, val);
                if(height(root.right)-height(root.left)==2){
                    if(val.compareTo(root.right.val)>0){
                        root = rightRightRotation(root);
                    }else{
                        root = rightLeftRotation(root);
                    }
                }
            }else{
                System.out.println("Unable to insert the same val node!!!");
            }
        }

        root.height = max(height(root.left), height(root.right)) + 1;

        return root;
    }

    public void insert(T val){
        mRoot = insert(mRoot, val);
    }

    //delete node
    private AVLTreeNode<T> delete(AVLTreeNode<T> root, T val){
        if(root==null) return null;

        int cmp = val.compareTo(root.val);
        if(cmp<0){
            root.left = delete(root.left, val);
            if(height(root.right)-height(root.left)==2){
                AVLTreeNode<T> r = root.right;
                if(height(r.right)>height(r.left)){
                    root = rightRightRotation(root);
                }
                else{
                    root = rightLeftRotation(root);
                }
            }
        }
        else if(cmp>0){
            root.right = delete(root.right, val);
            if(height(root.left)-height(root.right)==2){
                AVLTreeNode<T> l = root.left;
                if(height(l.left)>height(l.right)){
                    root = leftLeftRotation(root);
                }
                else{
                    root = leftRightRotation(root);
                }
            }
        }
        else{
            if((root.left!=null)&&(root.right!=null)){
                if(height(root.left)>height(root.right)){
                    AVLTreeNode<T> max = maximum(root.left);
                    root.val = max.val;
                    root.left = delete(root.left, max.val); //what's the outcome of not reassgin root.left?
                }
                else{
                    AVLTreeNode<T> min = minimum(root.right);
                    root.val = min.val;
                    root.right = delete(root.right, min.val);
                }
                
            }
            else{
                AVLTreeNode<T> tmp = root;
                root = (tmp.left!=null)?tmp.left:tmp.right;
                tmp = null;               
            }
        }
        if(root!=null) root.height = max(height(root.left), height(root.right)) + 1;

        return root;
    }

    public void delete(T val){
        if(!search(mRoot, val)){
            System.out.println("The value doesn't exist!!!");
        }
        else{
            delete(mRoot, val);
        }
    }

    private AVLTreeNode<T> maximum(AVLTreeNode<T> root){
        if(root==null) return null;
        while(root.right!=null){
            root = root.right;
        }
        return root;
    }

    private AVLTreeNode<T> minimum(AVLTreeNode<T> root){
        if(root==null) return null;
        while(root.left!=null){
            root = root.left;
        }
        return root;
    }

    private boolean search(AVLTreeNode<T> root, T val){
        int cmp=val.compareTo(root.val);
        if((root.left==null)&&(root.right==null)&&(cmp!=0)) return false;

        if(cmp==0){
            return true;
        }
        else if(cmp<0){
            return search(root.left, val);
        }
        else{
            return search(root.right, val);
        }

    }

    public void search(T val){
        if(search(mRoot, val)){
            System.out.println("Exist!");
        }
        else{
            System.out.println("Doesn't exist!!!");
        }
    }

    private void inOrder(AVLTreeNode<T> root){
        if(root!=null){
            if(root.left!=null) inOrder(root.left);
            System.out.print(root.val + " ");
            if(root.right!=null) inOrder(root.right);
        }   
    }

    public void inOrder(){
        inOrder(mRoot);
    }

    private void print(AVLTreeNode<T> root, T val, int direction){
        if(root!=null){
            if(direction==0) System.out.printf(root.val + " is root\n");
            else System.out.printf(root.val + " is " + val + "'s "+ (direction==1?"right" : "left") +"child\n");
            print(root.left, root.val, -1);
            print(root.right, root.val, 1);
        }
    }

    public void print(){
        if(mRoot!=null){
            print(mRoot, mRoot.val, 0);
        }
    }
}

