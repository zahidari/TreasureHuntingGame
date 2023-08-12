
package main;

import entity.Entity;
import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import tile.TileManager;

//oyun penceresi olarak çalışacaktır. (herşey burda çalışır)
public class GamePanel extends JPanel implements Runnable{
    
// SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 pixcel tile(doku)
    // çoğu 2d oyunlarında karakterler ve benzeri şeyler bu boyutta oluşturur.
    // fakat ekranımız 1920 x 1080 olaçağından bu objeler küçücuk gözükür.
    // bu durumu düzeltmek için Scale(ölçeklendirme) yapacağiz.
    final int scale = 3; // böylelikle 16x3(scale) = 48 pixel olur
    public final int tileSize = originalTileSize * scale; // 48*48 tile oluşturmak için kullanacağız.
    
    //toplam max ekran boyutumuz. Daha sonra büyütebiliriz.
    public final int maxScreenCol = 16; // x ekseni 16 adet kareden oluşur.
    public final int maxScreenRow = 12; // y ekseni 12 adet kareden oluşur.
    
    public final int screenWidth  = tileSize *maxScreenCol; // 16x48=768 pixcel
    public final int screenHeight = tileSize *maxScreenRow; // 12x48=576 pixcel    
    
//WORLD SETTINGS
    public final int maxWorldCol = 50;// yapacağımız map'e göre değiştirebiliriz.
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap =0;
    
    //ekranın kenarına gelince bize siyah arka plani göstermeyecek ve kamera köşelerde sabit kalacak
    //public final int worldWidth = tileSize * maxWorldCol; 
    //public final int worldHeight = tileSize * maxWorldRow;
    
    int FPS = 60;
// SCREEN SETTINGS, WORLD SET sonu
    //FOR FULL SCREEN
    
    
    //public boolean fullScreenOn = false;
    //FOR FULL SCREEN sonu
    
    
    
    
    
//SYSTEM OBJECTS   
   TileManager tileM = new TileManager(this);// gamepanel clasın içine attık.
   
    //karakter yön girdileri // input classımız.
    public KeyHandler keyH = new KeyHandler(this);// constactorda çağrılır. Ve player'a atanır
    
    public EventHandler eHandler = new EventHandler(this);
    
    Sound music =new Sound();// altta 3 metot belirledik ve soundlarımızı gameSetup ta çağırdık
    //ikisi aynı nesne üzerinden çalışır ama stop metotunda bug çıkıyor
    //o yüzden sound== music + se olarak ikiye ayırdık.
    Sound se =new Sound(); // sound effectleri çalıştırmak için. 
    //(S)ound(E)ffect
    
    
    public CollisionChecker cChecker = new CollisionChecker(this); // update metot içinde check yaparız.
    public AssetSetter aSatter = new AssetSetter(this);//varlıkları konumlandırmak için,SuperObjectle birlikte çalışır
    public UI ui = new UI(this);// yazılar item iconları falan

    //implement Runable etmeliyiz.
    //start, stop ve birşeyi loop döngüsüne girmesini sağlar.
    Thread gameThread;// startlarsak otomatik olarak run() metotunu çalıştırır.
    
//ENTITY AND OBJECT
    //Oyuncumuz, default x ve y, hizimiz.
    public Player player = new Player(this,keyH);
    public Entity obj[][] = new Entity[maxMap][20]; // 10 tane obj tutar
    public Entity npc[][] = new  Entity[maxMap][10];//
    ArrayList<Entity> entityList = new ArrayList<>();// çizmek için öncelik
    
    
//SYSTEM OBJECTS ENDS    
   
    
// OYUN DURUMU (GAME STATE)
    public int gameState;
    
    public final int titleState = 0; // giriş ekranı
    // 1 ve 2 neden ? ilerde değiştirebiliriz. kodta değişiklik yapmadan.
    public final int playState = 1; //oyun ekranı
    public final int pauseState = 2;// durdurma ekranını göstermek için kullanacağız
    //setupGame()'e sonra update()'e geçtik .
    public final int dialogueState= 3;
    public final int optionsState = 5;
    
    
    
// CONSTRACTOR    
    public GamePanel(){
        
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));// JPanelin boyutunu belirler.
        this.setBackground(Color.black);
        
        //true: rendering performansını arttıran bir metot(JPanel fonksunudur)
        this.setDoubleBuffered(true);
        
        this.addKeyListener(keyH);// basilanları dinler.
        this.setFocusable(true);// basilan tuşlara odaklanır. Ek bir fonksiyondur. daha iyi bir oyun deneyimi için.
        
        
    }// constractor sonu
    
    
    
    
    public void setupGame(){
        aSatter.setObject();
        aSatter.setNPC();
    //playMusic(0); // KeyHandler keyPressed "TITLE STATE" VK_ENTER içine alındı
        //stopMusic();// müziği durdurur. oyun başında çalmaz
        gameState = titleState;// oyundan önce ön menü açar
    }
    
