/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author rocke
 */

import conexion.Fachada;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.lang.String;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import ws.Response_cufe;
import ws.Response_docs;
import ws.Response_ws;
import ws.SERVICESFACTURATECH;
import ws.SERVICESFACTURATECHPortType;
import ws.SERVICESFACTURATECH_Impl;

public class generaXml {
   

    //////////////////////////////////////////////
    //////CONEXIÓN BASE DE DATOS
    Fachada fachada = new Fachada();
    String encabezadoConexion = "";
    String etiquetaFinDoc = "";

    String companyID = "";
    String accountID = "";
    String password = "";
    String correo = "";
    String tipoDocumento = "";
    String cufe = "";
    String zonaHoraria= "-05:00";
    
    String sSistemaOperativo = System.getProperty("os.name");


    //////////////////////////////////////////////
    /////ENCABEZADO DEL DOCUMENTO    
    String ENC_1 = "";//INVOIC-FACTURA;ND-NOTA DEBITO;NC-NOTA CREDITO
    String ENC_2 = "";//NIT FACTURADOR ELECTRONICO
    String ENC_3 = "";//NIT ADQUIRIENTE
    String ENC_4 = "";//VERSION ESQUEMA UBL (2.1)
    String ENC_5 = "";//VERSION FORMATO DOCUMENTO (DIAN 2.1)
    String ENC_6 = "";//NUMERO DOCUMENTO (PREFIJO+CONSECUTIVO)
    String ENC_7 = "";//FECHA EMISION FACTURA
    String ENC_8 = "";//HORA DE EMISION FACTURA + ZONA HORARIA (-5:)
    String ENC_9 = "";//01 FACTURA ELECTRONICA;02 FACTURA DE EXPORTACION;03 FACTURA DE CONTINGENCIA;04 CONTINGENCIA DIAN;91 NOTA CREDITO;92 NOTA DEBITO
    String ENC_10 = "";//MONEDA FACTURA
    String ENC_11 = "";//FECHA Y HORA INICIO DE PERIODO FACTURADO
    String ENC_12 = "";//FECHA Y HORA FINAL DE PERIODO FACTURADO
    String ENC_13 = "";//CENTRO DE COSTOS
    String ENC_14 = "";//DESCRIPCION DEL CENTRO DE COSTOS
    String ENC_15 = "";//NUMERO DE LINEAS DEL DOCUMENTO (IGUAL A LA CANTIDAD DE ITEMS)
    String ENC_16 = "";//FECHA VENCIMIENTO FACTURA
    String ENC_17 = "";//URL ARCHIVOS ADJUNTOS
    String ENC_18 = "";//URL PARA PAGO (SOLO FACTURA)
    String ENC_19 = "";//UNIDAD DE NEGOCIO
    String ENC_20 = "";//IDENTIFICADOR FACTURA (1 PARA FACTURA REAL - 2 PARA PRUEBAS)
    String ENC_21 = "";//TIPO DE OPERACION (10 ESTANDAR) VER TABLA 38
    String ENC_22 = "";//FECHA DE PAGO DE IMPUESTOS

    /////////////////////////////////////////////
    String encabezadoDocumento = "";
    String etiquetaInicialENC = "<ENC>";
    String etiquetaFinalENC = "</ENC>";
    String cif = "";
    String dv = "";
    String tipoDocEmpresa = "";
    String obligacionesEmpresa = "";
    String descEmpresa = "";
    String CIIU= "";
    String registroMercantil = "";
    String web = "";
    String direccion = "";
    String poblacion = "";
    String codPostal = "";
    String provincia = "";    
    String departamento = "";
    String idPais = "";
    String descPais = "";
    String codISOPais = "";
    String cifCliente = "";
    String dvCliente = "";
    String obligacionesADQ ="";
    String numeroFactura = "";
    String fechaEmisionFactura = "";
    String horaEmisionFactura = "";
    String moneda = "";
    String fechaVencimientoFactura = "";
    String medioPago = "";
    String formaPago = "";
    String prefijo = "";
    double importeBruto = 0.00;
    double baseImponible = 0.00;
    double valorIva = 0.00;
    double totalFactura = 0.00;
    double valorRetencion = 0.00;
    double valorImpuesto = 0.0;
    //////DATOS DEL EMISOR
    String emisor = "";
    String etiquetaInicialEMI = "<EMI>";
    String etiquetaFinalEMI = "</EMI>";
    String tipoPersona = "1";
    String tipoDocumentoEmisor = "";
    String EMI_1 = "";//Tipo de persona 1 Juridica - 2 Natural
    String EMI_2 = "";//Nit Emisor 
    String EMI_3 = "";//Tipo identificación fiscal Cedula 13 - NIT 31
    String EMI_4 = "";//Regimen del emisor 48 Responsable de iva - 49 No responsable de iva
    String EMI_5 = "";//Numero de identificacion interno
    String EMI_6 = "";//Nombre o Razón social del emisor
    String EMI_7 = "";//Nombre comercial
    String EMI_8 = "";//Campo Eliminado
    String EMI_9 = "";//Campo Eliminado
    String EMI_10 = "";//Texto libre para la dirección
    String EMI_11 = "";//Codigo departamento 76 Valle
    String EMI_12 = "";//Campo Eliminado
    String EMI_13 = "";//Nomre Ciudad debe coincidir con el listado DIAN sino notifica
    String EMI_14 = "";//Codigo Postal debe coincidir con el listado DIAN
    String EMI_15 = "";//Codigo Pais debe coincidir con el listado DIAN CO Colombia
    String EMI_16 = "";//Codigo de localizacion EAN
    String EMI_17 = "";//Campo Eliminado
    String EMI_18 = "";//Campo Eliminado
    String EMI_19 = "";//Nombre departamento
    String EMI_20 = "";//Campo Eliminado
    String EMI_21 = "";//Nombre del Pais
    String EMI_22 = "";//Digito de verificacion emisor
    String EMI_23 = "";//Codigo del municipio = codigo postal
    String EMI_24 = "";
    String EMI_25 = "";
    

    ////////////////////////////////////////////
    //////INFORMACION TRIBUTARIA RUT
    String infoTributariaEMI = "";
    String etiquetaInicialTAC = "<TAC>";
    String etiquetaFinalTAC = "</TAC>";
    String TAC_1 = "";
    
    
    /////////////////////////////////////////////
    //////INFORMACIÓN DIRECCIÓN FISCAL DEL EMISOR
    String etiquetaInicialDFE = "<DFE>";    
    String DFE_1 = "";
    String DFE_2 = "";
    String DFE_3 = "";
    String DFE_4 = "";
    String DFE_5 = "";
    String DFE_6 = "";
    String DFE_7 = "";
    String DFE_8 = "";   
    String etiquetaFinalDFE = "</DFE>";
    

    ///////////////////////////////////////////
    //////INFORMACIÓN CAMARA DE COMERCIO
    String camaraDeComercio = "";
    String etiquetaInicialICC = "<ICC>";
    String etiquetaFinalICC = "</ICC>";
    String ICC_1 = "<ICC_1>";
    //String ICC_2 = "ICC_2>";
    //String ICC_3 = "ICC_3>Yumbo";
    //String ICC_4 = "ICC_4>";
    //String ICC_5 = "ICC_5>Valle";
    //String ICC_6 = "ICC_6>CRA. 36 NRO. 13 151";
    //String ICC_7 = "ICC_7>CO";
    //String ICC_8 = "ICC_8>Colombia";
    String ICC_9 = "<ICC_9>";

    ///////////////////////////////////////////
    //////CONTACTO EMISOR
    String contactoEmisor = "";
    String etiquetaInicialCDE = "<CDE>";
    String etiquetaFinalCDE = "</CDE>";
    String CDE_1 = "<CDE_1>1";
    String CDE_2 = "<CDE_2>Maria Andrea Cardona;";
    String CDE_3 = "<CDE_3>";
    String CDE_4 = "<CDE_4>andreacardona@k-listo.com;";
    String CDE_5 = "<CDE_5>;";
    String CDE_6 = "<CDE_6>;";
    
    /////////////////////////////////////////////
    //////INFORMACION BANCARIA EMISOR
    String BFE_1 = "<BFE_1>";//NUMERO DE LA CUENTA
    String BFE_2 = "<BFE_2>";//NOMBRE TITULAR
    String BFE_3 = "<BFE_3>";//TIPO DE CUENTA CCTE - CAHO
    String BFE_4 = "<BFE_4>";//CODIGO BANCO
    String BFE_5 = "<BFE_5>";//CODIGO EAN SUCURSAL

    /////////////////////////////////////////////
    //////INFORMACION TRIBUTARIA EMISOR
    String etiquetaInicialGTE = "<GTE>";
    String GTE_1 = "<GTE_1>";//IDENTIFICADOR TRIBUTO 01-IVA
    String GTE_2 = "<GTE_2>";//NOMBRE TRIBUTO
    String etiquetaFinalGTE = "</GTE>";
    
    
    /////////////////////////////////////////////
    //////INFORMACION DESCUENTOS Y CARGOS
    String etiquetaInicialIDE = "<IDE>";
    String IDE_1 = "<IDE_1>";//IDENTIFICADOR DESCUENTO=false CARGOS=true
    String IDE_2 = "<IDE_2>";//VALOR DESCUENTO Y CARGO
    String IDE_3 = "<IDE_3>";//MONEDA
    String IDE_6 = "<IDE_6>";//PORCENTAJE
    String IDE_7 = "<IDE_7>";//BASE
    String IDE_8 = "<IDE_8>";//MONEDA BASE
    String IDE_10 = "<IDE_10>";//CONSECUTIVO DESCUENTO
    String etiquetaFinalIDE = "</IDE>";
    

    /////////////////////////////////////////////
    //////INFORMACION ADQUIRIENTE
    String regimenADQ = "";
    String tipoPersonaADQ = "";
    String tipoDocumentoADQ = "";
    String adquiriente = "";
    String direccionADQ = "";
    String razonSocialADQ = "";
    String nombreComercialADQ = "";
    String codPostalADQ = "";
    String poblacionADQ = "";
    String provinciaADQ = "";
    String etiquetaInicialADQ = "<ADQ>";
    String etiquetaFinalADQ = "</ADQ>";
    String ADQ_1 = "";//IDENTIFICADOR TIPO PERSONA 1 JURIDICA 2 NATURAL
    String ADQ_2 = "";// INDENTIFICACION ADQUIRIENTE
    String ADQ_3 = "";//TIPO DOC IDENTIFICACION FISCAL 31 NIT - 13 CEDULA
    String ADQ_4 = "";//REGIMEN FISCAL 48 IVA - 49 NO IVA
    //String ADQ_5 = "";//NUMERO DE IDENTIFICACION INTERNO
    String ADQ_6 = "";//RAZON SOCIAL DE LA EMPRESA
    String ADQ_7 = "";//NOMBRE COMERCIAL
    String ADQ_8 = "";//NOMBRE ADQUIRIENTE SOLO SI ES PERSONA NATURAL
   //String ADQ_9 = "";
    String ADQ_10 = "";//TEXTO LIBRE PARA DIRECCION
    String ADQ_11 = "";//CODIGO DEPARTAMENTO
    //String ADQ_12 = "";
    String ADQ_13 = "";//NOMBRE CIUDAD 
    String ADQ_14 = "";//CODIGO POSTAL
    String ADQ_15 = "";//CODIGO PAIS
    //String ADQ_16 = "";//CODIGO LOZALIZACION EAN
    //String ADQ_17 = ""
    //String ADQ_18 = "";
    String ADQ_19 = "";//NOMBRE DEPARTAMENTO
    //String ADQ_20 = "";
    String ADQ_21 = "";//NOMBRE PAIS
    String ADQ_22 = "";//DV ADQUIRIENTE
    String ADQ_23 = "";//CODIGO MUNICIPIO
    //String ADQ_24 = "";//IDENTIFICACION DEL ADQUIRIENTE
    
