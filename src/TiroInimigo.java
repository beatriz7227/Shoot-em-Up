import java.awt.Color;

public class TiroInimigo extends Projeteis {

    /* construtor pede a velocidade, pois cada inimigo atira num ângulo diferente */
    public TiroInimigo(double x, double y, double vx, double vy) {
        super(x, y); // manda o x e y já que sabe o raio de Projeteis

        // velocidades recebidas do inimigo
        this.vx = vx;
        this.vy = vy;
    }

    @Override
    public void draw() {
        if (state == ACTIVE) {
            GameLib.setColor(Color.RED);
            GameLib.drawDiamond(this.x, this.y, this.radius);
        }
    }
}