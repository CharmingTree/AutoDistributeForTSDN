package com.java.Listener;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import com.java.UI.ConfigurationFrame;
import com.java.UI.MainFrame;
import com.java.UI.SettingFrame;
import com.java.global.GlobalConstrants;

public class MenuActionListener implements ActionListener, GlobalConstrants
{
	public MenuActionListener()
	{
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String command = MENU_ITEM_LIST.get(e.getActionCommand());
		
		switch(command)
		{
			case MENU_SERVER_SETTING_ITEM :
				for ( Frame f : JFrame.getFrames())
				{
					if (f.getClass().equals(MainFrame.class))
					{
						f.setEnabled(false);
						break;
					}
				}
				new SettingFrame();
				break;
			case MENU_CONFIGURATION_SETTING_ITEM:
				for ( Frame f : JFrame.getFrames())
				{
					if (f.getClass().equals(MainFrame.class))
					{
						f.setEnabled(false);
						break;
					}
				}
				new ConfigurationFrame();
				break;
		}
	}
	
}
