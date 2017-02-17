
import java.util.LinkedList;

public class Matrica {

    static LinkedList<ZetonMapa> zetoni = new LinkedList<ZetonMapa>();
    static LinkedList<Dzid> dzidovi = new LinkedList<Dzid>();
    static int mapa[][] = new int[0][0];
    static Igrac igrac;
    static int X;
    static int Y;
    static LinkedList<Zeton> tokeni;

    Matrica(LinkedList<Zeton> lista) {
        tokeni = lista;
        polniListi(tokeni);
        konstuirajMapa();
        pecati();
    }
 
    
    public void pecati(){
         for(int i=0;i<mapa.length;i++){
            for(int j=0;j<mapa[0].length;j++){
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public int [][] vrati_mapa()
    {
        return mapa;
    }
    
    public int vrati_x()
    {
        return X;
    }
    
    public int vrati_y()
    {
        return Y;
    }
    
    public Igrac vrati_igrac()
    {
        return igrac;
    }

    static public boolean eBroj(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    static public void polniListi(LinkedList<Zeton> tokeni) {

        Zeton momentalen = null;
        Zeton prethoden = null;
        Zeton predPrethoden = null;
        Zeton eptenPrethoden = null;

        boolean okolina = false;
        boolean zidovi = false;
        boolean zetonite = false;
        boolean robot = false;
        int x = 0, y = 0;

        String pravec = null;
        int golemina = 0;

        String nasoka = null;

        while (tokeni.peek() != null) {

            prethoden = momentalen;
            momentalen = tokeni.poll();
            if (prethoden != null) {

                if (prethoden.ime.equals("околина")) {
                    okolina = true;
                }

                if (okolina == true && !momentalen.ime.equals("ѕидови")) {
                    if (momentalen.ime.equals("(")) {
                        x = Integer.parseInt(tokeni.peek().ime);
                    } else if (tokeni.peek().ime.equals(")")) {
                        y = Integer.parseInt(momentalen.ime);
                        mapa = new int[x][y];
                        X=x;
                        Y=y;
                        System.out.println(x + " " + y);
                    }

                } else {

                    okolina = false;
                }

                if (prethoden.ime.equals("ѕидови") && momentalen.ime.equals("почеток")) {
                    zidovi = true;
                }

                if (zidovi == true && !momentalen.ime.equals("крај")) {

                    if (prethoden.ime.equals("почеток") || momentalen.ime.equals("ИЗ") || momentalen.ime.equals("СЈ")) {
                        pravec = momentalen.ime;
                    } else if (prethoden.ime.equals("ИЗ") || prethoden.ime.equals("СЈ")) {
                        x = Integer.parseInt(momentalen.ime);
                    } else if (tokeni.peek().ime.equals("-")) {
                        y = Integer.parseInt(momentalen.ime);
                    } else if (prethoden.ime.equals("-")) {
                        golemina = Integer.parseInt(momentalen.ime);
                        dzidovi.add(new Dzid(x, y, pravec, golemina));
                        System.out.println(x + " " + y + " " + pravec + " " + golemina);
                    }

                } else {
                    zidovi = false;
                }

                if (prethoden.ime.equals("жетони") && momentalen.ime.equals("почеток")) {
                    zetonite = true;
                }
                if (zetonite == true && !momentalen.ime.equals("крај")) {

                    if (prethoden.ime.equals("почеток") || momentalen.ime.equals("(")) {
                        x = Integer.parseInt(tokeni.peek().ime);
                    } else if (prethoden.ime.equals(",")) {
                        y = Integer.parseInt(momentalen.ime);
                        zetoni.add(new ZetonMapa(x, y));
                        System.out.println(x + " " + y);
                    }

                } else {
                    zetonite = false;
                }

                if (prethoden.ime.equals("робот")) {
                    robot = true;
                }
                if ((robot == true) && (momentalen.ime.equals("И") || momentalen.ime.equals("З") || momentalen.ime.equals("С") || momentalen.ime.equals("Ј"))) {
                    nasoka = momentalen.ime;
                } else if (robot == true && momentalen.ime.equals("(")) {
                    x = Integer.parseInt(tokeni.peek().ime);
                } else if (robot == true && momentalen.ime.equals(")")) {
                    y = Integer.parseInt(prethoden.ime);
                    igrac = new Igrac(x, y, nasoka);
                    System.out.println(x + " " + y + " " + nasoka);
                }

            }
        }

    }

    public static void konstuirajMapa() {
        //1 e dzid
        //2 e zeton
        //3 e igrac

        try {
            for (int i = 0; i < dzidovi.size(); i++) {
                String nasoka = dzidovi.get(i).nasoka;
                int golemina = dzidovi.get(i).golemina;
                int poc_x = dzidovi.get(i).x;
                int poc_y = dzidovi.get(i).y;

                if (nasoka.equals("СЈ")) {
                    mapa[poc_x][poc_y] = 1;
                    for (int j = 0; j < golemina; j++) {
                        mapa[poc_x][poc_y + j] = 1;
                    }
                } else if (nasoka.equals("ИЗ")) {
                    mapa[poc_y][poc_x] = 1;

                    for (int j = 0; j < golemina; j++) {
                        mapa[poc_x + j][poc_y] = 1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Ѕидовите излегуваат надвор од мапата!!!");
        }

        try {
            for (int i = 0; i < zetoni.size(); i++) {
                ZetonMapa momentalen = zetoni.get(i);
                if (mapa[momentalen.x][momentalen.y] == 0) {
                    mapa[momentalen.x][momentalen.y] = 2;
                }
            }
        } catch (Exception e) {
            System.out.println("Една координата на жетонот е надвор од мапата!!!");
        }

        try {

                mapa[igrac.x][igrac.y] = 3;

        } catch (Exception e) {
            System.out.println("Една координата на играчот е надвор од мапата!!!");
        }

    }

}
