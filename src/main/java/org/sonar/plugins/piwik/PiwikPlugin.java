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

import org.sonar.api.Extension;
import org.sonar.api.Plugin;
import org.sonar.api.Properties;
import org.sonar.api.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * Sonar plug-in to collect usage information of a Sonar instance with the
 * Piwik open-source web analytics software. It must be configured with
 * location of the Piwik instance and the id of a website to which web
 * traffic will be associated with.
 * <p/>
 * For more information see: http://piwik.org/
 *
 * @author <a href="mailto:david@intelliware.ca">David Jones</a>
 */
@Properties({
    @Property(
        key = PiwikPlugin.PIWIK_WEBSITEID_PROPERTY,
        name = "Website ID",
        description = "Example : 2"
    ),
    @Property(
        key = PiwikPlugin.PIWIK_SERVER_PROPERTY,
        name = "Piwik Server",
        description = "Example : piwik.mycompany.com or 192.168.1.2"
    ),
    @Property(
        key = PiwikPlugin.PIWIK_PATH_PROPERTY,
        name = "Relative Path on Server",
        description = "Example : piwik-0.6 when URL is http://piwik.mycompany.com/piwik-0.6/index.php" +
            " or may be empty when Piwik is running at the root of the server"
    )
})
public class PiwikPlugin implements Plugin {

  public static final String PIWIK_WEBSITEID_PROPERTY = "org.sonar.plugins.piwik.website-id";
  public static final String PIWIK_SERVER_PROPERTY = "org.sonar.plugins.piwik.server";
  public static final String PIWIK_PATH_PROPERTY = "org.sonar.plugins.piwik.path";

  public static final String PIWIK_PLUGIN = "org.sonar.plugins.piwik";

  public PiwikPlugin() {
    super();
  }

  public String getKey() {
    return PIWIK_PLUGIN;
  }

  public String getName() {
    return "Piwik";
  }

  public String getDescription() {
    return "Piwik is an open-source web analytics tool that collects data on the traffic of web sites and then," +
        " through a powerful interface, enables access to reporting, goals, charts, and more." +
        " You can learn more by visiting the <a href='http://piwik.org/'>Piwik web site</a>.";
  }

  public List<Class<? extends Extension>> getExtensions() {
    List<Class<? extends Extension>> list = new ArrayList<Class<? extends Extension>>();
    list.add(PiwikWebFooter.class);
    return list;
  }

  @Override
  public String toString() {
    return getKey();
  }
}
