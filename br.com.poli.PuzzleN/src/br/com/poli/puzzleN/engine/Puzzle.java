package br.com.poli.puzzleN.engine;

import java.awt.Point;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import br.com.poli.puzzleN.Interfaces.CalculaScore;
import br.com.poli.puzzleN.exceptions.*;
import br.com.poli.puzzleN.frontend.buttons.BlocoButton;
import br.com.poli.puzzleN.frontend.screens.Game;

public class Puzzle implements Serializable, Comparable<Puzzle> {

	private static final long serialVersionUID = 0104L;
	private Jogador jogador;
	private Tabuleiro gridPuzzle;
	private int quantidadeMovimentos;
	private CalculaScore score;
	private boolean venceu;
	private Calendar tempo;
	private Calendar finalTime;
	private Dificuldade dificuldade;
	private Point zero;

	public Puzzle(String nome, Dificuldade dificuldade) {
		jogador = new Jogador(nome);
		this.dificuldade = dificuldade;
		quantidadeMovimentos = 0;
		venceu = false;
		tempo = Calendar.getInstance();
		tempo.setTime(new Date());
		gridPuzzle = new Tabuleiro((int) Math.sqrt(dificuldade.getValor() + 1));
	}

	public void iniciaPartida() {
		quantidadeMovimentos = 0;
		venceu = false;
		tempo.setTime(new Date());
		gridPuzzle.geraTabuleiro((int) Math.sqrt(dificuldade.getValor() + 1));
		zero = new Point(gridPuzzle.getGrid().length - 1, gridPuzzle.getGrid().length - 1);
		this.jhonnyBravo();
	}

	private void jhonnyBravo() {
		Random r = new Random();
		int k = gridPuzzle.getGrid().length - 1;
		int i = 1, j = 0, R, last_r = 0;

		while (i < (200 * gridPuzzle.getGrid().length)) {
			R = (int) ((r.nextInt(100) + r.nextInt(60)) / 40) + 1;
			// System.out.print(i + "-> *" + R + "X" + last_r + "*\t");
			if ((j - i) > 2)
				if (last_r == 3)
					last_r = 4;
				else if (last_r == 4)
					last_r = 3;
				else if (last_r == 2)
					last_r = 1;
				else
					last_r = 2;

			// System.out.println("Zero-> x:[" + zero.x + "]|y:[" + zero.y + "]");
			if (R == last_r || R == 1 ? last_r != 2 && zero.y > 0
					: R == 2 ? last_r != 1 && zero.y < k
							: R == 3 ? last_r != 4 && zero.x < k : R == 4 ? last_r != 3 && zero.x > 0 : true) {

				if (bubbleMoveZero(R)) {

					// Testes.showTab(gridPuzzle);
					last_r = R;
					i++;
				}

			}
			if (i > 1)
				j++;
		}
		gridPuzzle.setZero(this.zero);
	}

	public boolean bubbleMoveZero(int move) {
		switch (move) {
		case 1:
			return zeroMap("cima");
		case 2:
			return zeroMap("baixo");
		case 3:
			return zeroMap("direita");
		case 4:
			return zeroMap("esquerda");

		case 5:// Noroeste
			return zeroMap("cima") || zeroMap("esquerda");

		case 6:// Sudoeste
			return zeroMap("baixo") || zeroMap("esquerda");

		case 7:// Nordeste
			return zeroMap("direita") || zeroMap("cima");

		case 8:// Sudeste
			return zeroMap("direita") || zeroMap("baixo");

		default:
			return false;
		}
	}

	public void resolveTabuleiro() throws Error {

		// this.setTempo(Calendar.getInstance());
		// if (this.getTempo().get(Calendar.SECOND) > 10)
		// throw new TempoExcedido();
	}

