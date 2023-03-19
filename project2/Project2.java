import java.util.*;
import java.util.Scanner;


public class Project2 {

    static Random rand = new Random();

    public static void main(String args[]){
        System.out.println("Select the bloom filter technique to implement:");
        System.out.println("1. Bloom filter\n2. Counting bloom filter\n3. Coded bloom filter");
        Scanner userInput = new Scanner(System.in);
        int n = userInput.nextInt();
        switch(n){
            case 1: {
                System.out.println("Bloom filter");
                BloomFilter(1000,10000,7);
                break;
            }
            case 2: {
                System.out.println("Counting Bloom Filter");
                CountingBloomFilter(1000,10000,500,500,7);
                break;
            }
            case 3: {
                System.out.println("Coded Bloom Filter");
                CodedBloomFilter(7, 1000, 3, 30000, 7);
                break;
            }
            default : System.out.println("wrong input");
        }
        userInput.close();
    }

    //Function to implement bloom filter
    public static void BloomFilter(int ele, int bits, int k){

        //table entry array(storage array)
        int A[] = new int[ele];
        int B[] = new int[ele];

        //elements in A generated randomely
        for (int i = 0; i < ele; i++ ){
            A[i] = Math.abs(rand.nextInt());
        }

        //elements in B generated randomely
        for (int i = 0; i < ele; i++ ){
            B[i] = Math.abs(rand.nextInt());
        }

        //generate k random hash functions
        int s[] = new int[k];
        for (int i = 0; i < k; i++ ){
            s[i] = Math.abs(rand.nextInt());
        }

        int bitmap[] = new int[bits];

        //Encode A to the bitmap array
        for (int i=0; i<ele ; i++) {
            int h[] = new int[k];
            for (int j=0; j<k; j++){
                h[j] = A[i] ^ s[j];
                int result = 31 + h[j];
                if (bitmap[result % bits] == 0){
                    bitmap[result % bits] = 1;
                }
            }
        }
    
        //Lookup A to the bitmap array
        int lookupCountA = 0;
        for (int i=0; i<ele ; i++) {
            int counter = 0;
            int h[] = new int[k];
            for (int j=0; j<k; j++){
                h[j] = A[i] ^ s[j];
                int result = 31 + h[j];
                if (bitmap[result % bits] == 1){
                    counter++;
                }
            }
            if (counter == k) lookupCountA++;
        }

        //Lookup B to the bitmap array
        int lookupCountB = 0;
        boolean isFound = true;
        for (int i=0; i<ele ; i++) {
            isFound = true;
            for (int j=0; j<k; j++){
                int h = B[i] ^ s[j];
                int result = 31 + h;
                if (bitmap[result % bits ] != 1){
                    isFound = false;
                    break;
                }
            }
            if (isFound) lookupCountB++;
        }
        System.out.println("Number of successful lookups of set A of elements is: " + lookupCountA);
        System.out.println("Number of successful lookups of set B of elements is: " + lookupCountB);
    }

    //Function to implement counting bloom filter
    public static void CountingBloomFilter(int ele, int bits, int add, int remove, int k){

        //table entry array(storage array)
        int A[] = new int[ele];
        //elements in A generated randomely
        for (int i = 0; i < ele; i++ ){
            A[i] = Math.abs(rand.nextInt());
        }

        //counter array B
        int B[] = new int[add];
        //elements in B generated randomely
        for (int i = 0; i < add; i++ ){
            B[i] = Math.abs(rand.nextInt());
        }

        //generate k random hash functions
        int s[] = new int[k];
        for (int i = 0; i < k; i++ ){
            s[i] = Math.abs(rand.nextInt());
        }

        int bitmap[] = new int[bits];

        //Encode A to the bitmap array
        for (int i=0; i<A.length ; i++) {
            for (int j=0; j<k; j++){
                int h = A[i] ^ s[j];
                int result = 31 + h;
                bitmap[result % bits] += 1;
            }
        }

        //Remove elements in A 
        for (int i=0; i<remove ; i++) {
            for (int j=0; j<k; j++){
                int h = A[i] ^ s[j];
                int result = 31 + h;
                bitmap[result % bits] -= 1;
            }
        }

        //Encode B to the bitmap array
        for (int i=0; i<B.length ; i++) {
            for (int j=0; j<k; j++){
                int h = B[i] ^ s[j];
                int result = 31 + h;
                bitmap[result % bits] += 1;
            }
        }

        //Lookup A to the bitmap array
        int minCount = 0;
        int lookupCount = 0;
        boolean isFound = true;
        
        for (int i=0; i<A.length ; i++) {
            isFound = true;
            minCount = Integer.MAX_VALUE;
            for (int j=0; j<k; j++){
                int h = A[i] ^ s[j];
                int result = (31 + h)%bits;
                if (bitmap[result] < 1){
                    isFound = false;
                    break;
                }
                minCount = Math.min(minCount, bitmap[result]);
            }
            if (isFound) lookupCount = lookupCount + minCount;
        }


        System.out.println("Number of successful lookups of set A of elements is: " + lookupCount);
        //System.out.println("Number of successful lookups of set B of elements is: " + lookupCountB);
    }

