
package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;



public class Player  extends Entity{
    
    
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    
    //public int life = 3;
    public boolean haveRope = false;
    public int haveKey = 0;
    
    int standCounter = 0; // durunca karakter görüntüsünü duruyormuş gibi çizmesine yarar
    //boolean moving = false;//pokemon oyunları gibi kare kare hareket etmesini sağlar.
    //int pixelCounter = 0;// pokemon oyun...
    
    
//CONSTRACTOR
    public Player(GamePanel gp, KeyHandler keyH){
        
        super(gp);
        this.keyH = keyH;
        
        
        screenX = gp.screenWidth /  2 - (gp.tileSize/2); //ekranın ortasına kamerayı konumlandırıyoruz.
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);// "-(gp.tileSi.../2) == doku karesi her zaman sol üst köşeden itibaren çizilir. onu ortaladık.
        
        // 8,16,32,32 ayarlarında basıldığı kadar pixcel gitmesi için idi.
        solidArea = new Rectangle(8,16,32,32);// karakterden küçük olmalı ki kapıdan geçsin.8-32 ve 16-32
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        attackArea.width = 72;
        attackArea.height=72;
        
        setDefaultValues();// muhakak burda olmalı
        getPlayerImage();
        getPlayerRopeImage();
        
    }
//
    public void setDefaultValues(){//player positon on the world map
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        speed = 4;
        direction = "down";
    }
    // daha verilmi FPS çizebilmek için önceden çizimleri yapıp loop içinde çağırma yaptık.
    public void getPlayerImage(){
        
        up1 = setup("/player/boy_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/player/boy_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/player/boy_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/player/boy_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/player/boy_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/player/boy_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/player/boy_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/player/boy_right_2",gp.tileSize,gp.tileSize); 
    }// getPlayerImage() sonu
    public void getPlayerRopeImage(){
        
        ropeUp1 = setup("/player/rope_attack_up1",gp.tileSize,gp.tileSize*2);
        ropeUp2 = setup("/player/rope_attack_up2",gp.tileSize,gp.tileSize*2);
        ropeDown1 = setup("/player/rope_attack_down1",gp.tileSize,gp.tileSize*2);
        ropeDown2 = setup("/player/rope_attack_down2",gp.tileSize,gp.tileSize*2);
        ropeLeft1 = setup("/player/rope_attack_left1",gp.tileSize*2,gp.tileSize);
        ropeLeft2 = setup("/player/rope_attack_left2",gp.tileSize*2,gp.tileSize);
        ropeRight1 = setup("/player/rope_attack_right1",gp.tileSize*2,gp.tileSize);
        ropeRight2 = setup("/player/rope_attack_right2",gp.tileSize*2,gp.tileSize);
    }
  
