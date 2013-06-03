package controller;

import gui.PaperSubmissionForm;
import gui.TablePanel.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.Author;
import model.Paper;

public class PaperSubmissionController implements ActionListener {

  private Author author;
  
  private PaperSubmissionForm view;
  private TableModel model;
  
  public PaperSubmissionController(Author aAuthor, Object aView, Object aModel) {
    author = aAuthor;
    view = (PaperSubmissionForm) aView;
    model = (TableModel) aModel;
  }
  
  @Override 
  public void actionPerformed(final ActionEvent the_event) {
  // initialize new paper object using information from submission form
  Paper myPaper = new Paper(author, view.titleField.getText(),
       view.keywordsField.getText(),
        "",//view.paperAbstract.getText(),
        (String) view.catagoryField.getSelectedItem(),
      ""); // view.paperContent.getText());
  
  // saves the paper into database
  try {
    author.submitPaper(myPaper);
    model.addRow(myPaper);
    view.dispose();
  } catch (Exception e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
  }
  
  JOptionPane.showMessageDialog(new JDialog(), "Your paper has been submitted.");
    
    }
}