    //Function to implement bloom filter
    public static void CodedBloomFilter(int sets, int ele, int fil, int bits, int k){

        //fill sets arrays
        int[][] setsArr = new int[sets][ele];
        for (int i=0; i<sets; i++){
            for (int j=0; j<ele; j++){
                setsArr[i][j] = Math.abs(rand.nextInt());
            }
        }

        //generate k random hash functions
        int s[] = new int[k];
        for (int i = 0; i < k; i++ ){
            s[i] = Math.abs(rand.nextInt());
        }

        String[] codesArr = new String[sets];
        for (int i = 1; i <= sets; i++ ){
            codesArr[i-1] = BinaryConversion(i,sets);
        }

        int[][] filtersArr = new int[fil][bits];
        for (int i=0; i<sets; i++){
            for (int j=0; j<(int) Math.ceil(Math.log(sets+1)/Math.log(2)); j++){
                if (codesArr[i].charAt(j)=='1')
                {
                   pushElements(filtersArr[j], s, setsArr[i], sets, k, bits); 
                }
            }
        }

        //int lookupCount = 0;
        int correctLookupCount = 0;
        boolean isHelper = true;

        for (int p=0; p<sets; p++){
            for (int q=0; q<ele; q++){
                StringBuilder presentCode = new StringBuilder("");
                for (int r=0; r<fil; r++){
                    isHelper = helper(filtersArr[r], setsArr[p][q], s, k, bits);
                    if (isHelper==true){
                        presentCode.append("1");
                    }
                    else presentCode.append("0");
                }
                if (presentCode.toString().equals(codesArr[p])) correctLookupCount++;
            }
        }
        System.out.println("Number of correct lookups: " + correctLookupCount);
       
    }

    //This converts a integer number to a binary string
    static String BinaryConversion(int p, int sets){
        String str="";

        while(p>0){
            int a = p%2;
            p = p/2;
            str = String.valueOf(a)+str;
        }
        if(str.length()<Math.ceil(Math.log(sets+1)/Math.log(2))){
            int b = (int) (Math.ceil(Math.log(sets+ 1) / Math.log(2)) - str.length());
            while(b-->0) str = "0"+str;
        }
        return str;
    }

    static int masterHashFunc(int val, int hashVal){
        int pos = val ^ hashVal;
        pos = 31+pos;
        return pos;
    }

    //reusable function to perform encoding
    static void pushElements(int[] filtersArr, int[] s, int[] setsArr, int sets, int k, int bits){
        for (int i = 0; i < setsArr.length; i++) {
            for (int j = 0; j < k; j++) {
                int pos = masterHashFunc(setsArr[i], s[j]);
                if(filtersArr[pos%bits]!=1) filtersArr[pos%bits] = 1;
            }
        } 
    }

    static boolean helper(int[] filtersArr, int presentVal, int[] s, int k, int bits){
        for(int i=0;i<k;i++){
            int pos = masterHashFunc(presentVal,s[i]);
            if(filtersArr[pos%bits]!=1) return false;
        }
        return true;
    }
}




