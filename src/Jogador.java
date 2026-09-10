public class Jogador extends GameObject {

    /* Atributos específicos do Player */
    private double vx;             // Velocidade no eixo X
    private double vy;             // Velocidade no eixo Y
    private long nextShot;         // Timestamp para controlar o cooldown dos tiros

    /*Atributos para as explosões */
    private long explosionStart;
    private long explosionEnd;
    /* Construtor */
    public Jogador(double x, double y, double radius, double vx, double vy) {
        super(x, y, radius);
        this.vx = vx;
        this.vy = vy;
        this.nextShot = System.currentTimeMillis(); // Pronto para atirar imediatamente
        this.explosionStart = 0;
        this.explosionEnd = 0;
    }

    /* Processa o movimento com base nas teclas pressionadas */
    public void update(long delta, boolean keyUp, boolean keyDown, boolean keyLeft, boolean keyRight) {
        long currentTime = System.currentTimeMillis();

        if (state == EXPLODING) {
            // ao acabar o tempo da explosão, o jogador revive
            if (currentTime >= explosionEnd)  {
                state = ACTIVE;
                // coloca a nave no local inicial de novo 
                this.x = GameLib.WIDTH / 2;
                this.y = GameLib.HEIGHT * 0.90;
            }

        } else if (state == ACTIVE) {
            if (keyUp) y -= vy * delta;
            if (keyDown) y += vy * delta;
            if (keyLeft) x -= vx * delta;
            if (keyRight) x += vx * delta;
            
            // Restrições de tela (impede o Player de sair do mapa)
            if (x < 0) x = 0;
            if (x > GameLib.WIDTH) x = GameLib.WIDTH;
            if (y < 0) y = 0;
            if (y > GameLib.HEIGHT) y = GameLib.HEIGHT;
        }
    }

    /* Sobrescreve o update da classe pai */
    @Override
    public void update(long delta) {
        // O player precisa das teclas para atualizar, usado o método acima
    }

    /* Método para tentar disparar um tiro */
    public Projeteis shoot(boolean keyControl) {
        long currentTime = System.currentTimeMillis();

        if (state == ACTIVE && keyControl && currentTime >= nextShot) {
            nextShot = currentTime + 100; // Define o próximo tiro para daqui a 100ms
            // Cria e retorna um novo tiro saindo da ponta da nave (com velocidade para cima: vy negativo) 
            return new TiroJogador(x, y - 2 * radius);
        }

        return null; // Se não atirou, retorna nulo
    }

    /*Inicia o processo de explosão */
    public void explode() {
        if (state == ACTIVE) {
            state = EXPLODING;
            explosionStart = System.currentTimeMillis();
            explosionEnd = explosionStart + 2000; //2 segundos de animação (2000ms)
        }
    }

    /* Desenha o Player e/ou a explosão na tela */
    @Override
    public void draw() {
        long currentTime = System.currentTimeMillis();

        if (state == EXPLODING) {
            double alpha = (double) (currentTime - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(x, y, alpha);
        } else if (state == ACTIVE) {
            GameLib.setColor(java.awt.Color.BLUE);
            GameLib.drawPlayer(x, y, radius);
        }
    }
}
