import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class CodeGenerator {

    public static LinkedList<Zeton> lista;
    
    CodeGenerator(LinkedList<Zeton> lista) {
        
        this.lista = lista;
        
    }

    public static void fja() throws IOException
    {
        Zeton prv;
        Zeton vtor;
        Zeton tret;
        Zeton cetvrt;
        Zeton petti;
        prv = lista.pop();
        vtor = lista.pop();
        tret = lista.pop();
        cetvrt = lista.pop();
        petti = lista.pop();
        
        boolean vouslov=false;
        boolean vopovtoruvaj=false;
        boolean akozagrada=false;
        boolean povtoruvajzagrada=false;
        
        String prom="";
        
        File yourFile = new File("rimal.rml");
                if (!yourFile.exists()) {
                    yourFile.createNewFile();
                }

                PrintWriter writer = new PrintWriter("rimal.rml", "UTF-8");
                //writer.println();
                
        
        while(!lista.isEmpty())
        {
            if(prv.ime.equals("процедура") && vtor.ime.equals("("))
            {
                writer.println("main:");
            }
            else if(prv.ime.equals("процедура") && vtor.identifikator.equals("Identifikator"))
            {
                writer.println(vtor.ime + ":");
            }
            
            if(prv.ime.equals("променлива") && vtor.identifikator.equals("Identifikator"))
            {
                writer.println("data " + vtor.ime);
            }
            else if(prv.identifikator.equals("Zapirka") && vtor.identifikator.equals("Identifikator"))
            {
                    writer.println("data " + vtor.ime);
            }
            
            if(prv.identifikator.equals("Zapirka") && (vtor.identifikator.equals("Brojka") || (vtor.ime.equals("И")) || (vtor.ime.equals("З")) || (vtor.ime.equals("С")) || (vtor.ime.equals("Ј"))))
            {
                    if(tret.ime.equals(")"))
                    {
                        writer.println(", "+vtor.ime+")");
                    }
                    else
                    {
                        writer.println(", "+vtor.ime);
                    }
            }
            
            if(prv.identifikator.equals("Identifikator") && vtor.ime.equals("(") && tret.identifikator.equals("Brojka") && cetvrt.ime.equals(")"))
            {
                writer.print("call "+prv.ime+"("+tret.ime+")");
            }
            else if(prv.identifikator.equals("Identifikator") && vtor.ime.equals("(") && tret.identifikator.equals("Identifikator") && cetvrt.ime.equals(")"))
            {
                writer.print("call "+prv.ime+"("+tret.ime+")");
            }
                        
            if(prv.identifikator.equals("Identifikator") && vtor.ime.equals("(") && (tret.identifikator.equals("Brojka") || (tret.ime.equals("И")) || (tret.ime.equals("З")) || (tret.ime.equals("С")) || (tret.ime.equals("Ј"))))
            {
                writer.print("call "+prv.ime+"("+tret.ime);
            }
            else if(prv.identifikator.equals("Identifikator") && vtor.ime.equals("(") && (tret.identifikator.equals("Identifikator") || (tret.ime.equals("И")) || (tret.ime.equals("З")) || (tret.ime.equals("С")) || (tret.ime.equals("Ј"))))
            {
                writer.print("call "+prv.ime+"("+tret.ime);
            }
            
            if(prv.identifikator.equals("Identifikator") && vtor.ime.equals("(") && (tret.identifikator.equals("Brojka") || (tret.ime.equals("И")) || (tret.ime.equals("З")) || (tret.ime.equals("С")) || (tret.ime.equals("Ј"))) && cetvrt.ime.equals(")"))
            {
                writer.print("call "+prv.ime+"("+tret.ime+")");
            }
            else if(prv.identifikator.equals("Identifikator") && vtor.ime.equals("(") && (tret.identifikator.equals("Identifikator") || (tret.ime.equals("И")) || (tret.ime.equals("З")) || (tret.ime.equals("С")) || (tret.ime.equals("Ј")))&& cetvrt.ime.equals(")"))
            {
                writer.print("call "+prv.ime+"("+tret.ime+")");
            }
            
            if(prv.ime.equals("телепорт") && vtor.ime.equals("(") && tret.identifikator.equals("Brojka"))
            {
                writer.print("tlp "+"("+tret.ime);
            }
            
            if(prv.ime.equals("број") && vtor.identifikator.equals("Identifikator"))
            {
                writer.println(prv.ime+" "+vtor.ime);
            }
            
            
            if(prv.identifikator.equals("Identifikator") && vtor.identifikator.equals("Dodeluvanje") && (tret.identifikator.equals("Identifikator")) && cetvrt.ime.equals("+") && petti.identifikator.equals("Brojka"))
            {
                writer.println("push");
                writer.println("move regN "+ prv.ime);
                writer.println("add regN "+ petti.ime);
                writer.println("move "+prv.ime+" regN");
                writer.println("pop");
            }
            else if(prv.identifikator.equals("Identifikator") && vtor.identifikator.equals("Dodeluvanje") && (tret.identifikator.equals("Identifikator")) && cetvrt.ime.equals("-") && petti.identifikator.equals("Brojka"))
            {
                writer.println("push");
                writer.println("move regN "+ prv.ime);
                writer.println("sub regN "+ petti.ime);
                writer.println("move "+prv.ime+" regN");
                writer.println("pop");
            }
            else if(prv.identifikator.equals("Identifikator") && vtor.identifikator.equals("Dodeluvanje") && (tret.identifikator.equals("Identifikator")) && cetvrt.ime.equals("*") && petti.identifikator.equals("Brojka"))
            {
                writer.println("push");
                writer.println("move regN "+ prv.ime);
                writer.println("mul regN "+ petti.ime);
                writer.println("move "+prv.ime+" regN");
                writer.println("pop");
            }
            else if(prv.identifikator.equals("Identifikator") && vtor.identifikator.equals("Dodeluvanje") && tret.identifikator.equals("Brojka"))
            {
                writer.println("move "+ prv.ime +" "+tret.ime);
            }
            
            if((vouslov==true) && prv.ime.equals("!"))
            {
                akozagrada=true;
                writer.println("jne next");
            }
            
            if((akozagrada==true) && prv.ime.equals("!"))
            {
                akozagrada=false;
                vouslov=false;
                writer.println("next:");
            }
            
            if(prv.ime.equals("ако"))
            {
                vouslov=true;
                
                if(prv.ime.equals("ако")&& vtor.ime.equals("жетон"))
                {
                    writer.println("cmp regC $C");
                }
                else
                    if(prv.ime.equals("ако")&& vtor.ime.equals("ѕид"))
                {
                    writer.println("cmp regC $W");
                }
            }
            
            if(prv.ime.equals("повторувај") && vtor.identifikator.equals("Brojka") && tret.ime.equals("пати"))
            {
                vopovtoruvaj=true;
                writer.println("move regN, 0");
                prom=vtor.ime;
            }
            else if(prv.ime.equals("повторувај") && vtor.identifikator.equals("Identifikator") && tret.ime.equals("пати"))
            {
                vopovtoruvaj=true;
                writer.println("move regN, 0");
                prom=vtor.ime;
            }
            
            
            
            if((vopovtoruvaj==true) && (prv.ime.equals("!")))
            {
                povtoruvajzagrada=true;
                writer.println("start:");
            }
            
            if((povtoruvajzagrada==true) && (prv.ime.equals("!")) && akozagrada==false)
            {
                povtoruvajzagrada=false;
                vopovtoruvaj=false;
                writer.println("cmp regN " + prom);
                writer.println("jne");
            }


            if(prv.ime.equals("почеток"))
            {
                writer.println("start:");
            }
            else if (prv.ime.equals("крај"))
            {
                writer.println("ret");
            }
            else if(prv.ime.equals("оди"))
            {
                writer.println("go");
            }
            else if(prv.ime.equals("свртиЛево"))
            {
                writer.println("rl");
            }
            else if(prv.ime.equals("свртиДесно"))
            {
                writer.println("rr");
            }
            else if(prv.ime.equals("земи"))
            {
                writer.println("tk");
            }
            else if(prv.ime.equals("остави"))
            {
                writer.println("lv");
            }
            
//            writer.println(prv.identifikator+" "+prv.ime);
            
            
            prv=vtor;
            vtor=tret;
            tret=cetvrt;
            cetvrt=petti;
            petti=lista.pop();
        }
        LinkedList<Zeton> l = new LinkedList<Zeton>();
        l.add(prv);
        l.add(vtor);
        l.add(tret);
        l.add(cetvrt);
        l.add(petti);
        
        while(!l.isEmpty())
        {
            Zeton p;
            p=l.pop();
            if(p.ime.equals("крај"))
            {
                writer.println("ret");
            }
            else
            {
                writer.println(p.ime);
            }
        }
        
        writer.close();
        
        //writer.println(prv.ime+" "+vtor.ime+" "+tret.ime+" "+cetvrt.ime+" "+petti.ime);
    }
    
//    public static void main(String[] args) throws IOException {
//        Lexer lex = new Lexer("proba.txt");
//        CodeGenerator CG = new CodeGenerator(lex.getLista());
//        CG.fja();
//    }
    
}