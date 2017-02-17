
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Stack;

public class NizaKomandi {

    public static LinkedList<Zeton> lista;

    public static LinkedList<Promenliva> promen = new LinkedList<Promenliva>();

    NizaKomandi(LinkedList<Zeton> lista) {

        this.lista = lista;

    }

    public static void fja() throws IOException {
        int br = 0;

        boolean vouslov = false;
        boolean vopovtoruvaj = true;
        boolean akozagrada = false;
        boolean povtoruvajzagrada = false;
        boolean tocno = false;
        boolean voakouslov = false;

        HashMap<Integer, Zeton> tokeni = new HashMap<Integer, Zeton>();

        LinkedList<Funkcija> funk = new LinkedList<Funkcija>();

        Stack<LinijaDoKadeE> stek = new Stack<LinijaDoKadeE>();

        String izraz = "";

        File yourFile = new File("niza.niz");
        if (!yourFile.exists()) {
            yourFile.createNewFile();
        }

        PrintWriter writer = new PrintWriter("niza.niz", "UTF-8");

        while (!lista.isEmpty()) {
            br++;
            tokeni.put(br, lista.pop());
        }

        int linija = br;
        int main = 1;

        promen = vrati_promen();

        for (int i = 1; i < tokeni.size(); i++) {
            if (tokeni.get(i).ime.equals("процедура") && tokeni.get(i + 1).ime.equals("(") && tokeni.get(i + 2).ime.equals(")")) {
                main = i;
                linija = i;
                stek.push(new LinijaDoKadeE(i, 1));
            }
        }

        while (linija <= tokeni.size()) {
            int momentalna = 1;
            if (tokeni.get(linija).identifikator.equals("Identifikator") && tokeni.get(linija + 1).ime.equals("(")) {

                momentalna = linija;
                LinkedList<String> vlezni = new LinkedList<String>();
                while (!tokeni.get(momentalna).ime.equals(")")) {
                    momentalna++;
                    if (tokeni.get(momentalna).identifikator.equals("Brojka") || tokeni.get(momentalna).identifikator.equals("Identifikator")) {
                        vlezni.add(tokeni.get(momentalna).ime);
                    }
                }

                funk.add(new Funkcija(tokeni.get(linija).ime, linija, vlezni));
                stek.push(new LinijaDoKadeE(linija, 1));

                for (int i = 1; i <= tokeni.size(); i++) {
                    if (tokeni.get(i).ime.equals("процедура") && tokeni.get(i + 1).identifikator.equals("Identifikator") && tokeni.get(i + 1).ime.equals(tokeni.get(linija).ime)) {
                        linija = i + 1;
                        //          System.out.println("-"+tokeni.get(linija).ime);
                    }
                }

                while (linija <= tokeni.size()) {
                    if (tokeni.get(linija).ime.equals("крај")) {
                        linija = stek.pop().broj_linija;
                        break;
                    }

                  //  System.out.println("->>" + tokeni.get(linija).ime);

                    if (tokeni.get(linija).ime.equals("оди")) {
                        writer.println("Odi");
                    } else if (tokeni.get(linija).ime.equals("земи")) {
                        writer.println("ZemiZeton");
                    } else if (tokeni.get(linija).ime.equals("свртиЛево")) {
                        writer.println("SvrtiLevo");
                    } else if (tokeni.get(linija).ime.equals("свртиДесно")) {
                        writer.println("SvrtiDesno");
                    } else if (tokeni.get(linija).ime.equals("остави")) {
                        writer.println("OstaviZeton");
                    } else if (tokeni.get(linija).ime.equals("телепорт")) {
                        writer.println("Teleport(" + tokeni.get(linija + 2).ime + "," + tokeni.get(linija + 4).ime + ")");
                    }
                    linija++;
                }

            }

            if (tokeni.get(linija).ime.equals("повторувај") && (tokeni.get(linija + 2).ime.equals("пати"))) {
                stek.push(new LinijaDoKadeE(linija + 3, Integer.parseInt(tokeni.get(linija + 1).ime)));
                while (stek.peek().forbr > 1) {
                    LinijaDoKadeE LK = stek.pop();
                    LK.setForbr(LK.getForbr() - 1);
                    stek.push(LK);
                    linija = LK.getBroj_linija();

                    vopovtoruvaj = true;
                    while (vopovtoruvaj == true) {
                        //vopovtoruvaj=false;
                        if ((vouslov == true) && tokeni.get(linija).ime.equals("!")) {
                            akozagrada = true;
                        }

                        if ((akozagrada == true) && tokeni.get(linija).ime.equals("!")) {
                            akozagrada = false;
                            vouslov = false;
                        }

                        if ((vopovtoruvaj == true) && (tokeni.get(linija).ime.equals("!"))) {
                            povtoruvajzagrada = true;
                        }

                        if ((povtoruvajzagrada == true) && (tokeni.get(linija).ime.equals("!")) && akozagrada == false) {
                            povtoruvajzagrada = false;
                            vopovtoruvaj = false;
                        }

                        //System.out.println("force-->" + tokeni.get(linija + 2).ime);

                        if (tokeni.get(linija + 2).ime.equals("оди")) {
                            writer.println("Odi");
                        } else if (tokeni.get(linija + 2).ime.equals("земи")) {
                            writer.println("ZemiZeton");
                        } else if (tokeni.get(linija + 2).ime.equals("свртиЛево")) {
                            writer.println("SvrtiLevo");
                        } else if (tokeni.get(linija + 2).ime.equals("свртиДесно")) {
                            writer.println("SvrtiDesno");
                        } else if (tokeni.get(linija + 2).ime.equals("остави")) {
                            writer.println("OstaviZeton");
                        } else if (tokeni.get(linija + 2).ime.equals("телепорт")) {
                            writer.println("Teleport(" + tokeni.get(linija + 4).ime + "," + tokeni.get(linija + 6).ime + ")");
                        }
                        linija++;
                    }

                    //System.out.println("foce-->" + tokeni.get(linija + 2).ime + LK.forbr);
                }
            }

            if ((tokeni.get(linija).ime.equals("ако")) && tokeni.get(linija + 2).ime.equals("!")) {
                if (tokeni.get(linija + 1).ime.equals("жетон")) {
                    writer.println("AkoZeton");
                } else if (tokeni.get(linija + 1).ime.equals("ѕид")) {
                    writer.println("AkoDzid");
                }
                akozagrada = true;
                writer.println("{");
            } else if ((akozagrada == true) && (tokeni.get(linija).ime.equals("!")) && (!tokeni.get(linija - 2).ime.equals("ако"))) {
                akozagrada = false;
                writer.println("}");
            }

            if ((tokeni.get(linija).ime.equals("повторувај")) && tokeni.get(linija + 1).ime.equals("до") && tokeni.get(linija + 3).ime.equals("!")) {
                if (tokeni.get(linija + 2).ime.equals("жетон")) {
                    writer.println("PovtoruvajDoZeton");
                } else if (tokeni.get(linija + 2).ime.equals("ѕид")) {
                    writer.println("PovtoruvajDoDzid");
                }
                povtoruvajzagrada = true;
                writer.println("{");
            } else if ((povtoruvajzagrada == true) && (tokeni.get(linija).ime.equals("!")) && (!tokeni.get(linija - 3).ime.equals("повторувај"))) {
                povtoruvajzagrada = false;
                writer.println("}");
            }

            if (tokeni.get(linija).ime.equals("ако") && tokeni.get(linija + 1).ime.equals("(") && tokeni.get(linija + 6).ime.equals("!")) {
                //ако (и<д) !оди!

                String a = "";
                String b = "";
                int ab = 0;
                int bb = 0;
                String znak = tokeni.get(linija + 3).ime;
                if (tokeni.get(linija + 2).identifikator.equals("Identifikator")) {
                    a = tokeni.get(linija + 2).ime;
                } else if (tokeni.get(linija + 2).identifikator.equals("Brojka")) {
                    ab = Integer.parseInt(tokeni.get(linija + 2).ime);
                }

                if (tokeni.get(linija + 4).identifikator.equals("Identifikator")) {
                    b = tokeni.get(linija + 4).ime;
                } else if (tokeni.get(linija + 4).identifikator.equals("Brojka")) {
                    bb = Integer.parseInt(tokeni.get(linija + 4).ime);
                }

                if (a == "" || b == "") {
                    for (int i = 0; i < promen.size(); i++) {
                        Promenliva temp = promen.pollFirst();
                        if (a.equals(temp.ime)) {
                            ab = Integer.parseInt(temp.vrednost);
                        } else if (b.equals(temp.ime)) {
                            bb = Integer.parseInt(temp.vrednost);
                        }
                        promen.add(temp);
                    }
                }

                if (znak.equals("<")) {
                    if (ab < bb) {
                        tocno = true;
                    }
                } else if (znak.equals(">")) {
                    if (ab > bb) {
                        tocno = true;
                    }
                } else if (znak.equals("<=")) {
                    if (ab <= bb) {
                        tocno = true;
                    }
                } else if (znak.equals(">=")) {
                    if (ab >= bb) {
                        tocno = true;
                    }
                } else if (znak.equals("==")) {
                    if (ab == bb) {
                        tocno = true;
                    }
                } else if (znak.equals("<>")) {
                    if (ab != bb) {
                        tocno = true;
                    }
                }

                int mom = linija + 7;

                while (!tokeni.get(mom).ime.equals("!")) {

                    if ((tocno == true)) {
                        if (tokeni.get(mom).ime.equals("оди")) {
                            writer.println("Odi");
                        } else if (tokeni.get(mom).ime.equals("земи")) {
                            writer.println("ZemiZeton");
                        } else if (tokeni.get(mom).ime.equals("свртиЛево")) {
                            writer.println("SvrtiLevo");
                        } else if (tokeni.get(mom).ime.equals("свртиДесно")) {
                            writer.println("SvrtiDesno");
                        } else if (tokeni.get(mom).ime.equals("остави")) {
                            writer.println("OstaviZeton");
                        } else if (tokeni.get(mom).ime.equals("телепорт")) {
                            writer.println("Teleport(" + tokeni.get(mom + 2).ime + "," + tokeni.get(mom + 4).ime + ")");
                        }
                    }
                    mom++;
                }
                linija = mom;
                tocno = false;
            }

            if (tokeni.get(linija).ime.equals("оди")) {
                writer.println("Odi");
            } else if (tokeni.get(linija).ime.equals("земи")) {
                writer.println("ZemiZeton");
            } else if (tokeni.get(linija).ime.equals("свртиЛево")) {
                writer.println("SvrtiLevo");
            } else if (tokeni.get(linija).ime.equals("свртиДесно")) {
                writer.println("SvrtiDesno");
            } else if (tokeni.get(linija).ime.equals("остави")) {
                writer.println("OstaviZeton");
            } else if (tokeni.get(linija).ime.equals("телепорт")) {
                writer.println("Teleport(" + tokeni.get(linija + 2).ime + "," + tokeni.get(linija + 4).ime + ")");
            }

            // System.out.println(tokeni.get(linija).ime);
            linija++;

        }

        writer.close();
    }

    public static LinkedList<Promenliva> vrati_promen() {
        LinkedList<Promenliva> pro = new LinkedList<Promenliva>();
        Lexer lek = new Lexer("proba.txt");
        LinkedList<Zeton> list = lek.getLista();
        HashMap<Integer, Zeton> toke = new HashMap<Integer, Zeton>();
        int br = 1;
        while (!list.isEmpty()) {
            toke.put(br, lista.pop());
            br++;
        }

        for (int i = 1; i < toke.size() + 1; i++) {
            //System.out.println(pro.size());
            if (toke.get(i).identifikator.equals("Identifikator") && toke.get(i + 1).ime.equals("=") && toke.get(i + 2).identifikator.equals("Brojka")) {
                pro.add(new Promenliva(toke.get(i).ime, toke.get(i + 2).ime));
                //   System.out.println(pro.getLast().ime+" "+pro.getLast().vrednost);
            }
        }

        return pro;
    }

    public static void main(String[] args) throws IOException {
        Lexer lex = new Lexer("proba.txt");
        NizaKomandi NK = new NizaKomandi(lex.getLista());
        NK.fja();
    }

}
