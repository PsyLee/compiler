
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class EnvParserot {

    Stack<String> stack;
    int tabela[][];
    LinkedList<Zeton> tokeni;
    HashMap<String, Integer> terminali;
    HashMap<String, Integer> neTerminali;
    HashMap<Integer, String> pravila;
    int brTerminali;
    int brNeterminali;
    String poceten;// mora da e neterminal
    String ime;
    double brojce;
    boolean moze;
    int brojce2;

    public EnvParserot(String prvNeterminal, LinkedList<Zeton> zetonte, int m, int n) {
        stack = new Stack<String>();
        tokeni = zetonte;
        tabela = new int[m][n];
        terminali = new HashMap<String, Integer>();
        neTerminali = new HashMap<String, Integer>();
        pravila = new HashMap<Integer, String>();
        poceten = prvNeterminal;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                tabela[i][j] = 0;
            }
        }
        // за да имаме птерстава кој ред бр ни се терминалите и нетерминалите
        brTerminali = 0;
        brNeterminali = 0;

        // System.out.println(tokeni);
    }

    public void terminal(String vlez) {
        terminali.put(vlez, brTerminali++);
    }

    public void neTerminal(String vlez) {
        neTerminali.put(vlez, brNeterminali++);
    }

    public void pravilata(String vlez) {
        pravila.put(pravila.size() + 1, vlez);
    }

    public void tabela(String neTerminal, String terminal, int index) {
        try {
            int red = neTerminali.get(neTerminal);
            int kolona = terminali.get(terminal);
            this.tabela[red][kolona] = index;
        } catch (Exception e) {
            File log = new File("greski.txt");
            try {
                if (log.exists() == false) {
                    log.createNewFile();
                }
                PrintWriter out = new PrintWriter(new FileWriter(log, true));
                out.append("Termilanot/Neterminalot ne postoi " + neTerminal);
                out.close();
            } catch (IOException er) {
                System.out.println("COULD NOT LOG!!");
            }
            System.err.println("Termilanot/Neterminalot ne postoi " + neTerminal);
        }
    }

    public int zemiBrojPravilo(String neterminal, String terminal) throws IOException {
        File log = new File("greski.txt");

        if (log.exists() == false) {
            log.createNewFile();
        }
        PrintWriter out = new PrintWriter(new FileWriter(log, true));

        int red = 0;
        int kolona = 0;
        //       System.out.println(terminal);
        if (neTerminali.get(neterminal) == null) {
            System.err.println(neterminal + " ne postoi vo neterminali");
            out.append(neterminal + " ne postoi vo neterminali");
            //   System.err.println(neterminal + " не е команда");
        } else {
            red = neTerminali.get(neterminal);
        }
        if (terminali.get(terminal) == null) {
            System.err.println(terminal + " ne postoi vo terminalite");
            out.append(terminal + " ne postoi vo terminalite");
        } else {
            //System.out.println("");
            kolona = terminali.get(terminal);
        }

        int pravilo = tabela[red][kolona];
        out.close();
        return pravilo;

    }

    public boolean eBroj(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean eNasoka(String s) {
        if (s.equals("И") || s.equals("З") || s.equals("С") || s.equals("Ј")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ePravec(String s) {
        if (s.equals("ИЗ") || s.equals("СЈ")) {
            return true;
        } else {
            return false;
        }
    }

    public void dodadiPravilo(int n) {
        // go zimam praviloto so vrednost n
        String praviloto = pravila.get(n);

        List<String> items = Arrays.asList(praviloto.split(" "));
        Collections.reverse(items);
        //      System.out.println("-------------poc------------");
        for (String s : items) {
            //         System.out.println("Dodadeno " + s);
            stack.push(s);
        }
        //       System.out.println("-------------kraj------------");
    }

    public String PromeniToken(String token) {

        //  System.out.println("Vlezen token " + token);
        if (!terminali.containsKey(token)) {
            if (!eBroj(token)) {
                if (eNasoka(token)) {
                    token = "насока";
                    //    System.err.println("nesmee da ima promenlivi И,З,С,Ј");
                } else if (ePravec(token)) {
                    token = "правец";
                }
            } else {
                token = "број";
            }
        }
        //       System.out.println("Vlezen token " + token);
        return token;
    }

    public boolean parsiraj() throws IOException {
        
        File log = new File("greski.txt");

                        if (log.exists() == false) {
                            log.createNewFile();
                        }
                        PrintWriter out = new PrintWriter(new FileWriter(log, true));
                        

        
        //stack.push(poceten);
        //stack.push(tokeni.getFirst().getIme());
//        System.out.println("Бапнат " + poceten);
        stack.push(poceten);

        Zeton momentalen = tokeni.poll();
        String token = momentalen.getIme();
        //       System.out.println("Izvaden element " + token);
        //    Zeton momentalen = null;
        Zeton prethoden = null;
        Zeton predPrethoden = null;
        String top = "";
        boolean kraj = true;

        while (true) {

            if (stack.isEmpty() && momentalen != null) {
                System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                out.append("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                kraj = false;
                break;
            } else {
                top = stack.pop();
                //              System.out.println(top);
            }
            //овде беше топ==""
            if ("".equals(top)) {
                continue;
            }

 //           System.out.println("top od stekot " + top);
            // ako neterminalite go sodrzat top
            if (neTerminali.containsKey(top)) {
                //  System.out.println(neTerminali.containsKey(top));

                //   System.out.println(PromeniToken(token));
                token = PromeniToken(token);

                //    System.out.println("Tokenot " + token);
    //            System.out.println(top + " , " + token);
                int pravilo = zemiBrojPravilo(top, token);
                if (pravilo == 0) {
                    if (prethoden == null) {
                        System.out.println("Грешка во ред 1");
                        out.append("Грешка во ред 1");
                        kraj = false;
                        break;
                    } else {
                        System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                        out.append("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                        kraj = false;
                        break;
                    }
                }
                dodadiPravilo(pravilo);

            } else if (terminali.containsKey(top)) {

                token = PromeniToken(token);

                if (!top.equals(token)) {
                    System.out.println("Лексичка грешка во ред " + momentalen.start + " колона " + momentalen.end);
                    out.append("Лексичка грешка во ред " + momentalen.start + " колона " + momentalen.end);
                    //               System.out.println("Внесено \"" + token + "\" , очекувам \"" + top + "\"");
                    kraj = false;
                    break;
                } else {
                    //                  System.out.println("Поништени!");

                    if (tokeni.peek() == null) {
                        kraj = false;
                        break;
                    } else {
                        //    System.out.println(momentalen);
                        //  predPrethoden = prethoden;
                        prethoden = momentalen;
                        momentalen = tokeni.poll();
                        token = momentalen.getIme();
                    }

                    //                 System.out.println("Izvadeno od lista " + token);
                }

            } else {
                System.err.println(top + " го нема во терминали/нетерминали");
                out.append(top + " го нема во терминали/нетерминали");
            }

        }
        //       System.out.println("Ostanati elementi vo stekot " + stack.toString());
                                out.close();
        return kraj;
    }
}
