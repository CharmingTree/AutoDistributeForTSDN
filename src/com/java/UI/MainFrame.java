package com.java.UI;
import com.java.Listener.MenuActionListener;
import com.java.domain.AppInfo;
import com.java.domain.FilePathInfo;
import com.java.domain.ServerInfo;
import com.java.global.GlobalConstrants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.print.attribute.standard.Finishings;
import javax.print.attribute.standard.JobName;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.xml.sax.helpers.ParserFactory;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.processors.JsonVerifier;
import nu.xom.jaxen.util.DescendantAxisIterator;

public class MainFrame extends JFrame implements ActionListener, GlobalConstrants
{
	private MenuActionListener menuActionListner;
	private SettingFrame settingMenuItemFrame;
	private AppInfo appinfo;
	private ServerInfo serverinfo;
	private FilePathInfo filePathInfo;
	
	private JPanel topPanel;
	private JButton bundleDistributeBtn;
	private JButton karafDistributeBtn;
	private JButton bundleLogOpenURLBtn;
	private JButton fileDecodeingBtn;
	private JTextArea consoleField;
	
	private JPanel midPanel;
	private GridBagLayout gridBagLayout;
	
	private JLabel controllerBundleNameLabel;
	private JTextField controllerBundlePathField;
	private JButton controllerPathSearchBtn;
	private JCheckBox controllerCheckbox;
	
	private JLabel dmiNbiBundleNameLabel;
	private JTextField dmiNbiBundlePathFiled;
	private JButton dmiNbiPathSearchBtn;
	private JCheckBox dmiNbiCheckbox;
	
	private JLabel pceBundleNameLabel;
	private JTextField pceBundlePathFiled;
	private JButton pcePathSearchBtn;
	private JCheckBox pceCheckbox;
	
	private JLabel uiBundleNameLabel;
	private JTextField uiBundlePathFiled;
	private JButton uiPathSearchBtn;
	private JCheckBox uiCheckbox;
	
	private JButton logfileDownBtn;
	private JButton addtionalBtn_1;
	private JButton addtionalBtn_2;
	private JButton addtionalBtn_3;
	
	private JPanel bottomPanel;
	
	
	
