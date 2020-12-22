package com.wanglong.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wanglong
 * @date 2020/12/20 21:53
 * @desc
 */
public class PreOrder implements Serializable {
    private static final long serialVersionUID = 2705785195189874291L;

    //预定订单id
    private Long id;

    //客户id
    private Account account;

    //房型id
    private RoomType roomType;

    //预定者姓名
    private String name;

    //身份证号码
    private String idCard;

    //手机号
    private String mobile;

    //状态：0：预定中，1：已入住,2:已结算离店
    private String status;

    //入住日期
    private Date arriveDate;
    private String arriveDateStr;

    //离店日期
    private Date leaveDate;
    private String leaveDateStr;

    //预定日期
    private Date createTime;
    private String createTimeStr;

    //备注
    private String remark;


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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getArriveDateStr() {
        return "20"+new SimpleDateFormat().format(arriveDate);
    }

    public void setArriveDateStr(String arriveDateStr) {
        this.arriveDateStr = arriveDateStr;
    }

    public java.util.Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(java.util.Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getLeaveDateStr() {
        return "20"+new SimpleDateFormat().format(this.leaveDate);
    }

    public void setLeaveDateStr(String leaveDateStr) {
        this.leaveDateStr = leaveDateStr;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getCreateTimeStr() {
        SimpleDateFormat format = new SimpleDateFormat();
        return "20"+format.format(this.createTime);
    }

    public void setCreateTimeStr(String creaeTimeStr) {

        this.createTimeStr = creaeTimeStr;
    }

    @Override
    public String toString() {
        return "PreOrder{" +
                "id=" + id +
                ", account=" + account +
                ", roomType=" + roomType +
                ", name='" + name + '\'' +
                ", idCard='" + idCard + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status='" + status + '\'' +
                ", arriveDate=" + arriveDate +
                ", arriveDateStr='" + arriveDateStr + '\'' +
                ", leaveDate=" + leaveDate +
                ", leaveDateStr='" + leaveDateStr + '\'' +
                ", createTime=" + createTime +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
