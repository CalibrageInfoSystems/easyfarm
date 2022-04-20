package com.cis.easyfarm.database;



import android.text.TextUtils;

import com.cis.easyfarm.common.CommonConstants;

import org.bouncycastle.util.Integers;

import java.lang.reflect.Array;


public class Queries {

    private static Queries instance;
    private String isActive;

    public static Queries getInstance() {
        if (instance == null) {
            instance = new Queries();
        }
        return instance;
    }

    /* public static String getAlertsPlotFollowUpQuery(int limit, int offset) {
         return "select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,f.ContactNumber, \n" +
                 " m.Name as MandalName,\n" +
                 " v.Name as VillageName,\n" +
                 "p.TotalPlotArea, fu.PotentialScore,\n" +
                 "(select GROUP_CONCAT(lkp.Name) from PlotCurrentCrop pcc inner join LookUp lkp on pcc.CropId =lkp.Id  where PlotCode = p.Code and lkp.LookUpTypeId = '22')  as cropName,\n" +
                 "fu.CreatedDate as lastVisitDate,h.CreatedDate as harvestDate,\n" +
                 "(select Desc from TypeCdDmt where TypeCdId = SR.PrioritizationTypeId) as Prioritization\n" +
                 "from Plot p\n" +
                 "inner join Farmer f on f.code=p.FarmerCode\n" +
                 "left join Harvest h on h.PlotCode =p.Code\n" +
                 "left join SoilResource SR on SR.PlotCode=p.Code\n" +
                 "inner join FollowUp fu on fu.PlotCode = p.Code\n" +
                 "inner join Address addr on p.AddressCode = addr.Code\n" +
                 "inner join Village v on addr.VillageId = v.Id\n" +
                 "inner join Mandal m on addr.MandalId = m.Id\n" +
                 "inner join District d on addr.DistrictId = d.Id\n" +
                 "inner join State s on addr.StateId = s.Id\n" +
                 "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
                 "where  fu.PotentialScore>=7 and fh.IsActive = 1 order by lastVisitDate limit " + limit + " offset " + offset + ";";
     }*/
//    public static String getAlertsPlotFollowUpQuery(int limit, int offset) {
//        return "select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,f.ContactNumber,\n" +
//                "  m.Name as MandalName,\n" +
//                "  v.Name as VillageName,\n" +
//                "  p.TotalPlotArea, fu.PotentialScore,\n" +
//                " (select GROUP_CONCAT(lkp.Name) from PlotCurrentCrop pcc\n" +
//                "  inner join LookUp lkp on pcc.CropId =lkp.Id  where PlotCode = p.Code and lkp.LookUpTypeId = '22')  as Crops,\n" +
//                "  fh.CreatedDate as lastVisitDate, fu.HarvestingMonth as HarvestDate,\n" +
//                " (select tcd.desc from SoilResource sr inner join TypeCdDmt tcd on sr.PrioritizationTypeId = tcd.TypeCdId where PlotCode = p.Code) as plotPrioritization,\n" +
//                "  ui.FirstName || ' ' || (CASE WHEN ifnull(ui.MiddleName, '') = 'null' THEN '' ELSE ui.MiddleName || ' ' END) || ui.LastName AS 'UserName'\n"+
//                "  from Plot p\n" +
//                "  inner join UserInfo ui on ui.id =p.CreatedByUserId\n"+
//                "  inner join Farmer f on f.code=p.FarmerCode\n" +
//                "  inner join FollowUp fu on fu.PlotCode = p.Code\n" +
//                "  inner join Address addr on p.AddressCode = addr.Code\n" +
//                "  inner join Village v on addr.VillageId = v.Id\n" +
//                "  inner join Mandal m on addr.MandalId = m.Id\n" +
//                "  inner join District d on addr.DistrictId = d.Id\n" +
//                "  inner join State s on addr.StateId = s.Id\n" +
//                "  inner join FarmerHistory fh on fh.PlotCode = p.Code AND p.FarmerCode = fh.FarmerCode AND fh.IsActive = 1\n" +
//                "  where  fu.PotentialScore>=7 order by lastVisitDate limit " + limit + " offset " + offset + ";";
//    }
//
//    /*  SELECT CASE WHEN myImageColumn IS NULL THEN 0 ELSE 1 END
//      FROM myTable*/
//    public static String getAlertsCount(int type) {
//        return "select count(*) from Alerts where AlertType = " + type + "";
//    }

    /*public static String getAlertsMissingTreesInfoQuery(int limit, int offset) {
        return "select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName, \n" +
                " m.Name as MandalName,\n" +
                " v.Name as VillageName,\n" +
                "(select TreesCount from Plantation where PlotCode =p.Code ORDER BY CreatedDate DESC LIMIT 1) as saplingsplanted,\n" +
                "(select PlamsCount from Uprootment  where PlotCode =p.Code ORDER BY CreatedDate DESC LIMIT 1) as currentTrees,\n" +
                "(select MissingTreesCount from Uprootment  where PlotCode =p.Code ORDER BY CreatedDate DESC LIMIT 1) as missingTrees,\n" +
                "(select ROUND(MissingTreesCount * 100.0 / SeedsPlanted, 0) from Uprootment where PlotCode =p.Code ORDER BY CreatedDate DESC LIMIT 1) as percent\n" +
                "from Plot p\n" +
                "inner join Farmer f on f.code=p.FarmerCode\n" +
                "inner join Uprootment up on up.PlotCode = p.Code\n" +
                "inner join Plantation on Plantation.PlotCode = p.Code\n" +
                "inner join Address addr on p.AddressCode = addr.Code\n" +
                "inner join Village v on addr.VillageId = v.Id\n" +
                "inner join Mandal m on addr.MandalId = m.Id\n" +
                "inner join District d on addr.DistrictId = d.Id\n" +
                "inner join State s on addr.StateId = s.Id\n" +
                "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
                "where   fh.IsActive = 1 and up.MissingTreesCount >= 1 group by p.Code order by percent desc limit " + limit + " offset " + offset + ";";
    }*/

//    public static String getAlertsMissingTreesInfoQuery(int limit, int offset) {
//        return "  select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,\n" +
//                " m.Name as MandalName,\n" +
//                " v.Name as VillageName, plt.TreesCount as saplingsplanted, up.PlamsCount as currentTrees, up.MissingTreesCount as missingTrees,\n" +
//                " (select ROUND(MissingTreesCount * 100.0 / SeedsPlanted, 0) from Uprootment where PlotCode =p.Code ORDER BY CreatedDate DESC LIMIT 1) as percent\n" +
//                "  from Plot p\n" +
//                "  inner join Farmer f on f.code=p.FarmerCode\n" +
//                "  inner join Uprootment up on up.PlotCode = p.Code\n" +
//                "  inner join Plantation plt on plt.PlotCode = p.Code\n" +
//                "  inner join Address addr on p.AddressCode = addr.Code\n" +
//                "  inner join Village v on addr.VillageId = v.Id\n" +
//                "  inner join Mandal m on addr.MandalId = m.Id\n" +
//                "  inner join District d on addr.DistrictId = d.Id\n" +
//                "  inner join State s on addr.StateId = s.Id\n" +
//                "  inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
//                "  where   fh.IsActive = 1 and up.MissingTreesCount >= 1 group by p.Code order by percent desc limit " + limit + " offset " + offset + ";";
//    }
//
//    public static String getAlertsPandDInfoQuery() {
//        return "select p.Code,f.FirstName,f.MiddleName,f.LastName,v.Name as VillageName,p.TotalPlotArea,\n" +
//                " (select GROUP_CONCAT(lkp.Name) from Pest  inner join LookUp lkp on Pest .PestId=lkp.Id  where PlotCode = p.Code and lkp.LookUpTypeId = '6' order by pest.CreatedDate LIMIT 1)  as cropName,\n" +
//                "(select GROUP_CONCAT(lkp.Name) from Disease inner join LookUp lkp on Disease.DiseaseId=lkp.Id  where PlotCode = p.Code and lkp.LookUpTypeId = '5' order by Disease.CreatedDate LIMIT 1)  as diseaseName,\n" +
//                "(select GROUP_CONCAT(lkp.Name) from Nutrient inner join LookUp lkp on Nutrient.NutrientId=lkp.Id  where PlotCode = p.Code and lkp.LookUpTypeId = '5' order by Nutrient.CreatedDate LIMIT 1)  as nutrientName,\n" +
//                "(select CreatedDate from pest where Pest.PlotCode = p.Code order by CreatedDate) as recordeddate\n" +
//                " from Plot p \n" +
//                " inner join Farmer f on f.code=p.FarmerCode\n" +
//                " inner join Address addr on p.AddressCode = addr.Code\n" +
//                " inner join Village v on addr.VillageId = v.Id\n" +
//                " inner join Mandal m on addr.MandalId = m.Id\n" +
//                " inner join District d on addr.DistrictId = d.Id\n" +
//                "inner join State s on addr.StateId = s.Id\n" +
//                "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
//                "where   fh.IsActive = 1";
//    }

    /*public static String getAlertsVisitsInfoQuery(int limit, int offset) {
        return "select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,f.ContactNumber,\n" +
                " m.Name as MandalName,\n" +
                " v.Name as VillageName,\n" +
                " p.TotalPlotArea, p.DateofPlanting,\n" +
                " (select CreatedDate from CropMaintenanceHistory where CropMaintenanceHistory.PlotCode = p.Code order by CreatedDate LIMIT 1) as plotvisiteddate,\n" +
                " (select CreatedDate from FarmerHistory where StatusTypeId =82) as converteddate\n" +
                "  from Plot p\n" +
                "  inner join Farmer f on f.code=p.FarmerCode\n" +
                "  inner join Address addr on p.AddressCode = addr.Code\n" +
                "  inner join Village v on addr.VillageId = v.Id\n" +
                "  inner join Mandal m on addr.MandalId = m.Id\n" +
                "  inner join District d on addr.DistrictId = d.Id\n" +
                "  inner join State s on addr.StateId = s.Id\n" +
                "  inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
                "  where  fh.IsActive = 1 and fh.StatusTypeId in(85,88,89) order by converteddate limit " + limit + " offset " + offset + ";";
    }*/

    //    public static String getAlertsVisitsInfoQuery(int limit, int offset) {
//
//        return "select p.Code,p.FarmerCode,f.FirstName,f.MiddleName,f.LastName,f.ContactNumber,\n" +
//                " m.Name as MandalName,\n" +
//                " v.Name as VillageName,\n" +
//                " p.TotalPlotArea, p.DateofPlanting, cmh.CreatedDate as plotvisiteddate, fh.CreatedDate as converteddate\n" +
//                " from Plot p\n" +
//                " inner join Farmer f on f.code=p.FarmerCode\n" +
//                " inner join Address addr on p.AddressCode = addr.Code\n" +
//                " inner join Village v on addr.VillageId = v.Id\n" +
//                " inner join Mandal m on addr.MandalId = m.Id\n" +
//                " inner join District d on addr.DistrictId = d.Id\n" +
//                " inner join State s on addr.StateId = s.Id\n" +
//                " inner join FarmerHistory fh on fh.PlotCode = p.Code \n" +
//                " inner join CropMaintenanceHistory cmh on cmh.PlotCode = p.Code\n" +
//                " where  fh.IsActive = 1 and fh.StatusTypeId in(85,88,89) order by converteddate limit " + limit + " offset " + offset + ";";
//    }
//    public String getRegionMasterQuery() {
//        return "select RegionTypeId, Code, Name from Region";
//    }
//

    public String getConvertedFarmers() {
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return "SELECT * from UserInfo where Code in (select  FarmerCode from Plot where PlotStatusId != 104 And ServerUpdatedStatus != 0) GROUP by Code ORDER By UpdatedDate desc";
    }

    public String getCPlots(String famerCode) {

        //select * from Plot where Code = '" + plotCode + "'"
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return "select * from Plot where FarmerCode = "+"'"+famerCode+"' AND (PlotStatusId != 104) Group By Id";
    }

    public String getCPlotsbasedonStatus(String plotCode) {

        //select * from Plot where Code = '" + plotCode + "'"
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return "select StatusTypeId from InsuranceDetails where IsActive = 'true' and CropCycleCode in(select CycleCode from PlotCropCycle where PlotCode= "+"'"+plotCode+"') ORDER By UpdatedDate Desc";
    }

    public String getInsuranceStatuss(String plotCode) {

        //select * from Plot where Code = '" + plotCode + "'"
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return "select StatusTypeId from InsuranceDetails where IsActive = 'true' and CropCycleCode in(select CycleCode from PlotCropCycle where PlotCode= "+"'"+plotCode+"')";
    }

    public String getPPlotdetails(String plotCode) {

        //select * from Plot where Code = '" + plotCode + "'"
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return " SELECT * from Plot where Code = "+"'"+plotCode+"' Group By Id";
    }

    public String getInsuranceStatus(String cropCycleCode) {

        //select * from Plot where Code = '" + plotCode + "'"
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return " SELECT * from InsuranceDetails where CropCycleCode = "+"'"+cropCycleCode+"' Group By Id";
    }

    public String getCropCycleCode(String plotCode) {

        //select * from Plot where Code = '" + plotCode + "'"
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return "SELECT * from PlotCropCycle where PlotCode = "+"'"+plotCode+"' AND IsActive = 'true' ORDER BY Id Desc LIMIT 1";
    }

    public String getInsuranceDetailsbasedonCropCycleCode(String cropCycleCode) {

        //select * from Plot where Code = '" + plotCode + "'"
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return "Select * FROM InsuranceDetails where CropCycleCode = "+"'"+cropCycleCode+"' Order By UpdatedDate Desc LIMIT 1";
    }

    public String getConvertedPlots() {
        // return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId != 104) Group By Id order by UpdatedDate desc";
        return "SELECT * from UserInfo where Code in (select  FarmerCode from Plot where PlotStatusId != 104 ) GROUP by Code ORDER By UpdatedDate desc";
    }

