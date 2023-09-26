package cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

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

//            case 'a':
//                cena.direcao+=30;
//                break;
//            case 'd':
//                cena.direcao-=30;
//                break;

        }
        switch (e.getKeyCode()){
            case 151:
                cena.angY +=2;
                System.out.println(cena.angY);
                break;
            case 149:
                cena.angY -=2;
                System.out.println(cena.angY);
                break;
            case 150:
                cena.angZ +=2;
                System.out.println(cena.angY);
                break;
            case 152:
                cena.angZ -=2;
                System.out.println(cena.angY);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
