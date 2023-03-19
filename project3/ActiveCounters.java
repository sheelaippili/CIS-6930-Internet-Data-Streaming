import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class ActiveCounters {

    static String toBinary(int num) {
        String ans = "";
        while(num>0) {
            if(num%2==0) {
                ans = '0' + ans;
            }else {
                ans = '1' + ans;
            }
            num = num/2;
        }
        return ans;
    }

   public static void main(String[] args) throws IOException {
	   int ns = 16;
	   int counternumber=0,counterexponent=0;
	   int cSize = 1000000;
	   for(int i=0;i<cSize;i++) { 
			   int p = new Random().nextInt((int)Math.pow(2, counterexponent));
               //System.out.println(p + "\n");
			   if(p == 0) 
				   counternumber++;
			   String binNum = toBinary(counternumber);
			   if(binNum.length()>ns) { 
				   counterexponent++;
				   counternumber = counternumber>>1;
			   }
	   }
	   StringBuilder activeCounterOutput = new StringBuilder("");
	   int output = counternumber*(int)Math.pow(2, counterexponent);
	   activeCounterOutput.append("Final value of the active counter in decimal" + output);
       System.out.println("Final value of the active counter in decimal" + output);
       Path activeCounterOutputFile = Path.of("ActiveCountersOutput.txt");
       Files.writeString(activeCounterOutputFile, activeCounterOutput); 
   } 
}