//GAME LOOP
    public void startGameThread(){ // metot main de çağrılır.
        
        gameThread = new Thread(this);
        gameThread.start();// run() metotunu çağrır.
        
    }
    
    //FPS metotudur.(DElta/ Accumumator)
    @Override
    public void run(){// update() ve repaint() yapar saniyede 60 kare( 60FPS )
        
        double drawInterval =1000000000/FPS;//  1 nano sn / 60 "kaç sn de bir kare çizeceğimizi belirleriz.
        double delta =0;
        long lastTime =System.nanoTime();
        long currentTime;
        //long timer = 0;
        //int drawCount =0;
        
        while(gameThread != null){
            
            currentTime = System.nanoTime();
            delta +=(currentTime - lastTime)/ drawInterval;
            //timer +=(currentTime -lastTime);// 60 fps ispatlamak içindi.
            lastTime = currentTime;
            
            if(delta >=1){
                update(); // playeri 60FPS de hareketini kontrol eder (tuş'a bastımı?, yürüyormuş hareketi ayarları)
                repaint();// paintComponent(..) fonk çağrır. Çizimleri yapar
                delta--;
                //drawCount++; // kaç fps aldığımızı yazmak içindi.
            }
            
            // kaç fps gösteriyor un ispatı. 1 sn'de bize consolda gösterir.
            /*if(timer >= 1000000000){
                System.out.println("FPS:"+drawCount);
                drawCount = 0;
                timer = 0;
            }*/
            
        }
    }
//PLAYERİN KORDİNATLARINI UPDATE EDER
    public void update(){
        if(gameState == playState){
            //PLAYER
            player.update();// PLAYER'E taşıdık. x,y hareket yenileme
            //NPC
//BURASI çok önemli      //for[1] sadece 2 map varsa, 3 map te bunu arttır. ve aşağıdakilerle de öyle yap
            for(int i = 0;i < npc[1].length; i++){//iki boyutlu old.için npc[1] son array'a kadar dönsün yaptık.
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            } // NPC sonu
        }
        if(gameState == pauseState){
            // KeyHandler classında constractor ekledik
            //hangi ekranı çalıştıracaımızı seçmek için
        }
    }

//CİSİMLER ÇİZ
    public void paintComponent(Graphics g){// çağırmak için repaint();fonk girilir.
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;// Graphics -> Graphics2D ye yükselttik
        // Graphics2D nin daha fazla finksiyonu var.
        
        // Debug optimizasyonu için ön bilgi
        long drawStart =0;
        if(keyH.showDebugText == true){
            drawStart = System.nanoTime();    
        }
        
        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        
        //OTHERS çizilecekler.
        else{
            
            
            // Tile
            tileM.draw(g2);// ilk önce yazılır yoksa karakterimizi göremeyiz!!
            
            entityList.add(player);
            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);// buralar ne olacak ?
                }
            }
            
            for(int i = 0; i < obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);// buralar ne olacak ?
                }
            }
            // SORT. player dedenin arkasında kalacak şekilde yapıyoruz.
            Collections.sort(entityList,new Comparator<Entity>(){
                @Override
                public int compare(Entity e1, Entity e2){
                    
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    
                    return result;
                }
            });
            // DRAW ENTİTYS (SORTED)
            for(int i= 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            //EMPTY ENTİTYS (SORTED)
            entityList.clear();
            /*
            // Object // tüm objecleri tek tek tarar ve ekrana çizer.
            for(int i = 0; i< obj[1].length; i++){
                if( obj[currentMap][i]!= null){
                    obj[currentMap][i].draw(g2,this);
                }
            }
            //Npc
            for(int i = 0; i< npc[1].length; i++){
                if( npc[currentMap][i]!= null){
                    npc[currentMap][i].draw(g2);
                }
            }
            
            
            
            // Player
            // tiles(fayanslar) comes first than player
            player.draw(g2);//entity->player'a taşıdık.
            */
            
            
            //UI
            ui.draw(g2);
            
        }//OTHERS  else sonu 
        
        
        
        
        // debug optimizasyon için ön bilgilendirme kodudur. oyun durumunu etkilemez
        if(keyH.showDebugText == true){
            long drawEnd = System.nanoTime();
            long passed  = drawEnd - drawStart;
            
            g2.setFont(new Font("Arial",Font.PLAIN,20));
            g2.setColor(Color.white);
            int x = 10;
            int y = 400;
            int lineHeight = 20;
                    
            g2.drawString("worldX: "+player.worldX, x, y); y+= lineHeight;
            g2.drawString("worldY: "+player.worldY, x, y); y+= lineHeight;
            g2.drawString("Col: "+(player.worldX+player.solidArea.x)/tileSize, x, y); y+= lineHeight;
            g2.drawString("Row: "+(player.worldY+player.solidArea.y)/tileSize, x, y); y+= lineHeight;
            g2.drawString("Draw Time: "+passed, x, y);
            
            
            
            
        }
        
        g2.dispose();//memory save
        
    }
//CİSİMLER ÇİZ ends
    
    
//MUSİC METOTS
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){//SE = sound effect. örnek key toplama ya da coin toplama sesi
        se.setFile(i);
        se.play();
    }
}
//MUSİC METOT ends
