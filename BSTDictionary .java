public class BSTDictionary<E, K extends Sortable> implements Dictionary<E,K>{
		protected BSTNode<E, K> tree; // The root of the Binary Search Tree.

		boolean isEmpty() {
			return tree == null;
		} // isEmpty method

		public E search(K key) {
			BSTNode<E, K> node = recSearch(tree, key);
			if (node == null) {
				return null;
			} else {
				return node.getElement();
			}
		} // search method

		protected BSTNode<E, K> recSearch(BSTNode<E, K> node, K key) {
			if ((node == null) || (key.compareTo(node.getKey()) == 0)) {
				return node;
			} else {
				if (key.compareTo(node.getKey()) < 0) {
					return recSearch(node.getLeft(), key);
				} else {
					return recSearch(node.getRight(), key);
				}
			}
		} // recSearch method

		public void insert(K key, E element) {
			tree = recInsert(tree, key, element);
		} // insert method

		protected BSTNode<E, K> recInsert(BSTNode<E, K> node, K key, E element) {
			if (node == null) {
				node = new BSTNode<E, K>(key, element, null, null);
			} else if (key.compareTo(node.getKey()) == 0) {
				node.setElement(element);
			} else if (key.compareTo(node.getKey()) < 0) {
				node.setLeft(recInsert(node.getLeft(), key, element));
			} else {
				node.setRight(recInsert(node.getRight(), key, element));
			}
			return node;
		} // recInsert method

		public void delete(K key) {
			tree = recDelete(tree, key);
		} // delete method

		protected BSTNode<E, K> recDelete(BSTNode<E, K> node, K key) {
			if (node == null) {
				// Unable to find node containing key
				return null;
			} else if (key.compareTo(node.getKey()) == 0) {
				node = remove(node);
			} else if (key.compareTo(node.getKey()) < 0) {
				node.setLeft(recDelete(node.getLeft(), key));
			} else {
				node.setRight(recDelete(node.getRight(), key));
			}
			return node;
		} // recDelete method

		protected BSTNode<E, K> remove(BSTNode<E, K> node) {
			BSTNode<E, K> here = node;
			if (node.getLeft() == null) {
				return node.getRight();
			} else if (node.getRight() == null) {
				return node.getLeft();
			} else {
				// find in-order successor (leftmost node in right subtree)
				// also, keep track of immediate predecessor
				node = node.getRight();
				BSTNode<E, K> temp = node;
				BSTNode<E, K> prev = here;
				while (temp.getLeft() != null) {
					prev = temp;
					temp = temp.getLeft();
				}
				// we cannot simply swap TEMP and NODE, as BSTNode does not
				// provide a method to set the key value. So instead, we move
				// TEMP into NODE's position in three steps:
				// 1) make original NODE's left subtree TEMP's left subtree
				temp.setLeft(here.getLeft());
				// 2) TEMP had no left subtree, so move right subtree up to PREV
				if (prev != here) {
					prev.setLeft(temp.getRight());
				} else {
					prev.setRight(temp.getRight());
				}
				// 3) make original NODE's right subtree TEMP's right subtree
				temp.setRight(here.getRight());
				// return TEMP as new root of that subtree
				return temp;
			}
		} // remove method

		public void printTree() {
			recPrintTree(tree);
			System.out.println();
		} // printTree method

		protected void recPrintTree(BSTNode<E, K> node) {
			if (node == null)
				return;
			recPrintTree(node.getLeft());
			System.out.print(node.getKey() + " ");
			recPrintTree(node.getRight());
		} // recPrintTree method

		public int depth() {
			return recDepth(tree);
		} // depth method

		protected int recDepth(BSTNode<E, K> node) {
			if (node == null) {
				return 0;
			} else {
				return 1 + Math.max(recDepth(node.getLeft()), recDepth(node
						.getRight()));
			}
		} // recDepth method
	} /* BSTDictionary class */
