/**
 * Node class for a 2-3 tree
 * Created by Alienware on 8/14/2017.
 */
public class TwoThreeNode {

        protected Integer leftData;

        protected Integer rightData;

        protected TwoThreeNode left, right, middle, parent, temp;

        public TwoThreeNode(Integer leftData, Integer rightData, TwoThreeNode left, TwoThreeNode right, TwoThreeNode middle, TwoThreeNode parent) {
            this.leftData = leftData;
            this.rightData = rightData;
            this.left = left;
            this.right = right;
            this.middle = middle;
            this.parent = parent;

        } // TwoThreeNode constructor

        public Integer getLeftData() {
            return leftData;
        } // getLeftData method

        public Integer getRightData() {
            return rightData;
        } // getRightData method

        public TwoThreeNode getLeftChild() {
            return left;
        } // getLeft method

        public TwoThreeNode getRightChild() {
            return right;
        } // getRight method

         public TwoThreeNode getMiddleChild() {
             return middle;
          } // getMiddle method

        public TwoThreeNode getParent(){
            return parent;
        }

        public void setLeftData(Integer data) {
            this.leftData = data;
        } // setLeft method

        public void setRightData(Integer data) {
            this.rightData = data;
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

