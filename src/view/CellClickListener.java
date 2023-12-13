//used to help GameController communicate with GUIView
package view;
import javax.swing.JPanel;

public interface CellClickListener {
    void CellClick(int row, int col, JPanel cellPanel, GUIView currView); //GUIView calls this method and GameController defines it, cellPanel = panel clicked
}