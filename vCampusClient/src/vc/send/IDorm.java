package vc.send;

import java.awt.List;

import vc.common.DormChargeInf;
import vc.common.DormLivingInf;
import vc.common.DormRepairInf;
import vc.common.DormUtilityBillsInf;
import vc.common.DormVisitInf;

public abstract interface IDorm
{
  public abstract java.util.List<DormLivingInf> QueryDormLivingInf(DormLivingInf paramDormLivingInf);
  public abstract boolean ModifyDormLivingInf(DormLivingInf paramDormLivingInf);
  public abstract java.util.List<DormRepairInf> QueryDormRepairInf(DormRepairInf paramDormRepairInf);
  public abstract java.util.List<DormChargeInf> QueryDormChargeInf(DormChargeInf paramDormChargeInf);
  public abstract java.util.List<DormVisitInf> QueryDormVisitInf(DormVisitInf paramDormVisitInf);
  public abstract boolean AddDormChargeInf(DormChargeInf paramDormChargeInf);
  public abstract java.util.List<DormUtilityBillsInf> QueryDormUtilityBillsInf(DormUtilityBillsInf paramDormUtilityBillsInf);
  public abstract boolean AddDormRepairInf(DormRepairInf paramDormRepairInf);
}