package com.cis.easyfarm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class plotregistationResponse {

    @SerializedName("Result")
    @Expose
    private Result result;
    @SerializedName("IsSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("AffectedRecords")
    @Expose
    private Integer affectedRecords;
    @SerializedName("EndUserMessage")
    @Expose
    private String endUserMessage;
    @SerializedName("ValidationErrors")
    @Expose
    private Object validationErrors;
    @SerializedName("Exception")
    @Expose
    private Exception exception;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getAffectedRecords() {
        return affectedRecords;
    }

    public void setAffectedRecords(Integer affectedRecords) {
        this.affectedRecords = affectedRecords;
    }

    public String getEndUserMessage() {
        return endUserMessage;
    }

    public void setEndUserMessage(String endUserMessage) {
        this.endUserMessage = endUserMessage;
    }

    public Object getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Object validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }


    public class Result {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Code")
        @Expose
        private String code;
        @SerializedName("FarmerCode")
        @Expose
        private String farmerCode;
        @SerializedName("TotalPlotArea")
        @Expose
        private Double totalPlotArea;
        @SerializedName("AdaptedArea")
        @Expose
        private Double adaptedArea;
        @SerializedName("GPSPlotArea")
        @Expose
        private Double gPSPlotArea;
        @SerializedName("SurveyNumber")
        @Expose
        private String surveyNumber;
        @SerializedName("PassbookNumber")
        @Expose
        private String passbookNumber;
        @SerializedName("PlotOwnerShipTypeId")
        @Expose
        private Integer plotOwnerShipTypeId;
        @SerializedName("OwnerName")
        @Expose
        private String ownerName;
        @SerializedName("OwnerContactNumber")
        @Expose
        private String ownerContactNumber;
        @SerializedName("PlotStatusId")
        @Expose
        private Integer plotStatusId;
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFarmerCode() {
            return farmerCode;
        }

        public void setFarmerCode(String farmerCode) {
            this.farmerCode = farmerCode;
        }

        public Double getTotalPlotArea() {
            return totalPlotArea;
        }

        public void setTotalPlotArea(Double totalPlotArea) {
            this.totalPlotArea = totalPlotArea;
        }

        public Double getAdaptedArea() {
            return adaptedArea;
        }

        public void setAdaptedArea(Double adaptedArea) {
            this.adaptedArea = adaptedArea;
        }

        public Double getGPSPlotArea() {
            return gPSPlotArea;
        }

        public void setGPSPlotArea(Double gPSPlotArea) {
            this.gPSPlotArea = gPSPlotArea;
        }

        public String getSurveyNumber() {
            return surveyNumber;
        }

        public void setSurveyNumber(String surveyNumber) {
            this.surveyNumber = surveyNumber;
        }

        public String getPassbookNumber() {
            return passbookNumber;
        }

        public void setPassbookNumber(String passbookNumber) {
            this.passbookNumber = passbookNumber;
        }

        public Integer getPlotOwnerShipTypeId() {
            return plotOwnerShipTypeId;
        }

        public void setPlotOwnerShipTypeId(Integer plotOwnerShipTypeId) {
            this.plotOwnerShipTypeId = plotOwnerShipTypeId;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getOwnerContactNumber() {
            return ownerContactNumber;
        }

        public void setOwnerContactNumber(String ownerContactNumber) {
            this.ownerContactNumber = ownerContactNumber;
        }

        public Integer getPlotStatusId() {
            return plotStatusId;
        }

        public void setPlotStatusId(Integer plotStatusId) {
            this.plotStatusId = plotStatusId;
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

    public class Exception {

        @SerializedName("ClassName")
        @Expose
        private String className;
        @SerializedName("Message")
        @Expose
        private Object message;
        @SerializedName("Data")
        @Expose
        private Object data;
        @SerializedName("InnerException")
        @Expose
        private Object innerException;
        @SerializedName("HelpURL")
        @Expose
        private String helpURL;
        @SerializedName("StackTraceString")
        @Expose
        private Object stackTraceString;
        @SerializedName("RemoteStackTraceString")
        @Expose
        private Object remoteStackTraceString;
        @SerializedName("RemoteStackIndex")
        @Expose
        private Integer remoteStackIndex;
        @SerializedName("ExceptionMethod")
        @Expose
        private Object exceptionMethod;
        @SerializedName("HResult")
        @Expose
        private Integer hResult;
        @SerializedName("Source")
        @Expose
        private String source;
        @SerializedName("WatsonBuckets")
        @Expose
        private Object watsonBuckets;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getInnerException() {
            return innerException;
        }

        public void setInnerException(Object innerException) {
            this.innerException = innerException;
        }

        public String getHelpURL() {
            return helpURL;
        }

        public void setHelpURL(String helpURL) {
            this.helpURL = helpURL;
        }

        public Object getStackTraceString() {
            return stackTraceString;
        }

        public void setStackTraceString(Object stackTraceString) {
            this.stackTraceString = stackTraceString;
        }

        public Object getRemoteStackTraceString() {
            return remoteStackTraceString;
        }

        public void setRemoteStackTraceString(Object remoteStackTraceString) {
            this.remoteStackTraceString = remoteStackTraceString;
        }

        public Integer getRemoteStackIndex() {
            return remoteStackIndex;
        }

        public void setRemoteStackIndex(Integer remoteStackIndex) {
            this.remoteStackIndex = remoteStackIndex;
        }

        public Object getExceptionMethod() {
            return exceptionMethod;
        }

        public void setExceptionMethod(Object exceptionMethod) {
            this.exceptionMethod = exceptionMethod;
        }

        public Integer getHResult() {
            return hResult;
        }

        public void setHResult(Integer hResult) {
            this.hResult = hResult;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Object getWatsonBuckets() {
            return watsonBuckets;
        }

        public void setWatsonBuckets(Object watsonBuckets) {
            this.watsonBuckets = watsonBuckets;
        }

    }
}

