package controller;

/**
 * RevisedAuthorViewController.java
 * Controls the behavior of buttons in RevisedAuthorView
 * 
 * @author yongyuwang
 * @version 3
 * Based on code from AuthorViewController and SubPGChairViewController
 * by Roshun Jones and Danielle Tucker respectively.
 */

import gui.AuthorView;
import gui.ReviewForm;
import gui.RevisedPaperSubmissionForm;


import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import service.PaperService;
import model.Author;
import model.Paper;
import model.Review;


public class RevisedAuthorViewController implements Controller
{
	private AuthorView my_view;
	private Author the_author;

	public RevisedAuthorViewController(final AuthorView the_view)
	{
		my_view = the_view;
		the_author = my_view.getAuthor();
	}

	@Override
	public void actionPerformed(final ActionEvent the_event)
	{
		String command = the_event.getActionCommand();
		if("view_edit".equals(command))
		{
			Paper paper = my_view.getSelectedRow();
	        new RevisedPaperSubmissionForm(the_author, paper, my_view).setVisible(true); 
			my_view.getTableModel().fireTableDataChanged();
		}
		if("add_submission".equals(command))
		{
			//Launches the new paper submission form when pressed.
	        new RevisedPaperSubmissionForm(the_author, null, my_view).setVisible(true); 
			my_view.getTableModel().fireTableDataChanged();
		}
		if("view_review1".equals(command))
		{
			Paper paper = my_view.getSelectedRow();
			List<Review> reviewList = paper.getReviews();
			Review review = reviewList.get(0);
			if( review != null ) {
		        new ReviewForm(paper, the_author, review).start(); 
		    }
		}
		if("view_review2".equals(command))
		{
			Paper paper = my_view.getSelectedRow();
			List<Review> reviewList = paper.getReviews();
			Review review = reviewList.get(1);
			if( review != null ) {
		        new ReviewForm(paper, the_author, review).start(); 
		    }
		}
		if("view_review3".equals(command))
		{
			Paper paper = my_view.getSelectedRow();
			List<Review> reviewList = paper.getReviews();
			Review review = reviewList.get(2);
			if( review != null ) {
		        new ReviewForm(paper, the_author, review).start(); 
		    }
		}
		if("delete_submission".equals(command))
		{
		  Paper paper = my_view.getSelectedRow();
      PaperService.getInstance().deletePaper(paper.getID());
      my_view.getTableModel().deleteRow(paper);
      JOptionPane.showMessageDialog(new JDialog(), "Your submission has been removed.");
		}
	
	}

	@Override
	public void update(Object aObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModel(Object aObject) {
		// TODO Auto-generated method stub

	}
}