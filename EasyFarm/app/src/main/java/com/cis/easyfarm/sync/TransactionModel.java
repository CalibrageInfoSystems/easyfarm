package com.cis.easyfarm.sync;

import com.cis.easyfarm.Objects.BankDetails;
import com.cis.easyfarm.Objects.BankDetailsHistory;
import com.cis.easyfarm.Objects.Farmer;
import com.cis.easyfarm.Objects.FileRepository;
import com.cis.easyfarm.Objects.IdentityProof;
import com.cis.easyfarm.Objects.Plot;
import com.cis.easyfarm.Objects.PlotStatusHistory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionModel {
    @SerializedName("farmer")
    @Expose
    private List<Farmer> farmer = null;
    @SerializedName("identityProofs")
    @Expose
    private List<IdentityProof> identityProofs = null;
    @SerializedName("bankDetails")
    @Expose
    private List<BankDetails> bankDetails = null;
    @SerializedName("bankDetailsHistories")
    @Expose
    private List<BankDetailsHistory> bankDetailsHistories = null;
    @SerializedName("plots")
    @Expose
    private List<Plot> plots = null;
    @SerializedName("plotStatusHistories")
    @Expose
    private List<PlotStatusHistory> plotStatusHistories = null;
    @SerializedName("fileRepositories")
    @Expose
    private List<FileRepository> fileRepositories = null;

    public List<Farmer> getFarmer() {
        return farmer;
    }

    public void setFarmer(List<Farmer> farmer) {
        this.farmer = farmer;
    }

    public List<IdentityProof> getIdentityProofs() {
        return identityProofs;
    }

    public void setIdentityProofs(List<IdentityProof> identityProofs) {
        this.identityProofs = identityProofs;
    }

    public List<BankDetails> getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(List<BankDetails> bankDetails) {
        this.bankDetails = bankDetails;
    }

    public List<BankDetailsHistory> getBankDetailsHistories() {
        return bankDetailsHistories;
    }

    public void setBankDetailsHistories(List<BankDetailsHistory> bankDetailsHistories) {
        this.bankDetailsHistories = bankDetailsHistories;
    }

    public List<Plot> getPlots() {
        return plots;
    }

    public void setPlots(List<Plot> plots) {
        this.plots = plots;
    }

    public List<PlotStatusHistory> getPlotStatusHistories() {
        return plotStatusHistories;
    }

    public void setPlotStatusHistories(List<PlotStatusHistory> plotStatusHistories) {
        this.plotStatusHistories = plotStatusHistories;
    }

    public List<FileRepository> getFileRepositories() {
        return fileRepositories;
    }

    public void setFileRepositories(List<FileRepository> fileRepositories) {
        this.fileRepositories = fileRepositories;
    }


}
