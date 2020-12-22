package com.java.UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;

import javax.naming.spi.DirectoryManager;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.java.domain.FilePathInfo;
import com.java.global.GlobalConstrants;

public class ConfigurationFrame extends JFrame implements ActionListener, GlobalConstrants {

	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private FilePathInfo configurationFilepathInfo;
	private GridBagLayout gridBagLayout;
	
	private JLabel decodeResultFilePathlabel;
	private JTextField decodeResultFilePathTextFiled;
	private JButton decodeFilePathBtn;
	
	public ConfigurationFrame() {
		configurationFilepathInfo = new FilePathInfo();
		setTitle("설정");
		setLayout(new BorderLayout());
		centerPanel = new JPanel();
		gridBagLayout = new GridBagLayout();
		centerPanel.setLayout(gridBagLayout);
		add(centerPanel, BorderLayout.CENTER);
		
		initForm();
		initValue();
		setSize(700,500);
		setResizable(false);
		setVisible(true);
		for ( Frame f : JFrame.getFrames())
		{
			if (f.getClass().equals(MainFrame.class))
			{
				setLocationRelativeTo(f); // 메인 프레임 포지션 내에 새 프레임 생성
				break;
			}
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initValue() 
	{
		try {
			File pathinfofile = new File(FILEPATHINFO_RESOURCE_FILE_PATH);
			
			if (pathinfofile.exists())
			{
				BufferedReader reader = new BufferedReader(new FileReader(pathinfofile));
				String line = reader.readLine();
				JSONObject lineConvertedToJson = null;
				if (line != null) 
				{
					lineConvertedToJson = JSONObject.fromObject(JSONSerializer.toJSON(line));
					
					
					configurationFilepathInfo = (FilePathInfo)JSONObject.toBean(lineConvertedToJson, FilePathInfo.class);
					
					if (configurationFilepathInfo.getDecodeTargetResultPath() != null && !configurationFilepathInfo.getDecodeTargetResultPath().equals("")){
						decodeResultFilePathTextFiled.setText(configurationFilepathInfo.getDecodeTargetResultPath());
					}
				}
				
				

			}
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "에러", "Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void initForm()
	{
		decodeResultFilePathlabel = new JLabel("복호화 파일 경로 : ");
		gridBagLayoutInsert(decodeResultFilePathlabel, centerPanel, 0,0,1,1);
		decodeResultFilePathTextFiled = new JTextField(30);
		decodeResultFilePathTextFiled.setEditable(false);
		gridBagLayoutInsert(decodeResultFilePathTextFiled, centerPanel, 1,0,1,1);
		decodeFilePathBtn = new JButton("Path");
		decodeFilePathBtn.addActionListener(this);
		gridBagLayoutInsert(decodeFilePathBtn, centerPanel, 2,0,1,1);
		
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
	public void gridBagLayoutInsert(Component c, JPanel pan, int x, int y, int w, int h)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = gridBagConstraints.BOTH;
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		gridBagConstraints.gridwidth = w;
		gridBagConstraints.gridheight = h;
		
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		
		gridBagLayout.setConstraints(c, gridBagConstraints);
		pan.add(c);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(decodeFilePathBtn)) {
			FileWriter writer = null;
			try
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = chooser.showOpenDialog(this);
				
				if (option == JFileChooser.APPROVE_OPTION)
				{
					File f = chooser.getSelectedFile();
					configurationFilepathInfo.setDecodeTargetResultPath(f.getAbsolutePath());
					decodeResultFilePathTextFiled.setText(f.getAbsolutePath());
				}
				else
				{
					configurationFilepathInfo.setDecodeTargetResultPath("");
					decodeResultFilePathTextFiled.setText("");
				}
				
//				FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
//				dialog.setVisible(true);
//				
//				if(dialog.getFile() == null) return;
//				String path = dialog.getDirectory()+dialog.getFile();
//				decodeResultFilePathTextFiled.setText(path);
//				configurationFilepathInfo.setDecodeTargetResultPath(path);
				
				JSONObject jsonobj = JSONObject.fromObject(JSONSerializer.toJSON(configurationFilepathInfo));
				
				System.out.println(jsonobj.toString());
				
				File file = new File(FILEPATHINFO_RESOURCE_FILE_PATH);
				writer = new FileWriter(file, false);
				writer.write(jsonobj.toString());
				writer.flush();
				JOptionPane.showMessageDialog(this, "적용되었습니다.", "완료",JOptionPane.INFORMATION_MESSAGE);
				
			}
			catch(Exception e2)
			{
				JOptionPane.showMessageDialog(this, "잘못된 설정입니다.", "Error",JOptionPane.ERROR_MESSAGE);
				System.out.println(e2.getMessage());
			}
			
		}
		
	}
	
	@Override
	public void dispose() { // 현재 프래임 종료시 메인프레임 활성화 
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