    public String getConvertedBuyers() {

        return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=8 And StatusTypeId = 105 And ServerUpdatedStatus != 0) Group By Id order by UpdatedDate desc";
    }

    public String getConvertedVendors() {

        return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=9 And StatusTypeId = 105 And ServerUpdatedStatus != 0) Group By Id order by UpdatedDate desc";
    }


    public String getCountryMasterQuery() {
        return "select Id, Code, Name from Country";
    }

    public String getInsuranceProviderQuery() {
        return "select Id, Name from InsuranceProvider";
    }

    public String getStatesMasterQuery(final String CountryId) {
        return "select Id, Code, Name from State";
    }
    public String getstatesQuery(final String StateId) {

        return  "select  Name from State where id = "+StateId;
        //   return "select Id, Code, Name from State";
    }

    public String getplotStatusQuery(final String plotstatusId) {

        return  "select Desc from TypeCdDmt where TypeCdid = "+plotstatusId;
        //   return "select Id, Code, Name from State";
    }

    public String getinsuranceStatusQuery(final String plotstatusId) {

        return  "select Desc from TypeCdDmt where TypeCdid = "+plotstatusId;
        //   return "select Id, Code, Name from State";
    }

    public String getstatesIdsQuery(final String stateName) {

        return  "select  Id from State where Name = "+stateName;
        //   return "select Id, Code, Name from State";
    }

    public String getdistrictsIdsQuery(final String districtName) {

        return  "select  Id from District where Name = "+districtName;
        //   return "select Id, Code, Name from State";
    }

    public String getmandalIdsQuery(final String mandalName) {

        return  "select  Id from Mandal where Name = "+mandalName;
        //   return "select Id, Code, Name from State";
    }


    public String getvillagedsQuery(final String villageName) {

        return  "select  Id from Mandal where Name = "+villageName;
        //   return "select Id, Code, Name from State";
    }

    public String getCountriesQuery(final String CountryId) {

        return  "select  Name from Country where id = "+CountryId;
        //   return "select Id, Code, Name from State";
    }

    public String getdistrictsQuery(final String StateId) {

        return  "select  Name from District where id = "+StateId;
        //   return "select Id, Code, Name from State";
    }

    public String getmandalQuery(final String DistrictId) {

        return  "select  Name from Mandal where id = "+DistrictId;
        //   return "select Id, Code, Name from State";
    }

    public String getvillageQuery(final String MandalId) {

        return  "select  Name from Village where id = "+MandalId;
        //   return "select Id, Code, Name from State";
    }



    public String getGenderNames(final String TypeCdId) {

        return  "select  DESC from TypeCdDmt where TypeCdId = "+TypeCdId;
        //   return "select Id, Code, Name from State";
    }

    public String getProfileIds(final String Desc) {

        return  "select  TypeCdId from TypeCdDmt where DESC = "+Desc;
        //   return "select Id, Code, Name from State";
    }

//
    public String getStatesQuery() {
        return "SELECT s.Id," +
                "  s.Code," +
                "  s.Name," +
                "  s.CountryId," +
                "  c.Code AS CountryCode," +
                "  c.Name AS CountryName" +
                "FROM State  s" +
                "INNER JOIN Country c ON c.Id = s.CountryId";
    }
//
    public String getdistrictQuery(final String DistrictId) {
        return  "select  Name from District where id = "+DistrictId;
       // return "select Id, Code, Name from District where StateId IN (Select Id from State where Id = '" + stateId + "'" + ")";
    }

    public String getmandalsQuery(final String mandalId) {
        return "select Name, Code, Id from Mandal where id = "+mandalId;
    }

    public String getvillagesQuery(final String  VillageId) {
        return "select Name, Code, Id from Village where id = "+VillageId;
    }

    public String getMandalsQueryBasedOnMandalCode(final String MandalCode) {
        return "select MandalCode, MandalId, MandalName from MandalMaster where MandalCode = '" + MandalCode + "'";
    }

    public String getVillagesQueryBasedOnVillageCode(final String VillageCode) {
        return "select VillageCode from VillageMaster where VillageCode = '" + VillageCode + "'";
    }

    public String getCityQuery(final String DistrictId) {
        return "select CityId,CityName from CityMaster where DistrictCode IN (Select DistrictCode from DistrictMaster where DistrictId ='" + DistrictId + "'" + ")";
    }
    public String getDistrictQuery(final String stateId) {
        return "select Id, Code, Name from District where StateId IN (Select Id from State where Id = '" + stateId + "'" + ")";
    }

    public String getMandalsQuery(final String DistrictId) {
        return "select Id, Code, Name from Mandal where DistrictId IN (Select Id from District where Id = '" + DistrictId + "'" + ")";
    }

    public String getVillagesQuery(final String mandalId) {
        return "select Id, Code, Name from Village where MandalId IN (Select Id from Mandal where Id = '" + mandalId + "'" + ")";
    }

    public String getmandal(final String Mandalid, String DistrictId) {

        return "select  Name, Id from Mandal where id = '" + Mandalid + "'  and DistrictId = '" + DistrictId + "'";
//        return "select * from " + table + " where CropMaintenanceTypeId ='" + CropMaintenanceTypeId + "' and PlotCode ='" + plotCode + "'";
    }


    public String getdistrict(final String DistrictId, String StateId) {

        return "select  Name, Id from District where id = '" + DistrictId + "'  and StateId = '" + StateId + "'";
//        return "select * from " + table + " where CropMaintenanceTypeId ='" + CropMaintenanceTypeId + "' and PlotCode ='" + plotCode + "'";
    }


    public String updateisread(String notificationid) {
        return "update Notification set IsRead = 'true' where Id =  '" + notificationid + "'";
    }

    public String getplotstatus(final String StateId) {

        return  "select  Name from State where id = "+StateId;
        //   return "select Id, Code, Name from State";
    }
    public String getplotownership(final String StateId) {

        return  "select  Name from State where id = "+StateId;
        //   return "select Id, Code, Name from State";
    }


    public String getAssignid(final String Complaintcode) {

        return  "select  AssigntoUserId from ComplaintStatusHistory WHERE ComplaintCode = '" + Complaintcode + "' ORDER BY Id desc LIMIT 1";
    }
    public String getComments(final String Complaintcode) {

        return  "select  Comments from ComplaintStatusHistory WHERE ComplaintCode ='" + Complaintcode + "'";
        //   return "select Id, Code, Name from State";
    }
    public String getCompaintid(final String Complaintcode) {

        return  "select Id from ComplaintStatusHistory WHERE ComplaintCode ='" + Complaintcode + "'";
        //   return "select Id, Code, Name from State";
    }
    public String getComplaintstatusid(final String Complaintcode, Integer Id) {

        //SELECT StatusTypeId  FROM ComplaintStatusHistory where ComplaintCode='COM621PAPKNLKNLMMZ00004-2' ORDER BY Id desc LIMIT 1

        return  "select StatusTypeId from ComplaintStatusHistory WHERE ComplaintCode = '" + Complaintcode + "'  ORDER BY Id desc LIMIT 1";
        //   return "select Id, Code, Name from State";
    }

    //    public String getBankBranchNameQuery() {
//        return "select BankCode,BankName from BankMaster";
//    }
//
//    public String getSwapReasonNameQuery() {
//        return "select SwapReasonCode,SwapReasonName from SwapReasonsMaster";
//    }
//
//    public String getUOM() {
//        return "select Id,Name from UOM";
//    }
//
//    public String getVarietyTypeQuery() {
//        return "select CropVarietyCode,CropVarietyType from CropVarietyMaster";
//    }
//
//    public String getSourceofSaplings() {
//        return "";
//    }
//
//    public String getSaplingsNursery() {
//        return "select Id, Name from Nursery where IsActive = 'true'";
//    }
//
//    public String getBindPalmDetailsQuery() {
//        return "select OtherCrop,CropVarietyType,CropVarietyName, CropId from CropInfo where FarmerCode ='" + CommonConstants.FARMER_CODE + "' and PlotCode ='" + CommonConstants.PLOT_CODE + "'";
////        return "select CropName,CropVarietyType,CropVarietyName from CropInfo  where FarmerCode ='" + CommonConstants.FARMER_CODE + "' PlotCode ='"+CommonConstants.PLOT_CODE+"'";
//    }
//
//    public String getBindIntercropQuery() {
//        return "select InterCropInYear,OtherCrop,InterCropId from InterCropDetails where FarmerCode ='" + CommonConstants.FARMER_CODE + "' and PlotCode ='" + CommonConstants.PLOT_CODE + "'";
//    }
//
//    public String getBindFertilizerQuery() {
//        return "select FertilizerSource,FertilizerType,FertilizerProductName,CropMaintenanceId from CropMaintenance where CropMaintenanceTypeId = '1' and FarmerCode ='" + CommonConstants.FARMER_CODE + "' and PlotCode ='" + CommonConstants.PLOT_CODE + "'";
//    }
//
//    public String getBindWeedingQuery() {
//        return "select WeedMaster.WeedName,C.WeedMethod,C.OtherChemical,C.CropMaintenanceId from CropMaintenance C , WeedMaster wm inner join WeedMaster on WeedMaster.WeedCode = C.WeedCode" +
//                "where CropMaintenanceTypeId = '2' and FarmerCode ='" + CommonConstants.FARMER_CODE + "' and PlotCode ='" + CommonConstants.PLOT_CODE + "'" + " group by C.WeedCode";
//    }
//
//    public String getPruning() {
//        return "select Pruning from CropMaintenance where FarmerCode ='" + CommonConstants.FARMER_CODE + "' and PlotCode ='" + CommonConstants.PLOT_CODE + "'";
//    }
//
//    public String getBindDiseaseQuery() {
//        return "select DiseaseName,OtherChemical,UOM, PlantProtectionId from PlantProtectionDetails where FarmerCode ='" + CommonConstants.FARMER_CODE + "' and PlotCode ='" + CommonConstants.PLOT_CODE + "' and PlantProtecionTypeId = '1'";
//    }
//
//    public String getBindPestQuery() {
//        return "select OtherPestName,OtherChemical,UOM,PlantProtectionId from PlantProtectionDetails where FarmerCode ='" + CommonConstants.FARMER_CODE + "' and PlotCode ='" + CommonConstants.PLOT_CODE + "' and PlantProtecionTypeId = '2'";
//    }
//
//    public String getBindneighborQuery() {
//        return "select NeighbourPlot,OtherCrop,NeighboringPlotId from NeighboringPlot where FarmerCode ='" + CommonConstants.FARMER_CODE + "' and PlotCode ='" + CommonConstants.PLOT_CODE + "'";
//    }
//
//    public String getVarietyFromVarietyTypeQuery(final String varietyName) {
//        return "select CropVarietyCode,CropVarietyName from CropVarietyMaster where CropVarietyType ='" + varietyName + "'";
//    }
//
//    public String getCropNameQuery() {
//        return "select CropCode,CropName from CropMaster";
//    }
//
//    public String getWeedNameQuery() {
//        return "select WeedCode,WeedName from WeedMaster";
//    }
//
//    public String getWeedNameQuery(String WeedCode) {
//        return "select WeedName from WeedMaster where WeedCode = '" + WeedCode + "'";
//    }
//
//    public String getChemicalNameNameQuery() {
//        return "select ChemicalCode,ChemicalName from ChemicalMaster";
//    }
//
//    public String getUserNameQuery() {
//        return "select EmployeeId,EmployeeName from UserDetails";
//    }
//
//    public String getfertiliQzerNameQuery() {
//        return "select FertilizerCode,FertilizerName from FertilizerMaster";
//    }
//
//    public String getDiseaseCodeQuery() {
//        return "select DiseaseName,DiseaseCode from DiseaseMaster";
//    }
//
//    public String getDiseaseNameQuery() {
//        return "select DiseaseCode,DiseaseName from DiseaseMaster";
//    }
//
//    public String getPestCodeQuery() {
//        return "select PestName,PestCode from PestMaster";
//    }
//
//    public String getPestNameQuery() {
//        return "select PestCode,PestName from PestMaster";
//    }
//
//    public String getfertiliQzerTypeQuery() {
//        return "select FertilizerCode,FertilizerType from FertilizerMaster";
//    }
//
//    public String getPreferredCollectionQuery(String DistrictCode) {
//        return "select CollectionCentreCode,CollectionCentreName from CollectionCentreMaster where DistrictCode ='" + DistrictCode + "'";
//    }
//
//    public String getPreferredCollectionAllQuery() {
//        return "select CollectionCentreCode,CollectionCentreName from CollectionCentreMaster";
//    }
//
//    public String getUprootmentReasonQuery() {
//        return "select UprootReasonCode,UprootReasonName from UprootReasonsMaster";
//    }
//
//
//    public String getMaxNumberQuery(final String villageId, final String villageCode) {
//        return "SELECT max(substr(Code, length(Code) - 3,length(Code))) as Maxnumber FROM Farmer where Code like '%" + villageCode + "%' and villageId = '" + villageId + "'";
//    }
//
//    public String getMaxNumberQueryMarketSurvey(final String villageId, final String villageCode){
//        return "SELECT MAX(cast(substr(Code, INSTR(Code,'-') +1 , length(Code)) as INTEGER)) as Maxnumber FROM MarketSurvey where Code like '%" + villageCode + "%' and villageId = '" + villageId + "'";
//    }
//
//
//    public String getMaxNumberForPlotQuery(final String mandalId, final String mandalCode) {
//        return "SELECT  max(substr(Plot.Code, length(Plot.Code) - 3,length(Plot.Code))) as Maxnumber\n" +
//                " FROM Plot\n" +
//                " inner join Address on Plot.AddressCode = Address.Code\n" +
//                " WHERE Plot.Code like '%" + mandalCode + "%' and Address.MandalId = '" + mandalId + "'";
//    }
//
//    public String getCastesQuery() {
//        return "select CastId,CastName from CastMaster";
//    }
//
//    public String getsource_of_contactQuery() {
//        return "SELECT Id,NAme FROM LookUp where LookUpTypeId = '13' and isActive ='true'";
//    }
//
//    public String gettitleQuery() {
//        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '6' and isActive ='true'";
//    }
//
//    public String getgenderQuery() {
//        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '7' and isActive ='true'";
//    }
//
//    public String getcastQuery() {
//        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '8' and isActive ='true'";
//    }
//
//
//    public String getVehicleTypeQuery() {
//        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '19' and isActive ='true'";
//    }
//
    public String getTypeCdDmtData() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = '3' and isActive ='true'";

    }

//    public String getTypeCdDmtComplaintsTypeData(String classTypeId) {
//        return "SELECT Typecdid,desc FROM typecddmt where classtypeid= '" + classTypeId + "' and isActive ='true'";
//    }
//    public String getCloseDoneStatus(){
//        return "SELECT Typecdid,desc FROM typecddmt where classtypeid=40 and  TypeCdId in (202) and isActive ='true'";
//    }
//
//    public String getCollectionCenterData(String classTypeId) {
//        return "SELECT Id,Name FROM CollectionCenter and isActive ='true'";
//    }
//
    public String deleteTableData() {

        String deleteQuery = "delete from %s";

        return deleteQuery;
    }

    public String updateServerUpdatedStatus() {
        return "update %s set ServerUpdatedStatus = '1' where ServerUpdatedStatus = '0'";
    }




    public String deleteFileRepositoryTable() {
        return "DROP TABLE if EXISTS FileRepository";
    }

