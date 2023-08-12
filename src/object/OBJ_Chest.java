
package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Chest extends Entity{
    
    GamePanel gp;
    
    
    // constractor
    public OBJ_Chest(GamePanel gp){
        super(gp);
        
        name = "Chest";
        down1 = setup("/objects/chest",gp.tileSize,gp.tileSize);
        
        
    }// şimdi AssetSetter'da bunu ekle.. (sorce package/ main/AssetSetter.java)
}
