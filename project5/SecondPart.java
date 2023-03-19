import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SecondPart {

    static class Result{
        String s;
        Double estVal;
        public Result(String s, Double estVal){
            this.s = s;
            this.estVal  = estVal;
        }
    }
    public static void main(String[] args) throws Exception{
       
        File fName = new File("project5input.txt");
        Scanner ipScanner = new Scanner(fName);
        BSktHLL bsktHll = new BSktHLL(4000, 128, 3);
        OutputFunction(bsktHll, ipScanner);
        
    }

    private static void OutputFunction(BSktHLL bsktHll, Scanner scanner) throws IOException {
        FileWriter ofName = new FileWriter("output2.txt", false);
        Random rand = new Random();

        Map<String, Integer> hashMapObj = new HashMap<>();

        while (scanner.hasNextLine()) {
            int noOfFlows = Integer.valueOf(scanner.nextLine());
            for (int i = 0; i < noOfFlows; i++) {
                String[] stringInput = scanner.nextLine().split("\\s+");
                String fID = stringInput[0];
                int elementCount = Integer.valueOf(stringInput[1]);
                if (hashMapObj.containsKey(fID)) {
                    continue;
                }
                hashMapObj.put(fID, elementCount);
                for (int j = 0; j < elementCount; j++) {
                    int ele = rand.nextInt();
                    bsktHll.Record(fID, ele);
                }
            }
        }

      
        List<Result> res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : hashMapObj.entrySet()) {
            String s = entry.getKey();
            Double y = Double.valueOf(bsktHll.Query(entry.getKey())); 
            res.add(new Result(s, y));
            }
            Collections.sort(res, new Comparator<Result>() {
                public int compare(Result r1, Result r2) {
                    return r2.estVal.compareTo(r1.estVal);
                }
            });
        ofName.write("Flow id, Estimated spread\n");
        for(int i =0 ; i <25; i++)
            ofName.write(res.get(i).s + " " + res.get(i).estVal + "\n");    
        ofName.close();
    }
}

class BSktHLL {

    public class HLL {
        private double alpha;
        private BitSet[] bitmap;
        private int m;

        public HLL(int m) {
            this.m = m;
            this.alpha = 0.7213 / (1 + 1.079 / m);
            this.bitmap = new BitSet[m];
            for (int i = 0; i < m; i++) {
                this.bitmap[i] = new BitSet(5);
            }
            
        }

        public void Record(int el) {
            int hash = Math.abs(Objects.hash(el)) % m;
            byte lz = (byte) (Integer.numberOfLeadingZeros(Math.abs(Objects.hash(el))));
            byte present = bitmap[hash].toByteArray().length > 0 ? bitmap[hash].toByteArray()[0] : 0;
            if (lz > present) {
                bitmap[hash] = BitSet.valueOf(new byte[] { lz });
            }
        }

        public double Query() {
            double total = 0;
            for (int i = 0; i < m; i++) {
                byte number = bitmap[i].toByteArray().length > 0 ? bitmap[i].toByteArray()[0] : 0;
                total += 1.0 / (1 << number);
            }

            return alpha * m * m / total;
        }

    }

    private HLL[] Arr;
    private int[] randomValueArr;

    public BSktHLL(int m, int l, int k) {
        this.Arr = new HLL[m];
        this.randomValueArr = new int[k];
        for (int i = 0; i < m; i++) {
            Arr[i] = new HLL(l);
        }
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            this.randomValueArr[i] = rand.nextInt();
        }

    }

    public void Record(String fid, int ele) {
        for (int x : randomValueArr) {
            int hash = Math.abs(Objects.hash(fid) ^ x) % Arr.length;
            Arr[hash].Record(ele);
        }
    }

    public double Query(String fid) {
        double m = Double.MAX_VALUE;
        for (int x : randomValueArr) {
            int hash = Math.abs(Objects.hash(fid) ^ x) % Arr.length;
            m = Math.min(m, Arr[hash].Query());
        }

        return m;
    }

}

