package org.sz.platform.system.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sz.core.model.BaseModel;

public class Resources extends BaseModel {
	public static final String LOGO_PATH = "/styles/default/images/logo";
	public static final String ICON_PATH = "/styles/default/images/resicon";
	public static final String ICON_TYPE = "PNG|JPG|JPEG|GIF";
	public static final String ICON_DEFAULT_FOLDER = "styles/default/images/icon/tree_folder.gif";
	public static final String ICON_DEFAULT_LEAF = "styles/default/images/icon/tree_file.gif";
	public static final long ROOT_PID = -1L;
	public static final long ROOT_ID = 0L;
	public static final String ROOT_ICON = "/styles/default/images/icon/remoteupload.gif";
	public static final Short IS_OPEN_N = 0;
	public static final Short IS_OPEN_Y = 1;

	public static final Short IS_FOLDER_N = 0;
	public static final Short IS_FOLDER_Y = 1;

	public static final Short IS_DISPLAY_IN_MENU_N = 0;
	public static final Short IS_DISPLAY_IN_MENU_Y = 1;
	public static final String IS_CHECKED_N = "false";
	public static final String IS_CHECKED_Y = "true";

	protected Long resId;
	protected String resName;
	protected String alias;
	protected Integer sn;
	protected String icon;
	protected Long parentId;
	protected String defaultUrl;
	protected Short isFolder = IS_FOLDER_N;
	protected Short isDisplayInMenu;
	protected Short isOpen;
	protected Long systemId;
	protected String checked = "false";

	protected String param;

	public String getChecked() {
		return this.checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public Long getResId() {
		return this.resId;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResName() {
		return this.resName;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		if ((this.icon == null) || (this.icon.indexOf(".") < 0)) {
			if ((this.isFolder != null) && (this.isFolder.equals(IS_FOLDER_Y)))
				this.icon = "styles/default/images/icon/tree_folder.gif";
			else if ((this.isFolder != null)
					&& (this.isFolder.equals(IS_FOLDER_N))) {
				this.icon = "styles/default/images/icon/tree_file.gif";
			}
		}
		return this.icon;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public String getDefaultUrl() {
		return this.defaultUrl;
	}

	public void setIsFolder(Short isFolder) {
		this.isFolder = isFolder;
	}

	public Short getIsFolder() {
		return this.isFolder;
	}

	public void setIsDisplayInMenu(Short isDisplayInMenu) {
		this.isDisplayInMenu = isDisplayInMenu;
	}

	public Short getIsDisplayInMenu() {
		return this.isDisplayInMenu;
	}

	public void setIsOpen(Short isOpen) {
		this.isOpen = isOpen;
	}

	public Short getIsOpen() {
		return this.isOpen;
	}

	public String getOpen() {
		if ((this.isOpen != null) && (this.isOpen.shortValue() == 1))
			return "true";
		return "false";
	}

	public String getIsParent() {
		if ((this.isFolder != null) && (this.isFolder.shortValue() == 1))
			return "true";
		if ((this.isFolder != null) && (this.isFolder.shortValue() == 0))
			return "false";
		return "";
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public Long getSystemId() {
		return this.systemId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public boolean equals(Object object) {
		if (!(object instanceof Resources)) {
			return false;
		}
		Resources rhs = (Resources) object;
		return new EqualsBuilder().append(this.resId, rhs.resId)
				.append(this.resName, rhs.resName)
				.append(this.alias, rhs.alias).append(this.sn, rhs.sn)
				.append(this.icon, rhs.icon)
				.append(this.parentId, rhs.parentId)
				.append(this.defaultUrl, rhs.defaultUrl)
				.append(this.isFolder, rhs.isFolder)
				.append(this.isDisplayInMenu, rhs.isDisplayInMenu)
				.append(this.isOpen, rhs.isOpen)
				.append(this.systemId, rhs.systemId).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.resId)
				.append(this.resName).append(this.alias).append(this.sn)
				.append(this.icon).append(this.parentId)
				.append(this.defaultUrl).append(this.isFolder)
				.append(this.isDisplayInMenu).append(this.isOpen)
				.append(this.systemId).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("resId", this.resId)
				.append("resName", this.resName).append("alias", this.alias)
				.append("sn", this.sn).append("icon", this.icon)
				.append("parentId", this.parentId)
				.append("defaultUrl", this.defaultUrl)
				.append("isFolder", this.isFolder)
				.append("isDisplayInMenu", this.isDisplayInMenu)
				.append("isOpen", this.isOpen)
				.append("systemId", this.systemId).toString();
	}
}
