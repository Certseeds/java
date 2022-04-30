// SPDX-License-Identifier: AGPL-3.0-or-later
package week08;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "grid_n_m_gene")
public class grid_n_m_gene_warpper {
    private grid_n_m_gene gnmgs;

    @XmlElement(name = "gnmg")
    public grid_n_m_gene getGrid_n_m_gene() {
        return this.gnmgs;
    }

    public void setGrid_n_m_gene(grid_n_m_gene gnmg) {
        this.gnmgs = gnmg;
    }
}