    String etiquetaInicialTCR;
    String TCR_1;
    String etiquetaFinalTCR;
    
    String etiquetaInicialILA = "<ILA>";
    String ILA_1;
    String ILA_2;
    String ILA_3;
    String ILA_4;
    String etiquetaFinalILA = "</ILA>";
    
    String etiquetaInicialDFA = "<DFA>";
    String DFA_1;
    String DFA_2;
    String DFA_3;
    String DFA_4;
    String DFA_5;
    String DFA_6;
    String DFA_7;
    String DFA_8;    
    String etiquetaFinalDFA = "</DFA>";
    
    String etiquetaInicialICR = "<ICR>";
    String ICR_1;
    String etiquetaFinalICR = "</ICR>";
    
    String etiquetaInicialGTA = "<GTA>";
    String GTA_1;
    String GTA_2;
    String etiquetaFinalGTA = "</GTA>";
          
    
    String etiquetaInicialLote = "<LOT>";
    String etiquetaFinalLote = "</LOT>";
    String lote = "";
    String nombreArchivo = "";

    ResultSet resultadoFactRect = null;
    PrintWriter writer;
    
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader (isr);

    String contenidoDocumento = "";
    String tipoFactura= "";
    
 ////INFORMACION SOBRE LOS ITEM DE LA FACTURA
    String itemsFactura = "";
    String etiquetaInicialITE = "<ITE>";
    String etiquetaFinalITE = "</ITE>";
    int regalo = 0;

    String ITE_1 = "";
    String ITE_2 = "";
    String ITE_3 = "";
    String ITE_4 = "";
    String ITE_5 = "";
    String ITE_6 = "";
    String ITE_7 = "";
    String ITE_8 = "";
    String ITE_9 = "";
    String ITE_10 = "";
    String ITE_11 = "";
    String ITE_12 = "";
    String ITE_13 = "";
    String ITE_14 = "";
    String ITE_15 = "";
    String ITE_16 = "";
    String ITE_17 = "";
    String ITE_18 = "";
    String ITE_19 = "";
    String ITE_20 = "";
    String ITE_21 = "";
    String ITE_22 = "";
    String ITE_23 = "";
    String ITE_24 = "";
    String ITE_25 = "";
    String ITE_26 = "";
    String ITE_27 = "";
    String ITE_28 = "";
    
    String IIM_1 = "";
    String IIM_2 = "";
    String IIM_3 = "";
    String IIM_4 = "";
    String IIM_5 = "";
    String IIM_6 = "";
    
    
    
    String etiquetaInicialIAE = "<IAE>";
    String IAE_1 = "";
    String IAE_2 = "";
    String etiquetaFinalIAE = "</IAE>";


    String totalLineas = "";
    String etiquetaInicialTOT = "<TOT>";
    String etiquetaFinalTOT = "</TOT>";
    String TOT_1 = "<TOT_1>";
    String TOT_2 = "<TOT_2>";
    String TOT_3 = "<TOT_3>";
    String TOT_4 = "<TOT_4>";
    String TOT_5 = "<TOT_5>";
    String TOT_6 = "<TOT_6>";
    String TOT_7 = "<TOT_7>";
    String TOT_8 = "<TOT_8>";
    String TOT_9 = "<TOT_9>";
    String TOT_10 = "<TOT_10>";
    String TOT_11 = "<TOT_11>";
    String TOT_12 = "<TOT_12>";
    String TOT_13 = "<TOT_13>";
    String TOT_14 = "<TOT_14>";
                
////////////////////////////////////////////////
////////////////DESCUENTOS
/////////////////////////////////////////////////
    String etiquetaInicialDSC = "<DSC>";
    String etiquetaFinalDSC = "</DSC>";
    String DSC_1 = "<DSC_1>";
    String DSC_2 = "<DSC_2>";
    String DSC_3 = "<DSC_3>";
    String DSC_4 = "<DSC_4>";
    String DSC_5 = "<DSC_5>";
    String DSC_6 = "<DSC_6>";
    String DSC_7 = "<DSC_7>";
    String DSC_8 = "<DSC_8>";
    String DSC_9 = "<DSC_9>";
    String DSC_10 = "<DSC_10>";
    double totalDctoFactura = 0.0;
    double tarifaDctoFactura = 0.0;
                

