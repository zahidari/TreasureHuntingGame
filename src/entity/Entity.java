
package entity;

// karakterler, dokular için ana klasımız.

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

//c
public class Entity {// varlık class'ı
    
    GamePanel gp;
    public int worldX,worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // image leri depolamak. arabelleğine alır. 
    public BufferedImage ropeUp1, ropeUp2, ropeDown1, ropeDown2,
    ropeLeft1, ropeLeft2, ropeRight1, ropeRight2;
    public String direction = "down";
    public int spriteCounter =0; // harektetin bir sağ bir sol adımlı olması için gerekli
    public int spriteNum =1;    // hareketli grafik numarası(sağ ayak sol ayak)
    public Rectangle solidArea= new Rectangle(0,0,48,48); // çarpışma için görünmez bir dikdörtgen çizmemizi sağlar.
    public Rectangle attackArea = new Rectangle(0,0,0,0); // başka yerde ovverride edilecek.
    public int solidAreaDefaultX,solidAreaDefaultY; 
    public  boolean collisionON = false; // playerimiz çarpıyor mu ?
    public int actionLockCounter =0; // npc nin yavaş hareket etmesini kontrol eder.
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    boolean ropeUse = false;
    
    //SuperObject has been moved here...
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    
    
    
     public Entity(GamePanel gp){
         this.gp = gp;
     }
     
     
     // loop dışı çiizim yapmak için image load esnasında scale yapıyoruz
    public  BufferedImage setup(String imagePath, int width, int height){
        
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        
        try{
            // image'i oku ara belleğe al ve scale yap
            image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
            image = uTool.scaleImage(image, width, height);
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return image;
    }//playerImage()'leri çağırıp scale yapmak için kullanılır.
    
    
    
    
   public void setAction(){} //npc hareketi için(override yapılmak için
   public void speak(){
    if(dialogues[dialogueIndex]==null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
            
            
        }
   }
   public void update(){
       
       setAction();
        collisionON = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPlayer(this);
        
        //IF COLLISON IS FALSE, PLAYER CAN MOVE
            if(collisionON == false){
                
                switch(direction){
                case "up"   : worldY-= speed; break;
                case "down" : worldY+= speed; break;
                case "left" : worldX-= speed; break; 
                case "right": worldX+= speed; break;   
                }
            }
        //yrüyormuş gibi yapsın diye
            spriteCounter++;// FPS 60 olduğu için 
            if(spriteCounter >12){ // her 12 FPS de bir karakterin hareketi değişir( hareket eder.)
                if(spriteNum==1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
   } // npc kordinat yenileme(hepsinde aynı)
    
    
    public void draw(Graphics2D g2){
        
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;// hareket halinde nereye çizeceğiz ?
        int screenY = worldY - gp.player.worldY + gp.player.screenY;// player'a göre 500x ve 500y öteye çizmeli

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&// player'ın sol kısmını çizer. + bir kare(gp.tileSize) fazlasını çizer.
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&// player'in sağı
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&// player'ın üstü
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){// player'ın altı
               
            switch(direction){
            case "up":
                if(spriteNum==1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum==1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum==1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum==1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;   
            }
            
            
            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            }
    }
}
