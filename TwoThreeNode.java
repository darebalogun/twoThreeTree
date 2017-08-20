/**
 * Node class for a 2-3 tree
 * Created by Oludare balogun on 8/14/2017.
 */
public class TwoThreeNode<E, K extends Comparable<K>> {

        protected KeyValuePair<E,K> leftData;

        protected KeyValuePair<E,K> rightData;

        protected TwoThreeNode<E,K> left, right, middle, parent, temp;

        public TwoThreeNode(E leftData, K leftKey, E rightData, K rightKey, TwoThreeNode left, TwoThreeNode right, TwoThreeNode middle, TwoThreeNode parent) {
            this.leftData = new KeyValuePair(leftKey, leftData);
            if (rightData == null){
                this.rightData = null;
            } else {
                this.rightData = new KeyValuePair(rightKey, rightData);
            }
            this.left = left;
            this.right = right;
            this.middle = middle;
            this.parent = parent;
            this.temp = null;
        } // TwoThreeNode constructor

        public KeyValuePair<E,K> getLeftData() {
            return leftData;
        } // getLeftData method

        public KeyValuePair<E,K> getRightData() {
            return rightData;
        } // getRightData method

        public TwoThreeNode<E,K> getLeftChild() {
            return left;
        } // getLeft method

        public TwoThreeNode<E,K> getRightChild() {
            return right;
        } // getRight method

        public TwoThreeNode<E,K> getMiddleChild() {
             return middle;
          } // getMiddle method

        public TwoThreeNode<E,K> getParent(){
            return parent;
        } // getParet

        public void setLeftData(KeyValuePair value) {
            this.leftData = value;
        } // setLeft method

        public void setRightData(KeyValuePair value) {
            this.rightData = value;
         } // setRight method

        public void setLeftChild(TwoThreeNode node) {
            left = node;
        } // setLeft method

        public void setRightChild(TwoThreeNode node) {
            right = node;
        } // setRight method

        public void setMiddleChild(TwoThreeNode node) {
            middle = node;
        }
    } /* TwoThreeNode class */

