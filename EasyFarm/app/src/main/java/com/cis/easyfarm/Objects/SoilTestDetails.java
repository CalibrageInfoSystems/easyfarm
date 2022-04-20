package com.cis.easyfarm.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SoilTestDetails {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("PlotCode")
    @Expose
    private String plotCode;
    @SerializedName("CultureTypeId")
    @Expose
    private Integer cultureTypeId;
    @SerializedName("Nitrogen")
    @Expose
    private Double nitrogen;
    @SerializedName("Prosperous")
    @Expose
    private Double prosperous;
    @SerializedName("Potassium")
    @Expose
    private Double potassium;
    @SerializedName("Carbon")
    @Expose
    private Double carbon;
    @SerializedName("Hydrogen")
    @Expose
    private Double hydrogen;
    @SerializedName("Oxygen")
    @Expose
    private Double oxygen;
    @SerializedName("Sulphur")
    @Expose
    private Double sulphur;
    @SerializedName("Calcium")
    @Expose
    private Double calcium;
    @SerializedName("Magnesium")
    @Expose
    private Double magnesium;
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
    private Boolean isActive;
    @SerializedName("ServerUpdatedStatus")
    @Expose
    private Boolean serverUpdatedStatus;
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

    @SerializedName("FileBytes")
    @Expose
    private String fileBytes;

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

    public String getPlotCode() {
        return plotCode;
    }

    public void setPlotCode(String plotCode) {
        this.plotCode = plotCode;
    }

    public Integer getCultureTypeId() {
        return cultureTypeId;
    }

    public void setCultureTypeId(Integer cultureTypeId) {
        this.cultureTypeId = cultureTypeId;
    }

    public Double getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(Double nitrogen) {
        this.nitrogen = nitrogen;
    }

    public Double getProsperous() {
        return prosperous;
    }

    public void setProsperous(Double prosperous) {
        this.prosperous = prosperous;
    }

    public Double getPotassium() {
        return potassium;
    }

    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }

    public Double getCarbon() {
        return carbon;
    }

    public void setCarbon(Double carbon) {
        this.carbon = carbon;
    }

    public Double getHydrogen() {
        return hydrogen;
    }

    public void setHydrogen(Double hydrogen) {
        this.hydrogen = hydrogen;
    }

    public Double getOxygen() {
        return oxygen;
    }

    public void setOxygen(Double oxygen) {
        this.oxygen = oxygen;
    }

    public Double getSulphur() {
        return sulphur;
    }

    public void setSulphur(Double sulphur) {
        this.sulphur = sulphur;
    }

    public Double getCalcium() {
        return calcium;
    }

    public void setCalcium(Double calcium) {
        this.calcium = calcium;
    }

    public Double getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(Double magnesium) {
        this.magnesium = magnesium;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getServerUpdatedStatus() {
        return serverUpdatedStatus;
    }

    public void setServerUpdatedStatus(Boolean serverUpdatedStatus) {
        this.serverUpdatedStatus = serverUpdatedStatus;
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

}
