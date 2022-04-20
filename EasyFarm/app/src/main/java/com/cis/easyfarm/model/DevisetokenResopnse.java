package com.cis.easyfarm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DevisetokenResopnse {
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
    private List<Object> validationErrors = null;
    @SerializedName("Exception")
    @Expose
    private Object exception;

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

    public List<Object> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<Object> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }


public class Result {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("MiddleName")
    @Expose
    private Object middleName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("FatherName_GuardianName")
    @Expose
    private String fatherNameGuardianName;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private Object address2;
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
    @SerializedName("GenderTypeId")
    @Expose
    private Integer genderTypeId;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("PrimaryPhoneNumber")
    @Expose
    private String primaryPhoneNumber;
    @SerializedName("SecondaryPhoneNumber")
    @Expose
    private String secondaryPhoneNumber;
    @SerializedName("AnnualIncomeTypeId")
    @Expose
    private Object annualIncomeTypeId;
    @SerializedName("CategoryTypeId")
    @Expose
    private Integer categoryTypeId;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("EducationTypeId")
    @Expose
    private Integer educationTypeId;
    @SerializedName("EducationDegreeTypeId")
    @Expose
    private Integer educationDegreeTypeId;
    @SerializedName("SourceTypeId")
    @Expose
    private Object sourceTypeId;
    @SerializedName("PPFileName")
    @Expose
    private String pPFileName;
    @SerializedName("PPFileLocation")
    @Expose
    private String pPFileLocation;
    @SerializedName("PPFileExtension")
    @Expose
    private String pPFileExtension;
    @SerializedName("MAFileName")
    @Expose
    private Object mAFileName;
    @SerializedName("MAFileLocation")
    @Expose
    private Object mAFileLocation;
    @SerializedName("MAFileExtension")
    @Expose
    private Object mAFileExtension;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("CreatedByUserId")
    @Expose
    private Object createdByUserId;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("UpdatedByUserId")
    @Expose
    private Integer updatedByUserId;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("EmployeeTypeId")
    @Expose
    private Integer employeeTypeId;
    @SerializedName("ReportingManagerId")
    @Expose
    private Integer reportingManagerId;
    @SerializedName("ExpInYears")
    @Expose
    private Object expInYears;
    @SerializedName("ExpInMonths")
    @Expose
    private Object expInMonths;
    @SerializedName("AccessToken")
    @Expose
    private String accessToken;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherNameGuardianName() {
        return fatherNameGuardianName;
    }

    public void setFatherNameGuardianName(String fatherNameGuardianName) {
        this.fatherNameGuardianName = fatherNameGuardianName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public Object getAddress2() {
        return address2;
    }

    public void setAddress2(Object address2) {
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

    public Integer getGenderTypeId() {
        return genderTypeId;
    }

    public void setGenderTypeId(Integer genderTypeId) {
        this.genderTypeId = genderTypeId;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public Object getAnnualIncomeTypeId() {
        return annualIncomeTypeId;
    }

    public void setAnnualIncomeTypeId(Object annualIncomeTypeId) {
        this.annualIncomeTypeId = annualIncomeTypeId;
    }

    public Integer getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEducationTypeId() {
        return educationTypeId;
    }

    public void setEducationTypeId(Integer educationTypeId) {
        this.educationTypeId = educationTypeId;
    }

    public Integer getEducationDegreeTypeId() {
        return educationDegreeTypeId;
    }

    public void setEducationDegreeTypeId(Integer educationDegreeTypeId) {
        this.educationDegreeTypeId = educationDegreeTypeId;
    }

    public Object getSourceTypeId() {
        return sourceTypeId;
    }

    public void setSourceTypeId(Object sourceTypeId) {
        this.sourceTypeId = sourceTypeId;
    }

    public String getPPFileName() {
        return pPFileName;
    }

    public void setPPFileName(String pPFileName) {
        this.pPFileName = pPFileName;
    }

    public String getPPFileLocation() {
        return pPFileLocation;
    }

    public void setPPFileLocation(String pPFileLocation) {
        this.pPFileLocation = pPFileLocation;
    }

    public String getPPFileExtension() {
        return pPFileExtension;
    }

    public void setPPFileExtension(String pPFileExtension) {
        this.pPFileExtension = pPFileExtension;
    }

    public Object getMAFileName() {
        return mAFileName;
    }

    public void setMAFileName(Object mAFileName) {
        this.mAFileName = mAFileName;
    }

    public Object getMAFileLocation() {
        return mAFileLocation;
    }

    public void setMAFileLocation(Object mAFileLocation) {
        this.mAFileLocation = mAFileLocation;
    }

    public Object getMAFileExtension() {
        return mAFileExtension;
    }

    public void setMAFileExtension(Object mAFileExtension) {
        this.mAFileExtension = mAFileExtension;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Object createdByUserId) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(Integer employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public Integer getReportingManagerId() {
        return reportingManagerId;
    }

    public void setReportingManagerId(Integer reportingManagerId) {
        this.reportingManagerId = reportingManagerId;
    }

    public Object getExpInYears() {
        return expInYears;
    }

    public void setExpInYears(Object expInYears) {
        this.expInYears = expInYears;
    }

    public Object getExpInMonths() {
        return expInMonths;
    }

    public void setExpInMonths(Object expInMonths) {
        this.expInMonths = expInMonths;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
}
