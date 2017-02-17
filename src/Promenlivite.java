public class Promenlivite {

    String tip;
    boolean inicijalizirana;
    String vrednost;
    

    public Promenlivite(String tip, boolean inicijalizirana, String vrednost) {
        this.tip = tip;
        this.inicijalizirana = inicijalizirana;
        this.vrednost=vrednost;
    }

    public String getTip() {
        return tip;
    }

    public boolean inicijalizirana() {
        return inicijalizirana;
    }  
    
    public String getVrednost(){
    return vrednost;
    }
}
