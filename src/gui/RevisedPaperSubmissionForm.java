
package gui;

/**
 * RevisedPaperSubmissionForm.java
 * Displays an new paper submission form
 * 
 * @author yongyuwang
 * @version 3
 * Based on PaperSubmissionForm V.2
 * Created by yongyuwang, edited by Roshun Jones and Danielle Tucker
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import controller.AuthorViewController;
import controller.Controller;
import controller.PaperSubmissionController;
import controller.RevisedPaperSubmissionController;

import model.Author;
import model.Paper;
import model.User;

public class RevisedPaperSubmissionForm extends JFrame {

	/**
	 * The default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The default background color.
	 */
	private static final Color BACKGROUND_COLOR = Color.WHITE;

	/**
	 * The default frame width.
	 */
	private static final int FRAME_WIDTH = 300;

	/**
	 * The default frame height.
	 */
	private static final int FRAME_HEIGHT = 300;

	private Author user;

	private RevisedPaperSubmissionController controller;


	public final JLabel nameLabel, name, titleLabel, keywordsLabel, catagoryLabel;
	public final JTextField titleField, keywordsField/* , catagoryField */;
	public final JComboBox catagoryField;
	public final BackgroundTextArea paperAbstract, paperContent;

	final JButton submitButton, updateButton, cancelButton;
	final JPanel topPanel, midPanel, bottemPanel;

	Paper paper;
	boolean isNewSubmission = true;

	public RevisedPaperSubmissionForm(final Author the_user, Paper the_paper) {
		
		super("Paper Submission");
		this.user = the_user;
		controller = new RevisedPaperSubmissionController(user, this);

		// check if creating a new paper or editing an existing paper
		if(paper != null) {
			isNewSubmission = false;
			paper = the_paper;
		}



		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		nameLabel = new JLabel("Your name: ");
		name = new JLabel(firstName + " " + lastName);

		titleLabel = new JLabel("Submission title:");
		titleField = new JTextField(15);
		if (!isNewSubmission) {
			titleField.setText(paper.getTitle());
		}

		keywordsLabel = new JLabel("Keywords:");
		keywordsField = new JTextField(15);
		if (!isNewSubmission) {
			keywordsField.setText(paper.getKeywords());
		}

		catagoryLabel = new JLabel("Catagory:");
		catagoryField = new JComboBox();
		ComboBoxModel categories =
				new DefaultComboBoxModel(user.getConference().getCategories().toArray());
		catagoryField.setModel(categories);
		if (!isNewSubmission) {
			catagoryField.setSelectedItem(paper.getCategory());
		}

		topPanel = new JPanel(new GridLayout(4, 2));

		topPanel.add(nameLabel);
		topPanel.add(name);

		topPanel.add(titleLabel);
		topPanel.add(titleField);

		topPanel.add(keywordsLabel);
		topPanel.add(keywordsField);

		topPanel.add(catagoryLabel);
		topPanel.add(catagoryField);

		paperAbstract = new BackgroundTextArea("Paste or type your abstract here.");
		paperAbstract.setWrapStyleWord(true);
		JScrollPane abstractScrollPane = new JScrollPane(paperAbstract);

		paperContent = new BackgroundTextArea("Paste or type your paper here.");
		paperContent.setWrapStyleWord(true);
		JScrollPane contentScrollPane = new JScrollPane(paperContent);

		midPanel = new JPanel(new GridLayout(2, 1));
		midPanel.add(abstractScrollPane);
		midPanel.add(contentScrollPane);

		submitButton = new JButton("Submit");
		submitButton.setActionCommand("SubmitNewPaper");
		submitButton.addActionListener(controller); 

		updateButton = new JButton("Update");
		updateButton.setActionCommand("UpdatePaper");
		updateButton.addActionListener(controller); 

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent the_event)
			{
				dispose();
			}
		});

		bottemPanel = new JPanel();
		bottemPanel.add(submitButton);
		bottemPanel.add(cancelButton);

		// Update button only appears if editing existing submission
		if (!isNewSubmission) {
			bottemPanel.add(updateButton);
		}

		// Disables update button if deadline is passed
		if(!user.canSubmitOrModify()) {
			updateButton.setEnabled(false);
		}

		add(topPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(bottemPanel, BorderLayout.SOUTH);

		setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		setBackground(BACKGROUND_COLOR);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Paper getPaper() {
		return paper;
	}

	public static void main(String[] args) {
		User test = new User();
		//new PaperSubmissionForm(test2).setVisible(true);
	}

	public class BackgroundTextArea extends JTextArea implements FocusListener {

		/**
		 * Default serial ID
		 */
		private static final long serialVersionUID = 1L;

		private final String backgroundText;

		public BackgroundTextArea(final String backgroundText) {
			super(backgroundText);
			this.backgroundText = backgroundText;
			super.addFocusListener(this);
		}

		@Override
		public void focusGained(FocusEvent e) {
			if (this.getText().isEmpty()) {
				super.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (this.getText().isEmpty()) {
				super.setText(backgroundText);
			}
		}

		@Override
		public String getText() {
			String typed = super.getText();
			return typed.equals(backgroundText) ? "" : typed;
		}
	}  
}


