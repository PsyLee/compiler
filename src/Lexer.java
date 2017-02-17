
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

public class Lexer {

    // Osnovni biten raspored
    static LinkedList<Zeton> lista = new LinkedList<Zeton>();

    static String Bukva = "[абвгдѓежзѕијклњмнљопрстќуфхцчџшАБВГДЃЕЖЗЅИЈКЛЊМНЉОПРСТЌУФХЦЧШ]";
    static String Brojka = "[0-9]+";

    static String Ednakvo = "==";
    static String Pogolemo_Ednakvo = ">=";
    static String Pomalo_Ednakvo = "<=";
    static String Razlicno = "<>";
    static String Pogolemo = ">";
    static String Pomalo = "<";
    static String Blok = "!";
    static String Otvorena = "\\(";
    static String Zatvorena = "\\)";
    static String Dodeluvanje = "=";
    static String Mnozhenje = "\\*";
    static String Plus = "\\+";
    static String Minus = "\\-";
    static String Zapirka = ",";
    static String Broj = "[Бб]рој";
    static String Okolina = "[Оо]колина";
    static String Odi = "[Оо]ди";
    static String Zemi = "[Зз]еми";
    static String Ostavi = "[Оо]стави";
    static String SvrtiLevo = "[Сс]вртиЛево";
    static String SvrtiDesno = "[Сс]вртиДесно";
    static String Procedura = "[Пп]роцедура";
    static String Promenliva = "[Пп]роменлива";
    static String Pocetok = "[Пп]очеток";
    static String Kraj = "[Кк]рај";
    static String Povtoruvaj = "[Пп]овторувај";
    static String Povtoruvaj_Do = "[Пп]овторувајДо";
    static String Pati = "[Пп]ати";
    static String Do = "[Дд]о";
    static String Istok = "И";
    static String Zapad = "З";
    static String Sever = "С";
    static String Jug = "Ј";
    static String IZ = "ИЗ";
    static String SJ = "СЈ";
    static String Uslov_Ako = "[Аа]ко";
    static String Zeton = "[Жж]етон";
    static String Zetoni = "[Жж]етони";
    static String Dzid = "[Ѕѕ]ид";
    static String Dzidovi = "[Ѕѕ]идови";
    static String Nasoka = "[Нн]асока";
    static String Teleport = "[Тт]елепорт";
    static String Greska = "[\\p{Punct}&&[^(_)!><]]";
    static String Greska2 = "[a-zA-Z]";
    // ponapredni na kraj na regex
    static String Identifikator = "[_]*"
            + Bukva
            + "[_абвгдѓежзѕијклњмнљопрстќуфхцчџшАБВГДЃЕЖЗЅИЈКЛЊМНЉОПРСТЌУФХЦЧШ|0-9]*";
    static String VoString = "\".*\"";

    public Lexer(String datoteka) {
        izvrsi(datoteka);
    }

    public LinkedList<Zeton> getLista() {
        //  lista.add(new Zeton( "$","0",  0, 0));
        return lista;
    }

    public boolean imaGreski() {
        Zeton prethoden = lista.getFirst();
        boolean bool = true;
        for (Zeton z : lista) {

            if (z.identifikator.equals("Greska") || z.identifikator.equals("Greska2")) {
                if (z.ime.equals(":") && (prethoden.ime.equals("пати")
                        || prethoden.ime.equals("ѕид") || prethoden.ime.equals("жетон")
                        || prethoden.ime.equals("И") || prethoden.ime.equals("З")
                        || prethoden.ime.equals("С") || prethoden.ime.equals("J")
                        || prethoden.ime.equals(")"))) {

                    continue;
                } else {
                    File log = new File("greski.txt");
                    try {
                        if (log.exists() == false) {
                            log.createNewFile();
                        }
                        PrintWriter out = new PrintWriter(new FileWriter(log, true));
                        out.append("Грешка во ред " + z.start + " колона " + (z.end + 1));
                        out.close();
                    } catch (IOException e) {
                        System.out.println("COULD NOT LOG!!");
                    }
                    System.err.println("Грешка во ред " + z.start + " колона " + (z.end + 1));
                    bool = false;
                }
            }
            prethoden = z;
        }
        return bool;
    }

