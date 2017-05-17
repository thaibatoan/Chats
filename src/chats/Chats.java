package chats;

public class Chats {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(() -> {
            new Signin().setVisible(true);
        });
    }
    
}