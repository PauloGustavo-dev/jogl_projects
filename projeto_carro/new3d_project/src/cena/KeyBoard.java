package cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;

public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        System.out.println("Key pressed: " + e.getKeyCode());
        System.out.println("Key pressed: " + e.getKeyChar());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        switch (e.getKeyChar()) {

            case 'r':
                System.out.println("chamou Rotacao");
                cena.ang += 45.0f;
                break;

        }
        switch (e.getKeyCode()){
            case 151:
                cena.ang+=2;
                break;
            case 149:
                cena.ang-=2;
                break;
            case 150:
                System.out.println("cima");
                break;
            case 152:
                System.out.println("baixo");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
