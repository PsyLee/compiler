
import java.util.LinkedList;

public class Funkcija {
    
    String ime = "";
    int brojac =0;
    LinkedList<String> lista_vlezni = new LinkedList<String>();

    public Funkcija(String ime, int br, LinkedList<String> list) {
        this.ime = ime;
        this.brojac=br;
        this.lista_vlezni=list;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getBrojac() {
        return brojac;
    }

    public void setBrojac(int brojac) {
        this.brojac = brojac;
    }

    public LinkedList<String> getLista_vlezni() {
        return lista_vlezni;
    }

    public void setLista_vlezni(LinkedList<String> lista_vlezni) {
        this.lista_vlezni = lista_vlezni;
    }
    
    
    
}
