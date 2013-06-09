package controller;

import gui.MainView;
import gui.ReviewForm;
import gui.ReviewerView;

import java.awt.event.ActionEvent;
import java.util.List;

import model.Paper;
import model.Review;
import model.Reviewer;

public class ReviewerViewController implements Controller {
  private ReviewerView my_view;
  private ReviewForm my_form;
  private List<Paper> my_model;
  Reviewer my_reviewer;
  
  public ReviewerViewController(ReviewerView the_view, List<Paper> the_papers) {
    my_view = the_view;
    my_model = the_papers;
    my_reviewer = my_view.getReviewer();
  }
  
  @Override
  public void actionPerformed(final ActionEvent the_event) {
    if("write_review".equals(the_event.getActionCommand())) {
      final MainView parent = my_view.getMainView();
      final Paper paper = my_view.getSelectedRow();
      Review review = my_view.getReviewer().getReview(paper.getID());
      my_form = new ReviewForm(paper, my_view.getReviewer(), review);
      my_form.start();
    }
    if("Create".equals(the_event.getActionCommand()))
    {
		my_view.getTableModel().fireTableDataChanged();
    }
      
     // my_view.disableButton();
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
