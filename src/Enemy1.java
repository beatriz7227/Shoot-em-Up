import java.awt.Color;
import java.util.List;

public class Enemy1 extends Inimigo {

    protected long nextShoot;    // instante do próximo tiro

    public Enemy1(double x, double y) {
        super(x, y, 9.0, 0.20 + Math.random() * 0.15, (3 * Math.PI) / 2, 0.0);
        this.nextShoot = System.currentTimeMillis() + 500;
    }

    @Override
    public void update(long delta, List<Projeteis> projeteis, double playerY) {
        long currentTime = System.currentTimeMillis();

        //Se inimigo estiver explodindo
        if (isExploding()) {
            if (currentTime > this.explosionEnd) {
                setState(INACTIVE);
            }
        }

        //Se inimigo estiver ativo (ACTIVE)
        if (isActive()) {
            //Se sair da tela
            if (this.y > GameLib.HEIGHT + 10) {
                setState(INACTIVE);
            }
            else {
                this.x += this.v * Math.cos(this.angle) * delta;
                this.y += this.v * Math.sin(this.angle) * delta * (-1.0);
                this.angle += this.rv * delta;
                
                // Lógica de disparo do Enemy1
                if (currentTime > this.nextShoot && this.y < playerY) {
                        
                    double vx = Math.cos(this.angle) * 0.45;
                    double vy = Math.sin(this.angle) * 0.45 * (-1.0);
                        
                    // Cria o tiro
                    projeteis.add(new TiroInimigo(this.x, this.y, vx, vy));

                    //Próximo tiro
                    this.nextShoot = (long) (currentTime + 200 + Math.random() * 500);
                }
            }
        }
    }

    //Desenho do Enemy1 e sua explosão
    @Override
    public void draw() {
        if (isActive()) {
            GameLib.setColor(Color.CYAN);
            GameLib.drawCircle(this.x, this.y, this.radius);
        } 
        else if (isExploding()) {
        long currentTime = System.currentTimeMillis();
        double alpha = (double) (currentTime - this.explosionStart) / (this.explosionEnd - this.explosionStart);
            
        GameLib.drawExplosion(this.x, this.y, alpha);
    }
}
}