//    public String getSyncDate() {
//        return "select UpdatedOn from MasterVersionTrackingSystem";
//    }
//
//    public String getUserDetails(final String imeiNumer) {
//        return "select U.EmployeeId, U.EmployeeName, U.Password, T.TabletId from UserDetails U, TabletAllocation T " +
//                "inner join TabletAllocation on U.EmployeeId = T.EmployeeId where U.Employeeid IN (select Employeeid from TabletAllocation where TabletIMEINo = '" + imeiNumer + "'" + ") GROUP BY U.EmployeeId";
//    }
//
//    public String getFarmerLandDetailsQuery() {
//        return "select  FarmerDetails.FarmerCode,FarmerDetails.FarmerFirstName,FarmerDetails.FarmerMiddleName, FarmerDetails.FarmerLastName,FarmerDetails.DOB," +
//                "FarmerDetails.Gender,FarmerDetails.FatherName, FarmerDetails.MotherName,StateMaster.StateName,DistrictMaster.DistrictName,PictureReporting.Photo, " +
//                "FarmerDetails.Active, FarmerDetails.PrimaryContactNumber,FarmerDetails.SecondaryContactNumber,  FarmerDetails.Address1,FarmerDetails.Address2,FarmerDetails.Landmark," +
//                "VillageMaster.VillageName, VillageMaster.VillageCode, VillageMaster.VillageId, StateMaster.StateCode, StateMaster.StateId, MandalMaster.MandalCode, " +
//                "DistrictMaster.DistrictCode  from FarmerDetails inner join VillageMaster on FarmerDetails.VillageCode = VillageMaster.VillageCode LEFT JOIN PictureReporting on " +
//                "PictureReporting.FarmerCode = FarmerDetails.FarmerCode and PictureReporting.ModuleId = '1' inner join StateMaster on StateMaster.StateCode = FarmerDetails.StateCode " +
//                "inner join DistrictMaster on DistrictMaster.DistrictCode = FarmerDetails.DistrictCode inner join MandalMaster on MandalMaster.MandalCode = FarmerDetails.MandalCode " +
//                "where FarmerDetails.Active =1  group by FarmerDetails.FarmerCode;";
//    }
//
//    public String getBankDetailsQuery(String farmercode) {
//        return "select BankDetailId,OtherBank,AccountHolderName,AccountNumber,BranchName,IFSCCode from BankDetails where FarmerCode='" + farmercode + "'";
//    }
//
//    public String marketSurveyDataCheck(String farmerCode) {
//        return "select * from MarketSurveyAndReferrals where FarmerCode = '" + farmerCode + "'";
//    }
//
//
//
//    //********************* REFRESH QUERIES****************************************************************************************
//
//    public String getPlotDetailsQuery(String farmerCode) {
//        return "select LandDetails.PlotCode,LandDetails.Landmark,LandDetails.TotalArea from LandDetails " +
//                " inner join FarmerDetails on FarmerDetails.FarmerCode=LandDetails.FarmerCode where LandDetails.FarmerCode='" + farmerCode + "' group by LandDetails.PlotCode order by LandDetails.rowid";
//    }
//
//    public String getRefreshCountQuery(String tablename) {
//        return "select count(0) from " + tablename + " where ServerUpdatedStatus='0'";
//    }
//
//    public String getRefreshCountQueryForPictures() {
//        return "select count(0) from PictureReporting where ServerUpdatedStatus='0'";
//    }
//
//    public String getRefreshCountQueryForFileRepo() {
//        return "select count(0) from FileRepository where ServerUpdatedStatus='0'";
//    }
//
//    public String getPincode(String villageid) {
//        return "select PinCode from Village where Id ='" + villageid + "'";
//    }
//
//    public String getFarmerDetailsRefreshQuery() {
//        return "select FarmerCode,FarmerFirstName,FarmerMiddleName,FarmerLastName,DOB,Gender,FatherName,MotherName,EmailAddress," +
//                "Age,CastId,PrimaryContactNumber,SecondaryContactNumber,POCContactInfo,CareTaker,Address1,Address2,Landmark," +
//                "StateCode,DistrictCode,MandalCode,VillageCode,CityCode,Pincode,Active,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus,FarmerPhoto from FarmerDetails where ServerUpdatedStatus=0";
//    }
//
//    public String getIDProofDetailsRefreshQuery() {
//        return "select ProofID,ProofType,ProofNo,FarmerCode,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from IDProofs where ServerUpdatedStatus=0";
//    }
//
//    public String getBankDetailsRefreshQuery() {
//        return "select BankDetailId,FarmerCode,BankCode,OtherBank,AccountHolderName,AccountNumber,BranchName,IFSCCode,Active," +
//                "CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from BankDetails where ServerUpdatedStatus=0";
//    }
//
//    public String getLandDetailsRefreshQuery() {
//        return "select PlotCode,FarmerCode,DistrictCode,MandalCode,VillageCode,SurveyNumber,AdangalOrFileNo,CareTaker,POCContactInfo,POCContactNumber,OwnerShip,LandlordName,LandlordContactNumber,LeaseDateFrom,LeaseDateTo,Comments,TotalArea,TotalPalm,AreaLeftOut," +
//                "MOUNo,MOUDate,Landmark,PlotAddress,SourceOfWater,NumberOfBoreWells,GPSPlotArea,PlotDifference,Representative,Active,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from LandDetails where ServerUpdatedStatus=0 ";
//    }
//
//    public String getPlotBoundaryRefreshQuery() {
//        return "select BoundaryId,FarmerCode,PlotCode,Latitude,Longitude,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from PlotBoundaries where ServerUpdatedStatus=0 ";
//    }
//
//    public String getCropInfoRefreshQuery() {
//        return "select CropId,FarmerCode,PlotCode,CropCode,OtherCrop,CropVarietyType,CropVarietyName,AreaAllocated," +
//                "CountOfTrees,YearOfPlanting,CropCodeBeforeOilPalm,OtherCropBeforeOilPalm,SwapReasonCode,OtherSwapReason," +
//                "CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from CropInfo where ServerUpdatedStatus=0";
//    }
//
//    public String getCropInfoEditQuery(final int cropId) {
//        return getCropInfoRefreshQuery() + " and CropId ='" + cropId + "'";
//    }
//
//    public String getInterCropDetailsRefreshQuery() {
//        return "select InterCropId,FarmerCode,PlotCode,InterCropInYear,CropCode,OtherCrop,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from InterCropDetails where ServerUpdatedStatus=0";
//    }
//
//    public String getNeighbourPlotRefreshQuery() {
//        return "select NeighboringPlotId,FarmerCode,PlotCode,NeighbourPlot,CropCode,OtherCrop,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from NeighboringPlot where ServerUpdatedStatus=0";
//    }
//
//    public String getFFBHarvestRefreshQuery() {
//        return "select FFBHarvestId,FarmerCode,PlotCode,CollectionCentreId,ModeOfTransport,HarvestingMethod," +
//                "WagesPerDay,ContractRsPerMonth,ContractRsPerAnum,TypeOfHarvesting,ContractorPitch,FarmerConsent,Comments,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from FFB_HarvestDetails where ServerUpdatedStatus=0";
//    }
//
//    public String getFFBHarvestEditQuery(final String plotCode) {
//        return getFFBHarvestRefreshQuery() + " and PlotCode ='" + plotCode + "'";
//    }
//
//    public String getUprootmentRefreshQuery() {
//        return "select UprootmentId,FarmerCode,PlotCode,SeedingsPlanted,TreesCurrently,Uprootment,UprootmentReasonCode," +
//                "OtherReason,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from UprootmentDetails where ServerUpdatedStatus=0";
//    }
//
//    public String getUprootmentEditQuery(final String plotCode) {
//        return getUprootmentRefreshQuery() + " and PlotCode ='" + plotCode + "'";
//    }
//
//    public String getCropMaintainRefreshQuery() {
//        return "select CropMaintenanceId,CropMaintenanceTypeId,FarmerCode,PlotCode,StafflastVisit,RateOurService,Comments," +
//                "Pruning,FertilizerSource,FertilizerType,FertilizerProductName,WeedCode,WeedMethod,ChemicalCode,OtherChemical," +
//                "UnitOfMeasure,DosageGiven,LastAppliedDate,FrequencyApplicationPerYear,RateOnScale,CreatedBy,CreatedDate," +
//                "UpdatedBy,UpdatedDate,ServerUpdatedStatus from CropMaintenance where ServerUpdatedStatus=0";
//    }
//
//    public String getCropMaintainEditQuery(final String plotCode, final String cropMaintenanceId) {
//        return getCropMaintainRefreshQuery() + " and PlotCode ='" + plotCode + "'" + " and CropMaintenanceId = '" + cropMaintenanceId + "'";
//    }
//
//    public String getPlantProtectionRefreshQuery() {
//        return "select PlantProtectionId,PlantProtecionTypeId,FarmerCode,PlotCode,DiseaseCode,DiseaseName,ChemicalCode,OtherChemical," +
//                "PestCode,OtherPestName,UOM,DosageGiven,LastAppliedDate,Observations,Weavils,Mulching,CreatedBy,CreatedDate,UpdatedBy," +
//                "UpdatedDate,ServerUpdatedStatus from PlantProtectionDetails where ServerUpdatedStatus=0";
//    }
//
//    public String getPlantProtectionEditQuery(final String plotCode, final String PlantProtectionId) {
//        return getPlantProtectionRefreshQuery() + " and PlotCode ='" + plotCode + "'" + " and PlantProtectionId = '" + PlantProtectionId + "'";
//    }
//
//    public String getHealthPlantRefreshQuery() {
//        return "select PlantationDetailsId,FarmerCode,PlotCode,AppearanceOfTrees,GrowthOfTree,HeightOfTree,ColorOfFruit,SizeOfFruit," +
//                "PalmHygiene,TypeOfPlantation,Comments,Photo,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from HealthofPlantationDetails where ServerUpdatedStatus=0";
//    }
//
//    public String getHealthPlantEditQuery(String plotCode) {
//        return getHealthPlantRefreshQuery() + " and PlotCode ='" + plotCode + "'";
//    }

    //*****************************************************END OF REFRESH QUERIES************************************************************

