package com.amum.sma;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) throws ParseException {
		System.out.println(longestZigZag(new double[] {
				//1,2,3,4,5 //- 2 //UP
				//5,4,3,2,1 -  2 //down
				1,3,2,5,4,1,20,25,30
				
}));
		List<Double>  input = new ArrayList<>();
		input.add((double) 1);
		input.add((double) 2);
		input.add((double) 3);
		input.add((double) 4);
		

		//longestZigZag(input);
		
}

	public static  List<String> longestZigZag(List<Double>  input){
		List<String> zigzapList = new ArrayList<>();
		int[][] cache = new int[2][];
		//up
		cache[0] = new int[input.size()];
		Arrays.fill(cache[0], 1);
		//down
		cache[1] = new int[input.size()];
		Arrays.fill(cache[1], 1);
		int max = 1;
		for (int end = 1; end < input.size(); end++) {
			for (int start = 0; start < end; start++) {
				//update up array by checking down array
				if (input.get(end) > input.get(start) && cache[1][start] + 1 > cache[0][end]) {
					cache[0][end] = cache[1][start] + 1;
					System.out.println(">>>>UP<<<<");
				}
				//update down array by check up array
				if (input.get(end) < input.get(start) && cache[0][start] + 1 > cache[1][end]) {
					cache[1][end] = cache[0][start] + 1;
					System.out.println(">>>>DOWN<<<<");

				}
				max = Math.max(max, Math.max(cache[0][end], cache[1][end]));
				System.out.println("max>>>>"+max);
			}
		}
		return zigzapList;
	}
	public static  int longestZigZag(double[] in) {
		int[][] cache = new int[2][];
		//up
		cache[0] = new int[in.length];
		Arrays.fill(cache[0], 1);
		//down
		cache[1] = new int[in.length];
		Arrays.fill(cache[1], 1);
		int max = 1;
		for (int end = 1; end < in.length; end++) {
			for (int start = 0; start < end; start++) {
				//update up array by checking down array
				if (in[end] > in[start] && cache[1][start] + 1 > cache[0][end]) {
					cache[0][end] = cache[1][start] + 1;
					System.out.println(">>>>UP<<<<");
				}
				//update down array by check up array
				if (in[end] < in[start] && cache[0][start] + 1 > cache[1][end]) {
					cache[1][end] = cache[0][start] + 1;
					System.out.println(">>>>DOWN<<<<");

				}
				max = Math.max(max, Math.max(cache[0][end], cache[1][end]));
			}
		}
		return max;
	}
}
