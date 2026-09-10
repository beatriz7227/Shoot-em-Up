public abstract class Projeteis extends GameObject {

    /* Atributos específicos do Projétil: velocidades no eixo X e Y e booleano do tipo */
    protected double vx;
    protected double vy;

    /* construtor que pede só de onde o tiro está saindo */
    public Projeteis(double x, double y) {
        super(x, y, 2.0); // o raio 2.0 é padrão pra todo tiro
    }

    @Override
    public void update(long delta) {
        if (state == ACTIVE) {
            // Atualiza a posição baseada na velocidade acumulada pelo tempo (delta)
            x += vx * delta;
            y += vy * delta;

            // Se o tiro sair dos limites da tela, desativa ele
            if (this.y < 0 || this.y > GameLib.HEIGHT || this.x < 0 || this.x > GameLib.WIDTH) {
                state = INACTIVE;
            }
        }
    }

    /* Método chamado pela Main quando o projétil atinge um alvo */
    public void colidiu() {
        this.state = INACTIVE;
    }
}