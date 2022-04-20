package com.cis.easyfarm.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDetailsHistory {
    @SerializedName("FileBytes")
    @Expose
    private String fileBytes;

    @SerializedName("BankPassbookFileBytes")
    @Expose
    private String bankPassbookFileBytes;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("UserCode")
    @Expose
    private String userCode;
    @SerializedName("BankId")
    @Expose
    private Integer bankId;
    @SerializedName("BPFileName")
    @Expose
    private String bPFileName;
    @SerializedName("BPFileLocation")
    @Expose
    private String bPFileLocation;
    @SerializedName("BPFileExtension")
    @Expose
    private String bPFileExtension;
    @SerializedName("AccountHolderName")
    @Expose
    private String accountHolderName;
    @SerializedName("AccountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("FileName")
    @Expose
    private String fileName;
    @SerializedName("FileLocation")
    @Expose
    private String fileLocation;
    @SerializedName("FileExtension")
    @Expose
    private String fileExtension;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
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
    private Integer serverUpdatedStatus;

    public String getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(String fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getBankPassbookFileBytes() {
        return bankPassbookFileBytes;
    }

    public void setBankPassbookFileBytes(String bankPassbookFileBytes) {
        this.bankPassbookFileBytes = bankPassbookFileBytes;
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

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBPFileName() {
        return bPFileName;
    }

    public void setBPFileName(String bPFileName) {
        this.bPFileName = bPFileName;
    }

    public String getBPFileLocation() {
        return bPFileLocation;
    }

    public void setBPFileLocation(String bPFileLocation) {
        this.bPFileLocation = bPFileLocation;
    }

    public String getBPFileExtension() {
        return bPFileExtension;
    }

    public void setBPFileExtension(String bPFileExtension) {
        this.bPFileExtension = bPFileExtension;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
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

    public Integer getServerUpdatedStatus() {
        return serverUpdatedStatus;
    }

    public void setServerUpdatedStatus(Integer serverUpdatedStatus) {
        this.serverUpdatedStatus = serverUpdatedStatus;
    }

}

