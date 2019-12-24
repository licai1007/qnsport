package com.qingniao.core.pojo;

import com.qingniao.core.common.SERVER_URL;

/**
 * 品牌pojo
 * @author licai
 *
 */
public class BrandExample {
	private Long id;
	private Long[] ids;
	private String name;
	private String description;
	private String logo;
	private String img;
	private String url;
	private Integer status;//在售：1      停售：0      删除：2
	public BrandExample() {
		super();
	}
	
	public BrandExample(String name, String description, String logo, String url, Integer status) {
		super();
		this.name = name;
		this.description = description;
		this.logo = logo;
		this.url = url;
		this.status = status;
	}

	public BrandExample(Long id, String name, String description, String logo, String url, Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.logo = logo;
		this.url = url;
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getImg() {
		return SERVER_URL.IMG_URL+logo;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", description=" + description + ", img=" + img + ", url=" + url
				+ ", status=" + status + "]";
	}
	
	private Integer pageNo;
	private Integer pageSize = 4;
	private Integer startRow;
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		startRow = (pageNo - 1) * pageSize;
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		startRow = (pageNo - 1) * pageSize;
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
}
