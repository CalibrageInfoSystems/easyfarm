package com.cis.easyfarm.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsuranceDetailsObject {

    @SerializedName("FileBytes")
    @Expose
    private String fileBytes;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("CropCycleCode")
    @Expose
    private String cropCycleCode;
    @SerializedName("ProviderId")
    @Expose
    private Integer providerId;
    @SerializedName("InsurancePlan")
    @Expose
    private String insurancePlan;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("SumIssued")
    @Expose
    private Double sumIssued;
    @SerializedName("PremiumAmount")
    @Expose
    private Double premiumAmount;
    @SerializedName("FarmerPencentage")
    @Expose
    private Double farmerPencentage;
    @SerializedName("VFarmerPencentage")
    @Expose
    private Double vFarmerPencentage;
    @SerializedName("BondNumber")
    @Expose
    private String bondNumber;
    @SerializedName("FileName")
    @Expose
    private String fileName;
    @SerializedName("FileLocation")
    @Expose
    private String fileLocation;
    @SerializedName("FileExtention")
    @Expose
    private String fileExtention;
    @SerializedName("StatusTypeId")
    @Expose
    private Integer statusTypeId;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("StatusChangedBy")
    @Expose
    private Integer statusChangedBy;
    @SerializedName("StatusChangedDate")
    @Expose
    private String statusChangedDate;
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

    public String getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(String fileBytes) {
        this.fileBytes = fileBytes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropCycleCode() {
        return cropCycleCode;
    }

    public void setCropCycleCode(String cropCycleCode) {
        this.cropCycleCode = cropCycleCode;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getInsurancePlan() {
        return insurancePlan;
    }

    public void setInsurancePlan(String insurancePlan) {
        this.insurancePlan = insurancePlan;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getSumIssued() {
        return sumIssued;
    }

    public void setSumIssued(Double sumIssued) {
        this.sumIssued = sumIssued;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Double getFarmerPencentage() {
        return farmerPencentage;
    }

    public void setFarmerPencentage(Double farmerPencentage) {
        this.farmerPencentage = farmerPencentage;
    }

    public Double getVFarmerPencentage() {
        return vFarmerPencentage;
    }

    public void setVFarmerPencentage(Double vFarmerPencentage) {
        this.vFarmerPencentage = vFarmerPencentage;
    }

    public String getBondNumber() {
        return bondNumber;
    }

    public void setBondNumber(String bondNumber) {
        this.bondNumber = bondNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public Integer getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(Integer statusTypeId) {
        this.statusTypeId = statusTypeId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getStatusChangedBy() {
        return statusChangedBy;
    }

    public void setStatusChangedBy(Integer statusChangedBy) {
        this.statusChangedBy = statusChangedBy;
    }

    public String getStatusChangedDate() {
        return statusChangedDate;
    }

    public void setStatusChangedDate(String statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
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
