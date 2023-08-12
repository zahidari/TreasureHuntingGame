
package object;

import entity.Entity;
import main.GamePanel;

/**
 *
 * @author 
 */
public class OBJ_Door extends Entity {
    
    
    
    //Constractor
    public OBJ_Door(GamePanel gp){
        super(gp);
        
        name = "Door";
        down1 = setup("/objects/door",gp.tileSize,gp.tileSize);
        collision = true;
        
        // kapı normalden küçük bir görünmez rectangle oluşturur ki karakter hafif arkasındaymış gibi dursun diye
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width =  48;
        solidArea.height =  32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}// şimdi AssetSetter'da bunu ekle.. (sorce package/ main/AssetSetter.java)
