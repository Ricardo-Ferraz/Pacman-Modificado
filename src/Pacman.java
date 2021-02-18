import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Pacman implements GLEventListener, KeyListener {
    private boolean roda;
    private float anguloComida;
    private float angulo = 0.5f;
    private float anguloX;
    private float translacaoX, translacaoY,translacaoZ;
    private float escalaX=1, escalaY=1, escalaZ= 1;
    private boolean comida1= true, comida2 = true, comida3= true, comida4= true;
    private boolean obstaculo1= true, obstaculo2= true, obstaculo3= true, obstaculo4= true;
    private boolean[] pontos = new boolean[39];
    private NumberFormat aproximador = new DecimalFormat("0.0");
    private int auxComida1=0, auxComida2=0, auxComida3=0, auxComida4=0;
    private int contador=0;
    private GL2 gl;

    @Override
    public void init(GLAutoDrawable desenho) {
        gl = desenho.getGL().getGL2();
        gl.glShadeModel( GL2.GL_SMOOTH );
        gl.glClearColor( 0f, 0f, 0f, 0f );
        gl.glClearDepth( 1.0f );
        gl.glEnable( GL2.GL_DEPTH_TEST );
        gl.glDepthFunc( GL2.GL_LEQUAL );
        gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );

    }

    public Pacman(){
        for(int i=0; i < pontos.length; i++){
            pontos[i] = true;
        }
    }

    @Override
    public void dispose(GLAutoDrawable desenho) {

    }

    @Override
    public void display(GLAutoDrawable desenho) {
        double x, y; //Pontos auxiliares
        double raio = 0.2;
        double angulo = 0; //Angulo do circulo
        double somaDoAngulo = 2 * Math.PI / 50; //50 == Numero de pontos
        gl = desenho.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT |  GL2.GL_DEPTH_BUFFER_BIT ); //Limpar a imagem
        gl.glLoadIdentity();//Gera a matriz identidade

        //Plot das comidas
        if(comida1) {
            gl.glTranslatef(-1.4f, 0.9f, 0);
            gl.glRotatef(anguloX, 1f, 1f, 0f);
            comida();
            gl.glEnd();
            gl.glFlush();
            if((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("0,9")){
                comida1 = false;
                roda = true;
            }
        }
        
        gl.glLoadIdentity();//Gera a matriz identidade

        if(comida2) {
            gl.glTranslatef(1.4f, 0.9f, 0);
            gl.glRotatef(anguloX, 1f, 1f, 0f);
            comida();
            gl.glEnd();
            gl.glFlush();
            if((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("0,9")){
                comida2 = false;
                roda = true;
            }
        }

        gl.glLoadIdentity();//Gera a matriz identidade

        if(comida3) {
            gl.glTranslatef(1.4f, -1.2f, 0);
            gl.glRotatef(anguloX, 1f, 1f, 0f);
            comida();
            gl.glEnd();
            gl.glFlush();
            if((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("-1,2")){
                comida3 = false;
                roda = true;
            }
        }

        gl.glLoadIdentity();//Gera a matriz identidade

        if(comida4) {
            gl.glTranslatef(-1.4f, -1.2f, 0);
            gl.glRotatef(anguloX, 1f, 1f, 0f);
            comida();
            gl.glEnd();
            gl.glFlush();
            if((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("-1,2")){
                comida4 = false;
                roda = true;
            }
        }

        gl.glLoadIdentity();//Gera a matriz identidade


        //Plot dos obstaculos Sólidos
        if(obstaculo1) {
            gl.glTranslatef(-1.4f, 0.6f, 0f);
            gl.glScalef(0.15f, 0.15f, 0f);
            gl.glRotatef(261.5f, 1f, 0f, 0f);

            gl.glRotatef(this.angulo, 1f, 0f, 0f);
            obstaculoSolido();
            gl.glFlush();

            if((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("0,6")){
                obstaculo1 = false;
                escalaX += 0.25f;
                escalaY += 0.25f;
                escalaZ += 0.25f;
            }
        }

        gl.glLoadIdentity();//Gera a matriz identidade

        if(obstaculo2) {
            gl.glTranslatef(1.4f, 0.6f, 0f);
            gl.glScalef(0.15f, 0.15f, 0f);
            gl.glRotatef(261.5f, 1f, 0f, 0f);

            gl.glRotatef(this.angulo, 1f, 0f, 0f);
            obstaculoSolido();
            gl.glFlush();
            if((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("0,6")){
                obstaculo2 = false;
                escalaX += 0.25f;
                escalaY += 0.25f;
                escalaZ += 0.25f;
            }
        }

        gl.glLoadIdentity();//Gera a matriz identidade

        //Plot dos obstaculos aramados
        if(obstaculo3) {
            gl.glTranslatef(1.4f, -0.9f, 0f);
            gl.glScalef(0.15f, 0.15f, 0f);
            gl.glRotatef(261.5f, 1f, 0f, 0f);

            gl.glRotatef(this.angulo, 1f, 0f, 0f);
            obstaculoAramado();
            gl.glFlush();
            if((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("-0,9")){
                obstaculo3 = false;
                escalaX -= 0.25f;
                escalaY -= 0.25f;
                escalaZ -= 0.25f;
            }
        }

        gl.glLoadIdentity();//Gera a matriz identidade

        if(obstaculo4) {
            gl.glTranslatef(-1.4f, -0.9f, 1.5f);
            gl.glScalef(0.15f, 0.15f, 0f);
            gl.glRotatef(261.5f, 1f, 0f, 0f);

            gl.glRotatef(this.angulo, 1f, 0f, 0f);
            obstaculoAramado();
            gl.glFlush();
            if((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("-0,9")){
                obstaculo4 = false;
                escalaX -= 0.25f;
                escalaY -= 0.25f;
                escalaZ -= 0.25f;
            }
        }

        //Plot do Pac-man
        gl.glLoadIdentity();//Gera a matriz identidade
        gl.glTranslatef(translacaoX,translacaoY,translacaoZ);
        if(escalaX == 0f){
            escalaX = 0.25f;
            escalaY= 0.25f;
            escalaZ= 0.25f;
        }
        gl.glScalef(escalaX,escalaY,escalaZ);
        gl.glRotatef(anguloComida, 0f,1f,1f);

        gl.glRotatef(216.5f, 0f,0f,1f);

        gl.glScalef(0.3f,0.3f,0.3f);
        esfera(0.5, 100,100);

        gl.glFlush();
        gl.glEnd();

        gl.glLoadIdentity();//Gera a matriz identidade

        //Plot dos pontos, circulos vermelhos
        pontos();

        anguloX += 0.5f;
        if(roda){
            anguloComida += 4.5f;
            if(anguloComida >= 360.0f){
                roda = false;
                anguloComida =0f;
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable desenho, int x, int y, int largura, int altura) {
        gl = desenho.getGL().getGL2();
        gl.glViewport(0, 0, 1300, 1300);
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(-2.0,2.0,-2.0,2.0,-1.5,1.5);
        gl.glMatrixMode(gl.GL_MODELVIEW);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                translacaoX += 0.1f;
                break;

            case KeyEvent.VK_LEFT:
                translacaoX -= 0.1f;
                break;

            case KeyEvent.VK_UP:
                translacaoY += 0.1f;
                translacaoZ -= 0.1f;
                break;

            case KeyEvent.VK_DOWN:
                translacaoY -= 0.1f;
                translacaoZ += 0.1f;
                break;

            case KeyEvent.VK_R:
                this.angulo += 4.5;
                break;

            case KeyEvent.VK_N:
                comida1 = true;
                comida2 = true;
                comida3 = true;
                comida4 = true;
                obstaculo1 = true;
                obstaculo2 = true;
                obstaculo3 = true;
                obstaculo4 = true;
                for(int i=0; i < pontos.length; i++){
                    pontos[i] = true;
                }
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void comida(){
        gl.glScalef(0.035f, 0.035f, 0.035f);

        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1f,0f,0f); //Vermelho
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glVertex3f( -1.0f, 1.0f, -1.0f);
        gl.glVertex3f( -1.0f, 1.0f, 1.0f );
        gl.glVertex3f( 1.0f, 1.0f, 1.0f );

        gl.glColor3f( 0f,1f,0f ); //Verde
        gl.glVertex3f( 1.0f, -1.0f, 1.0f );
        gl.glVertex3f( -1.0f, -1.0f, 1.0f );
        gl.glVertex3f( -1.0f, -1.0f, -1.0f );
        gl.glVertex3f( 1.0f, -1.0f, -1.0f );

        gl.glColor3f( 0f,0f,1f ); //Azul
        gl.glVertex3f( 1.0f, 1.0f, 1.0f );
        gl.glVertex3f( -1.0f, 1.0f, 1.0f );
        gl.glVertex3f( -1.0f, -1.0f, 1.0f );
        gl.glVertex3f( 1.0f, -1.0f, 1.0f );

        gl.glColor3f( 1f,1f,0f ); //Amarelo
        gl.glVertex3f( 1.0f, -1.0f, -1.0f );
        gl.glVertex3f( -1.0f, -1.0f, -1.0f );
        gl.glVertex3f( -1.0f, 1.0f, -1.0f );
        gl.glVertex3f( 1.0f, 1.0f, -1.0f );

        gl.glColor3f( 1f,0f,1f ); //Roxo
        gl.glVertex3f( -1.0f, 1.0f, 1.0f ); //
        gl.glVertex3f( -1.0f, 1.0f, -1.0f ); //
        gl.glVertex3f( -1.0f, -1.0f, -1.0f ); //
        gl.glVertex3f( -1.0f, -1.0f, 1.0f ); //

        gl.glColor3f( 0f,1f, 1f ); //Azul claro
        gl.glVertex3f( 1.0f, 1.0f, -1.0f );
        gl.glVertex3f( 1.0f, 1.0f, 1.0f );
        gl.glVertex3f( 1.0f, -1.0f, 1.0f );
        gl.glVertex3f( 1.0f, -1.0f, -1.0f );

    }

    public void obstaculoSolido() {

        gl.glBegin(gl.GL_TRIANGLES);
        for (int k=0; k<=360; k+= 5){
            gl.glColor3f(1f,0f,0f); //vermelho
            gl.glVertex3f(0,0,1);
            gl.glVertex3d(Math.cos(k),Math.sin(k),0);
            gl.glVertex3d(Math.cos(k+5), Math.sin(k+5),0);
        }
        gl.glEnd();

        //Circulo de baixo
        gl.glRotated(90,1,0,0);
        gl.glBegin(gl.GL_TRIANGLES);
        for (int i=0; i<=360; i+= 5) {
            gl.glColor3f( 0f,1f,0f ); //verde
            gl.glVertex3f(0,0,0);
            gl.glVertex3d(Math.cos(i),0, Math.sin(i));
            gl.glVertex3d(Math.cos(i+5),0,Math.sin(i+5));
        }
        gl.glEnd();
    }

    public void obstaculoAramado(){
        gl.glBegin(gl.GL_LINE_LOOP);
        for (int k=0; k<=360; k+= 5){
            gl.glColor3f(1f,0f,0f); //vermelho
            gl.glVertex3f(0,0,1);
            gl.glVertex3d(Math.cos(k),Math.sin(k),0);
            gl.glVertex3d(Math.cos(k+5), Math.sin(k+5),0);
        }
        gl.glEnd();

        //Circulo de baixo
        gl.glRotated(90,1,0,0);
        gl.glBegin(gl.GL_LINE_LOOP);
        for (int i=0; i<=360; i+= 5) {
            gl.glColor3f( 0f,1f,0f ); //verde
            gl.glVertex3f(0,0,0);
            gl.glVertex3d(Math.cos(i),0, Math.sin(i));
            gl.glVertex3d(Math.cos(i+5),0,Math.sin(i+5));
        }
        gl.glEnd();
    }

    public void esfera(double r, int lats,int longs){
        int i, j;
        for(i = 0; i <= lats; i++) {
            double lat0 = Math.PI * (-0.5 + (double) (i - 1) / lats);
            double z0  = Math.sin(lat0);
            double zr0 =  Math.cos(lat0);

            double lat1 = Math.PI * (-0.5 + (double) i / lats);
            double z1 = Math.sin(lat1);
            double zr1 = Math.cos(lat1);

            gl.glBegin(gl.GL_QUAD_STRIP);
            for(j = 0; j <= longs; j++) {
                double lng = 2 * Math.PI * (double) (j - 1) / longs;
                double x = Math.cos(lng);
                double y = Math.sin(lng);


                gl.glColor3f( 1f,1f,0f ); //amarelo
                gl.glNormal3f((float)x * (float)zr0, (float)y *(float) zr0,(float) z0);
                gl.glVertex3f((float)r * (float)x * (float)zr0, (float)r * (float)y * (float)zr0, (float)r * (float)z0);
                gl.glNormal3f((float)x * (float)zr1, (float)y * (float)zr1,(float) z1);
                gl.glVertex3f((float)r *(float) x * (float)zr1, (float)r *(float) y * (float)zr1, (float)r *(float) z1);
                if(j == 80){
                    break;
                }
            }
            gl.glEnd();
        }
    }

    public void plotPonto(){
        double x, y; //Pontos auxiliares
        double raio = 0.05;
        double angulo = 0; //Angulo do circulo
        double somaDoAngulo = 2 * Math.PI / 50; //50 == Numero de pontos

        gl.glBegin(GL2.GL_POLYGON); //Para printar um poligono, porém de uma forma que pareça um circulo

        gl.glColor3f(1f,0f,0f); //vermelho
        for(int i = 0; i < 50; i++){
            angulo = i * somaDoAngulo;
            x = raio * Math.cos(angulo);
            y = raio * Math.sin(angulo);

            gl.glVertex2d(x, y);
        }

        gl.glEnd();
        gl.glFlush();
        gl.glLoadIdentity();//Gera a matriz identidade
    }

    public void pontos() {

        if (pontos[0]) {
            gl.glTranslatef(-1.4f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[0] = false;
                contador++;
            }
        }


        if (pontos[1]) {
            gl.glTranslatef(-0.9f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,9")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[1] = false;
                contador++;
            }
        }

        if (pontos[2]) {
            gl.glTranslatef(-0.5f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,5")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[2] = false;
                contador++;
            }
        }

        if (pontos[3]) {
            gl.glTranslatef(-0.1f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[3] = false;
                contador++;
            }
        }

        if (pontos[4]) {
            gl.glTranslatef(-1.4f, 0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("0,1")) {
                pontos[4] = false;
                contador++;
            }
        }

        if (pontos[5]) {
            gl.glTranslatef(-1.4f, 0.3f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("0,3")) {
                pontos[5] = false;
                contador++;
            }
        }

        if (pontos[6]) {
            gl.glTranslatef(-1.4f, 0.5f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("0,5")) {
                pontos[6] = false;
                contador++;
            }
        }

        if (pontos[7]) {
            gl.glTranslatef(-1.4f, -0.3f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("-0,3")) {
                pontos[7] = false;
                contador++;
            }
        }

        if (pontos[8]) {
            gl.glTranslatef(-1.4f, -0.5f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("-0,5")) {
                pontos[8] = false;
                contador++;
            }
        }

        if (pontos[9]) {
            gl.glTranslatef(-1.4f, -0.7f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-1,4")) && aproximador.format(translacaoY).equals("-0,7")) {
                pontos[9] = false;
                contador++;
            }
        }

        if (pontos[10]) {
            gl.glTranslatef(-0.1f, 0.3f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("0,3")) {
                pontos[10] = false;
                contador++;
            }
        }

        if (pontos[11]) {
            gl.glTranslatef(-0.1f, 0.5f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("0,5")) {
                pontos[11] = false;
                contador++;
            }
        }

        if (pontos[12]) {
            gl.glTranslatef(-0.1f, 0.7f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("0,7")) {
                pontos[12] = false;
                contador++;
            }
        }


        if (pontos[13]) {
            gl.glTranslatef(-0.1f, 0.9f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("0,9")) {
                pontos[13] = false;
                contador++;
            }
        }


        if (pontos[14]) {
            gl.glTranslatef(-0.5f, 0.9f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,5")) && aproximador.format(translacaoY).equals("0,9")) {
                pontos[14] = false;
                contador++;
            }
        }

        if (pontos[15]) {
            gl.glTranslatef(-0.9f, 0.9f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,9")) && aproximador.format(translacaoY).equals("0,9")) {
                pontos[15] = false;
                contador++;
            }
        }

        if (pontos[16]) {
            gl.glTranslatef(0.3f, 0.9f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("0,3")) && aproximador.format(translacaoY).equals("0,9")) {
                pontos[16] = false;
                contador++;
            }
        }

        if (pontos[17]) {
            gl.glTranslatef(0.7f, 0.9f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("0,7")) && aproximador.format(translacaoY).equals("0,9")) {
                pontos[17] = false;
                contador++;
            }
        }

        if (pontos[38]) {
            gl.glTranslatef(1.1f, 0.9f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,1")) && aproximador.format(translacaoY).equals("0,9")) {
                pontos[38] = false;
                contador++;
            }
        }

        if (pontos[18]) {
            gl.glTranslatef(0.3f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("0,3")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[18] = false;
                contador++;
            }
        }

        if (pontos[19]) {
            gl.glTranslatef(0.7f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("0,7")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[19] = false;
                contador++;
            }
        }

        if (pontos[20]) {
            gl.glTranslatef(0.9f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("0,9")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[20] = false;
                contador++;
            }
        }

        if (pontos[21]) {
            gl.glTranslatef(1.2f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,2")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[21] = false;
                contador++;
            }
        }

        if (pontos[22]) {
            gl.glTranslatef(1.4f, -0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("-0,1")) {
                pontos[22] = false;
                contador++;
            }
        }

        if (pontos[23]) {
            gl.glTranslatef(1.4f, -0.3f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("-0,3")) {
                pontos[23] = false;
                contador++;
            }
        }

        if (pontos[24]) {
            gl.glTranslatef(1.4f, -0.5f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("-0,5")) {
                pontos[24] = false;
                contador++;
            }
        }

        if (pontos[25]) {
            gl.glTranslatef(1.4f, -0.7f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("-0,7")) {
                pontos[25] = false;
                contador++;
            }
        }

        if (pontos[26]) {
            gl.glTranslatef(1.4f, 0.1f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("0,1")) {
                pontos[26] = false;
                contador++;
            }
        }

        if (pontos[27]) {
            gl.glTranslatef(1.4f, 0.3f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("0,3")) {
                pontos[27] = false;
                contador++;
            }
        }

        if (pontos[28]) {
            gl.glTranslatef(1.4f, 0.5f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,4")) && aproximador.format(translacaoY).equals("0,5")) {
                pontos[28] = false;
                contador++;
            }
        }

        if (pontos[29]) {
            gl.glTranslatef(-0.1f, -0.5f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("-0,5")) {
                pontos[29] = false;
                contador++;
            }
        }

        if (pontos[30]) {
            gl.glTranslatef(-0.1f, -0.7f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("-0,7")) {
                pontos[30] = false;
                contador++;
            }
        }

        if (pontos[31]) {
            gl.glTranslatef(-0.1f, -0.9f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("-0,9")) {
                pontos[31] = false;
                contador++;
            }
        }

        if (pontos[32]) {
            gl.glTranslatef(-0.1f, -1.2f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,1")) && aproximador.format(translacaoY).equals("-1,2")) {
                pontos[32] = false;
                contador++;
            }
        }

        if (pontos[33]) {
            gl.glTranslatef(-0.5f, -1.2f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,5")) && aproximador.format(translacaoY).equals("-1,2")) {
                pontos[33] = false;
                contador++;
            }
        }

        if (pontos[34]) {
            gl.glTranslatef(-0.9f, -1.2f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("-0,9")) && aproximador.format(translacaoY).equals("-1,2")) {
                pontos[34] = false;
                contador++;
            }
        }

        if (pontos[35]) {
            gl.glTranslatef(0.3f, -1.2f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("0,3")) && aproximador.format(translacaoY).equals("-1,2")) {
                pontos[35] = false;
                contador++;
            }
        }

        if (pontos[36]) {
            gl.glTranslatef(0.7f, -1.2f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("0,7")) && aproximador.format(translacaoY).equals("-1,2")) {
                pontos[36] = false;
                contador++;
            }
        }

        if (pontos[37]) {
            gl.glTranslatef(1.1f, -1.2f, 0);
            plotPonto();
            if ((aproximador.format(translacaoX).equals("1,1")) && aproximador.format(translacaoY).equals("-1,2")) {
                pontos[37] = false;
                contador++;
            }
        }
    }

    public int getContador(){
        return this.contador;
    }

}
