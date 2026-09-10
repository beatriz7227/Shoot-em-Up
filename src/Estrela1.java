import java.awt.Color;

public class Estrela1 extends Estrela {

    public Estrela1(double x, double y) {
        super(x, y); // chama o construtor 
        this.speed = 0.070;
        this.size = 3.0;
        this.color = Color.GRAY;
    }
}