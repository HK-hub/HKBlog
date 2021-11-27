package com.hkblog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 访问记录
 * @TableName tb_visit_log
 */
@TableName(value ="tb_visit_log")
@Data
public class VisitLog implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 访客标识码
     */
    @TableField(value = "tourists_id")
    private String touristsId;

    /**
     * 请求接口
     */
    @TableField(value = "uri")
    private String uri;

    /**
     * 请求方式
     */
    @TableField(value = "method")
    private String method;

    /**
     * 请求参数
     */
    @TableField(value = "param")
    private String param;

    /**
     * 访问行为
     */
    @TableField(value = "behavior")
    private String behavior;

    /**
     * 访问内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * ip
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * ip来源
     */
    @TableField(value = "ip_source")
    private String ipSource;

    /**
     * 操作系统
     */
    @TableField(value = "os")
    private String os;

    /**
     * 浏览器
     */
    @TableField(value = "browser")
    private String browser;

    /**
     * 请求耗时（毫秒）
     */
    @TableField(value = "times")
    private Integer times;

    /**
     * 访问时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * user-agent用户代理
     */
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 
     */
    @TableLogic
    @TableField(value = "deleted")
    private Boolean deleted;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        VisitLog other = (VisitLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTouristsId() == null ? other.getTouristsId() == null : this.getTouristsId().equals(other.getTouristsId()))
            && (this.getUri() == null ? other.getUri() == null : this.getUri().equals(other.getUri()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getParam() == null ? other.getParam() == null : this.getParam().equals(other.getParam()))
            && (this.getBehavior() == null ? other.getBehavior() == null : this.getBehavior().equals(other.getBehavior()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getIpSource() == null ? other.getIpSource() == null : this.getIpSource().equals(other.getIpSource()))
            && (this.getOs() == null ? other.getOs() == null : this.getOs().equals(other.getOs()))
            && (this.getBrowser() == null ? other.getBrowser() == null : this.getBrowser().equals(other.getBrowser()))
            && (this.getTimes() == null ? other.getTimes() == null : this.getTimes().equals(other.getTimes()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUserAgent() == null ? other.getUserAgent() == null : this.getUserAgent().equals(other.getUserAgent()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTouristsId() == null) ? 0 : getTouristsId().hashCode());
        result = prime * result + ((getUri() == null) ? 0 : getUri().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getParam() == null) ? 0 : getParam().hashCode());
        result = prime * result + ((getBehavior() == null) ? 0 : getBehavior().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getIpSource() == null) ? 0 : getIpSource().hashCode());
        result = prime * result + ((getOs() == null) ? 0 : getOs().hashCode());
        result = prime * result + ((getBrowser() == null) ? 0 : getBrowser().hashCode());
        result = prime * result + ((getTimes() == null) ? 0 : getTimes().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUserAgent() == null) ? 0 : getUserAgent().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", touristsId=").append(touristsId);
        sb.append(", uri=").append(uri);
        sb.append(", method=").append(method);
        sb.append(", param=").append(param);
        sb.append(", behavior=").append(behavior);
        sb.append(", content=").append(content);
        sb.append(", remark=").append(remark);
        sb.append(", ip=").append(ip);
        sb.append(", ipSource=").append(ipSource);
        sb.append(", os=").append(os);
        sb.append(", browser=").append(browser);
        sb.append(", times=").append(times);
        sb.append(", createTime=").append(createTime);
        sb.append(", userAgent=").append(userAgent);
        sb.append(", deleted=").append(deleted);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}