//    public String getHealthPlantImageQuery(String plotCode) {
//        return "select Photo from PictureReporting where PlotCode ='" + plotCode + "'" + " and ModuleId = '2'";
//    }
//
//    public String getComplaintRefreshQueries() {
//        return "select ComplaintId,FarmerCode,PlotCode,NatureofComplaint,DegreeOfComplaint,Comments,Status,Resolution,ResolvedBy," +
//                "FollowupRequired,NextFollowupDate,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from ComplaintDetails where ServerUpdatedStatus=0";
//    }
//
//    public String getComplaintEditQueries(String plotCode) {
//        return getComplaintRefreshQueries() + " and PlotCode ='" + plotCode + "'";
//    }
//
//    public String getMarketSurveyRefreshQuery() {
//        return "select SurveyId,VillageCode,VillageName,MarketSurveyNo,MarketSurveyDate,Farmer,FarmerCode,PersonName,FamilyCount," +
//                "CookingOilType,Brand,QuantityConsumedperMonth,AmountPaidForOilPerMonth,Total,ReasonForParticularOil,SwapToPalmOil," +
//                "BrandAmount,SmartPhone,Cattle,CattleQuantity,CattleDetails,OwnVehicles,VehiclesDetails,CollectionCentreIssues,IssueDetails," +
//                "Referrals,ReferralName,MobileNo,Complaint,CreatedBy,CreatedDate,UpdatedBy,UpdatedDate,ServerUpdatedStatus from MarketSurveyAndReferrals where ServerUpdatedStatus=0";
//    }
//
//
//    public String getPlotExistanceInAnyPalmDetailsQuery(final String plotCode) {
//        return "select COUNT(*) " +
//                "from (" +
//                "    select PlotCode from CropInfo" +
//                "    union all" +
//                "    select PlotCode from CropMaintenance" +
//                "    union all" +
//                "    select PlotCode from FFB_HarvestDetails" +
//                "    union all" +
//                "    select PlotCode from HealthofPlantationDetails" +
//                "    union all" +
//                "    select PlotCode from InterCropDetails" +
//                "    union all" +
//                "    select PlotCode from NeighboringPlot" +
//                "    union all" +
//                "    select PlotCode from PlantProtectionDetails" +
//                "     union all" +
//                "    select PlotCode from UprootmentDetails" +
//                "     union all" +
//                "    select PlotCode from ComplaintDetails" +
//                ") a" +
//                "where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getTableDataExist(final String plotCode, String table) {
//        return "select * from " + table + " where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getTableData(final String CropMaintenanceTypeId, String table, String plotCode) {
//        return "select * from " + table + " where CropMaintenanceTypeId ='" + CropMaintenanceTypeId + "' and PlotCode ='" + plotCode + "'";
//    }
//
//    public String getData(final String PlantProtecionTypeId, String table, String plotCode) {
//        return "select * from " + table + " where PlantProtecionTypeId ='" + PlantProtecionTypeId + "' and PlotCode ='" + plotCode + "'";
//    }
//
//    public String getMarketSurveyNumber() {
//        return "SELECT COALESCE(MAX(MarketSurveyNo),0)+1 FROM MarketSurveyAndReferrals";
//    }
//
//    public String getEmployeeDistrict(final String EmployeeId, String StateCode) {
//        return "select Distinct D.DistrictCode, D.DistrictId,D.DistrictName  from DistrictMaster  D" + " inner join EmployeeVillageAllocation E  on D.DistrictCode = E.DistrictCode where E.EmployeeId ='" + EmployeeId + "'and E.StateCode ='" + StateCode + "'";
//    }
//
//    public String getEmployeeMandal(final String EmployeeId, String DistrictCode) {
//        return "select Distinct D.MandalCode, D.MandalId,D.MandalName  from MandalMaster  D" + " inner join EmployeeVillageAllocation E  on D.MandalCode = E.MandalCode where E.EmployeeId ='" + EmployeeId + "'and E.DistrictCode ='" + DistrictCode + "'";
//    }
//
//    public String getEmployeeVillage(final String EmployeeId, String MandalCode) {
//        return "select Distinct D.VillageCode, D.VillageId,D.VillageName  from VillageMaster  D" + " inner join EmployeeVillageAllocation E  on D.VillageCode = E.VillageCode where E.EmployeeId ='" + EmployeeId + "'and E.MandalCode ='" + MandalCode + "'";
//    }
//
//    public String getImageDetails() {
//        return "select FarmerCode, PlotCode, ModuleTypeId, FileLocation from FileRepository where ServerUpdatedStatus = '0'";
//    }
//
//    public String updatedImageDetailsStatus(String code, final String farmerCode, int moduleId) {
//        return "update PictureReporting set ServerUpdatedStatus = 'true' where Code = '" + code + "' and FarmerCode ='" + farmerCode + "'" + " and ModuleId = " + moduleId;
//    }
//
//    public String deleteTableData(final String farmerCode, String table, String plotCode) {
//        return "delete from " + table + " where FarmerCode ='" + farmerCode + "' and PlotCode ='" + plotCode + "'";
//    }
//
//    public String deleteTableData(final String farmerCode, String table) {
//        return "delete from " + table + " where FarmerCode ='" + farmerCode + "'";
//    }
//
//    public String updatedConsignmentDetailsStatus(final String consignmentCode) {
//        return "update Consignment set ServerUpdatedStatus = 'true' where Code = " + consignmentCode;
//    }
//
//    public String updatedCollectionPlotXRefDetailsStatus(final String collectionCode, String plotCode) {
//        return "update CollectionPlotXref set ServerUpdatedStatus = 'true' where CollectionCode ='" + collectionCode + "' and PlotCode ='" + plotCode + "'";
//    }
//
//    public String updatedCollectionDetailsStatus(final String collectionCode) {
//        return "update Collection set ServerUpdatedStatus = 'true' where Code = " + collectionCode;
//    }
//
//    public String updatedImagesStatus(final String farmerCode, String plotCode) {
//
//        String query = "update PictureReporting set ServerUpdatedStatus = 1 where FarmerCode = '" + farmerCode + "'";
//        if (TextUtils.isEmpty(plotCode)) {
//            query = query + " and PlotCode IS NULL OR PlotCode = ''";
//        } else {
//            query = query + " and PlotCode ='" + plotCode + "'";
//        }
//        return query;
//    }
//
//    public String deleteOldRecord() {
//        return "delete from %s where %s = '" + "%s" + "' and PlotCode = '" + "%s" + "'";
//    }
//
//    public String deleteOldData() {
//        return "delete from %s where %s = '" + "%s" + "'";
//    }
//
//    public String queryToFindJunkData() {
//        return "SELECT DISTINCT(src.PlotCode) as PlotCode, src.FarmerCode from %s src " +
//                "LEFT JOIN LandDetails l" +
//                "on l.PlotCode = src.PlotCode " +
//                "where l.PlotCode IS NULL";
//    }
//
//    public String deleteInCompleteData() {
//        return "delete from %s where PlotCode IN (" + "%s" + ")";
//    }
//
//    public String deleteHOPimagesData() {
//        return "delete from PictureReporting where ServerUpdatedStatus = '1' and ModuleId = '2'";
//    }
//
//    public String queryToGetIncompleteMarketSurveyData() {
//        return "SELECT src.FarmerCode from MarketSurveyAndReferrals src " +
//                "LEFT JOIN FarmerDetails l" +
//                "on src.FarmerCode = l.FarmerCode" +
//                "where l.FarmerCode IS NULL";
//    }
//
//    public String deleteInCompleteMarketSurveyData() {
//        return "delete from %s where FarmerCode IN (" + "%s" + ")";
//    }
//
//    //*****conversion potential queries *********//
//
//    //*****water,power,soil Queries************//
//    public String getTypeofIrrigationQuery() {
//        return "";
//    }
//
//    public String getplotPrioritizationQuery() {
//        return "";
//    }
//
//    public String getSoilTypeQuery() {
//        return "";
//    }
//
//    public String getPowerAvailabilityQuery() {
//        return "";
//    }
//
//    public String getTypeOfPowerQuery() {
//        return "";
//    }
//
//    public String getFarmerReadytoConvertQuery() {
//        return "";
//    }
//
//    public String getConversionPotentialScore() {
//        return "";
//    }
//
//
//    /* Query for getting farmers data for CC data */
//    public String getFarmersDataForCC() {
//        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.DOB, f.GuardianName,  f.MotherName,\n" +
//                " s.Name as StateName, D.Name as DistrictName,\n" +
//                " f.ContactNumber, f.MobileNumber, v.Name VillageName, v.Code AS VillageCode, v.Id as VillageId, s.Code as StateCode, s.Id as StateId, m.Code as MandalCode,  d.Code as DistrictCode,\n" +
//                "   addr.AddressLine1, addr.AddressLine2, addr.AddressLine3, addr.Landmark, B.AccountNumber, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension, (select BranchName from Bank where Id = B.BankId) as BranchName," +
//                " (select Desc from TypeCdDmt where TypeCdId IN(select BankId from Bank where Id = B.BankId)) as bankName from Farmer f \n" +
//                "   left join Village v on f.VillageId = v.Id\n" +
//                "   left join Mandal m on f.MandalId = m.Id\n" +
//                "   left join District d on f.DistrictId = d.Id\n" +
//                "   left join Address addr on f.AddressCode = addr.Code\n" +
//                "   left join FarmerBank B on B.FarmerCode = f.Code\n" +
//                "   left join State s on f.StateId = s.Id\n" +
//                "   left join FileRepository fileRep on f.Code = fileRep.FarmerCode\n" +
//                " and fileRep.ModuleTypeId = 193;";
//    }
//
    public String getFarmersDataForWithOffsetLimit(String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.MobileNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode\n" +
                "and fileRep.ModuleTypeId = 193 \n" +
                "where f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' group by f.Code limit " + limit + " offset " + offset + ";";
    }

