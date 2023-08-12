
package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;



/**
 * 
 * 
 *on screen UI kontrolü 
 * we can display text message
 * item icons ve benzeri 
*/

public class UI {
    
    
    GamePanel gp;
    Graphics2D g2;
    Font /*arial_40, arial_80B,*/purisaB; // 60/sn kez ekranda fontu çağırmasın diye burada tanımladık. 
    //arial_80B == bold (kalın) demek
    public boolean messageON = false;
    public String message ="";
    int messageCounter=0;
    public boolean gameFinished = false;// player/pickUpObject()'de çağrılıyor.
    public String  currentDialogue = "";// anlık konuşma
    public int commandNum =0;// drawTitleScreen() içinde MENU "seçim çubuğunu" çizmek için.
    public int titleScreenState = 0;//(sub(alt) ekranlar) 0: ilk ekran, 1: ikinci ekran
    int subState = 0;
    
    
    
    
    public UI(GamePanel gp){
        this.gp = gp;
        // burada tanımladık çünkü 60FPS şeklinde sürekli font üretecekti.
        //arial_80B = new Font("Arial", Font.BOLD, 80);// "the End" yazısı için
        //arial_40 = new Font("Arial", Font.PLAIN, 40);// draw da çağıracağız. 
        
        
        try{
            InputStream is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB =Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException e){
               e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }// Constractor sonu
    
    public void showMessage(String text){// player'da key alınca çağrılır.
        message = text;
        messageON = true;
    }
    
    public void draw(Graphics2D g2){
        
        this.g2 = g2;// g2 yi bu class'ın başka metotlarında kullanmak için yaptık
        
        g2.setFont(purisaB);
        g2.setColor(Color.white);
        
        
        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        
        //PLAY STATE
        if(gp.gameState == gp.playState){
            //do playSatete stuff later
            drawPlayScreen();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //DIALOG STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        //OPTIONS STATE
        if(gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
    }
    public void drawPlayScreen(){
        
        if(gameFinished == true){
           
            g2.setFont(purisaB);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
            
            String text;
            int textLength;
            
            text = "Kazandın !";
            textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            
            int x = gp.screenWidth /2 - textLength/2;
            int y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text,x,y);
            
            gp.gameThread = null;
            
            
        }else{
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
            g2.drawString("Key:"+gp.player.haveKey, 25,50);
            if(messageON == true){
            
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
                g2.drawString(message, gp.tileSize/2,gp.tileSize*5);
            
                messageCounter++;
            
                if(messageCounter> 120){
                    messageCounter =0;
                    messageON = false;
                }
            }
        }
        
        
    }
    public void drawTitleScreen(){
        
        
        if(titleScreenState ==0) {
            
            
            //arka plan rengini değiştirmek
            g2.setColor(new Color(0,0,0)); // rgb 
            g2.fillRect(0 , 0, gp.screenWidth, gp.screenHeight);
        
            //TITLE NAME ( in menü)
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 76F));
            String text = "HAZINE AVCISI";
        
            // tam ortada oluşması görünmesi için.
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;// 3 kare aşağıya çiz(yukarıdan itibaren.)
            
            // Gölgelendirme
            g2.setColor(Color.gray);
            g2.drawString(text,x+5,y+5);
        
            //Yazının ana rengi
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
        
            //Blue boy image ekranın ortasında gözüklesi
            x = gp.screenWidth/2 - (gp.tileSize*2/2);// ekranın tam ortadası
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x,y,gp.tileSize*2,gp.tileSize*2, null);
        
            // MENU ( seçebilmek için int tanımladık commandNum =0; )
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "YENI OYUN";
            x = getXforCenteredText(text);
            y += gp.tileSize*3.5; // iki kare aşağı
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">",x-gp.tileSize,y);
            }
            /*
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "ESKIYI YÜKLE";
            x = getXforCenteredText(text);
            y += gp.tileSize*1; // iki kare aşağı
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">",x-gp.tileSize,y);
            }//bu kodu aktif edersen "KAPAT" da if içinde commandNum == 2 olacak*/
        
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "KAPAT";
            x = getXforCenteredText(text);
            y += gp.tileSize*1; // iki kare aşağı
            g2.drawString(text, x, y);
            if(commandNum == 1){// yukardaki kodu aktif edersen burası commandnum ==2 olcak
                g2.drawString(">",x-gp.tileSize,y);
            }   
        }//if(titleScreenState ==0) sonu
        
        /*else if(titleScreenState == 1){
            
            //CLASS SELECTİON SCREEN
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            
            String text = "Selec your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);
            
            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum ==0){
                g2.drawString(">", x-gp.tileSize, y);
            }
            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;// 1 kare aşağıya 
            g2.drawString(text, x, y);
            if(commandNum ==1){
                g2.drawString(">", x-gp.tileSize, y);
            }
            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize*1;
            g2.drawString(text, x, y);
            if(commandNum ==2){
                g2.drawString(">", x-gp.tileSize, y);
            }
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum ==3){
                g2.drawString(">", x-gp.tileSize, y);
            }
        }*/
    }
    public void drawPauseScreen(){
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,60F));
        
        String text = "PAUSED";
        // tam ortada oluşması görünmesi için.
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        // ekranın ortasında belirsin
        g2.drawString(text,x,y);
    }// draw()'da çağrılır.
    public void drawDialogueScreen(){
        
        //WINDOW dialogue ayarları
        int x = gp.tileSize*2;
        int y = gp.tileSize /2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;
        
        for(String line : currentDialogue.split("\n") ){// bir alt satıra yazmamızı sağlıyor
            g2.drawString(line, x, y);
            y+=40; 
        }
        
        
    }
    public void drawOptionsScreen(){
        
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        
        //SUB WINDOW
        int frameX= gp.tileSize*4;
        int frameY= gp.tileSize;
        int frameWidth=gp.tileSize*8;
        int frameHeight=gp.tileSize*10;
        drawSubWindow(frameX,frameY,frameWidth, frameHeight);
        
        switch (subState) {
            case 0: options_top(frameX, frameY);break;
            case 1:options_control(frameX, frameY); break;
            case 2: options_endGameConfrimation(frameX,frameY);break;
            
        }
        
        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX,int frameY){
        int textX;
        int textY;
        
        //TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);
        
        //FULL SCREEN ON/OFF
        
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*1;
        //g2.drawString("Full Screen", textX,textY);
        //.....#35 video
        
        // MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum ==0){
            g2.drawString(">", textX-25, textY);
        }
        
        // SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum ==1){
            g2.drawString(">", textX-25, textY);
        }
        
        // CONTROL
        textY += gp.tileSize;
        g2.drawString("CONTROL", textX, textY);
        if(commandNum ==2){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 1;
                commandNum = 0;
            }
        }
        
        // END GAME
        textY += gp.tileSize;
        g2.drawString("END GAME", textX, textY);
        if(commandNum ==3){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 2; commandNum = 0;
            }
        }
        
        // BACK
        textY += gp.tileSize*2;
        g2.drawString("BACK", textX, textY);
        if(commandNum ==4){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }
        
        //FULL SCREEN CHECK BOX
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY = gp.tileSize*2 +24;
        //g2.setStroke(new BasicStroke(3));
        //g2.drawRect(textX, textY, 24,24);
        
        
        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); // 120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        
        //SE VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
    }
    public void options_control(int frameX,int frameY){
        
        int textX;
        int textY;
        
        //TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        textX =frameX+(int)(gp.tileSize*0.5);
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);textY +=gp.tileSize;
        g2.drawString("Confrim", textX, textY);textY +=gp.tileSize;
        g2.drawString("Ip firlat", textX, textY);textY +=gp.tileSize;
        g2.drawString("Pause", textX, textY);textY +=gp.tileSize;
        g2.drawString("Time", textX, textY);textY +=gp.tileSize;
        g2.drawString("Options", textX, textY);textY +=gp.tileSize;
        
        textX = frameX +gp.tileSize*5;
        textY = frameY + gp.tileSize*2;
        g2.drawString("<^>", textX, textY); textY +=gp.tileSize;
        g2.drawString("Enter", textX, textY); textY +=gp.tileSize;
        g2.drawString("Space", textX, textY); textY +=gp.tileSize;
        g2.drawString("P", textX, textY); textY +=gp.tileSize;
        g2.drawString("T", textX, textY); textY +=gp.tileSize;
        g2.drawString("Esc", textX, textY); textY +=gp.tileSize;
        
        //GERİ TuŞU
        textX = frameX + gp.tileSize;
        textY = frameY +gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if(commandNum ==0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum =2;
            }
        }
    }
    public void options_endGameConfrimation(int frameX,int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;
        
        g2.setFont(g2.getFont().deriveFont(24F));
        currentDialogue = "Quit the game and \nreturn to the title \nscreen ?";
        
        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX,textY);
            textY +=40;
        }
        
        //YES
        
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                gp.stopMusic();
                gp.gameState = gp.titleState;
            }
        }
        
        //NO
        
         text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 3;
            }
        }
        
    }
    public void drawSubWindow(int x, int y, int width, int height){// konuşma ekranı
        
        Color c = new Color(0,0,0,200); //220== opaklık/transparancy(255=soild, 0= transparent)
        g2.setColor(c);
        int arcWidth = 35;
        int arcHeight = 35;
        g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        
        c = new Color(255,255,255);// beyaz
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, arcWidth-10, arcHeight-10);
    }
    public int getXforCenteredText(String text){
        // burda ise x ekseninde yazımızı ortalıyoruz. (word'te başlık atmak gibi)
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }// drawPauseScreen() de çağrılıyor
    
    
}
//gamePanelde nesne yaratıp, main/GamePanel/paintComponent() de çağrılır.
