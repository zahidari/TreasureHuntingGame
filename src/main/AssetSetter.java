
package main;

// haritada objelerin yerini yerleştirir.

import entity.NPC_OldMan;
import object.OBJ_Boots_Water;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_rope;

public class AssetSetter {// varlık setter
    
    GamePanel gp;
    
    public AssetSetter (GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        
        //hangi haritada belirecek ? mapNum = arraydaki harita indexi
        int mapNum = 0;//eşyamız hangi haritada gözüksün
        //world map te gözükecekler
        
        gp.obj[mapNum][0] = new OBJ_Key(gp);
        gp.obj[mapNum][0].worldX = 23*gp.tileSize;
        gp.obj[mapNum][0].worldY = 10*gp.tileSize;
        
        gp.obj[mapNum][1] = new OBJ_Key(gp);
        gp.obj[mapNum][1].worldX = 23*gp.tileSize;
        gp.obj[mapNum][1].worldY = 40*gp.tileSize;
        
        gp.obj[mapNum][2] = new OBJ_Key(gp);
        gp.obj[mapNum][2].worldX = 37*gp.tileSize;
        gp.obj[mapNum][2].worldY = 7*gp.tileSize;
        
        gp.obj[mapNum][3] = new OBJ_Door(gp);
        gp.obj[mapNum][3].worldX = 12*gp.tileSize;
        gp.obj[mapNum][3].worldY = 12*gp.tileSize;
        
        gp.obj[mapNum][4] = new OBJ_Door(gp);
        gp.obj[mapNum][4].worldX = 10*gp.tileSize;
        gp.obj[mapNum][4].worldY = 28*gp.tileSize;
        
        gp.obj[mapNum][5] = new OBJ_Door(gp);
        gp.obj[mapNum][5].worldX = 13*gp.tileSize;
        gp.obj[mapNum][5].worldY = 23*gp.tileSize;
        
        gp.obj[mapNum][6] = new OBJ_Chest(gp);
        gp.obj[mapNum][6].worldX = 12*gp.tileSize;
        gp.obj[mapNum][6].worldY = 8*gp.tileSize;
        
        gp.obj[mapNum][7] = new OBJ_Boots_Water(gp);
        gp.obj[mapNum][7].worldX = 26*gp.tileSize;
        gp.obj[mapNum][7].worldY = 16*gp.tileSize;
        
        gp.obj[mapNum][8] = new OBJ_rope(gp);
        gp.obj[mapNum][8].worldX = 35*gp.tileSize;
        gp.obj[mapNum][8].worldY = 40*gp.tileSize;
        
        gp.obj[mapNum][9] = new OBJ_Door(gp);
        gp.obj[mapNum][9].worldX = 12*gp.tileSize;
        gp.obj[mapNum][9].worldY = 34*gp.tileSize;
        
        //hangi haritada belirecek ? mapNum = arraydaki harita indexi
        mapNum = 1;
        //interior01 haritasında gözükecekler
        
        gp.obj[mapNum][10] = new OBJ_Key(gp);
        gp.obj[mapNum][10].worldX = 7*gp.tileSize;
        gp.obj[mapNum][10].worldY = 3*gp.tileSize;
        
        gp.obj[mapNum][11] = new OBJ_Key(gp);
        gp.obj[mapNum][11].worldX = 30*gp.tileSize;
        gp.obj[mapNum][11].worldY = 23*gp.tileSize;
        
        gp.obj[mapNum][12] = new OBJ_Door(gp);
        gp.obj[mapNum][12].worldX = 26*gp.tileSize;
        gp.obj[mapNum][12].worldY = 41*gp.tileSize;
        
    }
    public void setNPC(){
        
        int mapNum = 0; //eşyamız hangi haritada gözüksün
        
        gp.npc[mapNum][0] = new NPC_OldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize*21;
        gp.npc[mapNum][0].worldY = gp.tileSize*21;
        //GamePanel'de setupGame() ve draw()' a geçiniz.
        mapNum = 1;
        
        gp.npc[mapNum][1] = new NPC_OldMan(gp);
        gp.npc[mapNum][1].worldX = gp.tileSize*8;
        gp.npc[mapNum][1].worldY = gp.tileSize*7;
        
    }
}  
