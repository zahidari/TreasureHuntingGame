
package main;

import entity.Entity;



public class CollisionChecker {
    GamePanel gp;
    
    
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }
    
    // Entity çünkü sadece doku değil aynı zamanda canavarlara da değdini kontrol etmemiz için.
    public void checkTile(Entity entity){
        
        //solid area ya göre check yapmak iazım. worldX olmaz yani
        int entityLeftWorldX  = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY   = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY= entity.worldY + entity.solidArea.y + entity.solidArea.height;
        
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol= entityRightWorldX /gp.tileSize;
        int entityTopRow  = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;
        
        int tileNum1, tileNum2;// sadece iki tarafını kontrol edeceğiz. yukarı giderken iki üst köşeleri ağaıya giderken alt iki köşeleri.
        
        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum [gp.currentMap][entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){// birşeye çarpıyor demek
                    entity.collisionON = true;
                }
                break;
            case "down":
                    entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum [gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){// birşeye çarpıyor demek
                    entity.collisionON = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum [gp.currentMap][entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){// birşeye çarpıyor demek
                    entity.collisionON = true;
                }
                break;
            case "right":
                 entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum [gp.currentMap][entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){// birşeye çarpıyor demek
                    entity.collisionON = true;
                }
                break;
        } 
    }// checkTitle sonu
    
    public int checkObject(Entity entity, boolean player){//entity == player mı?
        
        int index = 999;
        
        for( int i =0; i< gp.obj[1].length; i++){// gp.obj[1] harita eklenince arttır. 1->2 olcak
            if(gp.obj[gp.currentMap][i] != null){
                
                // get entity 's (varliğin) solid area position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                // ger the object's solid area position
                gp.obj[gp.currentMap][i].solidArea.x += gp.obj[gp.currentMap][i].worldX;
                gp.obj[gp.currentMap][i].solidArea.y += gp.obj[gp.currentMap][i].worldY;
                
                switch(entity.direction){
                    case "up":      entity.solidArea.y -= entity.speed;
                                    if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                                        if(gp.obj[gp.currentMap][i].collision == true){
                                            entity.collisionON = true;
                                        }
                                        if(player == true){// non player can't pick up the key or objects
                                           index = i;
                                        }
                                    }
                        break;
                    case"down":     entity.solidArea.y += entity.speed;
                                    if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                                         if(gp.obj[gp.currentMap][i].collision == true){
                                            entity.collisionON = true;
                                        }
                                        if(player == true){// non player can't pick up the key or objects
                                           index = i;
                                        }
                                    }
                        break;
                    case "left":    entity.solidArea.x -= entity.speed;
                                    if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                                         if(gp.obj[gp.currentMap][i].collision == true){
                                            entity.collisionON = true;
                                        }
                                        if(player == true){// non player can't pick up the key or objects
                                           index = i;
                                        }
                                    }
                        break;
                    case"right":    entity.solidArea.x += entity.speed;
                                    if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                                         if(gp.obj[gp.currentMap][i].collision == true){
                                            entity.collisionON = true;
                                        }
                                        if(player == true){// non player can't pick up the key or objects
                                           index = i;
                                        }
                                    }
                        break;
                            
                }
                // hepsini sıfırlayalım ki sürekli artmasın
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }// checkObejct sonu
    
    //NPC OR MONSTER COLLISON
    public int checkEntity(Entity entity, Entity[][] target){// player (npc'ye)entity'ye çarpıyor mu ?
        
        int index = 999;
        
        for( int i =0; i< target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                
                // get entity 's (varliğin) solid area position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                // ger the object's solid area position
                target[gp.currentMap][i].solidArea.x += target[gp.currentMap][i].worldX;
                target[gp.currentMap][i].solidArea.y += target[gp.currentMap][i].worldY;
                
                switch(entity.direction){
                    case "up":      entity.solidArea.y -= entity.speed;
                                    if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                                        entity.collisionON = true;
                                        index = i;
                                    }
                        break;
                    case"down":     entity.solidArea.y += entity.speed;
                                    if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                                        entity.collisionON = true;
                                        index = i;
                                    }
                                    
                        break;
                    case "left":    entity.solidArea.x -= entity.speed;
                                    if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                                        entity.collisionON = true;
                                        index = i;
                                    }
                        break;
                    case"right":    entity.solidArea.x += entity.speed;
                                    if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                                        entity.collisionON = true;
                                        index = i;
                                    }
                        break;
                            
                }
                // hepsini sıfırlayalım ki sürekli artmasın
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public void checkPlayer(Entity entity){//NPC(entity) player'e çarpıyor mu?
        
                
                // get entity 's (varliğin) solid area position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                // ger the object's solid area position
                gp.player.solidArea.x += gp.player.worldX;
                gp.player.solidArea.y += gp.player.worldY;
                
                switch(entity.direction){
                    case "up":      entity.solidArea.y -= entity.speed;
                                    if(entity.solidArea.intersects(gp.player.solidArea)){
                                        entity.collisionON = true;
                                        
                                    }
                        break;
                    case"down":     entity.solidArea.y += entity.speed;
                                    if(entity.solidArea.intersects(gp.player.solidArea)){
                                        entity.collisionON = true;
                                        
                                    }
                                    
                        break;
                    case "left":    entity.solidArea.x -= entity.speed;
                                    if(entity.solidArea.intersects(gp.player.solidArea)){
                                        entity.collisionON = true;
                                        
                                    }
                        break;
                    case"right":    entity.solidArea.x += entity.speed;
                                    if(entity.solidArea.intersects(gp.player.solidArea)){
                                        entity.collisionON = true;
                                        
                                    }
                        break;
                            
                }
                // hepsini sıfırlayalım ki sürekli artmasın
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.player.solidArea.x = gp.player.solidAreaDefaultX;
                gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
    
    
}
