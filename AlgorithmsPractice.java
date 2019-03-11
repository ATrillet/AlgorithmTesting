import java.awt.font.TextHitInfo;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.IntPredicate;

import javax.swing.table.TableStringConverter;

public class AlgorithmsPractice {
	
	static AlgorithmsPractice T = new AlgorithmsPractice();

	public AlgorithmsPractice() {
		
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
	/*
	 * Goal is to take in an unsorted binary array, sort it
	 * and then print it.  This should take linear time and
	 * constant space.
	 * 
	 * First thoughts
	 * 
	 * Make a new array and all the ones and then all the zeros
	 * Would most likely take two passes so would be O(n^2), and
	 * the creation of a new array would use more than
	 * constant space
	 * 
	 * If I create two temp variables then that would be
	 * constant space, so I could implement some kind of
	 * swap function to move the two values if they
	 * need to be swapped.  This should only take 
	 * one pass as there can be only 0s or 1s, and
	 * if we swap a 0 and 1.  Probably wouldn't work
	 * if we had two 1s and then a 0 at the end
	 * 
	 * Learned
	 * 
	 * Simple is better sometimes. Dont over think it
	 * 
	 * location of -- and ++ can make code
	 * simpler, but make sure what they
	 * are actually doing before running.  
	 */
	public void sortBinaryArray(int[] A) {
		int numZeros= 0;
		for (int i = 0 ; i < A.length ; i++) {
			if (A[i] == 0) {
				numZeros++;
			}
		}
		int j = 0;
		while(numZeros-- != 0) {
			A[j++] = 0;
		}
		
		while(j < A.length) {
			A[j++] = 1;
		}
	}
	
	/*
	 * Initial thoughts
	 * 
	 * Use a Hashset to store each value of the
	 * array as we go through.  Then use contains,
	 * constant time, to find if there are duplicates
	 * 
	 * This seems to work
	 * 
	 * Takes O(n) time and O(n) space.  There is a
	 * solution that takes the same time, but with
	 * constant space.  Space hasn't really been a
	 * large factor in many programs I've written before
	 * so not as experienced with ways to save on that
	 * other than just being mindful and not doing
	 * anything crazy.
	 * 
	 *  Should look into space saving methods
	 */
	public String findDuplicate(int[] A) {
		HashSet<Integer> B = new HashSet<>();
		for (int i = 0 ; i < A.length ; i++) {
			if (B.contains(A[i])) {
				return "The duplicate element is " + A[i];
			} else {
				B.add(A[i]);
			}
		}
		return "No duplicates found";
	}
	
	
	/*
	 * First thoughts
	 * 
	 * I'm pretty sure I've done soemthing similar to this
	 * before, but with a sub array that only contains
	 * increasing values.
	 * 
	 * This one is the largest sub array with non repeating
	 * values
	 * 
	 * Brute force, check all values with all possible
	 * subarrays. definitly not a good idea
	 * 
	 * Use a Map of some kind to keep track of all the
	 * arrays and if they contain a value already.  Would
	 * need to keep track of a sub array length value in
	 * order to know position in the sub array
	 * 
	 * Slowly build up a map from the start of the array, adding
	 * new values if they are not contained.  If they are remove,
	 * that value from the map, and increase the starting index.
	 * Do this to the end of the 
	 */
	public int findLCSA(int[] A) {
		HashSet<Integer> B = new HashSet<>();
		int sublen = 0;
		int index = 1;
		for (int i = 0; i < A.length ; i++) {
			if(B.contains(A[i])) {
				if (sublen < B.size()) {
					sublen = B.size();
				}
				B.clear();
				i=index++;
			}
			B.add(A[i]);
			System.out.println(B.size());
		}
		return sublen;
	}
	
	public static void main(String[] args) {
		int[] arr = {1,3,5,4,5,2,8,0,1,3,5,8,9,};
		int sum = 8;
		int[] arr2 = {1,2,3,4,-11,0, 5,-4, -1};
		int[] arr3 = {1,0,1,0,1,0,1,0,0,1,1,1};
		int[] arr4 = {2,0,2,1,4,3,1,0,4,2,3,6,7,8,6,5,4,5,6,7,8,9,0};
		
		System.out.println(T.findSumPairs(arr, sum));
		System.out.println(T.findSubArrays(arr2));
		System.out.println(T.printSubArraysNoSum(arr2));
		T.sortBinaryArray(arr3);
		System.out.println(Arrays.toString(arr3));
		System.out.println(T.findDuplicate(arr));
		System.out.println(T.findLCSA(arr4));

	}
}
