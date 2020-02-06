package DataProcessing;

public class Signal {
	String Signal;
	String Subor;
	Integer Verzia;
	
	public Signal(String signal, String subor, Integer verzia) {
		Signal = signal;
		Subor = subor;
		Verzia = verzia;
	}

	public String getSignal() {
		return Signal;
	}

	public void setSignal(String signal) {
		Signal = signal;
	}

	public String getSubor() {
		return Subor;
	}

	public void setSubor(String subor) {
		Subor = subor;
	}

	public Integer getVerzia() {
		return Verzia;
	}

	public void setVerzia(Integer verzia) {
		Verzia = verzia;
	}
	
	
}
