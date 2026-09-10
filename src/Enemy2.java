import java.awt.Color;
import java.util.List;

public class Enemy2 extends Inimigo {

    protected double spawx;
    protected double count;

    public Enemy2(double x, double y) {
        super(x, y, 12.0, 0.42, (3 * Math.PI) / 2, 0.0);
        this.spawx = GameLib.WIDTH * 0.20;
        this.count = 0.0;
    }

    // Assinatura atualizada recebendo os parâmetros necessários
    @Override
    public void update(long delta, List<Projeteis> projeteis, double playerY) {
        long currentTime = System.currentTimeMillis();

        // Se inimigo estiver explodindo
        if (isExploding()) {
            if (currentTime > this.explosionEnd) {
                setState(INACTIVE);
            }
        }

        // Se inimigo estiver ativo (ACTIVE)
        if (isActive()) {
            // Se sair da tela nas laterais
            if (this.x < -10 || this.x > GameLib.WIDTH + 10) {
                setState(INACTIVE);
            }
            else {
                boolean shootNow = false;
                double previousY = this.y;
                                    
                this.x += this.v * Math.cos(this.angle) * delta;
                this.y += this.v * Math.sin(this.angle) * delta * (-1.0);
                this.angle += this.rv * delta;
                
                double threshold = GameLib.HEIGHT * 0.30;
                
                if (previousY < threshold && this.y >= threshold) {
                    if (this.x < GameLib.WIDTH / 2) this.rv = 0.003;
                    else this.rv = -0.003;
                }
                
                if (this.rv > 0 && Math.abs(this.angle - 3 * Math.PI) < 0.05) {
                    this.rv = 0.0;
                    this.angle = 3 * Math.PI;
                    shootNow = true;
                }
                
                if (this.rv < 0 && Math.abs(this.angle) < 0.05) {
                    this.rv = 0.0;
                    this.angle = 0.0;
                    shootNow = true;
                }

                // Lógica de disparo do Enemy2                    
                if (shootNow) {
                    // Três ângulos que dispara
                    double[] angles = { Math.PI/2 + Math.PI/8, Math.PI/2, Math.PI/2 - Math.PI/8 };

                    // Passa por cada ângulo para criar um projétil
                    for (int k = 0; k < angles.length; k++) {
                        double a = angles[k] + Math.random() * Math.PI/6 - Math.PI/12;
                        double vx = Math.cos(a) * 0.30;
                        double vy = Math.sin(a) * 0.30 ;
                        
                        // Cria o tiro e adiciona na lista
                        projeteis.add(new TiroInimigo(this.x, this.y, vx, vy));
                    }
                }
            }
        }
    }

    // Desenho do Enemy2 e sua explosão
    @Override
    public void draw() {
        if (isActive()) {
            GameLib.setColor(Color.MAGENTA);
            GameLib.drawDiamond(this.x, this.y, this.radius);
        } 
        else if (isExploding()) {
            long currentTime = System.currentTimeMillis();
            double alpha = (double) (currentTime - this.explosionStart) / (this.explosionEnd - this.explosionStart);
                
            GameLib.drawExplosion(this.x, this.y, alpha);
        }
    }
}