	public void fillLine(int[] line, P[] places) {

		PseudoTab way = this.getTabuleiro().getPseudoTabuleiro();
		int max = line.length - 1;
		int y = places == null ? PseudoTab.SOLVED.position(line[0]).y : places[0].y;

		for (int i = 0; i < line.length; i++)
			if (i < line.length - 2) {

				if (!way.position(line[i]).equals(places == null ? PseudoTab.SOLVED.position(line[i]) : places[i]))
					autoZeroMove(way.position(line[i]));
				autoMoveTo(line[i], places == null ? PseudoTab.SOLVED.position(line[i]) : places[i]);

			} else if (i < line.length && line.length == this.getTabuleiro().getGrid().length) {

				if (!way.position(line[i])
						.equals(places == null ? new P(max, (i - (line.length - 2) + 1) + y) : places[i]))
					autoZeroMove(way.position(line[i]));
				autoMoveTo(line[i], new P(max, (i - (line.length - 2) + 1) + y));
			}

		way = this.getTabuleiro().getPseudoTabuleiro();
		if (way.isInPositons(line, places)) {

			autoZeroMove(new P(max - 1, 1 + y));
			autoZeroMove(new P(max - 1, y));

			autoZeroMove(new P(max, y));
			autoZeroMove(new P(max, 1 + y));
			autoZeroMove(new P(max, 2 + y));

			autoZeroMove(new P(max - 1, 2 + y));
			autoZeroMove(new P(max - 1, 1 + y));
			autoZeroMove(new P(max - 1, y));

			autoZeroMove(new P(max, y));
			autoZeroMove(new P(max, 1 + y));
		}
		way = this.getTabuleiro().getPseudoTabuleiro();
		for (PseudoTab pst : way.ordernLine(y))
			executarMovimentoAuto(pst.move);
	}

	public void fillColl(int[] coll) {

		PseudoTab way = this.getTabuleiro().getPseudoTabuleiro();
		int max = coll.length - 1;
		int x = PseudoTab.SOLVED.position(coll[0]).x;
		int k = getTabuleiro().getGrid().length;
		for (int i = 0; i < coll.length; i++)
			if (i < coll.length - 2) {

				if (!way.position(coll[i]).equals(PseudoTab.SOLVED.position(coll[i])))
					autoZeroMove(way.position(coll[i]));
				autoMoveTo(coll[i], PseudoTab.SOLVED.position(coll[i]));

			} else if (i < coll.length && coll.length == k) {

				if (!way.position(coll[i]).equals(new P(max, (i - (coll.length - 2) + 1) + x)))
					autoZeroMove(way.position(coll[i]));
				autoMoveTo(coll[i], new P((i - (coll.length - 2) + 1) + x, k - max));

			}

		way = this.getTabuleiro().getPseudoTabuleiro();
		if (way.isInPositons(coll, null)) {

			autoZeroMove(new P(1 + x, max - 1));
			autoZeroMove(new P(x, max - 1));

			autoZeroMove(new P(x, max));
			autoZeroMove(new P(1 + x, max));
			autoZeroMove(new P(2 + x, max));

			autoZeroMove(new P(2 + x, max - 1));
			autoZeroMove(new P(1 + x, max - 1));
			autoZeroMove(new P(x, max - 1));

			autoZeroMove(new P(x, max));
			autoZeroMove(new P(1 + x, max));
		}
		way = this.getTabuleiro().getPseudoTabuleiro();
		for (PseudoTab pst : way.ordernColl(x))
			executarMovimentoAuto(pst.move);
	}

	private boolean inRange(int in, int min, int max) {
		return (in >= min && in < max);
	}

	public String smartMove(int x, int y) throws MovimentoInvalido {

		String sentido = "null";
		int i, j;
		for (i = -1; i <= 1; i += 2) {
			if (inRange((y + i), 0, gridPuzzle.getGrid().length))
				if (gridPuzzle.getGrid()[y + i][x].getValor() == 0) {
					if (i == -1)
						sentido = "cima";
					else
						sentido = "baixo";
					if (!gridPuzzle.executaMovimento(x, y, sentido))
						throw new MovimentoInvalido();
					quantidadeMovimentos++;
					return sentido;
				}
		}
		for (j = -1; j <= 1; j += 2) {
			if (inRange((x + j), 0, gridPuzzle.getGrid().length))
				if (gridPuzzle.getGrid()[y][x + j].getValor() == 0) {
					if (j == -1)
						sentido = "esquerda";
					else
						sentido = "direita";
					if (!gridPuzzle.executaMovimento(x, y, sentido))
						throw new MovimentoInvalido();
					quantidadeMovimentos++;
					return sentido;
				}

		}
		return sentido;
	}

	public String smartMove(Point bloco) throws MovimentoInvalido {
		return smartMove(bloco.x, bloco.y);
	}

