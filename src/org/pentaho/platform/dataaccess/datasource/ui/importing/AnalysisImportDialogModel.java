/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright 2008 - 2009 Pentaho Corporation.  All rights reserved.
 *
 *
 * Created December 08, 2011
 * @author Ezequiel Cuellar
 */

package org.pentaho.platform.dataaccess.datasource.ui.importing;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.gwt.widgets.client.utils.i18n.ResourceBundle;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.platform.dataaccess.datasource.beans.Connection;
import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class AnalysisImportDialogModel extends XulEventSourceAdapter {

	private List<Connection> connectionList;
	private List<ParameterDialogModel> analysisParameters;
	private String uploadedFile;
	private Connection connection;
	private boolean isParameterMode;
	private ParameterDialogModel selectedAnalysisParameter;
	private ResourceBundle resBundle;
	private StringBuffer errors;

	public AnalysisImportDialogModel(ResourceBundle bundle) {
		resBundle = bundle;
		connectionList = new ArrayList<Connection>();
		analysisParameters = new ArrayList<ParameterDialogModel>();
	}

	public void addParameter(String name, String value) {

		if (selectedAnalysisParameter == null) {
			analysisParameters.add(new ParameterDialogModel(name, value));
		} else {
			selectedAnalysisParameter.setName(name);
			selectedAnalysisParameter.setValue(value);
		}
		this.firePropertyChange("analysisParameters", null, analysisParameters);
	}

	public void removeParameter(int paramIndex) {
		selectedAnalysisParameter = null;
		analysisParameters.remove(paramIndex);
		this.firePropertyChange("analysisParameters", null, analysisParameters);
	}

	public void removeAllParameters() {
		analysisParameters.clear();
		this.firePropertyChange("analysisParameters", null, analysisParameters);
	}

	public String getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(String uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	@Bindable
	public List<ParameterDialogModel> getAnalysisParameters() {
		return analysisParameters;
	}

	@Bindable
	public void setAnalysisParameters(List<ParameterDialogModel> value) {
		List<ParameterDialogModel> previousValue = analysisParameters;
		this.analysisParameters = value;
		this.firePropertyChange("analysisParameters", previousValue, value);
	}

	@Bindable
	public List<Connection> getConnectionList() {
		return connectionList;
	}

	@Bindable
	public void setConnectionList(List<Connection> value) {
		List<Connection> previousValue = connectionList;
		this.connectionList = value;
		this.firePropertyChange("connectionList", previousValue, value);
	}

	@Bindable
	public Connection getConnection() {
		return connection;
	}

	@Bindable
	public void setConnection(Connection value) {
		Connection previousValue = connection;
		connection = value;
		firePropertyChange("connection", previousValue, value);
	}

	public String getParameters() {
		String result = "";
		if (isParameterMode) {
			for (ParameterDialogModel currentParameter : analysisParameters) {
				result = result + currentParameter.getName() + "=" + currentParameter.getValue() + ";";
			}
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public void setParameterMode(boolean value) {
		isParameterMode = value;
	}

	public boolean isValid() {
		errors = new StringBuffer();
		if(isParameterMode && analysisParameters.size() == 0) {
			errors.append("-" + resBundle.getString("importDialog.PARAMETERS_ERROR") + "\n");
		}
		if(uploadedFile == null) {
			errors.append("-" + resBundle.getString("importDialog.FILE_ERROR") + "\n");
		}
		if(connection == null) {
			errors.append("-" + resBundle.getString("importDialog.CONNECTION_ERROR") + "\n");
		}
		return StringUtils.isEmpty(errors.toString());
	}
	
	public String getErrors() {
		return errors.toString();
	}

	public void setSelectedAnalysisParameter(int index) {
		if (index > -1) {
			selectedAnalysisParameter = analysisParameters.get(index);
		} else {
			selectedAnalysisParameter = null;
		}
	}

	public ParameterDialogModel getSelectedAnalysisParameter() {
		return selectedAnalysisParameter;
	}
}
