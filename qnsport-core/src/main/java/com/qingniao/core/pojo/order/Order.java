package com.qingniao.core.pojo.order;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable {
    /**
     * ID或订单号
     */
    private Long id;

    /**
     * 运费
     */
    private Float deliverFee;

    /**
     * 应付金额
     */
    private Float totalFee;

    /**
     * 订单金额
     */
    private Float orderPrice;

    /**
     * 支付方式 0:到付 1:在线 2:邮局 3:公司转帐
     */
    private Integer paymentWay;

    /**
     * 货到付款方式.1现金,2POS刷卡
     */
    private Integer paymentCash;

    /**
     * 送货时间
     */
    private Integer delivery;

    /**
     * 是否电话确认 1:是  0: 否
     */
    private Boolean isConfirm;

    /**
     * 支付状态 :0到付1待付款,2已付款,3待退款,4退款成功,5退款失败
     */
    private Integer isPaiy;

    /**
     * 订单状态 0:提交订单 1:仓库配货 2:商品出库 3:等待收货 4:完成 5待退货 6已退货
     */
    private Integer orderState;

    /**
     * 订单生成时间
     */
    private Date createDate;

    /**
     * 附言
     */
    private String note;

    /**
     * 用户名
     */
    private String buyerId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDeliverFee() {
        return deliverFee;
    }

    public void setDeliverFee(Float deliverFee) {
        this.deliverFee = deliverFee;
    }

    public Float getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Float totalFee) {
        this.totalFee = totalFee;
    }

    public Float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(Integer paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Integer getPaymentCash() {
        return paymentCash;
    }

    public void setPaymentCash(Integer paymentCash) {
        this.paymentCash = paymentCash;
    }
  
    public Integer getDelivery(){
    	return delivery;
    }

    public void setDelivery(Integer delivery) {
        this.delivery = delivery;
    }

    public Boolean getIsConfirm(){
    	return isConfirm;
    }
    public void setIsConfirm(Boolean isConfirm) {
        this.isConfirm = isConfirm;
    }

    public Integer getIsPaiy() {
        return isPaiy;
    }

    public void setIsPaiy(Integer isPaiy) {
        this.isPaiy = isPaiy;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }
    /**
     * 下单时间
     * @return
     */
    public String getCreateDate() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String date = sdf.format(createDate);
        return date;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }
    
    //预计到货时间
    public String getComputeTime(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    	//生成订单三天之后
    	String time = sdf.format(new Date(createDate.getTime()+1000*60*60*24*3));
    	return time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deliverFee=").append(deliverFee);
        sb.append(", totalFee=").append(totalFee);
        sb.append(", orderPrice=").append(orderPrice);
        sb.append(", paymentWay=").append(paymentWay);
        sb.append(", paymentCash=").append(paymentCash);
        sb.append(", delivery=").append(delivery);
        sb.append(", isConfirm=").append(isConfirm);
        sb.append(", isPaiy=").append(isPaiy);
        sb.append(", orderState=").append(orderState);
        sb.append(", createDate=").append(createDate);
        sb.append(", note=").append(note);
        sb.append(", buyerId=").append(buyerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
    /**
     * 支付方式 0:到付 1:在线 2:邮局 3:公司转帐
     * @return
     */
    public String getPaymentWayName(){
    	switch (paymentWay) {
		case 0:
			return "货到付款";
		case 1:
			return "在线付款";
		case 2:
			return "邮局汇款";
		case 3:
			return "公司转帐";
		default:
			return "";
		}
    }
    /**
     * 货到付款方式.1现金,2POS刷卡
     * @return
     */
    public String getPaymentCashName(){
    	switch (paymentCash) {
		case 1:
			return "现金";
		case 2:
			return "POS刷卡";
		default:
			return "";
		}
    }
    /**
     * 支付状态 :0到付1待付款,2已付款,3待退款,4退款成功,5退款失败
     * @return
     */
    public String getIsPaiyName(){
    	switch (isPaiy) {
		case 0:
			return "货到付款";
		case 1:
			return "待付款";
		case 2:
			return "已付款";
		case 3:
			return "待退款";
		case 4:
			return "退款成功";
		case 5:
			return "退款失败";
		default:
			return "";
		}
    }
    /**
     * 订单状态 0:提交订单 1:仓库配货 2:商品出库 3:等待收货 4:完成 5待退货 6已退货
     * @return
     */
    public String getStateName(){
    	switch (orderState) {
		case 0:
			return "提交订单";
		case 1:
			return "仓库配货";
		case 2:
			return "商品出库";
		case 3:
			return "等待收货";
		case 4:
			return "完成";
		case 5:
			return "待退货";
		case 6:
			return "已退货";
		default:
			return "";
		}
    }
    /**
     * 送货时间
     * @return
     */
    public String getDeliveryName() {
    		switch (delivery) {
    		case 1:
    			return "工作日，双休日，假日均可送货";
    		case 2:
    			return "只双休日，假日送货";
    		case 3:
    			return "只工作日送货（双休日，节假日不送）";
    		default:
    			return "";
    		}
    }
    
     public String getIsConfirmName() {
		if(isConfirm){
    		return "是";
    	}else if(!isConfirm){
    		return "否";
    	}else{
    		return "";
    	}
}
 
}