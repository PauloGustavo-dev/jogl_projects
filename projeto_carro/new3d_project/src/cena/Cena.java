package cena;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;

    public  boolean start = false;

    public final float faixa1=500, faixa2=0, faixa3=-500;
    public float angY=-90, angZ =-20;
    public float inclinacao=0;
    public float direcao=0;

    public List<Float> listaPosicionamentoY = new ArrayList<>();
    public boolean cenarioCriado= false;

//    public boolean curvaEsquerda = false, curvaDireita=false;
    public float aproximacaoObstaculo =0, posicaoY=0, faixaAvenida=faixa2;

    public float size=50;

    public int mode;  
    
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena        
        GL2 gl = drawable.getGL().getGL2();        
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1000;
        xMax = yMax = zMax = 1000;


        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }
    public void play() {
        if (start) {
            aproximacaoObstaculo -= 10;
            if (direcao >= 500) {
                direcao =500;
            } else if (direcao <= -500){
                direcao = -500;
            }
        }
    }
    public List<Float> aleatorizaObstaculos(){
        Random random = new Random();
        int faixaAleatoria;

        // Adicione elementos à lista

        for (int i = 0; i < 30; i++) {
            faixaAleatoria = random.nextInt(3) + 1;
            listaPosicionamentoY.add((float)(faixaAleatoria));
        }
        return listaPosicionamentoY;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLUT glut = new GLUT();

        gl.glClearColor(1, 1, 1, 1);        

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);       
        gl.glLoadIdentity(); //ler a matriz identidade

        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);

        String m = mode == GL2.GL_LINE ? "LINE" : "FILL";


        if (listaPosicionamentoY.isEmpty()) {
            listaPosicionamentoY = aleatorizaObstaculos();
        }
        if(!cenarioCriado) {
            float distanciaObjeto = 0;
            for (float faixa : listaPosicionamentoY) {
                distanciaObjeto += 600;
                if (faixa == 1f) {
                    faixa = faixa1;
                } else if (faixa == 2f) {
                    faixa = faixa2;
                } else if (faixa == 3f) {
                    faixa = faixa3;
                }
                obstaculo(gl, glut, distanciaObjeto, faixa);
            }
        }
        carro(gl,glut);
        estrada(gl,glut);

        play();

        gl.glFlush();      
    }
    public void obstaculo(GL2 gl, GLUT glut,float posicaoinicial,float faixaAvenida){
        gl.glColor3f(0,0,0f); //cor do objeto
        gl.glPushMatrix();
        gl.glTranslatef(faixaAvenida, posicaoinicial,0);
        gl.glTranslatef(0, aproximacaoObstaculo,0);
        glut.glutSolidCube(200);
        gl.glPopMatrix();
    }
    public void carro(GL2 gl, GLUT glut){
        portaCarro(gl,glut);
        traseiraCarro(gl,glut);
        capoCarro(gl,glut);
        cabineCarro(gl,glut);
        //pneus
        pneuDianteiroEsquerda(gl,glut);
        pneuDianteiroDireita(gl,glut);
        pneuTraseiroEsquerda(gl,glut);
        pneuTraseiroDireita(gl,glut);
    }
    public void estrada(GL2 gl,GLUT glut){
        gl.glColor3f(0.5f,0.5f,0.5f); //cor do objeto
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, -1000);
        glut.glutSolidCube(1500);
        gl.glPopMatrix();
        //-90 angulo
    }
    public void pneuTraseiroEsquerda(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angY, 0, 1, 0);
        gl.glRotated(angZ, 0, 0, 1);
        gl.glRotated(inclinacao, 0, 1, 0);
        gl.glTranslatef(75, -25, 35);
        gl.glTranslatef(0, -300, direcao);
        glut.glutSolidTorus(10, 15, 1000, 50);
        gl.glPopMatrix();
    }
    public void pneuTraseiroDireita(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angY, 0, 1, 0);
        gl.glRotated(angZ, 0, 0, 1);
        gl.glRotated(inclinacao, 0, 1, 0);
        gl.glTranslatef(75, -25, -35);
        gl.glTranslatef(0, -300, direcao);
        glut.glutSolidTorus(10, 15, 1000, 50);
        gl.glPopMatrix();
    }
    public void pneuDianteiroEsquerda(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angY, 0, 1, 0);
        gl.glRotated(angZ, 0, 0, 1);
        gl.glRotated(inclinacao, 0, 1, 0);
        gl.glTranslatef(-75, -25, 35);
        gl.glTranslatef(0, -300, direcao);
        glut.glutSolidTorus(10, 15, 1000, 50);
        gl.glPopMatrix();
    }
    public void pneuDianteiroDireita(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angY, 0, 1, 0);
        gl.glRotated(angZ, 0, 0, 1);
        gl.glRotated(inclinacao, 0, 1, 0);
        gl.glTranslatef(-75, -25, -35);
        gl.glTranslatef(0, -300, direcao);
        glut.glutSolidTorus(10, 15, 1000, 50);
        gl.glPopMatrix();
    }
    public void portaCarro(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0.8f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angY, 0, 1, 0);
        gl.glRotated(angZ, 0, 0, 1);
        gl.glRotated(inclinacao, 0, 1, 0);
        gl.glTranslatef(0, -300, direcao);
        glut.glutSolidCube(size);
        gl.glPopMatrix();
    }
    public void traseiraCarro(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0.8f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angY, 0, 1, 0);
        gl.glRotated(angZ, 0, 0, 1);
        gl.glRotated(inclinacao, 0, 1, 0);
        gl.glTranslatef(50, 0, 0);
        gl.glTranslatef(0, -300, direcao);
        glut.glutSolidCube(size);
        gl.glPopMatrix();
    }
    public void capoCarro(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0.8f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angY, 0, 1, 0);
        gl.glRotated(angZ, 0, 0, 1);
        gl.glRotated(inclinacao, 0, 1, 0);
        gl.glTranslatef(-50, 0, 0);
        gl.glTranslatef(0, -300, direcao);
        glut.glutSolidCube(size);
        gl.glPopMatrix();
    }
    public void cabineCarro(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,1f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angY, 0, 1, 0);
        gl.glRotated(angZ, 0, 0, 1);
        gl.glRotated(inclinacao, 0, 1, 0);
        gl.glTranslatef(50, 50, 0);
        gl.glTranslatef(0, -300, direcao);
        glut.glutSolidCube(size);
        gl.glPopMatrix();
    }
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();  
                
        //ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); //ler a matriz identidade

        //projecao ortogonal sem a correcao do aspecto
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
        
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //ler a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}         
}
