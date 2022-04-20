package com.cis.easyfarm.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorDetails {

    @SerializedName("ROCFileBytes")
    @Expose
    private String rOCFileBytes;
    @SerializedName("INCFileBytes")
    @Expose
    private String iNCFileBytes;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("UserCode")
    @Expose
    private String userCode;
    @SerializedName("VendorTypeId")
    @Expose
    private Integer vendorTypeId;
    @SerializedName("VendorCategoryId")
    @Expose
    private Integer vendorCategoryId;
    @SerializedName("GSTNumber")
    @Expose
    private String gSTNumber;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("DistrictId")
    @Expose
    private Integer districtId;
    @SerializedName("MandalId")
    @Expose
    private Integer mandalId;
    @SerializedName("VillageId")
    @Expose
    private Integer villageId;
    @SerializedName("ROCFileName")
    @Expose
    private String rOCFileName;
    @SerializedName("ROCFileLocation")
    @Expose
    private String rOCFileLocation;
    @SerializedName("ROCFileExtension")
    @Expose
    private String rOCFileExtension;
    @SerializedName("CINNumber")
    @Expose
    private String cINNumber;
    @SerializedName("INCFileName")
    @Expose
    private String iNCFileName;
    @SerializedName("INCFileLocation")
    @Expose
    private String iNCFileLocation;
    @SerializedName("INCFileExtension")
    @Expose
    private String iNCFileExtension;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("CreatedByUserId")
    @Expose
    private Integer createdByUserId;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("UpdatedByUserId")
    @Expose
    private Integer updatedByUserId;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("ServerUpdatedStatus")
    @Expose
    private Boolean serverUpdatedStatus;

    public String getROCFileBytes() {
        return rOCFileBytes;
    }

    public void setROCFileBytes(String rOCFileBytes) {
        this.rOCFileBytes = rOCFileBytes;
    }

    public String getINCFileBytes() {
        return iNCFileBytes;
    }

    public void setINCFileBytes(String iNCFileBytes) {
        this.iNCFileBytes = iNCFileBytes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getVendorTypeId() {
        return vendorTypeId;
    }

    public void setVendorTypeId(Integer vendorTypeId) {
        this.vendorTypeId = vendorTypeId;
    }

    public Integer getVendorCategoryId() {
        return vendorCategoryId;
    }

    public void setVendorCategoryId(Integer vendorCategoryId) {
        this.vendorCategoryId = vendorCategoryId;
    }

    public String getGSTNumber() {
        return gSTNumber;
    }

    public void setGSTNumber(String gSTNumber) {
        this.gSTNumber = gSTNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getMandalId() {
        return mandalId;
    }

    public void setMandalId(Integer mandalId) {
        this.mandalId = mandalId;
    }

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public String getROCFileName() {
        return rOCFileName;
    }

    public void setROCFileName(String rOCFileName) {
        this.rOCFileName = rOCFileName;
    }

    public String getROCFileLocation() {
        return rOCFileLocation;
    }

    public void setROCFileLocation(String rOCFileLocation) {
        this.rOCFileLocation = rOCFileLocation;
    }

    public String getROCFileExtension() {
        return rOCFileExtension;
    }

    public void setROCFileExtension(String rOCFileExtension) {
        this.rOCFileExtension = rOCFileExtension;
    }

    public String getCINNumber() {
        return cINNumber;
    }

    public void setCINNumber(String cINNumber) {
        this.cINNumber = cINNumber;
    }

    public String getINCFileName() {
        return iNCFileName;
    }

    public void setINCFileName(String iNCFileName) {
        this.iNCFileName = iNCFileName;
    }

    public String getINCFileLocation() {
        return iNCFileLocation;
    }

    public void setINCFileLocation(String iNCFileLocation) {
        this.iNCFileLocation = iNCFileLocation;
    }

    public String getINCFileExtension() {
        return iNCFileExtension;
    }

    public void setINCFileExtension(String iNCFileExtension) {
        this.iNCFileExtension = iNCFileExtension;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(Integer updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Boolean getServerUpdatedStatus() {
        return serverUpdatedStatus;
    }

    public void setServerUpdatedStatus(Boolean serverUpdatedStatus) {
        this.serverUpdatedStatus = serverUpdatedStatus;
    }
}