    public void inicializarArchivo(String ruta) {

        try {
            writer = new PrintWriter(ruta);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(generaXml.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generarEncabezadoConexion(int tipo) {
        tipoDocumento = "";
        if (tipo == 0) {
            tipoDocumento = "<FACTURA>";
            etiquetaFinDoc = "</FACTURA>";
        }
        if (tipo == 1) {
            tipoDocumento = "<NOTANC>";
            etiquetaFinDoc = "</NOTANC>";
        }

        cufe = "[NO]";

        encabezadoConexion = companyID + accountID + cufe + tipoDocumento + correo + password;

        //System.out.print(encabezadoConexion);
    }
  

    public void generarConsulta() {
        fachada.establecerConexion();
/*
        String consultaFacturas = "select C.CifCliente,C.DescCliente,C.RazonSocial,tbClienteObservacion.IDObservacion as clasificacionCliente, C.IDTipoCliente,tbLatDocIdentidad.IDDocIdentidad,C.Direccion as DireccionC,C.Poblacion as CiudadC,"
                + "C.Provincia as DptoC,C.IDPais as PaisC,FVCCG.NFactura,SUBSTRING(FVCCG.NFactura,LEN(FVCCG.IDContador)+1,LEN(FVCCG.NFactura)-LEN(FVCCG.IDContador)) "
                + "AS Folio,CONVERT(CHAR(10),FVCCG.FechaFactura,23) as Fecha,CONVERT(CHAR(10), FVCCG.FechaFactura,108) as Hora,FVCCG.FechaFactura,CONVERT(CHAR(10),FVCCG.FechaVencimiento,23) as FechaV,FVCCG.FechaVencimiento,FVCCG.IDContador,FVCCG.IDMoneda,"
                + "FVCCG.IDFormaPago,FVCCG.IDCondicionPago,FVCCG.BaseImponible,FVCCG.ImpIva,FVCCG.ImpTotal,FVCCG.Direccion,FVCCG.Poblacion,FVCCG.Provincia,FVCCG.IDPais,FVCCG.IDFactura from tbMaestroCliente as C INNER JOIN tbLatDocIdentidad on tbLatDocIdentidad.TipoDocIdentidad = c.TipoDocIdentidad " 
                + "left JOIN tbClienteObservacion on C.IDCliente = tbClienteObservacion.IDCliente JOIN"
                + "(select FVC.IDCliente,FVC.NFactura,FVC.FechaFactura,FVC.FechaVencimiento,FVC.IDContador,FVC.IDMoneda,FVC.IDFormaPago,FVC.IDCondicionPago,FVC.BaseImponible,FVC.ImpIva,FVC.ImpTotal,FVC.DirecFacturaXML,CG.Direccion,CG.Poblacion,CG.Provincia,"
                + "CG.IDPais,FVC.IDFactura from tbFacturaVentaCabecera as FVC JOIN tbMaestroCentroGestion as CG on FVC.IDCentroGestion = CG.IDCentroGestion) as FVCCG "
                + "on C.IDCliente = FVCCG.IDCliente WHERE FVCCG.IDContador NOT LIKE 'N%' and FechaFactura >=CONVERT(date,GETDATE()) and (DirecFacturaXML is null or DirecFacturaXML <> 'Generada')";
*/
        String consultaFacturas = "select C.CifCliente,C.DescCliente,C.RazonSocial,tbLatDocIdentidad.IDDocIdentidad,C.Direccion as DireccionC,C.Poblacion as CiudadC,"
                + "C.Provincia as DptoC,C.IDPais as PaisC,FVCCG.CodPostal, FVCCG.NFactura,SUBSTRING(FVCCG.NFactura,LEN(FVCCG.IDContador)+1,LEN(FVCCG.NFactura)-LEN(FVCCG.IDContador)) "
                + "AS Folio,CONVERT(CHAR(10),FVCCG.FechaFactura,23) as Fecha,CONVERT(CHAR(10), FVCCG.FechaFactura,108) as Hora,FVCCG.FechaFactura,CONVERT(CHAR(10),FVCCG.FechaVencimiento,23) as FechaV,FVCCG.FechaVencimiento,FVCCG.IDContador,FVCCG.IDMoneda,"
                + "FVCCG.IDFormaPago,FVCCG.IDCondicionPago,FVCCG.BaseImponible,FVCCG.ImpIva,FVCCG.ImpTotal,FVCCG.ImpDtoFactura,FVCCG.DtoFactura,FVCCG.ImpRetencion, FVCCG.ImpImpuestos , FVCCG.Direccion,FVCCG.Poblacion,FVCCG.Provincia,FVCCG.IDPais,FVCCG.IDFactura,FVCCG.TipoFactura from tbMaestroCliente as C INNER JOIN tbLatDocIdentidad on tbLatDocIdentidad.TipoDocIdentidad = c.TipoDocIdentidad " 
                + "JOIN (select FVC.IDCliente,FVC.NFactura,FVC.FechaFactura,FVC.FechaVencimiento,FVC.IDContador,FVC.IDMoneda,FVC.IDFormaPago,FVC.IDCondicionPago,FVC.BaseImponible,FVC.ImpIva,FVC.ImpTotal,FVC.DtoFactura,FVC.ImpDtoFactura,FVC.ImpRetencion, FVC.ImpImpuestos ,FVC.DirecFacturaXML,CG.Direccion,CG.Poblacion,CG.Provincia,FVC.CodPostal,"
                + "CG.IDPais,FVC.IDFactura, FVC.TipoFactura from tbFacturaVentaCabecera as FVC JOIN tbMaestroCentroGestion as CG on FVC.IDCentroGestion = CG.IDCentroGestion) as FVCCG "
                + "on C.IDCliente = FVCCG.IDCliente WHERE FVCCG.FechaFactura >=CONVERT(date,'2020-02-29') and (DirecFacturaXML is null or DirecFacturaXML <> 'Generada')";

        ResultSet resultadoFacturas = fachada.ejecutarConsulta(consultaFacturas);
        ResultSet datosEmpresa = fachada.ejecutarConsulta("select DescEmpresa,Cif,Direccion,web,CodPostal, Poblacion, provincia as Departamento,substring(codpostal,1,2) as Provincia,tbDatosEmpresa.IDPais, tbMaestroPais.CodigoISO, DescPais, IDCNAE AS CIIU, DatosRegistrales as Obligaciones, RegistroMercantil, IDCAE as TipoDoc from tbDatosEmpresa inner join tbMaestroPais on tbDatosEmpresa.IDPais = tbMaestroPais.IDPais");

        String consultaNotas = "select C.CifCliente,C.DescCliente,C.RazonSocial,C.IDTipoCliente,C.TipoDocIdentidad,C.Direccion as DireccionC,C.Poblacion as CiudadC,"
                + "C.Provincia as DptoC,C.IDPais as PaisC,FVCCG.NFactura,SUBSTRING(FVCCG.NFactura,LEN(FVCCG.IDContador)+1,LEN(FVCCG.NFactura)-LEN(FVCCG.IDContador)) "
                + "AS Folio,CONVERT(CHAR(10),FVCCG.FechaFactura,23) as Fecha,CONVERT(CHAR(10), FVCCG.FechaFactura,108) as Hora,FVCCG.FechaFactura,FVCCG.FechaVencimiento as FechaV,FVCCG.IDContador,FVCCG.IDFacturaRectificada,FVCCG.IDMoneda,"
                + "FVCCG.IDFormaPago,FVCCG.IDCondicionPago,FVCCG.BaseImponible,FVCCG.ImpIva,FVCCG.ImpTotal,FVCCG.Direccion,FVCCG.Poblacion,FVCCG.Provincia,FVCCG.IDPais,FVCCG.IDFactura from tbMaestroCliente as C JOIN "
                + "(select FVC.IDCliente,FVC.NFactura,FVC.FechaFactura,FVC.FechaVencimiento,FVC.IDContador,FVC.IDFacturaRectificada,FVC.IDMoneda,FVC.IDFormaPago,FVC.IDCondicionPago,FVC.BaseImponible,FVC.ImpIva,FVC.ImpTotal,FVC.DirecFacturaXML,CG.Direccion,CG.Poblacion,CG.Provincia,"
                + "CG.IDPais,FVC.IDFactura from tbFacturaVentaCabecera as FVC JOIN tbMaestroCentroGestion as CG on FVC.IDCentroGestion = CG.IDCentroGestion) as FVCCG "
                + "on C.IDCliente = FVCCG.IDCliente WHERE FVCCG.IDContador LIKE 'N%' and FechaFactura >=CONVERT(date,GETDATE()) and (DirecFacturaXML is null or DirecFacturaXML <> 'Generada')";

        ResultSet resultadoNotasC = fachada.ejecutarConsulta(consultaNotas);

        generarDatosEmpresa(datosEmpresa);

        generarEncabezadoConexion(0);
        generarFacturas(resultadoFacturas, datosEmpresa);

        //generarNotasCredito(resultadoNotasC, datosEmpresa);

        fachada.cerrarConexion();

        //generarEmisorDocumento(resultado);
        //System.out.println(consulta);
        //return resultado;
    }

    public void generarDatosEmpresa(ResultSet datosEmpresa) {
        try {
            while (datosEmpresa.next()) {
                String id = datosEmpresa.getString("Cif");
                cif = id.substring(0, id.indexOf('-'));
                dv = id.substring(id.indexOf('-')+1);
                descEmpresa = datosEmpresa.getString("DescEmpresa");
                web = datosEmpresa.getString("web");
                direccion = datosEmpresa.getString("Direccion");
                codPostal = datosEmpresa.getString("codPostal");
                poblacion = datosEmpresa.getString("Poblacion");
                departamento = datosEmpresa.getString("Departamento");
                provincia = datosEmpresa.getString("Provincia");
                idPais = datosEmpresa.getString("IDPais");
                codISOPais = datosEmpresa.getString("CodigoIso");
                descPais = datosEmpresa.getString("DescPais");
                tipoDocEmpresa = datosEmpresa.getString("TipoDoc");
                obligacionesEmpresa = datosEmpresa.getString("obligaciones");
                CIIU = datosEmpresa.getString("CIIU");
                registroMercantil = datosEmpresa.getString("RegistroMercantil").substring(0, datosEmpresa.getString("RegistroMercantil").indexOf("-"));
                

            }
        } catch (SQLException ex) {
            Logger.getLogger(generaXml.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print(ex);
        }
    }

    public void generarFacturas(ResultSet datosDocumento, ResultSet datosEmpresa) {        
         
        try {

            while (datosDocumento.next()) {
                nombreArchivo = datosDocumento.getString("NFactura");
                if(sSistemaOperativo.contains("Mac")){
                    inicializarArchivo("//" + nombreArchivo + ".xml");
                }else
                inicializarArchivo("C:\\spool\\" + nombreArchivo + ".xml");
                obligacionesADQ = "";
                //Datos Encabezado ENC
                //cifCliente = datosDocumento.getString("CifCliente");
                String id = datosDocumento.getString("CifCliente");
                cifCliente = id.substring(0, id.indexOf('-'));
                dvCliente = id.substring(id.indexOf('-')+1);
                numeroFactura = datosDocumento.getString("NFactura");
                tipoFactura = datosDocumento.getString("TipoFactura");
                fechaEmisionFactura = datosDocumento.getString("Fecha");
                horaEmisionFactura = (datosDocumento.getString("Hora")).trim();
                moneda = datosDocumento.getString("IDMoneda");
                fechaVencimientoFactura = datosDocumento.getString("FechaV");
                
                if(datosDocumento.getString("IDFormaPago").equals("00")){
                medioPago = "ZZZ";
                }else{
                    medioPago = "10";
                }
                
                if(datosDocumento.getString("IDCondicionPago").equals("00")){
                    formaPago = "1";
                }else{
                    formaPago = "2";
                }

                //Datos Emisor ADQ
                tipoPersonaADQ = "";
                regimenADQ = "";
                tipoDocumentoADQ = datosDocumento.getString("IDDocIdentidad");
                direccionADQ = datosDocumento.getString("DireccionC");
                razonSocialADQ = datosDocumento.getString("RazonSocial");
                nombreComercialADQ = datosDocumento.getString("DescCliente");
                codPostalADQ = datosDocumento.getString("CodPostal");       
                /*
                if (codPostalADQ == null){
                    mensajesErrorCamposCliente("codPostalADQ", id);
                }*/
                
                poblacionADQ = datosDocumento.getString("CiudadC");
                /*
                if (poblacionADQ == null ){
                    mensajesErrorCamposCliente("poblacionADQ", id);
                }*/
                
                provinciaADQ = datosDocumento.getString("DptoC");
                /*
                if (provinciaADQ == null){
                    mensajesErrorCamposCliente("provinciaADQ", id);
                }*/

                //Totales
                baseImponible = datosDocumento.getDouble("baseImponible");
                valorIva = datosDocumento.getDouble("ImpIva");
                totalFactura = datosDocumento.getDouble("impTotal");
                valorRetencion = datosDocumento.getDouble("impRetencion");
                valorImpuesto = datosDocumento.getDouble("impImpuestos");
                totalDctoFactura = datosDocumento.getDouble("impDtoFactura");
                tarifaDctoFactura = datosDocumento.getDouble("DtoFactura")/100;

                prefijo = datosDocumento.getString("IDcontador");
                
                ResultSet r;
                
                encabezadoDocumento = "";
                ENC_1 = "<ENC_1>";
                ENC_2 = "<ENC_2>";
                ENC_3 = "<ENC_3>";
                ENC_4 = "<ENC_4>UBL 2.1";
                ENC_5 = "<ENC_5>DIAN 2.1";
                ENC_6 = "<ENC_6>";
                ENC_7 = "<ENC_7>";
                ENC_8 = "<ENC_8>";
                ENC_9 = "<ENC_9>";
                ENC_10 = "<ENC_10>";
                ENC_11 = "<ENC_11>";
                ENC_12 = "<ENC_12>";
                ENC_13 = "<ENC_13>";
                ENC_14 = "<ENC_14>";
                ENC_15 = "<ENC_15>";
                ENC_16 = "<ENC_16>";
                ENC_17 = "<ENC_17>";
                ENC_18 = "<ENC_18>";
                ENC_20 = "<ENC_20>";
                ENC_21 = "<ENC_21>";
                

                emisor = "";
                etiquetaInicialEMI = "<EMI>";
                etiquetaFinalEMI = "</EMI>";
                tipoPersona = "1";
                tipoDocumentoEmisor = "";

                EMI_1 = "<EMI_1>1";
                EMI_2 = "<EMI_2>";
                EMI_3 = "<EMI_3>";
                EMI_4 = "<EMI_4>";               

               /*
                String consulta = "select IDObservacion from tbClienteObservacion where IDCliente like "+"'"+cifCliente+"%"+"'";
                r =fachada.ejecutarConsulta<consulta);
                while <r.next()){
                    obligaciones+=r.getString("IDObservacion")+";";
                }                
                EMI_4 = "<EMI_4>";
                if(obligaciones.contains("O-48")){
                    EMI_4 +="48";
                }else if(obligaciones.contains("O-49")){
                    EMI_4 +="49";
                }
               */
                EMI_5 = "<EMI_5>";
                EMI_6 = "<EMI_6>";
                EMI_7 = "<EMI_7>";
                EMI_8 = "<EMI_8>";
                EMI_9 = "<EMI_9>";
                EMI_10 = "<EMI_10>";
                EMI_11 = "<EMI_11>";
                EMI_12 = "<EMI_12>";
                EMI_13 = "<EMI_13>";
                EMI_14 = "<EMI_14>";
                EMI_15 = "<EMI_15>";
                EMI_16 = "<EMI_16>";
                EMI_17 = "<EMI_17>";
                //EMI_18 = "<EMI_18>";
                EMI_19 = "<EMI_19>";
                //EMI_20 = "<EMI_20>";
                EMI_21 = "<EMI_21>";
                EMI_22 = "<EMI_22>";
                EMI_23 = "<EMI_23>";
                EMI_24 = "<EMI_24>";
                EMI_25 = "<EMI_25>";




               
                etiquetaInicialTAC = "<TAC>";
                etiquetaFinalTAC = "</TAC>";
                TAC_1 = "<TAC_1>"+obligacionesEmpresa;
                
                DFE_1 = "<DFE_1>";
                DFE_2 = "<DFE_2>";
                DFE_3 = "<DFE_3>";
                DFE_4 = "<DFE_4>";
                DFE_5 = "<DFE_5>";
                DFE_6 = "<DFE_6>";
                DFE_7 = "<DFE_7>";
                DFE_8 = "<DFE_8>";
                    
                camaraDeComercio = "";
                etiquetaInicialICC = "<ICC>";
                etiquetaFinalICC = "</ICC>";

                ICC_1 = "<ICC_1>"+registroMercantil;
                //ICC_2 = "ICC_2>";
                //ICC_3 = "ICC_3>Yumbo";
                //ICC_4 = "ICC_4>";
                //ICC_5 = "ICC_5>Valle";
                //ICC_6 = "ICC_6>CL 15 22 200";
                //ICC_7 = "ICC_7>CO";
                //ICC_8 = "ICC_8>Colombia";
                ICC_9 = "<ICC_9>"+prefijo+"";

                camaraDeComercio += etiquetaInicialICC + ICC_1 + etiquetaFinalICC;

                contactoEmisor = "";
                etiquetaInicialCDE = "<CDE>";
                etiquetaFinalCDE = "</CDE>";
                CDE_1 = "<CDE_1>1";
                CDE_2 = "<CDE_2>Maria Andrea Cardona";
                CDE_3 = "<CDE_3>";
                CDE_4 = "<CDE_4>andreacardona@k-listo.com";

                contactoEmisor += etiquetaInicialCDE + CDE_1 + CDE_2 + CDE_4 + etiquetaFinalCDE;
                adquiriente = "";
                etiquetaInicialADQ = "<ADQ>";
                etiquetaFinalADQ = "</ADQ>";

                ADQ_1 = "<ADQ_1>";
                ADQ_2 = "<ADQ_2>";
                ADQ_3 = "<ADQ_3>";
                ADQ_4 = "<ADQ_4>";
                ADQ_6 = "<ADQ_6>";
                ADQ_7 = "<ADQ_7>";
                ADQ_8 = "<ADQ_8>";
                ADQ_10 = "<ADQ_10>";
                ADQ_11 = "<ADQ_11>";
                ADQ_13 = "<ADQ_13>";
                ADQ_14 = "<ADQ_14>";
                ADQ_15 = "<ADQ_15>";
                ADQ_19 = "<ADQ_19>";
                ADQ_21 = "<ADQ_21>";
                ADQ_22 = "<ADQ_22>";
                ADQ_23 = "<ADQ_23>";
                
                ILA_1 = "<ILA_1>";
                ILA_2 = "<ILA_2>";
                ILA_3 = "<ILA_3>";
                ILA_4 = "<ILA_4>";
                
                DFA_1 = "<DFA_1>";
                DFA_2 = "<DFA_2>";
                DFA_3 = "<DFA_3>";
                DFA_4 = "<DFA_4>";
                DFA_5 = "<DFA_5>";
                DFA_6 = "<DFA_6>";
                DFA_7 = "<DFA_7>";
                DFA_8 = "<DFA_8>";
                
                ICR_1 = "<ICR_1>";
                
                GTA_1 = "<GTA_1>";
                GTA_2 = "<GTA_2>";
                    
                ENC_1 += "INVOIC";
                ENC_2 += cif + "";
                ENC_3 += cifCliente + "";
                ENC_6 += numeroFactura + "";
                //ENC_6 += "PRUE980000100";
                ENC_7 += fechaEmisionFactura + "";
                ENC_8 += horaEmisionFactura + zonaHoraria + "";
                ENC_9 += "01";
                ENC_10 += moneda + "";
                ENC_11 += "";
                ENC_12 += "";
                ENC_13 += "";
                ENC_14 += "";
                r = fachada.ejecutarConsulta("select count(*) as qFilas from tbFacturaVentaLinea where idFactura ="+datosDocumento.getString("IDFactura"));                
                while (r.next()){
                    ENC_15 = "<ENC_15>" + r.getString("qFilas")+"";
                }
                
                ENC_16 += fechaVencimientoFactura + "";
                ENC_17 += "";
                ENC_18 += "";
                if(prefijo.equals("SETT")){
                    ENC_20+="2";
                }else{
                    ENC_20+="1";
                }
                ENC_21+=tipoFactura+"";
                  

                //encabezadoDocumento += etiquetaInicialENC + ENC_1 + ENC_2 + ENC_3 + ENC_4 + ENC_5 + ENC_6 + ENC_7 + ENC_8 + ENC_9 + ENC_10 + ENC_16 + ENC_20 + ENC_21 +etiquetaFinalENC;
                //System.out.print(encabezado);

                EMI_1 += "";
                EMI_2 += cif + "";
                EMI_3 += tipoDocEmpresa+"";
                if(obligacionesEmpresa.contains("O-48")){
                    EMI_4 +="48";
                }else if(obligacionesEmpresa.contains("O-49")){
                    EMI_4 +="49";
                }
                EMI_5 += "";
                EMI_6 += descEmpresa + "";
                EMI_7 += descEmpresa;
                EMI_8 += "";
                EMI_9 += "";
                EMI_10 += direccion + "";
                EMI_11 += provincia + "";
                EMI_13 += poblacion + "";
                EMI_14 += codPostal+"";
                EMI_15 += codISOPais+"";
                EMI_16 += "";
                EMI_17 += "";
                EMI_19 += departamento + "";
                //EMI_20 += poblacion + "";
                EMI_21 += descPais+"";
                EMI_22 += dv+"";
                EMI_23 += codPostal+"";
                EMI_24 += descEmpresa + "";
                EMI_25 += CIIU + "";
                
                
             

                emisor += etiquetaInicialEMI + EMI_1 + EMI_2 + EMI_3 + EMI_4 + EMI_6 + EMI_10 + EMI_11  + EMI_13 + EMI_14 + EMI_15 + EMI_19 + EMI_21 + EMI_22 + EMI_23 + EMI_24 + EMI_25 + infoTributariaEMI + camaraDeComercio + contactoEmisor + etiquetaFinalEMI;
                //System.out.print(emisor);  



                DFE_1 +=codPostal+"";
                DFE_2 +=provincia+"";
                DFE_3 +=codISOPais+"";
                DFE_4 +=codPostal+"";
                DFE_5 += descPais;
                DFE_6 += departamento;
                DFE_7 += poblacion;
                DFE_8 += direccion;
                
                if(obligacionesEmpresa.contains("O-48")){
                    GTE_1 ="<GTE_1>01";
                    GTE_2 ="<GTE_2>IVA";
                }
                
               
                r =fachada.ejecutarConsulta("select tbClienteObservacion.IDObservacion as obligacion, Entidad from tbClienteObservacion inner join tbMaestroObservacion on tbClienteObservacion.IDObservacion = tbMaestroObservacion.IDObservacion where IDCliente = "+"'"+cifCliente+"' and Entidad = "+"'"+"ClienteObservacion"+"'");
                
                while (r.next()){
                    obligacionesADQ+=r.getString("obligacion")+";";
                }
                if(obligacionesADQ.isEmpty()){
                    mensajesErrorCamposCliente("obligaciones",id);
                }
                
             
                r =fachada.ejecutarConsulta("select tbClienteObservacion.IDObservacion as tipoPersona, Entidad from tbClienteObservacion inner join tbMaestroObservacion on tbClienteObservacion.IDObservacion = tbMaestroObservacion.IDObservacion where IDCliente = "+"'"+cifCliente+"' and Entidad = "+"'"+"LatTerceros"+"'");
                
                while (r.next()){
                    tipoPersonaADQ=r.getString("tipoPersona");
                }
                if(tipoPersonaADQ.isEmpty()){
                    mensajesErrorCamposCliente("tipo persona",id);
                }

                
                ADQ_1 += tipoPersonaADQ + "";
                ADQ_2 += cifCliente + "";
                ADQ_3 += tipoDocumentoADQ + "";
                
                   
                        
                
                ADQ_4 = "<ADQ_4>";
                if(obligacionesADQ.contains("O-48")){
                    ADQ_4 +="48";
                }else if(obligacionesADQ.contains("O-49")){
                    ADQ_4 +="49";
                }
                
                ADQ_6 = "<ADQ_6>" + razonSocialADQ + "";
                ADQ_7 += nombreComercialADQ + "";
                ADQ_8 += razonSocialADQ + "";
                ADQ_10 += direccionADQ + "";
                ADQ_11 += codPostalADQ.substring(0,1) + "";
                ADQ_13 += poblacionADQ + "";
                ADQ_14 += codPostalADQ + "";                
                ADQ_15 += "CO";
                ADQ_19 += provinciaADQ + "";  
                ADQ_21 += "Colombia" + ""; 
                ADQ_22 += dvCliente + ""; 
                ADQ_23 += codPostalADQ + ""; 



                etiquetaInicialTCR = "<TCR>";
                TCR_1 = "<TCR_1>"+ obligacionesADQ + "";
                etiquetaFinalTCR = "</TCR>";
                
                ILA_1 += razonSocialADQ + "";
                ILA_2 += cifCliente + "";
                ILA_3 += tipoDocumentoADQ + "";
                ILA_4 += dvCliente + ""; 
                
                
                DFA_1 +="CO";
                DFA_2 += codPostalADQ.substring(0,2) + "";
                DFA_3 += codPostalADQ + "";
                DFA_4 += codPostalADQ + "";
                DFA_5 += "Colombia";
                DFA_6 += provinciaADQ;
                DFA_7 += poblacionADQ;
                DFA_8 += direccionADQ;
                
                ICR_1 += "112345";
                
                if(obligacionesADQ.contains("O-48")){
                    GTA_1 ="<GTA_1>01";
                    GTA_2 ="<GTA_2>IVA";
                }
                        
                String etiquetaInicialCDA = "<CDA>";
                String etiquetaFinalCDA = "</CDA>";
                String contactoADQ = "";
                String CDA_1 = "<CDA_1>2";
                String CDA_2 = "<CDA_2>Dionaymer Ospitia";
                String CDA_3 = "<CDA_3>6602626";
                String CDA_4 = "<CDA_4>dop@doecsystem.com";


                String consultaLinea = "";
                consultaLinea = "select IDLineaFactura, Cantidad,FL.Lote as LoteItem,Regalo,Importe,Precio,DescArticulo,Dto,Dto1,Dto2,Dto3,IDUdMedida,IDArticulo,IVA.Factor from tbFacturaVentaLinea as FVL "
                        + "join tbMaestroTipoIva as IVA on FVL.IDTipoIVA = IVA.IDTipoIva"
                        + " left join tbAlbaranVentaLote as FL on FL.idLineaAlbaran = FVL.idLineaAlbaran where IDFactura=" + datosDocumento.getString("IDFactura");

                ResultSet resultadoLineas = fachada.ejecutarConsulta(consultaLinea);

                double cantidad = 0.00;
                double importe = 0.00;
                double precio = 0.00;
                String descArticulo = "";
                double iva = 0.00;
                double dto1 = 0.00;
                double dto2 = 0.00;
                double dto3 = 0.00;
                double dto = 0.00;
                double[]dtos = new double[4];
                String idUdMedida = "";
                String idArticulo = "";
                double subtotal = 0.00;
                double valorDto1 = 0.00;
                double totalDcto = 0.00;
                double divisor = 100.00;
                double mntIva = 0.00;
                double mntImpuestos = 0.00;
                String idLineaFactura = "";

                ////INFORMACION SOBRE LOS ITEM DE LA FACTURA
                itemsFactura = "";
                etiquetaInicialITE = "<ITE>";
                etiquetaFinalITE = "</ITE>";
                lote = "";
                regalo = 0;

                ITE_1 = "";
                ITE_3 = "";
                ITE_4 = "";
                ITE_5 = "";
                ITE_6 = "";
                ITE_7 = "";
                ITE_8 = "";
                ITE_9 = "";
                ITE_10 = "";
                ITE_11 = "";
                ITE_12 = "";
                ITE_13 = "";
                ITE_14 = "";
                ITE_15 = "";
                ITE_16 = "";
                ITE_17 = "";
                ITE_18 = "";
                ITE_19 = "";
                ITE_20 = "";
                ITE_21 = "";
                ITE_22 = "";
                ITE_23 = "";
                ITE_24 = "";
                ITE_25 = "";
                ITE_26 = "";


                totalLineas = "";
                etiquetaInicialTOT = "<TOT>";
                etiquetaFinalTOT = "</TOT>";
                TOT_1 = "<TOT_1>";
                TOT_2 = "<TOT_2>";
                TOT_3 = "<TOT_3>";
                TOT_4 = "<TOT_4>";
                TOT_5 = "<TOT_5>";
                TOT_6 = "<TOT_6>";
                TOT_7 = "<TOT_7>";
                TOT_8 = "<TOT_8>";
                TOT_9 = "<TOT_9>";
                TOT_10 = "<TOT_10>";
                TOT_11 = "<TOT_11>";
                TOT_12 = "<TOT_12>";
                TOT_13 = "<TOT_13>";
                TOT_14 = "<TOT_14>";
                
                DSC_1 = "<DSC_1>";
                DSC_2 = "<DSC_2>";
                DSC_3 = "<DSC_3>";
                DSC_4 = "<DSC_4>";
                DSC_5 = "<DSC_5>";
                DSC_6 = "<DSC_6>";
                DSC_7 = "<DSC_7>";
                DSC_8 = "<DSC_8>";
                DSC_9 = "<DSC_9>";
                DSC_10 = "<DSC_10>";

                
                
                
                
                String consultaDescuento = "";
                consultaDescuento = "select Cantidad,FL.Lote as LoteItem,Regalo,Importe,Precio,DescArticulo,Dto,Dto1,Dto2,Dto3,IDUdMedida,IDArticulo,IVA.Factor from tbFacturaVentaLinea as FVL "
                        + "join tbMaestroTipoIva as IVA on FVL.IDTipoIVA = IVA.IDTipoIva"
                        + " left join tbAlbaranVentaLote as FL on FL.idLineaAlbaran = FVL.idLineaAlbaran where IDFactura=" + datosDocumento.getString("IDFactura");

                ResultSet resultadoDescuento = fachada.ejecutarConsulta(consultaDescuento);
                
                while (resultadoDescuento.next()) {
                    cantidad = resultadoDescuento.getDouble("Cantidad");
                    importe = resultadoDescuento.getDouble("Importe");
                    precio = resultadoDescuento.getDouble("Precio");
                    dto1 = resultadoDescuento.getDouble("Dto1")/divisor;
                    dto2 = resultadoDescuento.getDouble("Dto2")/divisor;
                    dto3 = resultadoDescuento.getDouble("Dto3")/divisor;
                    dto = resultadoDescuento.getDouble("Dto")/divisor;
                    
                    dtos[0]=dto;
                    dtos[1]=dto1;                    
                    dtos[2]=dto2;
                    dtos[3]=dto3;
                    
                    double descuentos[][] = new double[4][0];

                    descuentos = calculaDescuento(cantidad * precio,dtos);
                    
                    for (double[] descuento : descuentos) {
                        totalDcto += descuento[0];
                    }

                    
                }
                

                    
                    
                

                importeBruto = baseImponible + totalDcto;
                TOT_1 += formateador(baseImponible) + "";
                TOT_2 += moneda + "";
                TOT_3 += formateador(baseImponible) + "";
                TOT_4 += moneda + "";
                TOT_5 += formateador(totalFactura);
                TOT_6 += moneda + "";
                TOT_7 += formateador(totalFactura);
                TOT_8 += moneda + "";
                TOT_9 += formateador(totalDcto);
                TOT_10 += moneda + "";
                
  
                DSC_1 += "false";
                DSC_2 += formateador(totalDcto/importeBruto);
                DSC_3 += formateador(totalDcto);
                DSC_4 += "COP";
                DSC_5 += "00";
                DSC_7 += formateador(importeBruto);
                DSC_8 += "COP"; 
                DSC_10 += "1";



                //String consultaIVA ="Select * from ";

                String totalImpuestos = "";
                String etiInicialTIM = "<TIM>";
                String etiFinalTIM = "</TIM>";
                String TIM_1 = "<TIM_1>";
                String TIM_2 = "<TIM_2>";
                String TIM_3 = "<TIM_3>";

                ////DATOS DE RESOLUCION DE FACTURACION
                String resolucionFacturacion = "";
                String etiquetaInicialDRF = "<DRF>";
                String etiquetaFinalDRF = "</DRF>";
                
                String consultaResolucion = "select NAutorizacion, CONVERT(date,FechaDesde) as FechaDesde ,CONVERT(date,FechaHasta) as FechaHasta, ContadorDesde, ContadorHasta from tbLatMaestroContador where idContador = "+"'"+ prefijo+"'";
                ResultSet resultadoResolucion= fachada.ejecutarConsulta(consultaResolucion);
                
                String resFacturacion = "";
                String fechaDesde = "";
                String fechaHasta = "";
                String consecutivoDesde = "";
                String consecutivoHasta = "";
                
                
                while(resultadoResolucion.next()){
                    
                    resFacturacion = resultadoResolucion.getNString("NAutorizacion");
                    fechaDesde = resultadoResolucion.getString("FechaDesde");
                    fechaHasta = resultadoResolucion.getString("FechaHasta");
                    consecutivoDesde = resultadoResolucion.getString("ContadorDesde");
                    consecutivoHasta = resultadoResolucion.getString("ContadorHasta");
                }

                String DRF_1 = "\t\t<DRF_1>"+resFacturacion;
                String DRF_2 = "\t\t<DRF_2>"+fechaDesde;
                String DRF_3 = "\t\t<DRF_3>"+fechaHasta;
                //String DRF_4 = "DRF_4>" + prefijo + "";
                String DRF_4 = "\t\t<DRF_4>"+prefijo;
                String DRF_5 = "\t\t<DRF_5>"+consecutivoDesde;
                String DRF_6 = "\t\t<DRF_6>"+consecutivoHasta;

                ////NOTAS QUE IRAN EN LA FACTURA TIPO RETENEDOR - REGIMEN - IVA ETC
                String etiquetaInicialNOT = "<NOT>";
                String etiquetaFinalNOT = "</NOT>";
                String notaFactura = "<NOT_1>NO SOMOS GRANDES CONTRIBUYENTES - REGIMEN COMUN - CIIU 4631";
                
                
                ///MEDIOS DE PAGO
                String etiquetaInicialMEP = "<MEP>";
                String MEP_1 = "<MEP_1>";
                String MEP_2 = "<MEP_2>";
                String MEP_3 = "<MEP_3>";
                String etiquetaFinalMEP = "</MEP>";
                
                
                MEP_1 += medioPago+"</MEP_1>";
                MEP_2 += formaPago+"</MEP_2>";
                MEP_3 += fechaVencimientoFactura+"</MEP_3>";               
                


                ////INFORMACION PARA CARVAJAL
                String etiquetaInicialCTS = "<CTS>";
                String etiquetaFinalCTS = "</CTS>";
                String infoCarvajal = "<CTS_1>CGEN01";

               // System.out.print(encabezadoConexion + encabezadoDocumento + emisor + adquiriente + infoTributariaADQ + etiquetaFinalADQ + totalLineas + resolucionFacturacion + notaFactura + infoCarvajal + itemsFactura + etiquetaFinDoc);

                writer.println(tipoDocumento);
                //////////////////////////////////
                ///////ENCABEZADO
                /////////////////////////////////
                writer.println("\t"+etiquetaInicialENC);
                writer.println("\t\t"+ENC_1+"</ENC_1>");
                ///writer.println("\t\t"+ENC_2+"</ENC_2>");
                writer.println("\t\t<ENC_2>901143311</ENC_2>");
                writer.println("\t\t"+ENC_3+"</ENC_3>");
                writer.println("\t\t"+ENC_4+"</ENC_4>");
                writer.println("\t\t"+ENC_5+"</ENC_5>");
                //writer.println("\t\t"+ENC_6+"</ENC_6>");
                writer.println("\t\t"+"<ENC_6>TCFA19113</ENC_6>");
                writer.println("\t\t"+ENC_7+"</ENC_7>");
                writer.println("\t\t"+ENC_8+"</ENC_8>");
                writer.println("\t\t"+ENC_9+"</ENC_9>");
                writer.println("\t\t"+ENC_10+"</ENC_10>");
                writer.println("\t\t"+ENC_15+"</ENC_15>");
                writer.println("\t\t"+ENC_16+"</ENC_16>");
                writer.println("\t\t"+ENC_20+"</ENC_20>");
                writer.println("\t\t"+ENC_21+"</ENC_21>");
                writer.println("\t"+etiquetaFinalENC);
                //////////////////////////////////
                ///////EMISOR
                /////////////////////////////////
                writer.println("\t"+etiquetaInicialEMI);
                writer.println("\t\t"+EMI_1+"</EMI_1>");
                //writer.println("\t\t"+EMI_2+"</EMI_2>");
                writer.println("\t\t<EMI_2>901143311</EMI_2>");
                writer.println("\t\t"+EMI_3+"</EMI_3>");
                writer.println("\t\t"+EMI_4+"</EMI_4>");
                writer.println("\t\t"+EMI_6+"</EMI_6>");
                writer.println("\t\t"+EMI_7+"</EMI_7>");
                writer.println("\t\t"+EMI_10+"</EMI_10>");
                writer.println("\t\t"+EMI_11+"</EMI_11>");
                //writer.println(EMI_12);
                writer.println("\t\t"+EMI_13+"</EMI_13>");
                writer.println("\t\t"+EMI_14+"</EMI_14>");
                writer.println("\t\t"+EMI_15+"</EMI_15>");
                //writer.println(EMI_18);
                writer.println("\t\t"+EMI_19+"</EMI_19>");
                //writer.println(EMI_20);
                writer.println("\t\t"+EMI_21+"</EMI_21>");
                //writer.println("\t\t"+EMI_22+"</EMI_22>");
                writer.println("\t\t<EMI_22>8</EMI_22>");
                writer.println("\t\t"+EMI_23+"</EMI_23>");
                writer.println("\t\t"+EMI_24+"</EMI_24>");
                writer.println("\t\t"+EMI_25+"</EMI_25>");
                ///////////////////////////////////
                ///////TAC
                ///////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialTAC);
                writer.println("\t\t\t\t"+TAC_1+"</TAC_1>");
                writer.println("\t\t\t"+etiquetaFinalTAC);
                /////////////////////////////////
                //////DFE
                writer.println("\t\t\t"+etiquetaInicialDFE);
                writer.println("\t\t\t\t"+DFE_1+"</DFE_1>");
                writer.println("\t\t\t\t"+DFE_2+"</DFE_2>");
                writer.println("\t\t\t\t"+DFE_3+"</DFE_3>");
                writer.println("\t\t\t\t"+DFE_4+"</DFE_4>");
                writer.println("\t\t\t\t"+DFE_5+"</DFE_5>");
                writer.println("\t\t\t\t"+DFE_6+"</DFE_6>");
                writer.println("\t\t\t\t"+DFE_7+"</DFE_7>");
                writer.println("\t\t\t\t"+DFE_8+"</DFE_8>");
                writer.println("\t\t\t"+etiquetaFinalDFE);
                /////////////////////////////////
                /////////////////////////////////
                //////ICC
                /////////////////////////////////
               // writer.println("\t\t\t"+etiquetaInicialICC);
                //writer.println("\t\t\t\t"+ICC_1+"</ICC_1>");
                //writer.println("ICC_3>Yumbo");
                //writer.println("ICC_5>Valle");
                //writer.println("ICC_6>CL 15 22 200");
                //writer.println("ICC_7>CO");
                //writer.println("ICC_8>Colombia");
                ///writer.println("\t\t\t\t"+ICC_9+"</ICC_9>");
                //writer.println("\t\t\t\t<ICC_9>SETT</ICC_9>");
                //writer.println("\t\t\t"+etiquetaFinalICC);
                //////////////////////////////////
                ///////CDE
                //////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialCDE);
                writer.println("\t\t\t\t"+CDE_1+"</CDE_1>");
                writer.println("\t\t\t\t"+CDE_2+"</CDE_2>");
                writer.println("\t\t\t\t"+CDE_4+"</CDE_4>");
                writer.println("\t\t\t"+etiquetaFinalCDE);                
                //////////////////////////////////
                ///////GTE
                //////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialGTE);
                writer.println("\t\t\t\t"+GTE_1+"</GTE_1>");
                writer.println("\t\t\t\t"+GTE_2+"</GTE_2>");
                writer.println("\t\t\t"+etiquetaFinalGTE);
                writer.println("\t\t"+etiquetaFinalEMI);
                //////////////////////////////////
                ///////ADQUIRIENTE
                //////////////////////////////////
                writer.println("\t\t"+etiquetaInicialADQ );
                writer.println("\t\t\t"+ADQ_1+"</ADQ_1>");
                writer.println("\t\t\t"+ADQ_2+"</ADQ_2>");
                writer.println("\t\t\t"+ADQ_3+"</ADQ_3>");
                writer.println("\t\t\t"+ADQ_4+"</ADQ_4>");
                writer.println("\t\t\t"+ADQ_6+"</ADQ_6>");
                writer.println("\t\t\t"+ADQ_7+"</ADQ_7>");
                writer.println("\t\t\t"+ADQ_8+"</ADQ_8>");
                writer.println("\t\t\t"+ADQ_10+"</ADQ_10>");
                writer.println("\t\t\t"+ADQ_11+"</ADQ_11>");
                writer.println("\t\t\t"+ADQ_13+"</ADQ_13>");
                writer.println("\t\t\t"+ADQ_14+"</ADQ_14>");
                writer.println("\t\t\t"+ADQ_15+"</ADQ_15>");
                writer.println("\t\t\t"+ADQ_19+"</ADQ_19>");
                writer.println("\t\t\t"+ADQ_21+"</ADQ_21>");
                writer.println("\t\t\t"+ADQ_22+"</ADQ_22>");
                writer.println("\t\t\t"+ADQ_23+"</ADQ_23>");
                ////////////////////////////////////
                //////TCR
                ///////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialTCR);
                writer.println("\t\t\t\t"+TCR_1+"</TCR_1>");
                writer.println("\t\t\t"+etiquetaFinalTCR);                
                ////////////////////////////////////
                //////ILA
                ///////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialILA);
                writer.println("\t\t\t\t"+ILA_1+"</ILA_1>");
                writer.println("\t\t\t\t"+ILA_2+"</ILA_2>");
                writer.println("\t\t\t\t"+ILA_3+"</ILA_3>");
                writer.println("\t\t\t\t"+ILA_4+"</ILA_4>");                   
                writer.println("\t\t\t"+etiquetaFinalILA);
                ////////////////////////////////////
                //////DFA
                ///////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialDFA);
                writer.println("\t\t\t\t"+DFA_1+"</DFA_1>");
                writer.println("\t\t\t\t"+DFA_2+"</DFA_2>");
                writer.println("\t\t\t\t"+DFA_3+"</DFA_3>");
                writer.println("\t\t\t\t"+DFA_4+"</DFA_4>");      
                writer.println("\t\t\t\t"+DFA_5+"</DFA_5>");
                writer.println("\t\t\t\t"+DFA_6+"</DFA_6>");
                writer.println("\t\t\t\t"+DFA_7+"</DFA_7>");
                writer.println("\t\t\t\t"+DFA_8+"</DFA_8>");
                writer.println("\t\t\t"+etiquetaFinalDFA);
                ////////////////////////////////////
                //////ICR
                ///////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialICR);
                writer.println("\t\t\t\t"+ICR_1+"</ICR_1>");           
                writer.println("\t\t\t"+etiquetaFinalICR);
                ////////////////////////////////////
                //////CDA
                ///////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialCDA);
                writer.println("\t\t\t\t"+CDA_1+"</CDA_1>");     
                writer.println("\t\t\t\t"+CDA_2+"</CDA_2>");
                writer.println("\t\t\t\t"+CDA_3+"</CDA_3>");
                writer.println("\t\t\t\t"+CDA_4+"</CDA_4>");
                writer.println("\t\t"+etiquetaFinalCDA);
                ////////////////////////////////////
                //////GTA
                ///////////////////////////////////
                writer.println("\t\t\t"+etiquetaInicialGTA);
                writer.println("\t\t\t\t"+GTA_1+"</GTA_1>");     
                writer.println("\t\t\t\t"+GTA_2+"</GTA_2>");
                writer.println("\t\t\t"+etiquetaFinalGTA);
                writer.println("\t\t"+etiquetaFinalADQ);
                ////////////////////////////////////////////
                ///////TOTALES
                ///////////////////////////////////////////
                writer.println("\t\t"+etiquetaInicialTOT);
                writer.println("\t\t\t"+TOT_1+"</TOT_1>");
                writer.println("\t\t\t"+TOT_2+"</TOT_2>");
                writer.println("\t\t\t"+TOT_3+"</TOT_3>");
                writer.println("\t\t\t"+TOT_4+"</TOT_4>");
                writer.println("\t\t\t"+TOT_5+"</TOT_5>");
                writer.println("\t\t\t"+TOT_6+"</TOT_6>");
                writer.println("\t\t\t"+TOT_7+"</TOT_7>");
                writer.println("\t\t\t"+TOT_8+"</TOT_8>");
                //writer.println("\t\t\t"+TOT_9+"</TOT_9>");
                //writer.println("\t\t\t"+TOT_10+"</TOT_10>");
                writer.println("\t\t"+etiquetaFinalTOT);
                ///////////////////////////////////////////////
                //////TIM
                ///////////////////////////////////////////////
                String consultaIVA = "select tbMaestroTipoIva.IDTipoIva, factor, BaseImponible, ImpIva from tbFacturaVentaBaseImponible inner join tbMaestroTipoIva on tbMaestroTipoIva.IDTipoIva = tbFacturaVentaBaseImponible.IDTipoIva  where IDFactura= "+datosDocumento.getString("IDFactura");
                ResultSet resultadoIVA = fachada.ejecutarConsulta(consultaIVA);
                
                double baseIva = 0.00;
                double factorIva = 0.00;
                double valorIVA = 0.00;
                double totalIVA = 0.00;
                
                String TIM1 = "";
                String IMP1 = "";
                
                if(resultadoIVA != null){
                    TIM1+="<TIM>"+"\n";
                    TIM1+="<TIM_1>"+false+"</TIM_1>\n";
                    
                while(resultadoIVA.next()){
                    valorIVA=resultadoIVA.getDouble("ImpIva");
                    baseIva = resultadoIVA.getDouble("BaseImponible");
                    factorIva = resultadoIVA.getDouble("factor");
                    totalIVA+=valorIVA;
                    
                    IMP1+="<IMP>\n";
                    IMP1+="\t<IMP_1>01</IMP_1>\n";
                    IMP1+="\t<IMP_2>"+formateador(baseIva)+"</IMP_2>\n";
                    IMP1+="\t<IMP_3>COP</IMP_3>\n";
                    IMP1+="\t<IMP_4>"+formateador(valorIVA)+"</IMP_4>\n";
                    IMP1+="\t<IMP_5>COP</IMP_5>\n";
                    IMP1+="\t<IMP_6>"+factorIva+"</IMP_6>\n";                   
                    IMP1+="</IMP>\n";
                }
                
                    TIM1+="<TIM_2>"+totalIVA+"</TIM_2>\n"; 
                    TIM1+="<TIM_3>COP</TIM_3>\n";
                    TIM1+= IMP1;                     
                    TIM1+="</TIM>\n";
                    
                    if(totalIVA != 0){
                    writer.println(TIM1);
                    }
                }
                
                String consultaImpuestos = "SELECT IDImpuesto, Valor, Importe FROM tbFacturaVentaImpuesto where IDFactura= "+datosDocumento.getString("IDFactura");
                ResultSet resultadoImpuestos = fachada.ejecutarConsulta(consultaImpuestos);
                
                double baseImp = 0.00;
                double factorImp = 0.00;
                double valorImp = 0.00;
                double totalImp = 0.00;
                String idImpuesto = "";
                        
                String TIM2 = "";
                String IMP2 = "";

                
                if(resultadoImpuestos != null){    
                    TIM2+="<TIM>"+"\n";
                    TIM2+="<TIM_1>"+false+"</TIM_1>\n";
                while(resultadoImpuestos.next()){
                    idImpuesto = resultadoImpuestos.getString("IDImpuesto");
                    factorImp =  resultadoImpuestos.getDouble("Valor");
                    valorImp = resultadoImpuestos.getDouble("Importe");
                    baseImp = valorImp/factorImp*100;
                    totalImp+= valorImp;                  
                    
                    IMP2+="<IMP>\n";
                    IMP2+="\t<IMP_1>"+idImpuesto+"</IMP_1>\n";
                    IMP2+="\t<IMP_2>"+formateador(baseImp)+"</IMP_2>\n";
                    IMP2+="\t<IMP_3>COP</IMP_3>\n";
                    IMP2+="\t<IMP_4>"+formateador(valorImp)+"</IMP_4>\n";
                    IMP2+="\t<IMP_5>COP</IMP_5>\n";
                    IMP2+="\t<IMP_6>"+factorImp+"</IMP_6>\n";                   
                    IMP2+="</IMP>\n";
                    
                }
                    TIM2+="<TIM_2>"+totalImp+"</TIM_2>\n"; 
                    TIM2+="<TIM_3>COP</TIM_3>\n";
                    TIM2+= IMP2;                     
                    TIM2+="</TIM>\n";
                    
                    if(totalImp != 0){
                    writer.println(TIM2);
                    }
                }
                
                String consultaRetenciones="select RVL.ImporteRetencion,RVL.BaseRetencion,RVL.Porcentaje,LMR.IDRetencion,LMR.TipoRetencion "
                        +"from tbLatRetencionVentaLinea as RVL JOIN tbLatMaestroRetencion LMR ON RVL.IDRetencion = LMR.IDRetencion "
                        +"where IDFactura="+datosDocumento.getString("IDFactura");
                ResultSet resultadoRetenciones = fachada.ejecutarConsulta(consultaRetenciones);
                
                double baseRet = 0.00;
                double factorRet = 0.00;
                double valorRet = 0.00;
                double totalRet = 0.00;
                String idRetencion = "";
                String tipoRetencion = "";
                        
                String TIM3 = "";
                String IMP3 = "";

                 
                    
                while(resultadoRetenciones.next()){
                    TIM3+="<TIM>"+"\n";
                    TIM3+="<TIM_1>"+true+"</TIM_1>\n";
                    
                    tipoRetencion = resultadoRetenciones.getString("TipoRetencion");
                    factorRet =  resultadoRetenciones.getDouble("Porcentaje");
                    valorRet = resultadoRetenciones.getDouble("ImporteRetencion");
                    baseRet = resultadoRetenciones.getDouble("BaseRetencion");
                    totalRet+= valorRet;    
                    if(tipoRetencion.equals("0")){
                        idRetencion = "06";
                    }else if(tipoRetencion.equals("1")){
                        idRetencion = "05";
                    }else if(tipoRetencion.equals("2")){
                        idRetencion = "07";
                    }
                    
                    IMP3+="<IMP>\n";
                    IMP3+="\t<IMP_1>"+idRetencion+"</IMP_1>\n";
                    IMP3+="\t<IMP_2>"+formateador(baseRet)+"</IMP_2>\n";
                    IMP3+="\t<IMP_3>COP</IMP_3>\n";
                    IMP3+="\t<IMP_4>"+formateador(valorRet)+"</IMP_4>\n";
                    IMP3+="\t<IMP_5>COP</IMP_5>\n";
                    IMP3+="\t<IMP_6>"+factorRet+"</IMP_6>\n";                   
                    IMP3+="</IMP>\n";
                    TIM3+="<TIM_2>"+valorRet+"</TIM_2>\n"; 
                    TIM3+="<TIM_3>COP</TIM_3>\n";
                    TIM3+= IMP3;                     
                    TIM3+="</TIM>\n";
                    writer.println(TIM3);
                    TIM3 = "";
                    IMP3="";
                }           
                
                
                ///////////////////////////////////////////////
                //////DSC
                ///////////////////////////////////////////////
                
                /*
                if(totalDcto != 0){
                    writer.println("\t\t"+etiquetaInicialDSC);
                writer.println("\t\t\t"+DSC_1+"</DSC_1>");
                writer.println("\t\t\t"+DSC_2+"</DSC_2>");
                writer.println("\t\t\t"+DSC_3+"</DSC_3>");
                writer.println("\t\t\t"+DSC_4+"</DSC_4>");
                writer.println("\t\t\t"+DSC_5+"</DSC_5>");
                writer.println("\t\t\t"+DSC_7+"</DSC_7>");
                writer.println("\t\t\t"+DSC_8+"</DSC_8>");     
                writer.println("\t\t\t"+DSC_10+"</DSC_10>");
                writer.println("\t\t"+etiquetaFinalDSC);
                        }
*/
                ///////////////////////////////////////////////
                //////DFR
                //////////////////////////////////////////////
                //writer.println("\t\t"+etiquetaInicialDRF);
                //writer.println("\t\t\t"+DRF_2+"</DRF_2>");
                //writer.println("\t\t\t"+DRF_3+"</DRF_3>");
                //writer.println("\t\t\t"+DRF_4+"</DRF_4>");
                //writer.println("\t\t\t"+DRF_5+"</DRF_5>");
                //writer.println("\t\t\t"+DRF_6+"</DRF_6>");
                //writer.println("\t\t"+etiquetaFinalDRF);
                writer.println("\t\t"+etiquetaInicialDRF);
                writer.println("\t\t\t<DRF_1>201911110152</DRF_1>");
                writer.println("\t\t\t<DRF_2>2019-11-11</DRF_2>");
                writer.println("\t\t\t<DRF_3>2020-12-31</DRF_3>");
                writer.println("\t\t\t<DRF_4>TCFA</DRF_4>");
                writer.println("\t\t\t<DRF_5>1</DRF_5>");
                writer.println("\t\t\t<DRF_6>5000000</DRF_6>");
                writer.println("\t\t"+etiquetaFinalDRF);
                //////////////////////////////////////////
                ///////NOT
                writer.println("\t\t"+etiquetaInicialNOT);
                writer.println("\t\t\t"+notaFactura+"</NOT_1>");
                writer.println("\t\t"+etiquetaFinalNOT);
                //////////////////////////////////////////
                ///////MEP
                writer.println("\t\t"+etiquetaInicialMEP);
                writer.println("\t\t\t"+MEP_1);
                writer.println("\t\t\t"+MEP_2);
                writer.println("\t\t\t"+MEP_3);
                writer.println("\t\t"+etiquetaFinalMEP);
                ///////////////////////////////////////////////                
                
                ///////////////////////////////////////////////
                ///////CTS
                ///////////////////////////////////////////////
                //writer.println("\t"+"\t"+etiquetaInicialCTS);
                //writer.println("\t"+"\t"+infoCarvajal+"</CTS_1>");
                //writer.println("\t"+"\t"+etiquetaFinalCTS);
                //////////////////////////////////////////////
                //////ITE
                /////////////////////////////////////////////
                 int cantidadLineas = 0;

                while (resultadoLineas.next()) {
                    
                    cantidad = resultadoLineas.getDouble("Cantidad");
                    importe = resultadoLineas.getDouble("Importe");
                    precio = resultadoLineas.getDouble("Precio");
                    descArticulo = resultadoLineas.getString("DescArticulo");
                    iva = resultadoLineas.getDouble("Factor");
                    dto1 = resultadoLineas.getDouble("Dto1")/divisor;
                    dto2 = resultadoLineas.getDouble("Dto2")/divisor;
                    dto3 = resultadoLineas.getDouble("Dto3")/divisor;
                    dto = resultadoLineas.getDouble("Dto")/divisor;
                    idLineaFactura = resultadoLineas.getString("IDLineaFactura");
                    
                    dtos[0]=dto;
                    dtos[1]=dto1;                    
                    dtos[2]=dto2;
                    dtos[3]=dto3;
                    
                    for(int i=0;i<dtos.length;i++){
                    valorDto1+= calculaDescuento(cantidad * precio,dtos)[i][0];
                    }
                    
                    idUdMedida = resultadoLineas.getString("IDUdMedida");
                    idArticulo = resultadoLineas.getString("IDArticulo");
                    
                    subtotal = subtotal + (cantidad * precio);
                    totalDcto += valorDto1;
                    mntIva = importe * iva / divisor;
                    regalo = resultadoLineas.getInt("Regalo");
                    lote = resultadoLineas.getString("LoteItem");
                    String unidadItem = "";

                    cantidadLineas++;

                    ITE_1 = "<ITE_1>" + cantidadLineas + "";
                    ITE_3 = "<ITE_3>" + cantidad + "";
                    //ITE_4 = "<ITE_4>" + idUdMedida + "";
                    if(idUdMedida.equals("PQT")){
                        unidadItem = "PQ";
                    }else if(idUdMedida.equals("UND")){
                        unidadItem = "94";
                    }else{
                    unidadItem = "NAR";
                    }
                    ITE_4 = "<ITE_4>"+unidadItem;
                    ITE_5 = "<ITE_5>" + formateador(importe);
                    ITE_6 = "<ITE_6>" + moneda + "";
                    ITE_7 = "<ITE_7>" + formateador(precio);
                    ITE_8 = "<ITE_8>" + moneda + "";
                    //ITE_9 = "<ITE_9>" + idArticulo + "";
                    ITE_11 = "<ITE_11>" + descArticulo + "";
                    ITE_19 = "<ITE_19>" + formateador(importe)+ "";
                    ITE_20 = "<ITE_20>" + moneda + "";
                    ITE_27 = "<ITE_27>" + cantidad + "";
                    //ITE_28 = "<ITE_28>" + idUdMedida + "";
                    ITE_28 = "<ITE_28>"+unidadItem;
                    
                    IIM_1 = "<IIM_1>";
                    IIM_2 = "<IIM_2>";
                    IIM_3 = "<IIM_3>";
                    IIM_4 = "<IIM_4>";
                    IIM_5 = "<IIM_5>";
                    IIM_6 = "<IIM_6>";
                    

                    if (lote != null) {
                        String loteItem = "<LOT>" + "<LOT_1>" + lote + "";
                        itemsFactura += loteItem;
                    }

                    itemsFactura += etiquetaFinalITE;
                writer.println("\t"+etiquetaInicialITE);
                writer.println("\t\t"+ITE_1+"</ITE_1>");
                writer.println("\t\t"+ITE_3+"</ITE_3>");
                writer.println("\t\t"+ITE_4+"</ITE_4>");
                writer.println("\t\t"+ITE_5+"</ITE_5>");
                writer.println("\t\t"+ITE_6+"</ITE_6>");
                writer.println("\t\t"+ITE_7+"</ITE_7>");
                writer.println("\t\t"+ITE_8+"</ITE_8>");
                //writer.println("\t\t"+ITE_9+"</ITE_9>");
                writer.println("\t\t"+ITE_11+"</ITE_11>");
                writer.println("\t\t"+ITE_19+"</ITE_19>");
                writer.println("\t\t"+ITE_20+"</ITE_20>");
                writer.println("\t\t"+ITE_27+"</ITE_27>");
                writer.println("\t\t"+ITE_28+"</ITE_28>");
                
                //////////////////////////////////////
                ///////LOTE
                //////////////////////////////////////

                if (lote != null) {
                    writer.println("\t\t"+etiquetaInicialLote);
                    writer.println("\t\t\t"+"<LOT_1>"+lote+"</LOT_1>");
                    writer.println("\t\t"+etiquetaFinalLote);
                }
                ////////////////////////////////////////
                ////////IAE CODIGO DE ARTICULO SEGUN ESTANDAR
                IAE_1 = idArticulo;
                IAE_2 = "999";
                
                writer.println("\t\t"+etiquetaInicialIAE);
                writer.println("\t\t\t<IAE_1>"+IAE_1+"</IAE_1>");
                writer.println("\t\t\t<IAE_2>"+IAE_2+"</IAE_2>");
                writer.println("\t\t"+etiquetaFinalIAE);
                //////////////////////////////////////
                ///////DESCUENTOS
                //////////////////////////////////////
                
                double baseActual = cantidad * precio;
                int j =0;
                double descuentos[][] = new double[dtos.length][0];
                
                descuentos = calculaDescuento(baseActual,dtos);
                
                for(int i = 0; i<descuentos.length;i++){              
                                  
                    
                    if(descuentos[i][0] != 0.0){
                    j++;
                    writer.println("\t\t<IDE>");
                    writer.println("\t\t<IDE_1>false</IDE_1>");
                    writer.println("\t\t<IDE_2>"+formateador(descuentos[i][0])+"</IDE_2>");
                    writer.println("\t\t<IDE_3>COP</IDE_3>");
                    writer.println("\t\t<IDE_6>"+dtos[i]*100+"</IDE_6>");
                    writer.println("\t\t<IDE_7>"+formateador(descuentos[i][1])+"</IDE_7>");
                    writer.println("\t\t<IDE_8>COP</IDE_8>");
                    writer.println("\t\t<IDE_10>"+j+"</IDE_10>");
                    writer.println("\t\t</IDE>");
                    
                    }
                    
                  
                }
                
                
                
                
                //////////////////////////////////////
                ///////IMPUESTOS
                //////////////////////////////////////   
                
                String sumaImpuestos = "SELECT sum(Importe)as Importe FROM tbFacturaVentaImpuesto where IDLineaFactura= "+idLineaFactura;
                ResultSet resultadoSumaImpuestos = fachada.ejecutarConsulta(sumaImpuestos);
                double impuestoLinea = 0.0;
                
                while(resultadoSumaImpuestos.next()){   
                    impuestoLinea= resultadoSumaImpuestos.getDouble("Importe");
                }
                    
                if(valorIva != 0){
                    writer.println("\t\t<TII>");
                    writer.println("\t\t\t<TII_1>"+formateador(impuestoLinea+mntIva)+"</TII_1>");
                    writer.println("\t\t\t<TII_2>"+moneda+"</TII_2>");
                    writer.println("\t\t\t<TII_3>"+false+"</TII_3>");
                    
                    writer.println("\t\t\t<IIM>");    
                    writer.println("\t\t\t\t<IIM_1>01</IIM_1>");
                    writer.println("\t\t\t\t<IIM_2>"+formateador(mntIva)+"</IIM_2>");
                    writer.println("\t\t\t\t<IIM_3>"+moneda+"</IIM_3>");
                    writer.println("\t\t\t\t<IIM_4>"+formateador(importe)+"</IIM_4>");
                    //writer.println("\t\t\t\t<IIM_4>0</IIM_4>");
                    writer.println("\t\t\t\t<IIM_5>"+moneda+"</IIM_5>");
                    writer.println("\t\t\t\t<IIM_6>"+iva+"</IIM_6>");
                    writer.println("\t\t\t</IIM>");   
                    
                     //writer.println("\t\t</TII>");
                    
                    }
                    
                
                
                consultaImpuestos = "SELECT IDImpuesto, Valor, Importe FROM tbFacturaVentaImpuesto where IDLineaFactura= "+idLineaFactura;
                resultadoImpuestos = fachada.ejecutarConsulta(consultaImpuestos);
                

                
                    
                while(resultadoImpuestos.next()){
                    if(valorIva == 0){
                        writer.println("\t\t<TII>");
                        writer.println("\t\t\t<TII_1>"+formateador(resultadoImpuestos.getDouble("Importe"))+"</TII_1>");
                        writer.println("\t\t\t<TII_2>"+moneda+"</TII_2>");
                        writer.println("\t\t\t<TII_3>"+false+"</TII_3>");
                    }

                    
                    writer.println("\t\t\t<IIM>");    
                    writer.println("\t\t\t\t<IIM_1>"+resultadoImpuestos.getString("IDImpuesto")+"</IIM_1>");
                    writer.println("\t\t\t\t<IIM_2>"+formateador(resultadoImpuestos.getDouble("Importe"))+"</IIM_2>");
                    writer.println("\t\t\t\t<IIM_3>"+moneda+"</IIM_3>");
                    writer.println("\t\t\t\t<IIM_4>"+formateador(importe)+"</IIM_4>");
                    //writer.println("\t\t\t\t<IIM_4>0</IIM_4>");
                    writer.println("\t\t\t\t<IIM_5>"+moneda+"</IIM_5>");
                    writer.println("\t\t\t\t<IIM_6>"+formateador(resultadoImpuestos.getDouble("Valor"))+"</IIM_6>");
                    writer.println("\t\t\t</IIM>"); 
                    
                }
                if(valorIva != 0 || impuestoLinea !=0 ){
                    writer.println("\t\t</TII>");
                }
                
               
                
                
                
                resultadoRetenciones=fachada.ejecutarConsulta(consultaRetenciones);
                
                String consultaReteCliente = "select IDCliente, IDRetencionGremio, IDRetencionIva, IDRetencionICA, AplicarRetencion, "
                        + "AplicarImporteRetencion, AplicarRetencionCREE from tbLatClienteRetencion where IDCliente ="+"'"+cifCliente+"'";
                 
                ResultSet resultadoReteCliente = fachada.ejecutarConsulta(consultaReteCliente);
                
                String consultaReteItem = "select * from tbLatArticuloRetencion where IDArticulo = "+"'"+idArticulo+"'";
                Double sumaRetenciones = 0.0;
                Double valorRetencion = 0.0;
                Double baseRetencion = 0.0;
                Double porcentajeRetencion = 0.0;
                tipoRetencion = "";
                
                
                String textoRetenciones = "";
                
                
                while(resultadoRetenciones.next()){
                    String retencion = "";
                    retencion = resultadoRetenciones.getString("IDRetencion");
                    
                    tipoRetencion = resultadoRetenciones.getString("TipoRetencion");
                    porcentajeRetencion = resultadoRetenciones.getDouble("Porcentaje");
                                       

                    while(resultadoReteCliente.next()){
                        
                        if(retencion.equals(resultadoReteCliente.getString("IDRetencionIva")) || (retencion.equals(resultadoReteCliente.getString("IDRetencionIca"))) || (retencion.equals(resultadoReteCliente.getString("IDRetencionIva")))|| (retencion.equals(resultadoReteCliente.getString("IDRetencionGremio")))){
                            
                            
                            textoRetenciones+=("\t\t\t<IIM>\n");    
                            if(tipoRetencion.equals("1")){
                                valorRetencion = (mntIva)*porcentajeRetencion/100;
                                baseRetencion = mntIva;
                                textoRetenciones+=("\t\t\t\t<IIM_1>05</IIM_1>\n");
                            }else if(tipoRetencion.equals("2")){
                                valorRetencion = (importe)*porcentajeRetencion/100;
                                baseRetencion = importe;
                            textoRetenciones+=("\t\t\t\t<IIM_1>07</IIM_1>\n");
                            }
                            textoRetenciones+=("\t\t\t\t<IIM_2>"+formateador(valorRetencion)+"</IIM_2>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_3>"+moneda+"</IIM_3>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_4>"+formateador(baseRetencion)+"</IIM_4>\n");
                            //textoRetenciones+=("\t\t\t\t<IIM_4>0</IIM_4>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_5>"+moneda+"</IIM_5>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_6>"+porcentajeRetencion+"</IIM_6>\n");
                            textoRetenciones+=("\t\t\t</IIM>\n");  
                            sumaRetenciones+=valorRetencion;
                        }
                    }
                    ResultSet resultadoReteItem = fachada.ejecutarConsulta(consultaReteItem);
                    while(resultadoReteItem.next()){
                        valorRetencion = (importe)*porcentajeRetencion/100;
                        if(retencion.equals(resultadoReteItem.getString("IDRetencion"))){
                            sumaRetenciones+=valorRetencion;
                            
                            textoRetenciones+=("\t\t\t<IIM>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_1>06</IIM_1>\n");
                            
                            textoRetenciones+=("\t\t\t\t<IIM_2>"+formateador(valorRetencion)+"</IIM_2>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_3>"+moneda+"</IIM_3>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_4>"+formateador(importe)+"</IIM_4>\n");
                            //textoRetenciones+=("\t\t\t\t<IIM_4>0</IIM_4>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_5>"+moneda+"</IIM_5>\n");
                            textoRetenciones+=("\t\t\t\t<IIM_6>"+porcentajeRetencion+"</IIM_6>\n");
                            textoRetenciones+=("\t\t\t</IIM>\n");  
                        }
                    }
                    
    

                }
                if(valorRetencion != 0.0){
                writer.println("\t\t<TII>");
                writer.println("\t\t\t<TII_1>"+formateador(sumaRetenciones)+"</TII_1>");
                writer.println("\t\t\t<TII_2>"+moneda+"</TII_2>");
                writer.println("\t\t\t<TII_3>"+true+"</TII_3>");
                    
                writer.println(textoRetenciones);
                writer.println("\t\t</TII>");
                }
               
 
                
                
                
                
                
                writer.println("\t"+etiquetaFinalITE);

                }
                
                writer.println(etiquetaFinDoc);               
                
                 writer.close(); 
                            

            }
            
            
            /*
                File archivo;
                FileReader fr;
                BufferedReader br2;
                archivo = new File("C:\\spool\\" + nombreArchivo + ".xml");
                String linea = "";
                String xml = "";
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while((linea=br.readLine())!=null){
                xml+=linea;
            }
            //System.out.println(xml);
            */
            
            

        } catch (Exception e) {
            System.out.println("Error Factura: " + e.toString());
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            //fachada.cerrarConexion();
        }

    }



    public String getDocumento() {

        return contenidoDocumento;
    }

    public int cuentaEspacios(String string) {
        int espacios = 0;
        String cadena = string;
        int j = -1;
        for (int i = 0; i < espacios + 1; i++) {
            j = string.indexOf(" ", j + 1);
            if (j != -1) {
                espacios++;
            }
        }

        System.out.print("Esapcios: " + espacios);
        return espacios;

    }

    public String[] divideString(String string) {
        String[] arreglo = new String[2];
        int espacios = 0;
        //int i=0;
        int j = 0;
        String apellidos = "";
        String nombres = "";

        for (int i = 0; i < 2; i++) {
            j = string.indexOf(" ", j + 1);
            if (j != -1) {
                espacios++;
                if (espacios == 2) {
                    apellidos = string.substring(0, j);
                    nombres = string.substring(j + 1, string.length());
                    arreglo[1] = apellidos;
                    arreglo[0] = nombres;
                    i = espacios;
                }
            }
        }

        return arreglo;
    }

    public String calculaTipoPersona(String tipoDocumento) {
        String tipoPersona = "";
        switch(tipoDocumento){
            case "01": tipoPersona = "2";
            break;
            case "02": tipoPersona = "2";
            break;
            case "03": tipoPersona = "1";
            break;
            case "04": tipoPersona = "1";
            break;
            case "05": tipoPersona = "3";
            break;
            case "06": tipoPersona = "3";
            break;
        }
        return tipoPersona;
    }

    public String[] getRutas(String ruta) {
        
       // String[] rutas = new String[];
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String calculaRegimen(String string) {
        String regimen = "";
        switch(string){
            case "01": regimen = "2";
            break;
            case "02": regimen = "0";
            break;
            case "03": regimen = "2";
            break;
            case "04": regimen = "0";
            break;
            case "05": regimen = "2";
            break;
            case "06": regimen = "0";
            break;
        }
        return regimen;              
    }
    
    private double[][] calculaDescuento(double base, double[] Dtos){
        double[][] arrayDescuento;
        arrayDescuento = new double[Dtos.length][2];
        double dtoTotal = 0.0;
        double baseconDescuento = base;
        
        
        for(int i=0;i<Dtos.length;i++){
            if(Dtos[i]!=0){                                
                arrayDescuento[i][0]=baseconDescuento*Dtos[i];
                arrayDescuento[i][1]=baseconDescuento;
                baseconDescuento-=baseconDescuento*Dtos[i];
            }
            
        }
        
        return arrayDescuento;
    }
    
    private String formateador(Double numero){
        String resultado = "";
        String valor = "";
        int decimales = 0;
        int largo = 0;
        
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formatoDecimal = new DecimalFormat("#0.000000", simbolos);
        valor = String.valueOf(numero);
        
        decimales = valor.length()-valor.indexOf(".")-1 ;
               if(decimales >6){
            valor = formatoDecimal.format(numero);  
               }
            largo  = valor.length()-1;
            for(int i=6;i>0;i--){
                if(valor.charAt(largo) =='0'){
                    valor = valor.substring(0, largo);
                    largo--;
                }else if(valor.charAt(largo) =='.'){
                    resultado = valor+"0";
                }else 
                    resultado = valor;
            }


        
        return resultado;
    }
        
    /*
    private String formateador(Double numero){
        String resultado = "";
        String valor = "";
        int decimales = 0;
        int largo =0;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                simbolos.setDecimalSeparator('.');
                
                DecimalFormat formatoDecimal = new DecimalFormat("#0.000000", simbolos);
                
        
        valor = String.valueOf(numero);
        decimales = valor.length()-valor.indexOf(".")-1 ;
        if(decimales > 6){
            valor = formatoDecimal.format(numero);
            decimales=6;
            largo = valor.length()-1;
            for(int i=6;i>0;i--){
                
                if(valor.charAt(largo) =='0'){
                    valor = valor.substring(0, largo);
                    largo--;
                }else
                    resultado = String.valueOf(roundHalfEven(valor, decimales));
                decimales--;
            }
            
        }/*else if(decimales == 1 & valor.charAt(valor.length()-1) != '0'){
            resultado = String.valueOf(roundHalfEven(valor, decimales));
        }else{
             resultado = String.valueOf(roundHalfEven(valor, decimales-1));
        }
        
        return resultado;
    }*/

    
        private BigDecimal roundHalfEven(String valor, int decimales) {
       
        //paso el string a un BigDecimal
        BigDecimal bigDecimal = new BigDecimal(valor);
        //con el metodo setScale(int newScale, RoundingMode roundingMode) del BigDecimal
        //establezco los parametros del redondeo, en este caso 4 el numero de decimales
        //RoundingMode.HALF_UP el modo de redondeo HALF_UP es de la mitad hacia arriba
        BigDecimal bigDecimalRedondeado = bigDecimal.setScale(decimales, RoundingMode.HALF_EVEN);
        return bigDecimalRedondeado;
    }
    
    private void mensajesErrorCamposCliente(String campo, String id){
        
        System.out.println("Por favor verifique el campo: "+ campo + " del cliente: "+id);
        
    }
    
    private String getCufe(String prefijo, String folio){
        String cufe = "";
        try {
            String username = "erp_doecsystem";
            String password = "4e05d1969a06eef80cbd440b554b437e4b5c296720778e458becc9c96892867b";

 
            
            SERVICESFACTURATECH _service = new SERVICESFACTURATECH_Impl();
            SERVICESFACTURATECHPortType sERVICESFACTURATECHPort = _service.getSERVICESFACTURATECHPort();
            //Response_docs result = sERVICESFACTURATECHPort.ftechActionDocumentStatusFile(username, password, transaccionID);
            //Response_cufe result = sERVICESFACTURATECHPort.ftechActionGetCUFEFile(username, password, prefijo, folio);
            Response_cufe result = sERVICESFACTURATECHPort.ftechActionGetCUFEFile(username, password, prefijo, folio);
          
            cufe = result.getResourceData();
            
            
            System.out.println(result.getError() + " error\n");
            System.out.println(result.getCode()+ " codigo\n");
            System.out.println(result.getSuccess() + " ok\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return cufe;
    }
}
