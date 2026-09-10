import java.awt.Color;

public class Estrela2 extends Estrela {

    public Estrela2(double x, double y) {
        super(x, y); // chama o construtor
        this.speed = 0.045;
        this.size = 2.0;
        this.color = Color.DARK_GRAY;
    }
}