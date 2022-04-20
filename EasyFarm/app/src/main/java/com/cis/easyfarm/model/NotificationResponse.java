package com.cis.easyfarm.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse {

    @SerializedName("ListResult")
    @Expose
    private List<ListResult> listResult = null;
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

    public List<ListResult> getListResult() {
        return listResult;
    }

    public void setListResult(List<ListResult> listResult) {
        this.listResult = listResult;
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

    public class ListResult {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("Desc")
        @Expose
        private String desc;
        @SerializedName("RaisedByUserId")
        @Expose
        private Integer raisedByUserId;
        @SerializedName("IsRead")
        @Expose
        private Boolean isRead;
        @SerializedName("NotificationTypeId")
        @Expose
        private Integer notificationTypeId;
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
        @SerializedName("Header")
        @Expose
        private String header;
        @SerializedName("ServerUpdatedStatus")
        @Expose
        private Boolean serverUpdatedStatus;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getRaisedByUserId() {
            return raisedByUserId;
        }

        public void setRaisedByUserId(Integer raisedByUserId) {
            this.raisedByUserId = raisedByUserId;
        }

        public Boolean getIsRead() {
            return isRead;
        }

        public void setIsRead(Boolean isRead) {
            this.isRead = isRead;
        }

        public Integer getNotificationTypeId() {
            return notificationTypeId;
        }

        public void setNotificationTypeId(Integer notificationTypeId) {
            this.notificationTypeId = notificationTypeId;
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

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
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
