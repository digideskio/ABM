package com.apiary.abm.ui;

import com.apiary.abm.entity.blueprint.MetadataEntity;
import com.apiary.abm.entity.config.ConfigConfigurationEntity;
import com.apiary.abm.enums.ConnectionTypeEnum;
import com.apiary.abm.utility.ConfigPreferences;
import com.apiary.abm.utility.Preferences;
import com.apiary.abm.utility.ProjectManager;
import com.apiary.abm.utility.Utils;
import com.apiary.abm.view.ImageButton;
import com.apiary.abm.view.JBackgroundPanel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ABMToolWindowConfiguration extends JFrame
{
	private ToolWindow mToolWindow;
	private ConfigPreferences mConfigPreferences = new ConfigPreferences();
	private ConfigConfigurationEntity mConfigurationEntity = mConfigPreferences.getConfigurationEntity();
	private List<String> mModuleList = new ArrayList<String>();
	private JTextArea mMethodTextArea = null;


	public ABMToolWindowConfiguration(ToolWindow toolWindow)
	{
		mToolWindow = toolWindow;

		Utils.trackPage("Configuration screen");

		checkValues();
		initLayout();
	}


	private void initLayout()
	{
		final ResourceBundle messages = ResourceBundle.getBundle("values/strings");

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
		final JBScrollPane middleScrollPanel = new JBScrollPane(middlePanel, JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		final JBackgroundPanel bottomPanel = new JBackgroundPanel("drawable/img_box_bottom.png", JBackgroundPanel.JBackgroundPanelType.PANEL);

		topPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));
		bottomPanel.setMinimumSize(new Dimension(0, Utils.reDimension(90)));

		topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));
		bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Utils.reDimension(90)));

		// add elements
		topPanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(20) + " " + Utils.reDimension(20) + " " + Utils.reDimension(20) + ", flowy, fillx, filly", "[fill, grow]", "[fill]"));
		middlePanel.setLayout(new MigLayout("insets 0 " + Utils.reDimension(15) + " 0 " + Utils.reDimension(15) + ", flowy, fillx, filly", "[fill, grow]", "[]" + Utils.reDimension(20) + "[]" + Utils.reDimension(20) + "[]" + Utils.reDimension(20) + "[]" + Utils.reDimension(20) + "[]"));
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

		// Connect label
		final JLabel infoText = new JLabel("<html><center>" + messages.getString("configuration_header") + "</center></html>");
		infoText.setForeground(Color.WHITE);
		infoText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_LARGE)));
		infoText.setHorizontalAlignment(SwingConstants.CENTER);
		topPanel.add(infoText);


		// Panel host
		final JBackgroundPanel hostPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		hostPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill,grow]", "[]"));
		hostPanel.setOpaque(false);
		middlePanel.add(hostPanel);

		// Label host
		final JLabel hostLabel = new JLabel("<html><center>" + messages.getString("configuration_message_host") + " " + mConfigurationEntity.getHost() + "</center></html>");
		hostLabel.setForeground(Color.WHITE);
		hostLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		hostLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hostPanel.add(hostLabel);

		// Panel module
		final JBackgroundPanel modulePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		modulePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow]", "[]"));
		modulePanel.setOpaque(false);
		middlePanel.add(modulePanel);

		// Label module
		final JLabel moduleLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_module") + "</center></html>");
		moduleLabel.setForeground(Color.WHITE);
		moduleLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		moduleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		modulePanel.add(moduleLabel);

		// ComboBox module
		mModuleList.add(0, "");
		final JComboBox moduleComboBox = new ComboBox(mModuleList.toArray());
		moduleComboBox.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		moduleComboBox.setSelectedItem(mConfigurationEntity.getModule());
		UIManager.put("ComboBox.selectionBackground", Color.darkGray);
		UIManager.put("ComboBox.selectionForeground", Color.WHITE);
		UIManager.put("ComboBox.background", Color.darkGray);
		UIManager.put("ComboBox.foreground", Color.WHITE);
		moduleComboBox.updateUI();
		modulePanel.add(moduleComboBox);


		// Panel Interface file
		final JBackgroundPanel interfaceClassPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		interfaceClassPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][][]", "[]"));
		interfaceClassPanel.setOpaque(false);
		middlePanel.add(interfaceClassPanel);

		// Label Interface file
		final JLabel interfaceClassLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_interface_red") + "</center></html>");
		interfaceClassLabel.setForeground(Color.WHITE);
		interfaceClassLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		interfaceClassLabel.setHorizontalAlignment(SwingConstants.CENTER);
		List<PsiClass> psiClassInterface = ProjectManager.getClasses(mConfigurationEntity.getModule(), mConfigurationEntity.getInterfaceClass());
		if(psiClassInterface != null && psiClassInterface.size() > 0)
			interfaceClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_interface_green") + "</center></html>");
		interfaceClassPanel.add(interfaceClassLabel);

		// TextField Interface file
		final JTextField interfaceClassTextField = new JTextField();
		interfaceClassTextField.setBackground(Color.darkGray);
		interfaceClassTextField.setForeground(Color.WHITE);
		interfaceClassTextField.setBorder(BorderFactory.createLineBorder(Color.gray));
		interfaceClassTextField.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		interfaceClassTextField.setText(mConfigurationEntity.getInterfaceClass());
		interfaceClassTextField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char ch = e.getKeyChar();
				if(!Character.isAlphabetic(ch)) e.consume();
			}
		});
		interfaceClassTextField.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				check();
			}


			public void removeUpdate(DocumentEvent e)
			{
				check();
			}


			public void insertUpdate(DocumentEvent e)
			{
				check();
			}


			public void check()
			{
				String exampleText = messages.getString("configuration_message_api_manager_note_example");
				exampleText = exampleText.replaceAll("<API_URL_HERE>", mConfigurationEntity.getHost());
				exampleText = exampleText.replaceAll("<INTERFACE_CLASS>", interfaceClassTextField.getText());
				exampleText = exampleText.replaceAll("<INTERFACE_CLASS_SMALL>", "instance");
				if(mMethodTextArea != null) mMethodTextArea.setText(exampleText);
			}
		});
		interfaceClassPanel.add(interfaceClassTextField);

		// Button Interface file
		final JButton interfaceClassButton = new JButton("<html><center>" + messages.getString("configuration_button_check") + "</center></html>");
		interfaceClassButton.setOpaque(false);
		//		interfaceClassButton.setForeground(Color.WHITE);
		interfaceClassButton.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		interfaceClassButton.setHorizontalAlignment(SwingConstants.CENTER);
		interfaceClassPanel.add(interfaceClassButton, "wrap");

		// Label Interface file note
		final JLabel interfaceClassNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_interface_note") + "</center></html>");
		interfaceClassNoteLabel.setForeground(Color.WHITE);
		interfaceClassNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		interfaceClassNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		interfaceClassPanel.add(interfaceClassNoteLabel, "span, grow");


		// Panel Entity package
		final JBackgroundPanel entityPackagePanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		entityPackagePanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[][fill,grow][][]", "[]"));
		entityPackagePanel.setOpaque(false);
		middlePanel.add(entityPackagePanel);

		// Label Entity package
		final JLabel entityPackageLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_entity_red") + "</center></html>");
		entityPackageLabel.setForeground(Color.WHITE);
		entityPackageLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		entityPackageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PsiPackage pack = ProjectManager.getPackages(mConfigurationEntity.getModule(), mConfigurationEntity.getEntityPackage());
		if(pack != null)
			entityPackageLabel.setText("<html><center>" + messages.getString("configuration_message_api_entity_green") + "</center></html>");
		entityPackagePanel.add(entityPackageLabel);

		// TextField Entity package
		final JTextField entityPackageTextField = new JTextField();
		entityPackageTextField.setBackground(Color.darkGray);
		entityPackageTextField.setForeground(Color.WHITE);
		entityPackageTextField.setBorder(BorderFactory.createLineBorder(Color.gray));
		entityPackageTextField.setMinimumSize(new Dimension(Utils.reDimension(160), 0));
		entityPackageTextField.setText(mConfigurationEntity.getEntityPackage());
		entityPackageTextField.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char ch = e.getKeyChar();
				if(ch != '.' && !Character.isAlphabetic(ch)) e.consume();
			}
		});
		entityPackagePanel.add(entityPackageTextField);

		// Button Entity package
		final JButton entityPackageButton = new JButton("<html><center>" + messages.getString("configuration_button_check") + "</center></html>");
		entityPackageButton.setOpaque(false);
		//		entityPackageButton.setForeground(Color.WHITE);
		entityPackageButton.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		entityPackageButton.setHorizontalAlignment(SwingConstants.CENTER);
		entityPackagePanel.add(entityPackageButton, "wrap");

		// Label Entity package
		final JLabel packageEntityNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_entity_note") + "</center></html>");
		packageEntityNoteLabel.setForeground(Color.WHITE);
		packageEntityNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
		packageEntityNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		entityPackagePanel.add(packageEntityNoteLabel, "span, grow");


		// Panel reset
		final JBackgroundPanel resetPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		resetPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowx, fillx, filly", "[fill, grow][fill, grow]", "[]"));
		resetPanel.setOpaque(false);
		middlePanel.add(resetPanel);

		// Label reset
		final JLabel resetText = new JLabel("<html><center>" + messages.getString("configuration_message_reset") + "</center></html>");
		resetText.setForeground(Color.WHITE);
		resetText.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		resetText.setHorizontalAlignment(SwingConstants.CENTER);
		resetPanel.add(resetText);

		// Button reset
		final JButton resetButton = new JButton(messages.getString("configuration_button_reset"));
		resetButton.setMaximumSize(new Dimension(Utils.reDimension(150), Integer.MAX_VALUE));
		resetButton.setMinimumSize(new Dimension(Utils.reDimension(150), 0));
		resetButton.setOpaque(false);
		resetPanel.add(resetButton, "wrap");

		// Label reset note
		final JLabel resetNoteText = new JLabel("<html><center>" + messages.getString("configuration_message_reset_note") + "</center></html>");
		resetNoteText.setForeground(Color.WHITE);
		resetNoteText.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		resetNoteText.setHorizontalAlignment(SwingConstants.CENTER);
		resetPanel.add(resetNoteText, "span 2");


		// Panel Manager
		final JBackgroundPanel managerClassPanel = new JBackgroundPanel("drawable/img_background_panel.9.png", JBackgroundPanel.JBackgroundPanelType.NINE_PATCH);
		managerClassPanel.setLayout(new MigLayout("insets " + Utils.reDimension(12) + " " + Utils.reDimension(12) + " " + Utils.reDimension(18) + " " + Utils.reDimension(19) + ", flowy, fillx, filly", "[fill,grow,center]", "[]" + Utils.reDimension(20) + "[]"));
		managerClassPanel.setOpaque(false);
		middlePanel.add(managerClassPanel);

		// Label Manager note
		final JLabel managerClassNoteLabel = new JLabel("<html><center>" + messages.getString("configuration_message_api_manager_note") + "</center></html>");
		managerClassNoteLabel.setForeground(Color.WHITE);
		managerClassNoteLabel.setFont(new Font("Ariel", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM)));
		managerClassNoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		managerClassPanel.add(managerClassNoteLabel);

		String exampleText = messages.getString("configuration_message_api_manager_note_example");
		exampleText = exampleText.replaceAll("<API_URL_HERE>", mConfigurationEntity.getHost());
		exampleText = exampleText.replaceAll("<INTERFACE_CLASS>", mConfigurationEntity.getInterfaceClass());
		exampleText = exampleText.replaceAll("<INTERFACE_CLASS_SMALL>", "instance");

		mMethodTextArea = new JTextArea(exampleText);
		mMethodTextArea.setForeground(Color.WHITE);
		mMethodTextArea.setFont(new Font("Ariel", Font.PLAIN, Utils.fontSize(Utils.FONT_SMALL)));
		mMethodTextArea.setOpaque(false);
		mMethodTextArea.setEditable(false);
		mMethodTextArea.setTabSize(4);

		final JBScrollPane methodScrollPanel = new JBScrollPane(mMethodTextArea, JBScrollPane.VERTICAL_SCROLLBAR_NEVER, JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		methodScrollPanel.setOpaque(false);
		methodScrollPanel.getViewport().setOpaque(false);
		methodScrollPanel.setBorder(BorderFactory.createEmptyBorder());
		methodScrollPanel.getVerticalScrollBar().setUnitIncrement(15);
		methodScrollPanel.removeMouseWheelListener(methodScrollPanel.getMouseWheelListeners()[0]);

		managerClassPanel.add(methodScrollPanel);

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

				new ABMToolWindowMain(mToolWindow);
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

		// Save button
		final ImageButton buttonSave = new ImageButton();
		buttonSave.setImage("drawable/img_button_save.png");
		buttonSave.setSize(Utils.reDimension(70), Utils.reDimension(70));

		buttonSave.addMouseListener(new MouseAdapter()
		{
			private boolean progress;


			public void mouseClicked(MouseEvent e)
			{
				if(progress) return;
				buttonSave.setImage("drawable/img_button_save_pressed.png");
				buttonSave.setSize(Utils.reDimension(70), Utils.reDimension(70));
				progress = true;

				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						Utils.trackEvent("Usage", "Configuration saved");

						mConfigurationEntity.setModule((String) moduleComboBox.getSelectedItem());
						mConfigurationEntity.setHost(mConfigurationEntity.getHost());
						mConfigurationEntity.setInterfaceClass(interfaceClassTextField.getText());
						mConfigurationEntity.setEntityPackage(entityPackageTextField.getText());
						mConfigPreferences.saveConfigurationEntity(mConfigurationEntity);

						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								new ABMToolWindowMain(mToolWindow);
							}
						});
					}
				});
				t.start();
			}


			public void mousePressed(MouseEvent e)
			{
				if(progress) return;
				buttonSave.setImage("drawable/img_button_save_pressed.png");
				buttonSave.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}


			public void mouseReleased(MouseEvent e)
			{
				if(progress) return;
				buttonSave.setImage("drawable/img_button_save.png");
				buttonSave.setSize(Utils.reDimension(70), Utils.reDimension(70));
			}
		});

		bottomPanel.add(buttonBack, "right");
		bottomPanel.add(buttonSave, "left");


		interfaceClassButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				List<PsiClass> cl = ProjectManager.getClasses((String) moduleComboBox.getSelectedItem(), interfaceClassTextField.getText());

				if(cl != null && cl.size() > 0)
					interfaceClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_interface_green") + "</center></html>");
				else
					interfaceClassLabel.setText("<html><center>" + messages.getString("configuration_message_api_interface_red") + "</center></html>");
			}
		});

		entityPackageButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				PsiPackage pack = ProjectManager.getPackages((String) moduleComboBox.getSelectedItem(), entityPackageTextField.getText());
				if(pack != null)
					entityPackageLabel.setText("<html><center>" + messages.getString("configuration_message_api_entity_green") + "</center></html>");
				else
					entityPackageLabel.setText("<html><center>" + messages.getString("configuration_message_api_entity_red") + "</center></html>");
			}
		});

		resetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane pane = new JOptionPane(Utils.generateMessage(messages.getString("configuration_dialog_message_reset")));
				Object[] options = new String[]{messages.getString("global_yes"), messages.getString("global_no")};
				pane.setOptions(options);
				JDialog dialog = pane.createDialog(new JFrame(), messages.getString("configuration_dialog_message_reset_header"));
				dialog.setVisible(true);
				Object obj = pane.getValue();
				int result = -1;
				for(int k = 0; k < options.length; k++)
					if(options[k].equals(obj)) result = k;
				if(result == 0)
				{
					Utils.trackEvent("Usage", "Reset plugin");

					ConfigPreferences.removeConfigFile();
					Preferences prefs = new Preferences();
					prefs.setBlueprintConnectionDocKey("");
					prefs.setBlueprintConnectionPath("");
					prefs.setBlueprintTmpFileLocation("");
					prefs.setBlueprintConnectionType(ConnectionTypeEnum.CONNECTION_TYPE_NONE);
					prefs.setBlueprintJsonTmpFileLocation("");

					SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							new ABMToolWindowWelcome(mToolWindow);
						}
					});
				}
			}
		});
	}


	private void checkValues()
	{
		if(mConfigurationEntity.getHost().equals(""))
		{
			try
			{
				Preferences prefs = new Preferences();
				String json = Utils.readFileAsString(prefs.getBlueprintJsonTmpFileLocation(), Charset.forName("UTF-8"));
				List<MetadataEntity> metadataList = Utils.parseJsonBlueprint(json).getAst().getMetadata();
				for(MetadataEntity metadata : metadataList)
				{
					if(metadata.getName().equals("HOST"))
					{
						mConfigurationEntity.setHost(metadata.getValue());
						break;
					}
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}

		for(Module module : ProjectManager.getAllModules())
		{
			mModuleList.add(module.getName());
		}
	}
}
