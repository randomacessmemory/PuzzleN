package br.com.poli.puzzleN.puzzles;

import br.com.poli.puzzleN.Interfaces.CalculaFacil;
import br.com.poli.puzzleN.engine.Dificuldade;
import br.com.poli.puzzleN.engine.Puzzle;

public class PuzzleFacil extends Puzzle {

	public PuzzleFacil(String nome) {
		super(nome, Dificuldade.FACIL);
		super.setScore(new CalculaFacil());
	}

}