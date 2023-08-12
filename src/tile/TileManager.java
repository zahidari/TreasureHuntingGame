
package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;


public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[50]; // 10 tane farkli fayans( arka plan fayansı)
        mapTileNum = new int [gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        
        
        getTileImage();
// WORLD TXT LOAD HERE ----->        
        loadMap("/maps/worldV3.txt",0);
        loadMap("/maps/interior01.txt",1);
    }
    
    public void getTileImage(){// tile image leri load eder.kullanmak için
        
        
            /*
        try{
        
            Bilgilendirme:  
            loop içerinde görüntü çizmek daha zahmetlidir ve FPS düşebilir.
             eski bilgisayarlar yavaş işleyebilir. bunu engellemek için
            resimleri Scale(yeniden boyutlandırma)'i daha image'i yüklerken yapmamız gerekiyor ve
             bu şekilde loop içersinde sadece çağırmamız yeterli hale gelecektir.
             bunun tersi loop içinde sürekli kareleri her 60FPS kere çizmektır. zahmetlidir. yavaşlatır.
            
            
            eskiden bu şekilde loop içinde çizdirirdik. işlem gerektirior.farkli yönteme geçtik. loop dışı yüklerken çizdireceğiz
            
            tile[0]= new Tile();//grass için
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
                       
            tile[1]= new Tile();//wall
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true; // çarpışma açık duvardan geçemezsin 
            
            tile[2]= new Tile();//water için
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;
            
            tile[3]= new Tile();//earth
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
            
            tile[4]= new Tile();//tree
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;
            
            tile[5]= new Tile();// sand
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
            
            }catch(IOException e){
            e.printStackTrace();
        }
            */
            /* new tiles geldi
            setup(0, "grass", false);
            setup(1, "wall", true);
            setup(2, "water", true);
            setup(3, "earth", false);
            setup(4, "tree", true);
            setup(5, "sand", false);
            */
            // 0 dan 10 kadar yazılı ama sadece 10 numaradan itibaren kullancak
            // neden diğerleri silinmiyor ? draw yaparken for döngüsü hata vermesin diye.
            //placeHolder
            setup(0, "grass00", false);// grass
            setup(1, "grass00", true); // wall
            setup(2, "grass00", true);// water yerşne yazılabilir, eski harita için, hata aınıyor.
            setup(3, "grass00", false);//earth 
            setup(4, "grass00", true); // tree
            setup(5, "grass00", false);// sand
            setup(6, "grass00", false);
            setup(7, "grass00", false);
            setup(8, "grass00", false);
            setup(9, "grass00", false);
            
            // burdan itibaren kullanacağız( harita oluştururken tile numaraları iki basamaklı olsun diye
            setup(10, "grass00", false);// haritayı kodlarken 50x50 görünümü bozulmasın kolay oluşturulsun ve okunsun
            setup(11, "grass01", false);
            setup(12, "water00", true);
            setup(13, "water01", true);
            setup(14, "water02", true);
            setup(15, "water03", true);
            setup(16, "water04", true);
            setup(17, "water05", true);
            setup(18, "water06", true);
            setup(19, "water07", true);
            setup(20, "water08", true);
            setup(21, "water09", true);
            setup(22, "water10", true);
            setup(23, "water11", true);
            setup(24, "water12", true);
            setup(25, "water13", true);
            
            setup(26, "road00", false);
            setup(27, "road01", false);
            setup(28, "road02", false);
            setup(29, "road03", false);
            setup(30, "road04", false);
            setup(31, "road05", false);
            setup(32, "road06", false);
            setup(33, "road07", false);
            setup(34, "road08", false);
            setup(35, "road09", false);
            setup(36, "road10", false);
            setup(37, "road11", false);
            setup(38, "road12", false);
            
            setup(39, "earth", false);
            setup(40, "wall", true);
            setup(41, "tree", true);
            
            setup(42, "hut", false);
            setup(43, "floor01", false);
            setup(44, "table01", true);
            setup(45, "teleport", false);
            
            
            
            
            
            
            
            // all the images are scaled.
            
        
    }
    //utilityTool dan bufferedImage scaledımage için setuptır.
    //tile image'leri loop dışında scale için kullanılır.
    public void setup(int index, String imageName, boolean collision){
        
        UtilityTool uTool = new UtilityTool();// kendi kodlarımızı classta tanımladık
        
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            
        } catch (Exception e) {
                e.printStackTrace();
        }
    }// getTileImage() metotunda çağrılır.
   public void loadMap(String filePath,int map){// filePath' ı constractorda içine yazıyoruz.
        try {
            InputStream is = getClass().getResourceAsStream(filePath);// import txt file
            BufferedReader br = new BufferedReader(new InputStreamReader(is));// read the context in the file.
            
            int col =0;
            int row =0;
            
            //
            while(col<gp.maxWorldCol&& row<gp.maxWorldRow){
                String line = br.readLine(); //tek tek text  satırı oku.
                
                while(col < gp.maxWorldCol){
                    String numbers[]= line.split(" ");// boşluksuz arraya sayıları ekleme.
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();//memory save
            
        } catch (Exception e) {
            
        }
    } 
    
    public void draw(Graphics2D g2){// maps'i çizer
        
        int worldCol = 0;
        int worldRow = 0;
        
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
           
            int worldX = worldCol * gp.tileSize; //  0*48
            int worldY = worldRow * gp.tileSize; //  0*48
            // offset yaptık ki ekranın dışında kalan kısım için == "+gp.player.screenX"
            int screenX = worldX - gp.player.worldX + gp.player.screenX;// hareket halinde nereye çizeceğiz ?
            int screenY = worldY - gp.player.worldY + gp.player.screenY;// player'a göre 500x ve 500y öteye çizmeli
            
            
            /*
            // ******stop the kamera at the edge************
            if(gp.player.screenX > gp.player.worldX){
                screenX = worldX;//sol kenar için sabitleme
            }
            if(gp.player.screenY > gp.player.worldY){
                screenY = worldY;// üst kenar için sabitleme
            }
            //sağ kenar ve alt kenarda bir sorunumuz var. nedeni
            //JAVA, karakter karesinin sol üst kısmından itibaren çizer(16x16'yı)
            // bu sefer sağ ve alt kenar için ayrı hesaplama yapmamız lazım.
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if(rightOffset > gp.worldWidth - gp.player.worldX){
                screenX = gp.screenWidth - (gp.worldWidth-worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if(bottomOffset > gp.worldHeight - gp.player.worldY){
                screenY = gp.screenHeight - (gp.worldHeight-worldY);
            }
            //****kamera stop adge bitimi******* Aşağıda ve player sınıfında da değişiklikler var.
            */
            
            
            
            // world mapi komple çizMEmek için if içine drawİmage sadece gödüğümüz kadar çizmemizi sağlar.
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&// player'ın sol kısmını çizer. + bir kare(gp.tileSize) fazlasını çizer.
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&// player'in sağı
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&// player'ın üstü
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){// player'ın altı
                
               g2.drawImage(tile[tileNum].image,screenX,screenY,null); //getTile() ile scale yapmamıza gerek kalmamıştır. gp.tileSize== width==height silindi
                
            }
            /*
            //*****stop the kamera at the edge *********
            // köşeye gidince ekran bu sefer ters köşeden kısılıyor.çünkü
            //player'a göre dört bir tarafı çiziliyor.
            else if(gp.player.screenX > gp.player.worldX||
                    gp.player.screenY > gp.player.worldY||
                    rightOffset > gp.worldWidth - gp.player.worldX||
                    bottomOffset > gp.worldHeight - gp.player.worldY){
                
                g2.drawImage(tile[tileNum].image,screenX,screenY,null);
            }//*******stop kamera at the edge bitimi********
            */
            worldCol++;
            
            
            if(worldCol == gp.maxWorldCol){// satır sonuna kadar okur sonra alt satıra geçmemizi sağlar.
                worldCol = 0;
                worldRow++;
                
            }
        }
        
    }
    
}
