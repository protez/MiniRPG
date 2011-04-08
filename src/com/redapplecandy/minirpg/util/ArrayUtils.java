package com.redapplecandy.minirpg.util;

public class ArrayUtils {

	static public int[] flattenIntMatrix(int[][] arr) {
		int[] newArray = new int[arr.length * arr[0].length];
		
		int idx = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				newArray[idx] = arr[i][j];
				idx++;
			}
		}
		
		return newArray;
	}
	
}
