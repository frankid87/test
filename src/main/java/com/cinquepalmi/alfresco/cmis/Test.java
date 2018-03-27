package com.cinquepalmi.alfresco.cmis;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
	
public class Test{

	public static final String LOCAL_URL = "http://localhost:8083/alfresco/api/-default-/cmis/versions/1.0/atom";
	public static final String LOCAL_USER = "admin";
	public static final String LOCAL_PSW = "admin";
	
	public static void main(String[] args) {
		final CmisClient cmisClient = new CmisClient();

		final Session session = cmisClient.getSession(LOCAL_URL, LOCAL_USER, LOCAL_PSW);
		final Folder folder = getRandomFolder(5, session.getRootFolder());
		
		Document doc = cmisClient.createDocument("ziAA", folder);
		
		String id = doc.getId();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		cmisClient.delete(session, id);
		
	}
	
	public static Folder getRandomFolder(int index, Folder parent) {
		ItemIterable<CmisObject> ii = parent.getChildren();
		Folder fo = null;
		jump: if(ii != null) {
			Iterator<CmisObject> i = ii.iterator();
			--index;
			while(i.hasNext()) {
				Object o = i.next();
				if(o instanceof Folder) {
					Folder f = (Folder) o;
					if(index == 0) {
						fo = f;
						break jump;
					}
					if((fo = getRandomFolder(index, f)) != null) {
						break jump;
					}
				}				
			}
		}
		return fo;
	}
	
	public static class CmisClient {

		public Session getSession(String atomPubUrl, String userID, String password) {
			Map<String, String> sessionParameters = getSessionParameters();
			sessionParameters.put(SessionParameter.USER, userID);
			sessionParameters.put(SessionParameter.PASSWORD, password);
			sessionParameters.put(SessionParameter.ATOMPUB_URL, atomPubUrl);

			List<Repository> repositories = SessionFactoryImpl.newInstance().getRepositories(sessionParameters);
			
			Session s = null;
			if (repositories != null && !repositories.isEmpty())
				s = repositories.get(0).createSession();
			return s;
		}

		private Map<String, String> getSessionParameters() {
			Map<String, String> sessionParameters = new HashMap<String, String>();
			sessionParameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
			sessionParameters.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");
			return sessionParameters;
		}
		
		public Document createDocument(String name, Folder folder) {
			String docName = name;
			if(docName == null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
				LocalDateTime date = LocalDateTime.now();			
				docName = new String("document_test_") + formatter.format(date);
			}
			
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			properties.put(PropertyIds.NAME, docName);
			
			byte[] content = "Hello World!".getBytes();
			InputStream stream = new ByteArrayInputStream(content);
			ContentStream contentStream = new ContentStreamImpl(null, BigInteger.valueOf(content.length), "text/plain", stream);

			return folder.createDocument(properties, contentStream, null);
		}
		
		public boolean delete(Session session, String id) {
			CmisObject object = session.getObject(id);
			if(object instanceof Document) {
				Document suppDoc = (Document) object;
				suppDoc.delete(true);
				return true;
			}
			return false;
		}
		
		public boolean createAndDelete(Folder folder, Session session) {
			Document doc = this.createDocument(null, folder);
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return this.delete(session, doc.getId());
		}
		
	}

}