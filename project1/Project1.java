import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Project1 {

    static Random rand = new Random();

    public static void main(String args[]){
        System.out.println("Select the hashing technique to implement:");
        System.out.println("1. MultiHashing\n2. Cuckoo Hashing\n3. D-Left Hashing");
        Scanner userInput = new Scanner(System.in);
        int n = userInput.nextInt();
        switch(n){
            case 1: {
                System.out.println("MultiHashing");
                MultiHashingTable(1000,1000,3);
                break;
            }
            case 2: {
                System.out.println("Cuckoo");
                CuckooHash(1000,1000,3,2);
                break;
            }
            case 3: {
                System.out.println("Dleft");
                DLeftHashingTable(1000, 1000, 4);
                break;
            }
            default : System.out.println("wrong input");
        }
        userInput.close();
    }

public static void MultiHashingTable(int te, int f, int k){

    //table entry array(storage array)
    int entries[] = new int[te];
    // counter for empty entries
    int emptyEntries = 0;

    //flow ids generated randomely
    int flowid[] = new int[f];
    for (int i = 0; i < f; i++ ){
        flowid[i] = Math.abs(rand.nextInt());
    }

    //generate k random hash functions
    int s[] = new int[k];
    for (int i = 0; i < k; i++ ){
        s[i] = Math.abs(rand.nextInt());
    }

    //generate k hash locations
    for (int i=0; i<f ; i++) {
        int h[] = new int[k];
        int counter = 0;
        for (int j=0; j<k; j++){
            h[j] = flowid[i] ^ s[j];
            int result = 31 + h[j];
            if (entries[result % te] == 0){
                entries[result % te] = flowid[i];
                break;
            }
            else counter++;
        }
        if (counter == k){
            emptyEntries++;
        }
    }

    System.out.println("MultiHashing: The total number of flows in the hashtable are: " + (te-emptyEntries));
    for (int i=0; i<te; i++){
        System.out.println("Flow id: " + entries[i] + " Position: " + i);   
    }
    }

public static void CuckooHash(int te, int f, int k, int c){

    //table entry array(storage array)
    int entries[] = new int[te];
    // counter for empty entries
    int entryCount = 0;

    //flow ids generated randomely
    int flowid[] = new int[f];
    for (int i = 0; i < f; i++ ){
        flowid[i] = Math.abs(rand.nextInt());
    }

    //generate k random hash functions
    int s[] = new int[k];
    for (int i = 0; i < k; i++ ){
        s[i] = Math.abs(rand.nextInt());
    }

    System.out.println("Starting cuckoo hash");
    //generate k hash locations
    for (int i=0; i<f; i++) {
        int h[] = new int[k];
        for (int j=0; j<k; j++){
            int cStep = 0;  
            h[j] = flowid[i] ^ s[j];
            //master hash function
            int result = 31 + flowid[i];
            if (entries[result % te] == 0){
                entries[result % te] = flowid[i];
                entryCount++;
                break;
            } 
            else  {
                while (cStep<c){
                    int t[] = new int[k];
                    for (int a=0; a<k; a++){
                        t[a] = entries[result % te] ^ s[a];
                        int tempResult = 31 + t[a];
                        if (entries[tempResult % te] == 0){
                            entries[tempResult % te] = entries[result % te];
                            entries[result % te] = flowid[i];
                            entryCount++;
                            cStep = 4;
                            break;
                        }
                        else if (!(entries[tempResult % te] == entries[result % te])) cStep++;   
                    }
                }
            }
            if (cStep >= c) break;
        }
    }

    System.out.println("Cuckoo hashing: The total number of flows in the hashtable are: " + (entryCount));
    for (int i=0; i<te; i++){
        System.out.println("Flow id: " + entries[i] + " Position: " + i);   
    }
    }

    public static void DLeftHashingTable(int te, int f, int k){

        //table entry array(storage array)
        int entries[] = new int[te];
        // counter for number of entries
        int entriesCount = 0;
    
        //flow ids generated randomely
        int flowid[] = new int[f];
        for (int i = 0; i < f; i++ ){
            flowid[i] = Math.abs(rand.nextInt());
        }
    
        //generate k random hash functions
        
        int s[] = new int[k];
        for (int i = 0; i < k; i++ ){
            s[i] = Math.abs(rand.nextInt());
        }
        
    
        //generate k hash locations
        int seg = te/k;
        for (int i=0; i<f; i++) {
            for (int j=0; j<k; j++){
                int h = (flowid[i] ^ s[j]) % 251;
                if ((h >= seg)) h = h%seg;
                h = h + (j*seg);
                if (entries[h] == 0){
                    entries[h] = flowid[i];
                    entriesCount++;
                    break;
                }
            }
        }
    
        System.out.println("dLeft hashing: The total number of flows in the hashtable are: " + (entriesCount));
        for (int i=0; i<te; i++){
            System.out.println("Flow id: " + entries[i] + " Position: " + i);   
        }
        }
}


