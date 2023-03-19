import java.io.*;
import java.util.*;
public class FirstPart {
    static Random rand = new Random();
    public static void main(String args[]) throws Exception {
        File file = new File("/Users/sheelaippili/Documents/Fall_2022/IDS/Project5/project5input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = br.readLine();
        int count = 0, n = 0, numRows = 0;
        String[] flowIdArr = new String[5];
        int[] elementNum = new int[5];

        //read input file, save flowid and number fo distinct elements for a flow in two different arrays
        while (st != null) {
            count++;
            if (count==1) {
                n = Integer.parseInt(st.trim());
                flowIdArr = new String[n];
                elementNum = new int[n];

            }
            else{
                int temp = 0;
                for (String a : st.split("\t")){
                    if (temp == 0 ) flowIdArr[numRows] = a.trim();
                    else elementNum[numRows] = Integer.parseInt(a);
                    temp = 1;
                    }
                numRows++;
            }
            st = br.readLine();
    }
        br.close();

    //initiliaze empty bitmap
    int m = 500000;
    int l = 500;
    int[] bitmap = new int[m];
    Arrays.fill(bitmap, 0);

    //random number hash generator for hashing
    int[] randNum = new int[l];
    for (int i=0; i<l; i++) randNum[i] = Math.abs(rand.nextInt());

    //record flows
    int element = 0;
    int flowIdHash = 0;
    for (int i=0; i<flowIdArr.length; i++){
        for (int j=0; j<elementNum[i]; j++){
            element = Math.abs(rand.nextInt());
            //flowIdHash = hash(hash(hash(flowIdArr[i]) ^ randNum[hash(element)%l]))%m;
            int rand = randNum[Math.abs(Objects.hash(element))%l];
            flowIdHash = Math.abs(Objects.hash(Objects.hash(flowIdArr[i])^rand))%m;
            bitmap[flowIdHash] = 1;
            }
        }

    //query the estimated flow spread
    
    int noOfBitmapZeroes = 0;
    for (int i=0; i<bitmap.length; i++){
        if (bitmap[i] == 0) noOfBitmapZeroes++;
    }

    //System.out.println("noofbitmapZeroes: " + noOfBitmapZeroes);
    double Vb = (double)noOfBitmapZeroes/(double)m;
    double bitmapFactor = (double)l * Math.log((double)Vb);

    //query virtual bitmap to physical bitmap
    double[] resultEstimateArr = new double[elementNum.length];
    String fID;
    int zeroCount = 0;
    Double estimate = 0.0;
    for (int i=0; i<flowIdArr.length; i++){
        zeroCount = 0;
        fID = flowIdArr[i];
        for (int j=0; j<l; j++){
            flowIdHash = Math.abs(Objects.hash(Objects.hash(fID)^randNum[j]))%m;
            if (bitmap[flowIdHash] == 0) zeroCount++;
        }
        double Vf = (double)zeroCount/(double)l;
        double virtualBitmapFactor = (double)l * Math.log((double)Vf);
        estimate = (double)bitmapFactor -(double)virtualBitmapFactor;
        if (estimate<0) estimate = 0.0;
        resultEstimateArr[i] = Math.round(estimate);
        }
    

    //display results
    // System.out.println("EleSize: " + elementNum[i] + " Estimate: " + resultEstimateArr[i]);
    try(FileWriter fw = new FileWriter("output.csv")){
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Actual, Estimate" + "\n");
        for (int i=0; i<resultEstimateArr.length; i++){
            //System.out.println(elementNum[i] + "," + resultEstimateArr[i] + "\n");
            bw.write(elementNum[i] + "," + resultEstimateArr[i] + "\n");
        }
        bw.flush();
    }catch(Exception e){
        e.printStackTrace();
    }
}
}
