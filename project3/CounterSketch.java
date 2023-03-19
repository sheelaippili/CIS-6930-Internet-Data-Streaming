import java.util.Arrays;
import java.util.Random;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CounterSketch {

	static int k = 3; 
	static int w = 3000; 
	static int n;
	static int randIntArr[] = new int[k];
	public static int [][] cs = new int[k][w];
	
	public static void main(String[] args) throws IOException {
		
		File inputFile = new File("project3input.txt");
		BufferedReader buffRead = new BufferedReader(new FileReader(inputFile));
		n = Integer.parseInt(buffRead.readLine());
		GenFlows[] flows = new GenFlows[n];
        for(int i=0;i<k;i++) { 
			randIntArr[i] = new Random().nextInt(Integer.MAX_VALUE-10)+1;
		}
		
		String str;
		int index=0;
		while((str = buffRead.readLine()) != null) {
		    String[] present = str.split("\\s+");
		    flows[index++] = new GenFlows(present[0],present[1]);
		}
		
		for(int i=0;i<flows.length;i++) {
			updatecs(flows[i].generateFid(), flows[i].fetchPSize());
		}
		
		for(int i=0; i<flows.length;i++) {
		 int estFSize = generateEstVals(flows[i].generateFid());
		   flows[i].putEstVal(estFSize);
		}
		
		double avgError = generateAvgErr(flows);
		Arrays.sort(flows, (p,q)->q.fetchEstVal() - p.fetchEstVal());
		

        StringBuilder output = new StringBuilder("");
        output.append("Average error among all flows: "+avgError);
		output.append("Flows - EstimatedError - ActualError:\n");
        System.out.println("Average error among all flows: "+avgError);
		System.out.println("Flows - EstimatedError - ActualError:");

        for(int i=0;i<100;i++) {
        output.append(flows[i]+"\n");
        System.out.println(flows[i]);
        }
        
        Path outputFile = Path.of("CounterSketchOutput.txt");
        Files.writeString(outputFile, output);
        buffRead.close();
		
	}

	static int generateEstVals(String fid) {
        int hashValue = (fid.hashCode());
		int estValuesArr[] = new int[k];
		
		for(int i=0;i<k;i++) {
			int a = hashValue^randIntArr[i];
			char op = (a>>31 & 1)==1?'+':'-';
			int index = (int)Math.abs(a%w);
		  estValuesArr[i] = (op=='+')?cs[i][index]:-cs[i][index];
		}
		
		Arrays.sort(estValuesArr);
		return (k%2==1)?estValuesArr[k/2]:(estValuesArr[k/2]+estValuesArr[k/2-1])/2;
	}
	
	static double generateAvgErr(GenFlows[] flows) {
		double te = 0.0;
		for(int i=0;i<flows.length;i++) {
			int es = flows[i].fetchEstVal();
			int as = Integer.parseInt(flows[i].fetchPSize());
			
			te += Math.abs(es-as);
		}
		te = te/(flows.length);
		return te;
	}
	
	static void updatecs(String fid, String ps) {
		int hashValue = fid.hashCode();
        int fs = Integer.parseInt(ps);
	
		   for(int i=0;i<fs;i++) {
			for(int j=0;j<k;j++) {	
				int x = hashValue^randIntArr[j];
				char op = (x>>31 & 1)==1?'+':'-';
				int idx = (int)Math.abs(x%w);
				if(op == '+')
				  cs[j][idx] += 1;
				else
				  cs[j][idx] -= 1;	
			}
		   }
		
	  }
}