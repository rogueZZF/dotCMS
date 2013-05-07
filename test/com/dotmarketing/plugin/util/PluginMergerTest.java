package com.dotmarketing.plugin.util;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.dotcms.TestBase;

public class PluginMergerTest extends TestBase {

	@Test
	public void testMergeByAttribute() throws IOException {
		PluginFileMerger fileMerger = new PluginFileMerger();

		String name = "override-test";
		String dwr = "<create creator=\"new\" javascript=\"UserAjax\" scope=\"application\">\n"
				+ "<param name=\"class\" value=\"com.arqiva.plugins.ajax.ArqivaUserAjax\"/>\n"
				+ "</create>";

		Map<String,String> overrideMap = new HashMap<String, String>();
		overrideMap.put("create", "javascript");

		StringBuilder sb = new StringBuilder("<!DOCTYPE dwr PUBLIC \"-//GetAhead Limited//DTD Direct Web Remoting 3.0//EN\" \"http://getahead.org/dwr//dwr30.dtd\">");
		sb.append("<dwr>");
		sb.append("<allow>");
		sb.append("<create creator=\"new\" javascript=\"UserAjax\" scope=\"application\">");
		sb.append("<param name=\"class\" value=\"com.dotmarketing.portlets.user.ajax.UserAjax\"/>");
		sb.append("</create>");
		sb.append("<!-- Don't ever delete the following comment tags, it will break the plugin system -->");
		sb.append("<!-- BEGIN PLUGINS -->");
		sb.append("<!-- END PLUGINS -->");
		sb.append("</allow>");
		sb.append("</dwr>");

		InputStream input = new ByteArrayInputStream(sb.toString().getBytes());

		String fileContent = fileMerger.mergeByAttribute(input, "<!-- BEGIN PLUGINS -->",
				"<!-- END PLUGINS -->", "<!-- BEGIN PLUGIN:" + name + " -->", "<!-- END PLUGIN:" + name + " -->", dwr,
				overrideMap, "<!-- BEGIN OVERRIDE:" + name, " END OVERRIDE:" + name + " -->", "<!-- BEGIN OVERRIDE");

		String newline = System.getProperty("line.separator");

		String comentedPart = "<!-- BEGIN OVERRIDE:override-test"
				+ newline + "<create creator=\"new\" javascript=\"UserAjax\" scope=\"application\"><param name=\"class\" value=\"com.dotmarketing.portlets.user.ajax.UserAjax\"/></create>"
				+ newline + " END OVERRIDE:override-test -->";

		String newPart = "<!-- BEGIN PLUGIN:override-test -->"
				+ newline + "<create creator=\"new\" javascript=\"UserAjax\" scope=\"application\">"
				+ newline + "<param name=\"class\" value=\"com.arqiva.plugins.ajax.ArqivaUserAjax\"/>"
				+ newline + "</create>"
				+ newline + "<!-- END PLUGIN:override-test -->";

		assertTrue(fileContent.toString().contains(comentedPart));
		assertTrue(fileContent.toString().contains(newPart));


	}

}