//    public String getPlotDetailsForConversion(final String farmercode, final int plotStatus) {
//
//        return "select p.Code, p.TotalPalmArea, p.TotalPlotArea, p.GPSPlotArea, p.SurveyNumber, addr.Landmark,\n" +
//                "v.Code AS VillageCode, v.Name as VillageName, v.Id as VillageId,\n" +
//                "m.Code as MandalCode, m.Name as MandalName, m.Id as MandalId,\n" +
//                "d.Code as DistrictCode, d.Name as DistrictName, d.Id as DistrictId,\n" +
//                "s.Code as StateCode, s.Name as StateName, s.Id as StateId,\n" +
//                "advd.NoOfSaplingsAdvancePaidFor as advanced , sum(nusd.NoOfSaplingsDispatched) as nursery \n" +
//                " from Plot p\n" +
//                "inner join Address addr on p.AddressCode = addr.Code\n" +
//                "inner join Village v on addr.VillageId = v.Id\n" +
//                "inner join Mandal m on addr.MandalId = m.Id\n" +
//                "inner join District d on addr.DistrictId = d.Id\n" +
//                "inner join State s on addr.StateId = s.Id\n" +
//                "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
//                "inner join AdvancedDetails advd on p.Code  = advd.PlotCode\n"+
//                "inner JOIN NurserySaplingDetails nusd on p.Code = nusd.PlotCode\n"+
//                "where p.FarmerCode='" + farmercode + "'" +"and fh.StatusTypeId = '" + plotStatus + "'" + " and fh.IsActive = 1  group by p.Code HAVING advanced = nursery ";
//    }
//
//    public String getPlotDetailsForCC(final String farmercode, final int plotStatus, final int multiStatus, boolean fromCm) {
//        String statusType = "";
//        if (fromCm) {
//            statusType = "and fh.StatusTypeId IN (" + plotStatus + "," + multiStatus + ")";
//        } else {
//            statusType = "and fh.StatusTypeId = '" + plotStatus + "'";
//        }
//        return "select p.Code, p.TotalPalmArea, p.TotalPlotArea, p.GPSPlotArea, p.SurveyNumber, addr.Landmark,\n" +
//                "v.Code AS VillageCode, v.Name as VillageName, v.Id as VillageId,\n" +
//                "m.Code as MandalCode, m.Name as MandalName, m.Id as MandalId,\n" +
//                "d.Code as DistrictCode, d.Name as DistrictName, d.Id as DistrictId,\n" +
//                "s.Code as StateCode, s.Name as StateName, s.Id as StateId from Plot p\n" +
//                "inner join Address addr on p.AddressCode = addr.Code\n" +
//                "inner join Village v on addr.VillageId = v.Id\n" +
//                "inner join Mandal m on addr.MandalId = m.Id\n" +
//                "inner join District d on addr.DistrictId = d.Id\n" +
//                "inner join State s on addr.StateId = s.Id\n" +
//                "inner join FarmerHi" +
//                "story fh on fh.PlotCode = p.Code\n" +
//                "where p.FarmerCode='" + farmercode + "'" + statusType + " and fh.IsActive = 1  group by p.Code";
//    }
//
//    public String getPlotDetailsForCC(final String farmercode, final int plotStatus) {
//        return getPlotDetailsForCC(farmercode, plotStatus, 0, false);
//    }
//
//    public String getComplaintsDataByPlot(String plotcode, String farmerCode) {
//        return "SELECT cx.ComplaintCode,cx.ComplaintTypeId,csh.AssigntoUserId,csh.StatusTypeId,c.CriticalityByTypeId,c.createddate ,p.code," +
//                "(select firstname from farmer where code = '" + farmerCode + "') as fname," +
//                "(select lastname from farmer where code = '" + farmerCode + "') as lname," +
//                "(select villageid from farmer where code = '" + farmerCode + "') as vcode\n" +
//                "from Complaints c \n" +
//                "inner join \n" +
//                "ComplaintTypeXref cx on c.code=cx.ComplaintCode " +
//                "inner join" +
//                " ComplaintStatusHistory csh on csh.ComplaintCode = c.Code  " +
//                "inner join plot p on p.code =c.plotcode  " +
//                "where c.plotcode = '" + plotcode + "'" + " group by cx.ComplaintCode";
//    }
//
//    public String getPlotDetailsForNonGeo(final String farmercode, final int plotStatus) {
//        return "select p.Code, p.TotalPalmArea, p.TotalPlotArea, p.GPSPlotArea, p.SurveyNumber, addr.Landmark,\n" +
//                "v.Code AS VillageCode, v.Name as VillageName, v.Id as VillageId,\n" +
//                "m.Code as MandalCode, m.Name as MandalName, m.Id as MandalId,\n" +
//                "d.Code as DistrictCode, d.Name as DistrictName, d.Id as DistrictId,\n" +
//                "s.Code as StateCode, s.Name as StateName, s.Id as StateId, geo.Latitude, geo.Longitude from Plot p\n" +
//                "inner join Address addr on p.AddressCode = addr.Code\n" +
//                "inner join Village v on addr.VillageId = v.Id\n" +
//                "inner join Mandal m on addr.MandalId = m.Id\n" +
//                "inner join District d on addr.DistrictId = d.Id\n" +
//                "inner join State s on addr.StateId = s.Id\n" +
//                "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
//                "left join GeoBoundaries geo on p.Code = geo.PlotCode\n" +
//                "where p.FarmerCode='" + farmercode + "'" + "and fh.StatusTypeId = '" + plotStatus + "'" + " and fh.IsActive = 1 group by p.Code";
//    }
//
//    public String getCollectionCenterMaster(final String userId) {
//        return "select c.Code collectionCenterCode, c.Name, c.WeighBridgeTypeId, t.Desc, v.Id as VillageId, v.Code as VillageCode, v.Name as VillageName from CollectionCenter  c                                                                               \n" +
//                "inner join Village v on c.VillageId = v.Id\n" +
//                "inner join TypeCdDmt t on c.WeighBridgeTypeId = t.TypeCdId where c.IsMill = 'false' and c.VillageId IN " + "(select villageId from UserVillageXref where userId IN ('" + userId + "'))" + " group by collectionCenterCode order by c.Id";
//    }
//
//    public String getMaxCollectionCenterCode(final String year, final String collectionCenterCode, final String tableName) {
//        return "select MAX(cast(substr(Code, INSTR(Code,'-') + 1, length(Code)) as INTEGER)) as Maxnumber from " + tableName +
//                " where Code like '%" + year + "%' and Code like '%" + collectionCenterCode + "%'";
//    }
//
//    public String getMaxReceiptCode(final String year, final String collectionCenterCode, final String tableName) {
//        return "select MAX(cast(substr(Code, INSTR(Code,'-') + 1, length(Code)) as INTEGER)) as Maxnumber from " + tableName +
//                " where ReceiptCode like '%" + year + "%' and ReceiptCode like '%" + collectionCenterCode + "%'";
//    }
//
//    public String getCollectionCenterWithVillage(final String gpsVillage) {
//        String[] villageNames = new String[2];
//        String filteredVillageName = "";
//        String filteredVillageName2 = "";
//        if (!TextUtils.isEmpty(gpsVillage)) {
//            villageNames = gpsVillage.split("-");
//        }
//        if (null != villageNames && villageNames.length > 1) {
//            filteredVillageName = villageNames[0];
//            filteredVillageName2 = villageNames[1];
//        }
//        return "select c.Code collectionCenterCode, c.Name, c.WeighBridgeTypeId, t.Desc, v.Id as VillageId, v.Code as VillageCode, v.Name as VillageName from CollectionCenter c\n" +
//                "inner join Village v on c.VillageId = c.VillageId \n" +
//                "inner join TypeCdDmt t on c.WeighBridgeTypeId = t.TypeCdId where v.Name = '" + filteredVillageName + "'" + " OR v.Name = '" + filteredVillageName2 + "'" + " and c.IsMill = 'false' group by collectionCenterCode order by c.Id";
//    }
//
//    public String getCollectionDetailsRefreshQuery() {
//        return "select Code,CollectionCenterCode,FarmerCode,WeighbridgeCenterId,WeighingDate,VehicleNumber,DriverName," +
//                "GrossWeight,TareWeight ,NetWeight,OperatorName,Comments,TotalBunches,RejectedBunches,AcceptedBunches," +
//                "Remarks ,GraderName ,ReceiptGeneratedDate,ReceiptCode,WBReceiptName," +
//                "WBReceiptLocation,WBReceiptExtension,IsActive,CreatedByUserId ,CreatedDate ,UpdatedByUserId,ServerUpdatedStatus,UpdatedDate,IsCollectionWithOutFarmer from Collection where ServerUpdatedStatus='false'";
//    }
//
//    public String getCollectionPlotXRefRefreshQuery() {
//
//        return "select CollectionCode,PlotCode,ServerUpdatedStatus,YieldPerHectar,NetWeightPerPlot,IsMainFarmerPlot  from CollectionPlotXref where ServerUpdatedStatus='false'";
//    }
//
//    public String getConsignmentRefreshQuery() {
//        return "select Code,CollectionCenterCode,VehicleNumber,DriverName,MillCode,TotalWeight,GrossWeight," +
//                "TareWeight ,NetWeight,WeightDifference,ReceiptGeneratedDate,ReceiptCode ," +
//                " IsActive,CreatedByUserId,CreatedDate ,UpdatedByUserId ,UpdatedDate,ServerUpdatedStatus, TotalBunches, RejectedBunches, AcceptedBunches, Remarks, GraderName from Consignment where ServerUpdatedStatus='false'";
//    }
//
//    public String getConsignementStatusHistoryRefreshQuery() {
//        return "select ConsignmentCode ,StatusTypeId,OperatorName,Comments,IsActive,CreatedByUserId,CreatedDate,UpdatedByUserId," +
//                "UpdatedDate,ServerUpdatedStatus from ConsignmentStatusHistory where ServerUpdatedStatus='false'";
//    }
//
//    public String getDateRangeCollectionReportDetails(String fromdate, String todate) {
//        return "select Collection.Code ,WeighingDate,FarmerCode,Farmer. FirstName,Farmer.MiddleName,Farmer.LastName," +
//                " GrossWeight,TareWeight,NetWeight,VehicleNumber,CollectionCenter.InchargeName \n" +
//                " from Collection\n" +
//                " inner join Farmer on Farmer.Code=Collection.FarmerCode\n" +
//                " inner join CollectionCenter on CollectionCenter.Code=Collection.CollectionCenterCode" +
//                " WHERE date(WeighingDate) BETWEEN date('" + fromdate + "')" +
//                " AND date('" + todate + "')";
//    }
//
//    public String getCollectionReportDetails() {
//        return "select Collection.Code ,WeighingDate,FarmerCode,Farmer. FirstName,Farmer.MiddleName,Farmer.LastName," +
//                " GrossWeight,TareWeight,NetWeight,VehicleNumber,CollectionCenter.InchargeName, cXref.PlotCode \n" +
//                " from Collection\n" +
//                "inner join CollectionPlotXref cXref on cXref.CollectionCode = Collection.Code \n" +
//                " inner join Farmer on Farmer.Code=Collection.FarmerCode\n" +
//                " inner join CollectionCenter on CollectionCenter.Code=Collection.CollectionCenterCode";
//    }
//
//    public String getCollectionPlotCodes(String farmercode) {
//        return "select Code  from Plot where FarmerCode='" + farmercode + "'";
//    }
//
//    public String getNewUserDetails(final String imeiNumber) {
//        return "select u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.TabletId from UserInfo u\n" +
//                "inner join Tablet t on u.TabletId = t.Id where t.IMEINumber = '" + imeiNumber + "'";
//    }
//
//    public String getNewUserDetailsWithUserName(final String userName, final String password) {
////        return "select u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.TabletId, uv.VillageId from UserInfo u\n" +
////                "inner join Tablet t on u.TabletId = t.Id " +
////                "inner join UserVillageXref uv on u.Id = uv.UserId where u.UserName = '" + userName + "'" + " and u.Password = '" + password + "'";
//
//        return "select u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.Id, u.FirstName from UserInfo u where u.UserName = '" + userName + "'" + " and u.Password = '" + password + "'";
//    }
//
//    public String getUserVillageIds(final String userId) {
//        return "select DISTINCT(villageId) from UserVillageXref where userId IN (" + userId + ")";
//    }
//
//    public String getUserMandalIds(final String villageIds) {
//        return "select DISTINCT(MandalId) from Village where Id IN (" + villageIds + ")";
//    }
//
//    public String getUserDistrictIds(final String mandalIds) {
//        return "select DISTINCT(DistrictId) from Mandal where Id IN (" + mandalIds + ")";
//    }
//
//    public String getUserStateIds(final String DistrictIds) {
//        return "select DISTINCT(StateId) from District where Id IN (" + DistrictIds + ")";
//    }
//
//    public String getPrivateWeighbridgeDetails() {
//        return "select Id, Name from WeighBridgeCenter";
//    }
//
//    public String getMillInformation() {
//        return "select Code, Name from CollectionCenter where IsMill = 'true'";
//    }
//
//    public String getCollectionCenterReports(final String fromDate, final String toDate) {
//        String query = "select Collection.Code ,WeighingDate, Collection.FarmerCode as fCode,Farmer. FirstName,Farmer.MiddleName,Farmer.LastName, GrossWeight,TareWeight,NetWeight,VehicleNumber,CollectionCenter.InchargeName, cXref.PlotCode, DATE(substr(Collection.CreatedDate, 0, INSTR(Collection.CreatedDate, ' ') + 1)) date, Collection.ReceiptCode, cc.Name, (select Desc from TypeCdDmt where TypeCdId IN(select BankId from Bank where Id = B.BankId)) as bankName,\n" +
//                "(select BranchName from Bank where Id = B.BankId) as BranchName,\n" +
//                "B.AccountNumber, RejectedBunches,\n" +
//                "wc.Name as wcName\n" +
//                "from Collection\n" +
//                "inner join CollectionPlotXref cXref on cXref.CollectionCode = Collection.Code\n" +
//                "inner join CollectionCenter cc on cc.Code = Collection.CollectionCenterCode\n" +
//                "inner join Farmer on Farmer.Code = Collection.FarmerCode\n" +
//                "inner join FarmerBank B on B.FarmerCode = Collection.FarmerCode\n" +
//                "left join WeighBridgeCenter wc on wc.Id = Collection.WeighbridgeCenterId\n" +
//                "inner join CollectionCenter on CollectionCenter.Code=Collection.CollectionCenterCode";
//
//        if (!TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
//            query = query + " where date between '" + fromDate + "' and '" + toDate + "'";
//        }
//
//        query = query + " group by Collection.Code  order by Collection.Code";
//
//        return query;
//    }
//
//    public String getCollectionNetSum(final String fromDate, final String toDate) {
//        return "select sum(NetWeight) from Collection  where DATE(substr(Collection.CreatedDate, 0, INSTR(Collection.CreatedDate, ' ') + 1)) between '" + fromDate + "' and '" + toDate + "'";
//    }
//
//    public String updateFileRepositoryTable(final String fileLocation, final String farmerCode) {
//        return "update FileRepository set FileLocation = '" + fileLocation + "' where FarmerCode = '" + farmerCode + "'";
//    }
//
//    public String getStatusTypeId() {
//        return "select TypeCdId from TypeCdDmt where Desc = 'Transit'";
//    }
//
//    public String getTabId(final String imieiNumber) {
//        return "select Name from Tablet where IMEINumber = '" + imieiNumber + "'";
//    }
//
//    public String getUserDetailsNewQuery(String imeiNumber) {
//        return "SELECT u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.Id, u.FirstName, t.Name, u.UserCode \n" +
//                " from Tablet t\n" +
//                " inner join UserInfo u on u.TabletId = t.Id\n" +
//                " where imeinumber = '" + imeiNumber + "'";
//    }
//
//    public String getUserDetailsForKrasQuery(final int managerId) {
//        return "SELECT u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.Id, u.FirstName, t.Name, u.UserCode \n" +
//                " from Tablet t\n" +
//                " inner join UserInfo u on u.TabletId = t.Id\n" +
//                " where ManagerId = '" + managerId + "'";
//    }
//
//    public String getMainUserDetails(final String imieiNumber) {
//        return "SELECT u.UserId, u.UserName, u.Password, u.RoleId, u.ManagerId, u.Id, u.FirstName, t.Name, r.Code\n" +
//                "                 from Tablet t\n" +
//                "                 inner join UserInfo u on u.TabletId = t.Id\n" +
//                "                 inner join Role r on r.Id = u.RoleId\n" +
//                "                 where imeinumber = '" + imieiNumber + "'";
//    }
//
//    public String getPlotCodes(final String collectionCode) {
//        return "SELECT PlotCode from CollectionPlotXref where CollectionCode = '" + collectionCode + "'";
//    }
//
//    public String getCollectionCodes() {
//        return "select Code from Collection where ServerUpdatedStatus = 'false'";
//    }
//
//    public String getConsignmentCodes() {
//        return "select Code from Consignment where ServerUpdatedStatus = 'false'";
//    }
//
//    public String getCollectionPlotXrefData() {
//        return "select DISTINCT(CollectionCode), PlotCode from CollectionPlotXRef where ServerUpdatedStatus = 'false'";
//    }
//
//
//    public String getPlotsCount(final String farmerCode) {
//        return "select count(*) from FarmerHistory where FarmerCode = '" + farmerCode + "' and StatusTypeId = '89'";
//    }
//
//    public String getCropsMasterInfo() {
//        return "select Id, name from LookUp where LookupTypeId = '22'";
//    }
//
//    public String getSourceOfWaterInfo() {
//        return "select Id, name from LookUp where LookupTypeId = '12'";
//    }
//
    public String getCategory() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 12";
    }

    public String getSoilTypes() {
        return "select Id, Name from LookUp where LookUpTypeId = 68";
    }

    public String getIrrigationType() {
        return "select Id, Name from LookUp where LookUpTypeId = 69";
    }


    public String getsoilCultureType() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 26";
    }

    public String getownership() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 16";
    }


    public String getplotStatus() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 14";
    }
    public String getbankNames() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 4";
    }
    public String getBranchNames(String BankId) {
        return "select Id,BranchName,IFSCCode from Bank where BankId ='" + BankId + "'";
    }
    public String getBankname(String BankId) {
        return " select Desc from TypeCdDmt where TypeCdId IN(select BankId from Bank where Id ='" + BankId + "')";

    }
    public String getbuyertype(String BuyerTypeId) {
        return " select DESC from TypeCdDmt where TypeCdId ='" + BuyerTypeId + "'";

    }

    public String getvendorCategorytype(String vendorCategoryId) {
        return " select Name from LookUp where Id ='" + vendorCategoryId + "'";

    }
    public String getbanchname(String BankId) {
        return " select BranchName from Bank where  Id ='" + BankId + "'";

    }
  //  select Desc from TypeCdDmt where TypeCdId IN(select BankId from Bank where Id = 2)
    public String getIfscCodes(String Id) {

        return "select IFSCCode, BranchName from Bank where Id ='" + Id + "'";
    }

      //  select IFSCCode, BranchName from Bank where Id = '2'


    public String getEducationType() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 2";
    }

    public String getbuyerType() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = '23' and isActive ='true'";
    }

    public String getvendorCategoryType() {
        return "select Id, Name from LookUp where LookUpTypeId = 76";
    }

    public String getgender() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = 1";
    }

    public String geteducationDegreeType() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = '9' and isActive ='true'";
    }
    public String getComplainttype() {
        return "select TypeCdid, Desc from TypeCdDmt where classTypeId = '29' and isActive ='true'";
    }

    public String getcomplaintstatus() {
        return "select  Desc from TypeCdDmt where classTypeId = '39' and isActive ='true'";
    }
//
//    public String gePlotCareTakerfromDB(String plotcode) {
//        return "select IsPlotHandledByCareTaker from Plot where Code ='" + plotcode + "'";
//    }

    /* Query for getting farmers data for CC data */
//    public String getFarmersDataForCCWithLimit(int position) {
//        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.DOB, f.GuardianName,  f.MotherName,\n" +
//                " s.Name as StateName, D.Name as DistrictName,\n" +
//                " f.ContactNumber, f.MobileNumber, v.Name VillageName, v.Code AS VillageCode, v.Id as VillageId, s.Code as StateCode, s.Id as StateId, m.Code as MandalCode,  d.Code as DistrictCode,\n" +
//                "   addr.AddressLine1, addr.AddressLine2, addr.AddressLine3, addr.Landmark, B.AccountNumber, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension, (select BranchName from Bank where Id = B.BankId) as BranchName,(select Desc from TypeCdDmt where TypeCdId IN(select BankId from Bank where Id = B.BankId)) as bankName from Farmer f \n" +
//                "   left join Village v on f.VillageId = v.Id\n" +
//                "   left join Mandal m on f.MandalId = m.Id\n" +
//                "   left join District d on f.DistrictId = d.Id\n" +
//                "   left join Address addr on f.AddressCode = addr.Code\n" +
//                "   left join FarmerBank B on B.FarmerCode = f.Code\n" +
//                "   left join State s on f.StateId = s.Id\n" +
//                "   left join FileRepository fileRep on f.Code = fileRep.FarmerCode\n" +
//                " and fileRep.ModuleTypeId = 193 limit " + position + " , 20;";
//    }
//
//    public String getConsignmentReportDetails(final String fromDate, final String toDate) {
//        String query = " select Consignment.Code,ReceiptGeneratedDate,MillCode ,TotalWeight,VehicleNumber," +
//                " ConsignmentStatusHistory.OperatorName,CollectionCenter.InchargeName, DATE(substr(Consignment.CreatedDate, 0, INSTR(Consignment.CreatedDate, ' ') + 1)) date, Consignment.ReceiptCode, Consignment.DriverName, cc.Name \n" +
//                " from Consignment\n" +
//                " inner join ConsignmentStatusHistory on ConsignmentStatusHistory.ConsignmentCode =Consignment.Code\n" +
//                " inner join CollectionCenter cc on cc.Code = Consignment.CollectionCenterCode \n" +
//                " inner join CollectionCenter on CollectionCenter.Code=Consignment.CollectionCenterCode";
//
//        if (!TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toDate)) {
//            query = query + " where date between '" + fromDate + "' and '" + toDate + "'";
//        }
//        return query;
//    }
//
//    public String getSelectedFarmer(final String farmerCode) {
//        return "select * from Farmer where Code = '" + farmerCode + "'";
//    }
//
    public String getSelectedPlot(final String plotCode) {
        return "select * from Plot where Code = '" + plotCode + "'";
    }

    //
//    //*************************Refresh Queries****************************//
//
    public String getSelectedFarmerAddress(final String addressCode) {
        //PAPGNTRALTML00001
        return "select * from Address where Code = '" + addressCode + "'";
    }

    public String getSelectedPlotAddress(final String addressCode) {
        return "select * from Address where Code = '" + addressCode + "'";
    }

    //
    public String getSelectedPlotCurrentCrop(final String plotCode) {
        return "select * from PlotCurrentCrop  where PlotCode = '" + plotCode + "'";
    }

    //    public String getSelectedNeighbourPlot(final String plotCode) {
//        return "select * from NeighbourPlot where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getCodeFromId(String tableName, String Id) {
//        return "select Code from " + tableName + " where Id = '" + Id + "'";
//    }
//
//    //*************************Refresh Queries****************************//
//
    public String getSelectedFarmerRefresh() {
        return "select * from UserInfo where ServerUpdateStatus=0";
       // ServerUpdateStatus
    }
//
//    public String getAddressRefresh() {
//        return "select * from Address where ServerUpdatedStatus=0";
//    }
//
    public String getFileRepositoryRefresh() {
        return "select * from FileRepository";
    }

    public String getSoilDetails() {
        return "select * from SoilDetails";
    }

    public String getPowerDetails() {
        return "select * from PowerDetails";
    }

    public String getIrrigationDetails() {
        return "select * from IrrigationDetails";
    }
