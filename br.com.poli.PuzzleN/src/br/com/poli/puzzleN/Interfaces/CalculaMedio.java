package br.com.poli.puzzleN.Interfaces;

import java.util.Calendar;

import br.com.poli.puzzleN.engine.Puzzle;

public class CalculaMedio implements CalculaScore{
	int pontos;
	
	public CalculaMedio(Puzzle partida){
		
		pontos = partida.getTabuleiro().getGrid().length * 100000;
	}
	
	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	public int pontos(Puzzle partida) {
		long time = partida.getTempo(Calendar.getInstance());
		int moves = partida.getQuantidadeMovimentos();
		pontos /= ((int)time / 10) + moves;
		return pontos;
	}
}
