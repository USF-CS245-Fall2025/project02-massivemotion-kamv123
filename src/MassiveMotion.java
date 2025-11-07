import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Builds output
 */
public class MassiveMotion extends JPanel implements ActionListener {

    protected Timer tm;
    protected int windowWidth = 640;
    protected int windowHeight = 480;
    protected List<CelestialBody> celestialObjects;

    // comet creation
    private double genXProb = 0.05;
    private double genYProb = 0.05;
    private int bodySize = 10;
    private int bodyVelocity = 3;

    /**
     * Reads through propfile and creates window
     * @param propfile
     */
    public MassiveMotion(String propfile) {
        try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream(propfile);
            prop.load(in);
            in.close();

            int delay = Integer.parseInt(prop.getProperty("timer_delay", "75"));
            windowWidth = Integer.parseInt(prop.getProperty("window_size_x", "640"));
            windowHeight = Integer.parseInt(prop.getProperty("window_size_y", "480"));

            tm = new Timer(delay, this);

            // Initialize list
            String listType = prop.getProperty("list", "arraylist").toLowerCase();
            switch (listType) {
                case "arraylist":
                    celestialObjects = new ArrayList<>();
                    break;
                case "single":
                    celestialObjects = new SinglyLinkedList<>();
                    break;
                case "double":
                    celestialObjects = new DoublyLinkedList<>();
                    break;
                case "dummyhead":
                    celestialObjects = new DummyHeadLinkedList<>();
                    break;
                default:
                    System.out.println("Unknown list type. Using ArrayList by default.");
                    celestialObjects = new ArrayList<>();
            }

            // Add red star as first celestial object
            int starX = Integer.parseInt(prop.getProperty("star_position_x", "320"));
            int starY = Integer.parseInt(prop.getProperty("star_position_y", "240"));
            int starSize = Integer.parseInt(prop.getProperty("star_size", "30"));
            int starVelX = Integer.parseInt(prop.getProperty("star_velocity_x", "0"));
            int starVelY = Integer.parseInt(prop.getProperty("star_velocity_y", "0"));
            CelestialBody star = new CelestialBody(starX, starY, starSize, starVelX, starVelY, Color.RED);
            celestialObjects.add(star);

            // Comet creation
            genXProb = Double.parseDouble(prop.getProperty("gen_x", "0.05"));
            genYProb = Double.parseDouble(prop.getProperty("gen_y", "0.05"));
            bodySize = Integer.parseInt(prop.getProperty("body_size", "10"));
            bodyVelocity = Integer.parseInt(prop.getProperty("body_velocity", "3"));

        } catch (Exception e) {
            System.out.println("Error loading properties. Using defaults.");
            tm = new Timer(75, this);
            celestialObjects = new ArrayList<>();
            CelestialBody star = new CelestialBody(320, 240, 30, 0, 0, Color.RED);
            celestialObjects.add(star);
        }
    }

    /**
     * Paints stars & comet
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < celestialObjects.size(); i++) {
            CelestialBody body = celestialObjects.get(i);
            g.setColor(body.getColor());
            g.fillOval(body.getX(), body.getY(), body.getSize(), body.getSize());
        }

        tm.start();
    }

    /**
     * updates actions
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        // Move existing celestial bodies and remove off-screen comets
        for (int i = 0; i < celestialObjects.size(); i++) {
            CelestialBody body = celestialObjects.get(i);
            body.move();

            // Only remove comets keep star
            if (body.getColor() == Color.BLACK && body.isOffScreen(windowWidth, windowHeight)) {
                celestialObjects.remove(i);
                i--;
            }
        }

        // Generate new comets probabilistically
        generateNewComets();

        repaint();
    }

    private void generateNewComets() {
        // Generate along top/bottom
        if (Math.random() < genXProb) {
            int x;
            double randomValue = Math.random();

            if(randomValue < 0.5) {
                x=0;
            } else {
                x = windowWidth - bodySize;
            }

            int y = (int) (Math.random() * windowHeight);
            int vx = randomVelocity(bodyVelocity);
            int vy = randomVelocity(bodyVelocity);
            celestialObjects.add(new CelestialBody(x, y, bodySize, vx, vy, Color.BLACK));
        }

        // Generate along left/right
        if (Math.random() < genYProb) {
            int x = (int) (Math.random() * windowWidth);
            int y;
            double randomValue = Math.random();
            if(randomValue < 0.5) {
                y = 0;
            } else {
                y = windowHeight - bodySize;
            }

            int vx = randomVelocity(bodyVelocity);
            int vy = randomVelocity(bodyVelocity);
            celestialObjects.add(new CelestialBody(x, y, bodySize, vx, vy, Color.BLACK));
        }
    }

    private int randomVelocity(int max) {
        int v = 0;
        while (v == 0) {
            v = (int) (Math.random() * (2 * max + 1)) - max;
        }
        return v;
    }

    /** Runs simulation
     */
    public static void main(String[] args) {
        System.out.println("Massive Motion starting...");

        if (args.length < 1) {
            System.out.println("Please provide property file as argument.");
            System.exit(1);
        }

        MassiveMotion mm = new MassiveMotion(args[0]);

        JFrame jf = new JFrame();
        jf.setTitle("Massive Motion");
        jf.setSize(mm.windowWidth, mm.windowHeight);
        jf.add(mm);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}
