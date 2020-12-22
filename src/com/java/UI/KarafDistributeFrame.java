package com.java.UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.beanutils.converters.StringArrayConverter;

import com.java.global.GlobalConstrants;

public class KarafDistributeFrame extends JFrame implements ActionListener, GlobalConstrants, MouseListener
{
	JTabbedPane tap;
	JTextArea userInputArea;
	JTextArea parsingResultArea;
	JTextArea createdRmCommandArea;
	
	JPanel topPanel;
	JButton parserBtn;
	JButton clearBtn;
	JTextField cachePathLabel;
	
	ArrayList<String> bundleCacheNumList;
	
	public KarafDistributeFrame()
	{
		setTitle("Karaf Distribute");
		setLayout(new BorderLayout());
		
		tap = new JTabbedPane();
		
		userInputArea = new JTextArea(50, 150);
		JScrollPane userInputScroller = new JScrollPane(userInputArea);
		
		parsingResultArea = new JTextArea(50, 150);
		parsingResultArea.setEditable(false);
		JScrollPane parsingResultScroller = new JScrollPane(parsingResultArea);
		
		createdRmCommandArea = new JTextArea(50, 150);
		createdRmCommandArea.setEditable(false);
		createdRmCommandArea.addMouseListener(this);
		JScrollPane createdRmCommandScroller = new JScrollPane(createdRmCommandArea);
		
		tap.add("입력", userInputScroller);
		tap.add("파싱 결과", parsingResultScroller);
		tap.add("생성 명령어", createdRmCommandScroller);
		
		
		add(tap, BorderLayout.CENTER);
		
		topPanel = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		topPanel.setLayout(fl);
		parserBtn = new JButton("Parsing");
		parserBtn.addActionListener(this);
		topPanel.add(parserBtn);
		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(this);
		topPanel.add(clearBtn);
		cachePathLabel = new JTextField();
		cachePathLabel.setText(KARAF_CACHE_DIRECTORY_PATH);
		cachePathLabel.setEditable(false);
		cachePathLabel.setFont(new Font("Serif", Font.BOLD, 12));
		cachePathLabel.addActionListener(this);
		cachePathLabel.addMouseListener(this);

		topPanel.add(cachePathLabel);
		
		add(topPanel, BorderLayout.NORTH);
		
		
		
		setSize(650,700);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		Image img = new ImageIcon(APPLICATION_ICON_IMAGE_PATH).getImage();
		setIconImage(img);
		for ( Frame f : JFrame.getFrames())
		{
			if (f.getClass().equals(MainFrame.class))
			{
				setLocationRelativeTo(f);
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource().equals(parserBtn))
		{
			userInputArea.scrollRectToVisible(getBounds());
			bundleCacheNumList = new ArrayList<String>();
			System.out.println(userInputArea.getText());
			
			Pattern pattern = Pattern.compile("([0-9]{4})+[\\w|\\s].+kt-TSDN-[\\w].*");
			Matcher matcher = pattern.matcher(userInputArea.getText());
			StringBuilder parsingStr = new StringBuilder("");
			while (matcher.find())
			{
				System.out.println("find!");
				System.out.println(matcher.group(0));
				parsingStr.append(matcher.group(0)+"\n");
				bundleCacheNumList.add("rm -rf "+matcher.group(1)+"\n");
			}
			parsingResultArea.setText("");
			parsingResultArea.setText(parsingStr.toString());
			
			if (bundleCacheNumList != null && !bundleCacheNumList.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "파싱이 완료되었습니다", "완료",JOptionPane.INFORMATION_MESSAGE);
				tap.setSelectedIndex(2);
				StringBuilder rmStr = new StringBuilder("");
				
				for ( String s : bundleCacheNumList)
				{
					rmStr.append(s);
				}
				
				createdRmCommandArea.setText(rmStr.toString());
			}
			else
			{
				JOptionPane.showMessageDialog(this, "파싱 결과가 없습니다.", "Error",JOptionPane.ERROR_MESSAGE);
				userInputArea.setText("");
				parsingResultArea.setText("");
				createdRmCommandArea.setText("");
			}
			
			
		}
		else if (e.getSource().equals(clearBtn))
		{
			userInputArea.setText("");
			parsingResultArea.setText("");
			createdRmCommandArea.setText("");
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(cachePathLabel))
		{
			if (e.getClickCount() == 2)
			{
				try 
				{
					StringSelection stringSelection = new StringSelection(KARAF_CACHE_DIRECTORY_PATH);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);
					
					JOptionPane.showMessageDialog(this, "클립보드에 복사되었습니다!", "완료",JOptionPane.INFORMATION_MESSAGE);
				} 
				catch 
				(Exception e2) 
				{
					JOptionPane.showMessageDialog(this, "클립보드에 무엇인가 에러가 있어서 복사할 수 없음.", "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		
		else if (e.getSource().equals(createdRmCommandArea))
		{
			if (e.getClickCount() == 2)
			{
				if (createdRmCommandArea.getText() != null && !createdRmCommandArea.getText().equals(""))
				{
					try 
					{
						StringSelection stringSelection = new StringSelection(createdRmCommandArea.getText());
						Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
						clipboard.setContents(stringSelection, null);
						
						JOptionPane.showMessageDialog(this, "클립보드에 복사되었습니다!", "완료",JOptionPane.INFORMATION_MESSAGE);
					} 
					catch 
					(Exception e2) 
					{
						JOptionPane.showMessageDialog(this, "클립보드에 무엇인가 에러가 있어서 복사할 수 없음.", "Error",JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		for ( Frame f : JFrame.getFrames())
		{
			if (f.getClass().equals(MainFrame.class))
			{
				f.setEnabled(true);
				f.requestFocus();
				break;
			}
		}
	}
	
	
	
	
}