//
    public String getPlotRefresh() {
        return "select * from Plot";
    }


    public String getFarmerDetails() {
        return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId = 105) Group By Id order by UpdatedDate desc";

    }
    public String getprosFarmerDetails() {
    //  return "select * from UserInfo";
        //return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=2 And StatusTypeId = 104) Group By Id order by UpdatedDate desc";
        return "SELECT * from UserInfo where Code in (select  FarmerCode from Plot where PlotStatusId = 104 ) GROUP by Code ORDER By UpdatedDate desc";
    }


    public String getprosBuyerDetails() {
        return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=8 And StatusTypeId = 104) Group By Id order by UpdatedDate desc";
    }

    public String getprosVendorDetails() {
        return "select * from UserInfo where Id in(select UserId from UserRoleXref where RoleId=9 And StatusTypeId = 104) Group By Id order by UpdatedDate desc";
    }

    public String getFarmerDetailsServerUpdatedStatus() {
        return "select * from UserInfo where ServerUpdatedStatus = '0' GROUP by Code";
    }

    public String getIdentityProofsServerUpdatedStatus() {
        return "select * from IdentityProofs where ServerUpdatedStatus = '0'";
    }

    public String getBankDetailsServerUpdatedStatus() {
        return "select * from BankDetails where ServerUpdatedStatus = '0'";
    }
    public String getplotsServerUpdatedStatus() {
        return "select * from Plot where ServerUpdatedStatus = '0' GROUP by Id";
    }

    public String getplotStatusServerUpdatedStatus() {
        return "select * from PlotStatusHistory where ServerUpdatedStatus = '0'  GROUP by Id";
    }

    public String getSoilDetailsServerUpdatedStatus() {
        return "select * from SoilDetails where ServerUpdatedStatus = '0'  GROUP by Id";
    }

    public String getBuyerDetailsServerUpdatedStatus() {
        return "select * from BuyerDetails where ServerUpdatedStatus = '0'  GROUP by Id";
    }

    public String getVendorDetailsServerUpdatedStatus() {
        return "select * from VendorDetails where ServerUpdatedStatus = '0'  GROUP by Id";
    }

    public String getPowerDetailsServerUpdatedStatus() {
        return "select * from PowerDetails where ServerUpdatedStatus = '0'  GROUP by Id";
    }

    public String getIrrigationDetailsServerUpdatedStatus() {
        return "select * from IrrigationDetails where ServerUpdatedStatus = '0'  GROUP by Id";
    }




    public String getplotDetails() {
        return "select * from Plot";
    }

    public String getFarmerPersonalDetails(String code) {
        return "select * from UserInfo where UserInfo.Code = "+"'"+code+"'";
    }

    public String getbuyerPersonalDetails(String code) {
        return "select * from UserInfo where UserInfo.Code = "+"'"+code+"'";
    }
    public String getbuyerBankdetailsDetails(String code) {
        return "select * from BankDetails where BankDetails.UserCode = "+"'"+code+"'";
    }

    public String getbuyerDetails(String code) {
        return "  select * from BuyerDetails where UserCode = "+"'"+code+"'";
    }
    public String getvendorDetails(String code) {
        return "  select * from VendorDetails where UserCode = "+"'"+code+"'";
    }


    public String getVendorPersonalDetails(String code) {
        return "select * from UserInfo where UserInfo.Code = "+"'"+code+"'";
    }

    public String getBankDetails(String code) {
        return "select * from BankDetails where  BankDetails.UserCode = "+"'"+code+"'";
    }

    public String getBuyerDetails(String code) {
        return "select * from BuyerDetails where  BuyerDetails.UserCode = "+"'"+code+"'";
    }

    public String getFarmerIDProofDetails(String code) {
        return "select * from IdentityProofs where IdentityProofs.UserCode = "+"'"+code+"'GROUP By IdProofTypeId " ;
    }

    public String updateLocationStatus() {
        return "update LocationTracker set ServerUpdatedStatus = '1' where ServerUpdatedStatus = '0'";
    }
    public String getComplaintrDetailsServerUpdatedStatus() {
        return "select * from Complaints where ServerUpdatedStatus = '0'";
    }
    public String getComplaintrepoDetailsServerUpdatedStatus() {
        return "select * from ComplaintRepository where ServerUpdatedStatus = '0'";
    }
    public String getComplaintstatusDetailsServerUpdatedStatus() {
        return "select * from ComplaintStatusHistory where ServerUpdatedStatus = '0' Group By Id";
    }

    public String getComplaintxrfsDetailsServerUpdatedStatus() {
        return "select * from ComplaintTypeXref where ServerUpdatedStatus = '0'";
    }

    public String getInsuranceDetailsServerUpdatedStatus() {
        return "select * from InsuranceDetails where ServerUpdatedStatus = '0'";
    }

    public String getPlotCropCycleServerUpdatedStatus() {
        return "select * from PlotCropCycle where ServerUpdatedStatus = '0'";
    }


    public String getComplaitsRepo(String code) {
        return "select * from ComplaintRepository where ComplaintCode="+"'"+code + "'" + " and IsActive = 'true'"+" GROUP By Id " ;
    }

    public String plotstatus() {
        return "update %s set ServerUpdatedStatus = '1' where ServerUpdatedStatus = '0'";
    }

//    public String queryVerifyFalogDistance() {
//
////        SELECT * FROM LocationTracker ORDER BY Id  DESC LIMIT 1;
//        return "select *  from LocationTracker ORDER BY Id DESC LIMIT 1";
//    }
    public String queryVerifyFalogDistance() {

//        SELECT * FROM LocationTracker ORDER BY Id  DESC LIMIT 1;
        return "select Latitude, Longitude from LocationTracker ORDER BY Id DESC LIMIT 1";
    }
    public String getFarmerplotDetails(String farmerCode) {

        return "select * from Plot where Plot.FarmerCode = "+"'"+farmerCode+"'GROUP By Id " ;
    }


    public String getFarmerbankDetails(String farmerCode) {

        return "select * from BankDetails where BankDetails.UserCode =  "+"'"+farmerCode+"'";
    }
    public String Notification() {
//        return "select * from Notification";
        return  "select * from Notification  GROUP by id ORDER by CreatedDate desc";
    }

//

    public String getstate(String stateid) {
        return "select Name from State where Id = "+"'"+stateid+"'";
    }
    public String getDistric(String districid) {
        return "select Name from District where Id = "+"'"+districid+"'";
    }
    public String getMandal(String mandalid) {
        return "select Name from Mandal where Id = " + "'" + mandalid + "'";
    }
    public String getvilage(String villageid) {
        return "select Name from Village where Id = "+"'"+villageid+"'";
    }
    public String getstatusid(String status) {
        return "select  Desc from TypeCdDmt where TypeCdId = "+"'"+status+"'";
    }
    public String getsoiltype(String plotcode) {

        return "select SoilTypeId from  SoilDetails where PlotCode  = "+"'"+plotcode+"'GROUP By Id " ;
    }

    public String getirrigationtype(String plotcode) {

        return "select IrrigationTypeId from  IrrigationDetails where PlotCode  = "+"'"+plotcode+"'GROUP By Id " ;
    }
    public String getpoweravailabity(String plotcode) {

        return "select IsAvailable from  PowerDetails where PlotCode   = "+"'"+plotcode+"'GROUP By Id " ;
    }
    public String getmeter(String plotcode) {

        return "select ServiceNumber from  PowerDetails where PlotCode   = "+"'"+plotcode+"'GROUP By Id " ;
    }
    public String getsoil(String status) {
        return "select Name from  LookUp where id = "+"'"+status+"'";
    }

    public String  getnotificationServerUpdatedStatus() {
        return "select * from Notification where ServerUpdatedStatus = '0'  GROUP by Id";
    }

    public String  getgeoboundryServerUpdatedStatus() {
        return "select * from GeoBoundaries where ServerUpdatedStatus=0";
    }

    public String  getlocationServerUpdatedStatus() {
        return "select * from LocationTracker where ServerUpdatedStatus = '0' ";
    }
    public String  getActivityServerUpdatedStatus() {
        return "select * from ActivityLog where ServerUpdatedStatus = '0' ";
    }

    public String  getSoilTestDetailsServerUpdatedStatus() {
        return "select * from SoilTestDetails where ServerUpdatedStatus = '0' ";
    }

    public String  getUserInfoXrefServerUpdatedStatus() {
        return "select * from UserRoleXref where ServerUpdatedStatus = '0' GROUP by Id";
    }



 //   select SoilTypeId from  SoilDetails where PlotCode ='PAPGNTRAL00100001' GROUP By Id

//    public String getownership(String status) {
//        return "select  Desc from TypeCdDmt where TypeCdId = "+"'"+status+"'";
//    }
//select Name from  LookUp where id ='3'
//    //Tracking
//select  Desc from TypeCdDmt where TypeCdId ='105'
//    public String getPlotCurrentCropRefresh() {
//        return "select * from PlotCurrentCrop where ServerUpdatedStatus=0";
//    }
//
//    public String getNeighbourPlotRefresh() {
//        return "select * from NeighbourPlot where ServerUpdatedStatus=0";
//    }
//
//    public String getWaterResourceRefresh() {
//        return "select * from WaterResource where ServerUpdatedStatus=0";
//    }
//
    public String getcompleteplotdetails(String plotcode) {

     //   SELECT * from plot WHERE Code ='PAPGNTRALTML00001' GROUP By Id
        return "select * from plot where Code= "+"'"+plotcode+"'GROUP By Id " ;
    }
//
    public String getPlots(String Farmer) {
       // SELECT Code from Plot where FarmerCode ='APGNTCHPCHPT00001' GROUP By Id
        return "select TotalPlotArea, Code from Plot where FarmerCode = "+"'"+Farmer+"'";
    }

    public String getGpsTrackingRefresh() {
        return "select * from LocationTracker where ServerUpdatedStatus = 0";
    }
//    public String getLastLatLong() {
//        return "select * from LocationTracker ORDER BY ID DESC LIMIT 1";
//    }
//
//    public String getGeoBoundariesRefresh() {
//        return "select * from GeoBoundaries where ServerUpdatedStatus=0";
//    }
//
//    public String getFollowUpRefresh() {
//        return "select * from FollowUp where ServerUpdatedStatus=0";
//    }
//
//    public String getReferralsRefresh() {
//        return "select * from Referrals where ServerUpdatedStatus=0";
//    }
//
//    public String getMarketSurveyRefresh() {
//        return "select * from MarketSurvey where ServerUpdatedStatus=0";
//    }
//
//    public String getFarmerHistoryRefresh() {
//        return "select * from FarmerHistory where ServerUpdatedStatus=0";
//    }
//
    public String getIdentityProofRefresh() {
        return "select * from IdentityProofs";
    }
//
    public String getFarmerBankRefresh() {
        return "select * from BankDetails";
    }
//
    public String getBankDetailshistoryRefresh() {
        return "select * from BankDetailsHistory";
    }
//
    public String getPlotLandlordRefresh() {
        return "select * from PlotStatusHistory";
    }
//
//    public String getLandlordBankRefresh() {
//        return "select * from LandlordBank  where ServerUpdatedStatus=0";
//    }
//
//    public String getLandlordIDProofsRefresh() {
//        return "select * from LandlordIdentityProof  where ServerUpdatedStatus=0";
//    }
//
//    public String getCookingOilRefresh() {
//        return "select * from CookingOil where ServerUpdatedStatus=0";
//    }
//
//    public String getDiseaseRefresh() {
//        return "select * from Disease where ServerUpdatedStatus=0";
//    }
//
//    public String getFertilizerRefresh() {
//        return "select * from Fertilizer  where ServerUpdatedStatus=0";
//    }
//
//    public String getFertilizerProviderRefresh() {
//        return "select * from FertilizerProvider  where ServerUpdatedStatus=0";
//    }
//
//    public String getHarvestRefresh() {
//        return "select * from Harvest  where ServerUpdatedStatus=0";
//    }
//
//    public String getHealthPlantationRefresh() {
//        return "select * from HealthPlantation  where ServerUpdatedStatus=0";
//    }
//
//    public String getInterCropPlantationXrefRefresh() {
//        return "select * from InterCropPlantationXref  where ServerUpdatedStatus=0";
//    }
//
//    public String getNutrientRefresh() {
//        return "select * from Nutrient where ServerUpdatedStatus=0";
//    }
//    public String getRecomFertilizerRefresh() {
//        return "select * from FertilizerRecommendations where ServerUpdatedStatus=0";
//    }
//
//    public String getOwnerShipFileRepositoryRefresh() {
//        return "select * from OwnerShipFileRepository  where ServerUpdatedStatus=0";
//    }
//
//    public String getPestRefresh() {
//        return "select * from Pest  where ServerUpdatedStatus=0";
//    }
//
//    public String getResourcesRefresh() {
//        return "select * from Resources  where ServerUpdatedStatus=0";
//    }
//
//    public String getUprootmentRefresh() {
//        return "select * from Uprootment  where ServerUpdatedStatus=0";
//    }
//
//    public String getWeedRefresh() {
//        return "select * from Weed  where ServerUpdatedStatus=0";
//    }
//
//    public String getDigitalContractRefresh() {
//        return "select * from DigitalContract  where ServerUpdatedStatus=0";
//    }
//
//    public String getIdentityProofFileRepositoryXrefRefresh() {
//        return "select * from IdentityProofFileRepositoryXref  where ServerUpdatedStatus=0";
//    }
//
//    public String getPestChemicalXrefRefresh() {
//        return "select * from PestChemicalXref  where ServerUpdatedStatus=0";
//    }
//
//    public String getPlantationFileRepositoryXrefRefresh() {
//        return "select * from PlantationFileRepositoryXref  where ServerUpdatedStatus=0";
//    }
//
//    public String getCropMaintanenanceHistoryRefresh() {
//        return "select * from CropMaintenanceHistory  where ServerUpdatedStatus=0";
//    }
//
//    //****Finding codes in refresh*********************//
//    public String getFarmerCodes() {
//        return "select Code from Farmer where ServerUpdatedStatus = '0'";
//    }
//
//    public String getFarmerHistoryCodes() {
//        return "select DISTINCT(FarmerCode), PlotCode from FarmerHistory where ServerUpdatedStatus = '0'";
//    }
//
//    public String getFarmerIDProofCodes() {
//        return "select FarmerCode from IdentityProof where ServerUpdatedStatus = '0'";
//    }
//
//    public String getAddressCodes() {
//        return "select Code from Address where ServerUpdatedStatus = '0'";
//    }
//
//    public String getFarmerBankCodes() {
//        return "select FarmerCode from FarmerBank where ServerUpdatedStatus = '0'";
//    }
//
//    public String getPlotCodes() {
//        return "select Code from Plot where ServerUpdatedStatus = '0'";
//    }
//
//    public String getWaterResourceCodes() {
//        return "select PlotCode from WaterResource where ServerUpdatedStatus = '0'";
//    }
//
//    public String getSoilResourceCodes() {
//        return "select PlotCode from SoilResource where ServerUpdatedStatus = '0'";
//    }
//
//    public String getPlotIrrigationCodes() {
//        return "select PlotCode from PlotIrrigationTypeXref where ServerUpdatedStatus = '0'";
//    }
//
//    public String getFollowUpCodes() {
//        return "select PlotCode from FollowUp where ServerUpdatedStatus = '0'";
//    }
//
//    public String getReferralCodes() {
//        return "select FarmerName from Referrals where ServerUpdatedStatus = '0'";
//    }
//
//    public String getMarketSurveyCodes() {
//        return "select VillageId  from MarketSurvey where ServerUpdatedStatus = '0'";
//    }
//
//    public String getPlotCodeFromFarmerCode(String farmerCode) {
//        return "select Code from Plot where FarmerCode = '" + farmerCode + "'";
//    }
//
//    public String getMarketSurveyFromFarmerCode(String farmerCode) {
//        return "select * from MarketSurvey where FarmerCode = '" + farmerCode + "'";
//    }
//
    public String getSelectedFileRepositoryQuery(String farmerCode, int moduleTypeId) {
        return "select * from FileRepository where PlotCode = '" + farmerCode + "'" + " and moduleTypeId = '" + moduleTypeId + "'";
    }
