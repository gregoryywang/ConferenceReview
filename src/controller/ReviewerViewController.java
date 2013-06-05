package controller;

import gui.MainView;
import gui.ReviewerDialog;
import gui.ReviewerView;

import java.awt.event.ActionEvent;
import java.util.List;

import model.Paper;
import model.Reviewer;
import model.Role;
import model.Status;
import model.User;
import service.PaperService;

public class ReviewerViewController implements Controller {
  private ReviewerView my_view;
  private ReviewerDialog my_dialog;
  private List<Paper> my_model;
  Reviewer my_reviewer;
  
  public ReviewerViewController(ReviewerView the_view, List<Paper> the_papers) {
    my_view = the_view;
    my_model = the_papers;
    my_reviewer = my_view.getReviewer();
  }
  
  @Override
  public void actionPerformed(final ActionEvent the_event) {
    if("view_edit".equals(the_event.getActionCommand())) {
      final MainView parent = my_view.getMainView();
      final Paper paper = my_view.getSelectedRow();
      my_dialog = new ReviewerDialog(parent, this, my_reviewer, paper);
      my_dialog.setVisible(true);
    } else if("Update".equals(the_event.getActionCommand())) {
      Paper paper = my_view.getSelectedRow();
      if(my_reviewer.canAddReview()) {
        Object decision = my_dialog.getDecision();
        if(paper != null && decision != null) { 
          paper.setAcceptanceStatus(Status.valueOf(decision.toString()));
          PaperService.getInstance().savePaper(paper);
          my_view.getTableModel().fireTableDataChanged();
        }
      }
      
      User reviewer = (User) my_dialog.getReviewer();
      if(reviewer != null) {
        PaperService.getInstance().assignPaper(paper.getID(), 
                                               reviewer.getID(), 
                                               my_reviewer.getConference().getID(), 
                                               Role.REVIEWER);
        my_view.getTableModel().fireTableDataChanged();
      }
      
      my_view.disableButton();
      my_dialog.dispose();
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