    public void izvrsi(String datoteka) {
        ArrayList<String> listata = citajDatoteka(datoteka);

        // System.out.println(text + "\n----------------");
        Pattern pattern = Pattern.compile(Brojka + "|" + Ednakvo + "|"
                + Dodeluvanje + "|" + Broj + "|" + Blok + "|" + Mnozhenje + "|"
                + Procedura + "|" + Plus + "|" + Pocetok + "|" + Kraj + "|"
                + Povtoruvaj + "|" + Do + "|" + Pati + "|" + Zemi + "|" + Uslov_Ako + "|"
                + Zeton + "|" + Odi + "|" + Promenliva + "|" + Teleport + "|" + Zapirka + "|"
                + Identifikator + "|" + VoString + "|" + Greska + "|"
                + Otvorena + "|" + Zatvorena + "|" + Greska2 + "|"
                + Pogolemo_Ednakvo + "|" + Pomalo_Ednakvo + "|" + Razlicno
                + "|" + Pogolemo + "|" + Pomalo + "|" + Ostavi + "|"
                + SvrtiLevo + "|" + SvrtiDesno + "|" + Minus + "|" + Okolina
                + "|" + Povtoruvaj_Do + "|" + Istok + "|" + Zapad + "|" + Sever
                + "|" + Jug + "|" + IZ + "|" + SJ + "|" + Zetoni + "|" + Dzid
                + "|" + Dzidovi + "|" + Nasoka);
        for (int i = 0; i < listata.size(); i++) {
            Matcher matcher = pattern.matcher(listata.get(i));
            while (matcher.find()) {

                if (i == 0) {
                    klasificiraj(matcher.group(), i + 1, matcher.start() - 1);
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
        } else if (Pattern.matches(Ednakvo, s)) {
            lista.add(new Zeton(s, "Ednakvo", start, end));
        } else if (Pattern.matches(Pogolemo_Ednakvo, s)) {
            lista.add(new Zeton(s, "Pogolemo_Ednakvo", start, end));
        } else if (Pattern.matches(Pomalo_Ednakvo, s)) {
            lista.add(new Zeton(s, "Pomalo_Ednakvo", start, end));
        } else if (Pattern.matches(Razlicno, s)) {
            lista.add(new Zeton(s, "Razlicno", start, end));
        } else if (Pattern.matches(Pomalo, s)) {
            lista.add(new Zeton(s, "Pomalo", start, end));
        } else if (Pattern.matches(Pogolemo, s)) {
            lista.add(new Zeton(s, "Pogolemo", start, end));
        } else if (Pattern.matches(Blok, s)) {
            lista.add(new Zeton(s, "Blok", start, end));
        } else if (Pattern.matches(Otvorena, s)) {
            lista.add(new Zeton(s, "Otvorena", start, end));
        } else if (Pattern.matches(Zatvorena, s)) {
            lista.add(new Zeton(s, "Zatvorena", start, end));
        } else if (Pattern.matches(Dodeluvanje, s)) {
            lista.add(new Zeton(s, "Dodeluvanje", start, end));
        } else if (Pattern.matches(Mnozhenje, s)) {
            lista.add(new Zeton(s, "Mnozhenje", start, end));
        } else if (Pattern.matches(Plus, s)) {
            lista.add(new Zeton(s, "Plus", start, end));
        } else if (Pattern.matches(Minus, s)) {
            lista.add(new Zeton(s, "Minus", start, end));
        } else if (Pattern.matches(Zapirka, s)) {
            lista.add(new Zeton(s, "Zapirka", start, end));
        } else if (Pattern.matches(Okolina, s)) {
            lista.add(new Zeton(s, "Komanda_Okolina", start, end));
        } else if (Pattern.matches(Broj, s)) {
            lista.add(new Zeton(s, "Komanda_Broj", start, end));
        } else if (Pattern.matches(Promenliva, s)) {
            lista.add(new Zeton(s, "Komanda_Promenliva", start, end));
        } else if (Pattern.matches(Odi, s)) {
            lista.add(new Zeton(s, "Komanda_Odi", start, end));
        } else if (Pattern.matches(Zemi, s)) {
            lista.add(new Zeton(s, "Komanda_Zemi", start, end));
        } else if (Pattern.matches(Ostavi, s)) {
            lista.add(new Zeton(s, "Komanda_Ostavi", start, end));
        } else if (Pattern.matches(SvrtiLevo, s)) {
            lista.add(new Zeton(s, "Komanda_SvrtiLevo", start, end));
        } else if (Pattern.matches(SvrtiDesno, s)) {
            lista.add(new Zeton(s, "Komanda_SvrtiDesno", start, end));
        } else if (Pattern.matches(Procedura, s)) {
            lista.add(new Zeton(s, "Komanda_Procedura", start, end));
        } else if (Pattern.matches(Povtoruvaj_Do, s)) {
            lista.add(new Zeton(s, "Komanda_Povtoruvaj_Do", start, end));
        } else if (Pattern.matches(Pocetok, s)) {
            lista.add(new Zeton(s, "Komanda_Pocetok", start, end));
        } else if (Pattern.matches(Kraj, s)) {
            lista.add(new Zeton(s, "Komanda_Kraj", start, end));
        } else if (Pattern.matches(Povtoruvaj, s)) {
            lista.add(new Zeton(s, "Komanda_Povtoruvaj", start, end));
        } else if (Pattern.matches(Do, s)) {
            lista.add(new Zeton(s, "Komanda_Do", start, end));
        } else if (Pattern.matches(Pati, s)) {
            lista.add(new Zeton(s, "Komanda_Pati", start, end));
        } else if (Pattern.matches(Istok, s)) {
            lista.add(new Zeton(s, "Istok", start, end));
        } else if (Pattern.matches(Zapad, s)) {
            lista.add(new Zeton(s, "Zapad", start, end));
        } else if (Pattern.matches(Sever, s)) {
            lista.add(new Zeton(s, "Sever", start, end));
        } else if (Pattern.matches(Jug, s)) {
            lista.add(new Zeton(s, "Jug", start, end));
        } else if (Pattern.matches(IZ, s)) {
            lista.add(new Zeton(s, "Istok_Zapad", start, end));
        } else if (Pattern.matches(SJ, s)) {
            lista.add(new Zeton(s, "Sever_Jug", start, end));
        } else if (Pattern.matches(Uslov_Ako, s)) {
            lista.add(new Zeton(s, "Komanda_Ako", start, end));
        } else if (Pattern.matches(Zeton, s)) {
            lista.add(new Zeton(s, "Komanda_Zeton", start, end));
        } else if (Pattern.matches(Zetoni, s)) {
            lista.add(new Zeton(s, "Komanda_Zetoni", start, end));
        } else if (Pattern.matches(Dzid, s)) {
            lista.add(new Zeton(s, "Komanda_Dzid", start, end));
        } else if (Pattern.matches(Dzidovi, s)) {
            lista.add(new Zeton(s, "Komanda_Dzidovi", start, end));
        } else if (Pattern.matches(Nasoka, s)) {
            lista.add(new Zeton(s, "Komanda_Nasoka", start, end));
        } else if (Pattern.matches(Teleport, s)) {
            lista.add(new Zeton(s, "Komanda_Teleport", start, end));
        } else if (Pattern.matches(VoString, s)) {
            lista.add(new Zeton(s, "Stringot", start, end));
        } else if (Pattern.matches(Greska, s)) {
            lista.add(new Zeton(s, "Greska", start, end));
        } else if (Pattern.matches(Greska2, s)) {
            lista.add(new Zeton(s, "Greska", start, end));
        } else if (Pattern.matches(Identifikator, s)) {
            lista.add(new Zeton(s, "Identifikator", start, end));
        }

    }
}