//
//    public String getSelectedFileRepositoryCheckQuery(String farmerCode, int moduleTypeId) {
//        return "select * from FileRepository where FarmerCode = '" + farmerCode + "'" + " and moduleTypeId = '" + moduleTypeId + "'" + "and filelocation != 'null'";
//    }
//
//    public String getBranchDetails(String branchTypeId) {
//        return "select Id, BranchName from Bank where BankId = '" + branchTypeId + "'";
//    }
//
//    public String getBranchBindDetails(String branchTypeId) {
//        return "select Id, BranchName from Bank where Id = '" + branchTypeId + "'";
//    }
//
    public String getIfscCode(String branchId) {
        return "select IFSCCode from Bank where Id = '" + branchId + "'";
    }
//
//    public String getVillageCode(String villageId) {
//        return "select name from village where Id = '" + villageId + "'";
//    }
//
//    public String getLookUpData(String LookupTypeId) {
//        return "SELECT Id, Name FROM LookUp where LookupTypeId ='" + LookupTypeId + "'";
//    }
//
//    public String getMarketSurveyId(String farmerCode) {
//        return "select Code from MarketSurvey where FarmerCode ='" + farmerCode + "'";
//    }
//
//    public String getWaterResourceBinding(String plotCode) {
//        return "select * from WaterResource where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getFarmerBankData(String farmerCode) {
//        return "select * from FarmerBank where FarmerCode = '" + farmerCode + "'";
//    }
//
//    public String getFarmerIdentityProof(String farmerCode) {
//        return "select * from IdentityProof where FarmerCode = '" + farmerCode + "'";
//    }
//
//    public String getSoilResourceBinding(String plotCode) {
//        return "select * from SoilResource where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getPlantatiobData(String plotCode) {
//        return "select * from Plantation where PlotCode = '" + plotCode + "'";
//    }
//    public String getAllPlantatiobData(String plotCode) {
//        return "select CropVarietyId from Plantation where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getFertilizerData(String plotCode) {
//        return "select * from Fertilizer where PlotCode = '" + plotCode + "'";
//    }
//
//
//    public String getDiseaseData(String plotCode) {
//        return "select * from Disease where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getPlotIrrigationTypeXrefBinding(String plotCode) {
//        return "select * from PlotIrrigationTypeXref where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getHarvestBinding(String plotCode) {
//        return "select * from Harvest where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getFollowUpBinding(String plotCode) {
//        return "select * from FollowUp where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getBankId(String Id) {
//        return "select BankId from Bank where Id = '" + Id + "'";
//    }
//
//    public String getBranchName(String banktypeid) {
//        return "select BranchName from Bank where Id = '" + banktypeid + "'";
//    }
//
//    public String getTypecdDmtIdBank(String banktypeid) {
//        return "select BankId from Bank where Id = '" + banktypeid + "'";
//    }
//
//    public String getTypecdDesc(String typeid) {
//        return "select Desc from TypeCdDmt where TypeCdId = '" + typeid + "'";
//    }
//
//    public String getTypecdDesc(int typeid) {
//        return "select Desc from TypeCdDmt where TypeCdId = '" + typeid + "'";
//    }
//
//
//    public String isPlotExisted(String PlotCode) {
//        return "select * from FarmerHistory where PlotCode = '" + PlotCode + "'";
//    }
//
    public String getPlotStatuesId(String type) {
        return "select TypeCdId from TypeCdDmt where Desc = '" + type + "'";
    }

//        public String getUserVillages(String villageIds, final String mandalId) {
//        return "select Id, Code, Name from Village where Id IN  (" + villageIds + ") and MandalId IN (Select Id from Mandal where Id = '" + mandalId + "'" + ")";
//    }

    public String getUserVillages(String villageIds) {
        return "select Id, Code, Name from Village where Id IN  (" + villageIds + ")";
    }
//
//    public String getUserMandals(String mandalIds, final String DistrictId) {
//        return "select Id, Code, Name from Mandal where Id IN  (" + mandalIds + ") and DistrictId IN (Select Id from District where Id = '" + DistrictId + "'" + ")";
//    }
//
//    public String getUserDistricts(String districtIds, final String stateId) {
//        return "select Id, Code, Name from District where Id IN  (" + districtIds + ") and StateId IN (Select Id from State where Id = '" + stateId + "'" + ")";
//    }
//
//    public String getUserStates(String stateIds) {
//        return "select Id, Code, Name from State where Id IN  (" + stateIds + ")";
//    }
//
//    public String activityRightQuery(final int RoleId) {
//        return "select Name  from RoleActivityRightXref rarx\n" +
//                "inner join ActivityRight ar on ar.Id = rarx.ActivityRightId where RoleId = '" + RoleId + "' order by rarx.ActivityRightId";
//    }
//
    public String checkRecordStatusInTable(String tableName, String columnName, String columnValue) {
        String checkrecord = "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + "= '" + columnValue + "'" + " LIMIT 1)";
        android.util.Log.d("@@ Mahesh", checkrecord);
        return  checkrecord;
    }

    public String checkRecordStatusInTable(String tableName, String columnName, String columnName2, String columnValue, int columnValue2) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + " = '" + columnValue + "'" + " and " + columnName2 + " = '" + columnValue2 + "'" + " LIMIT 1)";
    }

    public String checkRecordStatusInTable(String tableName, String columnName, String columnName2, String columnName3, String columnValue, String columnValue2, int columnValue3) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + " = '" + columnValue + "'" + " and " + columnName2 + " = '" + columnValue2 + "'" + " LIMIT 1)";
    }

    public String checkRecordStatusInTable(String tableName, String columnName, String columnName2, String columnName3, String columnValue, String columnValue2, String columnValue3) {
        return "SELECT EXISTS(SELECT 1 FROM " + tableName + " where " + columnName + " = '" + columnValue + "'" + " and " + columnName2 + " = '" + columnValue2 + "'" + " and " + columnName3 + " = '" + columnValue3 + "'" + " LIMIT 1)";
    }

    //
//    public String getPlotDetails(final String farmercode, final int plotStatus) {
//        return
//                "select p.Code, p.TotalPlotArea, v.Name as VillageName, m.Name as MandalName, t.Desc, f.PotentialScore , (select GROUP_CONCAT(lkp.Name) from PlotCurrentCrop pcc \n" +
//                        "inner join LookUp lkp on pcc.CropId =lkp.Id  where PlotCode = p.Code and lkp.LookUpTypeId = '22')  as Crops, fh.UpdatedDate from Plot p\n" +
//                        "inner join Address addr on p.AddressCode = addr.Code\n" +
//                        "inner join Village v on addr.VillageId = v.Id\n" +
//                        "inner join Mandal m on addr.MandalId = m.Id\n" +
//                        "inner join TypeCdDmt t on t.TypecdId = p.CropIncomeTypeId\n" +
//                        "inner join FarmerHistory fh on fh.PlotCode = p.Code\n" +
//                        "inner join FollowUp f on f.PlotCode = p.Code\n" +
//                        "where p.FarmerCode='" + farmercode + "'" + " and fh.StatusTypeId = " + plotStatus + " and fh.IsActive = '1' group by p.Code";
//    }
//
//    public String getFileRepositoryData(final String farmerCode) {
//        return "select FileLocation from FileRepository where FarmerCode = '" + farmerCode + "' and ModuleTypeId = 193";
//    }
//
//    public String getMaxPestCode(final String plotCode) {
//        return "SELECT Code FROM Pest where Code like '%" + plotCode + "%' ORDER BY ID DESC LIMIT 1";
//    }
//
//    public String getMaxCropMaintenanceHistoryCode(final String plotCode, final String userId) {
//        return "SELECT Code FROM CropMaintenanceHistory where Code like '%" + plotCode + "%' and CreatedByUserId = '" + userId + "' ORDER BY ID DESC LIMIT 1";
//    }
//
//    public String getMaxComplaintsHistoryCode(final String plotCode, final String userId) {
//        return "SELECT Code FROM Complaints where Code like '%" + plotCode + "%' and CreatedByUserId = '" + userId + "' ORDER BY ID DESC LIMIT 1";
//    }
//
//    public String getDigitalContract() {
//        return "select * from DigitalContract where IsActive = 'true'";
//    }
//
//    public String getInterCropPlantationXref(String plotCode) {
//        return "select * from InterCropPlantationXref  where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getYieldQuery(final String fromDate, final String todate) {
//        return "select sum(cpx.NetWeightPerPlot) as totalYield, sum(cpx.YieldPerHectar) as totalYieldPerHecter from CollectionPlotXref cpx\n" +
//                "inner join Collection c on c.Code = cpx.CollectionCode\n" +
//                "where  date(c.CreatedDate) BETWEEN date('" + fromDate + "')" +
//                " AND date('" + todate + "') and cpx.PlotCode  = '" + CommonConstants.PLOT_CODE + "'";
//    }
//
//    public String querySumOfSaplings(final String plotCode) {
//        return "select TreesCount from Plantation where PlotCode = '" + plotCode + "' ORDER BY CreatedDate DESC LIMIT 1";
//    }
//
//    public String queryGetCountOfPreviousTrees(final String plotCode) {
//        return "select PlamsCount from Uprootment  where PlotCode = '" + plotCode + "' order by CreatedDate  desc";
//    }
//
//    public String queryGeoTagCheck(final String plotCode) {
//        return "select * from GeoBoundaries where PlotCode = '" + plotCode + "'" + " and GeoCategoryTypeId = '207'";
//    }
//
//    public String queryWaterResourceCheck(final String plotCode) {
//        return "select * from WaterResource where PlotCode = '" + plotCode + "'";
//    }
//
//    public String querySoilResourceCheck(final String plotCode) {
//        return "select * from SoilResource where PlotCode = '" + plotCode + "'";
//    }
//
//    public String queryActivityLog() {
//        return "select * from ActivityLog where ServerUpdatedStatus = '0'";
//    }
//
    public String getFilterBasedProspectiveFarmers(final int statusTypeId, String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode " + "and fileRep.ModuleTypeId = 193" + "\n" +
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                "  and fh.StatusTypeId = '" + statusTypeId + "'" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' group by f.Code limit " + limit + " offset " + offset + ";";
    }
    public String getFilterBasedFarmers(final int statusTypeId, String seachKey, int offset, int limit) {
        return "SELECT  DISTINCT F.Code, F.FirstName, F.MiddleName, F.LastName, F.GuardianName,S.Name AS StateName,\n"+
                "F.ContactNumber, F.ContactNumber, V.Name, FR.FileLocation, FR.FileName, FR.FileExtension,\n"+
                "NSD.NoOfSaplingsDispatched AS NoOfSaplingsDispatched, AD.NoOfSaplingsAdvancePaidFor FROM  AdvancedDetails AD \n"+
                "INNER JOIN (SELECT SUM(NSD.NoOfSaplingsDispatched) AS NoOfSaplingsDispatched, NSD.PlotCode\n"+
                "FROM NurserySaplingDetails  NSD GROUP BY NSD.PlotCode) NSD ON NSD.PlotCode = AD.PlotCode\n"+
                "INNER JOIN Plot P ON P.Code=AD.PlotCode\n"+
                "INNER JOIN Farmer F ON F.Code=P.FarmerCode\n"+
                "INNER JOIN FarmerHistory FH ON FH.PlotCode=P.Code\n"+
                " and FH.StatusTypeId = '" + statusTypeId + "'" + "\n" +
                "and FH.IsActive = '1'" + "\n" +
                "LEFT JOIN Village V ON F.VillageId = V.Id\n"+
                "LEFT JOIN State S ON F.StateId = S.Id\n"+
                "LEFT JOIN FileRepository FR ON F.Code = FR.FarmerCode and FR.ModuleTypeId = 193\n"+
                "where F.FirstName like'%" + seachKey + "%' or F.MiddleName like '%" + seachKey + "%' or F.LastName like '%" + seachKey + "%'  or F.Code like '%" + seachKey + "%' \n"+
                " or F.ContactNumber like '%" + seachKey + "%' or F.GuardianName like '%" + seachKey + "%'  group by F.Code\n"+
                " HAVING NSD.NoOfSaplingsDispatched = AD.NoOfSaplingsAdvancePaidFor limit " + limit + " offset " + offset + "; " ;

    }
//
    public String getFilterBasedFarmersCrop(String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode" + " and fileRep.ModuleTypeId = 193" + "\n" +
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                " and fh.StatusTypeId in ('88','89')" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' group by f.Code limit " + limit + " offset " + offset + ";";
    }
    public String getFilterBasedFarmersCropRetake(String seachKey, int offset, int limit) {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode" + " and fileRep.ModuleTypeId = 193" + "\n" +
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                " and fh.StatusTypeId in ('258')" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' group by f.Code limit " + limit + " offset " + offset + ";";
    }

    public String getFilterBasedFarmersFollowUp(String seachKey, int offset, int limit)  {
        return "select f.Code, f.FirstName, f.MiddleName, f.LastName, f.GuardianName,\n" +
                "s.Name as StateName,\n" +
                "f.ContactNumber, f.ContactNumber, v.Name, fileRep.FileLocation, fileRep.FileName, fileRep.FileExtension \n" +
                "from Farmer f \n" +
                "left join Village v on f.VillageId = v.Id\n" +
                "left join State s on f.StateId = s.Id\n" +
                "left join FileRepository fileRep on f.Code = fileRep.FarmerCode\n" +
                "and fileRep.ModuleTypeId = 193\n" +
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code\n" +
                "and fh.StatusTypeId in ('81')" + "\n" +
                "and fh.IsActive = '1'" + "\n" +
                "where f.FirstName like '%" + seachKey + "%' or f.MiddleName like '%" + seachKey + "%' or f.LastName like '%" + seachKey + "%' or f.Code like '%" + seachKey + "%' \n" +
                "or f.ContactNumber like '%" + seachKey + "%' or f.GuardianName like '%" + seachKey + "%' group by f.Code limit " + limit + " offset " + offset + ";";
    }
