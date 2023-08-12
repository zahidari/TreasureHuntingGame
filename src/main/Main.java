//retro games
package main;

import javax.swing.JFrame;


public class Main {

   
    public static void main(String[] args) {
        
        //ekran yarattık.
        JFrame window = new JFrame();
        
        //pencereyi kapatınca program kapanır.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setResizable(false);// pencere boyutu değiştirilemez
        window.setTitle("m.z.ari 2D oyun");
        
        
        //buraya game Panel gelir. bir layer(katman) dir.
        GamePanel gamePanel = new GamePanel();
        
        window.add(gamePanel); // windowun üstüne bir layer gelir. (pencere)
        //window'un alt klassı olan GamePanel
        //ekran boyutlarına göre window yeniden boyutlandırılır.
        window.pack();
        // game panel sonu
        
        
        window.setLocationRelativeTo(null);// pencere ekranın ortasında belirir.
        window.setVisible(true);// pencereyi görünür yapar
        
        gamePanel.setupGame(); // tread öncesi yazılması lazım. objectleri haritada göstermek için (örnek: Key)
        gamePanel.startGameThread();// bu sayede loop'a girer.
        
        
        
        
    }
    
}
