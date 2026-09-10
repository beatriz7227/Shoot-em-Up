import java.awt.Color;
import java.util.List;

public class Enemy3 extends Inimigo {

    private double[][] cantos = new double[4][2];
    private int paradasRealizadas = 0;
    private boolean disparoDuplo = false;
    private int etapaDisparo = 0;
    private long nextShootTime = 0;
    private int indiceCantoAtual = 0;

    public Enemy3(double x, double y) {
        super(x, y, 15.0, 0.35, 0.0, 0.0);

        cantos[0][0] = GameLib.WIDTH * 0.30;
        cantos[0][1] = GameLib.HEIGHT * 0.15;

        cantos[1][0] = GameLib.WIDTH * 0.70;
        cantos[1][1] = GameLib.HEIGHT * 0.15;

        cantos[2][0] = GameLib.WIDTH * 0.70;
        cantos[2][1] = GameLib.HEIGHT * 0.40;

        cantos[3][0] = GameLib.WIDTH * 0.30;
        cantos[3][1] = GameLib.HEIGHT * 0.40;
    }

    @Override
    public void update(long delta, List<Projeteis> projeteis, double playerY) {
        long currentTime = System.currentTimeMillis();

        if (isExploding()) {
            if (currentTime > this.explosionEnd) {
                setState(INACTIVE);
            }
            return;
        }

        if (isActive()) {

            if (paradasRealizadas >= 8) {
                this.y += 0.35 * delta;

                if (this.y > GameLib.HEIGHT + 50) {
                    setState(INACTIVE);
                }
                return;
            }

            if (disparoDuplo) {
                if (etapaDisparo == 1 && currentTime >= nextShootTime) {

                    for (int i = 0; i < 4; i++) {
                        double angleTiro = (2 * Math.PI / 4) * i + (Math.PI / 4);
                        double vx = Math.cos(angleTiro) * 0.25;
                        double vy = Math.sin(angleTiro) * 0.25;

                        projeteis.add(new TiroInimigo(this.x, this.y, vx, vy));
                    }

                    disparoDuplo = false;
                    etapaDisparo = 0;
                    indiceCantoAtual = (indiceCantoAtual + 1) % 4;
                    paradasRealizadas++;
                }
                return;
            }

            double targetX = cantos[indiceCantoAtual][0];
            double targetY = cantos[indiceCantoAtual][1];

            double dx = targetX - this.x;
            double dy = targetY - this.y;
            double distancia = Math.sqrt(dx * dx + dy * dy);

            if (distancia > 2.0) {
                double angle = Math.atan2(dy, dx);
                this.x += this.v * Math.cos(angle) * delta;
                this.y += this.v * Math.sin(angle) * delta;
            }
            else {
                for (int i = 0; i < 4; i++) {
                    double angleTiro = (2 * Math.PI / 4) * i;
                    double vx = Math.cos(angleTiro) * 0.25;
                    double vy = Math.sin(angleTiro) * 0.25;

                    projeteis.add(new TiroInimigo(this.x, this.y, vx, vy));
                }

                disparoDuplo = true;
                etapaDisparo = 1;
                nextShootTime = currentTime + 200;
            }
        }
    }

    @Override
    public void draw() {
        if (isActive()) {
            GameLib.setColor(Color.ORANGE);
            GameLib.fillRect(this.x, this.y, this.radius * 2, this.radius * 2); // forma do quadrado
        }
        else if (isExploding()) {
            long currentTime = System.currentTimeMillis();
            double alpha = (double) (currentTime - this.explosionStart) / (this.explosionEnd - this.explosionStart);

            GameLib.drawExplosion(this.x, this.y, alpha);
        }
    }
}