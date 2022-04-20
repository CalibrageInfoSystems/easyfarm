package com.cis.easyfarm.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.net.IDN;

public class Farmer implements Parcelable {

    private String ProfilePicFileBytes;
    private String MutualAgreementFileBytes;
    private Integer Id;
    private String Code;
    private String UserId;
    private String FirstName;
    private String MiddleName;
    private String LastName;
    private String FatherName_GuardianName;
    private String  Address1;
    private String  Address2;
    private Integer StateId;
    private Integer DistrictId;
    private Integer MandalId;
    private Integer VillageId;
    private Integer GenderTypeId;
    private String DOB;
    private  String PrimaryPhoneNumber;
    private  String SecondaryPhoneNumber;
    private Integer AnnualIncomeTypeId;
    private Integer CategoryTypeId;
    private String Email;
    private Integer EducationTypeId;
    private Integer EducationDegreeTypeId;
    private Integer SourceTypeId;
    private String PPFileName;
    private String PPFileLocation;
    private String PPFileExtension;
    private String MAFileName;
    private String MAFileLocation;
    private String MAFileExtension;
    private Integer IsActive;
    private Integer CreatedByUserId;
    private String CreatedDate;
    private String UpdatedDate;
    private Integer UpdatedByUserId;
    private String UserName;
    private String Password;
    private Integer EmployeeTypeId;
    private Integer ReportingManagerId;
    private Integer ExpInYears;
    private Integer ExpInMonths;
    private String AccessToken;
    private Integer ServerUpdatedStatus;
    private Integer IsWillingtoConvert;

    private Integer IsNRI;
    private String CountryName;
    private String PostalCode;
    private Integer StatusTypeId;

