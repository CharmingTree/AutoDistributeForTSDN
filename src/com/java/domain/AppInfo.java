package com.java.domain;

public class AppInfo 
{
	private String controllerPath;
	private boolean isControllerPathCheck;
	private String dmiNbiPath;
	private boolean isDmiNbiPathCheck;
	private String pcePath;
	private boolean isPcePathCheck;
	private String dtiPath;
	private boolean isDtiPathCheck;
	private String uiPath;
	private boolean isUipathCheck;
	
	
	public boolean isControllerPathCheck() {
		return isControllerPathCheck;
	}
	public void setControllerPathCheck(boolean isControllerPathCheck) {
		this.isControllerPathCheck = isControllerPathCheck;
	}
	public boolean isDmiNbiPathCheck() {
		return isDmiNbiPathCheck;
	}
	public void setDmiNbiPathCheck(boolean isDmiNbiPathCheck) {
		this.isDmiNbiPathCheck = isDmiNbiPathCheck;
	}
	public boolean isPcePathCheck() {
		return isPcePathCheck;
	}
	public void setPcePathCheck(boolean isPcePathCheck) {
		this.isPcePathCheck = isPcePathCheck;
	}
	public boolean isDtiPathCheck() {
		return isDtiPathCheck;
	}
	public void setDtiPathCheck(boolean isDtiPathCheck) {
		this.isDtiPathCheck = isDtiPathCheck;
	}
	private String prevFileDialogPath;
		
	public String getControllerPath() {
		return controllerPath;
	}
	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}
	public String getDmiNbiPath() {
		return dmiNbiPath;
	}
	public void setDmiNbiPath(String dmiNbiPath) {
		this.dmiNbiPath = dmiNbiPath;
	}
	public String getPcePath() {
		return pcePath;
	}
	public void setPcePath(String pcePath) {
		this.pcePath = pcePath;
	}
	public String getDtiPath() {
		return dtiPath;
	}
	public void setDtiPath(String dtiPath) {
		this.dtiPath = dtiPath;
	}
	public String getPrevFileDialogPath() {
		return prevFileDialogPath;
	}
	public void setPrevFileDialogPath(String prevFileDialogPath) {
		this.prevFileDialogPath = prevFileDialogPath;
	}
	public String getUiPath() {
		return uiPath;
	}
	public void setUiPath(String uiPath) {
		this.uiPath = uiPath;
	}
	public boolean isUipathCheck() {
		return isUipathCheck;
	}
	public void setUipathCheck(boolean isUipathCheck) {
		this.isUipathCheck = isUipathCheck;
	}
	
	
	@Override
	public String toString() {
		return "AppInfo [controllerPath=" + controllerPath
				+ ", isControllerPathCheck=" + isControllerPathCheck
				+ ", dmiNbiPath=" + dmiNbiPath + ", isDmiNbiPathCheck="
				+ isDmiNbiPathCheck + ", pcePath=" + pcePath
				+ ", isPcePathCheck=" + isPcePathCheck + ", dtiPath=" + dtiPath
				+ ", isDtiPathCheck=" + isDtiPathCheck + ", uiPath=" + uiPath
				+ ", isUipathCheck=" + isUipathCheck + ", prevFileDialogPath="
				+ prevFileDialogPath + "]";
	}
	
	
	

	
	
}
