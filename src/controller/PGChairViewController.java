package controller;

import gui.MainView;
import gui.PGChairDialog;
import gui.PGChairView;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.List;

import service.PaperService;

import model.Paper;
import model.ProgramChair;
import model.Role;
import model.Status;
import model.SubProgramChair;
import model.User;

public class PGChairViewController implements Controller {
  private PGChairView view;
  private PGChairDialog dlg;
  private List<Paper> model;
  ProgramChair programChair;
  
  public PGChairViewController(PGChairView aView, List<Paper> aPapers) {
    view = aView;
    model = aPapers;
    programChair = view.getPGChair();
  }
  
  @Override
  public void actionPerformed(ActionEvent event) {
    if("view_edit".equals(event.getActionCommand())) {
      MainView parent = view.getMainView();
      Paper paper = view.getSelectedRow();
      dlg = new PGChairDialog(parent, this, programChair, paper);
      dlg.setVisible(true);
    } else if("Update".equals(event.getActionCommand())) {
      Paper paper = view.getSelectedRow();
      if(programChair.canAssignDecision()) {
        Object decision = dlg.getDecision();
        if(paper != null && decision != null) { 
          paper.setAcceptanceStatus(Status.valueOf(decision.toString()));
          PaperService.getInstance().savePaper(paper);
          view.getTableModel().fireTableDataChanged();
        }
      }
      
      User p = (User) dlg.getSubProgramChair();
      if(p != null) {
        PaperService.getInstance().assignPaper(paper.getID(), 
                                               p.getID(), 
                                               programChair.getConference().getID(), 
                                               Role.SUB_PROGRAM_CHAIR);
        view.getTableModel().fireTableDataChanged();
      }
      
      view.disableButton();
      dlg.dispose();
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
