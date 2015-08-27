package trees;

import java.util.*;
import java.lang.Exception;

public class BinaryHeap {
	private static final int DEFAULT_CAPCITY = 10;
	private int currentsize;
	private int[] array;

	public BinaryHeap() {
		currentsize = 0;
		array = new int[11];
		bulidHeap();
	}

	private void bulidHeap() {
		for (int i = currentsize / 2; i > 0; i--)
			percolateDown(i);

	}

	private void percolateDown(int hole) {
		int child;
		int tmp = array[hole];
		for (; hole * 2 <= currentsize; hole = child) {
			child = hole * 2;
			if (child != currentsize && array[child + 1] > array[child])
				child++;
			if (array[child] > tmp)
				array[hole] = array[child];
			else
				break;
		}
		int t1 = array[hole];
		array[hole] = tmp;
	}

	public void insert(int x) {
		if (currentsize < array.length) {

			int hole = ++currentsize;
			array[hole] = x;
			bulidHeap();
			// for(;hole>1&&x<array[hole/2];hole/=2)
			// array[hole]=array[hole/2];
			// array[hole]=x;
		}
	}

	public int deleteMin() throws Exception {
		if (isEmpty())
			throw new Exception("this is NUll");
		int minItem = array[1];
		array[1] = array[currentsize--];
		percolateDown(1);
		return minItem;
	}

	// public void enlargeArray(int s){
	// newArray= (int[])new Object[s];
	// / for(int i=0;i<array.length;i++){
	// / newArray[i]=array[i];
	// / }
	// / array=newArray;
	// / }
	public boolean isEmpty() {
		if (currentsize == 0)
			return false;
		return true;
	}

	public boolean isFull() {
		return currentsize == array.length - 1;
	}

	public int findMax() throws Exception {
		if (isEmpty())
			throw new Exception("this this null");
		return array[1];

	}

	public void print() {
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}

	}

	public static void main(String[] argv) {
		BinaryHeap a = new BinaryHeap();
		int[] b = { 7, 12, 1, 3, 22, 5, 11 };
		for (int i = 0; i < b.length; i++) {
			a.insert(b[i]);
		}
		a.print();

	}
}
