package controller;

/**
 * RevisedPaperSubmissionController.java
 * Controls the behavior of buttons in RevisedPaperSubmissionForm
 * 
 * @author yongyuwang
 * @version 3
 * Based on code from PaperSubmissionController 
 * by Roshun Jones 
 */

import gui.AuthorView;
import gui.RevisedPaperSubmissionForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.Author;
import model.Paper;

public class RevisedPaperSubmissionController implements ActionListener {

	private Author author;

	private RevisedPaperSubmissionForm form_view;
	private AuthorView author_view;

	public RevisedPaperSubmissionController(Author aAuthor, Object aView, final AuthorView the_view ) {
		author = aAuthor;
		form_view = (RevisedPaperSubmissionForm) aView;
		author_view = the_view;
		
	}

	@Override 
	public void actionPerformed(final ActionEvent the_event) {
		String command = the_event.getActionCommand();
		if("SubmitNewPaper".equals(command)) {
			// initialize new paper object using information from submission form
			Paper myPaper = new Paper(author, form_view.titleField.getText(),
					form_view.keywordsField.getText(),
					form_view.paperAbstract.getText(),
					(String) form_view.catagoryField.getSelectedItem(),
					form_view.paperContent.getText());

			// saves the paper into database
			try {
				author.submitPaper(myPaper);
				form_view.dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(new JDialog(), "Your paper has been submitted.");
			author_view.getTableModel().fireTableDataChanged();
		}
		
		if("UpdatePaper".equals(command)) {
			Paper paper = form_view.getPaper();
			paper.setTitle(form_view.titleField.getText());
			paper.setKeywords(form_view.keywordsField.getText());
			paper.setCategory((String) form_view.catagoryField.getSelectedItem());
			try {
				author.modifyPaper(paper);
				form_view.dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(new JDialog(), "Your submission has been updated.");
			author_view.getTableModel().fireTableDataChanged();
		}
	}
}
