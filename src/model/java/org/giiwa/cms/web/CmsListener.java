package org.giiwa.cms.web;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.giiwa.framework.web.IListener;
import org.giiwa.framework.web.Module;

public class CmsListener implements IListener {

	static Log log = LogFactory.getLog(CmsListener.class);

	@Override
	public void onStart(Configuration conf, Module m) {
		// TODO Auto-generated method stub
		log.info("cms is starting ...");

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void uninstall(Configuration conf, Module m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade(Configuration conf, Module m) {
		// TODO Auto-generated method stub

	}

}
