package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Paper;
import model.Review;
import model.Reviewer;
import model.User;
import service.PaperService;

public class ReviewForm extends JFrame
{	
	private User my_user;
	private Review my_review;
	private boolean my_is_new_review_flag = false;
	private Paper my_paper;
	
	public ReviewForm()
	{
		super("Review Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_user = new Reviewer(new User());
		my_review = new Review();
	}
	
	public ReviewForm(final Paper the_paper, final User the_user, final Review the_review)
	{
		super("Review Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		my_user = the_user;
		my_review = the_review;
		if (the_review.getSummaryRating() == 0)
		{
			my_is_new_review_flag = true;
		}
		my_paper = the_paper;
	}
	
	public void start()
	{
		final JPanel northern_panel = new JPanel();
		northern_panel.add(new JLabel("Reviewer: "));
		northern_panel.add(new JLabel(my_user.toString()));
		add(northern_panel, BorderLayout.NORTH);
		
		add(new ReviewerPanel(), BorderLayout.CENTER);
		
		final JPanel southern_panel = new JPanel();
		final JButton button = new JButton();
		if ("Reviewer".equals(my_user.getClass().getSimpleName()))
		{
			button.setText("Cancel");
			final JButton review_button = new JButton();
			if (my_is_new_review_flag)
			{
				review_button.setText("Create");
			}
			else
			{
				review_button.setText("Save");
			}
			review_button.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent the_event)
				{
					PaperService.getInstance().addReview(my_review, my_paper.getID());
				}
			});
		}
		else
		{
			button.setText("Ok");
		}
		
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent the_event)
			{
				if ("Cancel".equals(button.getText()))
				{
					JOptionPane.showMessageDialog(null, "No changes were made.");
				}
				dispose();
			}
		});
		
		
		pack();
		setVisible(true);
	}
	
	private class ReviewerPanel extends JPanel
	{
		private ReviewerPanel()
		{
			super();
		}
	}
}
