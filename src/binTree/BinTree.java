package binTree;

import java.util.Random;

public class BinTree {

	private int value;

	private BinTree left;
	private BinTree right;
	private BinTree parent;

	public BinTree(int value) {
		this.value = value;
	}

	public BinTree(int[] values) {
		if (values.length == 0)
			throw new IllegalArgumentException();
		this.value = values[0];

		for (int i = 1; i < values.length; ++i) {
			this.insert(values[i]);
		}
	}
	
	public BinTree(int count, int min, int max){
		if(count == 0) throw new IllegalArgumentException();
		Random r = new Random();
		for(int i = 1; i < count; ++i){
			this.insert(r.nextInt(min + max + 1) - min);
		}
	}

	public int getNodeCount() {
		if (isLeaf()){
			return 1;
		}
		int leftc = 0, rightc = 0;
		if(left != null){
			leftc = left.getNodeCount();
		}
		if(right != null){
			rightc = right.getNodeCount();
		}
		return 1 + leftc + rightc;
	}

	public int getHeight() {
		if (isLeaf()){
			return 1;
		}
		if(left == null) return right.getHeight() + 1;
		if(right == null) return left.getHeight() + 1;
		if (left.getHeight() > right.getHeight()) {
			return left.getHeight() + 1;
		}
		return right.getHeight() + 1;
		
	}
	
	public boolean isComplete(){
		return isComplete(this.getHeight());
	}

	private boolean isComplete(int height) {
		if((left == null) || (right == null)) return false;
		if(height == 2){
			if(!left.isLeaf() || !right.isLeaf()) return false;
			return true;
		}
		return left.isComplete(height - 1) && right.isComplete(height - 1);
	}
	
	public boolean isAVL(){
		if(isLeaf()) return true;
		if(left != null && !left.isAVL()) return false;
		if(right != null && !right.isAVL()) return false;
		if(left == null) return right.getHeight() < 2;
		if(right == null) return left.getHeight() < 2;
		return Math.abs(left.getHeight() - right.getHeight()) < 2;
	}

	public BinTree getNode(int x) {
		if (x == value) {
			return this;
		} else {
			if (left != null && left.getNode(x) != null)
				return left.getNode(x);
			if (right != null && right.getNode(x) != null)
				return right.getNode(x);
		}
		return null;
	}

	public BinTree getParentNode() {
		return parent;
	}

	public boolean search(int x) {
		return this.getNode(x) != null;
	}

	public void insert(int x) {
		if (x == value)
			throw new ArithmeticException();

		if (x < value) {
			if (left == null) {
				BinTree tmp = new BinTree(x);
				tmp.parent = this;
				left = tmp;
			} else {
				left.insert(x);
			}
		} else {
			if (right == null) {
				BinTree tmp = new BinTree(x);
				tmp.parent = this;
				right = tmp;
			} else {
				right.insert(x);
			}
		}
	}

	public void clear() {
		left = null;
		right = null;
		value = 0;
	}

	public void remove(int x) {
		if (!this.search(x))
			throw new ArithmeticException();

		if (x == value) {
			if (this.isLeaf()) {
				if (this.isRoot()) {
					this.clear();
				} else {
					if (this.parent.left == this)
						this.parent.left = null;
					if (this.parent.right == this)
						this.parent.right = null;
				}
			} else {
				BinTree t = this.getSmallestGreater();
				t.parent.left = t.right;

				if (t.parent.left == t)
					t.parent.left = null;
				if (t.parent.right == t)
					t.parent.right = null;

				if (left != null)
					left.parent = t;
				if (right != null)
					right.parent = t;

				t.left = left;
				t.right = right;
			}
		} else {
			this.getNode(x).remove(x);
		}
	}

	private BinTree getSmallestGreater() {
		if (right == null)
			return left;
		BinTree t = right;
		while (t.left != null) {
			t = t.left;
		}
		return t;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}

	public boolean isRoot() {
		return parent == null;
	}

	@Override
	public String toString() {
		return value + "; " + (left == null ? "" : left.toString())
				+ (right == null ? "" : right.toString());
	}

	public static void main(String[] args) {
		BinTree t = new BinTree(10);
		t.insert(5);
		t.insert(15);
		t.insert(3);
		t.insert(8);
		t.insert(12);
		t.insert(18);
		t.insert(11);
		t.insert(13);
		t.insert(17);
		t.insert(20);
		// t.insert(25);
		// t.insert(23);
		// t.insert(28);

		System.out.println(t);

		System.out.println(t.getHeight());
		System.out.println(t.getNodeCount());
		System.out.println(t.isComplete());
		System.out.println(t.isAVL());
		System.out.println("-----------");
		 
		t = new BinTree(10);
		t.insert(5);
		t.insert(15);
		
		System.out.println(t.isComplete());
		System.out.println(t);
		System.out.println(t.isAVL());

	}

}
