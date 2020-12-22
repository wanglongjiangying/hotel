package com.wanglong.pojo;

import java.io.Serializable;

/**
 * @author wanglong
 * @date 2020/12/13 14:31
 * @desc
 */

public class Room implements Serializable {
    private static final long serialVersionUID = 765741477170117065L;
    //房间id
    private Long id;

    //房间图片
    private String photo;

    //房间价格
    private String price;

    //房间编号
    private String code;

    //房型id
    private RoomType roomType;

    //所属楼层id
    private Floor floor;

    //房型状态，0：可入住,1:已入住,2:打扫中
    private String status;

    //房型备注
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", photo='" + photo + '\'' +
                ", price='" + price + '\'' +
                ", code='" + code + '\'' +
                ", roomType=" + roomType +
                ", floor=" + floor +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
