import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/***********************************************************************/
/*                                                                     */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/***********************************************************************/

public class Main {

	/* Constantes relacionadas aos estados que os elementos   */
	/* do jogo (player, projeteis ou inimigos) podem assumir. */

	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	public static final int EXPLODING = 2;

	/* Espera, sem fazer nada, até que o instante de tempo atual seja */
	/* maior ou igual ao instante especificado no parâmetro "time.    */

	public static void busyWait(long time){
		while(System.currentTimeMillis() < time) Thread.yield();
	}

	public static void main(String [] args){

		boolean running = true;
		long delta;
		long currentTime = System.currentTimeMillis();

		// Declaração das entidades de modo que o jogador é um objeto
		Jogador player = new Jogador(GameLib.WIDTH / 2, GameLib.HEIGHT * 0.90, 12.0, 0.25, 0.25);
		// Substitui os arrays estáticos por ArrayLists
		List<Projeteis> tirosJogador = new ArrayList<>();
		List<Projeteis> tirosInimigos = new ArrayList<>();
		List<Inimigo> inimigos = new ArrayList<>(); // como usamos herança, uma única lista para todos os inimigos funciona 
		List<Estrela> background = new ArrayList<>(); // lista única para as estrelas do cenário
		/* Temporizadores e controles de spawn de inimigos */
		long nextEnemy1 = currentTime + 2000;
		double enemy2_spawnX = GameLib.WIDTH * 0.20;
		int enemy2_count = 0;
		long nextEnemy2 = currentTime + 7000;
		long nextEnemy3 = currentTime + 5000;

		// Lógica da inicialização do jogo:

     	/* Inicialização das estrelas mantendo a lógica de classes */
		for(int i = 0; i < 20; i++){
			background.add(new Estrela1(Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT));
		}
		for(int i = 0; i < 50; i++){
			background.add(new Estrela2(Math.random() * GameLib.WIDTH, Math.random() * GameLib.HEIGHT));
		}

		GameLib.initGraphics();

		while(running){

			delta = System.currentTimeMillis() - currentTime;
			currentTime = System.currentTimeMillis();

			// Verificando colisões
			if (player.isActive()) {// só verifica colisões no jogador se ele estiver vivo

				/* Colisões Player vs Projéteis */
				for (int i = 0; i < tirosInimigos.size(); i++) {

					Projeteis tiroAtual = tirosInimigos.get(i);
					boolean tiroEstaAtivo = tiroAtual.isActive();

					if (tiroEstaAtivo == true) {
						
						boolean houveColisao = player.colisaoEntre(tiroAtual, 0.8);

						if (houveColisao == true) {
							player.explode();
							tiroAtual.colidiu();
							break;
						}
					}
				}

				/* Colisões Player vs Inimigos */
				for (int i = 0; i < inimigos.size(); i++) {

					Inimigo inimigoAtual = inimigos.get(i);
					boolean inimigoEstaAtivo = inimigoAtual.isActive();

					if (inimigoEstaAtivo == true) {

						boolean houveColisao = player.colisaoEntre(inimigoAtual, 0.8);

						if (houveColisao == true) {
							player.explode();
							inimigoAtual.explode();
							break;
						}
					}
				}
			}

			/* 3. Colisões Projéteis (Player) vs Todos os Inimigos */
			for (int i = 0; i < tirosJogador.size(); i++) {

				Projeteis tiroAtual = tirosJogador.get(i);
				boolean tiroEstaAtivo = tiroAtual.isActive();

				if (tiroEstaAtivo == true) {
					// para cada tiro ativo do jogador, compara com cada inimigo ativo
					for (int j = 0; j < inimigos.size(); j++) {

						Inimigo inimigoAtual = inimigos.get(j);
						boolean inimigoEstaAtivo = inimigoAtual.isActive();

						if (inimigoEstaAtivo == true) {

							boolean houveColisao = tiroAtual.colisaoEntre(inimigoAtual, 1.0);

							if (houveColisao == true) {
								inimigoAtual.explode();
								tiroAtual.colidiu();
							}
						}
					}
				}
			}

			// atualizando estados:
			for (int i = 0; i < background.size(); i++){
				Estrela estrelaAtual = background.get(i);
				estrelaAtual.update(delta);
			}

			for (int i = 0; i < tirosJogador.size(); i++) {
				Projeteis tiroAtual = tirosJogador.get(i);
				tiroAtual.update(delta);
			}

			for (int i = 0; i < tirosInimigos.size(); i++) {
				Projeteis tiroAtual = tirosInimigos.get(i);
				tiroAtual.update(delta);
			}

			// Atualização dos inimigos
			for (int i = 0; i < inimigos.size(); i++) {
				Inimigo inimigoAtual = inimigos.get(i);
				inimigoAtual.update(delta, tirosInimigos, player.getY());
			}

			// Entrada de novos inimigos

			// se o tempo atual passa o timer do Enemy1, cria um novo
			if (currentTime > nextEnemy1) {
				double spawnX = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
				inimigos.add(new Enemy1(spawnX, -10.0));
				nextEnemy1 = currentTime + 500;
			}

			if (currentTime > nextEnemy2) {
				inimigos.add(new Enemy2(enemy2_spawnX, -10.0));
				enemy2_count++;
				if (enemy2_count < 10) {
					nextEnemy2 = currentTime + 120;
				} else {
					enemy2_count = 0;
					enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
					nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
				}
			}

			if (currentTime > nextEnemy3) {
				double spawnX = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
				inimigos.add(new Enemy3(spawnX, -10.0));
				nextEnemy3 = currentTime + 4000;
			}

			// Input do usuário

			boolean up = false, down = false, left = false, right = false, control = false;

			if (player.isActive()) {
				up = GameLib.iskeyPressed(GameLib.KEY_UP);
				down = GameLib.iskeyPressed(GameLib.KEY_DOWN);
				left = GameLib.iskeyPressed(GameLib.KEY_LEFT);
				right = GameLib.iskeyPressed(GameLib.KEY_RIGHT);
				control = GameLib.iskeyPressed(GameLib.KEY_CONTROL);

				Projeteis novoTiro = player.shoot(control);
				if (novoTiro != null) {
					tirosJogador.add(novoTiro); // adiciona o tiro criado na lista da main
				}
			}

			player.update(delta, up, down, left, right);

			if (GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

			// Implementação do garbage collector

			tirosJogador.removeIf(projetil -> {
				int estadoAtual = projetil.getState();
				boolean condicao = (estadoAtual == INACTIVE);
				return condicao;
			});

			tirosInimigos.removeIf(projetil -> {
				int estadoAtual = projetil.getState();
				boolean condicao = (estadoAtual == INACTIVE);
				return condicao;
			});
			inimigos.removeIf(enemy -> {
				int estadoAtual = enemy.getState();
				boolean condicao = (estadoAtual == INACTIVE);
				return condicao;
			});

			// Desenhando o fundo:

			for (int i = 0; i < background.size(); i++) {
				Estrela estrelaAtual = background.get(i);
				estrelaAtual.draw();
			}

			player.draw();

			for (int i = 0; i < tirosJogador.size(); i++) {
				Projeteis tiroAtual = tirosJogador.get(i);
				tiroAtual.draw();
			}

			for (int i = 0; i < tirosInimigos.size(); i++) {
				Projeteis tiroAtual = tirosInimigos.get(i);
				tiroAtual.draw();
			}

			for (int i = 0; i < inimigos.size(); i++) {
				Inimigo inimigoAtual = inimigos.get(i);
				inimigoAtual.draw();
			}

			GameLib.display();
			busyWait(currentTime + 3);
		}

		System.exit(0);
	}
}