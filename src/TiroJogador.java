import java.awt.Color;

public class TiroJogador extends Projeteis {

    /* construtor pede apenas a posição inicial */
    public TiroJogador(double x, double y) {
        super(x, y); 
        
        // atribui a velocidade fixa do jogador
        this.vx = 0.0;  // não anda pros lados
        this.vy = -1.0; // sobe na tela
    }
    
    @Override
    public void draw() {
        if (state == ACTIVE) {
            GameLib.setColor(Color.GREEN);
            GameLib.drawLine(this.x, this.y - 5, this.x, this.y + 5);
            GameLib.drawLine(this.x - 1, this.y - 3, this.x - 1, this.y + 3);
            GameLib.drawLine(this.x + 1, this.y - 3, this.x + 1, this.y + 3);
        }
    }
}