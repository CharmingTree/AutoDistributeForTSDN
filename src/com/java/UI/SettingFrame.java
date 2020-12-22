package com.java.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.java.domain.ServerInfo;
import com.java.global.GlobalConstrants;

public class SettingFrame extends JFrame implements ActionListener, GlobalConstrants
{
	private ServerInfo serverinfo;
	
	private JLabel frameTitleLabel;
	private JLabel serverHostLabel;
	private JLabel serverIDLabel;
	private JLabel serverPWLabel;
	
	private JTextField hostTextField;
	private JTextField idTextFiled;
	private JTextField pwTextFiled;
	
	private JButton saveBtn;
	private JButton modifyBtn;
	private JButton cancelBtn;
	
	private GridBagLayout gridBagLayout;
	
	public SettingFrame()
	{
		serverinfo = new ServerInfo();
		setTitle("서버 세팅");
		
		gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		initForm();
		initValue();
		setSize(400,200);
		setResizable(false);
		setVisible(true);
		for ( Frame f : JFrame.getFrames())
		{
			if (f.getClass().equals(MainFrame.class))
			{
				setLocationRelativeTo(f);
				break;
			}
		}
		Image img = new ImageIcon(APPLICATION_ICON_IMAGE_PATH).getImage();
		setIconImage(img);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	void initValue()
	{
		try 
		{
			File file = new File(SERVERINFO_RESOURCE_FILE_PATH);
			if(file.exists())
			{
				BufferedReader reader = new BufferedReader((new FileReader(file)));
				String line = reader.readLine();
				JSONObject lineConvertedToJson = null;
				if (line != null) 
				{
					lineConvertedToJson = JSONObject.fromObject(JSONSerializer.toJSON(line));
				}
				
				System.out.println(lineConvertedToJson.toString());
				
				serverinfo = (ServerInfo)JSONObject.toBean(lineConvertedToJson, ServerInfo.class);
				
				hostTextField.setText(serverinfo.getHost());
				idTextFiled.setText(serverinfo.getId());
				pwTextFiled.setText(serverinfo.getPw());
				
			}
			else
			{
				System.out.println("존재하지 않습니다.");
				
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}
	
	void initForm()
	{
		frameTitleLabel = new JLabel("FTP 서버 설정");
		frameTitleLabel.setHorizontalAlignment(JLabel.LEFT);
		gridBagLayoutInsert(frameTitleLabel, 0, 0, 4, 1);
		
		serverHostLabel = new JLabel("Host: ");
		serverHostLabel.setHorizontalTextPosition(JLabel.LEFT);
		serverHostLabel.setHorizontalAlignment(JLabel.LEFT);
		gridBagLayoutInsert(serverHostLabel, 0, 1, 1, 1);
		
		hostTextField = new JTextField(20);
		hostTextField.setEditable(false);
		gridBagLayoutInsert(hostTextField, 1, 1, 2, 1);
		
		serverIDLabel = new JLabel("ID: ");
		serverIDLabel.setHorizontalTextPosition(JLabel.LEFT);
		serverIDLabel.setHorizontalAlignment(JLabel.LEFT);
		gridBagLayoutInsert(serverIDLabel, 0, 2, 1, 1);
		
		idTextFiled = new JTextField(20);
		idTextFiled.setEditable(false);
		gridBagLayoutInsert(idTextFiled, 1, 2, 2, 1);
		
		serverPWLabel = new JLabel("PW: ");
		serverPWLabel.setHorizontalTextPosition(JLabel.LEFT);
		serverPWLabel.setHorizontalAlignment(JLabel.LEFT);
		gridBagLayoutInsert(serverPWLabel, 0, 3, 1, 1);
		
		pwTextFiled = new JTextField(20);
		pwTextFiled.setEditable(false);
		gridBagLayoutInsert(pwTextFiled, 1, 3, 2, 1);
		
		saveBtn = new JButton("적용");
		saveBtn.setVisible(false);
		saveBtn.addActionListener(this);
		gridBagLayoutInsert(saveBtn, 2, 4, 1, 1);
		
		//saveBtn.setHorizontalTextPosition(JButton.CENTER);
		
		modifyBtn = new JButton("편집");
		modifyBtn.addActionListener(this);
		gridBagLayoutInsert(modifyBtn, 2, 4, 1, 1);
		
		cancelBtn = new JButton("취소");
		gridBagLayoutInsert(cancelBtn, 3, 4, 1, 1);
		cancelBtn.addActionListener(this);
		//cancelBtn.setHorizontalTextPosition(JButton.CENTER);
	}	

	public void gridBagLayoutInsert(Component c, int x, int y, int w, int h)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = gridBagConstraints.BOTH;
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		gridBagConstraints.gridwidth = w;
		gridBagConstraints.gridheight = h;
		
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		
		gridBagLayout.setConstraints(c, gridBagConstraints);
		this.add(c);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if (e.getSource().equals(modifyBtn))
		{
			modifyBtn.setVisible(false);
			saveBtn.setVisible(true);
			hostTextField.setEditable(true);
			idTextFiled.setEditable(true);
			pwTextFiled.setEditable(true);
		}
		else if (e.getSource().equals(saveBtn))
		{
			modifyBtn.setVisible(true);
			
			
			
			if (verifySettingData())
			{
				SaveServerInfoFileAndLabelSet();
				saveBtn.setVisible(false);
				hostTextField.setEditable(false);
				//hostTextField.setBackground(Color.);
				idTextFiled.setEditable(false);
				//idTextFiled.setBackground(Color.DARK_GRAY);
				pwTextFiled.setEditable(false);
				//pwTextFiled.setBackground(Color.DARK_GRAY);
				JOptionPane.showMessageDialog(this, "적용되었습니다.", "완료",JOptionPane.INFORMATION_MESSAGE);
				for (Frame f : JFrame.getFrames())
				{
					if( f.getClass().equals(MainFrame.class))
					{
						f.setTitle("TSDN 현재 세팅 서버 : "+serverinfo.getHost());
						break;
					}
				}
				
				
			}
			else
			{
				//JOptionPane.showMessageDialog(this, "잘못된 설정입니다.", "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (e.getSource().equals(cancelBtn))
		{
			System.out.println("취소");
			int result;

			if (saveBtn.isVisible())
			{
				System.out.println("여기까ㅣ지 들어옴");
				result = JOptionPane.showConfirmDialog(this, "설정을 정말 취소하시겠습니까?", "Confirm", JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.CLOSED_OPTION)
				{
					
				}
				else if (result == JOptionPane.YES_OPTION)
				{
					onlySaveServerInfoFile();
					dispose();
				}
				else
				{
					
				}
			}
			else if (modifyBtn.isVisible())
			{
				dispose();
			}

		}
		
	}
	
	private boolean verifySettingData()
	{
		String host = hostTextField.getText();
		String id = idTextFiled.getText();
		String pw = pwTextFiled.getText();
		
		String ipregExp = "^([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
							+"([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
							+"([01]?\\d?\\d|2[0-4]\\d|25[0-5])\\."
							+"([01]?\\d?\\d|2[0-4]\\d|25[0-5])$";
		
		if (host == null || host.equals(""))
		{
			JOptionPane.showMessageDialog(this, "호스트가 비어있습니다.", "Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (id == null || id.equals(""))
		{
			JOptionPane.showMessageDialog(this, "id가 비어있습니다.", "Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (pw == null || pw.equals(""))
		{
			JOptionPane.showMessageDialog(this, "pw가 비어있습니다.", "Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if ( !host.matches(ipregExp))
		{
			JOptionPane.showMessageDialog(this, "올바르지 않은 호스트 형식 입니다.", "Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if (host.equals("10.240.34.52"))
		{
			JOptionPane.showMessageDialog(this, "운영 서버 호스트는 지정하지 마십시오", "Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	
	private void SaveServerInfoFileAndLabelSet()
	{
		FileWriter writer = null;
		try 
		{	
			serverinfo.setHost(hostTextField.getText());
			serverinfo.setId(idTextFiled.getText());
			serverinfo.setPw(pwTextFiled.getText());
			
			JSONObject jsonobj = JSONObject.fromObject(JSONSerializer.toJSON(serverinfo));
			
			System.out.println(jsonobj.toString());
			
			File file = new File(SERVERINFO_RESOURCE_FILE_PATH);
			writer = new FileWriter(file, false);
			writer.write(jsonobj.toString());
			writer.flush();
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		finally
		{
			try 
			{
				if(writer != null)
					writer.close();
			} 
			catch (Exception e2) {
				
			}
			
		}		
	} // end of SaveServerInfoFileAndLabelSet Method
	
	private void onlySaveServerInfoFile()
	{
		FileWriter writer = null;
		try 
		{						
			JSONObject jsonobj = JSONObject.fromObject(JSONSerializer.toJSON(serverinfo));
			
			System.out.println(jsonobj.toString());
			
			File file = new File(SERVERINFO_RESOURCE_FILE_PATH);
			writer = new FileWriter(file, false);
			writer.write(jsonobj.toString());
			writer.flush();
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		finally
		{
			try 
			{
				if(writer != null)
					writer.close();
			} 
			catch (Exception e2) {
				
			}
			
		}		
	} // end of onlySaveServerInfoFile Method

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
