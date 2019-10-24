package com.step.operation.common;

import java.util.Date;

public class DeliveryTask {

    /**
     * "shipOrders": "ship132019082917454151,ship132019082917384512,ship132019082917094816",
     *             "deliver": 23,
     *             "status": 4,
     *             "startTime": null,
     *             "endTime": "2019-07-04T00:00:00.000+0000",
     *             "createTime": "2019-10-19T16:04:04.000+0000",
     *             "updateTime": "2019-10-19T18:12:01.000+0000",
     *             "createUser": null,
     *             "dcode": "yibin_001",
     *             "task_id": "shiptask2019102000035827",
     *             "order_type": 0,
     *             "ship_order_nos": [
     *                 "ship132019082917454151",
     *                 "ship132019082917384512",
     *                 "ship132019082917094816"
     *             ]
     */

    private String[]  ship_order_nos;
    private Integer deliver;
    private Integer status;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date updateTime;
    private String dcode;
    private String task_id;
    private Integer order_type;
    private String createUser;

    public String[] getShip_order_nos() {
        return ship_order_nos;
    }

    public void setShip_order_nos(String[] ship_order_nos) {
        this.ship_order_nos = ship_order_nos;
    }

    public Integer getDeliver() {
        return deliver;
    }

    public void setDeliver(Integer deliver) {
        this.deliver = deliver;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDcode() {
        return dcode;
    }

    public void setDcode(String dcode) {
        this.dcode = dcode;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public Integer getOrder_type() {
        return order_type;
    }

    public void setOrder_type(Integer order_type) {
        this.order_type = order_type;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