	private boolean zeroMap(String sentido) {
		int k = this.gridPuzzle.getGrid().length - 1;
		switch (sentido) {
		case "direita":
			if (zero.x < k ? this.gridPuzzle.executaMovimento(zero.x + 1, zero.y, "esquerda") : false) {
				zero.setLocation(zero.x + 1, zero.y);
				return true;
			} else
				return false;

		case "baixo":
			if (zero.y < k ? this.gridPuzzle.executaMovimento(zero.x, zero.y + 1, "cima") : false) {
				zero.setLocation(zero.x, zero.y + 1);
				return true;
			} else
				return false;

		case "esquerda":
			if (zero.x > 0 ? this.gridPuzzle.executaMovimento(zero.x - 1, zero.y, "direita") : false) {
				zero.setLocation(zero.x - 1, zero.y);
				return true;
			} else
				return false;

		case "cima":
			if (zero.y > 0 ? this.gridPuzzle.executaMovimento(zero.x, zero.y - 1, "baixo") : false) {
				zero.setLocation(zero.x, zero.y - 1);
				return true;
			} else
				return false;
		default:
			return false;
		}

	}

	public void executarMovimentoAuto(int x, int y) {
		try {
			if (Game.getTabuleiro() != null) {
				int index = gridPuzzle.getGrid()[y][x].getValor();
				if (index != 0)
					((BlocoButton) Game.getTabuleiro().get(index)).moveButton();
				this.getTabuleiro().print();
			} else
				smartMove(x, y);
			int k = this.getTabuleiro().getGrid().length - 1;
			if (this.getTabuleiro().getGrid()[k][k].getValor() == 0)
				if (this.isFimDeJogo()) {
					this.setFinalTime();
					// calcula e salva os pontos imediatamente para maior precisão
					this.getScore().pontos(this);
					Ranking.save(this);
				}
		} catch (Exception e) {
		}
	}

	public void executarMovimentoAuto(Point bloco) {
		if (bloco != null)
			executarMovimentoAuto(bloco.x, bloco.y);
		else
			System.out.println("err-606");
	}

	public void autoMoveTo(int quem, P to) {
		PseudoTab way = getTabuleiro().getPseudoTabuleiro();
		LinkedList<PseudoTab> solution;
		if (way.position(quem).equals(to))
			return;
		solution = way.pointWay(quem, to);
		solution.poll();
		for (PseudoTab p : solution)
			this.executarMovimentoAuto(p.move);
	}

	public void autoZeroMove(P to) {
		autoMoveTo(0, to);
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public Tabuleiro getTabuleiro() {
		return gridPuzzle;
	}

	public int getQuantidadeMovimentos() {
		return quantidadeMovimentos;
	}

	public void setQuantidadeMovimentos(int quantidadeMovimentos) {
		this.quantidadeMovimentos = quantidadeMovimentos;
	}

	public CalculaScore getScore() {
		return score;
	}

	public void setScore(CalculaScore score) {
		this.score = score;
	}

	public boolean getVenceu() {
		return venceu;
	}

	public void setVenceu(boolean venceu) {
		this.venceu = venceu;
	}

	public Calendar getTempo() {
		return tempo;
	}

	public void setTempo(Calendar tempo) {
		this.tempo = tempo;
	}

	public long getTempo(Calendar now) {
		now.setTime(new Date());
		return now.get(Calendar.MILLISECOND) - tempo.get(Calendar.MILLISECOND);
	}

	public Calendar getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(Calendar finalTime) {
		this.finalTime = finalTime;
	}

	public void setFinalTime() {
		this.finalTime = Calendar.getInstance();
		finalTime.setTime(new Date());
	}

	public float getTempoDecorrido() {
		long start = tempo.getTime().getTime();
		long end = finalTime.getTime().getTime();
		return (float) ((float) (end - start) / (60 * 1000));
	}

	public boolean isFimDeJogo() {
		venceu = gridPuzzle.isTabuleiroOrdenado(dificuldade);
		return venceu;
	}

	public void setDificuldade(Dificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}

	public Dificuldade getDificuldade() {
		return dificuldade;
	}

	public int compareTo(Puzzle puzzle) {
		return puzzle.getScore().getPontos() - this.getScore().getPontos();
	}

	public Point getZero() {
		return zero;
	}

	public void setZero(Point zero) {
		this.zero = zero;
	}

}