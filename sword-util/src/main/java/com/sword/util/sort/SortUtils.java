/**
 * 
 */
package com.sword.util.sort;

import java.util.Random;

/**
 * @author mengfanyuan
 *
 */
public class SortUtils {
	
	
	

	/**
	 * 随机数组生成
	 * @return
	 */
	public static int[] generateArray(){
		int[] array = new int[20];
		Random random = new Random();
		for (int i = 0; i < array.length;i++) {
			array[i] = random.nextInt(99);
		}
		return array;
	}
	
	/**
	 * 数组打印
	 * @param array
	 */
	public static void printArray(int[] array){
		StringBuilder sb = new StringBuilder("[");
		for(int value : array){
			if (sb.length()>1) {
				sb.append(",").append(value);
			} else {
				sb.append(value);
			}
		}
		sb.append("]");
		System.out.println(sb.toString());
	}
	
	
	/**
	 * 快速排序
	 * @param v
	 * @param left
	 * @param right
	 */
	public static void quickSort(int[] v, int left, int right){
		if (left < right) {
			int l = left;
			int h = right;
			int key = v[l];
			while (l < h) {
				//从右向左查找第一个比可以小的值
				while (l < h && v[h] >= key) {
					h--;
				}
				//h位置的数据比key小，将h位置的数据放到l位置
				v[l] = v[h];
				//从左向右查到第一个比可以大的值
				while (l < h && v[l] <= key ) {
					l++;
				}
				//l位置的数据比key大，将l位置的数据放到h位置
				v[h] = v[l];
			}
			//此时l位置已被空出，放入key的值
			v[l] = key;
			//递归排序左边
			quickSort(v, left, l-1);
			//递归排序右边
			quickSort(v, l+1, right);
		}
	}
	
	
	
	public static void merge(int[] array, int start, int mid, int end) {
		int[] tmp = new int[end - start+1];
		int s = start;
		int m = mid;
		int k = 0;
		//循环比较合并
		while (s < mid && m <= end) {
			if (array[s] < array[m]) {
				tmp[k] = array[s];
				s++;
			} else {
				tmp[k] = array[m];
				m++;
			}
			k++;
		}
		//将剩余数据复制到临时数组中
		while (s < mid) {
			tmp[k] = array[s];
			k++;
			s++;
		}
		//将剩余数据复制到临时数组中
		while (m <= end) {
			tmp[k] = array[m];
			k++;
			m++;
		}
		System.arraycopy(tmp, 0, array, start, tmp.length);
	}
	
	public static void mergeSort (int[] array, int first, int last) {
		//System.out.println("first:"+first+",last:"+last);
		if (first < last) {
			int mid = (first+last)/2;
			mergeSort(array, first, mid);
			mergeSort(array, mid+1, last);
			merge(array, first, mid+1, last);
		}
	}
	
	public static void main(String[] args) {
		int[] array = generateArray();
		printArray(array);
//		quickSort(array, 0, array.length-1);
		mergeSort(array, 0, array.length-1);
		printArray(array);
		
	}
	
}
