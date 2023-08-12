
package main;

public class EventHandler {
    
    
    
    GamePanel gp;
    EventRect eventRect[][][];
    
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    
    
    
    public EventHandler(GamePanel gp){
        this.gp = gp;  
        
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        
        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col<gp.maxWorldCol && row< gp.maxWorldRow){
            
            // tile ortasına minnacık bir kare koyduk, dokunmak için.
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row] .x = 23;
            eventRect[map][col][row] .y = 23;
            eventRect[map][col][row] .width = 2;
            eventRect[map][col][row] .height = 2 ;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
        
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
                
                if(row == gp.maxWorldRow){
                    row = 0;
                    map ++;
                }
            }
        }
     }
    
    public void checkEvent(){ // 
        
        
        
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }
        
        if(canTouchEvent == true){
            if(hit(0,27,16, "right")== true){ damagePit(gp.dialogueState);}
            else if(hit(0,10, 39, "up")== true){teleport(1,7, 9);}
            else if(hit(1,7, 9, "down")== true){teleport(0,10, 39);}
            else if(hit(0,12, 10, "up")== true){teleport(1,17, 35);}
            else if(hit(1,26, 45, "down")== true){teleport(0,13, 9);}
            
        }
        
        
        
    }
    
    
    public boolean hit(int map, int col, int row, String reqDirection){
        
        boolean hit = false;
        
        if(map == gp.currentMap){
        
            // getting player's current solidArea positions
        gp.player.solidArea.x = gp.player.worldX + gp.player. solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player. solidArea.y;
        //event rects solid area positions too.
        eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
        eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
        
        if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false){
            if(gp.player.direction.contentEquals(reqDirection)||reqDirection.contentEquals("any")){
                hit = true;
                
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
            
        }
        
        // after checking the collision reset the solidArea x and y
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
        eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        
        }
        
        
        return hit;
    }
    
    
    
    
    public void teleport(int map,int col, int row){// harita geçişi
        
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        gp.playSE(5);// 12 yapman gerekebilir. sound's a bak, "Stairs.wav" olacak
    }

    public void damagePit(int gameState) {
        
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit";
        //gp.player.life -=1;
    }
    
    
    
    
    
    
    
    
}
