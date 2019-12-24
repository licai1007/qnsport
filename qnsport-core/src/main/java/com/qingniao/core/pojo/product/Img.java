package com.qingniao.core.pojo.product;

import java.io.Serializable;

import com.qingniao.core.common.SERVER_URL;

public class Img implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 图片URL
     */
    private String url;

    /**
     * 是否默认:0否 1:是
     */
    private Boolean isDef;
    
    private String imgUrl;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Boolean getIsDef() {
        return isDef;
    }

    public void setIsDef(Boolean isDef) {
        this.isDef = isDef;
    }
    
    public String getImgUrl() {
		return SERVER_URL.IMG_URL+url;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", url=").append(url);
        sb.append(", isDef=").append(isDef);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}