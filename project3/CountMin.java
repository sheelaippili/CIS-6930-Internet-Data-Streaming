import java.util.Arrays;
import java.util.Random;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CountMin {

    static int n = 10000;
	static int k = 3;  
	static int w = 3000; 

    public static int [][] cm = new int[k][w];
    static int randIntArr[] = new int[k];
	
	public static void main(String[] args) throws IOException {
		
		File inputFile = new File("project3input.txt");
		BufferedReader buffRead = new BufferedReader(new FileReader(inputFile));
		n = Integer.parseInt(buffRead.readLine());
        String str;
		int index=0;
		GenFlows[] flows = new GenFlows[n];
		for(int i=0;i<k;i++) {
			randIntArr[i] = new Random().nextInt(Integer.MAX_VALUE-10)+10;
		}
		while((str = buffRead.readLine()) != null) {
		    String[] present = str.split("\\s+");
		    flows[index++] = new GenFlows(present[0],present[1]);
		}
		
		for(int i=0;i<flows.length;i++) {
			UpdateCounter(flows[i].generateFid(), flows[i].fetchPSize());
		}
		
		for(int i=0; i<flows.length;i++) {
		 int estFSize = GenerateEstVals(flows[i].generateFid());
		   flows[i].putEstVal(estFSize);
		}
		
		double avgError = FindAvgErr(flows);
		Arrays.sort(flows, (p,q)->q.fetchEstVal() - p.fetchEstVal());
		

        StringBuilder output = new StringBuilder("");
        output.append("The average eroor among all flows is: " + avgError);
		output.append("Flows - EstimatedError - ActualError:\n");
        System.out.println("The average error among all flows: " + avgError);
		System.out.println("Flows - EstimatedError - ActualError:");

        for(int i=0;i<100;i++) {
        output.append(flows[i]+"\n");
        System.out.println(flows[i]);
        }
        
        Path outputFile = Path.of("CountMinOutput.txt");
        Files.writeString(outputFile, output);
        buffRead.close();
	}

    static int generatehash(String fid, int randNum) {
		int hashValue = Math.abs(fid.hashCode());
		return  (hashValue^randNum)%w;
	}

	static double FindAvgErr(GenFlows[] flows) {
		double te = 0.0;
		for(int i=0;i<flows.length;i++) {
			int es = flows[i].fetchEstVal();
			int as = Integer.parseInt(flows[i].fetchPSize());
			te += Math.abs(es-as);
		}
        te = te/(flows.length);
		return te;
	}

    static int GenerateEstVals(String fid) {
		int low = Integer.MAX_VALUE;
		for(int i=0;i<k;i++) {
			int idx = generatehash(fid, randIntArr[i]);
			low = Math.min(low, cm[i][idx]);
		}
		return low;
	}
	
	static void UpdateCounter(String fid, String ps) {
		int fs = Integer.parseInt(ps);
		   for(int i=0;i<fs;i++) {
			for(int j=0;j<k;j++) {
				int idx = generatehash(fid,randIntArr[j]);
				cm[j][idx] += 1;
			}
		}
	}
}