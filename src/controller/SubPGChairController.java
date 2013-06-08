package controller;

import gui.MainView;
import gui.SubPGChairDialog;
import gui.SubPGChairView;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import model.Paper;
import model.Recommendation;
import model.Reviewer;
import model.Role;
import model.SubProgramChair;
import model.User;
import service.PaperService;

/**
 * Controller for actions associated with a SubProgram Chair
 * @author Roshun (template)
 * @author Danielle Tucker
 * @version 2013 Spring
 */
public class SubPGChairController implements Controller
{
	/**
	 * The view.
	 */
	private SubPGChairView my_view;
	
	/**
	 * The dialog box
	 */
	private SubPGChairDialog dlg;
	
	/**
	 * The model (papers)
	 */
	private List<Paper> model;
	
	/**
	 * The subprogram chair user
	 */
	private SubProgramChair the_SPChair;

	/**
	 * Constructor
	 * @param the_view the view
	 * @param the_papers the list of papers for this subProgram Chair
	 */
	public SubPGChairController(final SubPGChairView the_view, final List<Paper> the_papers)
	{
		my_view = the_view;
		model = the_papers;
		the_SPChair = my_view.getSPChair();
	}

	/**
	 * Directs the actions for the View/Edit button, Save Recommendation
	 * Button and Save Reviewers Button
	 * @param the_event the action event (button press)
	 */
	@Override
	public void actionPerformed(final ActionEvent the_event)
	{
		String command = the_event.getActionCommand();
		if("view_edit".equals(command))
		{
			MainView parent = my_view.getMainView();
			Paper paper = my_view.getSelectedRow();
			dlg = new SubPGChairDialog(parent, this, the_SPChair, paper);
			dlg.setVisible(true);
		}
		if("save_rec".equals(command))
		{
			Paper paper = my_view.getSelectedRow();
			Recommendation rec = new Recommendation(the_SPChair, dlg.getRating(), dlg.getComments());
			PaperService.getInstance().addRecommendation(rec, paper);
			my_view.getTableModel().fireTableDataChanged();
			dlg.dispose();			
		}
		if("save_rev".equals(command))
		{
			List<User> reviewers = dlg.getAssignedReviewer();
			if(reviewers.size() != 3)
			{
				JOptionPane.showMessageDialog(dlg.getParent(),
						"You need to choose 3 Reviewers");
			}
			else
			{
				Paper paper = my_view.getSelectedRow();
				for(User reviewer: reviewers)
				{
					PaperService.getInstance().assignPaper(paper.getID(), reviewer.getID(), 
							the_SPChair.getConference().getID(), Role.REVIEWER);
					my_view.getTableModel().fireTableDataChanged();
					dlg.dispose();
				}
			}
		}
	}

	/**
	 * Does nothing
	 */
	@Override
	public void update(Object aObject) {
		// TODO Auto-generated method stub

	}

	/**
	 * Does nothing
	 */
	@Override
	public void setModel(Object aObject) {
		// TODO Auto-generated method stub

	}
}