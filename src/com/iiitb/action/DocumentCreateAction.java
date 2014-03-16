package com.iiitb.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.interceptor.SessionAware;

import com.db4o.ObjectContainer;
import com.iiitb.dao.DocFragmentDao;
import com.iiitb.model.DocFragment;
import com.iiitb.model.User;
import com.iiitb.util.ConnectionPool;
import com.iiitb.util.DMSConstants;
import com.opensymphony.xwork2.ActionSupport;

public class DocumentCreateAction extends ActionSupport implements SessionAware
{
	private User user;
	private Map<String, Object> session;
	private Set<String> docFrags;
	
	public String execute()
	{
		this.setUser((User)session.get(DMSConstants.USER_LOGGED_IN));
		if(this.getUser()==null)
			return LOGIN;
		
		ObjectContainer db=ConnectionPool.getConnection();
		DocFragmentDao dao=new DocFragmentDao(db);
		this.setDocFrags(dao.getReusableDocFragments(this.getUser().getUsername()).keySet());
		ConnectionPool.freeConnection(db);
		return SUCCESS;
	}
	
	public void setSession(Map<String, Object> arg0)
	{
		this.session=arg0;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<String> getDocFrags() {
		return docFrags;
	}

	public void setDocFrags(Set<String> docFrags) {
		this.docFrags = docFrags;
	}
}
