package br.com.poli.puzzleN.frontend.buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import br.com.poli.puzzleN.puzzles.PuzzleDificil;
import br.com.poli.puzzleN.puzzles.PuzzleFacil;
import br.com.poli.puzzleN.puzzles.PuzzleMedio;
import br.com.poli.puzzleN.frontend.screens.*;
import br.com.poli.puzzleN.testes.Main;

public class StartButton extends JButton implements ActionListener {

    private static final long serialVersionUID = 1L;
    PuzzleFrame frame;

    public StartButton(PuzzleFrame frame) {
        super("Iniciar");
        this.frame = frame;
        this.setBounds(360, 303, 89, 23);
        this.addActionListener(this);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void actionPerformed(ActionEvent a) {
        if (a.getSource() == this) {
            String nome = ((Menu) frame.getTela()).getTextField().getText();
            int index = ((Menu) frame.getTela()).getDificuldade().getSelectedIndex();
            switch (index) {
            case 1:
                frame.setPartida(new PuzzleFacil(nome));
                break;
            case 2:
                frame.setPartida(new PuzzleMedio(nome));
                break;
            case 3:
                frame.setPartida(new PuzzleDificil(nome));
                break;
            case 4:
                Main.InsanoPlayer();
                frame.dispose();
            default:
            }
            try {
                frame.updateTela(new Game(frame));
                frame.getPartida().setTempo(Calendar.getInstance());
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(frame, "Selecione uma dificuldade!");
            }

        }
    }
}