//PLAYERİN KORDİNATLARINI UPDATE EDER ve çizilecek adım karesini(sağ adım mı sol adım mı?)  belirler
    @Override
    public void update(){
            
        if(ropeUse == true){
            ropeUseing();
        }
        else if(keyH.spacePressed == true){
                
                if(haveRope == true){
                    ropeUse = true;
                    gp.playSE(6);
                }
                
            }
        
        //bunu yazmamızın sebebi hareket ederse karakter, görüntüsü hareket eder.
        // aksi taktirde tuşlara basmasak bile hareket ediyordu. spriteCounter için yazdık. 
        else if(keyH.upPressed==true|| keyH.downPressed == true ||
            keyH.leftPressed==true||keyH.rightPressed== true){
            if(keyH.upPressed == true){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }
            
            
            
            // CHECK TİLE COLLISON
            collisionON = false;
            gp.cChecker.checkTile(this);
                
            // CHECK OBJECT COLLISON
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
        
       
            //CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);// npc array ile playeri kıyaslar
            interactNPC(npcIndex);
            
            //CHECK EVENT
            gp.eHandler.checkEvent();
            
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
            if(spriteCounter >10){ // her 12 FPS de bir karakterin hareketi değişir( hareket eder.)
                if(spriteNum==1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            /*else{
                standCounter++;
                
                if(standCounter == 20){ 
                spriteNum = 1;
                standCounter = 0;
                }   
            }*/
        }
    }//update() sonu
    public void ropeUseing(){// kendi metotum
        
        
        spriteCounter++;
        
        if(spriteCounter <=10){
            spriteNum =1;
        }
        if(spriteCounter >10 && spriteCounter <=35){
            spriteNum = 2;
            
            
            // save current position becuse hit detection will more forward than player
            int currentWorldX   = worldX;
            int currentWorldY   = worldY;
            int solidAreaWidth  = solidArea.width;
            int solidAreaHeight = solidArea.height;
            
            // adjust players worldX/Y for the attackArea
            switch (direction) {
                case "up"  :worldY  -= attackArea.height;break;
                case "down":worldY  += attackArea.height;break;
                case "left":worldX  -= attackArea.width;break;
                case "right":worldX += attackArea.width;break; 
            }
            
            // attack area becomes solid area
            solidArea.width  = attackArea.width;
            solidArea.height = attackArea.height;// for collision check
            
            //check object collision with the updated worldx, worldy and solidArea
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
             
            
            
            //after ckecking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width  =solidAreaWidth;
            solidArea.height  =solidAreaHeight;
            
            
        }
        if(spriteCounter > 35){
            spriteNum= 1;
            spriteCounter = 0;
            ropeUse = false;
        }
    }
    
    
   public void interactNPC(int i){
       
       if(i != 999){
           
           if(gp.keyH.enterPressed == true){
               gp.gameState = gp.dialogueState;
           gp.npc[gp.currentMap][i].speak();
           }
       }
       //gp.keyH.enterPressed = false;// açınca enter tuşuna basmalısın ki ihtiyarla konuşmak için
   }
    
    
    
    public void pickUpObject(int i){// key topla door aç...
        
        if(i != 999){// i == 999 ise oyuncu birseye dokunmamıştır.
            
            String objectName = gp.obj[gp.currentMap][i].name;
           
           
            if(objectName== "Boots"){
              gp.obj[gp.currentMap][i] = null;
              gp.playSE(2);
              speed++;
             
            }
            else if(objectName== "Key"){
              gp.obj[gp.currentMap][i] = null;
              gp.playSE(1);
              haveKey++;
              gp.ui.showMessage("anahtar buldun!");
             
            }
            else if(objectName== "Rope"){
                gp.obj[gp.currentMap][i] = null;
                gp.playSE(1);
                haveRope = true;
                gp.ui.showMessage("ip buldun!");
            }
            else if(objectName== "Door"){
                if(haveKey >0){
                    haveKey--;
                    gp.obj[gp.currentMap][i]= null; 
                    gp.playSE(3);
                    gp.ui.showMessage("Açıl susam açıl!");
                }else{
                    
                    gp.ui.showMessage("anahtar lazım galiba..");
                }
            }
            else if(objectName == "Chest"){
                gp.ui.gameFinished = true;
                gp.stopMusic();
                gp.playSE(4);
                
            }
        }
    }
// PLAYERİN ŞEKLİNİ ÇİZER.
    @Override
    public void draw(Graphics2D g2){
        
        //g2.setColor(Color.white);
        //g2.fillRect(x, y, gp.tileSize, gp.tileSize);// karedir. resim kullanmadan önceydi
        
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch(direction){
            case "up":
                if(ropeUse == false){
                    if(spriteNum==1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                }
                if(ropeUse == true){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum==1){image = ropeUp1;}
                    if(spriteNum == 2){image = ropeUp2;}
                }
                break;
            case "down":
                if(ropeUse == false){
                    if(spriteNum==1){image = down1;}
                    if(spriteNum == 2){image = down2;}
                }
                if(ropeUse == true){
                    if(spriteNum==1){image = ropeDown1;}
                    if(spriteNum == 2){image = ropeDown2;}
                }
                
                break;
            case "left":
                if(ropeUse == false){
                    if(spriteNum==1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                }
                if(ropeUse == true){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum==1){image = ropeLeft1;}
                    if(spriteNum == 2){image = ropeLeft2;}
                }
                break;
            case "right":
                if(ropeUse == false){
                    if(spriteNum==1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                }
                if(ropeUse == true){
                    if(spriteNum==1){image = ropeRight1;}
                    if(spriteNum == 2){image = ropeRight2;}
                }
                break;   
        }
        
        /*
        // **kamerayı,karakter koşelere gelince durdurmak için oluşturuldu**
        int x = screenX;
        int y = screenY;
        
        if(screenX  > worldX) {
            x = worldX;
        }
        if(screenY  > worldY){
            y = worldY;
        }
            int rightOffset = gp.screenWidth - screenX;
            if(rightOffset > gp.worldWidth - worldX){
                x = gp.screenWidth - (gp.worldWidth-worldX);
            }
            int bottomOffset = gp.screenHeight - screenY;
            if(bottomOffset > gp.worldHeight - worldY){
                y = gp.screenHeight - (gp.worldHeight-worldY);
            }
        //** kamera köşe bağlantısı sonu. **
        */
        g2.drawImage(image, tempScreenX,tempScreenY,null);
    }
}
