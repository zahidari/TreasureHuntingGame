package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_rope extends Entity{
    
    //constractor
    public OBJ_rope(GamePanel gp){
       super(gp);
        
        name = "Rope";
        down1 = setup("/rope/rope_toplu",gp.tileSize,gp.tileSize);
        
    }
}
