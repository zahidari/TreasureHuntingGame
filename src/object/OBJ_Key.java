package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Key extends Entity{
    
    //constractor
    public OBJ_Key(GamePanel gp){
        super(gp);
        
        name = "Key";
        down1 = setup("/objects/key",gp.tileSize,gp.tileSize);
        
        
    }
}// şimdi AssetSetter'da bunu ekle.. (sorce package/ main/AssetSetter.java)
