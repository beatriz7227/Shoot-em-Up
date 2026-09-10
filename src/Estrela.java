import java.awt.Color;

public class Estrela extends GameObject {

    // variáveis protegidas
    protected double speed;
    protected double size;
    protected Color color;

    /* construtor que pede apenas as coordenadas */
    public Estrela(double x, double y) {
        super(x, y, 1.0); // o 1 é um raio genérico só pra cumprir a regra do GameObject
    }

    @Override
    public void update(long delta) {
        if (state == ACTIVE) {
            this.y = this.y + (this.speed * delta);
            // se a estrela sair da tela por baixo, ela reaparece no topo
            if (this.y > GameLib.HEIGHT) {
                this.y = this.y - GameLib.HEIGHT; 
            }
        }
    }

    @Override
    public void draw() {
        if (state == ACTIVE) {
            GameLib.setColor(this.color);
            GameLib.fillRect(this.x, this.y, this.size, this.size);
        }
    }
}