	public MainFrame()
	{
		super();
		menuActionListner = new MenuActionListener();
		setTitle("TSDN");
		appinfo = new AppInfo();
		serverinfo = new ServerInfo();
		initLayout();
		initValue();
		checkSourceFile();
		
		Image img = new ImageIcon(APPLICATION_ICON_IMAGE_PATH).getImage();
		setIconImage(img);
		
		
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				FileWriter writer = null;
				try 
				{
					appinfo.setControllerPath(controllerBundlePathField.getText());
					appinfo.setControllerPathCheck(controllerCheckbox.isSelected());
					
					appinfo.setDmiNbiPath(dmiNbiBundlePathFiled.getText());
					appinfo.setDmiNbiPathCheck(dmiNbiCheckbox.isSelected());
					
					appinfo.setPcePath(pceBundlePathFiled.getText());
					appinfo.setPcePathCheck(pceCheckbox.isSelected());
					
					appinfo.setUiPath(uiBundlePathFiled.getText());
					appinfo.setUipathCheck(uiCheckbox.isSelected());
					
					
					JSONObject jsonobj = JSONObject.fromObject(JSONSerializer.toJSON(appinfo));
					
					System.out.println(jsonobj.toString());
					
					File file = new File(APPINFO_RESOURCE_FILE_PATH);
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
				System.exit(0);
			}
		});
	}
	
	public void initValue()
	{
		try 
		{
			File file = new File(APPINFO_RESOURCE_FILE_PATH);
			if(file.exists())
			{
				System.out.println("존재합니다");
				
				BufferedReader reader = new BufferedReader((new FileReader(file)));
				String line = reader.readLine();
				JSONObject lineConvertedToJson = null;
				if (line != null) 
				{
					lineConvertedToJson = JSONObject.fromObject(JSONSerializer.toJSON(line));
				}
				
				System.out.println(lineConvertedToJson.toString());
				
				
				appinfo = (AppInfo)JSONObject.toBean(lineConvertedToJson, AppInfo.class);
				
				System.out.println(appinfo.toString());
				
				controllerBundlePathField.setText(appinfo.getControllerPath());
				controllerCheckbox.setSelected(appinfo.isControllerPathCheck());
				
				dmiNbiBundlePathFiled.setText(appinfo.getDmiNbiPath());
				dmiNbiCheckbox.setSelected(appinfo.isDmiNbiPathCheck());
				
				pceBundlePathFiled.setText(appinfo.getPcePath());
				pceCheckbox.setSelected(appinfo.isPcePathCheck());
				
				uiBundlePathFiled.setText(appinfo.getUiPath());
				uiCheckbox.setSelected(appinfo.isUipathCheck());
			}
			else
			{
				System.out.println("존재하지 않습니다.");
				
			}
			
			File serverfile = new File(SERVERINFO_RESOURCE_FILE_PATH);
			
			if(serverfile.exists())
			{
				BufferedReader reader = new BufferedReader((new FileReader(serverfile)));
				String line = reader.readLine();
				JSONObject lineConvertedToJson = null;
				if (line != null) 
				{
					lineConvertedToJson = JSONObject.fromObject(JSONSerializer.toJSON(line));
				}
				
				serverinfo = (ServerInfo)JSONObject.toBean(lineConvertedToJson, ServerInfo.class);
				
				this.setTitle("TSDN 현재 세팅 서버 : "+ serverinfo.getHost());
			}
			else
			{
				System.out.println("서버 설정파일이 존재하지 않습니다.");
			}
			
			File pathinfofile = new File(FILEPATHINFO_RESOURCE_FILE_PATH);
			
			if (pathinfofile.exists())
			{
				BufferedReader reader = new BufferedReader((new FileReader(pathinfofile)));
				String line = reader.readLine();
				JSONObject lineConvertedToJson = null;
				if (line != null) 
				{
					lineConvertedToJson = JSONObject.fromObject(JSONSerializer.toJSON(line));
				}
				
				filePathInfo = (FilePathInfo)JSONObject.toBean(lineConvertedToJson, FilePathInfo.class);

			}
		} 
		
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void initLayout()
	{
		
		setBounds(20,20,800,800);
		
		gridBagLayout = new GridBagLayout();

		setLayout(gridBagLayout);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu settingMenu = new JMenu("TSDN");
		menuBar.add(settingMenu);
		
		JMenuItem serverSettingItem = new JMenuItem("Server Setting");
		serverSettingItem.addActionListener(menuActionListner);
		JMenuItem configurationSettingItem = new JMenuItem("Configuration Setting");
		configurationSettingItem.addActionListener(menuActionListner);
		
		settingMenu.add(serverSettingItem);
		settingMenu.add(configurationSettingItem);
		
		
		
		setJMenuBar(menuBar);

		controllerBundleNameLabel = new JLabel(" Controller Bundle : ");
		gridBagLayoutInsert(controllerBundleNameLabel, 0,0,1,1);
		controllerBundlePathField = new JTextField(40);
		controllerBundlePathField.setEditable(false);
		gridBagLayoutInsert(controllerBundlePathField, 1,0,2,1);
		controllerPathSearchBtn = new JButton("Path");
		gridBagLayoutInsert(controllerPathSearchBtn, 3,0,1,1);
		controllerPathSearchBtn.addActionListener(this);
		controllerCheckbox = new JCheckBox();
		controllerCheckbox.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				if( ((JCheckBox)e.getSource()).isSelected() && controllerBundlePathField.getText().equals(""))
				{
					controllerCheckbox.setSelected(false);
					JOptionPane.showMessageDialog(null, "지정된 경로가 없습니다.", "Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		gridBagLayoutInsert(controllerCheckbox, 4,0,1,1);
		
		dmiNbiBundleNameLabel = new JLabel(" DMI-NBI Bundle : ");
		gridBagLayoutInsert(dmiNbiBundleNameLabel, 0,1,1,1);
		dmiNbiBundlePathFiled = new JTextField(40);
		dmiNbiBundlePathFiled.setEditable(false);
		gridBagLayoutInsert(dmiNbiBundlePathFiled, 1,1,2,1);
		dmiNbiPathSearchBtn = new JButton("Path");
		gridBagLayoutInsert(dmiNbiPathSearchBtn, 3,1,1,1);
		dmiNbiPathSearchBtn.addActionListener(this);
		dmiNbiCheckbox = new JCheckBox();
		dmiNbiCheckbox.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				if( ((JCheckBox)e.getSource()).isSelected() && dmiNbiBundlePathFiled.getText().equals(""))
				{
					dmiNbiCheckbox.setSelected(false);
					JOptionPane.showMessageDialog(null, "지정된 경로가 없습니다.", "Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		gridBagLayoutInsert(dmiNbiCheckbox, 4,1,1,1);
		
		pceBundleNameLabel = new JLabel(" PCE Bundle : ");
		gridBagLayoutInsert(pceBundleNameLabel, 0,2,1,1);
		pceBundlePathFiled = new JTextField(40);
		pceBundlePathFiled.setEditable(false);
		gridBagLayoutInsert(pceBundlePathFiled, 1,2,2,1);
		pcePathSearchBtn = new JButton("Path");
		gridBagLayoutInsert(pcePathSearchBtn, 3,2,1,1);
		pcePathSearchBtn.addActionListener(this);
		pceCheckbox = new JCheckBox();
		pceCheckbox.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				if( ((JCheckBox)e.getSource()).isSelected() && pceBundlePathFiled.getText().equals(""))
				{
					pceCheckbox.setSelected(false);
					JOptionPane.showMessageDialog(null, "지정된 경로가 없습니다.", "Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		gridBagLayoutInsert(pceCheckbox, 4,2,1,1);
		
		uiBundleNameLabel = new JLabel(" UI Bundle : ");
		gridBagLayoutInsert(uiBundleNameLabel, 0,3,1,1);
		uiBundlePathFiled = new JTextField(40);
		uiBundlePathFiled.setEditable(false);
		gridBagLayoutInsert(uiBundlePathFiled, 1,3,2,1);
		uiPathSearchBtn = new JButton("Path");
		gridBagLayoutInsert(uiPathSearchBtn, 3,3,1,1);
		uiPathSearchBtn.addActionListener(this);
		uiCheckbox = new JCheckBox();
		uiCheckbox.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				if( ((JCheckBox)e.getSource()).isSelected() &&uiBundlePathFiled.getText().equals(""))
				{
					uiCheckbox.setSelected(false);
					JOptionPane.showMessageDialog(null, "지정된 경로가 없습니다.", "Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		gridBagLayoutInsert(uiCheckbox, 4,3,1,1);
		
		bundleDistributeBtn = new JButton("Bundle Distribute");
		gridBagLayoutInsert(bundleDistributeBtn, 0,4,1,1);
		bundleDistributeBtn.addActionListener(this);
		
		karafDistributeBtn = new JButton("Karaf Distribute");
		gridBagLayoutInsert(karafDistributeBtn, 1,4,1,1);
		karafDistributeBtn.addActionListener(this);
		
		bundleLogOpenURLBtn = new JButton("Open Log");
		gridBagLayoutInsert(bundleLogOpenURLBtn, 2,4,1,1);
		bundleLogOpenURLBtn.addActionListener(this);
		
		fileDecodeingBtn = new JButton("File Decode");
		fileDecodeingBtn.addActionListener(this);
		gridBagLayoutInsert(fileDecodeingBtn, 3,4,2,1);
		
		//Font font = new Font("Times",Font.BOLD, 10);
		consoleField = new JTextArea(20,50);
		consoleField.setEditable(false);
		consoleField.setBackground(Color.black);
		consoleField.setForeground(Color.white);
		//consoleField.setFont(font);

		JScrollPane scroller = new JScrollPane(consoleField);
//		gridBagLayoutInsert(consoleField,0,10,5,5);
//		

		gridBagLayoutInsert(scroller,0,10,5,1);
		
		logfileDownBtn = new JButton("LogFileDown");
		logfileDownBtn.addActionListener(this);
		gridBagLayoutInsert(logfileDownBtn,0,11,1,1);
		addtionalBtn_1 = new JButton("");
		//gridBagLayoutInsert(addtionalBtn_1,)
		addtionalBtn_2 = new JButton("");
		addtionalBtn_3 = new JButton("");
		//consoleField.setSize(300,300);
		this.pack();
		setResizable(false);
		setVisible(true);
		
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	}
	
	public void gridBagLayoutInsert(Component c, int x, int y, int w, int h)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = gridBagConstraints.BOTH;
		gridBagConstraints.gridx = x;
		gridBagConstraints.gridy = y;
		gridBagConstraints.gridwidth = w;
		gridBagConstraints.gridheight = h;
		
		gridBagLayout.setConstraints(c, gridBagConstraints);
		this.add(c);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(controllerPathSearchBtn))
		{
			fileDialogSearch4Bundle(controllerBundlePathField, BUNDLE_TYPE_CONTROLLER);
		}
		else if (e.getSource().equals(dmiNbiPathSearchBtn))
		{
			fileDialogSearch4Bundle(dmiNbiBundlePathFiled, BUNDLE_TYPE_DMINBI);
		}
		else if (e.getSource().equals(pcePathSearchBtn))
		{
			fileDialogSearch4Bundle(pceBundlePathFiled, BUNDLE_TYPE_PCE);
		}
		else if (e.getSource().equals(uiPathSearchBtn))
		{
			fileDialogSearch4Bundle(uiBundlePathFiled, BUNDLE_TYPE_UI);
		}
		else if (e.getSource().equals(bundleDistributeBtn))
		{
			int ans = JOptionPane.showConfirmDialog(this, "배포 하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
			updateSeverSetting();
			if (verifySFtpCommandParam() && ans == 0 && verifyServerSettingInfo())
			{
				setAllDisableBtnClick();
				String batchPath = "C:\\tsdndistribute\\bundledistribute.bat ";
				
				StringBuilder param = new StringBuilder();
				
				param.append(batchPath);
				param.append(controllerCheckbox.isSelected() ? controllerBundlePathField.getText() : "-");
				param.append(" "); 
				param.append(dmiNbiCheckbox.isSelected() ? dmiNbiBundlePathFiled.getText() : "-");
				param.append(" ");
				param.append(pceCheckbox.isSelected() ? pceBundlePathFiled.getText() : "-");
				param.append(" ");
				param.append(uiCheckbox.isSelected() ? uiBundlePathFiled.getText() : "-");
				param.append(" ");
				param.append(serverinfo.getId());
				param.append(" ");
				param.append(serverinfo.getPw());
				param.append(" ");
				param.append(serverinfo.getHost());
				
				System.out.println(param);
				
				
				try 
				{
					Process process = Runtime.getRuntime().exec(param.toString());
					
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					
					String line = null;
					StringBuilder appenderline = new StringBuilder();
					while ((line = bufferedReader.readLine()) != null)
					{
						System.out.println(line);
						appenderline.append(line+"\n");
						consoleField.setText(appenderline.toString());
					}
					JOptionPane.showMessageDialog(this, "배포가 완료되었습니다.", "완료",JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (Exception e2) 
				{
					System.out.println(e2.getMessage());
					JOptionPane.showMessageDialog(this, "sftp 명령도중 오류가 발생했습니다.\n 수작업으로 확인해주세요\n"+e2.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					setAllEnable();
				}
			}
			else
			{
				if (ans == 0)
				{
					JOptionPane.showMessageDialog(this, "배포할 번들을 지정해주세요.", "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
		}
		else if (e.getSource().equals(karafDistributeBtn))
		{
			for ( Frame f : JFrame.getFrames())
			{
				if (f.getClass().equals(MainFrame.class))
				{
					f.setEnabled(false);
					break;
				}
			}
			new KarafDistributeFrame();
		}
		else if (e.getSource().equals(bundleLogOpenURLBtn))
		{
			Desktop desktop = Desktop.getDesktop();
			
			try 
			{
				URI uri = new URI("http://10.81.204.38:7080/tail_7502.jsp");
				desktop.browse(uri);
			} 
			catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		else if (e.getSource().equals(fileDecodeingBtn)) {
			try 
			{
				setAllDisableBtnClick();
				updatefilePathSetting();
				consoleField.setText("");
				FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
				dialog.setVisible(true);
				
				if(dialog.getFile() == null) return;
				
				System.out.println(dialog.getFile());
				String s = dialog.getFile();
				String docType;
				String[] split = s.split("\\."); 
				
				
				String fileExtention = "-";
				
				if ( split != null)
				{
					fileExtention = split[split.length-1];
				}

				System.out.println(fileExtention);
			
				
				//String fileExtention = (dialog.getFile().split("."))[1] != null ? (dialog.getFile().split("."))[1] : "-" ;
				
				
				if (fileExtention.equals("xlsx")) {
					docType = "01";
				}
				else if (fileExtention.equals("pptx")) {
					docType = "02";
				}
				else if (fileExtention.equals("doc"))
				{
					docType = "03";
				}
				else if (fileExtention.equals("pdf"))
				{
					docType = "04";
				}
				else 
				{
					docType = "-";
				}
				
				String docPath = "\""+dialog.getDirectory()+dialog.getFile()+"\"";
				System.out.println(docPath);
				String batchPath = "C:\\tsdndistribute\\requestDocDecode.bat ";
				System.out.println(batchPath);
				StringBuilder param = new StringBuilder();
				
				param.append(batchPath);
				param.append(serverinfo.getHost());
				param.append(" ");
				param.append(serverinfo.getId());
				param.append(" ");
				param.append(serverinfo.getPw());
				param.append(" ");
				param.append(docPath);
				param.append(" ");
				param.append(docType);
				param.append(" ");

	
				
				if (filePathInfo == null || filePathInfo.getDecodeTargetResultPath() == null || filePathInfo.getDecodeTargetResultPath().equals(""))
				{
					param.append(DEFAULT_DECODE_FILE_PATH);
					param.append(" ");
					param.append("\""+DEFAULT_DECODE_FILE_PATH+"\\DECODE_"+dialog.getFile()+"\"");
				}
				else
				{
					param.append("\""+filePathInfo.getDecodeTargetResultPath()+"\"");
					param.append(" ");
					param.append("\""+filePathInfo.getDecodeTargetResultPath()+"\\DECODE_"+dialog.getFile()+"\"");
				}
				
				System.out.println(param);
				
				Process process = Runtime.getRuntime().exec(param.toString());
				
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				
				String line = null;
				StringBuilder appenderline = new StringBuilder();
				while ((line = bufferedReader.readLine()) != null)
				{
					System.out.println(line);
					appenderline.append(line+"\n");
					consoleField.setText(appenderline.toString());
				}
				JOptionPane.showMessageDialog(this, "복호화 완료", "완료",JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e2)
			{
				StringBuilder strbuilder = new StringBuilder();
				strbuilder.append(consoleField.getText()+"\n");
				
				System.out.println(e2.getMessage());
				strbuilder.append(e2.getMessage()+"\n");
				consoleField.setText(strbuilder.toString());
				JOptionPane.showMessageDialog(this, "문서 복호화 오류 발생", "Error",JOptionPane.ERROR_MESSAGE);
			}
			finally
			{
				setAllEnable();
			}
		}
	}
	
	public void fileDialogSearch4Bundle(JTextField fild, String bundleType)
	{
		try
		{
			FileDialog dialog = new FileDialog(this, "open", FileDialog.LOAD);
			dialog.setDirectory(appinfo.getPrevFileDialogPath());
			dialog.setVisible(true);
			
			if(dialog.getFile() == null) return;
			
			appinfo.setPrevFileDialogPath(dialog.getDirectory());
			String bundlePath = dialog.getDirectory()+dialog.getFile();
			
			if (verifyBundlePath(bundlePath, bundleType))
			{
				fild.setText(bundlePath);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "해당 "+bundleType+"에 맞지않는 파일입니다.", "Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, "잘못된 접근입니다.", "Error",JOptionPane.ERROR_MESSAGE);
		}
				
	}
	
	public boolean verifySFtpCommandParam()
	{
		if ( controllerCheckbox.isSelected() || dmiNbiCheckbox.isSelected() || pceCheckbox.isSelected() || uiCheckbox.isSelected() )
			return true;
		else
			return false;
	}
	
	public boolean verifyBundlePath(String path, String bundleType)
	{
		StringTokenizer tokenizer = new StringTokenizer(path, "\\");		
		String[] tokens = new String[tokenizer.countTokens()];
		
		for ( int i = 0; i < tokens.length; i++)
		{
			tokens[i] = tokenizer.nextToken();
		}		
		String bundleJarName = tokens[tokens.length-1];
		System.out.println(bundleJarName);
		
		String pattern = bundleType+"[\\w|\\W].*";
		
		if (Pattern.matches(pattern, bundleJarName))
		{
			System.out.println(bundleType+" is Matches!!");
			return true;
		}
		else 
		{
			return false;
		}	
	}
	
	public void setAllDisableBtnClick()
	{
		this.setEnabled(false);
	}
	
	public void setAllEnable()
	{
		this.setEnabled(true);
	}
	
	public void checkSourceFile()
	{
		StringBuilder appenderline = new StringBuilder();
		if (appinfo != null)
		{
			appenderline.append("appinfo Set"+"\n");
			//consoleField.setText(appenderline.toString());
		}
		else
		{
			appenderline.append("appinfo 값 없음"+"\n");
			//consoleField.setText(appenderline.toString());
		}
		
		if (serverinfo != null)
		{
			appenderline.append("ServerInfo Set"+"\n");
			appenderline.append(serverinfo.toString()+"\n");
			//consoleField.setText(appenderline.toString());
		}
		else
		{
			appenderline.append("서버 설정이 되어 있지 않음."+"\n");
			//consoleField.setText(appenderline.toString());
		}
		
		if (filePathInfo != null) 
		{
			appenderline.append("FilePath Set"+"\n");
			appenderline.append(filePathInfo.toString()+"\n");
			//consoleField.setText(appenderline.toString());
		}
		else
		{
			appenderline.append("FilePath 설정되지 않음"+"\n");
		}
		consoleField.setText(appenderline.toString());
	}
	
	public boolean verifyServerSettingInfo()
	{
		if(serverinfo != null)
		{
			if (serverinfo.getId().equals(""))
				return false;
			
			if (serverinfo.getPw().equals(""))
				return false;
			if (serverinfo.getHost().equals(""))
				return false;
		}
		else
			return false;
		
		return true;
	}
	public void updatefilePathSetting()
	{
		try 
		{
			File file = new File(FILEPATHINFO_RESOURCE_FILE_PATH);
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
				
				filePathInfo = (FilePathInfo)JSONObject.toBean(lineConvertedToJson, FilePathInfo.class);
				
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
	
	public void updateSeverSetting()
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

//	@Override
//	public void windowClosing(WindowEvent arg0) {
//		// TODO Auto-generated method stub
//		appinfo.setControllerPath(controllerBundlePathField.getText());
//		appinfo.setControllerPath(controllerCheckbox.isSelected());
//		
//		appinfo.setDmiNbiPath(dmiNbiBundlePathFiled.getText());
//		appinfo.setDmiNbiPath(dmiNbiCheckbox.isSelected());
//		
//		appinfo.setPcePath(pceBundlePathFiled.getText());
//		appinfo.setPcePath(pceCheckbox.isSelected());
//		
//		appinfo.setDtiPath(dtiBundlePathFiled.getText());
//		appinfo.setDtiPath(dtiCheckbox.isSelected());
//		
//		
//		JSONObject jsonobj = JSONObject.fromObject(JSONSerializer.toJSON(appinfo));
//		
//		System.out.println(jsonobj.toString());
//		System.exit(0);
//	}


	
}
