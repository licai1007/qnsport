package com.qingniao.core.pojo;

import com.qingniao.core.common.SERVER_URL;

/**
 * 品牌pojo
 * @author licai
 *
 */
public class Brand {
	private Long id;
	private String name;
	private String description;
	private String logo;
	private String img;
	private String url;
	private Integer status;//在售：1      停售：0      删除：2
	public Brand() {
		super();
	}
	
	public Brand(String name, String description, String logo, String url, Integer status) {
		super();
		this.name = name;
		this.description = description;
		this.logo = logo;
		this.url = url;
		this.status = status;
	}

	public Brand(Long id, String name, String description, String logo, String url, Integer status) {
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

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", description=" + description + ", logo=" + logo + ", url=" + url
				+ ", status=" + status + "]";
	}
	
}
