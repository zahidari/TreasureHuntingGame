
package object;

import entity.Entity;
import main.GamePanel;



/**
 *
 * @author zagu
 */
public class OBJ_Boots_Water extends Entity {
    
    public OBJ_Boots_Water(GamePanel gp){
        super(gp);
        
        name = "Boots";
        down1 = setup("/objects/boots_water",gp.tileSize,gp.tileSize);
        
        
    }// şimdi AssetSetter'da bunu ekle.. (sorce package/ main/AssetSetter.java)

}
