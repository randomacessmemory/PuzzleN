package br.com.poli.puzzleN.testes;

import java.util.Calendar;
import br.com.poli.puzzleN.frontend.screens.PuzzleFrame;

public class Main extends Thread {
	public static Calendar compareTime = null;
	public static int executeTime = 0;
	public static PuzzleFrame janela;
	public static void main(String args[]) {
		janela = new PuzzleFrame();
		janela.setVisible(true);
	}

	// public static void InsanoPlayer() {
	// 	try {
	// 		System.out.println("Jogar Insano? r: sim/nao");
	// 		Scanner read = new Scanner(System.in);

	// 		switch (read.nextLine()) {

	// 		case "sim":
	// 			System.out.flush();
	// 			System.out.println("A partida dura por no maximo 600 movimentos!");
	// 			System.out.println("Digite seu nome:");
	// 			Testes.start(read);
				
	// 			boolean kit = false;
	// 			while (!Testes.partida.getVenceu() && kit) {
	// 				for (int i = 0; i < 600; i++)
	// 				switch (Testes.mover(read)) {
	// 				case "HELP":
	// 					Testes.partida.resolveTabuleiro();
	// 					break;
	// 				case "EXIT":
	// 					kit = true;
	// 				default:
	// 					break;
	// 				}
	// 			}

	// 			if (Testes.partida.getVenceu()) {
	// 				System.out.println("Voce venceu!");
	// 				System.out.println("Salvar? r: sim/nao");
	// 				switch (read.nextLine()) {
	// 				case "sim":
	// 					// System.out.flush();
	// 					new Ranking();
	// 					Ranking.save(Testes.partida);
	// 					System.out.println("Ranking visivel no jogo padrão!");
	// 					read.close();
	// 					break;
	// 				default:
	// 					read.close();
	// 				}
	// 			} else
	// 				System.out.println("Voce perdeu!");
	// 			System.out
	// 					.println("tmepo de partida:" + Float.toString(Testes.partida.getTempoDecorrido()) + " min(s)");
	// 			read.close();
	// 			break;

	// 		default:
	// 			read.close();
	// 		}

	// 	} catch (Exception e) {
	// 		System.out.println(e.getLocalizedMessage());
	// 	}
	// }
}
