/*
 * Copyright 2015 JIHU, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.giiwa.cms.web;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.giiwa.framework.web.IListener;
import org.giiwa.framework.web.Module;

// TODO: Auto-generated Javadoc
public class CmsListener implements IListener {

	static Log log = LogFactory.getLog(CmsListener.class);

	/* (non-Javadoc)
	 * @see org.giiwa.framework.web.IListener#onStart(org.apache.commons.configuration.Configuration, org.giiwa.framework.web.Module)
	 */
	@Override
	public void onStart(Configuration conf, Module m) {
		// TODO Auto-generated method stub
		log.info("cms is starting ...");

	}

	/* (non-Javadoc)
	 * @see org.giiwa.framework.web.IListener#onStop()
	 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.giiwa.framework.web.IListener#uninstall(org.apache.commons.configuration.Configuration, org.giiwa.framework.web.Module)
	 */
	@Override
	public void uninstall(Configuration conf, Module m) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.giiwa.framework.web.IListener#upgrade(org.apache.commons.configuration.Configuration, org.giiwa.framework.web.Module)
	 */
	@Override
	public void upgrade(Configuration conf, Module m) {
		// TODO Auto-generated method stub

	}

}
