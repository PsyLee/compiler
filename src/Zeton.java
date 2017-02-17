public class Zeton {
	String identifikator, ime;
	int start, end;

	public Zeton( String ime, String identifikator, int start, int end) {
		this.identifikator = identifikator;
		this.ime = ime;
		this.start = start;
		this.end = end;
	}

	public String getIdentifikator() {
		return identifikator;
	}

	public void setIdentifikator(String identifikator) {
		this.identifikator = identifikator;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return identifikator + ", " + ime + " [" + start + " " + end + "]";
	}

}