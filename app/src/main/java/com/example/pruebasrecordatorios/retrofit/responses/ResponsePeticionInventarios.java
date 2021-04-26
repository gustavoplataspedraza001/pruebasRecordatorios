package com.example.pruebasrecordatorios.retrofit.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePeticionInventarios {

    @SerializedName("ARTC_DESCRIPCION")
    @Expose
    private String aRTCDESCRIPCION;
    @SerializedName("ARTC_COSTO")
    @Expose
    private String aRTCCOSTO;
    @SerializedName("ARTC_PRECIOVENTA")
    @Expose
    private String aRTCPRECIOVENTA;
    @SerializedName("ARTC_PRECIOOFERTA")
    @Expose
    private String aRTCPRECIOOFERTA;
    @SerializedName("INVENTARIO_INICIAL")
    @Expose
    private String iNVENTARIOINICIAL;
    @SerializedName("CANTIDAD_COMPRA")
    @Expose
    private String cANTIDADCOMPRA;
    @SerializedName("INV_ETRANS")
    @Expose
    private String iNVETRANS;
    @SerializedName("INV_EXDEV")
    @Expose
    private String iNVEXDEV;
    @SerializedName("INV_ENTRADAS")
    @Expose
    private String iNVENTRADAS;
    @SerializedName("INV_TOTALENTRADAS")
    @Expose
    private Integer iNVTOTALENTRADAS;
    @SerializedName("INV_SALXVE")
    @Expose
    private String iNVSALXVE;
    @SerializedName("INV_STRANS")
    @Expose
    private String iNVSTRANS;
    @SerializedName("INV_DEVOLUCIONES")
    @Expose
    private String iNVDEVOLUCIONES;
    @SerializedName("INV_SALIDAS")
    @Expose
    private String iNVSALIDAS;
    @SerializedName("INV_SGRAL")
    @Expose
    private String iNVSGRAL;
    @SerializedName("INV_TOTALSALIDAS")
    @Expose
    private Integer iNVTOTALSALIDAS;
    @SerializedName("INV_TEORICO")
    @Expose
    private Integer iNVTEORICO;
    @SerializedName("EXISTE")
    @Expose
    private String eXISTE;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponsePeticionInventarios() {
    }

    /**
     *
     * @param aRTCCOSTO
     * @param iNVSALXVE
     * @param iNVSALIDAS
     * @param aRTCDESCRIPCION
     * @param iNVEXDEV
     * @param iNVENTARIOINICIAL
     * @param iNVSTRANS
     * @param iNVSGRAL
     * @param aRTCPRECIOOFERTA
     * @param iNVENTRADAS
     * @param iNVTEORICO
     * @param cANTIDADCOMPRA
     * @param iNVDEVOLUCIONES
     * @param aRTCPRECIOVENTA
     * @param iNVETRANS
     * @param eXISTE
     * @param iNVTOTALENTRADAS
     * @param iNVTOTALSALIDAS
     */
    public ResponsePeticionInventarios(String aRTCDESCRIPCION, String aRTCCOSTO, String aRTCPRECIOVENTA, String aRTCPRECIOOFERTA, String iNVENTARIOINICIAL, String cANTIDADCOMPRA, String iNVETRANS, String iNVEXDEV, String iNVENTRADAS, Integer iNVTOTALENTRADAS, String iNVSALXVE, String iNVSTRANS, String iNVDEVOLUCIONES, String iNVSALIDAS, String iNVSGRAL, Integer iNVTOTALSALIDAS, Integer iNVTEORICO, String eXISTE) {
        super();
        this.aRTCDESCRIPCION = aRTCDESCRIPCION;
        this.aRTCCOSTO = aRTCCOSTO;
        this.aRTCPRECIOVENTA = aRTCPRECIOVENTA;
        this.aRTCPRECIOOFERTA = aRTCPRECIOOFERTA;
        this.iNVENTARIOINICIAL = iNVENTARIOINICIAL;
        this.cANTIDADCOMPRA = cANTIDADCOMPRA;
        this.iNVETRANS = iNVETRANS;
        this.iNVEXDEV = iNVEXDEV;
        this.iNVENTRADAS = iNVENTRADAS;
        this.iNVTOTALENTRADAS = iNVTOTALENTRADAS;
        this.iNVSALXVE = iNVSALXVE;
        this.iNVSTRANS = iNVSTRANS;
        this.iNVDEVOLUCIONES = iNVDEVOLUCIONES;
        this.iNVSALIDAS = iNVSALIDAS;
        this.iNVSGRAL = iNVSGRAL;
        this.iNVTOTALSALIDAS = iNVTOTALSALIDAS;
        this.iNVTEORICO = iNVTEORICO;
        this.eXISTE = eXISTE;
    }

    public String getARTCDESCRIPCION() {
        return aRTCDESCRIPCION;
    }

    public void setARTCDESCRIPCION(String aRTCDESCRIPCION) {
        this.aRTCDESCRIPCION = aRTCDESCRIPCION;
    }

    public ResponsePeticionInventarios withARTCDESCRIPCION(String aRTCDESCRIPCION) {
        this.aRTCDESCRIPCION = aRTCDESCRIPCION;
        return this;
    }

    public String getARTCCOSTO() {
        return aRTCCOSTO;
    }

    public void setARTCCOSTO(String aRTCCOSTO) {
        this.aRTCCOSTO = aRTCCOSTO;
    }

    public ResponsePeticionInventarios withARTCCOSTO(String aRTCCOSTO) {
        this.aRTCCOSTO = aRTCCOSTO;
        return this;
    }

    public String getARTCPRECIOVENTA() {
        return aRTCPRECIOVENTA;
    }

    public void setARTCPRECIOVENTA(String aRTCPRECIOVENTA) {
        this.aRTCPRECIOVENTA = aRTCPRECIOVENTA;
    }

    public ResponsePeticionInventarios withARTCPRECIOVENTA(String aRTCPRECIOVENTA) {
        this.aRTCPRECIOVENTA = aRTCPRECIOVENTA;
        return this;
    }

    public String getARTCPRECIOOFERTA() {
        return aRTCPRECIOOFERTA;
    }

    public void setARTCPRECIOOFERTA(String aRTCPRECIOOFERTA) {
        this.aRTCPRECIOOFERTA = aRTCPRECIOOFERTA;
    }

    public ResponsePeticionInventarios withARTCPRECIOOFERTA(String aRTCPRECIOOFERTA) {
        this.aRTCPRECIOOFERTA = aRTCPRECIOOFERTA;
        return this;
    }

    public String getINVENTARIOINICIAL() {
        return iNVENTARIOINICIAL;
    }

    public void setINVENTARIOINICIAL(String iNVENTARIOINICIAL) {
        this.iNVENTARIOINICIAL = iNVENTARIOINICIAL;
    }

    public ResponsePeticionInventarios withINVENTARIOINICIAL(String iNVENTARIOINICIAL) {
        this.iNVENTARIOINICIAL = iNVENTARIOINICIAL;
        return this;
    }

    public String getCANTIDADCOMPRA() {
        return cANTIDADCOMPRA;
    }

    public void setCANTIDADCOMPRA(String cANTIDADCOMPRA) {
        this.cANTIDADCOMPRA = cANTIDADCOMPRA;
    }

    public ResponsePeticionInventarios withCANTIDADCOMPRA(String cANTIDADCOMPRA) {
        this.cANTIDADCOMPRA = cANTIDADCOMPRA;
        return this;
    }

    public String getINVETRANS() {
        return iNVETRANS;
    }

    public void setINVETRANS(String iNVETRANS) {
        this.iNVETRANS = iNVETRANS;
    }

    public ResponsePeticionInventarios withINVETRANS(String iNVETRANS) {
        this.iNVETRANS = iNVETRANS;
        return this;
    }

    public String getINVEXDEV() {
        return iNVEXDEV;
    }

    public void setINVEXDEV(String iNVEXDEV) {
        this.iNVEXDEV = iNVEXDEV;
    }

    public ResponsePeticionInventarios withINVEXDEV(String iNVEXDEV) {
        this.iNVEXDEV = iNVEXDEV;
        return this;
    }

    public String getINVENTRADAS() {
        return iNVENTRADAS;
    }

    public void setINVENTRADAS(String iNVENTRADAS) {
        this.iNVENTRADAS = iNVENTRADAS;
    }

    public ResponsePeticionInventarios withINVENTRADAS(String iNVENTRADAS) {
        this.iNVENTRADAS = iNVENTRADAS;
        return this;
    }

    public Integer getINVTOTALENTRADAS() {
        return iNVTOTALENTRADAS;
    }

    public void setINVTOTALENTRADAS(Integer iNVTOTALENTRADAS) {
        this.iNVTOTALENTRADAS = iNVTOTALENTRADAS;
    }

    public ResponsePeticionInventarios withINVTOTALENTRADAS(Integer iNVTOTALENTRADAS) {
        this.iNVTOTALENTRADAS = iNVTOTALENTRADAS;
        return this;
    }

    public String getINVSALXVE() {
        return iNVSALXVE;
    }

    public void setINVSALXVE(String iNVSALXVE) {
        this.iNVSALXVE = iNVSALXVE;
    }

    public ResponsePeticionInventarios withINVSALXVE(String iNVSALXVE) {
        this.iNVSALXVE = iNVSALXVE;
        return this;
    }

    public String getINVSTRANS() {
        return iNVSTRANS;
    }

    public void setINVSTRANS(String iNVSTRANS) {
        this.iNVSTRANS = iNVSTRANS;
    }

    public ResponsePeticionInventarios withINVSTRANS(String iNVSTRANS) {
        this.iNVSTRANS = iNVSTRANS;
        return this;
    }

    public String getINVDEVOLUCIONES() {
        return iNVDEVOLUCIONES;
    }

    public void setINVDEVOLUCIONES(String iNVDEVOLUCIONES) {
        this.iNVDEVOLUCIONES = iNVDEVOLUCIONES;
    }

    public ResponsePeticionInventarios withINVDEVOLUCIONES(String iNVDEVOLUCIONES) {
        this.iNVDEVOLUCIONES = iNVDEVOLUCIONES;
        return this;
    }

    public String getINVSALIDAS() {
        return iNVSALIDAS;
    }

    public void setINVSALIDAS(String iNVSALIDAS) {
        this.iNVSALIDAS = iNVSALIDAS;
    }

    public ResponsePeticionInventarios withINVSALIDAS(String iNVSALIDAS) {
        this.iNVSALIDAS = iNVSALIDAS;
        return this;
    }

    public String getINVSGRAL() {
        return iNVSGRAL;
    }

    public void setINVSGRAL(String iNVSGRAL) {
        this.iNVSGRAL = iNVSGRAL;
    }

    public ResponsePeticionInventarios withINVSGRAL(String iNVSGRAL) {
        this.iNVSGRAL = iNVSGRAL;
        return this;
    }

    public Integer getINVTOTALSALIDAS() {
        return iNVTOTALSALIDAS;
    }

    public void setINVTOTALSALIDAS(Integer iNVTOTALSALIDAS) {
        this.iNVTOTALSALIDAS = iNVTOTALSALIDAS;
    }

    public ResponsePeticionInventarios withINVTOTALSALIDAS(Integer iNVTOTALSALIDAS) {
        this.iNVTOTALSALIDAS = iNVTOTALSALIDAS;
        return this;
    }

    public Integer getINVTEORICO() {
        return iNVTEORICO;
    }

    public void setINVTEORICO(Integer iNVTEORICO) {
        this.iNVTEORICO = iNVTEORICO;
    }

    public ResponsePeticionInventarios withINVTEORICO(Integer iNVTEORICO) {
        this.iNVTEORICO = iNVTEORICO;
        return this;
    }

    public String getEXISTE() {
        return eXISTE;
    }

    public void setEXISTE(String eXISTE) {
        this.eXISTE = eXISTE;
    }

    public ResponsePeticionInventarios withEXISTE(String eXISTE) {
        this.eXISTE = eXISTE;
        return this;
    }

}