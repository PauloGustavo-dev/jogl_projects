package cena;


import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;
import java.awt.Font;

public class Cena implements GLEventListener{    
    private float xMin, xMax, yMin, yMax, zMin, zMax;

    public float ang;

    public float size;

    public int mode;  
    
    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena        
        GL2 gl = drawable.getGL().getGL2();        
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1080;
        xMax = yMax = zMax = 1920;
        
        reset();

        //Habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }
    
    public void reset(){
        ang = 0;
        size = 50;
        mode = GL2.GL_FILL;
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

        carro(gl,glut);

        gl.glFlush();      
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
    public void pneuTraseiroEsquerda(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(ang, 0, 1, 0);
        gl.glTranslatef(75, -25, 35);
        glut.glutSolidTorus(10, 20, 1000, 50);
        gl.glPopMatrix();
    }
    public void pneuTraseiroDireita(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(ang, 0, 1, 0);
        gl.glTranslatef(75, -25, -35);
        glut.glutSolidTorus(10, 20, 1000, 50);
        gl.glPopMatrix();
    }
    public void pneuDianteiroEsquerda(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(ang, 0, 1, 0);
        gl.glTranslatef(-75, -25, 35);
        glut.glutSolidTorus(10, 20, 1000, 50);
        gl.glPopMatrix();
    }
    public void pneuDianteiroDireita(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(ang, 0, 1, 0);
        gl.glTranslatef(-75, -25, -35);
        glut.glutSolidTorus(10, 20, 1000, 50);
        gl.glPopMatrix();
    }
    public void portaCarro(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0.8f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(ang, 0, 1, 0);
        glut.glutSolidCube(size);
        gl.glPopMatrix();
    }
    public void traseiraCarro(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0.8f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(ang, 0, 1, 0);
        gl.glTranslatef(50, 0, 0);
        glut.glutSolidCube(size);
        gl.glPopMatrix();
    }
    public void capoCarro(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0.8f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(ang, 0, 1, 0);
        gl.glTranslatef(-50, 0, 0);
        glut.glutSolidCube(size);
        gl.glPopMatrix();
    }
    public void cabineCarro(GL2 gl, GLUT glut){
        gl.glColor3f(0,0,0.8f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(ang, 0, 1, 0);
        gl.glTranslatef(50, 50, 0);
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
