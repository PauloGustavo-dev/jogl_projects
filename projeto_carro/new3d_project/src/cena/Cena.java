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

    public final float faixa1=-500, faixa2=0, faixa3=500;
    public float angY=-90, angZ =-20;
    public float inclinacao=0;
    public float direcao=0;

    public final int quantidadeObstaculos= 30;

    public int i = 0;
    public boolean colisao= false;

    public List<Float> listaFaixasObstaculos = new ArrayList<>();
    public List<Float> listaPosicionamentoY = new ArrayList<>();
    public List<Float> listaPosicionamentoQuadrilhamento = new ArrayList<>();
    public final float distanciaObjetos = 1000;

//    public boolean curvaEsquerda = false, curvaDireita=false;
    public float aproximacaoObstaculo =0, faixaAvenida=0, cor=0;

    public float size=50;

    public int mode;

    //gera e armazena em uma lista 0 posicionamento x de cada quadrado da faixa de chegada;
    public List<Float> posicaoXLinhaDeChegada(){
        for (int quadrado = 0; quadrado < 15; quadrado++) {
            float posicao = (-700+(quadrado*100));
            listaPosicionamentoQuadrilhamento.add(posicao);
        }
        return listaPosicionamentoQuadrilhamento;
    }
    public float converteListaFaixasParaPosicao(Float faixa){
        float posicaoFaixa=0;
        if (faixa == 1) {
            posicaoFaixa = faixa1;
        } else if(faixa == 2) {
            posicaoFaixa = faixa2;
        } else if (faixa == 3) {
            posicaoFaixa = faixa3;
        }
        return posicaoFaixa;
    }
    public void metodo(){
        if(i<quantidadeObstaculos){
            faixaAvenida= converteListaFaixasParaPosicao(listaFaixasObstaculos.get(i));

            if((listaPosicionamentoY.get(i) * -1) == aproximacaoObstaculo) {
                if(faixaAvenida == (direcao*-1)){
                    colisao=true;
                }
                i++;
            }
        }
    }
    
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

            metodo();

            if (colisao){
                start=false;
                colisao=false;
            }
        }
    }
    public List<Float> aleatorizaObstaculos(){

        Random random = new Random();
        int faixaAleatoria;

        for (int faixaAtualCriada = 1; faixaAtualCriada <= 30; faixaAtualCriada++) {
            faixaAleatoria = random.nextInt(3) + 1;

            if(faixaAtualCriada>2){
                float posicaoFaixaAnterior,posicaoSegundaFaixaAnterior;
                int faixaAnterior = faixaAtualCriada - 2;
                int segundaFaixaAnterior =faixaAtualCriada-3;
                posicaoFaixaAnterior=(listaFaixasObstaculos.get(faixaAnterior)) ;
                posicaoSegundaFaixaAnterior=(listaFaixasObstaculos.get(segundaFaixaAnterior));

                if (posicaoFaixaAnterior != (float)faixaAleatoria && posicaoSegundaFaixaAnterior!= (float)faixaAleatoria) {
                    listaFaixasObstaculos.add((float)(faixaAleatoria));
                    float posicionamento = (distanciaObjetos * faixaAtualCriada) + 100;
                    listaPosicionamentoY.add(posicionamento);
                }else {
                    faixaAtualCriada--;
                }
            }else {
                listaFaixasObstaculos.add((float)(faixaAleatoria));
                float posicionamento = (distanciaObjetos * faixaAtualCriada) + 100;
                listaPosicionamentoY.add(posicionamento);
            }

        }
        return listaFaixasObstaculos;
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


        if (listaFaixasObstaculos.isEmpty()) {
            listaFaixasObstaculos = aleatorizaObstaculos();
        }
        if(listaPosicionamentoQuadrilhamento.isEmpty()){

            listaPosicionamentoQuadrilhamento = posicaoXLinhaDeChegada();
        }
        //verificar codigo como reduzir codigo abaixo;

        float distanciaObjeto = 0;
        for (float faixa : listaFaixasObstaculos) {
            distanciaObjeto += distanciaObjetos;
            faixa=converteListaFaixasParaPosicao(faixa);
            obstaculo(gl, glut, distanciaObjeto, faixa);
        }
        distanciaObjeto += distanciaObjetos;

        //cria linha de chegada apos todos os obstaculos criados
        for (float x : listaPosicionamentoQuadrilhamento){
            quadradoLinhaDeChegada(gl,glut,x,distanciaObjeto,cor);
            if(cor==0){cor=1;}else {cor=0;}
            distanciaObjeto+=100;
            quadradoLinhaDeChegada(gl,glut,x,distanciaObjeto,cor);
            distanciaObjeto-=100;
            if(x == 700f){cor=0;}
        }
        carro(gl,glut);
        estrada(gl,glut);
        play();

        gl.glFlush();      
    }
    public void quadradoLinhaDeChegada(GL2 gl, GLUT glut,float posicaoX,float posicaoY,float cor){
        gl.glColor3f(cor,cor,cor); //cor do objeto
        gl.glPushMatrix();
        gl.glTranslatef(posicaoX, posicaoY,-100);
        gl.glTranslatef(0, aproximacaoObstaculo,0);
        glut.glutSolidCube(100);
        gl.glPopMatrix();
    }
    public void obstaculo(GL2 gl, GLUT glut,float posicaoinicial,float faixaAvenidaRandomizada){
        gl.glColor3f(0,0,0f); //cor do objeto
        gl.glPushMatrix();
        gl.glRotated(angZ,1,0,0);
        gl.glTranslatef(faixaAvenidaRandomizada, posicaoinicial,0);
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
