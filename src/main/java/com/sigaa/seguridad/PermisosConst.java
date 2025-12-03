package com.sigaa.seguridad;

public class PermisosConst {

    public static class VUELOS {
        public static final String CREAR    = "VUELOS_CREAR";
        public static final String EDITAR   = "VUELOS_EDITAR";
        public static final String ESTADO   = "VUELOS_ESTADO";
        public static final String CANCELAR = "VUELOS_CANCELAR";
    }

    public static class GATES {
        public static final String ASIGNAR  = "GATES_ASIGNAR";
        public static final String ESTADO   = "GATES_ESTADO";
    }

    public static class CHECKIN {
        public static final String CREAR    = "CHECKIN_CREAR";
        public static final String EDITAR   = "CHECKIN_EDITAR";
        public static final String CANCELAR = "CHECKIN_CANCELAR";
    }

    public static class BRS {
        public static final String REGISTRAR      = "BRS_REGISTRAR";
        public static final String CAMBIAR_ESTADO = "BRS_CAMBIAR_ESTADO";
    }

    public static class COMERCIAL {
        public static final String LOCAL_EDITAR  = "COM_LOCAL_EDITAR";
        public static final String CONTRATO      = "COM_CONTRATO_CREAR";
        public static final String FACTURAR      = "COM_FACTURA_GENERAR";
        public static final String PAGAR         = "COM_FACTURA_PAGAR";
    }
}