import java.awt.font.TextHitInfo;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.IntPredicate;

import javax.swing.table.TableStringConverter;

public class AlgorithmsPractice {
	
	static AlgorithmsPractice T = new AlgorithmsPractice();

	public AlgorithmsPractice() {
		
	}
	
	public static void main(String[] args) {
		int[] arr = {1,3,5,4,5,2,8,0,1,3,5,8,9,};
		int[] arr2 = {1,2,3,4,-11,0, 5,-4, -1};
		int sum = 8;
		
		System.out.println(T.findSumPairs(arr, sum));
		System.out.println(T.findSubArrays(arr2));
		System.out.println(T.printSubArraysNoSum(arr2));

	}
	
	/*
	 * First thought would be to brute force this and check all
	 * pairs that are possible.  This is obvious and most likely
	 * not the best method. 
	 * 
	 *  Kept thinking and thought maybe
	 * if we subtract every value in A from the sum and then
	 * check if that subtraction gives another value in A
	 * Seemed good so tried it
	 * 
	 * As it turns out array doesn't have a contains function
	 * brute force is still an option but not the way to go
	 * 
	 * This was my first practice run, so I worked through the
	 * example and got the idea to add the potential pairs to
	 * a separate list and then compare with that.
	 * 
	 * Would be better to add to a hash as contains is constant.
	 * 
	 * Learned that arrays don't offer a built in search function.
	 * A hash takes constant time for contains, compared to ArrayList's
	 * n time.  So when doing something similar would be smart to 
	 * use a hash
	 */
	
	public String findSumPairs(int[] A, int sum) {
		ArrayList<Integer> B = new ArrayList<>();
		for (int i = 0; i < A.length-1 ; i++) {
			int temp = sum - A[i];
			if (B.contains(A[i])) {
				return "pair found " + temp + "," + A[i];
			} else {
				B.add(temp);
			}
		}
		return "No pairs found";
	}
	
	
	/*
	 * 
	 * First thoughts
	 * 
	 * Could brute force and make every sub array. Not even sure
	 * how I would do that as that would be a massive number of
	 * arrays
	 * Then there must be a simple solution to this, what kind
	 * of patters are there.
	 * 
	 * From the start, just add up all the values, and when we
	 * get a sum of 0, output the numbers used.  Add the values
	 * to a different array to keep track of them.
	 * 
	 * Doesn't seem the fastest, still going through in a brute
	 * force way.
	 * 
	 * Creating a copy array wouldn't help as we would just have
	 * a multi dimension arrays trying to solve 0 sums
	 * 
	 * Searching revealed yet again that a hashset is going to
	 * be our hero.  We begin by adding all of the numbers up
	 * in order.  We had each new sumation to a set if it is
	 * not already within that set. If a summation is created
	 * that is already in the set, then we know that a subset
	 * added to zero, which resulted in a previously found
	 * summation
	 * 
	 * Learned
	 * 
	 * hashs weren't used very often in my courses, but so far
	 * they have been extremely useful in these algorithms.
	 * hashMap and Hashset differ slightly, hashsets only take
	 * an input, but a Map needs a key and value. Hashes don't
	 * seemed to keep and order and I can't find out how they
	 * are placed in the hash.  But constant search time is
	 * nice
	 * 
	 * Would be good to sit down and write some notes
	 * on these to have a better process.  In head
	 * thoughts might not work as well, but it's late
	 * not going to go too hard
	 * 
	 * 
	 */
	public String findSubArrays(int[] A) {
		HashSet<Integer> B = new HashSet<>();
		int sum = 0;
		B.add(0);  //Edge condition that a subset that adds to zero starts the array
		
		for (int i = 0 ; i < A.length ; i++) {
			sum += A[i];
			
			if (B.contains(sum) && A[i] != 0) {
				return "Sub-array found";
			} else {
				B.add(sum);
			}
		}
		return "Sub-array not found";
 	}
	
	
	/*
	 * First thoughts
	 * 
	 * We know how to detect sub arrays, so thats a big start
	 * Now how do we print what they are.  If we change the hashSet
	 * to a HashMap, then we can get the indexes of where the 
	 * sub array begins.  And from there we just add up the following
	 * numbers until we reach zero.  Have the key of the Map be the sum,
	 * and the value the index where it was found.  And then use that index
	 * to sub-array with the current index
	 * 
	 * Not 100% sure on the complexity.  Most likely O(n^2)
	 * 
	 * The solution provided uses more than I am comfortable
	 * with and would not have been found.  Still trying
	 * to figure out why it works, before making my own
	 * implementation
	 * 
	 * Looks like what they do is insert all of the hashmaps
	 * into a Map containing an integer, and a list
	 */
	public String printSubArraysNoSum(int[] A) {
		HashMap<Integer, Integer> B = new HashMap<>();
		String buffer = "";
		int sum = 0;
		boolean found = false;
		B.put(0,0);  //Edge condition that a subset that adds to zero starts the array
		
		
		
		for (int i = 0 ; i < A.length ; i++) {
			sum += A[i];
			
			if (B.containsKey(sum)) {
				found = true;
				buffer+="Subarry {";
				for (int j = B.get(sum)+1 ; j <= i ; j++) {
					buffer+= A[j] + " ";
				}
				buffer+="}\n";
			} else {
				B.put(sum, i);
			}
		}
		if (found)
		return buffer;
		
		return "No subarrays";
	}
}
