package br.com.poli.puzzleN.frontend;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JButton;
import br.com.poli.puzzleN.engine.Puzzle;

public class Game extends JPanel {

	private static final long serialVersionUID = 1L;
	private Puzzle partida;
	private JButton[] tabuleiro;
	JLabel lblNome;
	SurrenderButton btnDesistir;

	public Game(Puzzle partida, PuzzleFrame frame) {

		this.partida = partida;
		this.partida.iniciaPartida();
		this.setLayout(null);
		int k = this.partida.getTabuleiro().getGrid().length;
		this.tabuleiro = new JButton[k * k];

		lblNome = new JLabel("nome: " + partida.getJogador().getNome());
		lblNome.setBounds(30, 500, 10 * (6 + partida.getJogador().getNome().length()), 20);
		lblNome.setForeground(Color.WHITE);
		lblNome.setHorizontalTextPosition(SwingConstants.LEFT);
		this.add(lblNome);

		btnDesistir = new SurrenderButton(partida, frame);
		btnDesistir.setBounds(700, 500, 90, 30);
		this.add(btnDesistir);
		
		for (int y = 0; y < k; y++) {
			for (int x = 0; x < k; x++) {
				if (partida.getTabuleiro().getGrid()[y][x].getValor() == 0)
					continue;
				this.tabuleiro[(y * k) + x] = new BlocoButton(partida, frame, x, y);
				this.add(this.tabuleiro[(y * k) + x]);
			}
		}
		
		JLabel backGround = new BackGround();
		this.add(backGround);
	}
	Puzzle getPartida(){
		return this.partida;
	}
}