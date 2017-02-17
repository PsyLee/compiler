
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvLexer {
    // Osnovni biten raspored

    static LinkedList<Zeton> lista = new LinkedList<Zeton>();
    static String Brojka = "[0-9]+";
    static String Otvorena = "\\(";
    static String Zatvorena = "\\)";
    static String Minus = "\\-";
    static String Zapirka = ",";
    static String Broj = "број";
    static String Okolina = "околина";
    static String Pocetok = "почеток";
    static String Kraj = "крај";
    static String Istok = "И";
    static String Zapad = "З";
    static String Sever = "С";
    static String Jug = "Ј";
    static String IZ = "ИЗ";
    static String SJ = "СЈ";
    static String Robot = "робот";
    static String Zetoni = "жетони";
    static String Dzidovi = "ѕидови";
    static String Greska = "[\\p{Punct}&&[^(_)!><]]";
    static String Greska2 = "[a-zA-Z]";

    static String Bukva = "[абвгдѓежзѕијклњмнљопрстќуфхцчџшАБВГДЃЕЖЗЅИЈКЛЊМНЉОПРСТЌУФХЦЧШ]";
    static String Identifikator = "[_]*"
            + Bukva
            + "[_абвгдѓежзѕијклњмнљопрстќуфхцчџшАБВГДЃЕЖЗЅИЈКЛЊМНЉОПРСТЌУФХЦЧШ|0-9]*";

    public EnvLexer(String datoteka) {
        izvrsi(datoteka);
    }

    public LinkedList<Zeton> getLista() {
        return lista;
    }

    public boolean imaGreski() {

        boolean bool = true;
        for (Zeton z : lista) {
            if (z.identifikator.equals("Greska") || z.identifikator.equals("Greska2") || z.identifikator.equals("Identifikator")) {
                System.err.println("Лексичка грешка во ред " + z.start + " колона " + (z.end));
                File log = new File("greski.txt");
                try {
                    if (log.exists() == false) {
                        log.createNewFile();
                    }
                    PrintWriter out = new PrintWriter(new FileWriter(log, true));
                    out.append("Лексичка грешка во ред " + z.start + " колона " + (z.end));
                    out.close();
                } catch (IOException e) {
                    System.out.println("COULD NOT LOG!!");
                }
                bool = false;
                break;
            }
        }
        return bool;
    }

    public void izvrsi(String datoteka) {
        ArrayList<String> listata = citajDatoteka(datoteka);
        // System.out.println(listata.get(1));

        // System.out.println(text + "\n----------------");
        Pattern pattern = Pattern.compile(Brojka + "|" + Broj + "|" + Zapirka
                + "|" + Greska + "|" + Otvorena + "|" + Zatvorena + "|" + Greska2 + "|"
                + Minus + "|" + Okolina + "|" + IZ + "|" + SJ
                + "|" + Istok + "|" + Zapad + "|" + Sever
                + "|" + Jug + "|" + Zetoni + "|" + Dzidovi + "|" + Pocetok + "|" + Kraj + "|" + Robot + "|" + Identifikator);
        for (int i = 0; i < listata.size(); i++) {
            Matcher matcher = pattern.matcher(listata.get(i));
            while (matcher.find()) {

                if (i == 0) {
                    klasificiraj(matcher.group(), i + 1, matcher.start() + 1);
                } else {
                    klasificiraj(matcher.group(), i + 1, matcher.start() + 1);
                }
            }
        }
    }

    private static ArrayList<String> citajDatoteka(String dokument) {
        BufferedReader br = null;
        try {
            String text = "";
            ArrayList<String> listata = new ArrayList<String>();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    dokument), "UTF-8"));
            while (br.ready()) {
                // text += br.readLine() + "\n";
                listata.add(br.readLine());

            }
            return listata;

        } catch (Exception e) {
        }
        return new ArrayList<String>();
    }

    private static void klasificiraj(String s, int start, int end) {

        if (Pattern.matches(Brojka, s)) {
            lista.add(new Zeton(s, "Brojka", start, end));
        } else if (Pattern.matches(Robot, s)) {
            lista.add(new Zeton(s, "Robot", start, end));
        } else if (Pattern.matches(Otvorena, s)) {
            lista.add(new Zeton(s, "Otvorena", start, end));
        } else if (Pattern.matches(Zatvorena, s)) {
            lista.add(new Zeton(s, "Zatvorena", start, end));
        } else if (Pattern.matches(Minus, s)) {
            lista.add(new Zeton(s, "Minus", start, end));
        } else if (Pattern.matches(Zapirka, s)) {
            lista.add(new Zeton(s, "Zapirka", start, end));
        } else if (Pattern.matches(Okolina, s)) {
            lista.add(new Zeton(s, "Komanda_Okolina", start, end));
        } else if (Pattern.matches(Broj, s)) {
            lista.add(new Zeton(s, "Komanda_Broj", start, end));
        } else if (Pattern.matches(Pocetok, s)) {
            lista.add(new Zeton(s, "Komanda_Pocetok", start, end));
        } else if (Pattern.matches(Kraj, s)) {
            lista.add(new Zeton(s, "Komanda_Kraj", start, end));
        } else if (Pattern.matches(IZ, s)) {
            lista.add(new Zeton(s, "Istok_Zapad", start, end));
        } else if (Pattern.matches(SJ, s)) {
            lista.add(new Zeton(s, "Sever_Jug", start, end));
        } else if (Pattern.matches(Istok, s)) {
            lista.add(new Zeton(s, "Istok", start, end));
        } else if (Pattern.matches(Zapad, s)) {
            lista.add(new Zeton(s, "Zapad", start, end));
        } else if (Pattern.matches(Sever, s)) {
            lista.add(new Zeton(s, "Sever", start, end));
        } else if (Pattern.matches(Jug, s)) {
            lista.add(new Zeton(s, "Jug", start, end));
        } else if (Pattern.matches(Zetoni, s)) {
            lista.add(new Zeton(s, "Komanda_Zetoni", start, end));
        } else if (Pattern.matches(Dzidovi, s)) {
            lista.add(new Zeton(s, "Komanda_Dzidovi", start, end));
        } else if (Pattern.matches(Greska, s)) {
            lista.add(new Zeton(s, "Greska", start, end));
        } else if (Pattern.matches(Greska2, s)) {
            lista.add(new Zeton(s, "Greska", start, end));
        } else if (Pattern.matches(Identifikator, s)) {
            lista.add(new Zeton(s, "Identifikator", start, end));
        }
    }
}
