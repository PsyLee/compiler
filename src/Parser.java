
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Parser {

    Stack<String> stack;
    int tabela[][];
    LinkedList<Zeton> tokeni;
    HashMap<String, Integer> terminali;
    HashMap<String, Integer> neTerminali;
    HashMap<Integer, String> pravila;
    int brTerminali;
    int brNeterminali;
    String poceten;// mora da e neterminal
    HashMap<String, Promenlivite> promenlivi;
    boolean procedura;
    String ime;
    HashMap<String, ArrayList<Funkcii>> metodite;
    ArrayList<Funkcii> deklariranite;
    double brojce;
    boolean moze;
    int brojce2;

    public Parser(String prvNeterminal, LinkedList<Zeton> zetonte, int m, int n) {
        stack = new Stack<String>();
        tokeni = zetonte;
        tabela = new int[m][n];
        terminali = new HashMap<String, Integer>();
        neTerminali = new HashMap<String, Integer>();
        pravila = new HashMap<Integer, String>();
        poceten = prvNeterminal;

        promenlivi = new HashMap<String, Promenlivite>();
        procedura = true;
        ime = "";
        metodite = new HashMap<String, ArrayList<Funkcii>>();
        deklariranite = new ArrayList<Funkcii>();
        brojce = -0.5;
        moze = false;
        brojce2 = 0;


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
            //	System.out.println(red + " " + kolona);
        } catch (Exception e) {
            System.err.println("Termilanot/Neterminalot ne postoi " + neTerminal);
        }
    }

    public int zemiBrojPravilo(String neterminal, String terminal) {
        int red = 0;
        int kolona = 0;
        System.out.println(terminal);
        if (neTerminali.get(neterminal) == null) {
            System.err.println(neterminal + " ne postoi vo neterminali");
            //   System.err.println(neterminal + " не е команда");
        } else {
            red = neTerminali.get(neterminal);
        }
        if (terminali.get(terminal) == null) {
            System.err.println(terminal + " ne postoi vo terminalite");
        } else {
            //System.out.println("");
            kolona = terminali.get(terminal);
        }
        //    System.out.println(neterminal + " , " + terminal);

   //     System.out.println(red);
     //   System.out.println(kolona);

        if (red == 7 && kolona == 23) {
            System.out.println("Не е дозволено променливи да се декларираат во блок!");
        }



        int pravilo = tabela[red][kolona];
        //  System.out.println(pravilo);
        /*    if (pravilo == 0) {         
         System.err
         .println("praviloto ne postoi vo tabelata , Non-Terminal("
         + neterminal + ") ,Terminal(" + terminal + ") ");
         }*/
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

    public boolean eUslov(String s) {
        if (s.equals(">") || s.equals("<") || s.equals(">=") || s.equals("<=") || s.equals("==") || s.equals("<>")) {
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
    //    System.out.println("-------------poc------------");
        for (String s : items) {
   //         System.out.println("Dodadeno " + s);
            stack.push(s);
        }
   //     System.out.println("-------------kraj------------");
    }

    public boolean SemantikaPromenlivi(Zeton prethoden, Zeton momentalen, String token, Zeton predPrethoden) {

        if (prethoden != null) {
       /*     System.out.println("///////////////////////////////");
            System.out.println("Prethodniot elem " + prethoden.ime);
            System.out.println("Momentalen elem " + momentalen.ime);
            System.out.println("Token elem " + token);
            System.out.println("///////////////////////////////");
*/
            if (procedura) {
                if (prethoden.ime.equals("процедура") && (token.equals("име") || token.equals("("))) {
                    promenlivi.clear();

                    if (momentalen.ime.equals("")) {
                    } else {
                        ime = momentalen.ime;
                    }
                    if (!metodite.keySet().isEmpty()) {
                        if (metodite.keySet().contains(ime)) {
                            System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                            System.out.println("Методот \"" + ime + "\" веќе е деклариран");
                            return false;
                        }
                    }
                }

                if (prethoden.ime.equals("број") || prethoden.ime.equals("насока")) {
                    deklariranite.add(new Funkcii(momentalen.ime, prethoden.ime));
                    // System.out.println("Dodadenoto "+momentalen.ime);
                    promenlivi.put(momentalen.ime, new Promenlivite(prethoden.ime, procedura, "нема"));
                }

                if (token.equals(")")) {
                    procedura = false;
                }
            } else {
                if (prethoden.ime.equals("променлива") || prethoden.ime.equals(",")) {
                    if (token.equals("име") || token.equals("број") || token.equals("насока")) {
                        if (!eBroj(momentalen.ime)) {
                            if (!moze) {
                                promenlivi.put(momentalen.getIme(), new Promenlivite("непознат", false, "нема"));
                            } else {
                            }
                        }
                    }
                } else {
                    if (token.equals("име")) {
                        //ova e ok
                        if (!promenlivi.keySet().contains(momentalen.ime) && !metodite.keySet().contains(momentalen.ime)) {
                            System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                            System.out.println("Променливата \"" + momentalen.ime + "\" не е декларирана");
                            return false;
                        }

                    }
                }


                if (!moze) {

                    if (token.equals("=")) {
                        // System.out.println("Taa sega predhodna " + momentalen.ime);
                        Zeton sleden = tokeni.peek();

                        if (eNasoka(sleden.ime)) {
                            if (promenlivi.get(prethoden.ime).tip.equals("непознат")) {
                                promenlivi.get(prethoden.ime).tip = "насока";
                                promenlivi.get(prethoden.ime).inicijalizirana = true;
                                promenlivi.get(prethoden.ime).vrednost = sleden.ime;
                            } else if (!promenlivi.get(prethoden.ime).tip.equals("насока")) {
                                System.out.println("Грешка во ред " + sleden.start + " колона " + sleden.end);
                                System.out.println("Променливата \"" + prethoden.ime + "\" е од тип број");
                                return false;
                            } else {
                                promenlivi.get(prethoden.ime).vrednost = sleden.ime;
                            }

                        } else if (eBroj(sleden.ime)) {

                            if (promenlivi.get(prethoden.ime).tip.equals("непознат")) {
                                promenlivi.get(prethoden.ime).tip = "број";
                                promenlivi.get(prethoden.ime).inicijalizirana = true;
                                promenlivi.get(prethoden.ime).vrednost = sleden.ime;
                            } else if (!promenlivi.get(prethoden.ime).tip.equals("број")) {
                                System.out.println("Грешка во ред " + sleden.start + " колона " + sleden.end);
                                System.out.println("Променливата \"" + prethoden.ime + "\" е од тип насока");
                                return false;
                            } else {
                                promenlivi.get(prethoden.ime).vrednost = sleden.ime;
                            }
                        } else {
                            System.out.println("Грешка во ред " + sleden.start + " колона " + sleden.end);
                            System.out.println("Неправилна вредност за променливата \"" + prethoden.ime + "\"");
                            return false;
                        }
                    } else if (momentalen.ime.equals("(") && metodite.keySet().contains(prethoden.ime) && !predPrethoden.ime.equals("процедура")) {
                      /*  System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;");
                        System.out.println(prethoden.ime);
                        System.out.println(predPrethoden.ime);*/
                        moze = true;
                        ime = prethoden.ime;
                    }
                }

                if (moze) {
                    brojce2 = metodite.get(ime).size()-1;
                    if (token.equals(")")) {

                        System.out.println("Takvoto " + brojce2 + " " + (int) Math.floor(brojce));

                        if (Math.floor(brojce) < brojce2) {
                            System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                            System.out.println("Декларираниот методот \"" + ime + "\" прима поголем број на аргументи");
                            moze = false;
                            return false;
                        }

                        moze = false;
                        brojce = -0.5;
                    } else if (token.equals("(") || token.equals(",")) {
                        //mozno e da zeza
                        if (token.equals(",") && tokeni.peek().ime.equals(")")) {
                            System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                            System.out.println("Неможе после \"" + token + "\" да следува \")\"");
                            return false;
                        }

                    } else {
                      //  System.out.println("TOkenot " + token);

                        if (brojce % 1 == 0) {

                            int broj = (int) Math.ceil(brojce);
                //            System.out.println(" Brojce " + broj + " " + brojce2);

                            if (broj > brojce2) {
                                System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                                System.out.println("Декларираниот методот \"" + ime + "\" прима помал број на аргументи");
                                return false;
                            } else {

                                if (token.equals(metodite.get(ime).get(broj).tip)) {
                                } else {
                                    System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                                    System.out.println("Декларираниот метод \"" + ime + "\" побарува тип \"" + metodite.get(ime).get(broj).tip + "\" пронајдено \"" + token + "\"");
                                    return false;
                                }


                                //   System.out.println(metodite.get(ime).get(broj).ime);
                            }




                        } else {
                        }
                        brojce += 0.5;
                    }



                }


                
                
                

            }


            if (token.equals("крај")) {
                procedura = true;
            }
        }
        return true;

    }

    public String PromeniToken(String token) {

      //  System.out.println("Vlezen token " + token);

        if (!terminali.containsKey(token)) {
            if (!eBroj(token)) {
                if (eNasoka(token)) {
                    token = "насока";
                    //    System.err.println("nesmee da ima promenlivi И,З,С,Ј");
                } else if (token.equals(":")) {
                    token = ":";
                } else {
                    token = "име";
                }
            } else {
                token = "број";
            }
        }
        //  System.out.println("Vlezen token " + token);
        return token;
    }

    public void parsiraj() {
        //stack.push(poceten);
        //stack.push(tokeni.getFirst().getIme());
        stack.push(poceten);

        Zeton momentalen = tokeni.poll();
        String token = momentalen.getIme();
 //       System.out.println("Izvaden element " + token);
        //    Zeton momentalen = null;
        Zeton prethoden = null;
        Zeton predPrethoden = null;
        String top="";

        while (true) {
            
            if(stack.isEmpty() && momentalen!=null){
               System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
               System.out.println("Главната процедура мора да е најдоле во кодот!");
               break;
            }else{
            
             top = stack.pop();
            //  System.out.println(momentalen);
            }
            //овде беше топ==""
            if ("".equals(top)) {
                continue;
            }

 //          System.out.println("top od stekot " + top);

            // ako neterminalite go sodrzat top
            if (neTerminali.containsKey(top)) {
                //  System.out.println(neTerminali.containsKey(top));

             //   System.out.println(PromeniToken(token));
                token = PromeniToken(token);

            //    System.out.println("Tokenot " + token);

                //za повторувај и ако komandata 
                if (prethoden != null) {
                    if (prethoden.ime.equals("повторувај") || eUslov(prethoden.ime) || prethoden.ime.equals("(") || prethoden.ime.equals("до")) {

                        if (token.equals("име")) {
                            if (promenlivi.keySet().contains(momentalen.ime)) {
                                if (!promenlivi.get(momentalen.ime).inicijalizirana) {
                                    System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                                    System.out.println("Не е иницијализирана променливата " + momentalen.ime);
                                    break;
                                } else {
                //                    System.out.println("Ovde vleze " + momentalen.ime);
                                    token = promenlivi.get(momentalen.ime).tip;
                //                    System.out.println("Ovde izleze " + token);
                                }
                            } else {
                                System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                                System.out.println("Не е декларирана променливата " + momentalen.ime);
                                break;
                            }
                        }
                    }
                }



   //             System.out.println(top + " , " + token);

                int pravilo = zemiBrojPravilo(top, token);
                if (pravilo == 0) {
                    if(prethoden==null){
                        System.out.println("Грешка во ред 1");
                        break;
                    }else{
                    System.out.println("Грешка во ред " + prethoden.getStart());
                    break;
                    }
                }
                dodadiPravilo(pravilo);

                if (!SemantikaPromenlivi(prethoden, momentalen, token, predPrethoden)) {
                    break;
                }

            } else if (terminali.containsKey(top)) {

                token = PromeniToken(token);

                //za повторувај и ако komandata 
                if (prethoden != null) {
                    if (prethoden.ime.equals("повторувај") || eUslov(prethoden.ime) || prethoden.ime.equals("(") || prethoden.ime.equals("до")) {

                        if (token.equals("име")) {
                            if (promenlivi.keySet().contains(momentalen.ime)) {
                                if (!promenlivi.get(momentalen.ime).inicijalizirana) {
                                    System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                                    System.out.println("Не е иницијализирана променливата " + momentalen.ime);
                                    break;
                                } else {
                                    System.out.println("Ovde vleze " + momentalen.ime);
                                    token = promenlivi.get(momentalen.ime).tip;
                                    System.out.println("Ovde izleze " + token);
                                }
                            } else {
                                System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                                System.out.println("Не е декларирана променливата " + momentalen.ime);
                                break;
                            }
                        }
                    }
                }

                if (!SemantikaPromenlivi(prethoden, momentalen, token, predPrethoden)) {
                    break;
                }

                if (!top.equals(token)) {
                       System.out.println("Лексичка грешка ред "+ momentalen.start +" колона " + momentalen.end+"");
                       break;
                } else {
   //                 System.out.println("Поништени!");
                    if (token.equals("крај")) {
                        boolean da = false;
                       // System.out.println("+S+S+S+S++S+S" + promenlivi.keySet());
                        for (String s : promenlivi.keySet()) {
                            if (promenlivi.get(s).inicijalizirana == false) {
      //                          System.out.println("Променливата " + s + " не е искористена");
                                da = true;
                            }
                        }
                        if (da) {
                            break;
                        }

                    }


                    if (tokeni.peek() == null) {
                        break;
                    } else {
                        //    System.out.println(momentalen);
                        predPrethoden = prethoden;
                        prethoden = momentalen;
                        momentalen = tokeni.poll();
                        token = momentalen.getIme();

                        if (token.equals(")") && procedura) {
                         //   System.out.println("--------------------------------------------------------------");
                            procedura = false;
                            metodite.put(ime, new ArrayList<Funkcii>(deklariranite));
                           /* for (String s : metodite.keySet()) {
                                for (Funkcii f : metodite.get(s)) {
                                    System.out.println(f.ime);
                                }
                            }*/
                            deklariranite.clear();
                        }


                        if (promenlivi.keySet().contains(token) && (prethoden.ime.equals("променлива") || prethoden.ime.equals(","))) {
                            System.out.println("Грешка во ред " + momentalen.start + " колона " + momentalen.end);
                            System.out.println("Променливата \"" + token + "\" е веќе декларирана");
                            break;
                        }

     //                   System.out.println("Izvadeno od lista " + token);
                    }
                }

            } else {
                System.err.println(top + " го нема во терминали/нетерминали");
            }

        }
        System.out.println("Ostanati elementi vo stekot " + stack.toString());


        System.out.println(promenlivi.keySet());


        for (Promenlivite s : promenlivi.values()) {
            System.out.println(s.tip + " " + s.inicijalizirana + " " + s.vrednost);
        }


        
        for(String s: metodite.keySet()){
            System.out.println(s);
        }
        
        
        for (String s : metodite.keySet()) {
            for (Funkcii f : metodite.get(s)) {
                System.out.println(f.ime + " " + f.tip);
            }
        }


    }
}
