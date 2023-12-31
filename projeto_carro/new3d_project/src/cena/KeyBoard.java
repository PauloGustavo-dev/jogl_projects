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
                if (cena.direcao >= 500) {
                    cena.direcao =500;
                } else{
                    cena.direcao+=500;
                }
                cena.inclinacaoCurva =-10;
                break;
            case 'd':
                if (cena.direcao <=-500) {
                    cena.direcao =-500;
                } else{
                    cena.direcao-=500;
                }
                cena.inclinacaoCurva =10;
                break;
            case 'p':
                cena.start = !cena.start;
                break;

        }

        switch (e.getKeyCode()){
            case 149://seta direita
                if (cena.direcao <= -500) {
                    cena.direcao =-500;
                } else{
                    cena.direcao-=500;
                }
                cena.inclinacaoCurva =10;
                break;
            case 151://seta esquerda
                if (cena.direcao >= 500) {
                    cena.direcao =500;
                } else{
                    cena.direcao+=500;
                }
                cena.inclinacaoCurva =-10;
                break;
            case 150://seta cima
                cena.inclinacaoCarro+=1;
                System.out.println(cena.inclinacaoCarro);
                break;
            case 152://seta baixo
                cena.inclinacaoCarro-=1;
                System.out.println(cena.inclinacaoCarro);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        System.out.println("Key released: " + e.getKeyCode());
        if (e.getKeyChar() == 'a') { // Tecla a
            cena.inclinacaoCurva =0;
        } else if (e.getKeyChar() == 'd') { // Tecla d
            cena.inclinacaoCurva =0;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) { // Tecla esquerda
            cena.inclinacaoCurva =0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // Tecla direita
            cena.inclinacaoCurva =0;
        }
    }

}
