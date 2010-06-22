/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2010 Intelliware Development Inc.
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
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
  public void shouldSkipOutputWhenWebsiteIdNotSet() {
    PiwikWebFooter subject = new PiwikWebFooter(configuration);
    assertThat(subject.getHtml(), nullValue());
  }

  @Test
  public void shouldIncludePiwikScriptWhenWebsiteIdPresent() {
    withValidWebsiteId();

    PiwikWebFooter subject = new PiwikWebFooter(configuration);
    assertThat(subject.getHtml(), notNullValue());
  }

  @Test
  public void shouldBuildServerPathFromServerAndPath() {
    withValidWebsiteId();

    when(configuration.getString(eq(PiwikPlugin.PIWIK_SERVER_PROPERTY), anyString())).thenReturn("server.com");
    when(configuration.getString(eq(PiwikPlugin.PIWIK_PATH_PROPERTY), anyString())).thenReturn("test/path");

    PiwikWebFooter subject = new PiwikWebFooter(configuration);
    assertThat(subject.getHtml(), containsString("http://server.com/test/path/"));
  }

  private void withValidWebsiteId() {
    when(configuration.getString(eq(PiwikPlugin.PIWIK_WEBSITEID_PROPERTY), anyString())).thenReturn("TestId");
  }
}
