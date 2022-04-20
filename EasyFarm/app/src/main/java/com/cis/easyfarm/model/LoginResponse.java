package com.cis.easyfarm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LoginResponse {

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

        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("token_type")
        @Expose
        private String tokenType;
        @SerializedName("expires_in")
        @Expose
        private Integer expiresIn;
        @SerializedName("userInfos")
        @Expose
        private UserInfos userInfos;
        @SerializedName("activityRights")
        @Expose
        private List<ActivityRight> activityRights = null;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }

        public Integer getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(Integer expiresIn) {
            this.expiresIn = expiresIn;
        }

        public UserInfos getUserInfos() {
            return userInfos;
        }

        public void setUserInfos(UserInfos userInfos) {
            this.userInfos = userInfos;
        }

        public List<ActivityRight> getActivityRights() {
            return activityRights;
        }

        public void setActivityRights(List<ActivityRight> activityRights) {
            this.activityRights = activityRights;
        }

        public class UserInfos {

            @SerializedName("Id")
            @Expose
            private Integer id;
            @SerializedName("UserId")
            @Expose
            private String userId;
            @SerializedName("Code")
            @Expose
            private String code;
            @SerializedName("FirstName")
            @Expose
            private String firstName;
            @SerializedName("MiddleName")
            @Expose
            private String middleName;
            @SerializedName("LastName")
            @Expose
            private String lastName;
            @SerializedName("UserName")
            @Expose
            private String userName;
            @SerializedName("FullName")
            @Expose
            private String fullName;
            @SerializedName("FatherName_GuardianName")
            @Expose
            private String fatherNameGuardianName;
            @SerializedName("Email")
            @Expose
            private String email;
            @SerializedName("Address1")
            @Expose
            private String address1;
            @SerializedName("Address2")
            @Expose
            private String address2;
            @SerializedName("CountryId")
            @Expose
            private Integer countryId;
            @SerializedName("CountryName")
            @Expose
            private String countryName;
            @SerializedName("StateId")
            @Expose
            private Integer stateId;
            @SerializedName("StateName")
            @Expose
            private String stateName;
            @SerializedName("DistrictId")
            @Expose
            private Integer districtId;
            @SerializedName("DistrictName")
            @Expose
            private String districtName;
            @SerializedName("MandalId")
            @Expose
            private Integer mandalId;
            @SerializedName("MandalName")
            @Expose
            private String mandalName;
            @SerializedName("VillageId")
            @Expose
            private Integer villageId;
            @SerializedName("VillageName")
            @Expose
            private String villageName;
            @SerializedName("GenderTypeId")
            @Expose
            private Integer genderTypeId;
            @SerializedName("Gender")
            @Expose
            private String gender;
            @SerializedName("DOB")
            @Expose
            private String dOB;
            @SerializedName("PrimaryPhoneNumber")
            @Expose
            private String primaryPhoneNumber;
            @SerializedName("SecondaryPhoneNumber")
            @Expose
            private Object secondaryPhoneNumber;
            @SerializedName("AnnualIncomeTypeId")
            @Expose
            private Object annualIncomeTypeId;
            @SerializedName("AnnualIncome")
            @Expose
            private Object annualIncome;
            @SerializedName("CategoryTypeId")
            @Expose
            private Object categoryTypeId;
            @SerializedName("Categorytype")
            @Expose
            private Object categorytype;
            @SerializedName("EducationTypeId")
            @Expose
            private Object educationTypeId;
            @SerializedName("EducationType")
            @Expose
            private Object educationType;
            @SerializedName("EducationDegreeTypeId")
            @Expose
            private Object educationDegreeTypeId;
            @SerializedName("EducationDegreeType")
            @Expose
            private Object educationDegreeType;
            @SerializedName("SourceTypeId")
            @Expose
            private Object sourceTypeId;
            @SerializedName("SourceType")
            @Expose
            private Object sourceType;
            @SerializedName("EmployeeTypeId")
            @Expose
            private Object employeeTypeId;
            @SerializedName("EmployeeType")
            @Expose
            private Object employeeType;
            @SerializedName("PPFileName")
            @Expose
            private Object pPFileName;
            @SerializedName("PPFileLocation")
            @Expose
            private Object pPFileLocation;
            @SerializedName("PPFileExtension")
            @Expose
            private Object pPFileExtension;
            @SerializedName("ProfilePic")
            @Expose
            private Object profilePic;
            @SerializedName("MAFileName")
            @Expose
            private Object mAFileName;
            @SerializedName("MAFileLocation")
            @Expose
            private Object mAFileLocation;
            @SerializedName("MAFileExtension")
            @Expose
            private Object mAFileExtension;
            @SerializedName("MutualAgreement")
            @Expose
            private Object mutualAgreement;
            @SerializedName("ReportingManagerId")
            @Expose
            private Object reportingManagerId;
            @SerializedName("ReportingManager")
            @Expose
            private Object reportingManager;
            @SerializedName("ExpInYears")
            @Expose
            private Object expInYears;
            @SerializedName("ExpInMonths")
            @Expose
            private Object expInMonths;
            @SerializedName("RoleIds")
            @Expose
            private String roleIds;
            @SerializedName("Roles")
            @Expose
            private String roles;
            @SerializedName("ClusterIds")
            @Expose
            private Object clusterIds;
            @SerializedName("Clusters")
            @Expose
            private Object clusters;
            @SerializedName("DistrictIds")
            @Expose
            private Object districtIds;
            @SerializedName("Districts")
            @Expose
            private Object districts;
            @SerializedName("StateIds")
            @Expose
            private Object stateIds;
            @SerializedName("States")
            @Expose
            private Object states;
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
            @SerializedName("IsActive")
            @Expose
            private Boolean isActive;
            @SerializedName("CreatedBy")
            @Expose
            private String createdBy;
            @SerializedName("UpdatedBy")
            @Expose
            private String updatedBy;
            @SerializedName("ServerUpdatedStatus")
            @Expose
            private Boolean serverUpdatedStatus;
            @SerializedName("IsWillingtoConvert")
            @Expose
            private Boolean isWillingtoConvert;
            @SerializedName("IsNRI")
            @Expose
            private Boolean isNRI;
            @SerializedName("NRICountryName")
            @Expose
            private Object nRICountryName;
            @SerializedName("PostalCode")
            @Expose
            private Object postalCode;
            @SerializedName("StatusTypeId")
            @Expose
            private Integer statusTypeId;
            @SerializedName("StatusType")
            @Expose
            private String statusType;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getMiddleName() {
                return middleName;
            }

            public void setMiddleName(String middleName) {
                this.middleName = middleName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getFatherNameGuardianName() {
                return fatherNameGuardianName;
            }

            public void setFatherNameGuardianName(String fatherNameGuardianName) {
                this.fatherNameGuardianName = fatherNameGuardianName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
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

            public Integer getCountryId() {
                return countryId;
            }

            public void setCountryId(Integer countryId) {
                this.countryId = countryId;
            }

            public String getCountryName() {
                return countryName;
            }

            public void setCountryName(String countryName) {
                this.countryName = countryName;
            }

            public Integer getStateId() {
                return stateId;
            }

            public void setStateId(Integer stateId) {
                this.stateId = stateId;
            }

            public String getStateName() {
                return stateName;
            }

            public void setStateName(String stateName) {
                this.stateName = stateName;
            }

            public Integer getDistrictId() {
                return districtId;
            }

            public void setDistrictId(Integer districtId) {
                this.districtId = districtId;
            }

            public String getDistrictName() {
                return districtName;
            }

            public void setDistrictName(String districtName) {
                this.districtName = districtName;
            }

            public Integer getMandalId() {
                return mandalId;
            }

            public void setMandalId(Integer mandalId) {
                this.mandalId = mandalId;
            }

            public String getMandalName() {
                return mandalName;
            }

            public void setMandalName(String mandalName) {
                this.mandalName = mandalName;
            }

            public Integer getVillageId() {
                return villageId;
            }

            public void setVillageId(Integer villageId) {
                this.villageId = villageId;
            }

            public String getVillageName() {
                return villageName;
            }

            public void setVillageName(String villageName) {
                this.villageName = villageName;
            }

            public Integer getGenderTypeId() {
                return genderTypeId;
            }

            public void setGenderTypeId(Integer genderTypeId) {
                this.genderTypeId = genderTypeId;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
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

            public Object getSecondaryPhoneNumber() {
                return secondaryPhoneNumber;
            }

            public void setSecondaryPhoneNumber(Object secondaryPhoneNumber) {
                this.secondaryPhoneNumber = secondaryPhoneNumber;
            }

            public Object getAnnualIncomeTypeId() {
                return annualIncomeTypeId;
            }

            public void setAnnualIncomeTypeId(Object annualIncomeTypeId) {
                this.annualIncomeTypeId = annualIncomeTypeId;
            }

            public Object getAnnualIncome() {
                return annualIncome;
            }

            public void setAnnualIncome(Object annualIncome) {
                this.annualIncome = annualIncome;
            }

            public Object getCategoryTypeId() {
                return categoryTypeId;
            }

            public void setCategoryTypeId(Object categoryTypeId) {
                this.categoryTypeId = categoryTypeId;
            }

            public Object getCategorytype() {
                return categorytype;
            }

            public void setCategorytype(Object categorytype) {
                this.categorytype = categorytype;
            }

            public Object getEducationTypeId() {
                return educationTypeId;
            }

            public void setEducationTypeId(Object educationTypeId) {
                this.educationTypeId = educationTypeId;
            }

            public Object getEducationType() {
                return educationType;
            }

            public void setEducationType(Object educationType) {
                this.educationType = educationType;
            }

            public Object getEducationDegreeTypeId() {
                return educationDegreeTypeId;
            }

            public void setEducationDegreeTypeId(Object educationDegreeTypeId) {
                this.educationDegreeTypeId = educationDegreeTypeId;
            }

            public Object getEducationDegreeType() {
                return educationDegreeType;
            }

            public void setEducationDegreeType(Object educationDegreeType) {
                this.educationDegreeType = educationDegreeType;
            }

            public Object getSourceTypeId() {
                return sourceTypeId;
            }

            public void setSourceTypeId(Object sourceTypeId) {
                this.sourceTypeId = sourceTypeId;
            }

            public Object getSourceType() {
                return sourceType;
            }

            public void setSourceType(Object sourceType) {
                this.sourceType = sourceType;
            }

            public Object getEmployeeTypeId() {
                return employeeTypeId;
            }

            public void setEmployeeTypeId(Object employeeTypeId) {
                this.employeeTypeId = employeeTypeId;
            }

            public Object getEmployeeType() {
                return employeeType;
            }

            public void setEmployeeType(Object employeeType) {
                this.employeeType = employeeType;
            }

            public Object getPPFileName() {
                return pPFileName;
            }

            public void setPPFileName(Object pPFileName) {
                this.pPFileName = pPFileName;
            }

            public Object getPPFileLocation() {
                return pPFileLocation;
            }

            public void setPPFileLocation(Object pPFileLocation) {
                this.pPFileLocation = pPFileLocation;
            }

            public Object getPPFileExtension() {
                return pPFileExtension;
            }

            public void setPPFileExtension(Object pPFileExtension) {
                this.pPFileExtension = pPFileExtension;
            }

            public Object getProfilePic() {
                return profilePic;
            }

            public void setProfilePic(Object profilePic) {
                this.profilePic = profilePic;
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

            public Object getMutualAgreement() {
                return mutualAgreement;
            }

            public void setMutualAgreement(Object mutualAgreement) {
                this.mutualAgreement = mutualAgreement;
            }

            public Object getReportingManagerId() {
                return reportingManagerId;
            }

            public void setReportingManagerId(Object reportingManagerId) {
                this.reportingManagerId = reportingManagerId;
            }

            public Object getReportingManager() {
                return reportingManager;
            }

            public void setReportingManager(Object reportingManager) {
                this.reportingManager = reportingManager;
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

            public String getRoleIds() {
                return roleIds;
            }

            public void setRoleIds(String roleIds) {
                this.roleIds = roleIds;
            }

            public String getRoles() {
                return roles;
            }

            public void setRoles(String roles) {
                this.roles = roles;
            }

            public Object getClusterIds() {
                return clusterIds;
            }

            public void setClusterIds(Object clusterIds) {
                this.clusterIds = clusterIds;
            }

            public Object getClusters() {
                return clusters;
            }

            public void setClusters(Object clusters) {
                this.clusters = clusters;
            }

            public Object getDistrictIds() {
                return districtIds;
            }

            public void setDistrictIds(Object districtIds) {
                this.districtIds = districtIds;
            }

            public Object getDistricts() {
                return districts;
            }

            public void setDistricts(Object districts) {
                this.districts = districts;
            }

            public Object getStateIds() {
                return stateIds;
            }

            public void setStateIds(Object stateIds) {
                this.stateIds = stateIds;
            }

            public Object getStates() {
                return states;
            }

            public void setStates(Object states) {
                this.states = states;
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

            public Boolean getIsActive() {
                return isActive;
            }

            public void setIsActive(Boolean isActive) {
                this.isActive = isActive;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(String updatedBy) {
                this.updatedBy = updatedBy;
            }

            public Boolean getServerUpdatedStatus() {
                return serverUpdatedStatus;
            }

            public void setServerUpdatedStatus(Boolean serverUpdatedStatus) {
                this.serverUpdatedStatus = serverUpdatedStatus;
            }

            public Boolean getIsWillingtoConvert() {
                return isWillingtoConvert;
            }

            public void setIsWillingtoConvert(Boolean isWillingtoConvert) {
                this.isWillingtoConvert = isWillingtoConvert;
            }

            public Boolean getIsNRI() {
                return isNRI;
            }

            public void setIsNRI(Boolean isNRI) {
                this.isNRI = isNRI;
            }

            public Object getNRICountryName() {
                return nRICountryName;
            }

            public void setNRICountryName(Object nRICountryName) {
                this.nRICountryName = nRICountryName;
            }

            public Object getPostalCode() {
                return postalCode;
            }

            public void setPostalCode(Object postalCode) {
                this.postalCode = postalCode;
            }

            public Integer getStatusTypeId() {
                return statusTypeId;
            }

            public void setStatusTypeId(Integer statusTypeId) {
                this.statusTypeId = statusTypeId;
            }

            public String getStatusType() {
                return statusType;
            }

            public void setStatusType(String statusType) {
                this.statusType = statusType;
            }}
        public class ActivityRight {

            @SerializedName("Id")
            @Expose
            private Integer id;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Desc")
            @Expose
            private String desc;
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

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
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
        }}}