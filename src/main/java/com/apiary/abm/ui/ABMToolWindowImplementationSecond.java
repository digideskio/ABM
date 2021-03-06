package com.apiary.abm.ui;


import com.apiary.abm.entity.BodyObjectEntity;
import com.apiary.abm.entity.BodyVariableEntity;
import com.apiary.abm.entity.ProblemEntity;
import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.entity.blueprint.HeadersEntity;
import com.apiary.abm.entity.blueprint.ParametersEntity;
import com.apiary.abm.utility.ConfigPreferences;
import com.apiary.abm.utility.ProjectManager;
import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiDirectory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class ABMToolWindowImplementationSecond extends JFrame
{
	private ToolWindow mToolWindow;
	private TreeNodeEntity mEntity;
	private final ResourceBundle mMessages = ResourceBundle.getBundle("values/strings");
	private List<Pair<JLabel, BodyObjectEntity>> mAllEntityList = new ArrayList<Pair<JLabel, BodyObjectEntity>>();


	public ABMToolWindowImplementationSecond(ToolWindow toolWindow, TreeNodeEntity entity)
	{
		mToolWindow = toolWindow;
		mEntity = entity;

		Utils.trackPage("Implementation second screen");

		initLayout();
	}


	private void initLayout()
	{
		// create UI
		final JBackgroundPanel myToolWindowContent = new JBackgroundPanel("drawable/img_background.png", JBackgroundPanel.JBackgroundPanelType.BACKGROUND_REPEAT);
		final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		final Content content = contentFactory.createContent(myToolWindowContent, "", false);
		mToolWindow.getContentManager().removeAllContents(true);
		mToolWindow.getContentManager().addContent(content);

		// MIGLAYOUT ( params, columns, rows)
		// insets TOP LEFT BOTTOM RIGHT
		myToolWindowContent.setLayout(new MigLayout("insets 0, flowy, fillx, filly", "[fill, grow, center]", "[fill,top][fill, grow][fill,bottom]"));

		final JBackgroundPanel topPanel = new JBackgroundPanel("drawable/img_box_top.png", JBackgroundPanel.JBackgroundPanelType.PANEL);
		final JPanel middlePanel = new JPanel();
		final JBScrollPane middleScrollPanel = new JBScrollPane(middlePanel, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		final JBackgroundPanel bottomPanel = new JBackgroundPanel("drawable/img_box_bottom.png", JBackgroundPanel.JBackgroundPanelType.PANEL);

		topPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));
		bottomPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(20) + " " + Utils.reDimension(20) + " " + Utils.reDimension(20) + ", flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(15) + " 0 " + Utils.reDimension(15) + ", flowy, fillx, filly", "[grow, center]", "[][]" + Utils.reDimension(30) + "[][]"));
		bottomPanel.setLayout(new MigLayout("insets " + Utils.reDimension(18) + " 0 0 0, flowx, fillx, filly", "[grow][grow]", "[center, top]"));

		topPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		middleScrollPanel.setOpaque(false);
		middleScrollPanel.getViewport().setOpaque(false);
		middleScrollPanel.setBorder(BorderFactory.createEmptyBorder());
		middleScrollPanel.getVerticalScrollBar().setUnitIncrement(15);
		bottomPanel.setOpaque(false);

		myToolWindowContent.add(topPanel);
		myToolWindowContent.add(middleScrollPanel);
		myToolWindowContent.add(bottomPanel);

		// Top Panel label
		final JLabel infoText = new JLabel("<html><center>" + mMessages.getString("implementation_second_header_new") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);


		// Label response header
		final JLabel methodImplementationHeaderText = new JLabel("<html><center><br />" + mMessages.getString("implementation_second_category_method_implementation") + "</center></html>");
		methodImplementationHeaderText.setForeground(Color.WHITE);
		methodImplementationHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
		methodImplementationHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(methodImplementationHeaderText);


		// Panel method
		final JBackgroundPanel methodPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		methodPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[center]", "[]"));
		methodPanel.setOpaque(false);
		methodPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(methodPanel);

		// Label method header
		final JLabel methodHeaderText = new JLabel("<html><center>" + mEntity.getMethodName() + "</center></html>");
		methodHeaderText.setForeground(Color.WHITE);
		methodHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		methodHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
		methodPanel.add(methodHeaderText, "span 2, wrap");

		List<ProblemEntity> methodProblems = ProjectManager.checkMethodForProblems(mEntity);
		if(!methodProblems.isEmpty())
			methodHeaderText.setText("<html><center><a style=\"color: red\">" + mEntity.getMethodName() + "</a></center></html>");
		else methodHeaderText.setText("<html><center><a style=\"color: green\">" + mEntity.getMethodName() + "</a></center></html>");

		// TextArea method
		final String methodExampleString = generateMethodExample();
		final JTextArea methodTextArea = new JTextArea(methodExampleString);
		methodTextArea.setForeground(Color.WHITE);
		methodTextArea.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		methodTextArea.setOpaque(false);
		methodTextArea.setEditable(false);
		methodTextArea.setTabSize(4);

		final JBScrollPane methodScrollPanel = new JBScrollPane(methodTextArea, JBScrollPane.VERTICAL_SCROLLBAR_NEVER, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		methodScrollPanel.setOpaque(false);
		methodScrollPanel.getViewport().setOpaque(false);
		methodScrollPanel.setBorder(BorderFactory.createEmptyBorder());
		methodScrollPanel.getVerticalScrollBar().setUnitIncrement(15);
		methodScrollPanel.removeMouseWheelListener(methodScrollPanel.getMouseWheelListeners()[0]);
		methodPanel.add(methodScrollPanel, "span 2, wrap");

		final JButton methodCheckButton = new JButton(mMessages.getString("implementation_second_button_check"));
		methodCheckButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
		methodCheckButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
		methodCheckButton.setOpaque(false);
		methodPanel.add(methodCheckButton, "left");

		methodCheckButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				List<ProblemEntity> methodProblems = ProjectManager.checkMethodForProblems(mEntity);

				if(!methodProblems.isEmpty())
				{
					methodHeaderText.setText("<html><center><a style=\"color: red\">" + mEntity.getMethodName() + "</a></center></html>");

					String problemList = "";
					for(ProblemEntity problemEntity : methodProblems)
						problemList += problemEntity.getText() + "\n\n";

					JOptionPane pane = new JOptionPane(Utils.generateMessage(problemList));
					Object[] options = new String[]{mMessages.getString("global_ok")};
					pane.setOptions(options);
					JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("implementation_dialog_check_header"));
					dialog.setVisible(true);
				}
				else
					methodHeaderText.setText("<html><center><a style=\"color: green\">" + mEntity.getMethodName() + "</a></center></html>");
			}
		});

		final JButton methodCopyButton = new JButton(mMessages.getString("implementation_second_button_copy"));
		methodCopyButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
		methodCopyButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
		methodCopyButton.setOpaque(false);
		methodPanel.add(methodCopyButton, "right");

		methodCopyButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				StringSelection stringSelection = new StringSelection(methodExampleString);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			}
		});


		// Panel generate all
		final JBackgroundPanel generateAllPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		generateAllPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[center]", "[]"));
		generateAllPanel.setOpaque(false);
		generateAllPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
		middlePanel.add(generateAllPanel);

		// Label generate all header
		final JLabel generateAllHeaderText = new JLabel("<html><center>" + mMessages.getString("implementation_second_message_generate_all") + "</center></html>");
		generateAllHeaderText.setForeground(Color.WHITE);
		generateAllHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		generateAllHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
		generateAllPanel.add(generateAllHeaderText);

		final JButton entityGenerateAllButton = new JButton(mMessages.getString("implementation_second_button_generate"));
		entityGenerateAllButton.setMaximumSize(new Dimension(Utils.reDimension(180), Integer.MAX_VALUE));
		entityGenerateAllButton.setMinimumSize(new Dimension(Utils.reDimension(180), 0));
		entityGenerateAllButton.setOpaque(false);
		generateAllPanel.add(entityGenerateAllButton);

		entityGenerateAllButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Utils.trackEvent("Usage", "Generate all entities button");

				for(Pair<JLabel, BodyObjectEntity> pair : mAllEntityList)
				{
					generateEntityFile(pair.second.getEntityName(), generateEntityExample(pair.second));

					List<ProblemEntity> entityProblems = ProjectManager.checkEntityForProblems(pair.second);

					if(!entityProblems.isEmpty())
					{
						pair.first.setText("<html><center><a style=\"color: red\">" + pair.second.getEntityName() + "</a></center></html>");

						String problemList = "";
						for(ProblemEntity problemEntity : entityProblems)
							problemList += problemEntity.getText() + "\n\n";

						JOptionPane pane = new JOptionPane(Utils.generateMessage(problemList));
						Object[] options = new String[]{mMessages.getString("global_ok")};
						pane.setOptions(options);
						JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("implementation_dialog_check_header"));
						dialog.setVisible(true);
					}
					else
						pair.first.setText("<html><center><a style=\"color: green\">" + pair.second.getEntityName() + "</a></center></html>");
				}
			}
		});


		if(mEntity.getRequestBody() != null)
		{
			// Label request header
			final JLabel requestHeaderText = new JLabel("<html><center><br />" + mMessages.getString("implementation_second_category_requests_entity") + "</center></html>");
			requestHeaderText.setForeground(Color.WHITE);
			requestHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
			requestHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
			middlePanel.add(requestHeaderText);

			for(final BodyObjectEntity entity : mEntity.getRequestBody())
			{
				// Panel method
				final JBackgroundPanel entityPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
				entityPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[center]", "[]"));
				entityPanel.setOpaque(false);
				entityPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
				middlePanel.add(entityPanel);

				// Label method header
				final JLabel entityHeaderText = new JLabel("<html><center>" + entity.getEntityName() + "</center></html>");
				entityHeaderText.setForeground(Color.WHITE);
				entityHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
				entityHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
				entityPanel.add(entityHeaderText, "span 2, wrap");

				List<ProblemEntity> entityProblem = ProjectManager.checkEntityForProblems(entity);
				if(!entityProblem.isEmpty())
					entityHeaderText.setText("<html><center><a style=\"color: red\">" + entity.getEntityName() + "</a></center></html>");
				else entityHeaderText.setText("<html><center><a style=\"color: green\">" + entity.getEntityName() + "</a></center></html>");

				// TextArea method
				final String entityExampleText = generateEntityExample(entity);
				final JTextArea entityTextArea = new JTextArea(entityExampleText);
				entityTextArea.setForeground(Color.WHITE);
				entityTextArea.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
				entityTextArea.setOpaque(false);
				entityTextArea.setEditable(false);
				entityTextArea.setTabSize(4);

				final JBScrollPane scrollPanel = new JBScrollPane(entityTextArea, JBScrollPane.VERTICAL_SCROLLBAR_NEVER, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollPanel.setOpaque(false);
				scrollPanel.getViewport().setOpaque(false);
				scrollPanel.setBorder(BorderFactory.createEmptyBorder());
				scrollPanel.getVerticalScrollBar().setUnitIncrement(15);
				scrollPanel.removeMouseWheelListener(scrollPanel.getMouseWheelListeners()[0]);

				entityPanel.add(scrollPanel, "span 2, wrap");

				final JButton entityCheckButton = new JButton(mMessages.getString("implementation_second_button_check"));
				entityCheckButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
				entityCheckButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
				entityCheckButton.setOpaque(false);
				entityPanel.add(entityCheckButton, "left");

				entityCheckButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						List<ProblemEntity> entityProblems = ProjectManager.checkEntityForProblems(entity);

						if(!entityProblems.isEmpty())
						{
							entityHeaderText.setText("<html><center><a style=\"color: red\">" + entity.getEntityName() + "</a></center></html>");

							String problemList = "";
							for(ProblemEntity problemEntity : entityProblems)
								problemList += problemEntity.getText() + "\n\n";

							JOptionPane pane = new JOptionPane(Utils.generateMessage(problemList));
							Object[] options = new String[]{mMessages.getString("global_ok")};
							pane.setOptions(options);
							JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("implementation_dialog_check_header"));
							dialog.setVisible(true);
						}
						else
							entityHeaderText.setText("<html><center><a style=\"color: green\">" + entity.getEntityName() + "</a></center></html>");
					}
				});

				final JButton entityGenerateButton = new JButton(mMessages.getString("implementation_second_button_generate"));
				entityGenerateButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
				entityGenerateButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
				entityGenerateButton.setOpaque(false);
				entityPanel.add(entityGenerateButton, "right");

				entityGenerateButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Utils.trackEvent("Usage", "Generate entity button");

						generateEntityFile(entity.getEntityName(), entityExampleText);

						List<ProblemEntity> entityProblems = ProjectManager.checkEntityForProblems(entity);

						if(!entityProblems.isEmpty())
						{
							entityHeaderText.setText("<html><center><a style=\"color: red\">" + entity.getEntityName() + "</a></center></html>");

							String problemList = "";
							for(ProblemEntity problemEntity : entityProblems)
								problemList += problemEntity.getText() + "\n\n";

							JOptionPane pane = new JOptionPane(Utils.generateMessage(problemList));
							Object[] options = new String[]{mMessages.getString("global_ok")};
							pane.setOptions(options);
							JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("implementation_dialog_check_header"));
							dialog.setVisible(true);
						}
						else
							entityHeaderText.setText("<html><center><a style=\"color: green\">" + entity.getEntityName() + "</a></center></html>");
					}
				});

				mAllEntityList.add(Pair.create(entityHeaderText, entity));
			}
		}


		if(mEntity.getResponseBody() != null)
		{
			// Label response header
			final JLabel responseHeaderText = new JLabel("<html><center><br />" + mMessages.getString("implementation_second_category_response_entity") + "</center></html>");
			responseHeaderText.setForeground(Color.WHITE);
			responseHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
			responseHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
			middlePanel.add(responseHeaderText);

			for(final BodyObjectEntity entity : mEntity.getResponseBody())
			{
				// Panel response
				final JBackgroundPanel entityPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
				entityPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[center]", "[]"));
				entityPanel.setOpaque(false);
				entityPanel.setMaximumSize(new Dimension(Utils.reDimension(600), Integer.MAX_VALUE));
				middlePanel.add(entityPanel);

				// Label response header
				final JLabel entityHeaderText = new JLabel("<html><center>" + entity.getEntityName() + "</center></html>");
				entityHeaderText.setForeground(Color.WHITE);
				entityHeaderText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
				entityHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
				entityPanel.add(entityHeaderText, "span 2, wrap");

				List<ProblemEntity> entityProblem = ProjectManager.checkEntityForProblems(entity);
				if(!entityProblem.isEmpty())
					entityHeaderText.setText("<html><center><a style=\"color: red\">" + entity.getEntityName() + "</a></center></html>");
				else entityHeaderText.setText("<html><center><a style=\"color: green\">" + entity.getEntityName() + "</a></center></html>");

				// TextArea response
				final String entityExampleText = generateEntityExample(entity);
				final JTextArea entityTextArea = new JTextArea(entityExampleText);
				entityTextArea.setForeground(Color.WHITE);
				entityTextArea.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
				entityTextArea.setOpaque(false);
				entityTextArea.setEditable(false);
				entityTextArea.setTabSize(4);

				final JBScrollPane scrollPanel = new JBScrollPane(entityTextArea, JBScrollPane.VERTICAL_SCROLLBAR_NEVER, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollPanel.setOpaque(false);
				scrollPanel.getViewport().setOpaque(false);
				scrollPanel.setBorder(BorderFactory.createEmptyBorder());
				scrollPanel.getVerticalScrollBar().setUnitIncrement(15);
				scrollPanel.removeMouseWheelListener(scrollPanel.getMouseWheelListeners()[0]);

				entityPanel.add(scrollPanel, "span 2, wrap");


				final JButton entityCheckButton = new JButton(mMessages.getString("implementation_second_button_check"));
				entityCheckButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
				entityCheckButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
				entityCheckButton.setOpaque(false);
				entityPanel.add(entityCheckButton, "left");

				entityCheckButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						List<ProblemEntity> entityProblems = ProjectManager.checkEntityForProblems(entity);

						if(!entityProblems.isEmpty())
						{
							entityHeaderText.setText("<html><center><a style=\"color: red\">" + entity.getEntityName() + "</a></center></html>");

							String problemList = "";
							for(ProblemEntity problemEntity : entityProblems)
								problemList += problemEntity.getText() + "\n\n";

							JOptionPane pane = new JOptionPane(Utils.generateMessage(problemList));
							Object[] options = new String[]{mMessages.getString("global_ok")};
							pane.setOptions(options);
							JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("implementation_dialog_check_header"));
							dialog.setVisible(true);
						}
						else
							entityHeaderText.setText("<html><center><a style=\"color: green\">" + entity.getEntityName() + "</a></center></html>");
					}
				});

				final JButton entityGenerateButton = new JButton(mMessages.getString("implementation_second_button_generate"));
				entityGenerateButton.setMaximumSize(new Dimension(Utils.reDimension(120), Integer.MAX_VALUE));
				entityGenerateButton.setMinimumSize(new Dimension(Utils.reDimension(120), 0));
				entityGenerateButton.setOpaque(false);
				entityPanel.add(entityGenerateButton, "right");

				entityGenerateButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Utils.trackEvent("Usage", "Generate entity button");

						generateEntityFile(entity.getEntityName(), entityExampleText);

						List<ProblemEntity> entityProblems = ProjectManager.checkEntityForProblems(entity);

						if(!entityProblems.isEmpty())
						{
							entityHeaderText.setText("<html><center><a style=\"color: red\">" + entity.getEntityName() + "</a></center></html>");

							String problemList = "";
							for(ProblemEntity problemEntity : entityProblems)
								problemList += problemEntity.getText() + "\n\n";

							JOptionPane pane = new JOptionPane(Utils.generateMessage(problemList));
							Object[] options = new String[]{mMessages.getString("global_ok")};
							pane.setOptions(options);
							JDialog dialog = pane.createDialog(new JFrame(), mMessages.getString("implementation_dialog_check_header"));
							dialog.setVisible(true);
						}
						else
							entityHeaderText.setText("<html><center><a style=\"color: green\">" + entity.getEntityName() + "</a></center></html>");
					}
				});

				mAllEntityList.add(Pair.create(entityHeaderText, entity));
			}
		}


		// Back button
		final ImageButton buttonBack = new ImageButton();
		buttonBack.setImage("drawable/img_button_back.png");
		buttonBack.setSize(Utils.reDimension(70), Utils.reDimension(70));

		buttonBack.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				buttonBack.setImage("drawable/img_button_back_pressed.png");
				buttonBack.setSize(Utils.reDimension(70), Utils.reDimension(70));
				progress = true;

				new ABMToolWindowImplementationFirst(mToolWindow, mEntity);
			}


			public void mousePressed(MouseEvent e)
			{
				if(progress) return;
				buttonBack.setImage("drawable/img_button_back_pressed.png");
				buttonBack.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				buttonBack.setImage("drawable/img_button_back.png");
				buttonBack.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		// Next button
		final ImageButton buttonNext = new ImageButton();
		buttonNext.setImage("drawable/img_button_save.png");
		buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));

		buttonNext.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				buttonNext.setImage("drawable/img_button_save_pressed.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
				progress = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						boolean error = false;
						String errorText = "";

						ConfigPreferences configPreferences = new ConfigPreferences();
						configPreferences.saveTreeNodeEntity(mEntity);

						if(error)
						{
							progress = false;
							JOptionPane.showMessageDialog(null, Utils.generateMessage(errorText), mMessages.getString("global_error_title"), JOptionPane.ERROR_MESSAGE);
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									buttonNext.setImage("drawable/img_button_save.png");
								}
							});
						}
						else
						{
							Utils.trackEvent("Usage", "Request implemented");
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									new ABMToolWindowMain(mToolWindow);
								}
							});
						}
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(progress) return;
				buttonNext.setImage("drawable/img_button_save_pressed.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				buttonNext.setImage("drawable/img_button_save.png");
				buttonNext.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		bottomPanel.add(buttonBack, "right");
		bottomPanel.add(buttonNext, "left");

		// Scroll to top after layout init.
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				middleScrollPanel.getVerticalScrollBar().setValue(0);
			}
		});
	}


	private String generateMethodExample()
	{
		String output = "";

		// step 1) Add all request headers
		if(mEntity.getRequestHeaders() != null)
		{
			output += "@Headers(\"";
			for(HeadersEntity entity : mEntity.getRequestHeaders())
			{
				output += entity.getName() + ": " + entity.getValue() + "\",";
			}
			output = output.substring(0, output.length() - 1);
			output += ")\n";
		}

		// step 2) Add request URI
		output += "@" + mEntity.getMethod().toUpperCase() + "(\"" + mEntity.getUri() + "\")\n";

		// step 3) add method
		output += "public ";
		if(mEntity.isAsync()) output += "void ";
		else if(mEntity.getResponseBody() != null) output += mEntity.getResponseBody().get(0).getEntityName() + " ";
		else output += "Response ";

		output += mEntity.getMethodName() + "(\n";

		//		Log.d("\n1\n" + output + "\n");

		// step 4) add path and query = parameters
		// public String getWithPath(@Path("id") String id);
		if(mEntity.getParameters() != null)
		{
			for(ParametersEntity entity : mEntity.getParameters())
			{
				if(mEntity.getUri().contains(entity.getName()))
				{
					output += "\t@" + entity.getTypeOfParam() + "(\"" + entity.getName() + "\") " + Utils.firstLetterUpperCase(entity.getType()) + " param" + Utils.firstLetterUpperCase(entity.getName()) + ", \n";
				}
			}
		}

		//		Log.d("\n2 - After Parameters\n" + output + "\n");

		// add body
		// public String getWithBody(@Body String id);
		if(mEntity.getRequestBody() != null)
		{
			for(BodyObjectEntity entity : mEntity.getRequestBody())
			{
				if(entity.getSerializableName().equals("ROOT"))
				{
					output += "\t@Body " + entity.getEntityName() + " param" + Utils.firstLetterUpperCase(entity.getEntityName()) + ", \n";
					break;
				}
			}
		}

		//		Log.d("\n3 - After Request\n" + output + "\n");

		if(mEntity.isAsync()) if(mEntity.getResponseBody() != null)
			output += "\tCallback<" + BodyObjectEntity.findBySerializableName(mEntity.getResponseBody(), "ROOT").getEntityName() + "> " + mEntity.getMethodName() + "Callback, \n";
		else output += "\tCallback<Response> " + mEntity.getMethodName() + "Callback, \n";

		//		Log.d("\n4 - After Callback\n" + output + "\n");

		if(output.endsWith(", \n")) output = output.substring(0, output.length() - 3);
		output += ");";

		//		Log.d("\n5\n" + output + "\n");
		return output;
	}


	private String generateEntityExample(BodyObjectEntity entity)
	{
		String output = "";

		output += "public class " + entity.getEntityName() + "\n{";

		for(BodyVariableEntity variableEntity : entity.getVariables())
		{
			output += "\n\t@SerializedName(\"" + variableEntity.getName() + "\")\n";
			output += "\tprivate " + variableEntity.getTypeName() + " m" + Utils.firstLetterUpperCase(Utils.cleanUpString(variableEntity.getName())) + ";\n";
		}

		for(BodyVariableEntity variableEntity : entity.getVariables())
		{
			output += "\n\tpublic " + variableEntity.getTypeName() + " get" + Utils.firstLetterUpperCase(Utils.cleanUpString(variableEntity.getName())) +
					"()\n\t{\n\t\treturn m" + Utils.firstLetterUpperCase(Utils.cleanUpString(variableEntity.getName())) + ";\n\t}\n";
		}

		output += "}";
		return output;
	}


	private void generateEntityFile(String name, String text)
	{
		ConfigPreferences prefs = new ConfigPreferences();
		PsiDirectory dir = ProjectManager.getDirectory(prefs.getConfigurationEntity().getModule(), prefs.getConfigurationEntity().getEntityPackage());

		if(dir != null)
		{
			text = "package " + prefs.getConfigurationEntity().getEntityPackage() + ";\n\n" +
					"import java.util.*;\n" +
					"import java.lang.*;\n" +
					"import java.util.*;\n" +
					"import com.google.gson.annotations.SerializedName;\n\n" + text;
			ProjectManager.createFileWithContent(dir, name + ".java", text);
		}

	}
}
