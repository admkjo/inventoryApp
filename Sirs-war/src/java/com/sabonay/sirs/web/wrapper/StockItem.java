/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.sirs.web.wrapper;

import com.sabonay.sirs.ejb.entities.Material;
import com.sabonay.sirs.web.common.ApplicationConstant;
import com.sabonay.sirs.web.common.utilities.NumberFormatter;

/**
 *
 * @author Emma
 */
public class StockItem {
    private Integer count;
    private String itemCode;
    private String description;
    private String unitOfMeasure=ApplicationConstant.FEET;
    private Integer quantity;
    private String materialType;
    private String fileName;
    private Double materialWidth = 0.0;
    private Double materialHeight = 0.0;
    private Double materialCost;
    private Double lineTotal;
    private Material material;
    private Double subTotal = 0.0;
    private String receiptDescription;
    private String receiptMaterialCost;

    
    public StockItem(){
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Double getLineTotal() {
        try {
            if (ApplicationConstant.MEASURED.equals(materialType)){
                lineTotal = ApplicationConstant.FEET.equals(unitOfMeasure)
                    ? (materialHeight * materialWidth * materialCost) : ((materialHeight * materialWidth) / 144) * materialCost;
            }else{
                lineTotal = materialCost;
            }
            return NumberFormatter.formatDoubleNum(lineTotal * quantity,1); 
        } catch (Exception e) {
        }
        return 0.0; 
    }

    public void setLineTotal(Double lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Double getMaterialCost() {
        return materialCost;  
    }

    public void setMaterialCost(Double materialCost) {
        this.materialCost = materialCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSubTotal() {
        
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getMaterialWidth() {
        return materialWidth;
    }

    public void setMaterialWidth(Double materialWidth) {
        this.materialWidth = materialWidth;
    }

    public Double getMaterialHeight() {
        return materialHeight;
    }

    public void setMaterialHeight(Double materialHeight) {
        this.materialHeight = materialHeight;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getReceiptDescription() {
        if (ApplicationConstant.MEASURED.equals(materialType)){
            receiptDescription = fileName + " " + materialWidth.toString() + " x " + materialHeight.toString() + " " + ("F".equals(unitOfMeasure) ? "Feet" : "Inch(es)");
        }else{
            receiptDescription = fileName;
        }
        
        return receiptDescription;
    }

    public void setReceiptDescription(String receiptDescription) {
        this.receiptDescription = receiptDescription;
    }

    public String getReceiptMaterialCost() {
        receiptMaterialCost = material.getMaterialName();
        return receiptMaterialCost;
    }

    public void setReceiptMaterialCost(String receiptMaterialCost) {
        this.receiptMaterialCost = receiptMaterialCost;
    }
}
