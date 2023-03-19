import java.util.*;
import java.util.Scanner;
import java.io.*;
class Project4{

    static Random rand = new Random();
    static byte sizeofInt = 8;
    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("Select the algorithm you want to implement from the following:\n1. Bitmap\n2. Bitmap with sampling\n3. Hyper Log Log\n");
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        switch(x){
            case 1: Bitmap();
                    break;
            case 2: BitmapWithSampling();
                    break;
            case 3: HyperLogLog();
                    break;
            default: System.out.println("incorrect number entered\n");
        }
        s.close();
    }

    public static void Bitmap(){
        System.out.println("BITMAP");
        int m = 10000;
        int[] B = new int[m];
        int A[] = {100, 1000, 10000, 100000, 1000000};
        int num = 0;
        int[] ele = new int[5];
        int hash = Math.abs(rand.nextInt());
        int pos = 0;
        double w = 0, v = 0, est = 0, u=0;
        for (int i=0; i< A.length; i++){
            num = A[i];
            ele = new int[num];
            w = 0;
            v = 0;
            u = 0;
            Arrays.fill(B, 0);
            Arrays.fill(ele, 0);
            for (int j=0; j<num; j++){
                ele[j] = Math.abs(rand.nextInt());
            }
            for (int k=0; k<num; k++){
                    pos = (hash ^ ele[k]) % m;
                    if (B[pos] != 1) {
                        B[pos] = 1;
                        w++;
                    }
            }
            u = m-w;
            v = u/m;
            est = Math.log(v);
            est = -m * est;
            System.out.println(u + " " + v + " " + w);
            System.out.println("True Size:" + num + " " + "Estimated size: " + est);
            try(FileWriter fw = new FileWriter("Bitmap.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw))
                    {
                    out.println("True Size:" + num + " " + "Estimated size: " + est);
                    } catch (IOException e) {
                    }
            
        }
    }

    public static void BitmapWithSampling(){
        System.out.println("BitmapWithSampling");
        int m = 10000;
        int[] B = new int[m];
        int A[] = {100, 1000, 10000, 100000, 1000000};
        int num = 0;
        int[] ele = new int[5];
        int hash = Math.abs(rand.nextInt());
        int pos = 0;
        double w = 0, v = 0, est = 0, u=0, p=0.1;
        int X = Integer.MAX_VALUE;
        for (int i=0; i< A.length; i++){
            num = A[i];
            ele = new int[num];
            w = 0;
            v = 0;
            u = 0;
            Arrays.fill(B, 0);
            Arrays.fill(ele, 0);
            for (int j=0; j<num; j++){
                ele[j] = Math.abs(rand.nextInt());
            }
            for (int k=0; k<num; k++){
                    pos = (hash ^ ele[k]) % m;
                    if (pos < X * p){
                        if (B[pos] != 1) {
                            B[pos] = 1;
                            w++;
                        }
                    }
            }
            u = m-w;
            v = u/m;
            est = Math.log(v);
            est = -m * est * (double)(1/p);
            System.out.println("True Size:" + num + " " + "Estimated size: " + est);
            try(FileWriter fw = new FileWriter("BitmapWithProbability.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw))
                    {
                    out.println("True Size:" + num + " " + "Estimated size: " + est);
                    } catch (IOException e) {
                    }
        }
    }

    public static String convertIntegerToBinary(int number) {
        String res = "";
        for(; number > 0; number = number/2){
            if(number % 2 != 0)
                res = '1' + res;
            else 
                res = '0' + res;
        }
        return res;
    }

    public static void HyperLogLog(){
        System.out.println("HyperLogLog");
        int m = 256;
        int bits = 5;
        int[] B = new int[m];
        int A[] = {1000, 10000, 100000, 1000000};
        int num = 0;
        int[] ele = new int[5];
        int hash = Math.abs(rand.nextInt());
        int pos = 0;
        double est = 0;
        for (int i=0; i< A.length; i++){
            num = A[i];
            ele = new int[num];
            Arrays.fill(B, 0);
            Arrays.fill(ele, 0);
            for (int j=0; j<num; j++){
                ele[j] = Math.abs(rand.nextInt());
            }
            for (int k=0; k<num; k++){
                    pos = (hash ^ ele[k]) % m;
                    int geoVal = (int)Math.pow(2, bits) - convertIntegerToBinary(ele[k]^hash).length();
                    B[pos] = Math.max(B[pos], geoVal);
            }
            double alpha = 0.7213/(1+(1.079/m));
            double hSum = 0;
            for (int k=0; k<m; k++){
                hSum = hSum + (1/Math.pow(2, B[k]));
            }
            est = alpha * m * m * (1/hSum);
            System.out.println("True Size:" + num + " " + "Estimated size: " + est);
            try(FileWriter fw = new FileWriter("HyperLogLog.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw))
                    {
                    out.println("True Size:" + num + " " + "Estimated size: " + est);
                    } catch (IOException e) {
                    } 
        }
        
    }
}
