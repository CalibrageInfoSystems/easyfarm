package com.cis.easyfarm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {


        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("MiddleName")
        @Expose
        private String middleName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("PrimaryPhoneNumber")
        @Expose
        private String primaryPhoneNumber;
        @SerializedName("FatherOrGuardianName")
        @Expose
        private String fatherOrGuardianName;
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
        @SerializedName("GenderTypeId")
        @Expose
        private Integer genderTypeId;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("UserName")
        @Expose
        private String userName;
        @SerializedName("Password")
        @Expose
        private String password;
        @SerializedName("RoleIds")
        @Expose
        private String roleIds;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("ServerUpdateStatus")
        @Expose
        private Boolean serverUpdateStatus;
        @SerializedName("IsWillingtoConvert")
        @Expose
        private Boolean isWillingtoConvert;
        @SerializedName("CreatedByUserId")
        @Expose
        private Integer createdByUserId;
        @SerializedName("UpdatedByUserId")
        @Expose
        private Integer updatedByUserId;
        @SerializedName("IsNRI")
        @Expose
        private Boolean isNRI;
        @SerializedName("CountryName")
        @Expose
        private String countryName;
        @SerializedName("PostalCode")
        @Expose
        private String postalCode;
        @SerializedName("UserCode")
        @Expose
        private String userCode;
        @SerializedName("TotalPlotArea")
        @Expose
        private Double totalPlotArea;
        @SerializedName("PAddress1")
        @Expose
        private String pAddress1;
        @SerializedName("PAddress2")
        @Expose
        private String pAddress2;
        @SerializedName("PStateId")
        @Expose
        private Integer pStateId;
        @SerializedName("PDistrictId")
        @Expose
        private Integer pDistrictId;
        @SerializedName("PMandalId")
        @Expose
        private Integer pMandalId;
        @SerializedName("PVillageId")
        @Expose
        private Integer pVillageId;
        @SerializedName("Latitude")
        @Expose
        private Double latitude;
        @SerializedName("Longitude")
        @Expose
        private Double longitude;

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

        public String getPrimaryPhoneNumber() {
            return primaryPhoneNumber;
        }

        public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
            this.primaryPhoneNumber = primaryPhoneNumber;
        }

        public String getFatherOrGuardianName() {
            return fatherOrGuardianName;
        }

        public void setFatherOrGuardianName(String fatherOrGuardianName) {
            this.fatherOrGuardianName = fatherOrGuardianName;
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

        public String getRoleIds() {
            return roleIds;
        }

        public void setRoleIds(String roleIds) {
            this.roleIds = roleIds;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean getServerUpdateStatus() {
            return serverUpdateStatus;
        }

        public void setServerUpdateStatus(Boolean serverUpdateStatus) {
            this.serverUpdateStatus = serverUpdateStatus;
        }

        public Boolean getIsWillingtoConvert() {
            return isWillingtoConvert;
        }

        public void setIsWillingtoConvert(Boolean isWillingtoConvert) {
            this.isWillingtoConvert = isWillingtoConvert;
        }

        public Integer getCreatedByUserId() {
            return createdByUserId;
        }

        public void setCreatedByUserId(Integer createdByUserId) {
            this.createdByUserId = createdByUserId;
        }

        public Integer getUpdatedByUserId() {
            return updatedByUserId;
        }

        public void setUpdatedByUserId(Integer updatedByUserId) {
            this.updatedByUserId = updatedByUserId;
        }

        public Boolean getIsNRI() {
            return isNRI;
        }

        public void setIsNRI(Boolean isNRI) {
            this.isNRI = isNRI;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public Double getTotalPlotArea() {
            return totalPlotArea;
        }

        public void setTotalPlotArea(Double totalPlotArea) {
            this.totalPlotArea = totalPlotArea;
        }

        public String getPAddress1() {
            return pAddress1;
        }

        public void setPAddress1(String pAddress1) {
            this.pAddress1 = pAddress1;
        }

        public String getPAddress2() {
            return pAddress2;
        }

        public void setPAddress2(String pAddress2) {
            this.pAddress2 = pAddress2;
        }

        public Integer getPStateId() {
            return pStateId;
        }

        public void setPStateId(Integer pStateId) {
            this.pStateId = pStateId;
        }

        public Integer getPDistrictId() {
            return pDistrictId;
        }

        public void setPDistrictId(Integer pDistrictId) {
            this.pDistrictId = pDistrictId;
        }

        public Integer getPMandalId() {
            return pMandalId;
        }

        public void setPMandalId(Integer pMandalId) {
            this.pMandalId = pMandalId;
        }

        public Integer getPVillageId() {
            return pVillageId;
        }

        public void setPVillageId(Integer pVillageId) {
            this.pVillageId = pVillageId;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
}}