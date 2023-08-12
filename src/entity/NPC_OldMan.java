
package entity;
import java.util.Random;
import main.GamePanel;

public class NPC_OldMan extends Entity{
    
    public NPC_OldMan(GamePanel gp){
        super(gp);
        
        direction ="down";
        speed = 1;
        
        getImage();
        setDialogue();
    }
    public void getImage(){//draw image için player dan copy yap
        
        up1 = setup("/npc/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/npc/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/npc/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/npc/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/npc/oldman_right_2",gp.tileSize,gp.tileSize); 
    }// getPlayerImage() sonu
    
    @Override
    public void setAction(){
        
        actionLockCounter++;
        
        if(actionLockCounter == 120){
            
            Random random = new Random();
            int i = random.nextInt(100)+1;//1-100 arası
        
            if( i <= 25){
                direction = "up";
            }
            else if(i>25 && i <= 50){
                direction = "down";
            }
            else if(i>50 && i<=75){
                direction = "left";
            }
            else if(i>75 && i<= 100){
                direction = "right";
            }
            actionLockCounter =0;
        }
    }//setAction() sonu
    
    
    public void setDialogue(){
        
        dialogues[0] = "Hello, Zahid.";
        dialogues[1] = "Demek ki bu adaya hazine\n aramaya gelmişsin?";
        dialogues[2] = "Eskiden iyi bir büyücüydüm\n fakat şimdi..\n Artık bir macera için fazla \nyaşlıyım.";
        dialogues[3] = "Bol şans.";
    }
    
    public void speak(){// normalde direk entityden miras alıyor ama ilerleyen
        // durumlarda özel bir şeye sahip olduktan sonra başka birşeyler söyletmek için
        // bu metotu burda oluşturduk
        
       super.speak();
    }
}
