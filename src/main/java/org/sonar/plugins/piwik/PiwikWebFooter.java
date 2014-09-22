/*
 * Piwik
 * Copyright (C) 2010 Intelliware Development Inc.
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.piwik;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.web.Footer;

/**
 * Attach the Piwik javascript tracking tag before the close of the body tag for each page of the site.
 * <p/>
 * For more information see: http://piwik.org/docs/javascript-tracking/
 *
 * @author <a href="mailto:david@intelliware.ca">David Jones</a>
 */
public class PiwikWebFooter implements Footer {

  private Settings settings;

  public PiwikWebFooter(Settings settings) {
    this.settings = settings;
  }

  protected String getIdAccount() {
    return settings.getString(PiwikPlugin.PIWIK_WEBSITEID_PROPERTY);
  }

  protected String getServer() {
    return settings.getString(PiwikPlugin.PIWIK_SERVER_PROPERTY);
  }

  protected String getPath() {
    return settings.getString(PiwikPlugin.PIWIK_PATH_PROPERTY);
  }

  public String getHtml() {
    String id = getIdAccount();
    String server = getServer();
    if (StringUtils.isBlank(id) || StringUtils.isBlank(server)) {
      return null;
    }
    String serverPath = getServerPath();
    return "<!-- Piwik -->\n"
      + "<script type=\"text/javascript\">\n"
      + "var pkBaseURL = ((\"https:\" == document.location.protocol) ? \"https://" + serverPath + "/\" : \"http://" + serverPath
      + "/\");\n"
      + "document.write(unescape(\"%3Cscript src='\" + pkBaseURL + \"piwik.js' type='text/javascript'%3E%3C/script%3E\"));\n"
      + "</script><script type=\"text/javascript\">\n"
      + "try {\n"
      + "var piwikTracker = Piwik.getTracker(pkBaseURL + \"piwik.php\", " + id + ");\n"
      + "piwikTracker.trackPageView();\n" + "piwikTracker.enableLinkTracking();\n"
      + "} catch( err ) {}\n"
      + "</script><noscript><p><img src=\"http://" + serverPath + "/piwik.php?idsite=" + id + "\" style=\"border:0\" alt=\"\"/></p></noscript>\n"
      + "<!-- End Piwik Tag -->";
  }

  private String getServerPath() {
    String serverPath = getServer();
    if (getPath() != null && !"".equals(getPath())) {
      serverPath += "/" + getPath();
    }
    return serverPath;
  }

}
