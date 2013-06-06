package controller;

import gui.MainView;
import gui.SubPGChairDialog;
import gui.SubPGChairView;

import java.awt.event.ActionEvent;
import java.util.List;

import model.Paper;
import model.SubProgramChair;

public class SubPGChairController implements Controller
{
	private SubPGChairView my_view;
	private SubPGChairDialog dlg;
	private List<Paper> model;
	private SubProgramChair the_SPChair;

	public SubPGChairController(final SubPGChairView the_view, final List<Paper> the_papers)
	{
		my_view = the_view;
		model = the_papers;
		the_SPChair = my_view.getSPChair();
	}

	@Override
	public void actionPerformed(final ActionEvent the_event)
	{
		if("view_edit".equals(the_event.getActionCommand()))
		{
			MainView parent = my_view.getMainView();
			Paper paper = my_view.getSelectedRow();
			dlg = new SubPGChairDialog(parent, this, the_SPChair, paper);
			dlg.setVisible(true);
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