package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {
    
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed,
            enterPressed, spacePressed;
    boolean showDebugText = false;
    boolean volumeON = true; // V ye basınca müziğin sesini kapatıp açar. 
    
    
    public KeyHandler(GamePanel gp){
        this.gp = gp;
        
    }
    
    
    
    
    
    // 8: backspace, 9: tab, 10:enter, 12:clear, 16:shift, 
    //17:ctrl, 18:alt, 65:A.... ascii 
    @Override
    public void keyPressed(KeyEvent e) {//
        
        
        int code = e.getKeyCode();
        
        //TITLE STATE
        titleState(code);
        
        //PLAY STATE'te geçen seçimler
        if(gp.gameState ==gp.playState){
            
           
            // sonradn eklenen ses açma kapama
            if(code == KeyEvent.VK_V){
                if(volumeON == true){
                    gp.stopMusic();
                    volumeON= false;
                }else{
                    volumeON = true;
                    gp.playMusic(0);
                }
            }
            
            // hareket tuşları
            if(code == KeyEvent.VK_UP){
                upPressed = true;
            }
            if(code == KeyEvent.VK_DOWN){
                downPressed = true;
            }
            if(code == KeyEvent.VK_LEFT){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }
            // pause ayarları
            if(code == KeyEvent.VK_P){// oyunu pause yapmak için
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.optionsState;
            }
        
        
            //debug (çizim için geçen zmanı ölçüyor.) oyun optimizasyonu için ön bilgi
            if(code == KeyEvent.VK_T){
                if(showDebugText == false){
                    showDebugText  = true;
                }
                else if(showDebugText == true){
                    showDebugText = false;
                }
            }
            if(code == KeyEvent.VK_R){
                switch(gp.currentMap){
                    case 0: gp.tileM.loadMap("/maps/worldV3.txt",0);System.out.println("load current map num: "+gp.currentMap);break;
                    case 1: gp.tileM.loadMap("/maps/interior01.txt", 1);System.out.println("load current map num: "+gp.currentMap);break;
                }
            }
        }//PLAY STATE sonu
        
        //PAUSE STATE 
        else if(gp.gameState ==gp.pauseState){
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }//PAUSE STATE sonu
       
        //DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }//DIALOGUE STATE sonu
        else if(gp.gameState == gp.optionsState){
                optionsState(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    public void titleState(int code) {
        
        if(gp.gameState ==gp.titleState){
            
            if(gp.ui.titleScreenState == 0){
                
                
                if(code == KeyEvent.VK_UP){
                    gp.playSE(9);
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum <0){
                        gp.ui.commandNum = 1;// "oyun yükle" kodunu açarsan burası 2 olacak
                    } 
                }
            
                if(code == KeyEvent.VK_DOWN){
                    gp.playSE(9);
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum >1){// burasını "oyun yükle" kısmını yaratırtsan "2" yap
                        gp.ui.commandNum = 0;
                        
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    gp.playSE(9);
                    if(gp.ui.commandNum == 0){//"oyun yükle" kısmını yaratırtsan
                        //gp.ui.titleScreenState = 1;// aynı şekilde aktif edilecek
                        // burdan sonrası yok. normalde titleScreen == 1 de zaten var
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                        
                    }
                    //"oyun yukle" kodu açarsan burasınıda açacaksın
                    /*if(gp.ui.commandNum == 1){
                        //Sonra yazılacak
                    }*/
                    if(gp.ui.commandNum == 1){// oyun yukle kısmını açarsan burası 2 olacak
                        gp.playSE(9);
                        System.exit(0);
                    }
                }
            } 
        /*else  if(gp.ui.titleScreenState == 1){// MENU deki 2. ekran
                
                
                if(code == KeyEvent.VK_UP){
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum <0){
                        gp.ui.commandNum = 3;
                    } 
                }
            
                if(code == KeyEvent.VK_DOWN){
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum >3){
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        System.out.println("Do some fighter specific stuff!");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 1){
                        System.out.println("Do some thief specific stuff!");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 2){
                        System.out.println("Do some sorcerer specific stuff!");
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 3){
                        gp.ui.titleScreenState =0;
                    }
                }
            }*/
        }
    }
    public void optionsState(int code){
        
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        
        
        
        int maxCommandNum =0;
        switch(gp.ui.subState){
            case 0: maxCommandNum = 4; break;
            case 2: maxCommandNum = 1; break;// ESC'de yes no için
        }
        
        
        
        
        if(code == KeyEvent.VK_UP){
            gp.ui.commandNum --;
            gp.playSE(9);
            if(gp.ui.commandNum <0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_DOWN){
            gp.ui.commandNum ++;
            gp.playSE(9);
            if(gp.ui.commandNum >maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_LEFT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum==0&& gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum==1&& gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                    
                    gp.playSE(9);
                }
            }
        }
        if(code == KeyEvent.VK_RIGHT){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum==0&& gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum==1&& gp.se.volumeScale < 5){
                    gp.se.volumeScale++;
                    
                    gp.playSE(9);
                }
            }
        }
        
    }
    
    
  
}
