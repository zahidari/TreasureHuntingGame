
package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Boots_White extends Entity {
    
    public OBJ_Boots_White(GamePanel gp){
        super(gp);
        
        name = "Boots";
        down1 = setup("/objects/boots_white",gp.tileSize,gp.tileSize);
        
    }// şimdi AssetSetter'da bunu ekle.. (sorce package/ main/AssetSetter.java)
}
