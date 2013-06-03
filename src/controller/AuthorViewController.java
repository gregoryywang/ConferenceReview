package controller;

import gui.PaperSubmissionForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import model.Author;
import model.Paper;
import service.PaperService;

public class AuthorViewController implements Controller, ActionListener {
  private Author author;
  private AbstractTableModel model;
  
  
  public AuthorViewController(final Author aAuthor) {
    author = aAuthor;
  }
  
  public void update(Object aObject) {
    if( aObject instanceof Paper )
      PaperService.getInstance().savePaper((Paper) aObject);
  }
  
  public void setModel(Object aModel) {
    model = (AbstractTableModel) aModel;
  }
  
  @Override
  public void actionPerformed(ActionEvent event) {
    if( event.getSource() instanceof JButton ) 
      if( "Add Submission".equals(event.getActionCommand()) )
        new PaperSubmissionForm(author,  model).setVisible(true); //Launches the new paper submission form when pressed.
  }
}
