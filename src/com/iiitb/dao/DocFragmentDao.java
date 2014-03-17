package com.iiitb.dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.ext.Status;
import com.db4o.query.Predicate;
import com.db4o.types.Blob;
import com.iiitb.model.DocFragment;
import com.iiitb.model.DocFragmentInfo;
import com.iiitb.model.DocFragmentVersionInfo;
import com.iiitb.util.ConnectionPool;

class Predicate1 extends Predicate<DocFragment>
{
	String username;

	Predicate1(String username)
	{
		this.username = username;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getInfo().getAccessors().get(username) != null;
	}
}

class Predicate2 extends Predicate<DocFragment>
{
	String username;

	Predicate2(String username)
	{
		this.username = username;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getInfo().isReusable();
	}
}

class Predicate3 extends Predicate<DocFragment>
{
	String username;

	Predicate3(String username)
	{
		this.username = username;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getInfo().isStandAlone();
	}
}

class DocumentNameAndVersionPredicate extends Predicate<DocFragment>
{
	String documentName;

	String version;

	DocumentNameAndVersionPredicate(String documentName)
	{
		this.documentName = documentName;
		this.version = version;
	}

	public boolean match(DocFragment arg0)
	{
		return arg0.getDocId().equals(documentName);
	}
}

public class DocFragmentDao
{
	private ObjectContainer db;

	public ObjectContainer getDb()
	{
		return db;
	}

	public void setDb(ObjectContainer db)
	{
		this.db = db;
	}

	public DocFragmentDao()
	{
	}

	public DocFragmentDao(ObjectContainer db)
	{
		this.db = db;
	}

	public List<DocFragment> getDocFragments(String username)
	{
		Predicate1 p = new Predicate1(username);
		List<DocFragment> l = db.query(p);
		return l;
	}

	public List<DocFragment> getReusableDocFragments(String username)
	{
		Predicate2 p = new Predicate2(username);
		List<DocFragment> l = db.query(p);

		HashMap<String, DocFragment> frags = new HashMap<String, DocFragment>();
		for (DocFragment df : l)
			frags.put(df.getDocId(), df);

		return l;
	}

	public List<DocFragment> getStandaloneDocFragments(String username)
	{
		Predicate3 p = new Predicate3(username);
		List<DocFragment> l = db.query(p);
		return l;
	}

	public DocFragment getFragment(String documentName)
	{

		DocumentNameAndVersionPredicate p = new DocumentNameAndVersionPredicate(documentName);

		List<DocFragment> l = db.query(p);

		DocFragment fragment = null;
		if (l != null && l.size() > 0)
		{
			fragment = l.get(0);
		}
		return fragment;

	}

	public boolean storeDocFragment(DocFragment df, File file)
	{
		this.db.store(df);
		if (file == null)
			return true;
		double status = -10.0;
		try
		{
			Blob blob = df.getBlob();
			blob.readFrom(file);

			status = blob.getStatus();
			while (status > Status.COMPLETED)
			{
				try
				{
					Thread.sleep(50);
					status = blob.getStatus();
				}
				catch (InterruptedException ex)
				{
					System.out.println(ex.getMessage());
				}
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return (status == Status.COMPLETED);
	}
}