package br.com.space.connect.domain.valueobjects;

public enum Terreno {
        PLANICIE("Planície", 1.0),
        SOLO_ROCHOSO("Solo Rochoso", 1.5),
        CRATERA("Cratera", 2.0);

        private final String tipoSolo;
        private final double multiplicadorConsumo;


        Terreno(String tipoSolo, double multiplicadorConsumo) {
            this.tipoSolo = tipoSolo;
            this.multiplicadorConsumo = multiplicadorConsumo;
        }

        public String getTipoSolo() { return tipoSolo; }
        public double getMultiplicadorConsumo() { return multiplicadorConsumo; }
}

