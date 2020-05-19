 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import ws.Response_cufe;
import ws.Response_docs;
import ws.SERVICESFACTURATECH;
import ws.SERVICESFACTURATECHPortType;
import ws.SERVICESFACTURATECH_Impl;

/**
 *
 * @author rocke
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) {
        generaXml app = new generaXml();
       // app.generarConsulta();
        /*
        try {
            String username = "erp_doecsystem";
            String password = "4e05d1969a06eef80cbd440b554b437e4b5c296720778e458becc9c96892867b";
            String transaccionID = "onf7qg1blcj1oiuy3acbxaie4u0xm4x4";
            String prefijo = "TCFA";
            String folio = "19112";
            
            
            SERVICESFACTURATECH _service = new SERVICESFACTURATECH_Impl();
            SERVICESFACTURATECHPortType sERVICESFACTURATECHPort = _service.getSERVICESFACTURATECHPort();
            //Response_docs result = sERVICESFACTURATECHPort.ftechActionDocumentStatusFile(username, password, transaccionID);
            Response_cufe result = sERVICESFACTURATECHPort.ftechActionGetCUFEFile(username, password, prefijo, folio);
            
            
              System.out.println(result.getError() + "\n");
            System.out.println(result.getCode()+ "\n");
            System.out.println(result.getSuccess() + "\n");
            System.out.println(result.getResourceData() + "\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

*/
    }
    
    
}
