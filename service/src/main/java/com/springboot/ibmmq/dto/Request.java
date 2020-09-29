package com.springboot.ibmmq.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.ibmmq.validation.CountryCode;
import com.springboot.ibmmq.validation.CustomerRefId;
import com.springboot.ibmmq.validation.SourceCode;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@XmlRootElement(name="Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @XmlElement(name="UniqueId")
    private String uniqueId;

    @JsonIgnore
    @XmlElement(name="Host")
    private String host;

    @ApiModelProperty(notes = "Custoerm ref id", required = true)
    @XmlElement(name="CustomerRefId")
    @CustomerRefId
    private String customerRefId;

    @ApiModelProperty(notes = "name field", required = false)
    @XmlElement(name="Name")
    private String name;

    @ApiModelProperty(notes = "country code field", required = true)
    @XmlElement(name="Country")
    @CountryCode
    private String country;

    @ApiModelProperty(notes = "Source field", required = true)
    @XmlElement(name="Source")
    @SourceCode
    private String source;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
