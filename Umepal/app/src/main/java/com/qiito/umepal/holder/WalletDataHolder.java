package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shiya on 6/6/16.
 */
public class WalletDataHolder implements Serializable {

    private String total_credit_amount;
    private List<RebateListHolder> rebate;
    private List<RebateListHolder> commission;
    private String ledger_amount;

    public String getTotal_credit_amount() {
        return total_credit_amount;
    }

    public void setTotal_credit_amount(String total_credit_amount) {
        this.total_credit_amount = total_credit_amount;
    }

    public List<RebateListHolder> getRebate() {
        return rebate;
    }

    public void setRebate(List<RebateListHolder> rebate) {
        this.rebate = rebate;
    }

    public List<RebateListHolder> getCommission() {
        return commission;
    }

    public void setCommission(List<RebateListHolder> commission) {
        this.commission = commission;
    }

    public String getLedger_amount() {
        return ledger_amount;
    }

    public void setLedger_amount(String ledger_amount) {
        this.ledger_amount = ledger_amount;
    }
}
