package controller;

import gui.RevisedPaperSubmissionForm;
import gui.TablePanel.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import model.Author;
import model.Paper;

public class RevisedPaperSubmissionController implements ActionListener {

	private Author author;

	private RevisedPaperSubmissionForm view;
	private TableModel model;

	public RevisedPaperSubmissionController(Author aAuthor, Object aView, Object aModel) {
		author = aAuthor;
		view = (RevisedPaperSubmissionForm) aView;
		model = (TableModel) aModel;
	}

	@Override 
	public void actionPerformed(final ActionEvent the_event) {
		String command = the_event.getActionCommand();
		if("SubmitNewPaper".equals(command)) {
			// initialize new paper object using information from submission form
			Paper myPaper = new Paper(author, view.titleField.getText(),
					view.keywordsField.getText(),
					view.paperAbstract.getText(),
					(String) view.catagoryField.getSelectedItem(),
					view.paperContent.getText());

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
		
		if("UpdatePaper".equals(command)) {
			Paper paper = view.getPaper();
			paper.setTitle(view.titleField.getText());
			paper.setKeywords(view.keywordsField.getText());
			paper.setCategory((String) view.catagoryField.getSelectedItem());
			try {
				author.modifyPaper(paper);
				view.dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(new JDialog(), "Your paper has been submitted.");
		}
	}
}
