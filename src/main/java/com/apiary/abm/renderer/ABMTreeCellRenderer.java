package com.apiary.abm.renderer;

import com.apiary.abm.entity.TreeNodeEntity;
import com.apiary.abm.utility.Utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;


public class ABMTreeCellRenderer extends JLabel implements TreeCellRenderer
{
	public ABMTreeCellRenderer()
	{
	}


	@SuppressWarnings("unchecked")
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		final ResourceBundle colors = ResourceBundle.getBundle("values/colors");

		// Find out which node we are rendering and get its text
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		TreeNodeEntity entity = (TreeNodeEntity) node.getUserObject();

		// Format cell and add custom text to the cell
		setFont(new Font("Arial", Font.BOLD, 14));
		setForeground(Color.WHITE);

		switch(entity.getTreeNodeType())
		{
			case CONFIGURATION_PROBLEM_ROOT:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
				setForeground(Color.decode(colors.getString("text_red_dark")));
				setText(entity.getText());
				break;
			case BLUEPRINT_PROBLEM_ROOT:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
				setForeground(Color.decode(colors.getString("text_red_dark")));
				setText(entity.getText() + " (" + entity.getValue() + ")");
				break;
			case NOT_IMPLEMENTED_ROOT:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
				setForeground(Color.decode(colors.getString("text_orange_dark")));
				setText(entity.getText() + " (" + entity.getValue() + ")");
				break;
			case MODIFIED_ROOT:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
				setForeground(Color.decode(colors.getString("text_orange_dark")));
				setText(entity.getText() + " (" + entity.getValue() + ")");
				break;
			case REMOVED_ROOT:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
				setForeground(Color.decode(colors.getString("text_green_dark")));
				setText(entity.getText() + " (" + entity.getValue() + ")");
				break;
			case HIDDEN_ROOT:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_MEDIUM_LARGE)));
				setForeground(Color.decode(colors.getString("text_grey_dark")));
				setText(entity.getText() + " (" + entity.getValue() + ")");
				break;


			case CONFIGURATION_PROBLEM:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
				setForeground(Color.decode(colors.getString("text_red_light")));
				setText(entity.getText());
				break;
			case BLUEPRINT_PROBLEM:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
				setForeground(Color.decode(colors.getString("text_red_light")));
				setText(entity.getText());
				break;
			case NOT_IMPLEMENTED:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
				setForeground(Color.decode(colors.getString("text_orange_light")));
				setText("Method: " + entity.getMethod() + "   URI: " + entity.getUri());
				break;
			case MODIFIED:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
				setForeground(Color.decode(colors.getString("text_orange_light")));
				setText("Method: " + entity.getMethod() + "   URI: " + entity.getUri());
				break;
			case REMOVED:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
				setForeground(Color.decode(colors.getString("text_green_light")));
				setText("Method: " + entity.getMethod() + "   URI: " + entity.getUri());
				break;
			case HIDDEN:
				setFont(new Font("Arial", Font.BOLD, Utils.fontSize(Utils.FONT_SMALL)));
				setForeground(Color.decode(colors.getString("text_grey_light")));
				setText("Method: " + entity.getMethod() + "   URI: " + entity.getUri());
				break;
		}
		return this;
	}
}