    public void setNRI(Integer NRI) {
        IsNRI = NRI;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public void setStatusTypeId(Integer statusTypeId) {
        StatusTypeId = statusTypeId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public Integer getNRI() {
        return IsNRI;
    }

    public String getCountryName() {
        return CountryName;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public Integer getStatusTypeId() {
        return StatusTypeId;
    }



    public Integer getEmployeeTypeId1() {
        return EmployeeTypeId1;
    }

    public void setEmployeeTypeId1(Integer employeeTypeId1) {
        EmployeeTypeId1 = employeeTypeId1;
    }

    private Integer EmployeeTypeId1;




    public String getEmail(){
        return Email;
    }

    public void setEmail(String Email){
        this.Email=Email;
    }

    public String getDOB(){
        return DOB;
    }

    public void setDOB(String DOB){
        this.DOB=DOB;
    }

    public String getCode(){
        return Code;
    }

    public void setCode(String Code){
        this.Code=Code;
    }

    public Integer getStateid(){
        return StateId;
    }

    public void setStateid(Integer StateId){
        this.StateId=StateId;
    }

    public Integer getDistictid(){
        return DistrictId;
    }

    public void setDistictid(Integer DistictId){
        this.DistrictId =DistictId;
    }

    public Integer getMandalid(){
        return MandalId;
    }

    public void setMandalid(Integer MandalId){
        this.MandalId=MandalId;
    }

    public Integer getVillageid(){
        return VillageId;
    }

    public void setVillageid(Integer VillageId){
        this.VillageId=VillageId;
    }


    public String getFirstname(){
        return FirstName;
    }

    public void setFirstname(String FirstName){
        this.FirstName=FirstName;
    }

    public String getMiddlename(){
        return MiddleName;
    }

    public void setMiddlename(String MiddleName){
        this.MiddleName=MiddleName;
    }

    public String getLastname(){
        return LastName;
    }

    public void setLastname(String LastName){
        this.LastName=LastName;
    }


    public Integer getGendertypeid(){
        return GenderTypeId;
    }

    public void setGendertypeid(Integer GenderTypeId){
        this.GenderTypeId=GenderTypeId;
    }


    public Integer getCategorytypeid(){
        return CategoryTypeId;
    }

    public void setCategorytypeid(Integer CategoryTypeId){
        this.CategoryTypeId=CategoryTypeId;
    }

    public Integer getAnnualincometypeid(){
        return AnnualIncomeTypeId;
    }

    public void setAnnualincometypeid(Integer AnnualIncomeTypeId){
        this.AnnualIncomeTypeId=AnnualIncomeTypeId;
    }

    public Integer getEducationtypeid(){
        return EducationTypeId;
    }

    public void setEducationtypeid(Integer EducationTypeId){
        this.EducationTypeId=EducationTypeId;
    }

    public Integer getIsactive() {
        return IsActive;
    }

    public void setIsactive(Integer IsActive) {
        this.IsActive = IsActive;
    }

    public Integer getCreatedbyuserid(){
        return CreatedByUserId;
    }

    public void setCreatedbyuserid(Integer CreatedByUserId){
        this.CreatedByUserId=CreatedByUserId;
    }

    public String getCreateddate(){
        return CreatedDate;
    }

    public void setCreateddate(String CreatedDate){
        this.CreatedDate=CreatedDate;
    }

    public Integer getUpdatedbyuserid(){
        return UpdatedByUserId;
    }

    public void setUpdatedbyuserid(Integer UpdatedByUserId){
        this.UpdatedByUserId=UpdatedByUserId;
    }

    public Integer getServerupdatedstatus(){
        return ServerUpdatedStatus;
    }

    public void setServerupdatedstatus(Integer ServerUpdatedStatus){
        this.ServerUpdatedStatus=ServerUpdatedStatus;
    }

    public Integer getIsWillingtoConvert(){
        return IsWillingtoConvert;
    }

    public void setIsWillingtoConvert(Integer isWillingtoConvert){
        this.IsWillingtoConvert=isWillingtoConvert;
    }

    public Farmer(Parcel in) {
        Email = in.readString();
        DOB = in.readString();
        Code = in.readString();
        Id = in.readInt();
        StateId = in.readInt();
        DistrictId = in.readInt();
        MandalId = in.readInt();
        VillageId = in.readInt();
        FirstName = in.readString();
        MiddleName = in.readString();
        LastName = in.readString();
        GenderTypeId = in.readInt();
        CategoryTypeId = in.readInt();
        AnnualIncomeTypeId = in.readInt();
        EducationTypeId = in.readInt();
        IsActive = in.readInt();
        CreatedByUserId = in.readInt();
        CreatedDate = in.readString();
        UpdatedByUserId = in.readInt();
        ServerUpdatedStatus = in.readInt();
        IsWillingtoConvert = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Email);
        dest.writeString(DOB);
        dest.writeString(Code);

        dest.writeInt(Id);

        dest.writeInt(StateId);
        dest.writeInt(DistrictId);
        dest.writeInt(MandalId);
        dest.writeInt(VillageId);

        dest.writeString(FirstName);
        dest.writeString(MiddleName);
        dest.writeString(LastName);

        dest.writeInt(GenderTypeId);

        dest.writeInt(CategoryTypeId);
        dest.writeInt(AnnualIncomeTypeId);

        dest.writeInt(EducationTypeId);
        dest.writeInt(IsActive);
        dest.writeInt(CreatedByUserId);
        dest.writeString(CreatedDate);
        dest.writeInt(UpdatedByUserId);
        dest.writeInt(ServerUpdatedStatus);

        dest.writeInt(IsWillingtoConvert);

    }

    public Farmer() {

    }
    public static final Parcelable.Creator<Farmer> CREATOR = new Parcelable.Creator<Farmer>() {
        @Override
        public Farmer createFromParcel(Parcel in) {
            return new Farmer(in);
        }

        @Override
        public Farmer[] newArray(int size) {
            return new Farmer[size];
        }
    };

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getProfilePicFileBytes() {
        return ProfilePicFileBytes;
    }

    public void setProfilePicFileBytes(String profilePicFileBytes) {
        ProfilePicFileBytes = profilePicFileBytes;
    }

    public String getMutualAgreementFileBytes() {
        return MutualAgreementFileBytes;
    }

    public void setMutualAgreementFileBytes(String mutualAgreementFileBytes) {
        MutualAgreementFileBytes = mutualAgreementFileBytes;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFatherName_GuardianName() {
        return FatherName_GuardianName;
    }

    public void setFatherName_GuardianName(String fatherName_GuardianName) {
        FatherName_GuardianName = fatherName_GuardianName;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getPrimaryPhoneNumber() {
        return PrimaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        PrimaryPhoneNumber = primaryPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return SecondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        SecondaryPhoneNumber = secondaryPhoneNumber;
    }

    public Integer getSourceTypeId() {
        return SourceTypeId;
    }

    public void setSourceTypeId(Integer sourceTypeId) {
        SourceTypeId = sourceTypeId;
    }

    public Integer getEducationDegreeTypeId() {
        return EducationDegreeTypeId;
    }

    public void setEducationDegreeTypeId(Integer educationDegreeTypeId) {
        EducationDegreeTypeId = educationDegreeTypeId;
    }

    public String getPPFileName() {
        return PPFileName;
    }

    public void setPPFileName(String PPFileName) {
        this.PPFileName = PPFileName;
    }

    public String getPPFileLocation() {
        return PPFileLocation;
    }

    public void setPPFileLocation(String PPFileLocation) {
        this.PPFileLocation = PPFileLocation;
    }

    public String getPPFileExtension() {
        return PPFileExtension;
    }

    public void setPPFileExtension(String PPFileExtension) {
        this.PPFileExtension = PPFileExtension;
    }

    public String getMAFileName() {
        return MAFileName;
    }

    public void setMAFileName(String MAFileName) {
        this.MAFileName = MAFileName;
    }

    public String getMAFileLocation() {
        return MAFileLocation;
    }

    public void setMAFileLocation(String MAFileLocation) {
        this.MAFileLocation = MAFileLocation;
    }

    public String getMAFileExtension() {
        return MAFileExtension;
    }

    public void setMAFileExtension(String MAFileExtension) {
        this.MAFileExtension = MAFileExtension;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Integer getEmployeeTypeId() {
        return EmployeeTypeId;
    }

    public void setEmployeeTypeId(Integer employeeTypeId) {
        EmployeeTypeId = employeeTypeId;
    }

    public Integer getReportingManagerId() {
        return ReportingManagerId;
    }

    public void setReportingManagerId(Integer reportingManagerId) {
        ReportingManagerId = reportingManagerId;
    }

    public Integer getExpInYears() {
        return ExpInYears;
    }

    public void setExpInYears(Integer expInYears) {
        ExpInYears = expInYears;
    }

    public Integer getExpInMonths() {
        return ExpInMonths;
    }

    public void setExpInMonths(Integer expInMonths) {
        ExpInMonths = expInMonths;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
