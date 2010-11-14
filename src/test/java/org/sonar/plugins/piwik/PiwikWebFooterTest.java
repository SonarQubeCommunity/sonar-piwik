/*
 * Sonar Piwik Plugin
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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;

public class PiwikWebFooterTest {

  private Configuration configuration;

  @Before
  public void setUp() {
    configuration = mock(Configuration.class);
  }

  @Test
  public void shouldSkipOutputWhenWebsiteIdOrServerMissing() {
    PiwikWebFooter subject = new PiwikWebFooter(configuration);
    assertThat(subject.getHtml(), nullValue());
  }

  @Test
  public void shouldIncludePiwikScriptWhenWebsiteIdAndServerPresent() {
    withValidWebsiteId();
    withServer("server.com");

    PiwikWebFooter subject = new PiwikWebFooter(configuration);
    assertThat(subject.getHtml(), notNullValue());
  }

  private void withServer(String server) {
    when(configuration.getString(eq(PiwikPlugin.PIWIK_SERVER_PROPERTY), anyString())).thenReturn(server);
  }

  @Test
  public void shouldBuildServerPathFromServerAndPath() {
    withValidWebsiteId();

    withServer("server.com");
    withRelativePath("test/path");

    PiwikWebFooter subject = new PiwikWebFooter(configuration);
    assertThat(subject.getHtml(), containsString("\"http://server.com/test/path/\""));
  }

  @Test
  public void shouldBeOptionalToProvidePath() {
    withValidWebsiteId();

    withServer("server.com");

    PiwikWebFooter subject = new PiwikWebFooter(configuration);
    assertThat(subject.getHtml(), containsString("\"http://server.com/\""));
  }

  private void withRelativePath(String path) {
    when(configuration.getString(eq(PiwikPlugin.PIWIK_PATH_PROPERTY), anyString())).thenReturn(path);
  }

  private void withValidWebsiteId() {
    when(configuration.getString(eq(PiwikPlugin.PIWIK_WEBSITEID_PROPERTY), anyString())).thenReturn("TestId");
  }
}
