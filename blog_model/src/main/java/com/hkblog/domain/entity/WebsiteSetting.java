package com.hkblog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 站点配置信息设置
 * @TableName tb_website_setting
 */
@TableName(value ="tb_website_setting")
@Data
public class WebsiteSetting implements Serializable {
    /**
     * 网站标题
     */
    @TableField(value = "web_name")
    private String webName;

    /**
     * 网站状态：1开启,0关闭
     */
    @TableField(value = "web_status")
    private Integer webStatus;

    /**
     * 网站图标
     */
    @TableField(value = "web_logo")
    private String webLogo;

    /**
     * 网站二维码
     */
    @TableField(value = "web_qrcode")
    private String webQrcode;

    /**
     * 网站站点描述
     */
    @TableField(value = "web_desc")
    private String webDesc;

    /**
     * 网站站点版权所有
     */
    @TableField(value = "web_copyright")
    private String webCopyright;

    /**
     * 网站版本
     */
    @TableField(value = "version")
    private String version;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 
     */
    @TableLogic
    @TableField(value = "deleted")
    private Boolean deleted;

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
        WebsiteSetting other = (WebsiteSetting) that;
        return (this.getWebName() == null ? other.getWebName() == null : this.getWebName().equals(other.getWebName()))
            && (this.getWebStatus() == null ? other.getWebStatus() == null : this.getWebStatus().equals(other.getWebStatus()))
            && (this.getWebLogo() == null ? other.getWebLogo() == null : this.getWebLogo().equals(other.getWebLogo()))
            && (this.getWebQrcode() == null ? other.getWebQrcode() == null : this.getWebQrcode().equals(other.getWebQrcode()))
            && (this.getWebDesc() == null ? other.getWebDesc() == null : this.getWebDesc().equals(other.getWebDesc()))
            && (this.getWebCopyright() == null ? other.getWebCopyright() == null : this.getWebCopyright().equals(other.getWebCopyright()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleted() == null ? other.getDeleted() == null : this.getDeleted().equals(other.getDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWebName() == null) ? 0 : getWebName().hashCode());
        result = prime * result + ((getWebStatus() == null) ? 0 : getWebStatus().hashCode());
        result = prime * result + ((getWebLogo() == null) ? 0 : getWebLogo().hashCode());
        result = prime * result + ((getWebQrcode() == null) ? 0 : getWebQrcode().hashCode());
        result = prime * result + ((getWebDesc() == null) ? 0 : getWebDesc().hashCode());
        result = prime * result + ((getWebCopyright() == null) ? 0 : getWebCopyright().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleted() == null) ? 0 : getDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", webName=").append(webName);
        sb.append(", webStatus=").append(webStatus);
        sb.append(", webLogo=").append(webLogo);
        sb.append(", webQrcode=").append(webQrcode);
        sb.append(", webDesc=").append(webDesc);
        sb.append(", webCopyright=").append(webCopyright);
        sb.append(", version=").append(version);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}