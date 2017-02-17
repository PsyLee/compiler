public class LinijaDoKadeE {
    
    int broj_linija;
    int forbr = 1;

    public LinijaDoKadeE(int broj_linija, int forb) {
        this.broj_linija = broj_linija;
        this.forbr=forb;
    }

    public int getForbr() {
        return forbr;
    }

    public void setForbr(int forbr) {
        this.forbr = forbr;
    }

    public int getBroj_linija() {
        return broj_linija;
    }

    public void setBroj_linija(int broj_linija) {
        this.broj_linija = broj_linija;
    }

}
