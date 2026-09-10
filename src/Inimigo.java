import java.util.List;

public abstract class Inimigo extends GameObject {
    /* Atributos específicos dos inimigos */
    protected double v;          // velocidade
    protected double angle;      // ângulo de movimento
    protected double rv;         // velocidade de rotação

    /*Atributos para as explosões */
    protected double explosionStart;    //instantes dos inícios das explosões
    protected double explosionEnd;      //instantes dos fins das explosões

    public Inimigo(double x, double y, double radius, double v, double angle, double rv) {
        super(x, y, radius);
        this.v = v;
        this.angle = angle;
        this.rv = rv;
        this.explosionStart = 0;
        this.explosionEnd = 0;
    }

    public void explode(){
        if (this.state == ACTIVE) {
            this.state = EXPLODING;
            this.explosionStart = System.currentTimeMillis();
            this.explosionEnd = this.explosionStart + 500;
        }
    }

    @Override
    public void update(long delta) {
    }

    public abstract void update(long delta, List<Projeteis> projeteis, double playerY);

}