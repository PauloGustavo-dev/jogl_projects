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

            case 'a':
                cena.direcao+=10;
                System.out.println(cena.direcao);
                break;
            case 'd':
                cena.direcao-=10;
                System.out.println(cena.direcao);
                break;
            case 'p':
                cena.start = !cena.start;
                break;

        }
        switch (e.getKeyCode()){
            case 151://seta esquerda
                cena.direcao-=10;
                System.out.println(cena.direcao);
                break;
            case 149://seta direita
                cena.direcao+=10;
                System.out.println(cena.direcao);
                break;
            case 150://seta cima
                break;
            case 152://seta baixo
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
