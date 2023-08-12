
package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author zagu
 */
// özel fonksiyon veya class tanımlamamız gerekiyorsa( daha kısa işlemler için)
// burada tanımlanır ve başka yerleder çağrılır.
public class UtilityTool {
    
    
    //çizmleri loop dışında çizim yapıp loopta çağırarak optimize çalıştırır.
    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        
        BufferedImage scaledImage = new BufferedImage(width,height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original , 0 , 0 , width, height, null);
        g2.dispose();// garbage collector tarafından toplanır.
        
        return scaledImage;
    }// TileManager class da  setup()'da çağrılır.ardından getTileİmage()'de
}
