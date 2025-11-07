import java.awt.Color;

public class CelestialBody {
    private int x, y, size, velx, vely;
    private Color color;


    /** intializes Celestial bodies (star/comet)
     * @param x
     * @param y
     * @param size
     * @param velx
     * @param vely
     * @param color
     */
    public CelestialBody(int x, int y, int size, int velx, int vely, Color color) {
        this.x = x; this.y = y; this.size = size; this.velx = velx; this.vely = vely; this.color = color;
    }

    /** Moves celestial body based on velocity in prop file
     */
    public void move() { x += velx; y += vely; }

    /** Checks star/comet placement and if it's off the screen
     * @param width
     * @param height
     * @return true if it's off-screen
     */
    public boolean isOffScreen(int width, int height) {
        return x + size < 0 || x > width || y + size < 0 || y > height;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
    public Color getColor() { return color; }
}
