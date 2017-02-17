
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JPanel;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Animacija extends JPanel {
    
    long lastTime = System.nanoTime();
    final double amountOfTicks = 40;
    double ns = 1000000000 / amountOfTicks;
    double delta = 0;
    int updates = 0;
    int frames = 0;
    long timer = System.currentTimeMillis();
    LinkedList<Dzid> dzidovi;
    LinkedList<ZetonMapa> zetoni;
    LinkedList<String> komandi;
    Igrac igrac;
    int[][] mapa;
    BufferedImage dzid;
    BufferedImage zeton;
    BufferedImage robot;
    BufferedImage pod;
    int broj_komanda = 0;
    float igrac_poz_x;
    float igrac_poz_y;
    float igrac_rot;
    boolean prekini = false;
    boolean prekini2 = false;
    int igrac_mom_x;//za zetoni
    int igrac_mom_y;
    public int X;
    public int Y;
    boolean pocni_so_crtanje = false;
    boolean posledna_komanda = false;
    private static LinkedList<Zeton> listata;
    
    public Animacija(int golemina_x, int golemina_y, LinkedList dzidovi, LinkedList zetoni, Igrac igrac) {
        mapa = new int[golemina_x][golemina_y];
        this.dzidovi = dzidovi;
        this.zetoni = zetoni;
        this.igrac = igrac;
    }
    
    private ArrayList<String> citajDatoteka(String dokument) {
        BufferedReader br = null;
        try {
            String text = "";
            ArrayList<String> listata = new ArrayList<String>();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(
                    dokument), "UTF-8"));
            while (br.ready()) {
                // text += br.readLine() + "\n";
                komandi.add(br.readLine());
                
            }
            return listata;
            
        } catch (Exception e) {
        }
        return new ArrayList<String>();
    }
    
    public Animacija() throws IOException {
        //za testiranje
        X = 10;
        Y = 10;
        Matrica m = null;
        while (true) {
            EnvLexer lex = new EnvLexer("okolina_def.txt");
            listata = lex.getLista();

            if (!lex.imaGreski()) {
                break;
            }

            EnvParser pars = new EnvParser("главна", listata, 4, 13);
            if (pars.parsiraj()) {
                break;
            }
            lex = new EnvLexer("okolina_def.txt");
            listata = lex.getLista();

            m = new Matrica(listata);
            break;
        }
        //1 e dzid
        //2 e zeton
        //3 e igrac
        mapa = m.vrati_mapa();
        X=m.vrati_x();
        Y=m.vrati_y();
        this.igrac = m.vrati_igrac(); // new Igrac(1, 1, "I");
        igrac_poz_x = igrac.x;
        igrac_poz_y = igrac.y;
        igrac_mom_x = igrac.x;
        igrac_mom_y = igrac.y;
        
        if (igrac.nasoka.equals("И")) {
            igrac_rot = 90;
        } else if (igrac.nasoka.equals("З")) {
            igrac_rot = -90;
        } else if (igrac.nasoka.equals("С")) {
            igrac_rot = 0;
        } else if (igrac.nasoka.equals("Ј")) {
            igrac_rot = 180;
        }
        
        this.komandi = new LinkedList<String>();
         citajDatoteka("niza.niz");

        
        try {
            this.dzid = ImageIO.read(new File("dzid.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Animacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            this.zeton = ImageIO.read(new File("zeton.png"));
        } catch (IOException ex) {
            Logger.getLogger(Animacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            this.robot = ImageIO.read(new File("robot.png"));
        } catch (IOException ex) {
            Logger.getLogger(Animacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.pod = ImageIO.read(new File("pod.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Animacija.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.LIGHT_GRAY);
        
        Dimension size = getSize();
        Insets insets = getInsets();
        
        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        
        
        
        g2d.drawImage(pod, 0, 0, w, h, this);
        
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                g2d.drawRect((i * w) / mapa.length, (j * h) / mapa[0].length, w / mapa.length, h / mapa[0].length);
                if (mapa[i][j] == 1) {
//                    g2d.fillRect((i * w) / mapa.length, (j * h) / mapa[0].length, w / mapa.length, h / mapa[0].length);
                    g2d.drawImage(dzid, (i * w) / mapa.length, (j * h) / mapa[0].length, w / mapa.length, h / mapa[0].length, this);
                }
                if (mapa[i][j] == 2) {

                    //  g2d.fillOval((i * w) / mapa.length, (j * h) / mapa[0].length, w / mapa.length, h / mapa[0].length);
                    g2d.drawImage(zeton, (i * w) / mapa.length, (j * h) / mapa[0].length, w / mapa.length, h / mapa[0].length, this);
                }
//                    if (mapa[i][j] == 3) {
//                        g2d.drawOval((i * w) / mapa.length, (j * h) / mapa[0].length, w / mapa.length, h / mapa[0].length);
//                        if (igrac.nasoka.equals("I")) {
//                        }
//                    }
            }
        }
        
        
        
        
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        
        
        
        if (delta >= 1) {
            if (broj_komanda < komandi.size()) {
                String komanda = komandi.get(broj_komanda);

                // System.out.println(komanda);
//                System.out.println(igrac.nasoka);

                System.out.println(igrac_mom_x + " " + igrac_mom_y + " " + posledna_komanda);
                
                
                if (komanda.equals("Odi")) {
                    if (igrac.nasoka.equals("И")) {
                        if (igrac_mom_x + 1 > mapa.length - 1) {
                            igrac_poz_x += (float) (w / mapa.length) / 80;
                            //   posledna_komanda = true;
                        } else if (mapa[igrac_mom_x + 1][igrac_mom_y] != 1) {
                            igrac_poz_x += (float) (w / mapa.length) / 80;
                            igrac_poz_y += 0;
                        }
                    } else if (igrac.nasoka.equals("З")) {
                        if (igrac_mom_x - 1 < 0) {
                            igrac_poz_x -= (float) (w / mapa.length) / 80;
                            //   posledna_komanda = true;
                        } else if (mapa[igrac_mom_x - 1][igrac_mom_y] != 1) {
                            igrac_poz_x -= (float) (w / mapa.length) / 80;
                            igrac_poz_y += 0;
                        }
                    } else if (igrac.nasoka.equals("С")) {
                        if (igrac_poz_y - 1 < 0) {
                            igrac_poz_y -= (float) (h / mapa[0].length) / 80;
                            //  posledna_komanda = true;
                        } else if (mapa[igrac_mom_x][igrac_mom_y - 1] != 1) {
                            igrac_poz_x += 0;
                            igrac_poz_y -= (float) (h / mapa[0].length) / 80;
                        }
                    } else if (igrac.nasoka.equals("Ј")) {
                        if (igrac_mom_y + 1 > mapa[0].length - 1) {
                            igrac_poz_y += (float) (h / mapa[0].length) / 80;
                            //   posledna_komanda = true;
                        } else if (mapa[igrac_mom_x][igrac_mom_y + 1] != 1) {
                            igrac_poz_x += 0;
                            igrac_poz_y += (float) (h / mapa[0].length) / 80;
                        }
                    }
                }
                
                if (!posledna_komanda) {
                    if (komanda.equals("Odi")) {
                        if (!prekini2) {
                            if (igrac.nasoka.equals("И")) {
                                if (igrac_mom_x + 1 > mapa.length - 1) {
                                    posledna_komanda = true;
                                } else if (mapa[igrac_mom_x + 1][igrac_mom_y] != 1) {
                                    igrac_mom_x += 1;
                                    prekini2 = true;
                                }
                            } else if (igrac.nasoka.equals("З")) {
                                if (igrac_mom_x - 1 < 0) {
                                    posledna_komanda = true;
                                } else if (mapa[igrac_mom_x - 1][igrac_mom_y] != 1) {
                                    igrac_mom_x -= 1;
                                    prekini2 = true;
                                }
                            } else if (igrac.nasoka.equals("С")) {
                                if (igrac_mom_y - 1 < 0) {
                                    posledna_komanda = true;
                                } else if (mapa[igrac_mom_x][igrac_mom_y - 1] != 1) {
                                    igrac_mom_y -= 1;
                                    prekini2 = true;
                                }
                            } else if (igrac.nasoka.equals("Ј")) {
                                if (igrac_mom_y + 1 > mapa[0].length - 1) {
                                    posledna_komanda = true;
                                } else if (mapa[igrac_mom_x][igrac_mom_y + 1] != 1) {
                                    igrac_mom_y += 1;
                                    prekini2 = true;
                                }
                            }
                        }
                    }
                    
                    if (komanda.equals("SvrtiDesno")) {
                        if (!prekini) {
                            if (igrac.nasoka.equals("И")) {
                                igrac.nasoka = "Ј";
                                prekini = true;
                            } else if (igrac.nasoka.equals("С")) {
                                igrac.nasoka = "И";
                                prekini = true;
                            } else if (igrac.nasoka.equals("З")) {
                                igrac.nasoka = "С";
                                prekini = true;
                            } else if (igrac.nasoka.equals("Ј")) {
                                igrac.nasoka = "З";
                                prekini = true;
                            }
                        }
                        igrac_rot += (float) 90 / 80;
                    }
                    
                    if (komanda.equals("SvrtiLevo")) {
                        if (!prekini) {
                            if (igrac.nasoka.equals("И")) {
                                igrac.nasoka = "С";
                                prekini = true;
                            } else if (igrac.nasoka.equals("С")) {
                                igrac.nasoka = "З";
                                prekini = true;
                            } else if (igrac.nasoka.equals("З")) {
                                igrac.nasoka = "Ј";
                                prekini = true;
                            } else if (igrac.nasoka.equals("Ј")) {
                                igrac.nasoka = "И";
                                prekini = true;
                            }
                        }
                        igrac_rot -= (float) 90 / 80;
                    }
                    
                    if (komanda.startsWith("Teleport")) {
                        
                        LinkedList<String> koord = new LinkedList<String>();
                        
                        Pattern pattern = Pattern.compile("[0-9]+");
                        Matcher matcher = pattern.matcher(komanda);
                        while (matcher.find()) {
                            koord.add(matcher.group());
                        }
                        
                        int x = Integer.valueOf(koord.getFirst());
                        int y = Integer.valueOf(koord.get(1));
                        if (!(x < 0 || y < 0 || x > mapa.length - 1 || y > mapa[0].length - 1)) {
                            if (mapa[x][y] != 1) {
                                igrac_poz_x = (w / mapa.length) * x;
                                igrac_poz_y = (h / mapa[0].length) * y;
                                System.out.println(igrac_poz_x + " " + igrac_poz_y);
                                //////////////////////////////////////////////////////  
                                igrac_mom_x = x;
                                igrac_mom_y = y;
                                System.out.println(x + " " + y);
                            }
                        }
                        koord.clear();
                    }
                }
                
                if (!(igrac_mom_x > mapa.length || igrac_mom_x < 0 || igrac_mom_y > mapa[0].length || igrac_mom_y < 0)) {
                    if (komanda.equals("ZemiZeton")) {
                        if (mapa[igrac_mom_x][igrac_mom_y] == 2) {
                            mapa[igrac_mom_x][igrac_mom_y] = 0;
                        }
                    }
                    if (komanda.equals("OstaviZeton")) {
                        //   System.out.println(mapa[igrac_mom_x][igrac_mom_y]);
                        if (mapa[igrac_mom_x][igrac_mom_y] != 1) {
                            mapa[igrac_mom_x][igrac_mom_y] = 2;
                        }
                    }
                }
                
                
                
                
            }
            updates++;
            delta--;
        }

//        AffineTransform transform = new AffineTransform();
//        transform.rotate(Math.toRadians(igrac_rot), 5, 5);
        g2d.translate(igrac.x + igrac_poz_x, igrac.y + igrac_poz_y);
//        g2d.fill(new Rectangle(((igrac.x) * w) / mapa.length, ((igrac.y) * h) / mapa[0].length, w / mapa.length, h / mapa[0].length));
        g2d.rotate(Math.toRadians(igrac_rot), (w / mapa.length) / 2, (h / mapa[0].length) / 2);
        //  g2d.drawRect(((igrac.x) * w) / mapa.length, ((igrac.y) * h) / mapa[0].length, w / mapa.length, h / mapa[0].length);

        int robot_golemina_x = w / mapa.length;
        int robot_golemina_y = h / mapa[0].length;
        int robot_poc_x = ((igrac.x) * w) / mapa.length;
        int robot_poc_y = ((igrac.y) * h) / mapa[0].length;
        g2d.drawImage(robot, robot_poc_x, robot_poc_y, robot_golemina_x, robot_golemina_y, null);
        frames++;
        
        if (System.currentTimeMillis() - timer > 2000) {
            timer += 2000;
            System.out.println(updates + " " + frames);
            updates = 0;
            frames = 0;
            broj_komanda++;
            prekini = false;
            prekini2 = false;
        }




//        for (int i = 0; i < komandi.size(); i++) {
//            String komanda = komandi.get(i);
//            int igrac_x = igrac.x;
//            int igrac_y = igrac.y;
//
//            System.out.println(komanda);
//            if (komanda.equals("Odi")) {
//                for (int j = 0; j < 10; j++) {//10 frejma
//                    repaint();
//                    g2d.drawOval((igrac_x * w) / mapa.length, (igrac_x * h) / mapa[0].length, w / mapa.length, h / mapa[0].length);
////                    try {
////                        Thread.sleep(100);
////                    } catch (InterruptedException ex) {
////                        Logger.getLogger(Animacija.class.getName()).log(Level.SEVERE, null, ex);
////                    }
//                    igrac_x += 32;
//                    igrac_y += 32;
//                    g2d.drawLine(0, 0, igrac_x, igrac_y);
//                    System.out.println(igrac_x+" "+igrac_y);
//                }
//            }
//        }

//        g2d.drawLine(0, 0, 50, 50);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        doDrawing(g);
        super.repaint();
    }
    
    public void pecatiMapa() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                System.out.print(mapa[i][j]);
            }
            System.out.println();
        }
    }
}

class Points extends JFrame {
    
    public Points() {
        initUI();
    }
    
    private void initUI() {
        try {
            setTitle("Animacija");
            // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Animacija a = new Animacija();
            
            add(a);
            //animacija
            a.pecatiMapa();
            //a.repaint();
            setSize(a.X * 50, a.Y * 50);
            // setSize(500, 500);
            setLocationRelativeTo(null);
            //   setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(Points.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void main(String[] args) throws IOException {
//        
//        Lexer lex = new Lexer("proba.txt");
//        
//        while (true) {
//            
//            if (!lex.imaGreski()) {
//                break;
//            }
//            
//            LanParser pars = new LanParser("главна", lex.getLista(), 28, 34);
//            pars.parsiraj();
//            break;
//        }
//        
//        
//        Lexer lexCG = new Lexer("proba.txt");
//        
//        CodeGenerator CG = new CodeGenerator(lexCG.getLista());
//        CG.fja();
//        
//        
//        Lexer lexNK = new Lexer("proba.txt");
//        
//        NizaKomandi NK = new NizaKomandi(lexNK.getLista());
//        NK.fja();
//        
//        
//        
//        SwingUtilities.invokeLater(new Runnable() {
//            
//            @Override
//            public void run() {
//                
//                Points ps = new Points();
//                ps.setVisible(true);
//            }
//        });
//    }
}
