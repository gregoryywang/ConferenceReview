package controller;

import gui.MainView;
import gui.PGChairDialog;
import gui.PGChairView;
import gui.ReviewForm;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.List;

import service.PaperService;

import model.Paper;
import model.ProgramChair;
import model.Review;
import model.Role;
import model.Status;
import model.SubProgramChair;
import model.User;

public class PGChairViewController implements Controller {
  private PGChairView view;
  private PGChairDialog dlg;
  private List<Paper> model;
  ProgramChair programChair;
  private Paper current_paper;
  
  public PGChairViewController(PGChairView aView, List<Paper> aPapers) {
    view = aView;
    model = aPapers;
    programChair = view.getPGChair();
  }
  
  @Override
  public void actionPerformed(ActionEvent event) {
    if("view_edit".equals(event.getActionCommand())) {
      MainView parent = view.getMainView();
      current_paper = view.getSelectedRow();
      dlg = new PGChairDialog(parent, this, programChair, current_paper);
      dlg.setVisible(true);
    } else if("Update".equals(event.getActionCommand())) {
      current_paper = view.getSelectedRow();
      if(programChair.canAssignDecision()) {
        Object decision = dlg.getDecision();
        if(current_paper != null && decision != null) { 
          current_paper.setAcceptanceStatus(Status.valueOf(decision.toString()));
          PaperService.getInstance().savePaper(current_paper);
          view.getTableModel().fireTableDataChanged();
        }
      }
      
      User p = (User) dlg.getSubProgramChair();
      if(p != null) {
        PaperService.getInstance().assignPaper(current_paper.getID(), 
                                               p.getID(), 
                                               programChair.getConference().getID(), 
                                               Role.SUB_PROGRAM_CHAIR);
        view.getTableModel().fireTableDataChanged();
      } 
      
      view.disableButton();
      dlg.dispose();
      
    } else if("ViewReview".equals(event.getActionCommand())) {
      Review review = (Review) dlg.getReview();
      
      if( review != null ) {
        new ReviewForm(current_paper, programChair, review).start(); //needs to be subprogram chair?
      }
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
