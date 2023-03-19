//package Project3;

public class GenFlows{
	
	String fid;
	String ps;
    int es;
    
    public GenFlows(String fid, String ps) {
    	this.fid = fid;
    	this.ps = ps;
    }
    
    public GenFlows(String fid, String ps, int es) {
    	this.fid = fid;
    	this.ps = ps;
    	this.es = es;
    }

	public String generateFid() {
		return fid;
	}

	public String fetchPSize() {
		return ps;
	}

	public int fetchEstVal() {
		return es;
	}

    public void putEstVal(int es) {
		this.es = es;
	}
    
	@Override
	public String toString() {
		return fid + " - "+ es + " - "+ ps ;
	}
   
}