//
//    public String queryCropLastVisitDate() {
//        return "SELECT CreatedDate from Uprootment where PlotCode = '" + CommonConstants.PLOT_CODE + "'" + " order by Id desc LIMIT 1";
//    }
//
    public String queryFarmersCount() {
        return "select count(distinct(f.code)) from Farmer f\n" +
                "inner join FarmerHistory fh on fh.FarmerCode = f.Code \n" +
                "and fh.StatusTypeId in ('88', '89')";
    }
//
//    public String queryVerifyGeoTag() {
//        return "select Latitude, Longitude from GeoBoundaries where PlotCode = '" + CommonConstants.PLOT_CODE + "'" + "  and GeoCategoryTypeId = '207'";
//    }
//
//    public String queryVerifyFalogDistance() {
//
////        SELECT * FROM LocationTracker ORDER BY Id  DESC LIMIT 1;
//        return "select Latitude, Longitude from LocationTracker ORDER BY Id DESC LIMIT 1";
//    }
//
//    public String onlyValueFromDb(String tableName, String columnName, String whereCondition) {
//        return "SELECT " + columnName + " from " + tableName + " where " + whereCondition;
//    }
//
//    public String queryLandLordBankData(final String plotCode) {
//        return "select * from LandlordBank where PlotCode = '" + plotCode + "'";
//    }
//
//    public String queryPlotLandlordData(final String plotCode) {
//        return "select * from PlotLandlord where PlotCode = '" + plotCode + "'";
//    }
//
//    public String getLatestCropmaintenanceHistoryRecord() {
//        return "select Code, DATETIME(CreatedDate) date from CropmaintenanceHistory order by date DESC limit 1";
//    }
//
//    public String getLatestCropMaintanaceHistoryCode(String plotcode) {
//        return "select Code, max(CreatedDate) date from CropmaintenanceHistory where plotcode='" + plotcode + "'";
//    }
//
//    public String getCropMaintenanceHistoryData(String historyCode, String tableName) {
//        return "select * from " + tableName + " where CropMaintenanceCode =  '" + historyCode + "' and PlotCode = '" + CommonConstants.PLOT_CODE + "'";
//    }
//    public String getRecommndCropMaintenanceHistoryData(String historyCode, String tableName) {
//        return "select * from " + tableName + " where CropMaintenanceCode =  '" + historyCode + "' and PlotCode = '" + CommonConstants.PLOT_CODE + "'";
//    }
//
//    public String getPestXrefData(String pestCode) {
//        return "select * from PestChemicalXref where PestCode = '" + pestCode + "'";
//    }
//
//    public String getMaxComplaintsCode(final String plotCode) {
//        return "SELECT Code FROM Complaints where Code like '%" + plotCode + "%' ORDER BY ID DESC LIMIT 1";
//    }
//
//    //  ----------to send server Complaint details  -----
//    public String getCompolaintRepository() {
//        return "select * from ComplaintRepository where ServerUpdatedStatus=0";
//    }
//
//    public String getComplaintData() {
//        return "select * from Complaints where ServerUpdatedStatus=0";
//    }
//
//    public String getComplaintDataByCode(String complaintCode) {
//        return "select * from Complaints where Code = '" + complaintCode + "'";
//    }
//
//    public String getComplaintStatusHistoryByCode(String complaintCode) {
//        return "select * from ComplaintStatusHistory where ComplaintCode = '" + complaintCode + "'" + " and IsActive = '1'";
//    }
//
//    public String getComplaintStatusHistoryAll(String complaintCode) {
//        return "select * from ComplaintStatusHistory where ComplaintCode = '" + complaintCode + "'";
//    }
//
//    public String getComplaintXrefByCode(String complaintCode) {
//        return "select * from ComplaintTypeXref where ComplaintCode = '" + complaintCode + "'";
//    }
//
//    public String getComplaintTypeXref() {
//        return "select * from ComplaintTypeXref where ServerUpdatedStatus=0";
//    }
//
//    public String getComplaintStatusHistory() {
//        return "select * from ComplaintStatusHistory  where ServerUpdatedStatus=0";
//    }
//
//    public String getComplaintRepository() {
//        return "select * from ComplaintRepository  where ServerUpdatedStatus=0";
//    }
//
//    public String getComplaintRepositoryByCode(String complaintCode) {
//        return "select * from ComplaintRepository where ComplaintCode = '" + complaintCode + "'";
//    }
//
//    public String getComplaintRepositoryByCodeForAudio(String complaintCode) {
//        return "select * from ComplaintRepository where ComplaintCode = '" + complaintCode + "'" + " and FileExtension ='.mp3'";
//    }
//
    public String UpgradeCount() {
        //number of Users
        return "select count(*) from UserInfo";
    }

    //    public String getComplaintToDisplay(boolean isPlot, String plotcode) {
//        String wherecondition;
//        if (isPlot) {
//            wherecondition = "and cp.plotcode = " + "'" + plotcode + "'";
//        } else {
//            wherecondition = "";
//        }
//        return "select cp.Code, cx.ComplaintTypeId, csh.AssigntoUserId, csh.StatusTypeId,cp.CriticalityByTypeId, cp.CreatedDate, cp.PlotCode,\n" +
//                "(select FirstName from Farmer f where f.Code = (select FarmerCode from Plot p where p.Code = cp.PlotCode)) as FarmerFirstName,\n" +
//                "(select LastName from Farmer f where f.Code = (select FarmerCode from Plot p where p.Code = cp.PlotCode)) as FarmerLastName,\n" +
//                "(select Name from Village where Id = (select VillageId from Farmer f where f.Code = (select FarmerCode from Plot p where p.Code = cp.PlotCode))) as FarmerVillageName,\n" +
//                "(select Desc from TypeCdDmt where TypeCdId = cx.ComplaintTypeId) ComplaintType,\n" +
//                "(select Desc from TypeCdDmt where TypeCdId = csh.StatusTypeId) ComplaintStatusType, \n" +
//                "(select UserName from UserInfo ui where  ui.Id = cp.CreatedByUserId) as CreatedName\n" +
//                "from Complaints cp\n" +
//                "inner join\n" +
//                "ComplaintTypeXref cx on cp.code = cx.ComplaintCode\n" +
//                "inner join\n" +
//                "ComplaintStatusHistory csh on csh.ComplaintCode = cp.Code where csh.IsActive = '1' " + wherecondition + " group by csh.ComplaintCode order by cp.CreatedDate DESC";
//    }
//
//    public String getKRAsDisplayQuery(int userId) {
//        if (userId == 0) {
//            return "select ut.UserKRAId, ut.KRACode, ut.KRAName, ut.UOM, ut.AnnualTarget, ut.AchievedTarget, ut.UserId, umt.MonthNumber, umt.MonthlyTarget, umt.AchievedTarget from UserTarget ut\n" +
//                    "left join UserMonthlyTarget umt on ut.KRACode = umt.KRACode";
//        }
//
//        return "select ut.UserKRAId, ut.KRACode, ut.KRAName, ut.UOM, ut.AnnualTarget, ut.AchievedTarget, ut.UserId, umt.MonthNumber, umt.MonthlyTarget, umt.AchievedTarget from UserTarget ut\n" +
//                "left join UserMonthlyTarget umt on ut.KRACode = umt.KRACode where ut.UserId = '" + userId + "'";
//    }

    public String getUnreadNotificationsCountQuery(int userId) {
        return "SELECT COUNT(DISTINCT Id) FROM Notification where UserId='"+userId+"' AND IsRead= 'false'";
    }
    public String getbankdetailsCount(String usercode) {
        return "select count(*) from BankDetails where UserCode = '"+usercode+"'";
    }

    public String getplotdetailsCount(String plotCode) {
        return "select count(*) from Plot where Code = '"+plotCode+"'";
    }
    public String getplotstatusdetailsCount(String Id) {
        return "select count(*) from PlotStatusHistory where Id = '"+Id+"'";
    }

    public String getpowerDetailsCount(String Id) {
        return "select count(*) from PowerDetails where Id = '"+Id+"'";
    }

    public String getsoilDetailsCount(String Id) {
        return "select count(*) from SoilDetails where Id = '"+Id+"'";
    }

    public String getirrigationDetailsCount(String Id) {
        return "select count(*) from IrrigationDetails where Id = '"+Id+"'";
    }

    public String getnotificationCount(String Id) {
        return "select count(*) from Notification where Id = '"+Id+"'";
    }
    public String getBuyerDetailsCount(String Id) {
        return "select count(*) from BuyerDetails where UserCode = '"+Id+"'";
    }
    public String getVendorDetailsCount(String Id) {
        return "select count(*) from VendorDetails where UserCode = '"+Id+"'";
    }

    public String getComplaintsCount(String Id) {
        return "select count(*) from Complaints where Code = '"+Id+"'";
    }

    public String getComplaintRepositoryCount(String Id) {
        return "select count(*) from ComplaintRepository where Id = '"+Id+"'";
    }

    public String getComplaintStatusHistoryCount(String Id) {
        return "select count(*) from ComplaintStatusHistory where Id = '"+Id+"'";
    }

    public String getComplaintTypeXrefCount(String Id) {
        return "select count(*) from ComplaintTypeXref where Id = '"+Id+"'";
    }

    public String getInsuranceDetailsCount(String Id) {
        return "select count(*) from InsuranceDetails where Id = '"+Id+"'";
    }

    public String getPlotCropCycleCount(String Id) {
        return "select count(*) from PlotCropCycle where Id = '"+Id+"'";
    }

    public String getActivityLogCount(String Id) {
        return "select count(*) from ActivityLog where Id = '"+Id+"'";
    }

    public String getSoilTestDetailsCount(String Id) {
        return "select count(*) from SoilTestDetails where Id = '"+Id+"'";
    }
    public String getUserRoleXrefCount(String UserId, String RoleId) {
        return "select * from UserRoleXref where UserId = '" + UserId + "'" + " and RoleId = '" + RoleId + "'";
    }

    public String getbankdetailsHistoryCount(String Id) {
        return "select count(*) from BankDetailsHistory where Id = '"+Id+"'";
    }

    public String getIdProofCount(String UserCode, String IdProofTypeId) {
        return "select count(*) from IdentityProofs where UserCode = '" + UserCode + "' and IdProofTypeId = '" + IdProofTypeId + "'";
    }

    public String getuserInfoCount(String Code) {
        return "select count(*) from UserInfo where Code = '"+Code+"'";
    }

    public String getAlertsDetailsQuery() {
        return "select * from Alerts";
    }
    public String getGeoboundries(String Id) {
        return "select count(*) from GeoBoundaries where Id = '"+Id+"'";
    }
    public String getActivitylog(String Id) {
        return "select count(*) from ActivityLog where Id = '"+Id+"'";
    }

    public String getAlertsDetailsQueryToRender() {
        return "select a.*, t.Desc as alertType from Alerts a\n" +
                "inner join TypeCdDmt t on a.AlertTypeId = t.TypeCdId";
    }

    //
    public String getAlertsDetailsQueryToSendCloud() {
        return "select * from Alerts where ServerUpdatedStatus = 0";
    }

    public String getComplaintsbyplotcode(String plotcode) {
        return "select * from Complaints WHERE PlotCode ='"+plotcode+"' Group By Id";

    }

    public String getComplaintsbyplotCcode(String plotcode) {
        return "select * from Complaints WHERE PlotCode ='"+plotcode+"' Group By Code";

    }

    public String getComplaintsbyplotcodebasedonServerupdatedStatus(String plotcode) {
        return "select * from Complaints WHERE PlotCode ='"+plotcode+"'  AND ServerUpdatedStatus != 0 Group By Id";

    }
}
//    public String retakeGeoBoundry(final String PlotCode) {
//        return "select IsRetakeGeoTagRequired from Plot where Code ='" + PlotCode + "'";
//    }
//
//    public static String deleteduplicate() {
//        return "delete from FileRepository where rowid not in ( select  min(rowid) from FileRepository group by PlotCode , CreatedDate )";
//    }
//
//    public static String plotAgeAndPlotLocation(final String PlotCode,final String currentDate) {
//
//        return   "Select Cast (( JulianDay('" + currentDate + "') - JulianDay(p.DateofPlanting)) As Integer)/365 as plotAge,addr.StateId \n " +
//                "FROM Plot p \n" +
//                " inner join Address addr on p.AddressCode = addr.Code \n" +
//                "inner join State s on addr.StateId = s.Id \n" +
//                "WHERE p.Code = '" + PlotCode + "'";
//    }
//
//    public static String CalculateExpectedYield(final int FincYear,final String PlotAge,final String stateId, final String curremtMonth) {
//
//        return   "SELECT Round(sum(MonthlyPercentage/100 * YieldPerHectar),2) as ExpectedYield \n" +
//                " FROM Benchmark WHERE Year = '" + FincYear + "' AND Age = '" + PlotAge + "' AND StateId = '" + stateId + "' \n" +
//                "and  MonthSequenceNumber <= (select MonthSequenceNumber from  Benchmark \n" +
//                " WHERE Year = '" + FincYear + "' AND Age = '" + PlotAge + "' AND StateId = '" + stateId + "' and MonthName = '" + curremtMonth + "' )";
//    }
//
//    public String ExpectedYield(final String plotCode,String YPHValue) {
//        return "select Round(TotalPalmArea * '" + YPHValue + "',2) as areaunderpalm FROM Plot WHERE  Code = '" + plotCode + "'";
//    }
//
////    "SELECT Round(TotalPalmArea * 5423.25,2) Round(sum(MonthlyPercentage/100 * YieldPerHectar),2) as ExpectedYield FROM Benchmark WHERE Year = 2017 AND Age = 6 AND StateId = 2 and  MonthSequenceNumber <= (select MonthSequenceNumber from  Benchmark WHERE Year = 2017 AND Age = 6 AND StateId = 2 and MonthName =  )"
//
//
//
//
//
//}
//
//
//
//
//
