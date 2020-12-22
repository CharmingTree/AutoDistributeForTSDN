package com.java.domain;

public class ServerInfo 
{
	private String host;
	private String id;
	private String pw;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	@Override
	public String toString() {
		return "ServerInfo [host=" + host + ", id=" + id + ", pw=" + pw + "]";
	}
	
	
	
}
