package org.sz.platform.bpm.dao.flow;

public interface BpmDao {

	String getDefXmlByDeployId(String deployId);

	void wirteDefXml(final String deployId, String defXml);

}