import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class Janela implements ActionListener {

    private File openning;
    private Clip sound;

    private JButton b1;
    private int pontos;
    JLabel cont = new JLabel("Pontos: "+pontos);

    public Janela(){
        pontos=0;
        cont.setBounds(50,300, 100,30);

        try{
            openning = new File("src\\Hora.wav");
            sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(openning));
            sound.loop(1000000000);
        }catch(Exception e){

        }

        JFrame frame = new JFrame();
        frame.setSize(700, 600);
        try{
            Image img = javax.imageio.ImageIO.read(new File("src\\Ednaldo_Pereira_Legal.png"));
            frame.setContentPane(new JPanel(new BorderLayout()) {
                @Override
                public void paintComponent(Graphics g) {
                    g.drawImage(img, 350, 350, null);
                }
            });
        }catch(Exception e){

        }
        b1 = new JButton("Pac-Man");
        b1.setBounds(frame.getWidth()/2, frame.getHeight()/2, 100, 60);
        b1.addActionListener(this);

        frame.setLayout(null);
        frame.add(b1);
        frame.add(cont);
        frame.setTitle("Projeto 02");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){

            GLProfile profile = GLProfile.get(GLProfile.GL2);
            GLCapabilities capabilities = new GLCapabilities(profile);
            GLCanvas glcanvas = new GLCanvas(capabilities);
            glcanvas.setSize(1000, 1000);
            Pacman p = new Pacman();
            glcanvas.addGLEventListener(p);
            glcanvas.addKeyListener(p);

            JFrame frame = new JFrame("Pac-Man");
            frame.getContentPane().add(glcanvas);
            frame.setSize(1000,1000);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setLayout(null);

            FPSAnimator animacao = new FPSAnimator(glcanvas, 300, true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    animacao.stop();
                    pontos= p.getContador();
                    cont.setText("Pontos: "+pontos);
                }
            });
            animacao.start();
        }
    }
}
